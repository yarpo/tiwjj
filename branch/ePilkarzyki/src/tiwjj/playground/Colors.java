package tiwjj.playground;

/**
 * Statyczna klasa Colors
 * Przechowuje stale z obiektami kolorow wykorzystywanymi w grze
 * @author  Patryk yarpo Jar
 * @date    6 - 01 - 2009
 */
import java.awt.Color;

public class Colors {
    public static final Color Normal = new Color(50, 150, 0);   // tla, kiedy mysz nie znajduje sie nad boiskiem
    public static final Color Hover  = new Color(100, 150, 50); // tla gdy mysz jest nad boiskiem
    public static final Color Lines  = new Color(140, 240, 180);// srodkowej lini
    public static final Color Points = new Color(100, 100, 100);// punktow nieaktywnych
    public static final Color HoveredPoint = Color.blue;        // punktu podwietlonego
    public static final Color FocusedPoint = Color.black;       // punktow osiagalnych z aktualnego
    public static final Color CurrentPoint = Color.red;         // aktualny punkt koncowy
    public static final Color Goals = new Color(255, 255, 255); // linie symulujace bramki
    public static final Color [] Teams = {Color.black, Color.white}; // kolory druzyn
}