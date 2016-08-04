package org.intocps.orchestration.coe.distribution;

import java.io.File;
import java.net.URI;
import java.rmi.RemoteException;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.intocps.fmi.IFmu;
import org.intocps.orchestration.coe.IFmuFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DistributedFmuFactory implements IFmuFactory
{
	final static Logger logger = LoggerFactory.getLogger(DistributedFmuFactory.class);

	@Override
	public boolean accept(URI uri)
	{
		IDaemon stub = connect(uri);
		if (stub != null)
		{
			try
			{
				Map<String, String> daemonConfig = stub.getDaemonConfiguration();

				if (daemonConfig != null)
				{
					// TODO check if the daemon is capable
				}

				return true;

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
			IRemoteFmu fmu = stub.uploadFmu(IOUtils.toByteArray(URI.create(uri.getFragment())), uri.getFragment());
			return new FmuRemoteProxy(fmu);
		}
		return null;
	}

	public static IDaemon connect(URI uri)
	{
		if (uri.getScheme() != null && uri.getScheme().equals("rmi"))
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
