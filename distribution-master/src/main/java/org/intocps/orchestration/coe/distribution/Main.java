package org.intocps.orchestration.coe.distribution;

import java.io.File;

import org.intocps.fmi.FmuInvocationException;
import org.intocps.fmi.IFmu;

public class Main {

	public static void main(String[] args) {
		DistributionMaster distMan = DistributionMaster.getInstance();
		distMan.connectToRemote("rmi://localhost/FMU");
		
		//IFmuCallback distCallback = null;
		
		IFmu obj = null;
		obj = distMan.DistributedFmu("rmi://localhost/FMU", new File("/home/peter/Documents/rep_test/distributed-fmu/daemon/src/main/resources/watertank.fmu"));
		
		String version = "EMPTY";	
		try {
			obj.load();
		} catch (FmuInvocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//version = obj.getVersion();

		System.out.println("Remote FMU version" + version);
	}

}
