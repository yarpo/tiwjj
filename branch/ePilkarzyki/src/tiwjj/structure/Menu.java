/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tiwjj.structure;

/**
 *
 * @author yarpo
 */

import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class Menu {

    private JMenu menu;

    public Menu(String label)
    {
        this.menu = new JMenu();
        this.menu.setText(label);
    }

    public void AddItem(JMenuItem item)
    {
        
    }
}
