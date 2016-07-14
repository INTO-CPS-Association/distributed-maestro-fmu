package org.intocps.orchestration.coe.distribution;

import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.util.HashMap;

public class DistributionMaster {
	
	private static DistributionMaster instance;
	
	private DistributionMaster()
	{}
	
	private HashMap<String, CoeDistributionInterface> map_of_stubs = new HashMap<String, CoeDistributionInterface>();
	
    public static synchronized DistributionMaster getInstance(){
        if(instance == null){
            instance = new DistributionMaster();
        }
        return instance;
    }
	
	public void connect_to_remote(String remote_path)
	{
	    try
	    {
			System.setSecurityManager(new RMISecurityManager());
			CoeDistributionInterface stub = (CoeDistributionInterface) Naming.lookup(remote_path);
			map_of_stubs.put(remote_path,stub);	    
		} catch (Exception e) {
	        System.err.println("Client exception: " + e.toString());
	        e.printStackTrace();
	    }
	}
	
	public void get_platform(String remote_path)
	{
	    try
	    {
			CoeDistributionInterface stub = map_of_stubs.get(remote_path);
			String response = stub.return_config_string();
        	System.out.println("Remote system applicable: " + response);
		} catch (Exception e) {
	        System.err.println("Client exception: " + e.toString());
	        e.printStackTrace();
	    }
	}
}
