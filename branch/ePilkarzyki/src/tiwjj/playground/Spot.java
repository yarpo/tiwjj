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
import java.util.Vector;

public class Spot extends Point {

    /**
     * statyczne pole zawierajace obiekt punktu nad ktorym aktualnie znajduje
     * sie kursor
     */
    static public Spot hoveredSpot;

    /**
     * statycze pole zawierajace obiekt punktu stanowiacego koniec sciezki
     * kolejnych ruchow
     */
    static public Spot lastSpot;

    protected Vector <Spot> neighbours = null;

    public Spot(int x, int y)
    {
        super(x, y);
    }

    public Spot(Point p)
    {
        this(p.x, p.y);
    }

    public int getXx()
    {
        return this.x - Size.OffsetX;
    }

    public int getYy()
    {
        return this.y - Size.OffsetY;
    }

    public boolean theSameField(int x, int y)
    {
        return (0 == this.distance(x, y));
    }

    public boolean theSameField(Point p)
    {
        return (0 == this.distance(p));
    }

    /**
     Sprawdza czy podany jako parametr
     */
    public boolean isFocused()
    {
        if ((Spot.lastSpot.x <= this.x + Size.HoverAreaX)
                &&
            (Spot.lastSpot.x >= this.x - Size.HoverAreaX)
            &&
            (Spot.lastSpot.y >= this.y - Size.HoverAreaY)
                &&
            (Spot.lastSpot.y <= this.y + Size.HoverAreaY))
        {
            return true;
        }

        return false;
    }

    public boolean isAccessible(Spot p)
    {
        return (this.distance(p) <= Size.MaxDistance);
    }

    private Vector<Spot> createNeighboursVector()
    {
        this.neighbours = new Vector<Spot>();

        int [] xs = { this.x, this.x - Size.HorizontalGap, this.x + Size.HorizontalGap };
        int [] ys = { this.y, this.y - Size.VerticalGap, this.y + Size.VerticalGap };

        for(int i = 2; i >= 0; i--)
        {
            for(int j = 2; j >= 0; j--)
            {
                if (Playground.isSpotable(xs[i], ys[j]) &&
                    !this.theSameField(xs[i], ys[j]))
                {
                    this.neighbours.add(new Spot(xs[i], ys[j]));
                }
            }
        }
        return this.neighbours;
    }

    public Vector<Spot> getNeighbours()
    {
        if (null != this.neighbours)
        {
            return this.neighbours;
        }

        return this.createNeighboursVector();
    }
}
