package tiwjj.communication;

import tiwjj.playground.*;
import java.util.Vector;

/**
 * Interfejs IClient implementowany przez dowolnego klienta
 * @author  Patryk yarpo Jar
 * @date    6 - 01 - 2009
 */
public interface IClient {

    /**
     * Logowanie do systemu. Zwraca true, jesli udalo sie, false else
     *
     * @param String user
     * @param String pass
     *
     * @returns boolean
     */
    public boolean login(String user, String pass);


    /**
     * Dolaczanie do gry. Zwraca identyfikator dryzyny. Jesli -1, nie udalo sie
     * dolaczyc
     *
     * @returns int
     */
    public int joinGame();


    /**
     * Dolaczanie do konkretnej druzyny
     *
     * @param int i
     *
     * @returns int
     */
    public int joinTeam(int i);


    /**
     * Pobranie identyfikatora wlasnej druzyny
     *
     * @returns int
     */
    public int getMyTeam();


    /**
     * Pobranie identyfikatora druzyny, ktora ma aktualnie ruch
     *
     * @returns int
     */
    public int getCurrentTeam();


    /**
     * Sprawdza czy to twoja runda
     *
     * @returns boolean
     */
    public boolean isMyTurn();


    /**
     * Przekazanie serwerowi informacji o tym, ze teraz powinna byc inna druzyna
     */
    public void nextTeam();


    /**
     * Przeslanie kolejnego ruchu na serwer
     *
     * @param Vector<Move> moves
     *
     * @returns boolean
     */
    public boolean myMove(Vector<Move> moves);


    /**
     * Komunikat o koncu gry
     */
    public boolean end();


    /**
     * Pobranie aktualnego wektora ruchow
     */
    public boolean update();


    /**
     * Rozpoczecie gry
     */
    public void start(Playground p);


    /**
     * Wstrzymanie gry
     */
    public void pause();


    /**
     * Wznowienie gry
     */
    public void resume();
}
