import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.registry.LocateRegistry;
        
public class CoeDistributionDeamon
{
        
    public static void main(String args[]) {
    	try {
    		System.setSecurityManager(new RMISecurityManager());
    		
    		// Host RMI registry on the specified port using the current classpath
    		LocateRegistry.createRegistry(1099);
            CoeDistribution stub = new CoeDistribution();

            Naming.rebind("rmi://localhost/FMU", stub);
            
            System.err.println("Remote COE Deamon Ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
