package SnakeGame;

import Geometry.*;

/**
 * A class containing the required information to be
 * able to get the snake's properties on command
 * 
 * @author Diogo Fonseca a79858
 * @version 08/05/2024
 * 
 * @see Snake
 * @see ISnakeStats
 */
public class SnakeStats implements ISnakeStats
{
	Snake snake;

	/**
	 * Instantiates SnakeStats
	 * @param snake the snake to keep track of
	 */
	public SnakeStats(Snake snake)
	{
		this.snake = snake;
	}

	@Override
	public Direction direction()
	{
		return this.snake.direction();
	}

	@Override
	public Point position()
	{
		return this.snake.position();
	}

	@Override
	public int score()
	{
		return GameManager.getInstance().score();
	}
}
