import java.rmi.Naming;
import java.rmi.RMISecurityManager;
        
public class CoeDistributionDeamon
{
        
    public static void main(String args[]) {
    	try {
    		System.setSecurityManager(new RMISecurityManager());
    		      
            CoeDistribution stub = new CoeDistribution();

            Naming.rebind("rmi://localhost/FMU", stub);
            
            System.err.println("Remote COE Deamon Ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
