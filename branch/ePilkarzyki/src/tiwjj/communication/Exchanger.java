/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tiwjj.communication;

public class Exchanger implements java.io.Serializable {
    public int a;
    public int b;
    public String s;
}


/**
 *
 * @author yarpo

 *
 *
public class Exchanger implements java.io.Serializable {

    public enum MessagesType {
        CONNECT,        // C->S polaczenie z serwerem
        OK,             // S->C serwer mowi klientowi, ze go przyjal
        GO,             // S->C pozwala klientowi na ruch
        CHECK_MOVE,     // C->S nowy ruch, sprawdzenie czy dobry
        BAD_MOVE,       // S->C bledny ruch - sprobuj ponownie
        UPDATE,         // S->C dobry ruch, update wektora ruchow, ale nadal gra przeciwnik
        WINNER,         // S->C wygrales!
        LOSER,          // S->C przegrales
        END             // C->S walkower
    }

    public MessagesType type;
    public String content;
    public int team;
}
*/