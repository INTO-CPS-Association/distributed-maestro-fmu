package org.intocps.orchestration.coe.distribution.daemon;

import java.io.File;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.intocps.orchestration.coe.distribution.daemon.server.Daemon;

public class DaemonMain
{

	public static void main(String args[])
	{
		Options options = new Options();
		Option helpOpt = Option.builder("h").longOpt("help").desc("Show this description").build();
		Option configOpt = Option.builder("conf").longOpt("config").desc("Configuration property file").hasArg().build();
		options.addOption(configOpt);
		options.addOption(helpOpt);

		CommandLineParser parser = new DefaultParser();
		CommandLine cmd = null;
		try
		{
			cmd = parser.parse(options, args);
		} catch (ParseException e1)
		{
			System.err.println("Parsing failed.  Reason: " + e1.getMessage());
			showHelp(options);
			return;
		}

		if (cmd.hasOption(helpOpt.getOpt()))
		{
			showHelp(options);
			return;
		}

		File configFile = null;

		if (cmd.hasOption(configOpt.getOpt()))
		{
			configFile = new File(cmd.getOptionValue(configOpt.getOpt()).replace('/', File.separatorChar));
		}

		launchDaemonService(configFile);
	}

	private static void launchDaemonService(File configFile)
	{
		try
		{
			// Host RMI registry on the specified port using the current class-path
			LocateRegistry.createRegistry(1099);

			Daemon stub = new Daemon();
			stub.initialize(configFile);

			Naming.rebind("rmi://localhost/FMU", stub);

			System.out.println("Remote COE Deamon Ready");
		} catch (Exception e)
		{
			System.err.println("Server exception: " + e.toString());
			e.printStackTrace();
		}
	}

	public static void showHelp(Options options)
	{
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp("daemon", options);
	}
}
