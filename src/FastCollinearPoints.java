import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by sugan on 27/05/16.
 */
public class FastCollinearPoints {
    private int numberOfSegments;
    private LineSegment[] lineSegments;
    private boolean[] status;

    public FastCollinearPoints(Point[] points)     // finds all line segments containing 4 or more points
    {
        if (points == null) {
            throw new NullPointerException();
        }
        if (Arrays.asList(points).contains(null)) {
            throw new NullPointerException();
        }
        for (int i = 0; i < points.length - 1; ++i) {
            for (int j = i + 1; j < points.length; ++j) {
                if (points[i] == points[j]) {
                    throw new IllegalArgumentException();
                }
            }
        }
        ArrayList<LineSegment> lineSegmentList = new ArrayList<LineSegment>();
        Point[] temp;
        Arrays.sort(points);
        status = new boolean[points.length];

        for (int i = 0; i < points.length - 3; i++) {
//            temp = Arrays.copyOfRange(points, i+1, points.length);
            Arrays.sort(points, i+1, points.length, points[i].slopeOrder());
            for (int j = i + 1; j < points.length - 2; j++) {
                for (int k = j + 1; j < points.length-1; j++) {
                    for (int l = k + 1; j < points.length; j++) {

                        if (points[i].slopeOrder().compare(points[j], points[k]) == 0) {

                        }
                    }
                }
            }

        public int numberOfSegments ()        // the number of line segments
        {
            return numberOfSegments;
        }
        public LineSegment[] segments ()                // the line segments
        {
            return lineSegments;
        }
    }
