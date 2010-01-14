package tiwjj.communication.rmiserver;

import java.io.*;
import java.rmi.server.*;
import java.net.*;
import java.security.*;
import javax.net.*;
import javax.net.ssl.*;

public class RMISSLServerSocketFactory
    implements RMIServerSocketFactory {

    private ServerSocketFactory serverSocket;

    public ServerSocket createServerSocket(int port)
    {
        if (null == this.serverSocket)
        {
            try
            {
                //return new XorServerSocket(port, pattern);
                char[] passphrase = "keyword".toCharArray();
                KeyStore ks = KeyStore.getInstance("JKS");
                ks.load(new FileInputStream(".keystore"), passphrase);
                KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
                kmf.init(ks, passphrase);
                SSLContext sslContext = SSLContext.getInstance("TLS");
                sslContext.init(kmf.getKeyManagers(), null, null);
                this.serverSocket = sslContext.getServerSocketFactory();
            }
            catch(Exception e)
            {
                return null;
            }
        }
        try
        {
            return this.serverSocket.createServerSocket(port);
        }
        catch(Exception e) { return null; }
    }

}
