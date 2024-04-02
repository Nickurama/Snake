/**
 * Represents an exception of the geometric type.
 */
class GeometricException extends Exception
{
	/**
	* Empty geometric exception
	*/
	public GeometricException()
	{
		super();
	}

	/**
	* Geometric exception with an error message
	*/
	public GeometricException(String message)
	{
		super(message);
	}
}
