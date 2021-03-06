package org.intocps.orchestration.coe.distribution.daemon;

import org.apache.commons.cli.*;
import org.intocps.orchestration.coe.distribution.daemon.server.Daemon;

import java.io.File;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class DaemonMain {

    public static void main(String args[]) {
        Options options = new Options();
        Option helpOpt = Option.builder("h").longOpt("help").desc("Show this description").build();
        Option preferIp4Opt = Option.builder("ip4").longOpt("prefer-ip4").desc("Set the deamon to prefer ip4 when binding service").build();
        Option configOpt = Option.builder("conf").longOpt("config").desc("Configuration property file").hasArg().build();
        Option serverHostnameOpt = Option.builder("host").longOpt("hostname").desc("Public server host name for Java RMI").hasArg().build();

        options.addOption(configOpt);
        options.addOption(helpOpt);
        options.addOption(serverHostnameOpt);
        options.addOption(preferIp4Opt);

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = null;
        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e1) {
            System.err.println("Parsing failed. Reason: " + e1.getMessage());
            showHelp(options);
            return;
        }

        if (cmd.hasOption(helpOpt.getOpt())) {
            showHelp(options);
            return;
        }

        if (cmd.hasOption(preferIp4Opt.getOpt())) {
            System.setProperty("java.net.preferIPv4Stack", "true");
        }

        if (cmd.hasOption(serverHostnameOpt.getOpt())) {
            String hostName = cmd.getOptionValue(serverHostnameOpt.getOpt());
            System.out.println("Setting host name to: " + hostName);
            System.setProperty("java.rmi.server.hostname", hostName);
        }

        File configFile = null;

        if (cmd.hasOption(configOpt.getOpt())) {
            configFile = new File(cmd.getOptionValue(configOpt.getOpt()).replace('/', File.separatorChar));
        }

        launchDaemonService(configFile);
    }

    private static void launchDaemonService(File configFile) {
        try {
            // Host RMI registry on the specified port using the current class-path
            LocateRegistry.createRegistry(1099);

            Daemon stub = new Daemon();
            stub.initialize(configFile);

            Naming.rebind("rmi://localhost/FMU", stub);

            System.out.println("Remote COE Deamon Ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }

    public static void showHelp(Options options) {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("daemon", options);
    }
}
