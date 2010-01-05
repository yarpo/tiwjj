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

public class Spot extends Point {

    static public Spot hoveredSpot;

    public Spot(int x, int y)
    {
        super(x, y);
    }

    public Spot(Point p)
    {
        super(p.x, p.y);
    }

    public double distance(Point p)
    {
        int a = Math.abs(this.x-p.x);
            a = a*a;
        int b = Math.abs(this.y-p.y);
            b = b*b;

        return Math.sqrt((double)(a+b));
    }

    /*
    private boolean isFocused(int x, int y)
    {
        Spot e =  this.getLastPoint();

        if ((e.x <= x + Size.HoverAreaX)
                &&
            (e.x >= x - Size.HoverAreaX)
            &&
            (e.y >= y - Size.HoverAreaY)
                &&
            (e.y <= y + Size.HoverAreaY))
        {
            return true;
        }

        return false;
    }*/

    public boolean isHovered(int x, int y)
    {
        return this.isHovered(new Spot(x ,y));
    }

    public boolean isHovered(Spot p)
    {
        if (    (x <= p.x + Size.HorizontalGap/2 - 1)
                &&
                (x >= p.x - Size.HorizontalGap/2)
                &&
                (y >= p.y - Size.VerticalGap/2)
                &&
                (y <= p.y + Size.VerticalGap/2 - 1)
           )
        {
            return true;
        }

        return false;
    }

    public boolean isFocused()
    {
        return false;
    }

    public boolean isAccessible(Spot p)
    {
        return (this.distance(p) <= Size.MaxDistance);
    }

}
