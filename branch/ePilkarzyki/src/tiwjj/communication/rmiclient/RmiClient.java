package tiwjj.communication.rmiclient;

import java.rmi.registry.*;
import tiwjj.communication.*;

public class RmiClient implements Runnable, IClient  {

    Thread clientThread;

    public void start()
    {
        clientThread = new Thread(this);
        clientThread.start();
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
    
    public boolean myMove()
    {
        try
        {
            return rmiServer.myMove(this.team);
        }
        catch(Exception e)
        {
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
        try
        {
            System.out.println(rmiServer.update(this.team));
        }
        catch(Exception e)
        {
            return false;
        }

        return true;
    }

    public void run()
    {
        while(true)
        {
            this.update();
            try
            {
                Thread.sleep(1000);
            }
            catch(Exception e)
            {
                
            }
        }
    }

}