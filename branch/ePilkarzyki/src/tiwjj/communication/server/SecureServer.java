package tiwjj.communication.server;

import java.io.*;
import java.net.*;
import java.security.*;
import javax.net.*;
import javax.net.ssl.*;
import java.util.Vector;
import tiwjj.communication.*;

public class SecureServer {

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


    private int teamCounter;

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

        this.teamCounter = 0;
    }


    /**
     * Konstruktor SecureServer - tworzy serwer na domyslnym porcie
     */
    public SecureServer()
    {
        this(Settings.PORT);
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
        this.addClients(clientsNo);

        Exchanger e = new Exchanger();

        while (!this.closed)
        {
            int m = this.clients.size();

            for(int i = 0; i < m; i++)
            {
                Socket client = this.clients.elementAt(i);

                //this.get(this.clients.elementAt(i));
                e.type = Exchanger.MessagesType.GO;
                e.team = i;
                this.send(client, e);
                e = this.get(client);
                
                System.out.println("STATUS od klienta " + i + " " + e.type);

                // TODO: powyliczac wyniki

                e.type = Exchanger.MessagesType.UPDATE;
                e.team = (i+1)%2;

                this.broadcast(e);
            }
        }
    }

    private void broadcast(Exchanger e)
    {
        int n = clients.size();
        for(int i = 0; i < n; i++)
        {
            send( clients.elementAt(i), e );
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
        this.broadcast(e);
    }

    /**
     * Dodaje okreslona liczbe klientow
     *
     * @param int n
     */
    public boolean addClients(int n)
    {
        for(int i = 0; i < n; i++)
        {
            Socket newClient = this.addClient(); // zlapal klienta na nowy socket
            if (null == newClient)
            {
                System.out.println("jakis blad z socketem");
            }

            Exchanger e = this.get(newClient);
            if (Exchanger.MessagesType.CONNECT != e.type)
            {
                System.out.println("No i dupa, bledne dane!");
            }

            e.type = Exchanger.MessagesType.OK;
            e.team = this.teamCounter++;
            boolean result = this.send(newClient, e);

            if (false == result)
            {
                System.out.println("No i nie udalo sie wyslac START!");
            }
        }
        return true;
    }


    /**
     * Dodaje jednego klienta
     */
    public Socket addClient()
    {
        Socket newClient = null;
        try
        {
            newClient = this.serverSocket.accept(); // kolejny klient
            clients.add(newClient);
        }
        catch(Exception e)
        {
            this.closed = true; // blad, zamykamy serwer
            return null;
        }

        return newClient; // zwracamy socket z klientem
    }

    /**
     * Wysyla dane do kokretnego kliekta
     *
     * @param Socket client
     * @param Exchanger message
     */
    private boolean send(Socket client, Exchanger message)
    {
        try
        {
            if (!this.closed)
            {
                ObjectOutputStream oos = new ObjectOutputStream(
                                                client.getOutputStream());
                oos.writeObject(message);
                oos.flush();
            }
        }
        catch(Exception ex)
        {
            this.closed = true;
            return false;
            //ex.printStackTrace();
        }

        return true;
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
            if (!this.closed)
            {
                ObjectInputStream in = new ObjectInputStream(from.getInputStream());
                message = (Exchanger) in.readObject();
            }
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
        char[] passphrase = Settings.SSL.KEYWORD.toCharArray();
        KeyStore ks = KeyStore.getInstance("JKS");
        ks.load(new FileInputStream(Settings.SSL.KEY_File), passphrase);
        KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
        kmf.init(ks, passphrase);
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(kmf.getKeyManagers(), null, null);
        ServerSocketFactory ssfSec = sslContext.getServerSocketFactory();

        serverSocket = ssfSec.createServerSocket(port);
    }
}
