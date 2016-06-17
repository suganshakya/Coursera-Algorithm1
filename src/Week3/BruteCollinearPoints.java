import edu.princeton.cs.algs4.Quick;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by sugan on 27/05/16.
 */
public class BruteCollinearPoints {
    private LineSegment[] lineSegments;

    public BruteCollinearPoints(Point[] points)    // finds all line segments containing 4 points
    {
        if (points == null) {
            throw new NullPointerException();
        }
        if (Arrays.asList(points).contains(null)) {
            throw new NullPointerException();
        }
        for (int i = 0; i < points.length - 1; ++i) {
            for (int j = i + 1; j < points.length; ++j) {
                if (points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException();
                }
            }
        }
        ArrayList<LineSegment> lineSegmentList = new ArrayList<>();
        Point[] copyPoints = Arrays.copyOf(points, points.length);
        Quick.sort(copyPoints);
        for (int i = 0; i < copyPoints.length - 3; i++) {
            for (int j = i + 1; j < copyPoints.length - 2; j++) {
                for (int k = j + 1; k < copyPoints.length - 1; k++) {
                    for (int l = k + 1; l < copyPoints.length; l++) {
                        if (doesLieOnSameLine(copyPoints[i], copyPoints[j], copyPoints[k], copyPoints[l])) {
                            lineSegmentList.add(new LineSegment(copyPoints[i], copyPoints[l]));
                        }
                    }
                }
            }
        }
        lineSegments = lineSegmentList.toArray(new LineSegment[lineSegmentList.size()]);
    }


    private boolean doesLieOnSameLine(Point point0, Point point1, Point point2, Point point3) {
        if (point0.slopeTo(point1) == point0.slopeTo(point2) && point0.slopeTo(point1) == point0.slopeTo(point3))
            return true;
        return false;
    }

    public int numberOfSegments()        // the number of line segments
    {
        return lineSegments.length;
    }

    public LineSegment[] segments()                // the line segments
    {
        return Arrays.copyOf(lineSegments, lineSegments.length);
    }

}