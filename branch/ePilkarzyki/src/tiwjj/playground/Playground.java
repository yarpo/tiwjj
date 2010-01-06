/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tiwjj.playground;

/**
 *
 * @author yarpo
 */
import java.awt.*;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Iterator;

// TODO: dodac komentarze do metod i atrybutow

public class Playground extends Canvas {

    private Graphics g;   // punkt nad ktorym aktualnie jest kursor
    private Moves moves;

    public static class Colors {
        public static final Color Normal = new Color(50, 150, 0);
        public static final Color Hover  = new Color(100, 150, 50);
        public static final Color Lines  = new Color(140, 240, 180);
        public static final Color Points = new Color(100, 100, 100);
        public static final Color HoveredPoint = Color.blue;
        public static final Color FocusedPoint = Color.black;
        public static final Color CurrentPoint = Color.red;
        public static final Color Goals = new Color(255, 255, 255);
        public static final Color [] Teams = {Color.black, Color.white};
    }

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
            drawFocusedPoints();
            drawHoveredPoint();
        }
    }

    private void drawHoveredPoint()
    {
        Spot s = this.calculateNextMove(Spot.hoveredSpot);
        g.setColor(Colors.HoveredPoint);
        if (Spot.lastSpot.isAccessible(s))
        {
// TODO: do size dodac stala wyliczana wg PointX/2 PointY/2
            g.fillRect(s.x-Size.OffsetX, s.y-Size.OffsetY, Size.PointX, Size.PointY);
        }
    }

    

    private void drawFocusedPoints()
    {
        g.setColor(Colors.FocusedPoint);
        FocusedPointDimension p = new FocusedPointDimension(Spot.lastSpot);

        g.fillRect(p.xLeft, p.yTop, Size.PointX, Size.PointY);
        g.fillRect(p.xLeft, p.yCenter, Size.PointX, Size.PointY);
        g.fillRect(p.xLeft, p.yBottom, Size.PointX, Size.PointY);
        g.fillRect(p.xCenter, p.yTop, Size.PointX, Size.PointY);
        g.fillRect(p.xCenter, p.yBottom, Size.PointX, Size.PointY);
        g.fillRect(p.xRight, p.yTop, Size.PointX, Size.PointY);
        g.fillRect(p.xRight, p.yCenter, Size.PointX, Size.PointY);
        g.fillRect(p.xRight, p.yBottom, Size.PointX, Size.PointY);
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
        g.fillRect(Spot.lastSpot.x-2, Spot.lastSpot.y-2, Size.PointX, Size.PointY);
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
}
