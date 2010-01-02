/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tiwjj.playground;

/**
 *
 * @author yarpo
 */

import java.awt.Point;

public class Move {

    private Point start;
    private Point end;
    private int team;
    // TODO: private int player;

    public Move(Point s, Point e, int t)
    {
        this.start = s;
        this.end = e;
        this.team = t;
    }

    public Point getStart()
    {
        return this.start;
    }

    public Point getEnd()
    {
        return this.end;
    }

    public int getTeam()
    {
        return this.team;
    }
}
