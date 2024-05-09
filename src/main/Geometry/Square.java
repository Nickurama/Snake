package Geometry;

import java.text.ParseException;

/**
 * Represents a square
 * A square is a rectangle whose sides are
 * all the same length
 * 
 * @author Diogo Fonseca a79858
 * @version 18/03/2024
 */
public class Square extends Rectangle
{
    private final String ERROR_MESSAGE = "Quadrado:vi";

    /**
     * Initializes a square
     * @param points the points that make the square
     * @pre all sides must have equal length
     */
    public Square(Point[] points) throws GeometricException
    {
        super(points);
        validateSquare();
    }

    /**
     * Initializes a square from a polygon
     * @param poly the polygon to initialize from
     */
    public Square(Polygon poly) throws GeometricException
    {
        super(poly);
        validateSquare();
    }

	/**
	 * Initializes a square from two points
	 * @param firstPoint a corner of the square
	 * @param secondPoint the opposite corner of the square
	 *
	 * @pre the two points must form a valid square
	 * @throws GeometricException
	 */
	public Square(Point firstPoint, Point secondPoint) throws GeometricException
	{
		super(firstPoint, secondPoint);
		validateSquare();
	}

    /**
     * Validates the preconditions for the
     * class to work. terminates the program if
     * they aren't met
     */
    public void validateSquare() throws GeometricException
    {
        if (!areAllSidesSameLength())
			throw new GeometricException(ERROR_MESSAGE + " Square should have all sides of same length.");
    }

    /**
     * Initializes a square
     * parses a string to a square
     * @param str the string to parse into a square
     * @pre all sides must have equal length
     */
    public Square(String str) throws GeometricException, ParseException
    {
        this(Point.parseToArray(str, NUM_SIDES));
    }

    /**
     * Checks if all the sides have the same length
     * @return if all the sides have the same length
     */
    private boolean areAllSidesSameLength()
    {
        double len = this.sides()[0].length();
        for (int i = 1; i < sides().length; i++)
            if (!MathUtil.areEqual(this.sides()[i].length(), len))
                return false;
        return true;
    }

    @Override
    public String toString()
    {
        return "Quadrado: " + VirtualPoint.arrayToString(this.vertices());
    }

    @Override
    public Square rotate(double angle, VirtualPoint anchor) throws GeometricException
    {
        return new Square(super.rotate(angle, anchor));
    }

    @Override
    public Square rotate(double angle) throws GeometricException
    {
        return new Square(super.rotate(angle));
    }

    @Override
    public Square rotateDegrees(double angle, VirtualPoint anchor) throws GeometricException
    {
        return new Square(super.rotateDegrees(angle, anchor));
    }

    @Override
    public Square rotateDegrees(double angle) throws GeometricException
    {
        return new Square(super.rotateDegrees(angle));
    }

    @Override
    public Square translate(Vector vector) throws GeometricException
    {
        return new Square(super.translate(vector));
    }

    @Override
    public Square moveCentroid(Point newCentroid) throws GeometricException
    {
        return new Square(super.moveCentroid(newCentroid));
    }
}
