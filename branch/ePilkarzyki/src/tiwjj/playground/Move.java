package tiwjj.playground;
/**
 * Klasa Move
 * Pozwala na obsluge ruchow na Playground
 * @author  Patryk yarpo Jar
 * @date    6 - 01 - 2009
 */

public class Move implements java.io.Serializable {

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


    /**
     * Sprawdza czy dwa ruchy sa takie same
     */
    public static boolean theSameMove(Move m1, Move m2)
    {
        Spot m1_1 = m1.getStart();  // punkt startowy
        Spot m1_2 = m1.getEnd();    // punkt koncowy rychu
        Spot m2_1 = m2.getStart();  // punkt startowy
        Spot m2_2 = m2.getEnd();    // punkt koncowy rychu

        if ((m2_1.theSameField(m1_1) && m2_2.theSameField(m1_2))
            ||
            (m2_1.theSameField(m1_2) && m2_2.theSameField(m1_1)))
        {
            return true; // juz istnieje
        }

        return false;
    }


    /**
     * Sprawdza czy aktualny ruch jest identyczny z podanym jako poarametr
     *
     * @param Move m
     *
     * @returns boolean
     */
    public boolean theSameMove(Move m)
    {
        return Move.theSameMove(this, m);
    }


    /**
     * Sprawdza czy aktualny ruch jest taki sam jak podany jako porametr
     * Wlaczajac to te sama druzyne
     *
     * @param Move m
     * @param int team
     *
     * @returns boolean
     */
    public boolean theSameMove(Move m, int t)
    {
        if (m.getTeam() == this.team && Move.theSameMove(this, m))
        {
            return true;
        }
        return false;
    }
}
