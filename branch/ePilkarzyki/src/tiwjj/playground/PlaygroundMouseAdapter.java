package tiwjj.playground;

/**
 * Klasa PlaygroundMouseAdapter dziedziczaca po MouseAdapter
 * Obsluga zdarzen na canvasie
 * @author  Patryk yarpo Jar
 * @date    6 - 01 - 2009
 */
import java.awt.event.*;

public class PlaygroundMouseAdapter extends MouseAdapter {

    /**
     * Referencja na obiekt boiska
     */
    private Playground canvas;


    /**
     * Konstruktor
     */
    public PlaygroundMouseAdapter(Playground canvas)
    {
        this.canvas = canvas;
    }


    /**
     * Obsluga zdarzenia klikniecia mysza
     * Probuje dodac nowy ruch
     *
     * @param MouseEvent evt
     */
    @Override
    public void mouseClicked(MouseEvent evt)
    {
        this.canvas.addMove(new Spot(evt.getPoint()));
    }


    /**
     * Obsluga zdarzenia wjechania mysza na obszar boiska
     * Podswietla odpowiednie punkty
     *
     * @param MouseEvent evt
     */
    @Override
    public void mouseEntered(MouseEvent evt)
    {
        this.canvas.hover(new Spot(evt.getPoint()));
        this.canvas.mouseOver();
    }


    /**
     * Obsluga zdarzenia poruszania mysza nad obszarem boiska
     * Podswietla odpowiednie punkty
     *
     * @param MouseEvent evt
     */
    @Override
    public void mouseMoved(MouseEvent evt)
    {
        this.canvas.hover(new Spot(evt.getPoint()));
    }


    /**
     * Obsluga zdarzenia wyjechania mysza znad obszaru boiska
     *
     * @param MouseEvent evt
     */
    @Override
    public void mouseExited(MouseEvent evt)
    {
        this.canvas.mouseOut();
    }
}
