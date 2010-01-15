package tiwjj.playground;

/**
 * Klasa Playground dziedziczaca po Canvas
 * Pozwala na wyswieltanie gry w aplecie
 * @author  Patryk yarpo Jar
 * @date    6 - 01 - 2009
 */
import java.awt.*;
import tiwjj.communication.*;
import java.util.Vector;



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
        this.client.myMove(this.moves.getMoves());
        this.client.start(this);
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
     * Dodaje nowy ruch - nie sprawdza, czy moze. Zaklada, ze juz mozna skoro
     * zostala wywolana
     *
     * @param Move m
     */
    private void addMove(Move m)
    {
        this.moves.add(m);
        this.client.myMove(this.moves.getMoves());
        if (!this.moves.isMyTurn(this.client.getMyTeam()))
        {
            this.client.nextTeam(); // kolej na nastepna druzyne
        }
        update(); // wyswietl owa sytuacje na boisku
        this.matchesState();
    }


    private void matchesState()
    {
        if (this.moves.winner(this.client.getMyTeam()))
        {
            System.out.println("Wygrales");
        }
       /* else if (this.moves.loser(this.client.getMyTeam()))
        {
            System.out.println("przegrales");
        }*/
    }

    /**
     * Dodaje nowy ruch jesli jest to mozliwe
     *
     * @param Spot newSpot
     */
    public void addMove(Spot p)
    {
        this.client.pause(); // zastopuj automatyczne pozyskiwanie danych

        if (this.client.isMyTurn())
        {
            p = Spot.normalize(p);

            if (this.moves.possible(p))
            {
                this.addMove(new Move(Spot.lastSpot, p, this.client.getMyTeam()));
            }
        }

        this.client.resume(); // wznow dzialanie watku
    }


    /**
     * Setter wektora ruchow
     *
     * @param Vector<Move> moves
     */
    public void setMoves(Vector<Move> moves)
    {
        this.moves.setMoves(moves);
    }


    /**
     * Getter wektora ruchow
     *
     * @returns Moves moves
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
