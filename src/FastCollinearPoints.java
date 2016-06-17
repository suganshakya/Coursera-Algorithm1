import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by sugan on 27/05/16.
 */
public class FastCollinearPoints {
    private List<LineSegment> lineSegmentList;
    private HashMap<Double, List<Point>> map;

    public FastCollinearPoints(Point[] points)     // finds all gitline segmentsMap containing 4 or more points
    {
        checkValidity(points);
        Point[] copyPoints = Arrays.copyOf(points, points.length);
        lineSegmentList = new ArrayList<>();
        map = new HashMap<>();

        for (Point pointP : points) {
            Arrays.sort(copyPoints, pointP.slopeOrder());
            double previousSlope = Double.NEGATIVE_INFINITY;    // slope to own
            List<Point> temp = new ArrayList<>();
            boolean isOnLine = false;
            int currentNumberOfPoints = 1;
            for (int q = 1; q < points.length; q++) {
                double slope = pointP.slopeTo(copyPoints[q]);

                if (slope == previousSlope) {
                    if (isOnLine == false) {  // first point whose slope match
                        temp.add(copyPoints[q - 1]);
                    }
                    temp.add(copyPoints[q]);
                    isOnLine = true;
                    currentNumberOfPoints++;
                } else {
                    if (isOnLine && currentNumberOfPoints >= 3) {
                        temp.add(pointP);
                        addToMap(previousSlope, pointP, new ArrayList<Point>(temp));
                        isOnLine = false;
                    } else {
                        temp.clear();
                        isOnLine = false;
                    }
                    currentNumberOfPoints = 1;
                    previousSlope = slope;
//                    onLine = false;
                }
                if (q == points.length - 1 && currentNumberOfPoints >= 3) {
                    temp.add(pointP);
                    addToMap(slope, pointP, new ArrayList<Point>(temp));
                    isOnLine = false;
                }
            }
        }
    }

    public int numberOfSegments()        // the number of line segmentsMap
    {
        return lineSegmentList.size();
    }

    public LineSegment[] segments()                // the line segmentsMap
    {
        return lineSegmentList.toArray(new LineSegment[lineSegmentList.size()]);
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

    private void addToMap(Double slope, Point refPoint, List<Point> pList) {
        if (map.size() == 0) {  // empty no need to check duplicate
            addToLineSegment(slope, pList);
            return;
        }
        if (!map.containsKey(slope)) {   // no line with given slope, no need to check duplicate
            addToLineSegment(slope, pList);
            return;
        }
        // why is existing Points empty for repeated line
        List<Point> existingPoints = (List<Point>) map.get(slope);
        if (existingPoints.contains(refPoint)) {
            return;
        }
        addToLineSegment(slope, pList);
    }

    private void addToLineSegment(Double slope, List<Point> pList) {
        map.put(slope, pList);
        // add new line to line segment
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
        lineSegmentList.add(new LineSegment(start, end));
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
