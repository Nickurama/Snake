package GameEngine;

/**
 * Represents an exception of Game Engine.
 */
public class GameEngineException extends Exception
{
	/**
	* Empty GameEngine exception
	*/
	public GameEngineException()
	{
		super();
	}

	/**
	* GameEngine exception with an error message
	*/
	public GameEngineException(String message)
	{
		super(message);
	}
}
