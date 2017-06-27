package org.intocps.orchestration.coe.distribution;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.rmi.RemoteException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.commons.io.IOUtils;
import org.intocps.fmi.FmuInvocationException;
import org.intocps.fmi.IFmu;
import org.intocps.fmi.jnifmuapi.Factory;
import org.intocps.fmi.jnifmuapi.FmiUtil;
import org.intocps.orchestration.coe.IFmuFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DistributedFmuFactory implements IFmuFactory
{
	final static Logger logger = LoggerFactory.getLogger(DistributedFmuFactory.class);

	@Override
	public boolean accept(URI uri)
	{
		if (!hasUriRmiSchema(uri))
		{
			return false;
		}

		IDaemon stub = null;
		try
		{
			stub = connect(uri);
		} catch (Exception e1)
		{
			// logger.error("Distributed simulation not possible for FMU: '"+uri+"' defaulting normal behavior. This may lead to load issues due to the usage of a URI containing rmi:");
			return true;
		}
		if (stub != null)
		{
			try
			{
				Map<String, String> daemonConfig = stub.getDaemonConfiguration();

				if (daemonConfig != null)
				{
					try
					{
						File fmuFile = new File(URI.create(uri.getFragment()));
						IFmu fmu = Factory.create(fmuFile);
						File daemonLibrary = FmiUtil.generateLibraryFileFromPlatform(daemonConfig.get("os.name"), daemonConfig.get("os.arch"), FmiUtil.getModelIdentifier(fmu.getModelDescription()), new File("."));

						String libraryZipName = daemonLibrary.getPath().substring(2);

						List<String> files = Collections.synchronizedList(new Vector<>());
						try (ZipFile zipFile = new ZipFile(fmuFile);)
						{
							zipFile.stream().map(ZipEntry::getName).collect(Collectors.toCollection(() -> files));
						}

						if (files.contains(libraryZipName))
						{
							return true;
						} else
						{
							logger.error("Daemon at {} does not support the specified FMU, wrong OS+Arch.", uri);
							return false;
						}

					} catch (IOException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (FmuInvocationException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				return false;

			} catch (RemoteException e)
			{
				logger.error("faild to connect to daemon and obtain config", e);
			}

		}
		return false;
	}

	@Override
	public IFmu instantiate(File sessionRoot, URI uri) throws Exception
	{
		IDaemon stub = connect(uri);
		if (stub != null)
		{
			String fmuName = getFmuName(uri);
			IRemoteFmu fmu = stub.uploadFmu(IOUtils.toByteArray(URI.create(uri.getFragment())), fmuName);
			return new FmuRemoteProxy(fmu);
		}
		return null;
	}

	public static boolean hasUriRmiSchema(URI uri)
	{
		return uri.getScheme() != null && uri.getScheme().equals("rmi");
	}

	public String getFmuName(URI uri)
	{
		String fmuName = uri.getFragment();
		fmuName = fmuName.substring(fmuName.lastIndexOf('/') + 1);
		return fmuName;
	}

	public static IDaemon connect(URI uri) throws Exception
	{
		if (hasUriRmiSchema(uri))
		{
			// TODO check if the current FMI can be accepted by this daemon

			// rmi://localhost/fmu#file://some/path/to/fmu.fmu
			logger.debug("Connecting to daemon {}", uri);
			IDaemon stub = DistributionMaster.getInstance().connectToRemote(uri);

			return stub;

		}
		return null;
	}

}
