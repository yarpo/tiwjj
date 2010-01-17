package tiwjj.actions;

import tiwjj.communication.IClient;
import java.awt.event.*;

/**
 * Abstrakcyjna klasa ActionHandler
 * Klasy dziedziczace po tej klasie obsluguja zdarzenia zwiazane z zarzadzaniem
 * gra: koniec poczate, dolacz itp.
 * @author  Patryk yarpo Jar
 * @date    6 - 01 - 2009
 */

public class EndGame extends ActionHandler  {

    /**
     * Konstruktor
     *
     * @param client
     */
    public EndGame(IClient client)
    {
        super(client, client.getMyTeam());
    }


    /**
     * Obsluga zdarzenia - wybranie w menu "zakoncz gre"
     *
     * @param ActionEvent e
     */
    public void actionPerformed(ActionEvent e)
    {
        javax.swing.JOptionPane.showMessageDialog(null, "Koniec gry",
              "Komunikat", javax.swing.JOptionPane.ERROR_MESSAGE);
        this.client.end();
    }
}

