/**
 * Represents a triangle
 * A triangle is a polygon with 3 sides
 * 
 * @author Diogo Fonseca a79858
 * @version 18/03/2024
 */
public class Triangulo extends Poligono
{
    private static final String ERROR_MESSAGE = "Triangulo:vi";
    public static final int NUM_SIDES = 3;

    /**
     * Initializes a triangle
     * @param points the points that make the triangle
     * 
     * @pre points.length = 3
     */
    public Triangulo(Point[] points)
    {
        super(points);
        validateTriangle(this.sides.length);
    }

    /**
     * Initializes a triangle from a polygon
     * @param poly the polygon to initialize from
     */
    public Triangulo(Poligono poly)
    {
        super(poly);
        validateTriangle(poly.getNumSides());
    }

    /**
     * Validates the preconditions for the
     * class to work. terminates the program if
     * they aren't met
     */
    private void validateTriangle(int numSides)
    {
        if (this.sides.length != NUM_SIDES)
            Error.terminateProgram(ERROR_MESSAGE);
    }

    /**
     * Initializes a triangle
     * Parses a string into a triangle
     * @param str the string to turn into a triangle
     * 
     * @pre must have 3 points
     */
    public Triangulo(String str)
    {
        this(Point.parseToArray(str, NUM_SIDES));
    }

    @Override
    public String toString()
    {
        return "Triangulo: " + VirtualPoint.arrayToString(this.vertices);
    }

    @Override
    public Triangulo rotate(double angle, VirtualPoint anchor)
    {
        return new Triangulo(super.rotate(angle, anchor));
    }

    @Override
    public Triangulo rotate(double angle)
    {
        return new Triangulo(super.rotate(angle));
    }

    @Override
    public Triangulo rotateDegrees(double angle, VirtualPoint anchor)
    {
        return new Triangulo(super.rotateDegrees(angle, anchor));
    }

    @Override
    public Triangulo rotateDegrees(double angle)
    {
        return new Triangulo(super.rotateDegrees(angle));
    }

    @Override
    public Triangulo translate(Vector vector)
    {
        return new Triangulo(super.translate(vector));
    }

    @Override
    public Triangulo moveCentroid(Point newCentroid)
    {
        return new Triangulo(super.moveCentroid(newCentroid));
    }
}
