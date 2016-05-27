import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by sugan on 27/05/16.
 */
public class FastCollinearPoints {
    private LineSegment[] lineSegments;

    public FastCollinearPoints(Point[] points)     // finds all line segments containing 4 or more points
    {
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
        List<LineSegment> lineSegmentList = new ArrayList<>();
        Point[] copyPoints = Arrays.copyOf(points, points.length);
        Arrays.sort(copyPoints);
        for (int i = 0; i < points.length - 3; i++) {
            Arrays.sort(copyPoints, i + 1, points.length, points[i].slopeOrder());
//            List<Point> pointsOnLine = new ArrayList<>();
            int numberOfCollinearPoints = 1;
            double previousSlope = copyPoints[i].slopeTo(copyPoints[i + 1]);
            for (int j = i + 1; j < points.length - 2; j++) {
                double currentSlope = copyPoints[i].slopeTo(copyPoints[j]);
                if (previousSlope == currentSlope) {
//                    pointsOnLine.add(copyPoints[j]);
                    numberOfCollinearPoints++;
                } else if (numberOfCollinearPoints > 3) {
                    lineSegmentList.add(new LineSegment(copyPoints[i], copyPoints[j - 1]));
                    previousSlope = currentSlope;
                    numberOfCollinearPoints = 1;
                } else {
                    previousSlope = currentSlope;
                }
            }
        }
        lineSegments = lineSegmentList.toArray(new LineSegment[lineSegmentList.size()]);
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

