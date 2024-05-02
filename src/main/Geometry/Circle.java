package Geometry;

public class Circle implements IGeometricShape<Circle>
{
	private Point center;
	private double radius;

	public Circle(Point center, double radius) throws GeometricException
	{
		validateCircle(center, radius);
		this.center = center;
		this.radius = radius;
	}

	public Circle(Circle toCopy) throws GeometricException
	{
		this(toCopy.center, toCopy.radius);
	}

	private void validateCircle(Point center, double radius) throws GeometricException
	{
		if (radius <= 0)
			throw new GeometricException("Circle radius cannot be equal to or less than 0");
		if (radius > center.X())
			throw new GeometricException("Circle radius cannot go into negative X coordinates");
		if (radius > center.Y())
			throw new GeometricException("Circle radius cannot go into negative Y coordinates");
	}

	public double perimeter()
	{
		return 2 * Math.PI * radius;
	}

	public Circle rotate(double angle) throws GeometricException
	{
		return new Circle(this);
	}

	public Circle rotate(double angle, VirtualPoint anchor) throws GeometricException
	{
		return new Circle(this.center.rotate(angle, anchor), this.radius);
	}

	public Circle rotateDegrees(double angle, VirtualPoint anchor) throws GeometricException
	{
		return rotate(Math.toRadians(angle), anchor);
	}

	public Circle rotateDegrees(double angle) throws GeometricException
	{
		return rotate(Math.toRadians(angle));
	}

	public Circle translate(Vector vector) throws GeometricException
	{
		return new Circle(this.center.translate(vector), this.radius);
	}

	public Circle moveCentroid(Point newCentroid) throws GeometricException
	{
		return new Circle(newCentroid, this.radius);
	}

	public Point getCentroid()
	{
		return this.center;
	}

	public double radius()
	{
		return this.radius;
	}

	public boolean intersects(IGeometricShape<?> that)
	{
		if (that instanceof Circle)
			return intersects((Circle) that);
		else if (that instanceof Polygon)
			return intersects((Polygon) that);
		else
			throw new UnsupportedOperationException(this.getClass() + " doesn't have intersect method for " + that.getClass());
	}

	public boolean intersects(Circle that)
	{
		double distance = this.center.dist(that.center);
		if (this.contains(that))
			return false;
		return distance < (this.radius + that.radius);
	}

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

	public boolean intersects(Polygon that)
	{
		for (LineSegment side : that.sides())
			if (this.intersects(side))
				return true;
		return false;
	}

	public boolean intersectsInclusive(IGeometricShape<?> that)
	{
		if (that instanceof Circle)
			return intersectsInclusive((Circle) that);
		else if (that instanceof Polygon)
			return intersectsInclusive((Polygon) that);
		else
			throw new UnsupportedOperationException(this.getClass() + " doesn't have intersect method for " + that.getClass());
	}

	public boolean intersectsInclusive(Circle that)
	{
		double distance = this.center.dist(that.center);
		if (this.contains(that))
			return false;
		return MathUtil.isLessOrEqualThan(distance, this.radius + that.radius);
	}

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

	public boolean intersectsInclusive(Polygon that)
	{
		for (LineSegment side : that.sides())
			if (this.intersectsInclusive(side))
				return true;
		return false;
	}

	public boolean contains(IGeometricShape<?> that)
	{
		if (that instanceof Circle)
			return contains((Circle) that);
		else if (that instanceof Polygon)
			return contains((Polygon) that);
		else
			throw new UnsupportedOperationException(this.getClass() + " doesn't have contain method for " + that.getClass());
	}

	public boolean contains(Circle that)
	{
		double distance = this.center.dist(that.center);
		double maxDistance = this.radius - that.radius;
		return MathUtil.isLessOrEqualThan(distance, maxDistance);
	}

	public boolean contains(Point that)
	{
		return MathUtil.isLessOrEqualThan(this.center.dist(that), this.radius);
	}

	public boolean containsExclusive(Point that)
	{
		return this.center.dist(that) < this.radius;
	}

	public boolean contains(LineSegment that)
	{
		return this.contains(that.firstPoint()) && this.contains(that.firstPoint());
	}

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

	private boolean isCircleEqual(Circle that)
	{
		return this.center.equals(that.center) && MathUtil.areEqual(this.radius, that.radius);
	}

	public String toString()
	{
		return "Circle: center=" + this.center.toString() + " radius=" + this.radius;
	}
}
