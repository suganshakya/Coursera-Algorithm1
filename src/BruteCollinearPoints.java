import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by sugan on 27/05/16.
 */
public class BruteCollinearPoints {
    private int numberOfSegment;
    private LineSegment[] lineSegments;

    public BruteCollinearPoints(Point[] points)    // finds all line segments containing 4 points
    {
        if (points == null) {
            throw new NullPointerException();
        }
        if (Arrays.asList(points).contains(null)) {
            throw new NullPointerException();
        }
        for(int i= 0; i< points.length-1; ++i){
            for(int j = i+1; j< points.length; ++j){
                if(points[i] == points[j]){
                    throw new IllegalArgumentException();
                }
            }
        }
        ArrayList<LineSegment> lineSegmentList = new ArrayList<LineSegment>();
        Arrays.sort(points);
        for(int i=0; i< points.length-3; i++){
            for(int j=i+1; j< points.length-2; j++){
                for(int k= j+1; k< points.length-1; k++){
                    for (int l= k+1; l< points.length; l++){
                        if(doesLieOnSameLine(points[i], points[j], points[k], points[l])){
                            lineSegmentList.add(new LineSegment(points[i], points[l]));
                        }
                    }
                }
            }
        }
        lineSegments = (LineSegment[]) lineSegmentList.toArray();
    }


    private boolean doesLieOnSameLine(Point point0, Point point1, Point point2, Point point3) {
        if(point0.slopeTo(point1) == point0.slopeTo(point2) && point0.slopeTo(point1) == point0.slopeTo(point3) )
            return true;
        return false;
    }

    public int numberOfSegments()        // the number of line segments
    {
        return numberOfSegment;
    }

    public LineSegment[] segments()                // the line segments
    {
        return lineSegments;
    }

}
