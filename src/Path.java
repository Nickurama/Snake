/**
 * Represents a path on two dimensions
 * 
 * @author Diogo Fonseca a79858
 * @version 23/02/2024
 * 
 * @inv segments are the line segments that the path describes
 * @inv points are the points the path goes over
 */
public class Path 
{
    private final String ERROR_MESSAGE = "Trajetoria:vi";
    private LineSegment[] segments;
    private Point[] points;

    /**
     * Initializes a path
     * @param points the points the path goes over
     * @pre two points in a row cannot be the same point
     */
    public Path(Point[] points)
    {
        if (points.length < 2)
            Error.terminateProgram(ERROR_MESSAGE);
        this.points = Point.copyArray(points);
        segments = generateSegments(points);
    }

    /**
     * generates the line segments the points describe
     * @param points the points that describe the path
     * @return the line segments the points describe
     */
    private LineSegment[] generateSegments(Point[] points)
    {
        LineSegment[] segments = new LineSegment[points.length - 1];
        for (int i = 1; i < points.length; i++)
            segments[i - 1] = new LineSegment(points[i - 1], points[i]);
        return segments;
    }

    /**
     * calculates the distance of the path
     * @return the distance of the path
     */
    public double dist()
    {
        double result = 0;
        for (int i = 1; i < points.length; i++)
            result += points[i - 1].dist(points[i]);
        return result;
    }
    
    /**
     * checks if a polygon intersects the path
     * @param poly the polygon to check intersection with
     * @return if the polygon intersects the path
     */
    public boolean intercepts(Poligono poly)
    {
        for (LineSegment segment : segments)
            if (poly.intersects(segment))
                return true;
        return false;
    }

    /**
     * checks if any of the polygons intersects the array
     * @param polygons the polygons to check for intersection
     * @return if any of the polygons intersect the array
     */
    public boolean intercepts(Poligono[] polygons)
    {
        for(Poligono poly : polygons)
            if (this.intercepts(poly))
                return true;
        return false;
    }

}
