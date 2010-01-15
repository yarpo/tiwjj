/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tiwjj.communication;

import tiwjj.playground.*;
import java.util.Vector;

/**
 *
 * @author yarpo
 */
public interface IClient {

    public int joinGame();
    public int joinTeam(int i);
    public int getMyTeam();
    public int getCurrentTeam();
    public boolean isMyTurn();
    public void nextTeam();
    public boolean myMove(Vector<Move> moves);
    public boolean end();
    public boolean update();
    public void start(Playground p);
    public void pause();
    public void resume();
}
