/*
 * ePilkarzyki - gra w pilkarzyki na kartce poprzez lacze internetowe
 *
 * Main.java - stad staruje projekt
 *
 */

package tiwjj;

/**
 *
 * @author yarpo
 */
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.JPanel;
import tiwjj.playground.*;

public class Main extends JApplet {

    private static final int DEFAULT_WIDTH = 270;
    private static final int DEFAULT_HEIGHT = 350;

    private void createMenuBar()
    {
        JMenuBar jMenuBar = new JMenuBar();
        JMenu jMenu1 = new JMenu();
        JMenu jMenu2 = new JMenu();
        JMenuItem jMenu1Item1 = new JMenuItem();
        JMenuItem jMenu1Item2 = new JMenuItem();
        JMenuItem jMenu1Item4 = new JMenuItem();
        JMenuItem jMenu2Item3 = new JMenuItem();
        JMenuItem jMenu2Item5 = new JMenuItem();
        jMenu1.setText("Gra");

        jMenu1Item1.setText("Nowa gra");
        jMenu1Item1.addActionListener(new tiwjj.actionListener.NewGame());
        jMenu1.add(jMenu1Item1);
      
        jMenu1Item2.setText("Dołącz do gry");
        jMenu1Item2.addActionListener(new tiwjj.actionListener.JoinGame());
        jMenu1.add(jMenu1Item2);
     
        jMenu1Item4.setText("Zakończ");
        jMenu1Item4.addActionListener(new tiwjj.actionListener.EndGame());
        jMenu1.add(jMenu1Item4);
        jMenuBar.add(jMenu1);
        jMenu2.setText("Edycja");
       
        jMenu2Item3.setText("Ustawienia");
        jMenu2Item3.addActionListener(new tiwjj.actionListener.Settings());
        jMenu2.add(jMenu2Item3);
        
        jMenu2Item5.setText("Zasady");
        jMenu2Item5.addActionListener(new tiwjj.actionListener.Rules());
        jMenu2.add(jMenu2Item5);
        jMenuBar.add(jMenu2);
        setJMenuBar(jMenuBar);
    }

    private void createCanvas()
    {
        JPanel jPanel1 = new JPanel();

        Playground canvas1 = new Playground();
        canvas1.setSize(Main.DEFAULT_WIDTH, Main.DEFAULT_HEIGHT);
        canvas1.addMouseListener(new PlaygroundMouseAdapter(canvas1));
        canvas1.addMouseMotionListener(new PlaygroundMouseAdapter(canvas1));

        GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(canvas1, GroupLayout.PREFERRED_SIZE, Playground.Size.WIDTH, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE)
            )
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(50, Short.MAX_VALUE)
                .addComponent(canvas1, GroupLayout.PREFERRED_SIZE, Playground.Size.HEIGHT, GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
            )
        );

        add(jPanel1);
    }

    @Override
    public void init()
    {
        createMenuBar();
        setSize(Main.DEFAULT_WIDTH, Main.DEFAULT_HEIGHT);
        createCanvas();
    }
    /*
    public static void main(String[] args) {
        run(new Main(), 300, 400);
    }
*/
    private static void createFrame(JApplet applet, int w, int h)
    {
        JFrame frame = new JFrame("e-Piłkarzyki");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(applet);
        frame.setSize(w, h);
        applet.init();
        applet.start();
        frame.setVisible(true);
    }

    public static void run(JApplet applet, int width, int height)
    {
        createFrame(applet, width, height);
    }

    public static void run(JApplet applet)
    {
        createFrame(applet, Main.DEFAULT_WIDTH, Main.DEFAULT_HEIGHT);
    }
} ///:~

