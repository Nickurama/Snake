package SnakeGame;

import Geometry.*;
import SnakeGame.*;
import SnakeGame.Snake.*;

public class SnakeStats implements ISnakeStats
{
	Snake snake;

	public SnakeStats(Snake snake)
	{
		this.snake = snake;
	}

	public Direction direction()
	{
		return this.snake.direction();
	}

	public Point position()
	{
		return this.snake.position();
	}

	public int score()
	{
		return GameManager.getInstance().score();
	}
}
