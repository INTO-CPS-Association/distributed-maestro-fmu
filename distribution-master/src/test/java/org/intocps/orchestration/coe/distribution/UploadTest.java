package org.intocps.orchestration.coe.distribution;

import java.io.File;
import java.net.URI;
import java.util.Map.Entry;

import org.apache.commons.io.IOUtils;
import org.intocps.fmi.Fmi2Status;
import org.intocps.fmi.IFmiComponent;
import org.intocps.orchestration.coe.distribution.daemon.DaemonMain;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class UploadTest
{
	static Thread daemonThread = null;

	@BeforeClass
	public static void launchDeamon() throws InterruptedException
	{
		if (daemonThread != null)
		{
			return;
		}

		daemonThread = new Thread(new Runnable()
		{

			@Override
			public void run()
			{
				DaemonMain.main(new String[] {});

			}
		});
		daemonThread.setDaemon(true);
		daemonThread.start();
		Thread.sleep(1000);
	}

	@AfterClass
	public static void killDaemon()
	{
		if (daemonThread != null)
		{
			try
			{
				daemonThread.interrupt();
			} catch (Exception e)
			{
			}
		}
	}

	@Test
	public void configTest() throws Exception
	{
		URI fmu = URI.create("file://src/test/resources/watertank-c.fmu");
		fmu = new File(".").toURI().resolve(fmu);
		URI uri = URI.create("rmi://localhost/FMU#" + fmu.toString());

		IDaemon daemon = DistributedFmuFactory.connect(uri);
		Assert.assertNotNull(daemon);

		for (Entry<String, String> config : daemon.getDaemonConfiguration().entrySet())
		{
			System.out.println(config.getKey() + " = " + config.getValue());
		}
	}

	@Test
	public void uploadTest() throws Exception
	{
		URI fmu = new URI("src/test/resources/watertank-c.fmu");
		fmu = new File(".").toURI().resolve(fmu);
		URI uri = URI.create("rmi://localhost/FMU#" + fmu.toString());

		IDaemon daemon = DistributedFmuFactory.connect(uri);
		Assert.assertNotNull(daemon);

		IRemoteFmu remoteFmu = daemon.uploadFmu(IOUtils.toByteArray(fmu), "watertank-c.fmu");

		Assert.assertNotNull("remote fmu must not be null", remoteFmu);

		FmuRemoteProxy proxy = new FmuRemoteProxy(remoteFmu);

		proxy.load();
		System.out.println(proxy.getVersion());
		Assert.assertEquals("2.0", proxy.getVersion());

		proxy.unLoad();
	}

	@Test
	public void instantiateTest() throws Exception
	{
		URI fmu = new URI("src/test/resources/watertank-c.fmu");
		fmu = new File(".").toURI().resolve(fmu);
		URI uri = URI.create("rmi://localhost/FMU#" + fmu.toString());

		IDaemon daemon = DistributedFmuFactory.connect(uri);
		Assert.assertNotNull(daemon);

		IRemoteFmu remoteFmu = daemon.uploadFmu(IOUtils.toByteArray(fmu), "watertank-c.fmu");

		Assert.assertNotNull("remote fmu must not be null", remoteFmu);

		FmuRemoteProxy proxy = new FmuRemoteProxy(remoteFmu);

		proxy.load();
		System.out.println(proxy.getVersion());
		Assert.assertEquals("2.0", proxy.getVersion());

		IFmiComponent comp = proxy.instantiate("{8c4e810f-3df3-4a00-8276-176fa3c9f001}", "instance 1", true, true, new org.intocps.fmi.IFmuCallback()
		{

			@Override
			public void log(String instanceName, Fmi2Status status,
					String category, String message)
			{
				// TODO Auto-generated method stub
				System.out.println(message);
			}

			@Override
			public void stepFinished(Fmi2Status status)
			{
				// TODO Auto-generated method stub
				System.out.println(status);
			}
		});
		Assert.assertNotNull("Component must not be null", comp);

		comp.setupExperiment(false, 0, 0, true, 10);
		comp.enterInitializationMode();

		proxy.unLoad();
	}
}
