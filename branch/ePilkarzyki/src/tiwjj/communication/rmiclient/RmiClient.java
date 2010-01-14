package tiwjj.communication.rmiclient;

import java.rmi.registry.*;
import tiwjj.communication.*;
import java.util.Vector;
import tiwjj.playground.*;

public class RmiClient implements Runnable, IClient  {

    Thread clientThread;
    Playground playground;

    public void start(Playground p)
    {
        this.playground = p;
        this.clientThread = new Thread(this);
        this.clientThread.start();
    }

    public void pause()
    {
        clientThread.suspend();
    }

    public void resume()
    {
        clientThread.resume();
    }


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

    public RmiClient(String host, int port)
    {
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

    public RmiClient()
    {
        this(Settings.HOST, Settings.PORT);
    }

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

    public boolean isMyTurn()
    {
        try
        {
            return rmiServer.isMyTurn(this.team);
        }
        catch(Exception e)
        {
            return false;
        }
    }
    
    public boolean myMove(Vector<Move> moves)
    {
        try
        {
            Exchanger data = new Exchanger();
            data.team = this.team;
            data.moves = moves;
            System.out.println("Moves size: " + moves.size());
            return rmiServer.myMove(data);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.out.println("To nie dziala");
            return false;
        }
    }

    // TODO jakies cialo metody
    public boolean end()
    {
        // wyslac na serwer komunikat o tym, ze ten klient konczy gre
        return true;
    }

    public int getTeam()
    {
        return this.team;
    }

    public boolean update()
    {
        if (first)
        {
            try { Thread.sleep(1000); } catch(Exception e) {}
            first = false;
        }


System.out.println("Update start:");
        try
        {
           Exchanger data = rmiServer.update();
           if (null != data)
           {
System.out.println("1");
               playground.setMoves(data.moves);
System.out.println("2");
               playground.update();
           }
        }
        catch(Exception e)
        {
System.out.println("3");
            e.printStackTrace();
            System.out.println("Update sie nie udal");
        }

System.out.println("Update stop.");
        return true;
    }

    public void run()
    {
        while(true)
        {
            //Exchanger data = this.update();
            this.update();
            //this.playground.setMoves(data.moves);
            try
            {
                Thread.sleep(1000);
            }
            catch(Exception e)
            {
                System.out.println("Update sie nie powiodl");
            }
        }
    }

}