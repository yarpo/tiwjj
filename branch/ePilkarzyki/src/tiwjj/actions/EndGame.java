/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tiwjj.actions;

import tiwjj.communication.client.SecureClient;
/**
 *
 * @author yarpo
 */
import java.awt.event.*;

public class EndGame implements ActionListener  {

    private int team;
    private SecureClient client;

    public EndGame(SecureClient client, int team)
    {
        this.team = team;
        this.client = client;
    }

    public void actionPerformed(ActionEvent e)
    {
        javax.swing.JOptionPane.showMessageDialog(null, "Koniec gry",
              "Komunikat", javax.swing.JOptionPane.ERROR_MESSAGE);
        this.client.disconnect();
    }
}

