package tiwjj.playground;

/**
 * Klasa Moves
 * Zbiornik na kolejne ruchy graczy
 * @author  Patryk yarpo Jar
 * @date    6 - 01 - 2009
 */
import java.util.Vector;
import java.util.Iterator;

// TODO: zastanowic sie nad wprowadzeniem dodatkowo macierzy punktow

public class Moves {

    /**
     * wektor ruchow
     */
    private Vector<Move> moves;


    /**
     * Konstruktor Moves 1
     * Tworzy pierwszy ruch
     *
     * @param Move m
     */
    public Moves(Move m)
    {
        this.moves = new Vector<Move>();
        this.moves.add(m);
    }

    
    /**
     * Konstruktor Moves 2
     * Tworzy pierwszy ruch - punkt o wspolrzednych x, y
     *
     * @param int x_center
     * @param int y_center
     */
    public Moves(int x, int y)
    {
        this(new Spot(x,y), new Spot(x,y), 0);
    }


    /**
     * Konstruktor Moves 3
     * Tworzy pierwszy ruch
     *
     * @param Spot s
     * @param Spot e
     * @param int team
     */
    public Moves(Spot s, Spot e, int team)
    {
        this(new Move(s, e, team));
    }


    /**
     * Dodaje nowy ruch
     *
     * @param Move m
     */
    public void add(Move m)
    {
        this.moves.add(m);
        Spot.lastSpot = m.getEnd();
    }


    /**
     * Zwraca iterator
     * Dzieki temu mozna przechodzic po wszystkich ruchach
     *
     * @return Iterator
     */
    public Iterator getIterator()
    {
        return this.moves.iterator();
    }


    /**
     * Zwraca liczbe ruchow
     *
     * @return int
     */
    public int length()
    {
        return this.moves.size();
    }


    /**
     * Sprawdza, czy mozna przejsc na wskazane pole
     *
     * @param Spot e
     *
     * @return boolean
     */
    public boolean possible(Spot e)
    {
        // ruch jest niemozliwy, gdy:
        if (!Playground.isSpotable(e) // nowego punktu nie ma na boisku
                ||
            Spot.lastSpot.theSameField(e) // to ten sam punkit, co aktualny
                ||
            !Spot.lastSpot.isAccessible(e) // z aktualnego nie mozna tam pojsc
                ||
            !this.isEmpty(e)) // juz istnieje polaczenie
        {
            return false; // niemozliwy ruch
        }

        return true; // w kazym innym wypadku mozliwy
    }

    
    /**
     * Sprawdza czy ten ruch nie byl juz wykonany
     *
     * @param Spot e
     *
     * returns boolean
     */
    private boolean isEmpty(Spot e)
    {
        Iterator move = this.getIterator();

        while(move.hasNext())
        {
            Move m = (Move)move.next();
            if (Move.theSameMove(m, new Move(Spot.lastSpot, e, 0)))
            {
                return false;
            }
        }

        return true;
    }


    /**
     * Metoda do debuga - TODO: usunac, gdy niepotrzebna
     */
    private void debug(String a)
    {
        System.out.println(a);
    }


    /**
     * Sprawdza czy dany punkt jest juz uzywany
     *
     * @param Spot p
     *
     * @return boolean
     */
    public boolean isUsed(Spot p)
    {
        int n = this.moves.size() - 1;

        if (0 == n)
        {
            return true;
        }

        // nie bierze pierwszego ani ostatniego ruchu
        // pierwszy jest mockowy - srodek do srodek
        // ostatni to on sam
        for(int i = 1; i < n; i++)
        {
            Move m = this.moves.elementAt(i);
            if (m.getStart().theSameField(p) || m.getEnd().theSameField(p))
            {
                return true;
            }
        }
        return false;
    }
    

    /**
     * Sprawdza czy to jest tura druzyny o podanym identyfikatorze
     *
     * @param int team
     *
     * @return boolean
     */
    public boolean isMyTurn(int team)
    {
        // kolejny ruch pod rzad
        if (this.isUsed(this.moves.lastElement().getEnd()) ||
            this.moves.lastElement().getEnd().isBorder())
        {
            if (team == this.moves.lastElement().getTeam())
            {
                return true;
            }
        }
        else
        {
            // koniec tury przeciwnika
            if (team != this.moves.lastElement().getTeam())
            {
                return true;
            }
        }

        return false;
    }


    /**
     * Getter wektora ruchow
     *
     * @return Vector<Move> moves
     */
    public Vector<Move> getMoves()
    {
        return this.moves;
    }


    /**
     * Setter wektora ruchow
     *
     * @param Vector<Move> moves
     */
    public void setMoves(Vector<Move> moves)
    {
        this.moves = moves;
        Spot.lastSpot = this.moves.lastElement().getEnd();
    }


    /**
     * Pobiera ostatni punkt ze sciezki wszystkich ruchow
     *
     * @return Spot
     */
    public Spot getLastSpot()
    {
        return this.moves.lastElement().getEnd();
    }


    /**
     * Sprawdza czy padla bramka. jesli tak to zwraca identyfikator zwycieskiej
     * druzyny, albo -1 jesli bramki nie ma
     *
     * @return int
     */
    public int goal()
    {
        // czy w plaszczyznie x pasuje na bramke
        if ((Spot.lastSpot.getXx() >= Size.GoalXStart &&
            Spot.lastSpot.x <= Size.GoalXStart + Size.GoalWidth))
        {
            if (Spot.lastSpot.y == Size.GoalYTop + Size.GoalHeight)
            {
                return 1; // bramka gorna
            }

            if (Spot.lastSpot.y == Size.GoalYBottom)
            {
                return 0; // bramka dolna
            }
        }

       return -1;
    }


    /**
     * Sprawdza czy podana druzyna wygrala
     *
     * @param int team
     *
     * @return boolean
     */
    public boolean winner(int team)
    {
        return (this.goal() == team);
    }


    /**
     * Sprawdza czy podana druzyna przegrala
     *
     * @param int team
     *
     * @rteturns boolean
     */
    public boolean loser(int team)
    {
        int score = this.goal();
        return (score > -1 && score != team);
    }
}
