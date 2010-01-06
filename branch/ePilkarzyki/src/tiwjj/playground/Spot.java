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

    public Spot(int x, int y)
    {
        super(x, y);
    }

    public Spot(Point p)
    {
        super(p.x, p.y);
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

}
