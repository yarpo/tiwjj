package tiwjj;

/**
 * Klasa Main dziedziczaca po JApplet
 * Pozwala na stworzenie apletu i umieszczenie w nim gry
 * @author  Patryk yarpo Jar
 * @date    6 - 01 - 2009
 */
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.ButtonGroup;
import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.JPanel;
import tiwjj.playground.*;
import tiwjj.communication.*;
import tiwjj.communication.rmiclient.RmiClient;

/**
 *
 * @author yarpo
 */
public class Main extends JApplet {

    /**
     * Domyslna szerokosc okna
     */
    private static final int DEFAULT_WIDTH = 270;


    /**
     * Domyslna wysokosc okna
     */
    private static final int DEFAULT_HEIGHT = 350;

    
    /**
     * Obiekt sluzacy do wymiany danych z serwerem
     */
    private static IClient client = new RmiClient("localhost", 3232);

    public static int WHITE_TEAM = 0;
    public static int BLACK_TEAM = 1;

    /**
     * Tworzy menu
     */
    public JMenuBar createMenuBar() {
        JMenuBar menuBar;
        JMenu menu, submenu;
        JMenuItem menuItem;
        JRadioButtonMenuItem rbMenuItem;

        //Create the menu bar.
        menuBar = new JMenuBar();

        //Build the first menu.
        menu = new JMenu("Gra");
        menuBar.add(menu);

        //a submenu
        submenu = new JMenu("Dołącz do gry");

        ButtonGroup group = new ButtonGroup();

        rbMenuItem = new JRadioButtonMenuItem("Biali");
        rbMenuItem.addActionListener(new tiwjj.actions.JoinGame(client,
                                                             WHITE_TEAM));
        group.add(rbMenuItem);
        submenu.add(rbMenuItem);

        rbMenuItem = new JRadioButtonMenuItem("Czarni");
        rbMenuItem.addActionListener(new tiwjj.actions.JoinGame(client,
                                                              BLACK_TEAM));
        group.add(rbMenuItem);
        submenu.add(rbMenuItem);
        menu.add(submenu);
        menu.addSeparator();
        menuItem = new JMenuItem("Zakończ grę");
        menuItem.addActionListener(new tiwjj.actions.EndGame(client,
                                                              BLACK_TEAM));
        menu.add(menuItem);

        return menuBar;
    }

    /**
     * Wstawienie canvasu do aplettu
     */
    private void createCanvas()
    {
        JPanel jPanel1 = new JPanel();

        Playground canvas1 = new Playground(client);
        canvas1.setSize(Main.DEFAULT_WIDTH, Main.DEFAULT_HEIGHT);
        canvas1.addMouseListener(new PlaygroundMouseAdapter(canvas1));
        canvas1.addMouseMotionListener(new PlaygroundMouseAdapter(canvas1));

        GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(canvas1, GroupLayout.PREFERRED_SIZE, Size.WIDTH,
                                                    GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE)
            )
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, jPanel1Layout.
                                                        createSequentialGroup()
                .addContainerGap(50, Short.MAX_VALUE)
                .addComponent(canvas1, GroupLayout.PREFERRED_SIZE, Size.HEIGHT,
                                                    GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
            )
        );

        add(jPanel1);
    }


    /**
     * Rozpoczecie dzialania aplletu
     */
    @Override
    public void init()
    {
        setJMenuBar(createMenuBar());
        setSize(Main.DEFAULT_WIDTH, Main.DEFAULT_HEIGHT);
        createCanvas();
    }


    /**
     * 
     */
    public static void main(String[] args)
    {
        run(new Main(), Main.DEFAULT_WIDTH, Main.DEFAULT_HEIGHT);
    }


    /**
     * Stworzenie ramki z appletem
     *
     * @param JApplet applet
     * @param int width
     * @param int height
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


    /**
     * Uruchomienie aplletu z zadanymi rozmiarami
     *
     * @param applet
     * @param width
     * @param height
     */
    public static void run(JApplet applet, int width, int height)
    {
        createFrame(applet, width, height);
    }

    
    /**
     * Uruchomiwnie apletu z domyslnymi rozmiarami
     * 
     * @param applet
     */
    public static void run(JApplet applet)
    {
        run(applet, Main.DEFAULT_WIDTH, Main.DEFAULT_HEIGHT);
    }
} ///:~

