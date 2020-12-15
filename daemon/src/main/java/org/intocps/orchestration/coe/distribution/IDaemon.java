package org.intocps.orchestration.coe.distribution;

import org.intocps.fmi.FmuInvocationException;
import org.intocps.fmi.FmuMissingLibraryException;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

public interface IDaemon extends Remote {
    IRemoteFmu uploadFmu(byte buffer[], String name) throws IOException, FmuInvocationException, FmuMissingLibraryException;

    Map<String, String> getDaemonConfiguration() throws RemoteException;
}
