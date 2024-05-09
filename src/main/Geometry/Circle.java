package Geometry;

/**
 * Represents an immutable circle
 * 
 * @author Diogo Fonseca a79858
 * @version 02/05/2024
 * 
 * @inv center is the center of the circle
 * @inv radius is the radius of the circle
 */
public class Circle implements IGeometricShape<Circle>
{
	private Point center;
	private double radius;

	/**
	 * Initializes a circle
	 * @param center the center of the circle
	 * @param radius the radius of the circle
	 * @throws GeometricException if the center and radius don't define a valid circle
	 */
	public Circle(Point center, double radius) throws GeometricException
	{
		validateCircle(center, radius);
		initialize(center, radius);
	}

	private void initialize(Point center, double radius)
	{
		this.center = center;
		this.radius = radius;
	}

	/**
	 * Copies a circle
	 * @param toCopy the circle to copy
	 */
	public Circle(Circle toCopy)
	{
		initialize(center, radius);
	}

	/**
	 * Checks if a circle is valid
	 * @param center the center of the circle to check
	 * @param radius the radius of the circle to check
	 * @throws GeometricException if the circle is not valid
	 */
	private void validateCircle(Point center, double radius) throws GeometricException
	{
		if (radius <= 0)
			throw new GeometricException("Circle radius cannot be equal to or less than 0");
		if (radius > center.X())
			throw new GeometricException("Circle radius cannot go into negative X coordinates");
		if (radius > center.Y())
			throw new GeometricException("Circle radius cannot go into negative Y coordinates");
	}

	@Override
	public double perimeter()
	{
		return 2 * Math.PI * radius;
	}

	@Override
	public Circle rotate(double angle) throws GeometricException
	{
		return new Circle(this);
	}

	@Override
	public Circle rotate(double angle, VirtualPoint anchor) throws GeometricException
	{
		return new Circle(this.center.rotate(angle, anchor), this.radius);
	}

	@Override
	public Circle rotateDegrees(double angle, VirtualPoint anchor) throws GeometricException
	{
		return rotate(Math.toRadians(angle), anchor);
	}

	@Override
	public Circle rotateDegrees(double angle) throws GeometricException
	{
		return rotate(Math.toRadians(angle));
	}

	@Override
	public Circle translate(Vector vector) throws GeometricException
	{
		return new Circle(this.center.translate(vector), this.radius);
	}

	@Override
	public Circle moveCentroid(Point newCentroid) throws GeometricException
	{
		return new Circle(newCentroid, this.radius);
	}

	@Override
	public Point getCentroid()
	{
		return this.center;
	}

	/**
	 * Radius of the circle
	 * @return the radius of the circle
	 */
	public double radius()
	{
		return this.radius;
	}

	@Override
	public boolean intersects(IGeometricShape<?> that)
	{
		if (that instanceof Circle)
			return intersects((Circle) that);
		else if (that instanceof Polygon)
			return intersects((Polygon) that);
		else
			throw new UnsupportedOperationException(this.getClass() + " doesn't have intersect method for " + that.getClass());
	}

	/**
	 * Checks if the circle intersects with a circle
	 * @param that the circle to check intersection with
	 * @return if the two circles intersect
	 */
	public boolean intersects(Circle that)
	{
		double distance = this.center.dist(that.center);
		if (this.contains(that))
			return false;
		return distance < (this.radius + that.radius);
	}

	/**
	 * Checks if the circle intersects with a line segment
	 * @param that the segment to check intersection with
	 * @return if the circle is intersected by the line segment
	 */
	public boolean intersects(LineSegment that)
	{
		boolean firstOnCircle = this.containsExclusive(that.firstPoint());
		boolean secondOnCircle = this.containsExclusive(that.secondPoint());
		if (firstOnCircle != secondOnCircle)
			return true;
		else if (firstOnCircle && secondOnCircle)
			return false;

		Line perpendicular = that.line().generatePerpendicular(this.center);
		VirtualPoint intersection = that.line().intersection(perpendicular);
		if (!that.contains(intersection))
			return false;
		return this.center.dist(intersection) < this.radius;
	}

	/**
	 * Checks if the circle intersects with a polygon
	 * @param that the polygon to check intersection with
	 * @return if the circle is intersected by the polygon
	 */
	public boolean intersects(Polygon that)
	{
		for (LineSegment side : that.sides())
			if (this.intersects(side))
				return true;
		return false;
	}

	@Override
	public boolean intersectsInclusive(IGeometricShape<?> that)
	{
		if (that instanceof Circle)
			return intersectsInclusive((Circle) that);
		else if (that instanceof Polygon)
			return intersectsInclusive((Polygon) that);
		else
			throw new UnsupportedOperationException(this.getClass() + " doesn't have intersect method for " + that.getClass());
	}

	/**
	 * Checks if the circle intersects with a circle (inclusive)
	 * @param that the circle to check intersection with
	 * @return if the two circles intersect (inclusive)
	 */
	public boolean intersectsInclusive(Circle that)
	{
		double distance = this.center.dist(that.center);
		if (this.contains(that))
			return false;
		return MathUtil.isLessOrEqualThan(distance, this.radius + that.radius);
	}

	/**
	 * Checks if the circle intersects with a line segment (inclusive)
	 * @param that the segment to check intersection with
	 * @return if the circle is intersected by the line segment (inclusive)
	 */
	public boolean intersectsInclusive(LineSegment that)
	{
		boolean firstOnCircle = this.containsExclusive(that.firstPoint());
		boolean secondOnCircle = this.containsExclusive(that.secondPoint());
		if (firstOnCircle != secondOnCircle)
			return true;
		else if (firstOnCircle && secondOnCircle)
			return false;

		Line perpendicular = that.line().generatePerpendicular(this.center);
		VirtualPoint intersection = that.line().intersection(perpendicular);
		if (!that.contains(intersection))
			return false;
		return MathUtil.isLessOrEqualThan(this.center.dist(intersection), this.radius);
	}

	/**
	 * Checks if the circle intersects with a polygon (inclusive)
	 * @param that the polygon to check intersection with
	 * @return if the circle is intersected by the polygon (inclusive)
	 */
	public boolean intersectsInclusive(Polygon that)
	{
		for (LineSegment side : that.sides())
			if (this.intersectsInclusive(side))
				return true;
		return false;
	}

	@Override
	public boolean contains(IGeometricShape<?> that)
	{
		if (that instanceof Circle)
			return contains((Circle) that);
		else if (that instanceof Polygon)
			return contains((Polygon) that);
		else
			throw new UnsupportedOperationException(this.getClass() + " doesn't have contain method for " + that.getClass());
	}

	/**
	 * Checks if the circle contains a circle
	 * @param that the circle to check if is contained within this circle
	 * @return if the circle is contained by this circle
	 */
	public boolean contains(Circle that)
	{
		double distance = this.center.dist(that.center);
		double maxDistance = this.radius - that.radius;
		return MathUtil.isLessOrEqualThan(distance, maxDistance);
	}

	/**
	 * Checks if the circle contains a point
	 * @param that the point to check if is contained within this circle
	 * @return if the point is contained by this circle
	 */
	public boolean contains(Point that)
	{
		return MathUtil.isLessOrEqualThan(this.center.dist(that), this.radius);
	}

	/**
	 * Checks if the circle contains a point (exclusive)
	 * @param that the point to check if is contained within this circle
	 * @return if the point is contained by this circle (exclusive)
	 */
	public boolean containsExclusive(Point that)
	{
		return this.center.dist(that) < this.radius;
	}

	/**
	 * Checks if the circle contains a line segment
	 * @param that the line segment to check if is contained within this circle
	 * @return if the line segment is contained by this circle
	 */
	public boolean contains(LineSegment that)
	{
		return this.contains(that.firstPoint()) && this.contains(that.firstPoint());
	}

	/**
	 * Checks if the circle contains a polygon
	 * @param that the polygon to check if is contained within this circle
	 * @return if the polygon is contained by this circle
	 */
	public boolean contains(Polygon that)
	{
		for (Point p : that.vertices())
			if (!this.contains(p))
				return false;
		return true;
	}

	@Override
	public final boolean equals(Object other)
	{
		if (other == this) return true;
		if (other == null) return false;
		if (getClass() != other.getClass()) return false; // if not Polygon or child of Polygon

		Circle that = (Circle) other;
		return this.isCircleEqual(that);
	}

    @Override
    public int hashCode()
    {
        throw new UnsupportedOperationException();
    }

	/**
	 * Checks if a circle is equal to this circle
	 * @param that the circle to compare with
	 * @return if the two circles are equivalent
	 */
	private boolean isCircleEqual(Circle that)
	{
		return this.center.equals(that.center) && MathUtil.areEqual(this.radius, that.radius);
	}

	@Override
	public String toString()
	{
		return "Circle: center=" + this.center.toString() + " radius=" + this.radius;
	}
}
