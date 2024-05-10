package SnakeGame;

import GameEngine.*;

/**
 * Responsible for controlling a snake (using a controller)
 * 
 * @author Diogo Fonseca a79858
 * @version 08/05/2024
 * 
 * @see Snake
 * @see ISnakeController
 */
public class SnakeController extends GameObject
{
	private Snake snake;
	private ISnakeController controller;

	/**
	 * Instantiates a SnakeController
	 * @param snake the snake to control
	 * @param controller the controller
	 */
	public SnakeController(Snake snake, ISnakeController controller)
	{
		this.snake = snake;
		this.controller = controller;
	}

	@Override
	public void earlyUpdate()
	{
		if (!snake.isDead())
			turnSnake();
	}

	/**
	 * Turns the snake to the next direction
	 * given by the controller
	 */
	private void turnSnake()
	{
		switch(controller.nextTurn())
		{
			case Direction.TurnDirection.NONE:
				break;
			case Direction.TurnDirection.RIGHT:
				snake.turnRight();
				break;
			case Direction.TurnDirection.LEFT:
				snake.turnLeft();
				break;
		}
	}
}
