/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tiwjj.playground;

/**
 *
 * @author yarpo
 */

public class Move {

    private Spot start;
    private Spot end;
    private int team;
    // TODO: private int player;

    public Move(Spot s, Spot e, int t)
    {
        this.start = s;
        this.end = e;
        this.team = t;
    }

    public Spot getStart()
    {
        return this.start;
    }

    public Spot getEnd()
    {
        return this.end;
    }

    public int getTeam()
    {
        return this.team;
    }
}
