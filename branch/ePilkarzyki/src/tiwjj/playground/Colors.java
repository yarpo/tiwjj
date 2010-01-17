package tiwjj.playground;

/**
 * Statyczna klasa Colors
 * Przechowuje stale z obiektami kolorow wykorzystywanymi w grze
 * @author  Patryk yarpo Jar
 * @date    6 - 01 - 2009
 */
import java.awt.Color;

public class Colors {

    /**
     * Normalny kolor tla - kiedy mysz nie znajduje sie nad boiskiem
     */
    public static final Color Normal = new Color(50, 150, 0);


    /**
     * Kolor tla, gdy mysz znajduje sie nad obszarem boiska
     */
    public static final Color Hover  = new Color(100, 150, 50);


    /**
     * Kolor srodkowej lini na boisku
     */
    public static final Color Lines  = new Color(140, 240, 180);


    /**
     * kolor nieaktywnych punktow na boisku
     */
    public static final Color Points = new Color(100, 100, 100);


    /**
     * Kolor 1 punktu, nad ktorym aktualnie jest kursor
     */
    public static final Color HoveredPoint = new Color(0,0,255);


    /**
     * kolor 8 punktow otaczajacych ostatni punkt ze sciezki ruchow
     */
    public static final Color FocusedPoint = new Color(0,0,0);


    /**
     * Kolor ostatniego punktu ze sciezki ruchow
     */
    public static final Color CurrentPoint = new Color(255,0,0);


    /**
     * Kolor bramek
     */
    public static final Color Goals = new Color(255, 255, 255);


    /**
     * Tablica kolorow druzyn
     */
    public static final Color [] Teams = {new Color(0,0,0), new Color(255,255,255)};
}