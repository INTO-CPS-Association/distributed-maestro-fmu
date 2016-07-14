import java.rmi.Naming;

public class CoeDistributionDeamon
{
        
    public static void main(String args[]) {
    	try {
    		if (System.getSecurityManager() == null) {
    	        System.setSecurityManager(new SecurityManager());
    	    }
    		      
            CoeDistribution stub = new CoeDistribution();

            Naming.rebind("rmi://localhost/FMU", stub);
            
            System.err.println("Remote COE Deamon Ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
