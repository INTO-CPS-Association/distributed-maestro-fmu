package org.intocps.orchestration.coe.distribution;

import java.io.File;
import java.rmi.Naming;
import java.util.HashMap;

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
	    try
	    {
			CoeDistributionInterface stub = mapOfStubs.get(remote_path);
			IFmu distFmu = stub.getDistributedFmu(file);
			return distFmu;
		} catch (Exception e) {
	        System.err.println("Client exception: " + e.toString());
	        e.printStackTrace();
	        return null;
	    }
	}
	
	public void Platform(String remote_path)
	{
	    try
	    {
			CoeDistributionInterface stub = mapOfStubs.get(remote_path);
			String response = stub.returnConfigString();
        	System.out.println("Remote system applicable: " + response);
		} catch (Exception e) {
	        System.err.println("Client exception: " + e.toString());
	        e.printStackTrace();
	    }
	}
}
