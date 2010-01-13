/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tiwjj.actions;

import tiwjj.communication.IClient;
/**
 *
 * @author yarpo
 */
import java.awt.event.*;

public class EndGame extends ActionHandler implements ActionListener  {

    public EndGame(IClient client, int team)
    {
        super(client, team);
    }

    public void actionPerformed(ActionEvent e)
    {
        javax.swing.JOptionPane.showMessageDialog(null, "Koniec gry",
              "Komunikat", javax.swing.JOptionPane.ERROR_MESSAGE);
        this.client.end();
    }
}

