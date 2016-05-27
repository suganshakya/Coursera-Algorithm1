/**
 * Created by sugan on 27/05/16.
 */

import java.util.Comparator;

import edu.princeton.cs.algs4.StdDraw;

public class Point implements Comparable<Point> {
//    public final Comparator<Point> BY_SLOPE = new SlopeOrder();
    private final int x;
    private final int y;

    public Point(int x, int y)                         // constructs the point (x, y)
    {
        this.x = x;
        this.y = y;
    }

    public void draw()                               // draws this point
    {
        StdDraw.point(x, y);
    }

    public void drawTo(Point that)                   // draws the line segment from this point to that point
    {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    public String toString()                           // string representation
    {
        return "(" + x + ", " + y + ")";
    }

    public int compareTo(Point that)     // compare two points by y-coordinates, breaking ties by x-coordinates
    {
        if (this.y < that.y) {
            return -1;
        }
        if (this.y > that.y) {
            return 1;
        }
        if (this.x < that.x) {
            return -1;
        }
        if (this.x > that.x) {
            return 1;
        }
        return 0;
    }

    public double slopeTo(Point that)       // the slope between this point and that point
    {
        if (that.x == this.x) {
            if (that.y == this.y) {
                return Double.NEGATIVE_INFINITY;
            } else {
                return Double.POSITIVE_INFINITY;
            }
        }
        return (double) (that.y - this.y) / (that.x - this.x);
    }

    public Comparator<Point> slopeOrder()              // compare two points by slopes they make with this point
    {
        return new SlopeOrder();
    }

    private class SlopeOrder implements Comparator<Point> {
        public int compare(Point a, Point b) {
            if (Point.this.slopeTo(a) - Point.this.slopeTo(b) > 0){
                return -1;
            }
            if (Point.this.slopeTo(a) - Point.this.slopeTo(b) > 0){
                return 1;
            }
            return 0;
        }
    }

    public static void main(String[] args) {

    }
}
