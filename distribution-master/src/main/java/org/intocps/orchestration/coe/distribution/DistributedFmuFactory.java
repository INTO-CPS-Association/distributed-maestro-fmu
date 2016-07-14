package org.intocps.orchestration.coe.distribution;

import java.io.File;

import org.intocps.fmi.IFmu;
import org.intocps.orchestration.coe.IFmuFactory;

public class DistributedFmuFactory implements IFmuFactory
{

	@Override
	public boolean accept(File file)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public IFmu instantiate(File sessionRoot, File file) throws Exception
	{
		// TODO Auto-generated method stub
		return null;
	}

}
