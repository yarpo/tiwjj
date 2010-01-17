package tiwjj.communication.rmiserver;

import java.io.*;
import java.rmi.server.*;
import java.net.*;
import javax.net.ssl.*;

/**
 * Klasa RMISSLClientSocketFactory implementujaca RMIClientSocketFactory oraz
 * Serializable
 * Pozwala na tworzenie bezpiecznego polaczenia miedzy serwerem a klientem
  * @author  Patryk yarpo Jar
 * @date    6 - 01 - 2009
 */
public class RMISSLClientSocketFactory
    implements RMIClientSocketFactory, Serializable {


    /**
     * Metoda tworzaca socket zabezpieczony SSL
     *
     * @param host
     * @param port
     *
     * @return Socket
     */
    public Socket createSocket(String host, int port) throws IOException {

        SSLSocketFactory factory =
                                (SSLSocketFactory)SSLSocketFactory.getDefault();
        SSLSocket socket = (SSLSocket)factory.createSocket(host, port);

        return socket;
    }
}
