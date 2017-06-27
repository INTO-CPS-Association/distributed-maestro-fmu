package dcoe;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.intocps.orchestration.coe.CoeMain;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import difflib.Delta;
import difflib.DiffUtils;
import difflib.Patch;
import fi.iki.elonen.NanoHTTPD.ResponseException;

public class LocalhostTest
{

	Process p = null;
	boolean ready = false;
	boolean abort = false;
	File configJson = null;
	final String fmiPrefix = "rmi://localhost/FMU#";

	@Before
	public void beforeTest() throws IOException, InterruptedException
	{
		launchDaemon();
		prepareConfig();
	}

	public static String toOsP(String p)
	{
		return p.replace('/', File.separatorChar);
	}

	private void prepareConfig()
	{
		try
		{
			String config = FileUtils.readFileToString(new File(toOsP("src/test/resources/config.json")), "UTF-8");
			configJson = new File(toOsP("target/config.json"));
			config = config.replace("watertankcontroller-c.fmu", new File(toOsP("src/test/resources/watertankcontroller-c.fmu")).toURI().toASCIIString());
			config = config.replace("watertank-c.fmu", fmiPrefix
					+ new File(toOsP("src/test/resources/watertank-c.fmu")).toURI().toASCIIString());
			FileUtils.write(configJson, config);
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	protected void launchDaemon() throws IOException, InterruptedException
	{
		p = JavaProcess.exec(org.intocps.orchestration.coe.distribution.daemon.DaemonMain.class);

		Runtime.getRuntime().addShutdownHook(new Thread()
		{
			public void run()
			{
				if (p.isAlive())
					p.destroyForcibly();
			}
		});

		Thread t = new Thread(new Runnable()
		{

			@Override
			public void run()
			{
				try
				{
					if (p == null)
					{
						return;
					}
					BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
					String line = null;
					try
					{
						while ((line = in.readLine()) != null)
						{
							if (line.contains("Remote COE Deamon Ready"))
							{
								ready = true;
								return;
							}
						}
					} catch (IOException e)
					{
						abort = false;
					}
				} catch (Exception e)
				{
					abort = false;
				}
			}
		});
		t.setDaemon(true);
		t.start();
	}

	@After
	public void afterTest()
	{
		if (p != null && p.isAlive())
		{
			p.destroyForcibly();
		}
	}

	@Test
	public void testWatertankLocalhostOfflineDaemon()
			throws InterruptedException, IOException, ResponseException
	{
		afterTest();

		File output = new File("target/outputOffline.csv");
		if (output.exists())
		{
			output.delete();
		}

		System.setProperty("coe.fmu.custom.factory", org.intocps.orchestration.coe.distribution.DistributedFmuFactory.class.getName());
		CoeMain.main(new String[] { "--oneshot", "--config",
				configJson.getAbsolutePath(), "-s", "0", "--endtime", "1",
				"--result", output.getAbsolutePath() });

		Assert.assertFalse("Expected the simulation to fail", output.exists());
	}

	@Test
	public void testWatertankLocalhost() throws InterruptedException,
			IOException, ResponseException
	{

		long startTime = System.currentTimeMillis(); // fetch starting time
		while (!abort && !ready)
		{
			Thread.sleep(500);
			if (System.currentTimeMillis() - startTime > 5000)
			{
				abort = true;
				break;
			}
		}

		if (abort)
		{
			Assert.fail("Could not launch daemon");
			return;
		}

		File output = new File("target/output.csv");
		if (output.exists())
		{
			output.delete();
		}

		System.setProperty("coe.fmu.custom.factory", org.intocps.orchestration.coe.distribution.DistributedFmuFactory.class.getName());
		CoeMain.main(new String[] { "--oneshot", "--config",
				configJson.getAbsolutePath(), "-s", "0", "--endtime", "1",
				"--result", output.getAbsolutePath() });

		assertResultEqualsDiff(FileUtils.readFileToString(new File("src/test/resources/output.csv"), Charset.forName("UTF-8")), new FileInputStream(output));
	}

	public static void assertResultEqualsDiff(String resultData,
			InputStream resultInputStream0)
	{
		if (resultInputStream0 != null)
		{
			List<String> original = fileToLines(new ByteArrayInputStream(resultData.getBytes(StandardCharsets.UTF_8)));
			List<String> revised = fileToLines(resultInputStream0);

			// Compute diff. Get the Patch object. Patch is the container for computed deltas.
			Patch patch = DiffUtils.diff(original, revised);

			for (Delta delta : patch.getDeltas())
			{
				System.err.println(delta);
				Assert.fail("Expected result and actual differ: " + delta);
			}

		}
	}

	private static List<String> fileToLines(InputStream filename)
	{
		List<String> lines = new LinkedList<String>();
		String line = "";
		try
		{
			BufferedReader in = new BufferedReader(new InputStreamReader(filename));
			while ((line = in.readLine()) != null)
			{
				lines.add(line);
			}
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return lines;
	}

}
