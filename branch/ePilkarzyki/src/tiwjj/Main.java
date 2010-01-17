package tiwjj;

/**
 * Klasa Main dziedziczaca po JApplet
 * Pozwala na stworzenie apletu i umieszczenie w nim gry
 * @author  Patryk yarpo Jar
 * @date    6 - 01 - 2009
 */
import javax.swing.*;
import tiwjj.playground.*;
import tiwjj.communication.*;
import tiwjj.communication.rmiclient.RmiClient;

/**
 * Statyczna klasa Main
 * Pozwala rozpoczac dzialanie aplikacji - zarowno jako apletu
 * jak i jako standalone
 * @author  Patryk yarpo Jar
 * @date    15 - 01 - 2009
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
    private static IClient client = new RmiClient();


    /**
     * Tworzy menu
     *
     * @returns JMenuBar
     */
    public JMenuBar createMenuBar() {
        JMenuBar menuBar;
        JMenu menu;
        JMenuItem menuItem;

        menuBar = new JMenuBar();

        menu = new JMenu("Gra");
        menuBar.add(menu);

        menuItem = new JMenuItem("Zakończ grę");
        menuItem.addActionListener(new tiwjj.actions.EndGame(client));
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
        boolean loged = false;
        while(!loged)
        {
            String login = JOptionPane.showInputDialog("Podaj login:");
            String pass = JOptionPane.showInputDialog("Podaj hasło:");
            loged = client.login(login, pass);
        }
        createCanvas();
    }


    /**
     * Metoda rozpoczynajaca prgram odpalany jako standalone
     *
     * @param String [] args nie uzywany
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

