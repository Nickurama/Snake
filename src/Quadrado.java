/**
 * Represents a square
 * A square is a rectangle whose sides are
 * all the same length
 * 
 * @author Diogo Fonseca a79858
 * @version 18/03/2024
 */
public class Quadrado extends Retangulo
{
    private final String ERROR_MESSAGE = "Quadrado:vi";

    /**
     * Initializes a square
     * @param points the points that make the square
     * @pre all sides must have equal length
     */
    public Quadrado(Point[] points)
    {
        super(points);
        validateSquare();
    }

    /**
     * Initializes a square from a polygon
     * @param poly the polygon to initialize from
     */
    public Quadrado(Poligono poly)
    {
        super(poly);
        validateSquare();
    }

    /**
     * Validates the preconditions for the
     * class to work. terminates the program if
     * they aren't met
     */
    public void validateSquare()
    {
        if (!areAllSidesSameLength())
            Error.terminateProgram(ERROR_MESSAGE);
    }

    /**
     * Initializes a square
     * parses a string to a square
     * @param str the string to parse into a square
     * @pre all sides must have equal length
     */
    public Quadrado(String str)
    {
        this(Point.parseToArray(str, NUM_SIDES));
    }

    /**
     * Checks if all the sides have the same length
     * @return if all the sides have the same length
     */
    private boolean areAllSidesSameLength()
    {
        double len = this.sides[0].length();
        for (int i = 1; i < sides.length; i++)
            if (!MathUtil.areEqual(this.sides[i].length(), len))
                return false;
        return true;
    }

    @Override
    public String toString()
    {
        return "Quadrado: " + VirtualPoint.arrayToString(this.vertices);
    }

    @Override
    public Quadrado rotate(double angle, VirtualPoint anchor)
    {
        return new Quadrado(super.rotate(angle, anchor));
    }

    @Override
    public Quadrado rotate(double angle)
    {
        return new Quadrado(super.rotate(angle));
    }

    @Override
    public Quadrado rotateDegrees(double angle, VirtualPoint anchor)
    {
        return new Quadrado(super.rotateDegrees(angle, anchor));
    }

    @Override
    public Quadrado rotateDegrees(double angle)
    {
        return new Quadrado(super.rotateDegrees(angle));
    }

    @Override
    public Quadrado translate(Vector vector)
    {
        return new Quadrado(super.translate(vector));
    }

    @Override
    public Quadrado moveCentroid(Point newCentroid)
    {
        return new Quadrado(super.moveCentroid(newCentroid));
    }
}
