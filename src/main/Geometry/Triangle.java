package Geometry;

import java.text.ParseException;

/**
 * Represents a triangle
 * A triangle is a polygon with 3 sides
 * 
 * @author Diogo Fonseca a79858
 * @version 18/03/2024
 */
public class Triangle extends Polygon
{
    private static final String ERROR_MESSAGE = "Triangulo:vi";
    public static final int NUM_SIDES = 3;

    /**
     * Initializes a triangle
     * @param points the points that make the triangle
     * 
     * @pre points.length = 3
     */
    public Triangle(Point[] points) throws GeometricException
    {
        super(points);
        validateTriangle(this.sides().length);
    }

    /**
     * Initializes a triangle from a polygon
     * @param poly the polygon to initialize from
     */
    public Triangle(Polygon poly) throws GeometricException
    {
        super(poly);
        validateTriangle(poly.getNumSides());
    }

    /**
     * Validates the preconditions for the
     * class to work. terminates the program if
     * they aren't met
     */
    private void validateTriangle(int numSides) throws GeometricException
    {
        if (this.sides().length != NUM_SIDES)
			throw new GeometricException(ERROR_MESSAGE + " A triangle should have exactly 3 vertices.");
    }

    /**
     * Initializes a triangle
     * Parses a string into a triangle
     * @param str the string to turn into a triangle
     * 
     * @pre must have 3 points
     */
    public Triangle(String str) throws GeometricException, ParseException
    {
        this(Point.parseToArray(str, NUM_SIDES));
    }

    @Override
    public String toString()
    {
        return "Triangulo: " + VirtualPoint.arrayToString(this.vertices());
    }

    @Override
    public Triangle rotate(double angle, VirtualPoint anchor) throws GeometricException
    {
        return new Triangle(super.rotate(angle, anchor));
    }

    @Override
    public Triangle rotate(double angle) throws GeometricException
    {
        return new Triangle(super.rotate(angle));
    }

    @Override
    public Triangle rotateDegrees(double angle, VirtualPoint anchor) throws GeometricException
    {
        return new Triangle(super.rotateDegrees(angle, anchor));
    }

    @Override
    public Triangle rotateDegrees(double angle) throws GeometricException
    {
        return new Triangle(super.rotateDegrees(angle));
    }

    @Override
    public Triangle translate(Vector vector) throws GeometricException
    {
        return new Triangle(super.translate(vector));
    }

    @Override
    public Triangle moveCentroid(Point newCentroid) throws GeometricException
    {
        return new Triangle(super.moveCentroid(newCentroid));
    }
}
