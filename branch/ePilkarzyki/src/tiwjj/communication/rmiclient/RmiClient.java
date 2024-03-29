package tiwjj.communication.rmiclient;

import java.rmi.registry.*;
import tiwjj.communication.*;
import java.util.Vector;
import tiwjj.playground.*;

/**
 * Klasa RmiClient implementujaca Runnable oraz IClient
 * Pozwala na wymiane danych z serwerem poprzez RMI
 * @author  Patryk yarpo Jar
 * @date    6 - 01 - 2009
 */

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
     * Flaga wskazujaca na stan watku update'u
     */
    private boolean stopped = false;


    /**
     * Konstruktor
     *
     * @param String host
     * @param int port
     */
    public RmiClient(String host, int port)
    {

        System.setProperty("java.security.policy", Settings.POLICY);
        System.setProperty("javax.net.ssl.trustStore", Settings.SSL.TRU_File);

	if (System.getSecurityManager() == null) {
	    System.setSecurityManager(new SecurityManager());
	}

        try
        {
           this.registry = LocateRegistry.getRegistry(host, port);
           this.rmiServer = (RMIInterface)(registry.lookup(Settings.NAME));
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
     * Sprawdza czy podany uzytkownik ma takie haslo
     *
     * @param String user
     * @param String password
     *
     * @return boolean
     */
     public boolean login(String user, String pass)
     {
         boolean result = false;
         try
         {
             result = this.rmiServer.login(user, pass);
         }
         catch(Exception e) {}

         return result;

     }

     
    /**
     * Dolacz do gry. Zwraca numer druzyny, jaki dostalismy od serwera
     *
     * @return int team
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
     * @return int
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
     * @return boolean
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


     /**
      * Zmienia druzyne - koniec mojej tury
      */
     public void nextTeam()
     {
        try
        {
            rmiServer.nextTeam();
        }
        catch(Exception e) {}
     }


     /**
      * Sprawdza czy teraz jest tura tego zawodnika
      *
      * @return boolean
      */
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
     * @return boolean
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
     * @return boolean
     */
    public boolean end()
    {
        try
        {
            this.stopped = true;
            this.wait(2);
            this.clientThread.stop();
            this.rmiServer.reset();
        }
        catch(Exception e) {}

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
     * @return boolean
     */
    public boolean update()
    {
        if (this.first)
        {
            this.wait(1);
            this.first = false;
        }

        if (this.stopped)
        {
            return false;
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
        this.stopped = false;
    }


    /**
     * Zatrzymuje watek
     */
    public void pause()
    {
        clientThread.suspend();
        this.stopped = true;
    }


    /**
     * Wznawia watek
     */
    public void resume()
    {
        clientThread.resume();
        this.stopped = false;
    }

}