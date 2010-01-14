package tiwjj.communication.rmiserver;

import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.server.UnicastRemoteObject;
import tiwjj.communication.*;
import tiwjj.playground.Move;
import java.util.Vector;

public class RmiServer extends UnicastRemoteObject implements RMIInterface {

    /**
     * Port na ktorym slucha serwer
     */
    private int port = Settings.PORT;

    /**
     * TODO: Przerobic na zabezpieczone polaczenie
     */
    Registry registry;

    /**
     * id druzyny, ktora aktualnie moze grac
     */
    int currentTeam = 0;

    /**
     * tablica "zajetosci" druzyn
     */
    boolean [] teams = {false, false};


    private Vector<Move> moves;
    /**
     * Sprawdza, czy teraz jest kolej tej druzyny
     *
     * @param inr team
     *
     * @returns boolean
     */
    public boolean isMyTurn(int team)
    {
System.out.println("Druzyna " + team + "pyta sie czy to jego tura. Teraz tura " + this.currentTeam);
        if (team == this.currentTeam)
        {
            return true;
        }

        return false;
    }


    /**
     * Dolacza do gry. Zwraca numer druzyny do ktorej zostal gracz przypisany,
     * albo -1 jesli nie udalo mu sie rzpoczac gry
     *
     * @returns int 
     */
    public int joinGame()
    {
        for(int i = 0; i < this.teams.length; i++)
        {
            if (-1 != this.joinTeam(i))
            {
                return i;
            }
        }

        return -1;
    }

    /**
     * Probuje dolaczyc gracza do gry do konkretej druzyny
     *
     * @param index - numer druzyny, do ktoprej chcemy dolaczyc
     *
     * @returns int - numer druzyny, do ktorej nas dolaczylo alno -1 jesli sie nie udalo
     */
    public int joinTeam(int index)
    {
        if (false == this.teams[index])
        {
            this.teams[index] = true;
            return index;
        }
        return -1;
    }


    /**
     * Dodaje nowe ruch wykonany przez zawodnika
     * Czy dany ruch jest dozwolony sprawdza klient tu jedynie zapisanie
     * i pozniejsze rozsylanie
     *
     * TODO : zrobic, aby dzialalo jak w powyzszym opisie
     *
     * @param int team
     *
     * @returns boolean
     */
    public boolean myMove(Exchanger data)
    {
System.out.println("Ruch druzyny " + data.team);
        this.moves = data.moves;
System.out.println("Byl ruch druzyny " + this.currentTeam);
        this.currentTeam = (this.currentTeam+1)%2;
System.out.println("teraz ruch druzyny " + this.currentTeam);
        System.out.println("Teraz druzyna: " + this.currentTeam);

        return true;
    }


    /**
     * Konczy gre
     *
     * @returns boolean
     */
    public boolean end()
    {
        return true; // TODO
    }

    public Exchanger update()
    {
        //System.out.println("zespol: " + data.team + "\t" + data.moves);
        //this.moves = data.moves;
        if (null == this.moves)
        {
            System.out.println("Nie ma ruchow");
            return null;
        }

        Exchanger data = new Exchanger();
        data.moves = this.moves;

        return data;
    }

    /**
     * Konstruktor
     */
    public RmiServer() throws RemoteException
    {
        System.out.println("serwer pracuje na porcie " + this.port);

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