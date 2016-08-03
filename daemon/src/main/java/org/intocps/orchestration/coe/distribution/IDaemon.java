package org.intocps.orchestration.coe.distribution;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

import org.intocps.fmi.FmuInvocationException;

public interface IDaemon extends Remote
{
	IRemoteFmu uploadFmu(byte buffer[], String name) throws RemoteException,
			IOException, FmuInvocationException;

	Map<String, String> getDaemonConfiguration() throws RemoteException;
}
