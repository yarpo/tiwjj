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
    public boolean isMyTurn();
    public boolean myMove();

}
