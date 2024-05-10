package SnakeGame;

/**
 * Represents an exception within the snake game.
 * @author Diogo Fonseca a79858
 * @version 30/04/2024
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
