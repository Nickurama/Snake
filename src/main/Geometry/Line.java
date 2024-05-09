package Geometry;

/**
 * Represents an immutable line described in euclidean space.
 * Uses the general equation for a line in 2D space: ax + by + c = 0
 * 
 * @author Diogo Fonseca a79858
 * @version 28/04/2024
 * 
 * @inv coefficientX is immutable and set when line is created (represents the "a" coefficient)
 * @inv coefficientY is immutable and set when line is created (represents the "b" coefficient)
 * @inv constant is immutable and set when line is created (represents the "c" constant)
 * @inv containedPoint is a point contained in the line
 */
public class Line
{
    private final String ERROR_MESSAGE = "Reta:vi";
    private double coefficientX;
    private double coefficientY;
    private double constant;
    private Point containedPoint;

	/**
	 * Initializes a line
	 * @param coefficientX the coefficient of the x variable
	 * @param coefficientY the coefficient of the y variable
	 * @param containedPoint a point contained in the line
	 * @throws GeometricException if both coefficients are 0
	 */
	public Line(double coefficientX, double coefficientY, Point containedPoint) throws GeometricException
	{
		if (MathUtil.areEqual(coefficientX, 0) && MathUtil.areEqual(coefficientY, 0))
			throw new GeometricException("Cannot create a a line with both coefficients set to 0.");

		this.coefficientX = coefficientX;
		this.coefficientY = coefficientY;
		this.containedPoint = containedPoint;

		double firstY = coefficientX + containedPoint.Y();
		double firstX = containedPoint.X() - coefficientY;
		this.constant = firstX * containedPoint.Y() - containedPoint.X() * firstY;
	}

    /**
     * Initializes a line 
     * @param a first point contained in the line
     * @param b second point contained in the line
     * 
     * @pre a != b
     */
    public Line(Point a, Point b) throws GeometricException
    {
        if (a.equals(b))
			throw new GeometricException(ERROR_MESSAGE + " cannot create a line with equal points.");
        
        this.coefficientX = a.Y() - b.Y();
        this.coefficientY = b.X() - a.X();
        this.constant = a.X() * b.Y() - b.X() * a.Y();
        this.containedPoint = new Point(a);
    }

    /**
     * Checks if a point is contained within a line
     * @param point the point to be checked if the line contains
     * @return if the point is contained within the line
     */
    public boolean isCollinear(VirtualPoint point)
    {
        return MathUtil.areEqual(calcExpr(point), 0);
    }

    /**
     * Plugs a point into the line equation
     * @param point the point to plug into the line equation
     * @return the result of the equation
     */
    private double calcExpr(VirtualPoint point)
    {
        return point.X() * this.coefficientX + point.Y() * this.coefficientY + constant;
    }

    /**
     * Calculates the point of intersection between the two lines
     * @param that the other line to check the intersection with
     * @pre the lines must not be paralel
     * @return the point that intersects the two lines
     */
    public VirtualPoint intersection(Line that)
    {
		assert(isParalel(that)); // lines shoudn't be paralel
        
        double x = (this.coefficientY * that.constant - that.coefficientY * this.constant) /
                    (this.coefficientX * that.coefficientY - that.coefficientX * this.coefficientY);
        double y = (that.coefficientX * this.constant - this.coefficientX * that.constant) /
                    (this.coefficientX * that.coefficientY - that.coefficientX * this.coefficientY);
        return new VirtualPoint(x, y);
    }

    /**
     * Checks if the lines are paralel
     * @param that the other line to check if is paralel with the current one
     * @return if the lines are paralel
     */
    public boolean isParalel(Line that)
    {
        return MathUtil.areEqual(this.coefficientX * that.coefficientY - that.coefficientX * this.coefficientY, 0);
    }

    /**
     * Checks if the lines are perpendicular
     * @param that the other line to check if is perpendicular with the current one
     * @return if the lines are perpendicular
     */
    public boolean isPerpendicular(Line that)
    {
        return MathUtil.areEqual(this.coefficientX * that.coefficientX + this.coefficientY * that.coefficientY, 0);
    }

	/**
	 * Generates a perpendicular line
	 * @param containedPoint the line to get a perpendicular line to
	 * @return the perpendicular line
	 */
	public Line generatePerpendicular(Point containedPoint)
	{
		try
		{
			return new Line(-this.coefficientY, this.coefficientX, containedPoint);
		}
		catch (GeometricException e)
		{
			throw new Error("Shouldn't happen! there should always be a valid line when generating perpendicular lines.\n" + e.getMessage());
		}
	}

    @Override
    public boolean equals(Object other)
    {
        if (other == this) return true;
        if (other == null) return false;
        if (getClass() != other.getClass()) return false;
        Line that = (Line) other;
        return this.isParalel(that) && this.isCollinear(that.containedPoint);
    }

    @Override
    public int hashCode()
    {
        throw new UnsupportedOperationException();
    }
}
