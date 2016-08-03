package org.intocps.orchestration.coe.distribution;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.rmi.RemoteException;
import java.util.Map.Entry;

import javax.xml.xpath.XPathExpressionException;

import org.apache.commons.io.IOUtils;
import org.intocps.fmi.FmuInvocationException;
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
	public void configTest() throws InterruptedException, RemoteException
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
	public void uploadTest() throws InterruptedException, IOException,
			FmuInvocationException, URISyntaxException
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
	public void instantiateTest() throws InterruptedException, IOException,
			FmuInvocationException, URISyntaxException, XPathExpressionException
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
		
		IFmiComponent comp = proxy.instantiate("{8c4e810f-3df3-4a00-8276-176fa3c9f001}", "instance 1", true,true, null);
		Assert.assertNotNull("Component must not be null", comp);
		
		comp.setupExperiment(false, 0, 0, true, 10);
		comp.enterInitializationMode();

		proxy.unLoad();
	}
}
