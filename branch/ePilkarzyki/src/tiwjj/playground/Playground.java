package tiwjj.playground;

/**
 * Klasa Playground dziedziczaca po Canvas
 * Pozwala na wyswieltanie gry w aplecie
 * @author  Patryk yarpo Jar
 * @date    6 - 01 - 2009
 */
import java.awt.*;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Iterator;
import java.util.Vector;


public class Playground extends Canvas {

    /**
     * Obiekt pozwalajacy wyrysowywac wyniki na ekran
     */
    private Graphics g;


    /**
     * Obiekt pozwalajacy na obsluge ruchow graczy
     */
    private Moves moves;

    private Color bgColor = Colors.Normal;
    private int xCenter = (int)(Size.PlaygroundWidth/2) +  Size.StartXGrass;
    private int yCenter = (int)(Size.PlaygroundHeight/2) + Size.StartYGrass;
    private int xStart = Size.StartXGrass - (int)Size.PointX/2;
    private int yStart = Size.StartYGrass - (int)Size.PointY/2;
    private int xStop  = Size.PlaygroundWidth + this.xStart;
    private int yStop  = Size.PlaygroundHeight + this.yStart;

    public Playground()
    {
        Spot f = new Spot(this.xCenter, this.yCenter);
        Spot.hoveredSpot = f;
        Spot.lastSpot = Spot.hoveredSpot;
        Move first = new Move(f, f, 0);
        this.moves = new Moves(first);
    }

    public void mouseOver()
    {
        this.bgColor = Colors.Hover;
        this.update();
    }

    public void mouseOut()
    {
        this.bgColor = Colors.Normal;
        this.update();
    }

    public void hover(Spot p)
    {
        Spot.hoveredSpot = p;
        if (p.isAccessible(Spot.lastSpot))
        {
            drawSpecialPoints();
            drawHoveredPoint();
        }
    }

    private void drawHoveredPoint()
    {
        Spot s = this.calculateNextMove(Spot.hoveredSpot);
        g.setColor(Colors.HoveredPoint);
        if (Spot.lastSpot.isAccessible(s) && s.distance(Spot.lastSpot) != 0)
        {
// TODO: do size dodac stala wyliczana wg PointX/2 PointY/2
            g.fillRect(s.x-Size.OffsetX, s.y-Size.OffsetY, Size.PointX, Size.PointY);
        }
    }

    private void drawSpecialPoints()
    {
        g.setColor(Colors.FocusedPoint);

        Vector v = Spot.lastSpot.getNeighbours();

        for(int i = v.size()- 1; i >= 0; i--)
        {
            Spot p = (Spot)v.elementAt(i);
            g.fillRect(p.getXx(), p.getYy(), Size.PointX, Size.PointY);
        }
    }

    private void drawPoints()
    {
        g.setColor(Colors.Points);

        for (int i = this.xStop; i >= this.xStart;  i -= Size.VerticalGap)
        {
            for (int j = this.yStop; j >=  this.yStart; j -= Size.HorizontalGap)
            {
                g.drawRect(i, j, Size.PointX, Size.PointY);
            }
        }
    }

    private void drawGoals()
    {
        g.setColor(Colors.Goals);
        int x_start = (int)(Size.PointsX/2 - 1)*Size.HorizontalGap+Size.StartXGrass;
        g.fillRect(x_start-Size.OffsetX, Size.StartYGrass - Size.GoalHeight, Size.GoalWidth, Size.GoalHeight);
        g.fillRect(x_start-Size.OffsetY, Size.PlaygroundHeight + Size.StartYGrass, Size.GoalWidth, Size.GoalHeight);
    }

    private void drawGrass()
    {
        g.clearRect(0, 0, Size.WIDTH, Size.HEIGHT);
        g.setColor(this.bgColor);
        g.fillRect(Size.StartXGrass, Size.StartYGrass, Size.PlaygroundWidth, Size.PlaygroundHeight);
        g.setColor(Colors.Lines);
        g.drawLine(Size.StartXGrass, this.yCenter, Size.PlaygroundWidth,
                                                            this.yCenter);
    }

    private void drawMove( Move m)
    {
        g.setColor(Colors.Teams[m.getTeam()]);
        g.drawLine(m.getStart().x, m.getStart().y, m.getEnd().x, m.getEnd().y);
    }

    private void drawMoves()
    {
        Iterator move = this.moves.getIterator();
        while(move.hasNext())
        {
            drawMove((Move)move.next());
        }

        g.setColor(Colors.CurrentPoint);
        g.fillRect(Spot.lastSpot.getXx(), Spot.lastSpot.getYy(), Size.PointX, Size.PointY);
    }
// TODO: przeniesc do ktorejs z klas
    private int round(int x, int m)
    {
        int tmp = x%m;
        if (tmp >= (int)(m/2))
        {
            x += (m - tmp);
        }
        else
        {
            x -= tmp;
        }
        return x;
    }
// TODO przeniesc
    private Spot calculateNextMove(Spot p)
    {
        p.x = round(p.x - this.xStart, Size.HorizontalGap) + this.xStart + 2;
        p.y = round(p.y - this.yStart, Size.VerticalGap) + this.yStart + 2;

        return p;
    }

    private int teamTurn = 0;

    // TODO: uproscic
    public void addMove(Spot p)
    {
        p = this.calculateNextMove(p);

        if (this.moves.possible(p))
        {
            this.moves.add(new Move(Spot.lastSpot, p, (this.teamTurn = (this.teamTurn+1)%2)));
            update();
        }
        else
        {
            System.out.println("Nie mozesz!");
        }
        
    }

    public void update()
    {
        if (null == this.g)
        {
            this.g = this.getGraphics();
        }

        drawGrass();
        drawGoals();
        drawPoints();
        drawMoves();
    }

   /*
    * Paint when the AWT tells us to...
    */
    @Override
    public void paint(Graphics g)
    {
        update();
    }

    @Override
    public void update(Graphics g)
    {
        update();

    }

        /**
     * czy w punkcie o takich wspolrzednym moze zostac postawiony punkt
     */
    public static boolean isSpotable(int x, int y)
    {
        if (y >= Size.StartYGrass - Size.PointY &&
            y <= Size.PlaygroundHeight - Size.PointY &&
            x <= Size.PlaygroundWidth + Size.PointX &&
            x >= Size.StartYGrass - Size.PointX)
        {
            return true;
        }

        return false;
    }
}
