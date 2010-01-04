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
        final static public int StartXGrass      = 2;
        final static public int StartYGrass      = 10;
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
        public static final Color HoverPoints = new Color(50, 50, 50);
        public static final Color Goals = new Color(255, 255, 255);
        public static final Color [] Teams = {Color.black, Color.white};
    }

    private Color bgColor = Colors.Normal;
    private int xCenter = (int)(Size.PlaygroundWidth/2) +  Size.StartXGrass;
    private int yCenter = (int)(Size.PlaygroundHeight/2) + Size.StartYGrass;

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

    private void drawLines(Graphics g)
    {
        g.setColor(Colors.Lines);
        g.drawLine(Size.StartXGrass, this.yCenter, Size.PlaygroundWidth,
                                                            this.yCenter);

    }

    private boolean isFocused(Point p)
    {
        Point e =  this.moves.lastElement().getEnd();

        if ((e.x <= p.x + Size.HoverAreaX)
                &&
            (e.x >= p.x - Size.HoverAreaX)
            &&
            (e.y >= p.y - Size.HoverAreaY)
                &&
            (e.y <= p.y + Size.HoverAreaY))
        {
            return true;
        }

        return false;
    }

    private boolean isHovered(int x, int y)
    {

        if (    (x <= hoverPoint.x + Size.HoverAreaX)
                &&
                (x >= hoverPoint.x - Size.HoverAreaX)
            &&
                (y >= hoverPoint.y - Size.HoverAreaY)
                &&
                (y <= hoverPoint.y + Size.HoverAreaY)
           )
        {
            return true;
        }

        return false;
    }

    private void drawPoints(Graphics g)
    {
        g.setColor(Colors.Points);
        int x_start = Size.StartXGrass - (int)Size.PointX/2,
            y_start = Size.StartYGrass - (int)Size.PointY/2,
            x_stop  = Size.PlaygroundWidth + x_start,
            y_stop  = Size.PlaygroundHeight + y_start;

        for (int i = x_start; i <= x_stop; i+= Size.VerticalGap)
        {
            for (int j = y_start; j <= y_stop; j+= Size.HorizontalGap)
            {
                if (this.isFocused(new Point(i, j)))
                {
                    g.setColor(Colors.HoverPoints);
                    g.fillRect(i, j, Size.PointX, Size.PointY);
                }
                else
                {
                    g.drawRect(i, j, Size.PointX, Size.PointY);
                }
                g.setColor(Colors.Points);
            }
        }
    }

    private void drawGoals(Graphics g)
    {
        g.setColor(Colors.Goals);
        g.fillRect(100, 0, Size.GoalWidth, Size.GoalHeight);
        g.fillRect(100, Size.HEIGHT - Size.GoalHeight, Size.GoalWidth, Size.GoalHeight);
    }

    private void drawGrass(Graphics g)
    {
        g.setColor(this.bgColor);
        g.fillRect(Size.StartXGrass, Size.StartYGrass, Size.PlaygroundWidth, Size.PlaygroundHeight);
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
        while(move.hasNext())
        {
            drawMove(g, (Move)move.next());
        }
    }

    public void addMove(Point p)
    {
        Move m = this.moves.lastElement();
        Point s = m.getEnd();
        this.moves.add(new Move(s, p, 0));
        update();
    }

    public void update()
    {
        Graphics g = this.getGraphics();

        drawGrass(g);
        drawLines(g);
        drawPoints(g);
        drawGoals(g);
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
