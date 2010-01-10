/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tiwjj.communication;

/**
 *
 * @author yarpo
 */
public class Exchanger implements java.io.Serializable {

    public enum MessagesType {
        START,
        NAME,
        WINER,
        LOSER,
        NORMAL
    }

    public String content;
    public MessagesType type;
}
