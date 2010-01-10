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

public class JoinGame implements ActionListener  {

    private int team;
    private SecureClient client;

    public JoinGame(SecureClient client, int team)
    {
        this.team = team;
        this.client = client;
    }

    public void actionPerformed(ActionEvent e)
    {
        javax.swing.JOptionPane.showMessageDialog(null, "alert " + e.getActionCommand() +"\n",
              "Komunikat", javax.swing.JOptionPane.ERROR_MESSAGE);
    }
}
