package Geometry;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.ParseException;

/**
 * Represents an immutable point in two dimensional space
 * 
 * @author Diogo Fonseca a79858
 * @version 08/05/2024
 * 
 * @inv x the x coordinate
 * @inv y the y coordinate
 */
public class VirtualPoint
{
    private double x, y;

    /**
     * Initializes the virtual point
     * @param x the x coordinate
     * @param y the y coordinate
     */
    public VirtualPoint(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    /**
     * Initializes the virtual point, performing a deep copy
     * over the other virtual point
     * @param p the virtual point to copy from
     */
    public VirtualPoint(VirtualPoint p)
    {
        this(p.x, p.y);
    }

    /**
     * Performs a deep copy of an array of points
     * @param array the array to copy
     * @return a deep copy of the array
     */
    public static VirtualPoint[] copyArray(VirtualPoint[] array)
    {
        VirtualPoint[] result = new VirtualPoint[array.length];
        for (int i = 0; i < array.length; i++)
            result[i] = new VirtualPoint(array[i]);
        return result;
    }

    /**
     * Initializes a virtual point from the input of a scanner
     * @param reader the scanner to read the point from
     * @return the initialized point
     */
    public static VirtualPoint getPointFromInput(BufferedReader reader) throws IOException
    {
        String[] tokens = reader.readLine().split(" ");
        return new VirtualPoint(Double.parseDouble(tokens[0]), Double.parseDouble(tokens[1]));
    }

    /**
     * Calculates the distance between the current point and the other
     * @param that the point to calculate the distance with
     * @return the distance between the points
     */
    public double dist(VirtualPoint that)
    {
        double dx = this.x - that.x;
        double dy = this.y - that.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    @Override
    public boolean equals(Object other)
    {
        if (other == this) return true;
        if (other == null) return false;
        if (!VirtualPoint.class.isInstance(other)) return false;
        VirtualPoint that = (VirtualPoint) other;
        return MathUtil.areEqual(this.x, that.x) && MathUtil.areEqual(this.y, that.y);
    }

    @Override
    public int hashCode()
    {
		return Double.valueOf(this.x).hashCode() * 31 + Double.valueOf(this.y).hashCode();
        // throw new UnsupportedOperationException();
    }

    @Override
    public String toString()
    {
		// DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.getDefault());
		// symbols.setDecimalSeparator('.');
		// symbols.setGroupingSeparator(',');
		// DecimalFormat format = new DecimalFormat("#.0", symbols);
		// String printableX = format.format(this.x);
		// String printableY = format.format(this.y);

        // this is done because the expected behaviour is to
        // truncate the number, but sometimes the number will be
        // a small infitesimal below the actual number which
        // makes the truncation to be 1 below the actual
        // number, which is unexpected behaviour
        int printableX = (int)this.x;
        int printableY = (int)this.y;
        int roundedX = (int)Math.round(this.x);
        int roundedY = (int)Math.round(this.y);
        if (MathUtil.areEqual(this.x, roundedX))
            printableX = roundedX;
        if (MathUtil.areEqual(this.y, roundedY))
            printableY = roundedY;
        return "(" + printableX + "," + printableY + ")";
    }

    /**
     * transforms an array of points to a string
     * @param points the points to include in the string
     * @return a string with all the points
     */
    public static String arrayToString(VirtualPoint[] points)
    {
        StringBuilder str = new StringBuilder("[");
        for (int i = 0; i < points.length - 1; i++)
        {
            str.append(points[i].toString());
            str.append(", ");
        }
        str.append(points[points.length - 1].toString());
        str.append("]");

        return str.toString();
    }

    /**
     * Creates array of points from a string with the format:
     * num_points x0 y0 x1 y1 x2 y2 ...
     * @param str the string to read the points from
     * @return the points extracted form the string
	 * @pre str = num_points x0 y0 x1 y1 x2 y2 ...
     */
    public static VirtualPoint[] parseToArray(String str) throws ParseException
    {
        String[] tokens = str.split(" ");
        if (tokens.length < 3)
			throw new ParseException("Can't parse a string to VirtualPoint with less than 3 elements", 0);
        
		int numVertices;
		try
		{
        	numVertices = Integer.parseInt(tokens[0]);
		}
		catch (NumberFormatException e)
		{
			throw new ParseException("Error parsing number of virtual points", 0);
		}

        VirtualPoint[] result = new VirtualPoint[numVertices];
		int maxInd = numVertices * 2;
        for (int i = 1; i < maxInd; i += 2)
        {
			double x, y;
			try
			{
				x = Double.parseDouble(tokens[i]);
				y = Double.parseDouble(tokens[i + 1]);
				result[i / 2] = new VirtualPoint(x, y);
			}
			catch (Exception e)
			{
				throw new ParseException("Error parsing string to virtual points: " + e.getMessage(), 2 + 2*i);
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
    public static VirtualPoint[] parseToArray(String str, int numPoints) throws ParseException
    {
        return parseToArray(String.valueOf(numPoints) + " " + str);
    }

    /**
     * Rotates a point around a fixed point (anchor)
     * @param angle the angle (in radians) to rotate the point by
     * @param anchor the fixed point to rotate the point around
     * @return a point with the rotation applied to it
     */
    public VirtualPoint rotate(double angle, VirtualPoint anchor) throws GeometricException
    {
        double newX = (this.x - anchor.x) * Math.cos(angle) - (this.y - anchor.y) * Math.sin(angle) + anchor.x;
        double newY = (this.x - anchor.x) * Math.sin(angle) + (this.y - anchor.y) * Math.cos(angle) + anchor.y;
        return new VirtualPoint(newX, newY);
    }

    /**
     * Translates a point by a vector
     * @param vector the vector to translate the point
     * @return the translated point
     */
    public VirtualPoint translate(Vector vector) throws GeometricException
    {
        return new VirtualPoint(this.X() + vector.X(), this.Y() + vector.Y());
    }

	/**
	 * Translates a point by dx and dy
	 * @param dx the difference to the x coordinate
	 * @param dy the difference to the y coordinate
	 * @return the translated point
	 * @throws GeometricException if the point is translated into an invalid position
	 */
	public VirtualPoint translate(double dx, double dy) throws GeometricException
	{
		return translate(new Vector(dx, dy));
	}

	/**
	 * Translates a point by a vector
	 * @param vector the vector represented as a point to translate the point
	 * @return the translated point
	 * @throws GeometricException if the point is translated into an invalid position
	 */
	public VirtualPoint translate(Point vector) throws GeometricException
	{
		return translate(new Vector(vector));
	}

    /**
	 * The x coordinate
     */
    public double X() { return this.x; }

    /**
     * The y coordinate
     */
    public double Y() { return this.y; }
}
