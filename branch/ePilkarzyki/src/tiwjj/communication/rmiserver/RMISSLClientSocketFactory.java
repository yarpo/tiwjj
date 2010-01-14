package tiwjj.communication.rmiserver;

import java.io.*;
import java.rmi.server.*;
import java.net.*;
import javax.net.ssl.*;

public class RMISSLClientSocketFactory
    implements RMIClientSocketFactory, Serializable {


    public Socket createSocket(String host, int port) throws IOException {

        System.setProperty("javax.net.ssl.trustStore", ".truststore");
        System.setProperty("javax.net.ssl.trustStorePassword","keyword");

        SSLSocketFactory factory =
                                (SSLSocketFactory)SSLSocketFactory.getDefault();
        SSLSocket socket = (SSLSocket)factory.createSocket(host, port);

        return socket;
    }
}
