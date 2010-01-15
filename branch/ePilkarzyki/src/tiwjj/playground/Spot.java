package tiwjj.playground;

/**
 * Klasa Spot dziedziczaca po Point
 * Pozwala na obsluge punktow na Playground
 * @author  Patryk yarpo Jar
 * @date    6 - 01 - 2009
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


    /**
     * lista sasiadow danego punktu - tworzona tylko w przypadku wywolania
     * Spot.getNeighbours
     */
    protected Vector <Spot> neighbours = null;


    /**
     * Konstruktor 1 - ze wspolrzednych
     * @param int x - wspolrzedna x
     * @param int y - wspolrzedna y
     */
    public Spot(int x, int y)
    {
        super(x, y);
        if (null == Spot.lastSpot)
        {
            Spot.lastSpot = this;
        }
    }

    
    /**
     * Konstruktor 2 - rozszerzajacy obiekt Point
     * @param int x - wspolrzedna x
     * @param int y - wspolrzedna y
     */
    public Spot(Point p)
    {
        this(p.x, p.y);
    }

    
    /**
     * Zwraca wspolrzedna x gotowa do zastosowania w wyswietlaniu na canvasie
     * 
     * @uses Size.OffsetX
     *
     * @returns int
     */
    public int getXx()
    {
        return this.x - Size.OffsetX;
    }

    
     /**
     * Zwraca wspolrzedna y gotowa do zastosowania w wyswietlaniu na canvasie
     *
     * @uses Size.OffsetY
     *
     * @returns int
     */
    public int getYy()
    {
        return this.y - Size.OffsetY;
    }


    /**
     * Sprawdza czy aktualny punkt nie ma takich samym wspolrzednych z innym
     *
     * @param int x - wspolrzedna x
     * @param int y - wspolrzedna y
     *
     * @returns boolean
     */
    public boolean theSameField(int x, int y)
    {
        return (0 == this.distance(x, y));
    }


     /**
     * Sprawdza czy aktualny punkt nie ma takich samym wspolrzednych z innym
     *
     * @param Point p - obiekt klasy Point
     *
     * @returns boolean
     */
    public boolean theSameField(Point p)
    {
        return (0 == this.distance(p));
    }

    
    /**
     * Sprawdza czy ten punkt powinien byc podswietlony
     *
     * @uses Spot.lastSpot
     * @uses Size.HoverAreaX
     * @uses Size.HoverAreaY
     *
     * @returns boolean
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


    /** 
     * Sprawdza czy podany jako parametr punkt jest w zasiegu aktualnego
     * Wykorzystywane przy tworzeniu nowych ruchow
     *
     * @param Point p - punkt do ktorego chcielibysmy przejsc
     *
     * @uses Size.MaxDistance
     *
     * @returns boolean
     */
    public boolean isAccessible(Point p)
    {
        return (this.distance(p) <= Size.MaxDistance);
    }


    /** 
     * Tworzy wektor sasiednich punktow (tych, ktore  znajduja sie na planszy)
     * _Nie_ sprawdza czy mozna do nich pojsc
     *
     * @uses Size.HorizontalGap
     * @uses Size.VerticalGap
     *
     * @returns Vector<Spot>
     */
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


    /**
     * Zwraca wektor sasiednich punktow
     * Jesli taki istnieje lub wpierw nakazuje go stworzyc i potem zwraca
     *
     * @returns Vector<Spot> 
     */
    public Vector<Spot> getNeighbours()
    {
        if (null != this.neighbours)
        {
            return this.neighbours;
        }

        return this.createNeighboursVector();
    }

    
    /**
     * Sprawdza, czy aktualny punkt jest polozony na lini koncowej lub bocznej
     * boiska
     *
     * @returns boolean
     */
    public boolean isBorder()
    {
        if (this.getXx() == Size.xStart || this.getXx() == Size.xStop ||
            this.getYy() == Size.yStart || this.getYy() == Size.yStop)
        {
            return true;
        }

        return false;
    }

    
    /**
     * Pseudo podloga i sufit
     * z ta roznica, ze zaokragla nie do calosci, a do liczby podanej jako
     * drugi parametr
     *
     * @param int x - liczba 'zaokraglana'
     * @param int m - liczba do ktorej wielokrotnosci sie zaokragla
     *
     * @returns int
     */
    private static int round(int x, int m)
    {
        int tmp = x%m;
        if (tmp >= (int)(m/2))
        {
            x += (m - tmp);
        }
        else
        {
            x -= tmp;
        }
        return x;
    }


    /**
     * Normalizuje wspolrzedne podanego punktu do wpolrzednych
     * najblizszego punktu na boisku
     *
     * @param Point p
     *
     * @returns Spot
     */
    public static Spot normalize(Point p)
    {
        return Spot.normalize(new Spot(p));
    }


    /**
     * Normalizuje podane wspolrzedne do wspolrzednych
     * najblizszego punktu na boisku i zwraca obiekt z takimi wspolrzednymi
     *
     * @param Point p
     *
     * @returns Spot
     */
    public static Spot normalize(int x, int y)
    {
       return Spot.normalize(new Spot(x, y));
    }

    /**
     * Normalizuje podane wspolrzedne do wspolrzednych
     * najblizszego punktu na boisku
     *
     * @param Spot p
     *
     * @returns Spot
     */
    public static Spot normalize(Spot p)
    {
        p.x = round(p.x - Size.xStart, Size.HorizontalGap) + Size.xStart + Size.OffsetX;
        p.y = round(p.y - Size.yStart, Size.VerticalGap) + Size.yStart + Size.OffsetY;

        return p;
    }
}
