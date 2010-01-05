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
import java.util.Vector;
import java.util.Iterator;

public class Playground extends Canvas {

    //private Spot hoverPoint;   // punkt nad ktorym aktualnie jest kursor
    private Vector<Move> moves; // ruchy jakie wykonali gracze

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
        Spot.hoveredSpot = new Spot(this.xCenter, this.yCenter);
        this.moves = new Vector<Move>();
        this.moves.add(new Move(new Spot(this.xCenter, this.yCenter),
                                new Spot(this.xCenter, this.yCenter), 0));
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
        if (this.isAccessible(p))
        {
            this.update();
        }
    }

    private boolean isAccessible(Spot p)
    {
        double distance = this.getDistance(p, this.getLastPoint());

        return (distance <= Size.MaxDistance);
    }

    private Spot getLastPoint()
    {
        return (Spot)this.moves.lastElement().getEnd();
    }

    private boolean isHovered(int x, int y)
    {
        if (    (x <= Spot.hoveredSpot.x + Size.HorizontalGap/2 - 1)
                &&
                (x >= Spot.hoveredSpot.x - Size.HorizontalGap/2)
                &&
                (y >= Spot.hoveredSpot.y - Size.VerticalGap/2)
                &&
                (y <= Spot.hoveredSpot.y + Size.VerticalGap/2 - 1)
           )
        {
            return true;
        }

        return false;
    }

    private boolean isFocused(int x, int y)
    {
        Spot e =  this.getLastPoint();

        if ((e.x <= x + Size.HoverAreaX)
                &&
            (e.x >= x - Size.HoverAreaX)
            &&
            (e.y >= y - Size.HoverAreaY)
                &&
            (e.y <= y + Size.HoverAreaY))
        {
            return true;
        }

        return false;
    }

    private void drawHoveredPoint(Graphics g)
    {
        g.setColor(Colors.HoveredPoint);
        
    }

    private void drawFocusedPoints(Graphics g)
    {
        g.setColor(Colors.FocusedPoint);
        
    }

    private void drawPoints(Graphics g)
    {
        g.setColor(Colors.Points);

        for (int i = this.xStop; i >= this.xStart;  i -= Size.VerticalGap)
        {
            for (int j = this.yStop; j >=  this.yStart; j -= Size.HorizontalGap)
            {
                boolean is_focused = this.isFocused(i, j);
                boolean is_hovered = this.isHovered(i, j);

                if (is_focused)
                {
                    g.setColor(is_hovered ? Colors.HoveredPoint : Colors.FocusedPoint);
                    g.fillRect(i, j, Size.PointX, Size.PointY);
                    g.setColor(Colors.Points);
                }
                else
                {
                    g.drawRect(i, j, Size.PointX, Size.PointY);
                }
            }
        }
    }

    private void drawGoals(Graphics g)
    {
        g.setColor(Colors.Goals);

        int x_start = (int)(Size.PointsX/2 - 1)*Size.HorizontalGap+Size.StartXGrass;

        g.fillRect(x_start, Size.StartYGrass-Size.GoalHeight, Size.GoalWidth, Size.GoalHeight);
        g.fillRect(x_start, Size.PlaygroundHeight + Size.StartYGrass, Size.GoalWidth, Size.GoalHeight);
    }

    private void drawGrass(Graphics g)
    {
        g.setColor(this.bgColor);
        g.fillRect(Size.StartXGrass, Size.StartYGrass, Size.PlaygroundWidth, Size.PlaygroundHeight);
        g.setColor(Colors.Lines);
        g.drawLine(Size.StartXGrass, this.yCenter, Size.PlaygroundWidth,
                                                            this.yCenter);
    }

    private void drawMove(Graphics g, Move m)
    {
        g.setColor(Colors.Teams[m.getTeam()]);
        int [] x = {m.getStart().x, m.getEnd().x };
        int [] y = {m.getStart().y, m.getEnd().y };
        g.drawPolygon(x, y, 2);
    }

    private void drawMoves(Graphics g)
    {
        Iterator move = this.moves.iterator();
        Move m = null;
        while(move.hasNext())
        {
            m = (Move)move.next();
            drawMove(g, m);
        }

        if (null != m)
        {
            Spot current = m.getEnd();
            g.setColor(Colors.CurrentPoint);
            g.fillRect(current.x-2, current.y-2, Size.PointX, Size.PointY);
        }
    }

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

    private Spot calculateNextMove(Spot p)
    {
        p.x = round(p.x - this.xStart, Size.HorizontalGap) + this.xStart + 2;
        p.y = round(p.y - this.yStart, Size.VerticalGap) + this.yStart + 2;

        return p;
    }

    private int a = 0;

    private boolean isEmpty(Spot s, Spot e)
    {
        Iterator move = this.moves.iterator();
        int i = 0;
        while(move.hasNext())
        {
            Move m = (Move)move.next();
            Spot c = m.getStart();
            Spot b = m.getEnd();

            if ((s.x == c.x && s.y == c.y && e.x == b.x && e.y == b.y)
                    ||
                (s.x == b.x && s.y == b.y && e.x == c.x && e.y == c.y))
            {
              
                return false;
            }
        }
        return true;
    }

    private boolean possibleMove(Spot s, Spot e)
    {
        int x_point = Size.PointX/2;
        int y_point = Size.PointY/2;

        // nie jest na polu
        if ((s.x > Size.PlaygroundWidth + x_point || s.x < Size.StartXGrass - x_point)
            ||
            (s.y > Size.PlaygroundHeight + y_point || s.y < Size.StartYGrass - y_point)
            ||
            (s.x == e.x && s.y == e.y))
        {
            return false;
        }
        double distance = this.getDistance(s, e);

        if (distance > Size.MaxDistance)
        {
            return false;
        }

        return this.isEmpty(s, e);
    }

    private double getDistance(Spot s, Spot e)
    {
        // jest zbyt daleko od aktualnego punktu
        int a = Math.abs(s.x-e.x);
            a = a*a;
        int b = Math.abs(s.y-e.y);
            b = b*b;
        double c = Math.sqrt((double)(a+b));
        return c;
    }

    public void addMove(Spot p)
    {
        p = this.calculateNextMove(p);
        Move m = this.moves.lastElement();
        Spot s = m.getEnd();
        if (possibleMove(p, s))
        {
            this.moves.add(new Move(s, p, (this.a = (this.a+1)%2)));
            update();
        }
        else
        {
            System.out.println("Nie mozesz!");
        }
        
    }

    public void update()
    {
        Graphics g = this.getGraphics();

        drawGrass(g);
        drawGoals(g);
        drawPoints(g);
        drawMoves(g);
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
