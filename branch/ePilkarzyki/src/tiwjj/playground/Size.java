/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tiwjj.playground;

/**
 *
 * @author yarpo
 */
public class Size {
    final static public int PointX  = 4;
    final static public int PointY  = 4;
    final static public int OffsetX = (int)(PointX/2);
    final static public int OffsetY = (int)(PointY/2);
    final static public int PointsX = 10;
    final static public int PointsY = 12;
    final static public int StartXGrass   = 2;
    final static public int StartYGrass   = 10;
    final static public int VerticalGap   = 20;
    final static public int HorizontalGap = 20;
    final static public int PlaygroundWidth  = HorizontalGap*PointsX;
    final static public int PlaygroundHeight = VerticalGap*PointsY;
    final static public int WIDTH   = PlaygroundWidth+StartXGrass*2;
    final static public int HEIGHT  = PlaygroundHeight+StartYGrass*2;
    final static public int GoalWidth  = 45;
    final static public int GoalHeight = 5;
    final static public int HoverAreaX = 30;
    final static public int HoverAreaY = 30;
    final static public double MaxDistance = Math.sqrt(Size.VerticalGap*Size.VerticalGap + Size.HorizontalGap*Size.HorizontalGap);
}
