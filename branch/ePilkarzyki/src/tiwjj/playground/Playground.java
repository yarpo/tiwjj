package tiwjj.playground;

/**
 * Klasa Playground dziedziczaca po Canvas
 * Pozwala na wyswieltanie gry w aplecie
 * @author  Patryk yarpo Jar
 * @date    6 - 01 - 2009
 */
import java.awt.*;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Iterator;
import java.util.Vector;
import tiwjj.communication.*;


public class Playground extends Canvas {

    /**
     * Obiekt pozwalajacy na polaczenie z serwerem
     */
    private IClient client;

    /**
     * Obiekt pozwalajacy wyrysowywac wyniki na ekran
     */
    private Graphics g;


    /**
     * Obiekt pozwalajacy na obsluge ruchow graczy
     */
    private Moves moves;


    /**
     * kolor tla
     */
    public Color bgColor = Colors.Normal;


    /**
     * srodek boiska w poziomie
     */
    public int xCenter = (int)(Size.PlaygroundWidth/2) +  Size.StartXGrass;


    /**
     * srodek boiska w pionie
     */
    public int yCenter = (int)(Size.PlaygroundHeight/2) + Size.StartYGrass;


    /** 
     * poczatek na osi x wyrysowywania punktow.
     * _Nie_ musi pokrywac sie z poczatekiem boiska
     */
    public final static int xStart = Size.StartXGrass - Size.OffsetX;


    /**
     * poczatek na osi y wyrysowywania punktow.
     * _Nie_ musi pokrywac sie z poczatekiem boiska
     */
    public final static int yStart = Size.StartYGrass - Size.OffsetY;


    /**
     * graniczy punkt dla wyrysowywania punktow na boisku w osi x
     */
    public final static int xStop  = Size.PlaygroundWidth + Playground.xStart;


    /**
     * graniczy punkt dla wyrysowywania punktow na boisku w osi y
     */
    public final static int yStop  = Size.PlaygroundHeight + Playground.yStart;

    private DrawPlayground view;

    
    /** 
     * Konstruktor
     *
     * tworzy pierwszy ruch
     */
    public Playground(IClient client)
    {
        this.client = client;
        this.moves = new Moves(this.xCenter, this.yCenter);
        this.view = new DrawPlayground(this);
        System.out.println("Druzyna numer " + this.client.joinGame());
    }


    /** 
     * Obsluga zdarzenia wjechania mysza nad obszar canvasu
     * Zmienia kolor tla + wyrysowuje wszystko od nowa
     */
    public void mouseOver()
    {
        this.bgColor = Colors.Hover;
        this.update();
    }


    /**
     * Obsluga zdarzenia wyjechania mysza znad obszaru canvasu
     * Zmienia kolor tla + wyrysowuje wszystko od nowa
     */
    public void mouseOut()
    {
        this.bgColor = Colors.Normal;
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
     */
    public boolean addMove(Spot p)
    {
        if (!this.client.isMyTurn())
        {
            System.out.println("To nie twoja kolej!");
            return false;
        }

        p = Spot.normalize(p);

        if (this.moves.possible(p))
        {
            this.client.myMove();
            this.moves.add(new Move(Spot.lastSpot, p, this.client.getTeam()));
            update();
        }

        return false;
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
        if (y >= Playground.yStart &&
            y <= Playground.yStop + Size.OffsetY &&
            x <= Playground.xStop + Size.OffsetX &&
            x >= Playground.xStart)
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


    public Moves getMoves()
    {
        return this.moves;
    }
}
