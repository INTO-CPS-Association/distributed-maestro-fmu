package org.intocps.orchestration.coe.distribution;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.rmi.Naming;
import java.util.HashMap;

import org.apache.commons.io.IOUtils;
import org.intocps.fmi.FmuInvocationException;
import org.intocps.fmi.IFmu;

public class DistributionMaster
{

	private static DistributionMaster instance;

	private DistributionMaster()
	{
	}

	private HashMap<String, IDaemon> mapOfStubs = new HashMap<String, IDaemon>();

	public static synchronized DistributionMaster getInstance()
	{
		if (instance == null)
		{
			instance = new DistributionMaster();
		}
		return instance;
	}

	public IDaemon connectToRemote(URI remote_path)
	{
		try
		{
			String path = remote_path.toString();
			path = path.substring(0, path.indexOf('#'));
			if (mapOfStubs.containsKey(path))
			{
				return mapOfStubs.get(path);
			}

			System.out.println("Trying to lookup on: " + path);

			IDaemon stub = (IDaemon) Naming.lookup(path);
			mapOfStubs.put(path, stub);
			return stub;
		} catch (Exception e)
		{
			System.err.println("Client exception: " + e.toString());
			e.printStackTrace();
		}
		return null;
	}

	public IFmu uploadFmu(String remote_path, File file)
	{
		IDaemon stub = mapOfStubs.get(remote_path);
		IRemoteFmu distFmu = null;
		try
		{
			distFmu = stub.uploadFmu(IOUtils.toByteArray(new FileInputStream(file)), file.getName());// .getDistributedFmu(file,
																										// remote_path);
		} catch (IOException | FmuInvocationException e)
		{
			e.printStackTrace();
		}
		return new FmuRemoteProxy(distFmu);
	}

}
