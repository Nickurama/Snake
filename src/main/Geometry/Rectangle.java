package Geometry;

import java.text.ParseException;

/**
 * Represents a rectangle
 * A rectangle is a polygon with 4 sides and every side has
 * an inner angle of 90 degrees
 * 
 * @author Diogo Fonseca a79858
 * @version 03/05/2024
 *
 * @inv has 4 sides
 * @inv all the inner angles make right angles
 */
public class Rectangle extends Polygon
{
    private static final String ERROR_MESSAGE = "Retangulo:vi";
    public static final int NUM_SIDES = 4;

    /**
     * Initializes a rectangle
     * @param points the points that make the rectangle
     * 
     * @pre points.length = 4
     * @pre all angles are right angles
     */
    public Rectangle(Point[] points) throws GeometricException
    {
        super(points);
        validateRectangle(points.length);
    }

    /**
     * Initializes a rectangle from a polygon
     * @param poly the polygon to initialize from
     */
    public Rectangle(Polygon poly) throws GeometricException
    {
        super(poly);
        validateRectangle(poly.getNumSides());
    }

	/**
	 * Initializes a rectangle from two points
	 * @param firstPoint a corner of the rectangle
	 * @param secondPoint the opposite corner of the rectangle
	 * @pre the points must not be within the same x or y
	 * @throws GeometricException if the two points don't define a rectangle (same x or same y)
	 */
	public Rectangle(Point firstPoint, Point secondPoint) throws GeometricException
	{
		super(makeRectangleFromTwoPoints(firstPoint, secondPoint));
		validateRectangle(4);
	}

	/**
	 * Generates a rectangle from two points
	 * @param firstPoint a corner of the rectangle
	 * @param secondPoint the opposite corner of the rectangle
	 * @return a rectangle formed by the two corners
	 * @throws GeometricException if the two points don't define a rectangle (same x or same y)
	 */
	private static Point[] makeRectangleFromTwoPoints(Point firstPoint, Point secondPoint) throws GeometricException
	{
		return new Point[] {
			firstPoint,
			new Point(firstPoint.X(), secondPoint.Y()),
			secondPoint,
			new Point(secondPoint.X(), firstPoint.Y()),
		};
	}

    /**
     * Validates the preconditions for the
     * class to work. terminates the program if
     * they aren't met
     */
    void validateRectangle(int numSides) throws GeometricException
    {
        if (numSides != NUM_SIDES)
			throw new GeometricException(ERROR_MESSAGE + " A Rectangle must have exactly 4 sides.");
        
        if (!allAnglesAreRightAngles())
			throw new GeometricException(ERROR_MESSAGE + " A Recrangle's inner angles must all form 90 degrees.");
    }

    /**
     * Initializes a rectangle
     * parses a string into a rectangle
     * @param str the string to parse into a rectangle
     * 
     * @pre must have 4 points
     * @pre all angles are right angles
     */
    public Rectangle(String str) throws GeometricException, ParseException
    {
        this(Point.parseToArray(str, NUM_SIDES));
    }

    /**
     * Checks if all inner angles of the rectangle are right angles
     * @return if all the inner angles of the rectangle are right angles
     */
    private boolean allAnglesAreRightAngles()
    {
        return (this.sides()[0].line().isPerpendicular(this.sides()[1].line()) &&
            this.sides()[0].line().isPerpendicular(this.sides()[3].line()) &&
            this.sides()[1].line().isPerpendicular(this.sides()[2].line()));
    }

    @Override
    public String toString()
    {
        return "Retangulo: " + VirtualPoint.arrayToString(this.vertices());
    }

    @Override
    public Rectangle rotate(double angle, VirtualPoint anchor) throws GeometricException
    {
        return new Rectangle(super.rotate(angle, anchor));
    }

    @Override
    public Rectangle rotate(double angle) throws GeometricException
    {
        return new Rectangle(super.rotate(angle));
    }

    @Override
    public Rectangle rotateDegrees(double angle, VirtualPoint anchor) throws GeometricException
    {
        return new Rectangle(super.rotateDegrees(angle, anchor));
    }

    @Override
    public Rectangle rotateDegrees(double angle) throws GeometricException
    {
        return new Rectangle(super.rotateDegrees(angle));
    }

    @Override
    public Rectangle translate(Vector vector) throws GeometricException
    {
        return new Rectangle(super.translate(vector));
    }

    @Override
    public Rectangle moveCentroid(Point newCentroid) throws GeometricException
    {
        return new Rectangle(super.moveCentroid(newCentroid));
    }
}
