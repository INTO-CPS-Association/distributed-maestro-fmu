import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import org.intocps.fmi.IFmu;
import org.intocps.orchestration.coe.distribution.CoeDistributionInterface;

public class CoeDistribution extends UnicastRemoteObject implements CoeDistributionInterface
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CoeDistribution ()  throws RemoteException
	{
	
	}
	
	public String return_config_string() throws RemoteException 
	{
	    return "linux_x86_64";
	}

	@Override
	public IFmu start_dist_coe() throws RemoteException {
		return new DFmu();
	}
}
