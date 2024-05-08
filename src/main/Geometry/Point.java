package Geometry;

import java.text.ParseException;

/**
 * Represents an immutable point in two dimensional space, in the first quadrant only
 * 
 * @author Diogo Fonseca a79858
 * @version 18/03/2024
 */
public class Point extends VirtualPoint
{
    private static final String ERROR_MESSAGE = "Ponto:vi";

    /**
     * Initializes a point
     * @param x the x coordinate
     * @param y the y coordinate
     * @pre x >= 0
     * @pre y >= 0
     */
    public Point(double x, double y) throws GeometricException
    {
        super(x, y);
        validatePoint();
    }

    /**
     * Initializes a point, performing a deep copy
     * over the other point
     * @param p the point to copy from
     */
    public Point(Point p) throws GeometricException
    {
        super(p);
        validatePoint();
    }

    /**
     * Initializes a point from a VirtualPoint
     * @param p the VirtualPoint to initialize from
     */
    public Point(VirtualPoint p) throws GeometricException
    {
        super(p);
        validatePoint();
    }

    /**
     * Validates the preconditions for the
     * class to work. terminates the program if
     * they aren't met
     */
    private void validatePoint() throws GeometricException
    {
        if (this.X() < 0 || this.Y() < 0)
			throw new GeometricException(ERROR_MESSAGE + " point coordinates should always be positive");
    }
    
    /**
     * Performs a deep copy of an array of points
     * @param array the array to copy
     * @return the copy of the array
     */
    public static Point[] copyArray(Point[] array)
    {
        VirtualPoint[] vps = VirtualPoint.copyArray(array);
        Point[] result = new Point[vps.length];

        for (int i = 0; i < vps.length; i++)
		{
			try
			{
            	result[i] = new Point(vps[i]);
			}
			catch (GeometricException e)
			{
				throw new IllegalStateException("This should never happen: a point will be a point");
			}
		}
        return result;

        // Point[] result = new Point[array.length]; //! Issue: if not implemented, could use the parent class's implementation which will break
        // for (int i = 0; i < array.length; i++)
        //     result[i] = new Point(array[i]);
        // return result;
    }

    /**
     * Creates array of points from a string with the format:
     * num_points x0 y0 x1 y1 x2 y2 ...
     * @param str the string to read the points from
     * @return the points extracted form the string
     */
    public static Point[] parseToArray(String str) throws ParseException
    {
        VirtualPoint[] vps = VirtualPoint.parseToArray(str);
        Point[] result = new Point[vps.length];

        for (int i = 0; i < vps.length; i++)
		{
			try
			{
				result[i] = new Point(vps[i]);
			}
			catch (GeometricException e)
			{
				throw new ParseException("Error parsing point to array, the point isn't valid: " + e.getMessage(), 2 + 2*i);
			}
		}
        return result;
    }

    /**
     * Creates array of points from a string with the format:
     * x0 y0 x1 y1 x2 y2 ...
     * @param str the string to read the points from
     * @param numPoints how many points are to be read from the string
     * @return the points extracted from the string
     */
    public static Point[] parseToArray(String str, int numPoints) throws ParseException
    {
        VirtualPoint[] vps = VirtualPoint.parseToArray(str, numPoints);
        Point[] result = new Point[vps.length];

        for (int i = 0; i < vps.length; i++)
		{
			try
			{
				result[i] = new Point(vps[i]);
			}
			catch (GeometricException e)
			{
				throw new ParseException("Error parsing point to array, the point isn't valid: " + e.toString(), 2 + 2*i);
			}
		}
        return result;
        // return parseToArray(String.valueOf(numPoints) + " " + str);
    }

    @Override
    public Point translate(Vector vector) throws GeometricException
    {
        return new Point(super.translate(vector));
    }

	public Point translate(double x, double y) throws GeometricException
	{
		return translate(new Vector(x, y));
	}

	public Point translate(Point vector) throws GeometricException
	{
		return translate(new Vector(vector));
	}

	@Override
	public Point rotate(double angle, VirtualPoint anchor) throws GeometricException
	{
		return new Point(super.rotate(angle, anchor));
	}
}
