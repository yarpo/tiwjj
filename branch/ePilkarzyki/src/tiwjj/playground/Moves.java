/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tiwjj.playground;

/**
 *
 * @author yarpo
 */

import java.util.Vector;
import java.util.Iterator;

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

    public Spot getLastPoint()
    {
        return this.moves.lastElement().getEnd();
    }

    public int length()
    {
        return this.moves.size();
    }
}
