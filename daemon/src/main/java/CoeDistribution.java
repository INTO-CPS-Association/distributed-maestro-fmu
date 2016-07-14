import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.io.File;
import java.io.IOException;

import org.intocps.fmi.FmuInvocationException;
import org.intocps.fmi.IFmu;
import org.intocps.fmi.jnifmuapi.Factory;
import org.intocps.orchestration.coe.distribution.CoeDistributionInterface;

public class CoeDistribution extends UnicastRemoteObject implements CoeDistributionInterface
{

	public CoeDistribution ()  throws RemoteException
	{
	
	}
	
	public String returnConfigString() throws RemoteException 
	{
	    return "linux_x86_64";
	}

	@Override
	public IFmu getDistributedFmu(File file) throws RemoteException {
		if (file.isFile())
		{
			try {
				return Factory.create(file);
			} catch (IOException | FmuInvocationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		} else
		{
			try {
				return Factory.createFromDirectory(file);
			} catch (IOException | FmuInvocationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}
	}
}
