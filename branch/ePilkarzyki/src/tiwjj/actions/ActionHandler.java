package tiwjj.actions;

import tiwjj.communication.*;
import java.awt.event.*;

/**
 * Abstrakcyjna klasa ActionHandler
 * Klasy dziedziczace po tej klasie obsluguja zdarzenia zwiazane z zarzadzaniem
 * gra: koniec poczate, dolacz itp.
 * @author  Patryk yarpo Jar
 * @date    6 - 01 - 2009
 */

public abstract class ActionHandler  implements ActionListener {

    /**
     * Identyfikator druzyny
     */
    protected int       team;

    
    /**
     * Obiekt klienta odpoiwedzialny za komunikacje z serwerem
     */
    protected IClient   client;


    /**
     * Konstruktor
     *
     * @param IClient client
     * @param int team
     */
    public ActionHandler(IClient client, int team)
    {
        this.team = team;
        this.client = client;
    }

    abstract public void actionPerformed(ActionEvent e);

}
