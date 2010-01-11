package tiwjj.communication.client;

import java.io.*;
import java.net.*;
import java.security.*;
import javax.net.*;
import javax.net.ssl.*;
import tiwjj.communication.*;

public class SecureClient {

    /**
     * Socket przez ktory laczy sie z serwerem
     */
    private Socket clientSocket = null;


    private boolean connected = false;


    private int team;
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
        this(Settings.HOST, Settings.PORT);
    }


    /**
     * Odbiera dane z serwera
     *
     * @returns Exchange message
     */
    public Exchanger receive()
    {
        Exchanger message = null;

        try
        {
            ObjectInputStream in = new ObjectInputStream(this.clientSocket.
                                                            getInputStream());
            message = (Exchanger) in.readObject();
        }
        catch(Exception e)
        {
            return null;
        }

        return message;
    }

    public Exchanger waitForYourTurn()
    {
        Exchanger e = this.receive();

        // jesli to jest jeden z kimunikatow mowiacych o updacie planszy
        if (Exchanger.MessagesType.GO == e.type ||
            Exchanger.MessagesType.UPDATE == e.type)
        {
            return e;
        }

        // to nie jest twoj ruch
        return null;
    }

    public boolean isConnected()
    {
        return this.connected;
    }

    public boolean connection()
    {
        Exchanger e = new Exchanger();

        e.type = Exchanger.MessagesType.CONNECT;
        this.send(e);
        e = this.receive();

        this.connected = (Exchanger.MessagesType.OK == e.type);

        this.team = e.team;

        return this.connected;
    }

    public int getTeam()
    {
        return this.team;
    }

    /**
     * Bezpieczne polaczenie z serwerem - SSL, mozna przekazac wlasny klucz
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
            ks.load(new FileInputStream(Settings.SSL.KEY_File), passphrase);
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
        connect(host, port, Settings.SSL.TRUSTWORD);
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
        catch(Exception e) { }
    }

    public void send(String content)
    {
        Exchanger e = new Exchanger();
        e.content = content;
        this.send(e);
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

    // tymczasowa
    public String alert()
    {
        Exchanger e = this.receive();
        if (null != e)
        {
            return e.content;
        }
        return "";
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
           // try
           // {
                Exchanger response = this.receive();
                System.out.println(response.content);
           // } catch(Exception e) {}
            //a.content += i;
            //this.send(a);
            //Exchanger response = this.receive();
            //System.out.println(response.content);
            //try { Thread.sleep(1000); } catch(Exception e) {}
            //i++;
            if (i > 20)
            {
                a.content = "to dziala";
            }

        }
    }
}

