package org.intocps.orchestration.coe.distribution;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

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
			String path = remote_path.getScheme() + ":" + remote_path.getPath();
			if (mapOfStubs.containsKey(path))
			{
				return mapOfStubs.get(path);
			}

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

	public IFmu DistributedFmu(String remote_path, File file)
	{
		IDaemon stub = mapOfStubs.get(remote_path);
		IRemoteFmu distFmu = null;
		try
		{
			distFmu = stub.uploadFmu(IOUtils.toByteArray(new FileInputStream(file)), file.getName());//.getDistributedFmu(file, remote_path);
		} catch (IOException | FmuInvocationException e)
		{
			e.printStackTrace();
		}
		return new FmuRemoteProxy(distFmu);
	}

	public void Platform(String remote_path)
	{
		IDaemon stub = mapOfStubs.get(remote_path);
		Map<String, String> response = new HashMap<>();
		try
		{
			response = stub.getDaemonConfiguration();
		} catch (RemoteException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Remote system applicable: " + response);
	}
}
