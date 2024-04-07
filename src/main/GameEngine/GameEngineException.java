package GameEngine;

/**
 * Represents an exception of the geometric type.
 */
public class GameEngineException extends Exception
{
	/**
	* Empty geometric exception
	*/
	public GameEngineException()
	{
		super();
	}

	/**
	* Geometric exception with an error message
	*/
	public GameEngineException(String message)
	{
		super(message);
	}
}
