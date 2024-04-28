package Geometry;

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
    public LineSegment(Point a, Point b) throws GeometricException
    {
        this.point1 = new Point(a);
        this.point2 = new Point(b);
        this.line = new Line(this.point1, this.point2);
        if (a.equals(b))
			throw new GeometricException(ERROR_MESSAGE + " can't create a line segment with two equal points.");
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
        VirtualPoint intersection = this.line.intersection(that.line);

        // checks if a point is exactly on one of the bounds of the segment
        if (isPointOnBounds(intersection) || that.isPointOnBounds(intersection))
            return false;

        return this.contains(intersection) && that.contains(intersection);
    }

    public boolean intersectsInclusive(LineSegment that)
    {
        if (this.line.isParalel(that.line))
            return false;
            // return doParalelSegmentsCollide(that);
        VirtualPoint intersection = this.line.intersection(that.line);

        // checks if a point is exactly on one of the bounds of the segment
        if (isPointOnBounds(intersection) || that.isPointOnBounds(intersection))
            return true;

        return this.contains(intersection) && that.contains(intersection);
    }

	public boolean intersects(Line that)
	{
		if (this.line.isParalel(that))
			return false;

		VirtualPoint intersection = this.line.intersection(that);

		if (isPointOnBounds(intersection))
			return false;

		return contains(intersection);
	}

	public boolean intersectsInclusive(Line that)
	{
		if (this.line.isParalel(that))
			return false;

		VirtualPoint intersection = this.line.intersection(that);

		if (isPointOnBounds(intersection))
			return true;

		return contains(intersection);
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
            return this.contains(that.point1) || this.contains(that.point2) ||
                    that.contains(this.point1) || that.contains(this.point2);
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
     * Checks if the point is contained in the line segment
     * @param pointOnSegment a collinear point to check if is withing the line segment
     * @return if the point is contained within the line segment
     */
    public boolean contains(VirtualPoint pointOnSegment)
    {
		if (!this.line().isCollinear(pointOnSegment))
			return false;

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
    public Point firstPoint() { return this.point1; }

    /**
     * Accessor method to return the second bound of the segmend
     */
    public Point secondPoint() { return this.point2; }

    /**
     * Accessor method to return the line containing the segment
     */
    public Line line() { return this.line; }
}
