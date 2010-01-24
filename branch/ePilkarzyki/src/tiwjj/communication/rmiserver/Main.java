package tiwjj.communication.rmiserver;

import java.rmi.registry.*;
import java.rmi.server.*;
import tiwjj.communication.*;
import java.net.InetAddress;


/**
 * Klasa Main
 * Pozwala na uruchomienie serwera RMI
 * @author  Patryk yarpo Jar
 * @date    6 - 01 - 2009
 */
public class Main {

    /**
     * 
     * @param args
     */
    static public void main(String args[])
    {
        System.setProperty("java.security.policy", Settings.POLICY);
        System.setProperty("java.rmi.server.codebase", Settings.CODEBASE);
        System.setProperty("java.rmi.server.hostname", Settings.HOST);

        System.setProperty("javax.net.ssl.keyStore", Settings.SSL.KEY_File);
        System.setProperty("javax.net.ssl.keyStorePassword", Settings.SSL.KEYWORD);

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
		(RMIInterface) UnicastRemoteObject.exportObject(server, 0, csf,
                                                                           ssf);


	    LocateRegistry.createRegistry(Settings.PORT);
	    Registry registry = LocateRegistry.getRegistry(Settings.PORT);
	    registry.rebind(Settings.NAME, stub);
            System.out.println("Host: " + InetAddress.getLocalHost()
                                                            .toString());
	    System.out.println("port: " + Settings.PORT);
        }
        catch (Exception e)
        {
           e.printStackTrace();
           System.exit(1);
        }
    }
}
