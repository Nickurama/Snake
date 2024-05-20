package Geometry;

/**
 * Creates a box of bounds, useful for knowing the bounds of a bunch of points
 * Such as the minimum coordinates and maximum coordinates
 * 
 * @author Diogo Fonseca a79858
 * @version 29/04/2024
 * 
 * @inv min is the lower (left) point of the bounding box
 * @inv max is the upper (right) point of the bounding box
 */
public class BoundingBox
{
    private Point min, max;

    /**
     * Initializes a bounding box
     * @param points the points to be bounded
	 * @pre points > 0
     */
    public BoundingBox(Point[] points) throws GeometricException
    {
        if (points.length == 0)
            throw new GeometricException("BoundingBox error: points should be greater than 0");
        
		initialize(points);
    }

	/**
	 * Initializes a bounding box from a polygon
	 * @param poly the polygon to bound
	 */
	public BoundingBox(Polygon poly)
	{
		initialize(poly.vertices());
	}

	/**
	 * Initializes the bounding box with an array of points
	 * @param points
	 */
	private void initialize(Point[] points)
	{
        this.min = points[0];
        this.max = points[0];

        for (Point p : points)
            addPoint(p);
	}

    /**
     * Adds a point to the box, ensuring that the box still bounds all points
     * @param p the point to be added
     */
    private void addPoint(Point p)
    {
		try
		{
			this.min = new Point(Math.min(this.min.X(), p.X()), Math.min(this.min.Y(), p.Y()));
			this.max = new Point(Math.max(this.max.X(), p.X()), Math.max(this.max.Y(), p.Y()));
		}
		catch (GeometricException e)
		{
			throw new IllegalStateException("Should never happen: error making a point from a point: " + e.getMessage());
		}
    }

    /**
     * Checks if two bounding boxes intercept eachother
     * @param that the bounding box to check interception with
     * @return if the two bounding boxes intercept
     */
    public boolean intersects(BoundingBox that)
    {
        boolean interceptsX = !(MathUtil.isLessOrEqualThan(this.max.X(), that.min.X()) ||
                                MathUtil.isGreaterOrEqualThan(this.min.X(), that.max.X()));
        boolean interceptsY = !(MathUtil.isLessOrEqualThan(this.max.Y(), that.min.Y()) ||
                                MathUtil.isGreaterOrEqualThan(this.min.Y(), that.max.Y()));

        return interceptsX && interceptsY;
    }

	/**
	 * Returns the minimum xy coordinates
	 * @return The point containing the minimum xy coordinates
	 */
	public Point minPoint() { return this.min; }

	/**
	 * Returns the maximum xy coordinates
	 * @return The point containing the maximum xy coordinates
	 */
	public Point maxPoint() { return this.max; }
}
