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
        int x_point = Size.PointX/2;
        int y_point = Size.PointY/2;

        // nie jest na polu
        if ((e.x > Size.PlaygroundWidth + x_point || e.x < Size.StartXGrass - x_point)
            ||
            (e.y > Size.PlaygroundHeight + y_point || e.y < Size.StartYGrass - y_point)
            ||
            (e.x == Spot.lastSpot.x && e.y == Spot.lastSpot.y))
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
