/**
 * Represents an immutable segment within a two dimensional line
 *
 * @author Diogo Fonseca a79858
 * @version 23/02/2024
 * 
 * @inv point1 is the first point defining a bound within the line
 * @inv point2 is the second point defining a bound within the line
 * @inv line is the line the segment belongs to
 */
public class LineSegment
{
    private static final String ERROR_MESSAGE = "Segmento:vi";
    private Point point1, point2;
    private Line line;

    /**
     * Initializes a line segment
     * @param a the first bound of the segment
     * @param b the second bound of the segment
     * @pre a != b
     * @post getFirstPoint() = a
     * @post getSecondPoint() = b
     */
    public LineSegment(Point a, Point b)
    {
        this.point1 = new Point(a);
        this.point2 = new Point(b);
        this.line = new Line(this.point1, this.point2);
        if (a.equals(b))
            Error.terminateProgram(ERROR_MESSAGE);
    }

    /**
     * Checks if a segment intersects the current segment
     * @param that the segment to check intersection with
     * @return if the segment intersects the current one
     */
    public boolean intersects(LineSegment that)
    {
        if (this.line.isParalel(that.line))
            return false;
            // return doParalelSegmentsCollide(that);
        VirtualPoint intersection = this.line.calcIntersect(that.line);

        // checks if a point is exactly on one of the bounds of the segment
        if (isPointOnBounds(intersection) || that.isPointOnBounds(intersection))
            return false;

        return this.containsPointOnSegment(intersection) && that.containsPointOnSegment(intersection);
    }

    /**
     * checks if two paralel segments overlap
     * @param that the other paralel segment
     * @return if the two paralel segments overlap
     */
    @SuppressWarnings("unused")
    private boolean doParalelSegmentsOverlap(LineSegment that)
    {
        if (this.line.equals(that.line))
            return this.containsPointOnSegment(that.point1) || this.containsPointOnSegment(that.point2) ||
                    that.containsPointOnSegment(this.point1) || that.containsPointOnSegment(this.point2);
        return false;
    }

    /**
     * checks if a point is exactly at the start or end of the segment
     * @param point the point to check if it is on one of the bounds of the segment
     * @return if the point is on one of the bounds
     */
    private boolean isPointOnBounds(VirtualPoint point)
    {
        return point.equals(this.point1) || point.equals(this.point2);
    }

    /**
     * Checks if the point (that is already collinear with the segment) is contained in the line segment
     * @param pointOnSegment a collinear point to check if is withing the line segment
     * @pre pointOnSegment must be collinear with the current segment
     * @return if the point is contained within the line segment
     */
    private boolean containsPointOnSegment(VirtualPoint pointOnSegment)
    {
        double minX = Math.min(this.point1.X(), this.point2.X());
        double maxX = Math.max(this.point1.X(), this.point2.X());
        double minY = Math.min(this.point1.Y(), this.point2.Y());
        double maxY = Math.max(this.point1.Y(), this.point2.Y());
        boolean containsX = (pointOnSegment.X() > minX && pointOnSegment.X() < maxX) ||
                            (MathUtil.areEqual(pointOnSegment.X(), minX) ||
                            MathUtil.areEqual(pointOnSegment.X(), maxX));
        boolean containsY = (pointOnSegment.Y() > minY && pointOnSegment.Y() < maxY) ||
                            (MathUtil.areEqual(pointOnSegment.Y(), minY) ||
                            MathUtil.areEqual(pointOnSegment.Y(), maxY));
        return containsX && containsY;
    }

    /**
     * Calculates the length of the segment
     * @return returns the length of the segment
     */
    public double length()
    {
        return point1.dist(point2);
    }

    /**
     * Accessor method to return the first bound of the segment
     */
    public Point getFirstPoint() { return this.point1; }

    /**
     * Accessor method to return the second bound of the segmend
     */
    public Point getSecondPoint() { return this.point2; }

    /**
     * Accessor method to return the line containing the segment
     */
    public Line line() { return this.line; }
}