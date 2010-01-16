package tiwjj.communication.rmiserver;

import java.rmi.*;

import java.rmi.server.*;
import java.util.Vector;
import tiwjj.communication.*;
import tiwjj.playground.Move;
import java.rmi.registry.*;


public class RmiServer implements RMIInterface {

    /**
     * id druzyny, ktora aktualnie moze grac
     */
    private int currentTeam = 0;

    
    /**
     * tablica "zajetosci" druzyn
     */
    private boolean [] teams = {false, false};


    /**
     * Wektor ruchow wykonanych przez zawodnikow
     */
    private Vector<Move> moves;


    /**
     * Konstruktor
     */
    public RmiServer()
    {
        //System.out.println("serwer pracuje na porcie " + Settings.PORT);
/*
        try
        {

            Registry registry = LocateRegistry.createRegistry( Settings.PORT );
            registry.rebind("rmiServer", this);

        }
        catch(Exception e) {}*/
    }


    /**
     * Sprawdza, czy teraz jest kolej tej druzyny
     *
     * @param inr team
     *
     * @returns boolean
     */
    public int getCurrentTeam()
    {
        return this.currentTeam;
    }


    /**
     * 
     */
    public void nextTeam()
    {
        this.currentTeam = (this.currentTeam+1)%2;
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
     * @param int team
     *
     * @returns boolean
     */
    public boolean myMove(Vector<Move> moves)
    {
        this.moves = moves;

        return true;
    }


    /**
     * Konczy gre
     *
     * @returns boolean
     */
    public boolean reset()
    {
        this.moves = null;
        this.teams[0] = false;
        this.teams[1] = false;
        this.currentTeam = 0;

        return true;
    }

    /**
     * Wywolywana przez specjlany watek metoda zwraca aktualny wektor ruchow
     */
    public Vector<Move> update()
    {
        if (null == this.moves)
        {
            System.out.println("Nie ma ruchow");
            return null;
        }

        return this.moves;
    }
}