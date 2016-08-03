package org.intocps.orchestration.coe.distribution.daemon.server;

import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import org.intocps.fmi.FmuInvocationException;
import org.intocps.fmi.IFmu;
import org.intocps.orchestration.coe.distribution.IDistributionCoe;

public class CoeDistribution extends UnicastRemoteObject implements
		IDistributionCoe
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 72476226803281111L;

	public CoeDistribution() throws RemoteException
	{

	}

	public String returnConfigString() throws RemoteException
	{
		return "linux_x86_64";
	}

	public IFmu getDistributedFmu(File file, String name) throws IOException,
			FmuInvocationException, RemoteException
	{
		IFmu distFmu = new RemoteFmu(file, name);
		return distFmu;
	}
}
