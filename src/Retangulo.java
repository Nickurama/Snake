/**
 * Represents a rectangle
 * A rectangle is a polygon with 4 sides and every side has
 * an inner angle of 90 degrees
 * 
 * @author Diogo Fonseca a79858
 * @version 18/03/2024
 */
public class Retangulo extends Poligono
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
    public Retangulo(Point[] points)
    {
        super(points);
        validateRectangle(points.length);
    }

    /**
     * Initializes a rectangle from a polygon
     * @param poly the polygon to initialize from
     */
    public Retangulo(Poligono poly)
    {
        super(poly);
        validateRectangle(poly.getNumSides());
    }

    /**
     * Validates the preconditions for the
     * class to work. terminates the program if
     * they aren't met
     */
    private void validateRectangle(int numSides)
    {
        if (numSides != NUM_SIDES)
            Error.terminateProgram(ERROR_MESSAGE);
        
        if (!allAnglesAreRightAngles())
            Error.terminateProgram(ERROR_MESSAGE);
    }

    /**
     * Initializes a rectangle
     * parses a string into a rectangle
     * @param str the string to parse into a rectangle
     * 
     * @pre must have 4 points
     * @pre all angles are right angles
     */
    public Retangulo(String str)
    {
        this(Point.parseToArray(str, NUM_SIDES));
    }

    /**
     * Checks if all inner angles of the rectangle are right angles
     * @return if all the inner angles of the rectangle are right angles
     */
    private boolean allAnglesAreRightAngles()
    {
        return (this.sides[0].line().isPerpendicular(this.sides[1].line()) &&
            this.sides[0].line().isPerpendicular(this.sides[3].line()) &&
            this.sides[1].line().isPerpendicular(this.sides[2].line()));
    }

    @Override
    public String toString()
    {
        return "Retangulo: " + VirtualPoint.arrayToString(this.vertices);
    }

    @Override
    public Retangulo rotate(double angle, VirtualPoint anchor)
    {
        return new Retangulo(super.rotate(angle, anchor));
    }

    @Override
    public Retangulo rotate(double angle)
    {
        return new Retangulo(super.rotate(angle));
    }

    @Override
    public Retangulo rotateDegrees(double angle, VirtualPoint anchor)
    {
        return new Retangulo(super.rotateDegrees(angle, anchor));
    }

    @Override
    public Retangulo rotateDegrees(double angle)
    {
        return new Retangulo(super.rotateDegrees(angle));
    }

    @Override
    public Retangulo translate(Vector vector)
    {
        return new Retangulo(super.translate(vector));
    }

    @Override
    public Retangulo moveCentroid(Point newCentroid)
    {
        return new Retangulo(super.moveCentroid(newCentroid));
    }
}
