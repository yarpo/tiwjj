package tiwjj.actions;

import tiwjj.communication.IClient;
/**
 *
 * @author yarpo
 */
import java.awt.event.*;

public class JoinGame extends ActionHandler implements ActionListener  {

    public JoinGame(IClient client, int team)
    {
        super(client, team);
    }

    public void actionPerformed(ActionEvent e)
    {
        if (-1 != this.client.joinTeam(this.team))
        {
            javax.swing.JOptionPane.showMessageDialog(null, "alert " +
                                                e.getActionCommand() +"\n",
                                                "Komunikat",
                                                javax.swing.JOptionPane.
                                                                ERROR_MESSAGE);
        }
        else
        {
            
        }
    }
}
