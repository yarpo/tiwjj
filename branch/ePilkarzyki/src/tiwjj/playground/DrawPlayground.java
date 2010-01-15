
package tiwjj.playground;

import java.awt.Graphics;
import java.util.Iterator;
import java.util.Vector;
import java.awt.Color;

/**
 *
 * @author yarpo
 */
public class DrawPlayground {

    /**
     * Referacja na obiekt boiska
     */
    private Playground playground;


    /**
     * referencja na obiekt pozwalajacy wypisywac cokolweik na ekran
     */
    private Graphics g;


    /**
     * Kolor tla
     */
    private Color bgColor = Colors.Normal;


    /**
     * Konstruktor
     *
     * @param Playground playground
     */
    public DrawPlayground(Playground playground)
    {
        this.playground = playground;
    }


    /**
     * Wyrysuj wszystko na ekran ponownie
     */
    public void refresh()
    {
        if (null == this.g)
        {
            this.g = this.playground.getGraphics();
        }

        this.drawGrass();
        this.drawGoals();
        this.drawPoints();
        this.drawMoves();
    }

    /**
     * Wyrysowywanie podswietlonego punktow
     */
    public void drawHoveredPoint()
    {
        Spot s = Spot.normalize(Spot.hoveredSpot);
        this.g.setColor(Colors.HoveredPoint);
        if (Spot.lastSpot.isAccessible(s) && !s.theSameField(Spot.lastSpot))
        {
            this.g.fillRect(s.x-Size.OffsetX, s.y-Size.OffsetY, 
                                                        Size.PointX,
                                                        Size.PointY);
        }
    }


    /**
     * Wyrysuj specjalne punkty - otaczajace aktualny punkt
     */
    public void drawSpecialPoints()
    {
        this.g.setColor(Colors.FocusedPoint);

        Vector v = Spot.lastSpot.getNeighbours();

        for(int i = v.size()- 1; i >= 0; i--)
        {
            Spot p = (Spot)v.elementAt(i);
            this.g.fillRect(p.getXx(), p.getYy(), Size.PointX, Size.PointY);
        }
    }

    public void setBgColor(Color c)
    {
        this.bgColor = c;
    }

    /**
     * Wyrysowuje punkty niekatywne
     */
    private void drawPoints()
    {
        this.g.setColor(Colors.Points);

        for (int i = Size.xStop; i >= Size.xStart;  i -= Size.VerticalGap)
        {
            for (int j = Size.yStop; j >=  Size.yStart; j -= Size.HorizontalGap)
            {
                this.g.drawRect(i, j, Size.PointX, Size.PointY);
            }
        }
    }

    /**
     * Rysuje bramki
     */
    private void drawGoals()
    {
        this.g.setColor(Colors.Goals);
        this.g.fillRect(Size.goalStart - Size.OffsetX, Size.StartYGrass -
                                                            Size.GoalHeight,
                                                            Size.GoalWidth,
                                                            Size.GoalHeight);
        this.g.fillRect(Size.goalStart - Size.OffsetY, Size.PlaygroundHeight +
                                                            Size.StartYGrass,
                                                            Size.GoalWidth,
                                                            Size.GoalHeight);
    }

    /**
     * Rysuje trawe i srodkowa linie
     */
    private void drawGrass()
    {
        this.g.clearRect(0, 0, Size.WIDTH, Size.HEIGHT);
        this.g.setColor(this.bgColor);
        this.g.fillRect(Size.StartXGrass, Size.StartYGrass,
                                                Size.PlaygroundWidth,
                                                Size.PlaygroundHeight);
        this.g.setColor(Colors.Lines);
        this.g.drawLine(Size.StartXGrass, Size.yCenter,
                                                Size.PlaygroundWidth,
                                                Size.yCenter);
    }


    /**
     * Rysuje pojedynczy ruch
     *
     * @param Move m
     */
    private void drawMove( Move m)
    {
        this.g.setColor(Colors.Teams[m.getTeam()]);
        this.g.drawLine(m.getStart().x, m.getStart().y, m.getEnd().x,
                                                        m.getEnd().y);
    }


    /**
     * Rysuje wszystkie ruchy
     */
    private void drawMoves()
    {
        Iterator move = this.playground.getMoves().getIterator();
        while(move.hasNext())
        {
            this.drawMove((Move)move.next());
        }

        this.g.setColor(Colors.CurrentPoint);
        this.g.fillRect(Spot.lastSpot.getXx(), Spot.lastSpot.getYy(), 
                                                                Size.PointX,
                                                                Size.PointY);
    }

}
