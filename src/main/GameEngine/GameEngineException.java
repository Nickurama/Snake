package GameEngine;

/**
 * Represents an exception within the game engine
 */
public class GameEngineException extends Exception
{
	/**
	 * An empty GameEngine exception
	 */
	public GameEngineException()
	{
		super();
	}

	/**
	 * An exception within the game engine, with a message
	 * @param message the error message
	 */
	public GameEngineException(String message)
	{
		super(message);
	}
}
