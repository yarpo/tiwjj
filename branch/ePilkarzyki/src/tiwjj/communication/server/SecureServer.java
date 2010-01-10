package tiwjj.communication.server;

import java.io.*;
import java.net.*;
import java.security.*;
import javax.net.*;
import javax.net.ssl.*;
import dataexchange.Exchanger;
import java.util.Vector;

public class SecureServer {

    /**
     * domyslny numer portu, na ktorym dziala serwer
     */
    private final static int PORT = 4444;


    /**
     * Socket serwerowy - tu pracuje na sockecie zabezpieczonym SSL
     */
    private ServerSocket serverSocket;


    /**
     * Wektor klientow podpietych pod serwer
     */
    private Vector<Socket> clients = new Vector<Socket>();


    /**
     * flaga wskazujaca na stan serwera
     */
    private boolean closed = false;


    /**
     * Konstruktor SecureServer - tworzy serwer na podanym porcie
     *
     * @param int port
     */
    public SecureServer(int port)
    {
        try
        {
            this.server(port);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }


    /**
     * Konstruktor SecureServer - tworzy serwer na domyslnym porcie
     */
    public SecureServer()
    {
        this(PORT);
    }

    
    /**
     * Ruchamia serwer, nie dodajac nowych klientow
     * W zamyslne - ktos wczesniej za pomoca addClient dodal
     */
    public void listen()
    {
        this.listen(0);
    }

    
    /**
     * Uruchamia serwer dodajac nowyh klientow
     *
     * @param int n
     */
    public void listen(int clientsNo)
    {
        for(int i = 0; i<clientsNo; i++)
        {
            this.addClient();
        }

        while (!this.closed)
        {
            int m = this.clients.size();

            for(int i = 0; i < m; i++)
            {
                Exchanger e = this.get(this.clients.elementAt(i));
                System.out.println("Wynik od klienta " + i + " " + e.content);

                // TODO: powyliczac wyniki

                this.broadcast("otrzymalem dane od klienta " + i);
            }
        }
    }

    /**
     * Rozsyla dane do wszystkich klientow
     *
     * @param String content
     */
    public void broadcast(String content)
    {
        Exchanger e = new Exchanger();
        e.content = content;

        int n = clients.size();
        for(int i = 0; i < n; i++)
        {
            send( clients.elementAt(i), e );
        }
    }

    /**
     * Dodaje okreslona liczbe klientow
     *
     * @param int n
     */
    public void addClient(int n)
    {
        for(int i = n; i >= 0; i--)
        {
            this.addClient();
        }
    }


    /**
     * Dodaje jednego klienta
     */
    public void addClient()
    {
        try
        {
            clients.add(serverSocket.accept());
        }
        catch(Exception e)
        {
            this.closed = true;
            e.printStackTrace();
        }
    }

    /**
     * Wysyla dane do koknretnego kliekta
     *
     * @param Socket client
     * @param Exchanger message
     */
    private void send(Socket client, Exchanger message)
    {
        try
        {
            ObjectOutputStream oos = new ObjectOutputStream(
                                            client.getOutputStream());
            oos.writeObject(message);
            oos.flush();
        }
        catch(Exception ex)
        {
            this.closed = true;
            ex.printStackTrace();
        }
    }


    /**
     * Nasluchuje odpowiedzi od konretnego klienta
     *
     * @param Socket from
     *
     * @returns Exchanger message
     */
    private Exchanger get(Socket from)
    {
        Exchanger message = null;

        try
        {
            ObjectInputStream in = new ObjectInputStream(from.getInputStream());
            message = (Exchanger) in.readObject();
        }
        catch(Exception e)
        {
            this.closed = true;
            return null;
        }
        return message;
    }


    /**
     * tworzy ServerSocket z zabezpieczeniem SLL na wskazanym porcie
     */
    private void server(int port) throws Exception
    {
        char[] passphrase = "keyword".toCharArray();
        KeyStore ks = KeyStore.getInstance("JKS");
        ks.load(new FileInputStream(".keystore"), passphrase);
        KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
        kmf.init(ks, passphrase);
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(kmf.getKeyManagers(), null, null);
        ServerSocketFactory ssfSec = sslContext.getServerSocketFactory();

        serverSocket = ssfSec.createServerSocket(port);
    }
}
