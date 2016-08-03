import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.io.File;
import java.io.IOException;

import org.intocps.fmi.FmuInvocationException;
import org.intocps.fmi.IFmu;
import org.intocps.orchestration.coe.distribution.CoeDistributionInterface;

public class CoeDistribution extends UnicastRemoteObject implements CoeDistributionInterface
{

	public CoeDistribution () throws RemoteException
	{
	
	}
	
	public String returnConfigString() throws RemoteException
	{
	    return "linux_x86_64";
	}
	
	public IFmu getDistributedFmu(File file, String name) throws IOException, FmuInvocationException, RemoteException
	{
		IFmu distFmu = new DFmu(file, name);
		return distFmu;
	}
}
