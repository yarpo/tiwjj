/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tiwjj.communication;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



/**
 *
 * @author yarpo
 */
import java.rmi.*;

public interface RMIInterface  extends Remote {

    public enum MessagesType {
        CONNECT,        // C->S polaczenie z serwerem
        OK,             // S->C serwer mowi klientowi, ze go przyjal
        GO,             // S->C pozwala klientowi na ruch
        STOP,           // S->C serwer zabrania klientowi ruchu
        MOVE,           // C->S nowy ruch, sprawdzenie czy dobry
        BAD_MOVE,       // S->C bledny ruch - sprobuj ponownie
        UPDATE,         // S->C dobry ruch, update wektora ruchow
        WINNER,         // S->C wygrales!
        LOSER,          // S->C przegrales
        END             // C->S walkower
    }

    public int joinGame() throws RemoteException;
    public boolean isMyTurn(int team)  throws RemoteException;
    public boolean myMove(int team)  throws RemoteException;
}

