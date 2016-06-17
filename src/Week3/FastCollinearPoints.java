import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Sugan.
 */
public class FastCollinearPoints {
    private List<MyLineSegment> lineSegmentList;
    private LineSegment[] lineSegments;

    private class MyLineSegment {
        private Point p;
        private Point q;

        public MyLineSegment(Point p, Point q) {
            if (p == null || q == null) {
                throw new NullPointerException("argument is null");
            }
            this.p = p;
            this.q = q;
        }

        public boolean isEqual(MyLineSegment ls) {
            if (ls == null) {
                return false;
            }
            return ((this.p == ls.p && this.q == ls.q) || (this.p == ls.q && this.q == ls.p));
        }
    }

    public FastCollinearPoints(Point[] points)     // finds all gitline segmentsMap containing 4 or more points
    {
        checkValidity(points);
        Point[] copyPoints = Arrays.copyOf(points, points.length);
        lineSegmentList = new ArrayList<>();

        for (Point pointP : points) {
            Arrays.sort(copyPoints, pointP.slopeOrder());
            double previousSlope = Double.NEGATIVE_INFINITY;    // slope to own
            List<Point> pointList = new ArrayList<>();
            boolean isOnLine = false;
            int currentNumberOfPoints = 1;

            for (int q = 1; q < points.length; q++) {
                double slope = pointP.slopeTo(copyPoints[q]);

                if (slope == previousSlope) {
                    if (!isOnLine) {  // first point whose slope match
                        pointList.add(copyPoints[q - 1]);
                    }
                    pointList.add(copyPoints[q]);
                    isOnLine = true;
                    currentNumberOfPoints++;
                } else {
                    if (isOnLine && currentNumberOfPoints >= 3) {
                        pointList.add(pointP);
                        addToLineSegment(pointList);
                    }
                    pointList.clear();
                    isOnLine = false;
                    currentNumberOfPoints = 1;
                    previousSlope = slope;
                }
                if (q == points.length - 1 && currentNumberOfPoints >= 3) {
                    pointList.add(pointP);
                    addToLineSegment(pointList);
                    isOnLine = false;
                }
            }
        }
    }

    private void addToLineSegment(List<Point> pList) {
        Point start, end;
        start = pList.get(0);
        end = start;
        for (Point p : pList.subList(1, pList.size())) {
            if (p.compareTo(start) < 0) {
                start = p;
            }
            if (p.compareTo(end) > 0) {
                end = p;
            }
        }
        MyLineSegment mls = new MyLineSegment(start, end);
        for (MyLineSegment lineSegment : lineSegmentList) {
            if (lineSegment.isEqual(mls)) {
                return;     // already added
            }
        }
        MyLineSegment ls = new MyLineSegment(start, end);
        lineSegmentList.add(ls);
    }

    public int numberOfSegments()        // the number of line segmentsMap
    {
        return lineSegmentList.size();
    }

    public LineSegment[] segments()                // the line segmentsMap
    {
        lineSegments = new LineSegment[lineSegmentList.size()];
        int i = 0;
        for (MyLineSegment mls : lineSegmentList) {
            lineSegments[i++] = new LineSegment(mls.p, mls.q);
        }
        return Arrays.copyOf(lineSegments, lineSegments.length);
//        return lineSegments;
    }

    private void checkValidity(Point[] points) {
        if (points == null) {
            throw new NullPointerException();
        }
        if (Arrays.asList(points).contains(null)) {
            throw new NullPointerException();
        }
        for (int i = 0; i < points.length - 1; i++) {
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException();
                }
            }
        }
    }

    public static void main(String[] args) {

        // read the N points from a file
        In in = new In(args[0]);
        int N = in.readInt();
        Point[] points = new Point[N];
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.show(0);
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
    }
}
