package org.intocps.orchestration.coe.distribution;

import java.io.File;

import org.intocps.fmi.IFmu;

public class Main {

	public static void main(String[] args) {
		DistributionMaster distMan = DistributionMaster.getInstance();
		distMan.connectToRemote("rmi://localhost/FMU");
		IFmu obj= distMan.DistributedFmu("rmi://localhost/FMU", new File("some path"));

	}

}
