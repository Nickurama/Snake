package SnakeGame;

/**
 * An interface to get a snake's properties
 * 
 * @author Diogo Fonseca a79858
 * @version 08/05/2024
 * 
 * @see Snake
 * @see ISpatialComponent
 */
public interface ISnakeStats extends ISpatialComponent
{
	/**
	 * The snake's current score
	 * @return the snake's current score
	 */
	public int score();

	/**
	 * The snake's current direction
	 * @return the snake's current direction
	 */
	public Direction direction();
}
