package tiwjj.playground;

/**
 * Statyczna Klasa Size
 * Zawiera wielkosci boiska, odleglosci punktow, itp.
 * Zbiorczy kontener stalych liczbowych dla projektu
 * @author  Patryk yarpo Jar
 * @date    6 - 01 - 2009
 */
public class Size {

    // wielkosci wyrazone sa w px

    final static public int PointX  = 4;                // szerokosc punktu
    final static public int PointY  = 4;                // wysokosc punktu
    final static public int OffsetX = (int)(PointX/2);  // przesuniecie rysowanego punktu wzgledem jego wspolrzednej x
    final static public int OffsetY = (int)(PointY/2);  // przesuniecie rysowanego punktu wzgledem wspolrzednej y
    final static public int PointsX = 10;   // liczba punktow w pionie
    final static public int PointsY = 12;   // liczba punktow w poziomie
    final static public int StartXGrass   = 2;  // odleglosc murawy od gory canvasa
    final static public int StartYGrass   = 10; // odleglosc murawy od bokow canvasa
    final static public int VerticalGap   = 20; // odleglosc punktow miedzy soba w pionie
    final static public int HorizontalGap = 20; // odleglosc punktow w poziomie
    final static public int PlaygroundWidth  = HorizontalGap*PointsX;   // szerokosc boiska
    final static public int PlaygroundHeight = VerticalGap*PointsY;     // wysokosc boiska
    final static public int WIDTH   = PlaygroundWidth+StartXGrass*2;    // szerokosc calego apletu
    final static public int HEIGHT  = PlaygroundHeight+StartYGrass*2;   // wysokosc apletu
    final static public int GoalWidth  = 45;    // szerokosc bramki
    final static public int GoalHeight = 5;     // wysokosc lini symulujacej bramke
    final static public int HoverAreaX = 30;    // obszar podswietlany wokol aktualnego punktu w pionie
    final static public int HoverAreaY = 30;    // obszar podswietlany wokol aktualnego punktu w poziomie
    // maksymalny dystans miedzy dwoma punktami, ktore mialy tworzy jeden ruch
    final static public double MaxDistance = Math.sqrt(Size.VerticalGap*Size.VerticalGap + Size.HorizontalGap*Size.HorizontalGap);


        /**
     * srodek boiska w poziomie
     */
    public final static int xCenter = (int)(PlaygroundWidth/2) + StartXGrass;


    /**
     * srodek boiska w pionie
     */
    public final static int yCenter = (int)(PlaygroundHeight/2) + StartYGrass;


    /**
     * poczatek na osi x wyrysowywania punktow.
     * _Nie_ musi pokrywac sie z poczatekiem boiska
     */
    public final static int xStart = StartXGrass - OffsetX;


    /**
     * poczatek na osi y wyrysowywania punktow.
     * _Nie_ musi pokrywac sie z poczatekiem boiska
     */
    public final static int yStart = StartYGrass - OffsetY;


    /**
     * graniczy punkt dla wyrysowywania punktow na boisku w osi x
     */
    public final static int xStop  = PlaygroundWidth + xStart;


    /**
     * graniczy punkt dla wyrysowywania punktow na boisku w osi y
     */
    public final static int yStop  = PlaygroundHeight + yStart;

    public final static int GoalXStart = (int)((PointsX / 2 - 1) *
                                                        HorizontalGap +
                                                        StartXGrass) - OffsetX;

    public final static int GoalYBottom = PlaygroundHeight + StartYGrass;
    
    public final static int GoalYTop = StartYGrass - GoalHeight;

    
}
