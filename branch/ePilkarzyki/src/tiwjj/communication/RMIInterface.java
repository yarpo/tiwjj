package tiwjj.communication;

import java.rmi.*;
import java.util.Vector;
import tiwjj.playground.Move;

/**
 * Interfejs RMIInterface implementowany przez serwer RMI oraz wykorzystywany
 * przez klienta RMI
 * @author  Patryk yarpo Jar
 * @date    6 - 01 - 2009
 */

public interface RMIInterface  extends Remote {

    /**
     * Logowanie
     *
     * @param user
     * @param pass
     *
     * @throws RemoteException
     *
     * @returns boolean
     */
    public boolean login(String user, String pass) throws RemoteException;


    /**
     * Dolaczanie do gry
     *
     * @throws RemoteException
     *
     * @returns int
     */
    public int joinGame() throws RemoteException;


    /**
     * Dolaczanie do konretnej druzyny
     *
     * @param team 
     *
     * @throws RemoteException
     *
     * @returns int
     */
    public int joinTeam(int team) throws RemoteException;


    /**
     * Zwraca identyfikator aktualnej druzyny, ktora ma ruch
     *
     * @throws RemoteException
     *
     * @returns int
     */
    public int getCurrentTeam() throws RemoteException;


    /**
     * Zmiana druzyny, ktora aktualnie wykonuje ruch
     *
     * @throws RemoteException
     */
    public void nextTeam() throws RemoteException;


    /**
     * Przeslanie najnowszego wektora ruchow
     *
     * @param data
     *
     * @throws RemoteException
     *
     * @return boolean
     */
    public boolean myMove(Vector<Move> data) throws RemoteException;


    /**
     * Restart ustawien serwera
     *
     * @throws RemoteException
     *
     * @returns boolean
     */
    public boolean reset() throws RemoteException;


    /**
     * Update wartosci wektorow moves
     *
     * @throws RemoteException
     *
     * @returns Vector<Move>
     */
    public Vector<Move> update() throws RemoteException;
}


