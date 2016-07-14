package org.intocps.orchestration.coe.distribution;

import java.rmi.Remote;
import java.rmi.RemoteException;

import org.intocps.fmi.IFmu;

public interface CoeDistributionInterface extends Remote {
	
    String return_config_string() throws RemoteException;
        
    IFmu start_dist_coe() throws RemoteException, Exception;
}
