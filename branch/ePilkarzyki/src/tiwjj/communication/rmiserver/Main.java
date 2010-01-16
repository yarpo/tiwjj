package tiwjj.communication.rmiserver;

import java.rmi.registry.*;
import java.rmi.server.*;
import tiwjj.communication.*;

/**
 *
 * @author yarpo
 */
public class Main {
    
    static public void main(String args[])
    {
        String codebase =   "file:/C:/Users/yarpo/Documents/NetBeansProjects/";
               codebase +=  "ePilkarzyki/build/classes/";
        System.setProperty("java.security.policy", "policy");
        System.setProperty("java.rmi.server.codebase", codebase);
        System.setProperty("java.rmi.server.hostname", "localhost");

        System.setProperty("javax.net.ssl.keyStore", ".keyword");
        System.setProperty("javax.net.ssl.keyStorePassword", "keyword");

	if (null == System.getSecurityManager())
        {
	    System.setSecurityManager(new SecurityManager());
	}

        try
        {
            RMIInterface server = new RmiServer();

	    RMIClientSocketFactory csf = new RMISSLClientSocketFactory();
	    RMIServerSocketFactory ssf = new RMISSLServerSocketFactory();
	    RMIInterface stub =
		(RMIInterface) UnicastRemoteObject.exportObject((RMIInterface)server, 0, csf, ssf);


	    LocateRegistry.createRegistry(Settings.PORT);
	    Registry registry = LocateRegistry.getRegistry(Settings.PORT);
	    registry.rebind("rmiServer", stub);
	    System.out.println("Dobra, dziala bound in registry");
        }
        catch (Exception e)
        {
           e.printStackTrace();
           System.exit(1);
        }
    }
}
