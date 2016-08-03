package org.intocps.orchestration.coe.distribution.daemon;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

import org.intocps.orchestration.coe.distribution.daemon.server.Daemon;

public class DaemonMain
{

	public static void main(String args[])
	{
		try
		{
			// Host RMI registry on the specified port using the current classpath
			LocateRegistry.createRegistry(1099);
			Daemon stub = new Daemon();

			Naming.rebind("rmi://localhost/FMU", stub);

			System.out.println("Remote COE Deamon Ready");
		} catch (Exception e)
		{
			System.err.println("Server exception: " + e.toString());
			e.printStackTrace();
		}
	}
}
