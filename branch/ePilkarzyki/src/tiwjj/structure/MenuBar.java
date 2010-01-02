/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tiwjj.structure;

/**
 *
 * @author yarpo
 */

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import javax.swing.JApplet;

public class MenuBar {

    private JMenuBar menuBar; // glowne menu
    private HashMap menus; //

    public MenuBar()
    {
        this.menuBar = new JMenuBar();
        this.menus = new HashMap();
    }

    /**
     * Dodaje nowa pozycje do menubara
     */
    public void AddMenu(String label)
    {
        this.menus.put(label, new JMenu());
    }

    /**
     *  Dodaje wszystkie pozycje z menu do menubara
     */
    private void createMenuBar()
    {
        Set ms = this.menus.keySet();
        Iterator it = ms.iterator();
        while (it.hasNext()) {
            this.menuBar.add((JMenu)it.next());
        }
    }

    /**
     * Wyswietla menuBar na aplecie
     */
    public void SetMenuBar(JApplet applet)
    {
        this.createMenuBar();
        applet.setJMenuBar(this.menuBar);
    }
    /*

     JMenuItem jMenuItem1 = new javax.swing.JMenuItem();
    JMenuItem jMenuItem2 = new javax.swing.JMenuItem();
    JMenuItem jMenuItem4 = new javax.swing.JMenuItem();
   
    JMenuItem jMenuItem3 = new javax.swing.JMenuItem();
    javax.swing.JMenuItem jMenuItem5 = new javax.swing.JMenuItem();

    jMenu1.setText("Gra");

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem1.setText("Nowa gra");
        jMenu1.add(jMenuItem1);

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_J, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem2.setText("Dołącz do gry");
        jMenu1.add(jMenuItem2);

        jMenuItem4.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem4.setText("Zakończ");
        jMenu1.add(jMenuItem4);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edycja");

        jMenuItem3.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem3.setText("Ustawienia");
        jMenu2.add(jMenuItem3);

        jMenuItem5.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Z, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem5.setText("Zasady");
        jMenu2.add(jMenuItem5);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);
     */

}
