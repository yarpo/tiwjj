/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tiwjj.playground;

/**
 *
 * @author yarpo
 */
import java.awt.Point;

class FocusedPointDimension {

        public int xCenter;
        public int xRight;
        public int xLeft;
        public int yCenter;
        public int yTop;
        public int yBottom;

        public FocusedPointDimension(int x, int y)
        {
            x -=  Size.OffsetX;
            y -=  Size.OffsetY;

            this.xCenter = x;
            this.xLeft   = x - Size.HorizontalGap;
            this.xRight  = x + Size.HorizontalGap;
            this.yCenter = y;
            this.yBottom = y + Size.VerticalGap;
            this.yTop    = y - Size.VerticalGap;
        }

        public FocusedPointDimension(Point p)
        {
            this(p.x, p.y);
        }
    }