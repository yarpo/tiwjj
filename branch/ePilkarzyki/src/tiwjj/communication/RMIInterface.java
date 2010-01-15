/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tiwjj.communication;

/**
 *
 * @author yarpo
 */
import java.rmi.*;
import java.util.Vector;
import tiwjj.playground.Move;

public interface RMIInterface  extends Remote {
    public int joinGame() throws RemoteException;
    public int joinTeam(int team) throws RemoteException;
    public int getCurrentTeam() throws RemoteException;
    public void nextTeam() throws RemoteException;
    public boolean myMove(Vector<Move> data) throws RemoteException;
    public boolean end() throws RemoteException;
    public Vector<Move> update() throws RemoteException;
}


