package tiwjj.communication.rmiserver;

import java.rmi.*;
import java.rmi.registry.*;
import java.net.*;
import tiwjj.communication.RMIInterface;

public class RmiServer extends java.rmi.server.UnicastRemoteObject
                                            implements RMIInterface
{
    int     port = 3232;
    String  address;
    Registry registry;    // rmi registry for lookup the remote objects.
    Integer n = 0;
    int currentTeam = 0;
    boolean [] teams = {false, false};

    public boolean isMyTurn(int team)
    {
        if (team == this.currentTeam)
        {
            return true;
        }

        return false;
    }

    public int joinGame()
    {
        for(int i = 0; i < this.teams.length; i++)
        {
            if (false == this.teams[i])
            {
                this.teams[i] = true;
                return i;
            }
        }

        return -1;
    }

    public boolean myMove(int team)
    {
        java.util.Random r = new java.util.Random();
        int a = r.nextInt(10);

        if (a%2 == 1)
        {
            return true;
        }

        this.currentTeam = (this.currentTeam+1)%2;

        System.out.println("Teraz druzyna: " + this.currentTeam);

        return false;
    }

    public RmiServer() throws RemoteException
    {
        try
        {
            this.address= (InetAddress.getLocalHost()).toString();
        }
        catch(Exception e)
        {
            throw new RemoteException("can't get inet address.");
        }

        System.out.println("serwer pracuje na " + this.address + ":" + this.port);

        try
        {
            registry = LocateRegistry.createRegistry( this.port );
            registry.rebind("rmiServer", this);
        }
        catch(RemoteException e)
        {
            throw e;
        }
    }
}