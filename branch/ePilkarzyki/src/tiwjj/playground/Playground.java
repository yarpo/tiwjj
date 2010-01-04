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
import java.awt.Point;
import java.util.Vector;
import java.util.Iterator;

public class Playground extends Canvas {

    private Point hoverPoint;   // punkt nad ktorym aktualnie jest kursor
    private Vector<Move> moves; // ruchy jakie wykonali gracze

    public static class Size {
        final static public int PointX  = 4;
        final static public int PointY  = 4;
        final static public int PointsX = 10;
        final static public int PointsY = 12;
        final static public int StartXGrass   = 2;
        final static public int StartYGrass   = 10;
        final static public int VerticalGap   = 20;
        final static public int HorizontalGap = 20;
        final static public int PlaygroundWidth  = VerticalGap*PointsX;
        final static public int PlaygroundHeight = HorizontalGap*PointsY;
        final static public int WIDTH   = PlaygroundWidth+StartXGrass*2;
        final static public int HEIGHT  = PlaygroundHeight+StartYGrass*2;
        final static public int GoalWidth  = 45;
        final static public int GoalHeight = 5;
        final static public int HoverAreaX = 30;
        final static public int HoverAreaY = 30;
    }

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
        this.hoverPoint = new Point(Size.WIDTH/2, Size.HEIGHT/2);
        this.moves = new Vector<Move>();
        this.moves.add(new Move(new Point(this.xCenter, this.yCenter),
                                new Point(this.xCenter, this.yCenter), 0));
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

    public void hover(Point p)
    {
        this.hoverPoint = p;
        this.update();
    }

    private boolean isHovered(int x, int y)
    {

        if (    (x <= hoverPoint.x + Size.HorizontalGap/2 - 1)
                &&
                (x >= hoverPoint.x - Size.HorizontalGap/2)
                &&
                (y >= hoverPoint.y - Size.VerticalGap/2)
                &&
                (y <= hoverPoint.y + Size.VerticalGap/2 - 1)
           )
        {
            return true;
        }

        return false;
    }

    private boolean isFocused(int x, int y)
    {
        Point e =  this.moves.lastElement().getEnd();

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
            Point current = m.getEnd();
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

    private Point calculateNextMove(Point p)
    {
        p.x = round(p.x - this.xStart, Size.HorizontalGap) + this.xStart + 2;
        p.y = round(p.y - this.yStart, Size.VerticalGap) + this.yStart + 2;

        return p;
    }

    private int a = 0;

    private boolean isEmpty(Point s, Point e)
    {
        System.out.println("isEmpty");
        Iterator move = this.moves.iterator();
        int i = 0;
        while(move.hasNext())
        {
            System.out.println("i = " + i++);
            Move m = (Move)move.next();
            Point c = m.getStart();
            Point b = m.getEnd();
            System.out.println("c " + c);
            System.out.println("b " + b);
            System.out.println("s " + s);
            System.out.println("e " + e);

            if ((s.x == c.x && s.y == c.y && e.x == b.x && e.y == b.y)
                    ||
                (s.x == b.x && s.y == b.y && e.x == c.x && e.y == c.y))
            {
                System.out.println("Zajety");
                return false;
            }
            else
            {
                System.out.println("Nie zajety");
            }
            //return true;
        }
        return true;
    }

    private boolean possibleMove(Point s, Point e)
    {
        int x_point = Size.PointX/2;
        int y_point = Size.PointY/2;

        // nie jest na polu
        if ((s.x > Size.PlaygroundWidth + x_point || s.x < Size.StartXGrass - x_point)
            ||
            (s.y > Size.PlaygroundHeight + y_point || s.y < Size.StartYGrass - y_point))
        {
            System.out.println("poza polem!");
            return false;
        }

        // jest zbyt daleko od aktualnego punktu
        int a = Math.abs(s.x-e.x);
            a = a*a;
        int b = Math.abs(s.y-e.y);
            b = b*b;
        double t = a+b;
        double c = Math.sqrt(t);
        double max = Math.sqrt(Size.VerticalGap*Size.VerticalGap + Size.HorizontalGap*Size.HorizontalGap);
        if (c > max)
        {
            System.out.println("Zbyt duzy skok");
            return false;
        }

        return this.isEmpty(s, e);
    }


    public void addMove(Point p)
    {
        p = this.calculateNextMove(p);
        Move m = this.moves.lastElement();
        Point s = m.getEnd();
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
