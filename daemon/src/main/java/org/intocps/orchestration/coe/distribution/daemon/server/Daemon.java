package org.intocps.orchestration.coe.distribution.daemon.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.attribute.FileAttribute;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

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

	static final Map<String, String> config = new HashMap<>();

	static
	{
		config.put("os.arch", System.getProperty("os.arch"));
		config.put("os.name", System.getProperty("os.name"));
		config.put("os.version", System.getProperty("os.version"));
	}

	public Daemon() throws RemoteException
	{

	}

	public void initialize(File file)
	{
		if (file != null && file.exists())
		{
			loadProperties(file);
		}
	}

	private void loadProperties(File file)
	{
		Properties prop = new Properties();
		InputStream input = null;

		try
		{

			input = new FileInputStream("config.properties");

			// load a properties file
			prop.load(input);

			for (Entry<Object, Object> p : prop.entrySet())
			{
				config.put("" + p.getKey(), "" + p.getValue());
			}

		} catch (IOException ex)
		{
			ex.printStackTrace();
		} finally
		{
			if (input != null)
			{
				try
				{
					input.close();
				} catch (IOException e)
				{
					e.printStackTrace();
				}
			}

		}
	}

	@Override
	public IRemoteFmu uploadFmu(byte[] buffer, String name)
			throws RemoteException, IOException, FmuInvocationException
	{
		File downloadFolder = Files.createTempDirectory("deamon-fmus", new FileAttribute[] {}).toFile();
		File file = new File(downloadFolder, name);

		FileUtils.writeByteArrayToFile(file, buffer);

		IRemoteFmu distFmu = new RemoteFmu(file);
		return distFmu;
	}

	@Override
	public Map<String, String> getDaemonConfiguration() throws RemoteException
	{

		return config;
	}
}
