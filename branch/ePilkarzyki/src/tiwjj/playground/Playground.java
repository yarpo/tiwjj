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
import tiwjj.communication.client.*;


public class Playground extends Canvas {

    /**
     * Obiekt pozwalajacy na polaczenie z serwerem
     */
    private SecureClient client;

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
    private Color bgColor = Colors.Normal;


    /**
     * srodek boiska w poziomie
     */
    private int xCenter = (int)(Size.PlaygroundWidth/2) +  Size.StartXGrass;


    /**
     * srodek boiska w pionie
     */
    private int yCenter = (int)(Size.PlaygroundHeight/2) + Size.StartYGrass;


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

    
    /** 
     * Konstruktor
     *
     * tworzy pierwszy ruch
     */
    public Playground(SecureClient client)
    {
        this.client = client;

        this.client.connection();

        if (this.client.isConnected())
        {
            System.out.println("Jestem polaczony");
        }
        else
        {
            System.out.println("Nie jestem polaczony");
        }

/*
        Exchanger e = new Exchanger();
        e.type = Exchanger.MessagesType.CHECK_MOVE;
        this.client.send(e);

        e = this.client.receive();
        if (e.type == Exchanger.MessagesType.BAD_MOVE)
        {
            System.out.println("BAD_MOVE");
        }

        e.type = Exchanger.MessagesType.CHECK_MOVE;
        this.client.send(e);

        e = this.client.receive();
        if (e.type == Exchanger.MessagesType.UPDATE_MOVE)
        {
            System.out.println("UPDATE_MOVE");
        }

        e.type = Exchanger.MessagesType.END;
        this.client.send(e);

        e = this.client.receive();
        if (e.type == Exchanger.MessagesType.LOSER)
        {
            System.out.println("LOSER");
        }
*/
       this.createFirstMove();
    }

    /**
     * Tworzy pierwszy (pusty) ruch
     * WYMAGANE przy starcie gry
     */
    private void createFirstMove()
    {
        Spot f = new Spot(this.xCenter, this.yCenter);
        this.moves = new Moves(new Move(f, f, 0));
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
        drawSpecialPoints();
        if (p.isAccessible(Spot.lastSpot))
        {
            drawHoveredPoint();
        }
    }


    /**
     * Wyrysowywanie podswietlonego punktow
     */
    private void drawHoveredPoint()
    {
        Spot s = Spot.normalize(Spot.hoveredSpot);
        g.setColor(Colors.HoveredPoint);
        if (Spot.lastSpot.isAccessible(s) && !s.theSameField(Spot.lastSpot))
        {
            g.fillRect(s.x-Size.OffsetX, s.y-Size.OffsetY, Size.PointX, Size.PointY);
        }
    }


    /**
     * Wyrysuj specjalne punkty - otaczajace aktualny punkt
     */
    private void drawSpecialPoints()
    {
        g.setColor(Colors.FocusedPoint);

        Vector v = Spot.lastSpot.getNeighbours();

        for(int i = v.size()- 1; i >= 0; i--)
        {
            Spot p = (Spot)v.elementAt(i);
            g.fillRect(p.getXx(), p.getYy(), Size.PointX, Size.PointY);
        }
    }

    /**
     * Wyrysowuje punkty niekatywne
     */
    private void drawPoints()
    {
        g.setColor(Colors.Points);

        for (int i = Playground.xStop; i >= Playground.xStart;  i -= Size.VerticalGap)
        {
            for (int j = Playground.yStop; j >=  Playground.yStart; j -= Size.HorizontalGap)
            {
                g.drawRect(i, j, Size.PointX, Size.PointY);
            }
        }
    }

    /**
     * Rysuje bramki
     */
    private void drawGoals()
    {
        g.setColor(Colors.Goals);
        int x_start = (int)(Size.PointsX/2 - 1)*Size.HorizontalGap+Size.StartXGrass;
        g.fillRect(x_start-Size.OffsetX, Size.StartYGrass - Size.GoalHeight, Size.GoalWidth, Size.GoalHeight);
        g.fillRect(x_start-Size.OffsetY, Size.PlaygroundHeight + Size.StartYGrass, Size.GoalWidth, Size.GoalHeight);
    }

    /**
     * Rysuje trawe i srodkowa linie
     */
    private void drawGrass()
    {
        g.clearRect(0, 0, Size.WIDTH, Size.HEIGHT);
        g.setColor(this.bgColor);
        g.fillRect(Size.StartXGrass, Size.StartYGrass, Size.PlaygroundWidth, Size.PlaygroundHeight);
        g.setColor(Colors.Lines);
        g.drawLine(Size.StartXGrass, this.yCenter, Size.PlaygroundWidth,
                                                            this.yCenter);
    }


    /**
     * Rysuje pojedynczy ruch
     *
     * @param Move m
     */
    private void drawMove( Move m)
    {
        g.setColor(Colors.Teams[m.getTeam()]);
        g.drawLine(m.getStart().x, m.getStart().y, m.getEnd().x, m.getEnd().y);
    }


    /**
     * Rysuje wszystkie ruchy
     */
    private void drawMoves()
    {
        Iterator move = this.moves.getIterator();
        while(move.hasNext())
        {
            drawMove((Move)move.next());
        }

        g.setColor(Colors.CurrentPoint);
        g.fillRect(Spot.lastSpot.getXx(), Spot.lastSpot.getYy(), Size.PointX, Size.PointY);
    }

    // TODO: usunac po wprowadzeniu serwera
    private int teamTurn = 0;

    /**
     * Dodaje nowy ruch jesli jest to mozliwe
     */
    public boolean addMove(Spot p)
    {
        System.out.println("addMove - no i co?");

        Exchanger e = this.client.waitForYourTurn();

        if (null == e)
        {
            System.out.println("Nie moja tura?!");
            return false;
        }
        else
        {
            System.out.println("Niby moja tura");
            if (Exchanger.MessagesType.UPDATE == e.type)
            {
                System.out.println("NAJPIERW UPADTE PLANSZY");
            }
        }

        p = Spot.normalize(p); 

        do
        {
            System.out.println("Probuje robic krok");

            e.type = Exchanger.MessagesType.CHECK_MOVE;
            this.client.send(e);
            e = this.client.receive();

            System.out.println("Z serwera dostalem : " + e.type + " druzyny" + e.team);
            
            if (this.moves.possible(p))
            {
                this.moves.add(new Move(Spot.lastSpot, p, this.client.getTeam()));
                update();
            }
            else
            {
                System.out.println("Nie mozesz!");
            }
            e = this.client.receive();
        }
        while(this.client.getTeam() != e.team);

        System.out.println("To juz niby zrobilem krok, teraz czas na drugiego klienta");

        

        return false;
    }

    /**
     * Wyrysowauje wszystko od nowa na canvasie
     */
    public void update()
    {
        if (null == this.g)
        {
            this.g = this.getGraphics();
        }

        drawGrass();
        drawGoals();
        drawPoints();
        drawMoves();
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
}
