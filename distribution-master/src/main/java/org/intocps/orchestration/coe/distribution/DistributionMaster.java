package org.intocps.orchestration.coe.distribution;

import java.io.File;
import java.io.IOException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.HashMap;

import org.intocps.fmi.FmuInvocationException;
import org.intocps.fmi.IFmu;

public class DistributionMaster {
	
	private static DistributionMaster instance;
	
	private DistributionMaster()
	{}
	
	private HashMap<String, CoeDistributionInterface> mapOfStubs = new HashMap<String, CoeDistributionInterface>();
	
    public static synchronized DistributionMaster getInstance(){
        if(instance == null){
            instance = new DistributionMaster();
        }
        return instance;
    }
	
	public void connectToRemote(String remote_path)
	{
	    try
	    {
	        if (System.getSecurityManager() == null) {
	            System.setSecurityManager(new SecurityManager());
	        }
			CoeDistributionInterface stub = (CoeDistributionInterface) Naming.lookup(remote_path);
			mapOfStubs.put(remote_path,stub);	    
		} catch (Exception e) {
	        System.err.println("Client exception: " + e.toString());
	        e.printStackTrace();
	    }
	}
	
	public IFmu DistributedFmu(String remote_path, File file)
	{
		CoeDistributionInterface stub = mapOfStubs.get(remote_path);
		IFmu distFmu = null;
		try {
			distFmu = stub.getDistributedFmu(file, remote_path);
		} catch (IOException | FmuInvocationException e) {
			e.printStackTrace();
		}
		return distFmu;
	}
	
	public void Platform(String remote_path)
	{
		CoeDistributionInterface stub = mapOfStubs.get(remote_path);
		String response = "empty";
		try {
			response = stub.returnConfigString();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Remote system applicable: " + response);
	}
}
