/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tiwjj.actions;

import tiwjj.communication.*;
/**
 *
 * @author yarpo
 */
public abstract class ActionHandler {

    protected int       team;
    protected IClient   client;

    public ActionHandler(IClient client, int team)
    {
        this.team = team;
        this.client = client;
    }

}
