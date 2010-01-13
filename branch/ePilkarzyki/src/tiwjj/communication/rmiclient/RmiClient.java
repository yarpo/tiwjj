package tiwjj.communication.rmiclient;

import java.rmi.registry.*;
import tiwjj.communication.*;

public class RmiClient implements IClient
{
    private RMIInterface    rmiServer;
    private Registry        registry;
    private int             team;

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

}