package org.intocps.orchestration.coe.distribution;

import java.io.File;
import java.rmi.Remote;
import java.rmi.RemoteException;

import org.intocps.fmi.FmuInvocationException;
import org.intocps.fmi.IFmu;

public interface CoeDistributionInterface extends Remote {
	
    String returnConfigString() throws RemoteException;
        
    IFmu getDistributedFmu(File file, String name) throws RemoteException;
}
