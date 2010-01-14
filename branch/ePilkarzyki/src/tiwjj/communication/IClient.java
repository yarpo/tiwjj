/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tiwjj.communication;

/**
 *
 * @author yarpo
 */
public interface IClient {

    public int joinGame();
    public int joinTeam(int i);
    public int getTeam();
    public boolean isMyTurn();
    public boolean myMove();
    public boolean end();
    public boolean update(Exchanger data);
    public void start();
    public void pause();
    public void resume();
}
