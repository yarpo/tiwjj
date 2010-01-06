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

    private int xX;
    private int yY;

    public Spot(int x, int y)
    {
        super(x, y);
        this.xX = x - Size.OffsetX;
        this.yY = y - Size.OffsetY;
    }

    public Spot(Point p)
    {
        this(p.x, p.y);
    }

    public double getXx()
    {
        return xX;
    }

    public double getYy()
    {
        return yY;
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

    public Vector<Spot> getNeighbours()
    {
        Vector v = new Vector<Spot>();

        int [] xs =  {
                            this.x,                        // wspolrz x wyswietlana
                            this.x - Size.HorizontalGap,   // na lewo
                            this.x + Size.HorizontalGap    // na prawo
                        };

        int [] ys =  {
                            this.y,    // wspolrzedna y wyswieltana
                            this.y - Size.VerticalGap,   // do gory
                            this.y + Size.VerticalGap    // na dol
                        };

        for(int i = 2; i >= 0; i--)
        {
            for(int j = 2; j >= 0; j--)
            {
                if (Playground.isSpotable(xs[i], ys[j]) &&
                    !this.theSameField(xs[i], ys[j]))
                {
                    v.add(new Spot(xs[i], ys[j]));
                }
            }
        }

        return v;
    }
}
