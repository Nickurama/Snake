package Geometry;

/**
 * Represents an exception within a geometric component.
 */
public class GeometricException extends Exception
{
	/**
	 * Exception within a geometric component
	 */
	public GeometricException()
	{
		super();
	}

	/**
	 * Exception within a geometric component
	 * @param message the error message
	 */
	public GeometricException(String message)
	{
		super(message);
	}
}
