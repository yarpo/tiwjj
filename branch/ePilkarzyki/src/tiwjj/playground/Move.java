package tiwjj.playground;

/**
 * Klasa Move
 * Pozwala na obsluge ruchow na Playground
 * @author  Patryk yarpo Jar
 * @date    6 - 01 - 2009
 */


public class Move {

    /**
     * Punkt startowy ruchu
     */
    private Spot start;


    /** 
     * Punkt koncowy ruchu
     */
    private Spot end;

    /**
     * Identyfikator druzyny
     */
    private int team;
    // TODO: private int player;


    /**
     * Konstruktor
     *
     * @param Spot s - punkt startowy
     * @param Spot e - punkt koncowy
     * @param int t - druzyna
     */
    public Move(Spot s, Spot e, int t)
    {
        this.start = s;
        this.end = e;
        this.team = t;
    }


    /**
     * getter punktu startowego
     */
    public Spot getStart()
    {
        return this.start;
    }


    /**
     * getter punktu koncowego 
     */
    public Spot getEnd()
    {
        return this.end;
    }

    /**
     * getter identyfikatora teamu
     */
    public int getTeam()
    {
        return this.team;
    }
}
