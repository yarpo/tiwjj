package tiwjj.communication.client;

import java.io.*;
import java.net.*;
import java.security.*;
import javax.net.*;
import javax.net.ssl.*;
import tiwjj.communication.Exchanger;

public class SecureClient {

    /**
     * Nazwa serwera
     */
    static public String HOST       = "localhost";


    /**
     * Domylsny port, na ktorym pracuje serwer
     */
    static public int PORT          = 4444;

    
    /**
     * Domylsny klucz zabezpieczajacy polaczenie
     */
    static private final String KEY = "keyword";


    /**
     * Socket przez ktory laczy sie z serwerem
     */
    private Socket clientSocket = null;


    /**
     * Konstruktor tworzocy polaczenie z wlasnymi wartosciami hosta i portu
     *
     * @param String host
     * @param int port
     */
    public SecureClient(String host, int port)
    {
        this.connect(host, port);
    }


    /**
     * Konstruktor tworzocy polaczenie z domyslnymi wartosciami hosta i portu
     */
    public SecureClient()
    {
        this(HOST, PORT);
    }


    /**
     * Odbiera dane z serwera
     *
     * @returns Exchange message
     */
    private Exchanger receive() throws Exception
    {
        ObjectInputStream in = new ObjectInputStream(this.clientSocket.
                                                            getInputStream());
        Exchanger message = null;

        try
        {
            message = (Exchanger) in.readObject();
        }
        catch(ClassNotFoundException e)
        {
            System.out.println("Niby nie ma klasy:/");
            return null;
        }

        return message;
    }


    /**
     * Tworzy bezpieczne polaczenie z serwerem - SSL, mozna przekazac wlasny klucz
     *
     * @param String host
     * @param int port
     * @param String keyword
     */
    private void connect(String host, int port, String keyword)
    {
        try
        {
            char[] passphrase = keyword.toCharArray();
            KeyStore ks = KeyStore.getInstance("JKS");
            ks.load(new FileInputStream(".truststore"), passphrase);
            TrustManagerFactory tmf = TrustManagerFactory.
                                                    getInstance("SunX509");
            tmf.init(ks);
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, tmf.getTrustManagers(), null);
            SocketFactory sfSec = sslContext.getSocketFactory();
            this.clientSocket = sfSec.createSocket(host, port);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }


    /**
     * Tworzy bezpieczne polaczenie z serwerem - SSL, klucz domyslny
     */
    private void connect(String host, int port)
    {
        connect(host, port, KEY);
    }

    /**
     * Zamyka polaczenie z serwerem
     */
    public void disconnect()
    {
        // TODO: komunkat do serwera
        try
        {
            System.out.println("Siuaa");
            this.clientSocket.close();
        }
        catch(Exception e)
        {

        }
    }


    /**
     * Wysyla dane na serwer
     *
     * @param Exchanger message
     */
    public void send(Exchanger message)
    {
        try
        {
            ObjectOutputStream oos = new ObjectOutputStream(
                                            clientSocket.getOutputStream());
            oos.writeObject(message);
            oos.flush();
        }
        catch(Exception e) { }
    }


    /**
     * metoda tymczasowa do testow
     * W programie wlasciwym powinna zostac podmieniona
     */
    public void start() throws Exception
    {
        Exchanger a = new Exchanger();
        a.content = "To dziala";
        int i = 0;
        while (true)
        {
            a.content += i;
            this.send(a);
            Exchanger response = this.receive();
            System.out.println(response.content);
            try { Thread.sleep(1000); } catch(Exception e) {}
            i++;
            if (i > 20)
            {
                a.content = "to dziala";
            }

        }
    }
}

