package org.intocps.orchestration.coe.distribution.daemon.server;

import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.intocps.fmi.FmuInvocationException;
import org.intocps.orchestration.coe.distribution.IDaemon;
import org.intocps.orchestration.coe.distribution.IRemoteFmu;

public class Daemon extends UnicastRemoteObject implements IDaemon
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 72476226803281111L;

	public Daemon() throws RemoteException
	{

	}

	@Override
	public IRemoteFmu uploadFmu(byte[] buffer, String name)
			throws RemoteException, IOException, FmuInvocationException
	{
		File file = new File(name);
		FileUtils.writeByteArrayToFile(file, buffer);

		IRemoteFmu distFmu = new RemoteFmu(file);
		return distFmu;
	}

	@Override
	public Map<String, String> getDaemonConfiguration() throws RemoteException
	{
		Map<String, String> config = new HashMap<>();

		config.put("os.arch", System.getProperty("os.arch"));
		config.put("os.name", System.getProperty("os.name"));
		config.put("os.version", System.getProperty("os.version"));

		return config;
	}
}
