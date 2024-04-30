package SnakeGame;

/**
 * Represents an exception of Snake Game.
 */
public class SnakeGameException extends Exception
{
	/**
	* Empty SnakeGame exception
	*/
	public SnakeGameException()
	{
		super();
	}

	/**
	* SnakeGame exception with an error message
	*/
	public SnakeGameException(String message)
	{
		super(message);
	}
}
