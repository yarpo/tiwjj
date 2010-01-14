package tiwjj.playground;

/**
 * Klasa Playground dziedziczaca po Canvas
 * Pozwala na wyswieltanie gry w aplecie
 * @author  Patryk yarpo Jar
 * @date    6 - 01 - 2009
 */
import java.awt.*;
//import java.awt.Graphics;
import tiwjj.communication.*;
import java.lang.Thread;



public class Playground extends Canvas {

    /**
     * Obiekt pozwalajacy na polaczenie z serwerem
     */
    private IClient client;


    /**
     * Obiekt pozwalajacy na obsluge ruchow graczy
     */
    private Moves moves;

    /**
     * Obiekt odpowiedzialny za wyrysowanie calego boiska na ekranie
     */
    private DrawPlayground view;

    
    /** 
     * Konstruktor
     *
     * tworzy pierwszy ruch
     */
    public Playground(IClient client)
    {
        this.client = client;
        this.moves = new Moves(Size.xCenter, Size.yCenter);
        this.view = new DrawPlayground(this);
        System.out.println("Druzyna numer " + this.client.joinGame());

        this.client.start();
    }


    /** 
     * Obsluga zdarzenia wjechania mysza nad obszar canvasu
     * Zmienia kolor tla + wyrysowuje wszystko od nowa
     */
    public void mouseOver()
    {
        this.view.setBgColor(Colors.Hover);
        this.update();
    }


    /**
     * Obsluga zdarzenia wyjechania mysza znad obszaru canvasu
     * Zmienia kolor tla + wyrysowuje wszystko od nowa
     */
    public void mouseOut()
    {
        this.view.setBgColor(Colors.Normal);
        Spot.hoveredSpot = null;
        this.update();
    }


    /**
     * Obsluga sytuacji, w ktorej kursor jest nad powierzchnia boiska
     * Moze sie poruszac.
     *
     * Wyrysowuje podswietlone punkty (aktualny, mozliwe do osiganiecia z aktualnego)
     * oraz - w odpowiednich sytucjach - podswietla znormalizowany punkt do przejscia
     *
     * @param Spot p
     */
    public void hover(Spot p)
    {
        Spot.hoveredSpot = p;
        this.view.drawSpecialPoints();
        if (p.isAccessible(Spot.lastSpot))
        {
            this.view.drawHoveredPoint();
        }
    }


    /**
     * Dodaje nowy ruch jesli jest to mozliwe
     *
     * @param Spot newSpot
     */
    public boolean addMove(Spot p)
    {
        this.client.pause(); // zastopuj automatyczne pozyskiwanie danych

        if (!this.client.isMyTurn())
        {
            System.out.println("To nie twoja kolej!");
            this.client.resume(); // wznow dzialanie watkus
            return false;
        }

        p = Spot.normalize(p);

        if (this.moves.possible(p))
        {
            this.client.myMove();
            this.moves.add(new Move(Spot.lastSpot, p, this.client.getTeam()));
            update();
        }

        this.client.resume(); // wznow dzialanie watku

        return false;
    }

    
    /**
     * Getter wektora ruchow
     */
    public Moves getMoves()
    {
        return this.moves;
    }

    /**
     * Wyrysowauje wszystko od nowa na canvasie
     */
    public void update()
    {
        this.view.refresh();
    }

   /**
    * Rysowanie na canvasie
    */
    @Override
    public void paint(Graphics g)
    {
        update();
    }

    /**
     * Nadpisana metoda wyrysowujaca na nowo na canvasie.
     * U mnie przekierowuje na mojego update
     */
    @Override
    public void update(Graphics g)
    {
        update();

    }


    /**
     * Sprawdza, czy dany punkt jest osiagalny - czy moze byc tam punkt
     *
     * @param int x
     * @param int y
     *
     * @returns boolean
     */
    public static boolean isSpotable(int x, int y)
    {
        if (y >= Size.yStart &&
            y <= Size.yStop + Size.OffsetY &&
            x <= Size.xStop + Size.OffsetX &&
            x >= Size.xStart)
        {
            return true;
        }

        return false;
    }

    /**
     * Sprawdza, czy dany punkt jest osiagalny - czy moze byc tam punkt
     *
     * @param Point p
     * 
     * @returns boolean
     */
    public static boolean isSpotable(Point p)
    {
        return isSpotable(p.x, p.y);
    }


    
}
