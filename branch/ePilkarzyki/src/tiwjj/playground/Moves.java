/*
 * Moves.java
 *
 * Klasa przechowujaca informacje o wszystkich ruchach wykonanych w grze
 * do tej pory
 */

package tiwjj.playground;

/**
 * @author  yarpo
 * @data    5 01 2010
 */

import java.util.Vector;
import java.util.Iterator;

// TODO: zastanowic sie nad wprowadzeniem dodatkowo macierzy punktow
// TODO: pokomentowac metody i atrybuty

public class Moves {

    private Vector<Move> moves;
    private Spot startPosition;

    public Moves(Move m)
    {
        this.moves = new Vector<Move>();
        this.startPosition = m.getStart();
        this.moves.add(m);
    }

    public Moves(Spot s, Spot e, int team)
    {
        this(new Move(s, e, team));
    }

    public void add(Move m)
    {
        this.moves.add(m);
        Spot.lastSpot = m.getEnd();
    }

    public Iterator getIterator()
    {
        return this.moves.iterator();
    }

    public Spot getLastPoint()
    {
        return this.moves.lastElement().getEnd();
    }

    public int length()
    {
        return this.moves.size();
    }

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
