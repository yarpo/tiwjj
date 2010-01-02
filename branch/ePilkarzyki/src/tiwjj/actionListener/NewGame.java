/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tiwjj.actionListener;

/**
 *
 * @author yarpo
 */

import java.awt.event.*;

public class NewGame implements ActionListener  {
    public void actionPerformed(ActionEvent e)
    {
        javax.swing.JOptionPane.showMessageDialog(null, "alert " + e.getActionCommand() +"\n",
              "Komunikat", javax.swing.JOptionPane.ERROR_MESSAGE);
    }
}
