/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tiwjj.playground;

/**
 *
 * @author yarpo
 */
import java.awt.event.*;

public class PlaygroundMouseAdapter extends MouseAdapter {

    private Playground canvas;

    public PlaygroundMouseAdapter(Playground canvas)
    {
        this.canvas = canvas;
    }

    @Override
    public void mouseClicked(MouseEvent evt)
    {
        this.canvas.addMove(new Spot(evt.getPoint()));
    }

    @Override
    public void mouseEntered(MouseEvent evt)
    {
        this.canvas.hover(new Spot(evt.getPoint()));
        this.canvas.mouseOver();
    }

    @Override
    public void mouseMoved(MouseEvent evt)
    {
        this.canvas.hover(new Spot(evt.getPoint()));
    }

    @Override
    public void mouseExited(MouseEvent evt)
    {
        this.canvas.mouseOut();
    }
}
