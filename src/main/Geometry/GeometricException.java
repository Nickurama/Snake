package Geometry;

/**
 * Represents an exception within a geometric component.
 * 
 * @author Diogo Fonseca a79858
 * @version 07/04/2024
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
