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
     *
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
     *
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

// TODO: czy iterator jest szybszy od petli?
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
     * @returns int
     */
    public int length()
    {
        return this.moves.size();
    }

// TODO: uzyc Playground.isSpotable
    /**
     * Sprawdza, czy mozna przejsc na wskazane pole
     */
    public boolean possible(Spot e)
    {
        // nie jest na polu
        if ((e.x > Size.PlaygroundWidth + Size.OffsetX
                ||
             e.x < Size.StartXGrass - Size.OffsetX)
           ||
            (e.y > Size.PlaygroundHeight+Size.OffsetY+Size.StartYGrass 
                ||
             e.y < Size.StartYGrass - Size.OffsetY)
           ||
            Spot.lastSpot.theSameField(e))
        {
            return false;
        }
      
        if (Spot.lastSpot.distance(e) > Size.MaxDistance)
        {
            return false;
        }

        return this.isEmpty(e);
    }

// TODO: przerobic na statyczna
    /**
     * Sprawdza czy ten ruch nie byl juz wykonany
     */
    private boolean isEmpty(Spot e)
    {
        Iterator move = this.getIterator();

        while(move.hasNext())
        {
            Move m = (Move)move.next();
            Spot a = m.getStart();
            Spot b = m.getEnd();

            if ((Spot.lastSpot.x == a.x && Spot.lastSpot.y == a.y && e.x == b.x && e.y == b.y)
                    ||
                (Spot.lastSpot.x == b.x && Spot.lastSpot.y == b.y && e.x == a.x && e.y == a.y))
            {

                return false;
            }
        }
        return true;
    }
}
