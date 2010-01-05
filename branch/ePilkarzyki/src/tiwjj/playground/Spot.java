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

    public boolean isHovered()
    {
        return false;
    }

    public boolean isFocused()
    {
        return false;
    }

}
