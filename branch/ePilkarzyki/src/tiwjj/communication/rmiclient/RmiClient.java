package tiwjj.communication.rmiclient;

import java.rmi.registry.*;
import tiwjj.communication.*;
import java.util.Vector;
import tiwjj.playground.*;

public class RmiClient implements Runnable, IClient  {

    /**
     * Osobny watek dla ciaglego update'owania wektora ruchow
     */
    Thread clientThread;


    /**
     * Referencja na playground, aby moc zmieniac wektor ruchow i ponownie
     * wszystko wyrysowywac
     */
    Playground playground;


    /**
     * Instacje zdalnego obiektu
     */
    private RMIInterface    rmiServer;


    /**
     * todo: przerobic na zabezpieczone polaczenie
     */
    private Registry        registry;


    /**
     * numer druzyny, w ktorej jest ten klient
     */
    private int             team = -1; // domyslnie nie ma druzyny


    /**
     * Flaga dla update. Pierwszy update jest traktowany specjalnie
     */
    private boolean first = true;


    /**
     * Konstruktor
     *
     * @param String host
     * @param int port
     */
    public RmiClient(String host, int port)
    {

        System.setProperty("java.security.policy", "policy");
        System.setProperty("javax.net.ssl.trustStore", ".trustword");

	if (System.getSecurityManager() == null) {
	    System.setSecurityManager(new SecurityManager());
	}

        try
        {
           this.registry = LocateRegistry.getRegistry(host, port);
           this.rmiServer = (RMIInterface)(registry.lookup("rmiServer"));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }


    /**
     * Konstruktor tworzocy obiekt na domyslnych wartosciach odczytywanych
     * ze statycznej klasy Settings
     */
    public RmiClient()
    {
        this(Settings.HOST, Settings.PORT);
    }


    /**
     * Dolacz do gry. Zwraca numer druzyny, jaki dostalismy od serwera
     *
     * @returns int team
     */
    public int joinGame()
    {
        try
        {
            this.team = rmiServer.joinGame();
        }
        catch(Exception e)
        {
            this.team = -1;
            e.printStackTrace();
        }

        return this.team;
    }


    /**
     * Dolacz do konkretnego zespolu. Zwraca numer zespolu, albo -1 jesli 
     * nie jest mozliwe dolaczenie do takiego zespolu
     *
     * @param int team
     *
     * @returns int
     */
    public int joinTeam(int team)
    {
        try
        {
            this.team = rmiServer.joinTeam(team);
        }
        catch(Exception e)
        {
            this.team = -1;
            e.printStackTrace();
        }

        return this.team;
    }


    /**
     * Zwraca identyfikator druzyny
     *
     * @returns boolean
     */
    public int getCurrentTeam()
    {
        try
        {
            return rmiServer.getCurrentTeam();
        }
        catch(Exception e)
        {
            return -1;
        }
    }


     public void nextTeam()
     {
        try
        {
            rmiServer.nextTeam();
        }
        catch(Exception e) {}
     }


     public boolean isMyTurn()
     {
         int currentTeam = this.getCurrentTeam();

         if (-1 != currentTeam && currentTeam == this.team)
         {
             return true;
         }

         return false;
     }

    /**
     * Przeslanie na serwer swojego wektora ruchow
     *
     * @param Vector<Move> moves
     *
     * @returns boolean
     */
    public boolean myMove(Vector<Move> moves)
    {
        try
        {
            return rmiServer.myMove(moves);
        }
        catch(Exception e)
        {
            return false;
        }
    }


    /**
     * Konczy gre.
     * Przeciwnik dostaje informacje o poddaniu meczu. Serwer ustawia sie w
     * gotowosci na nowych zawodnikow
     *
     * @returns boolean
     */
    public boolean end()
    {
        // TODO jakies cialo metody
        // wyslac na serwer komunikat o tym, ze ten klient konczy gre
        return true;
    }


    /**
     * Getter identyfikatora druzyny
     */
    public int getMyTeam()
    {
        return this.team;
    }


    /**
     * Regularnie wywolywana metoda pobierajaca z serwera aktualny wektor ruchow
     *
     * @returns boolean
     */
    public boolean update()
    {
        if (this.first)
        {
            this.wait(1);
            this.first = false;
        }

        try
        {
           Vector<Move> moves = rmiServer.update();
           if (null != moves)
           {
                playground.setMoves(moves);
                playground.update();
                return false;
           }
           return true;
        }
        catch(Exception e)
        {
            return false;
        }
    }


    /**
     * Uruchamia watek odpowiedzialny za ciagla aktualizacje danych w wektorze
     * ruchow
     */
    public void run()
    {
        while(true)
        {
            this.update();
            this.wait(1);
        }
    }

    
    /**
     * Uspienie watku na okreslona liczbe sekund
     *
     * @param int sec
     */
    private void wait(int sec)
    {
            try
            {
                Thread.sleep(sec*1000);
            }
            catch(Exception e) {}
    }

    /**
     * Uruchamia watek
     *
     * @param Playground p
     */
    public void start(Playground p)
    {
        this.playground = p;
        this.clientThread = new Thread(this);
        this.clientThread.start();
    }


    /**
     * Zatrzymuje watek
     */
    public void pause()
    {
        clientThread.suspend();
    }


    /**
     * Wznawia watek
     */
    public void resume()
    {
        clientThread.resume();
    }

}