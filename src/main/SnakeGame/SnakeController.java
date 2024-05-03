package SnakeGame;

import GameEngine.*;

public class SnakeController extends GameObject
{
	private Snake snake;
	private ISnakeController controller;

	public SnakeController(Snake snake, ISnakeController controller)
	{
		this.snake = snake;
		this.controller = controller;
	}

	@Override
	public void earlyUpdate()
	{
		switch(controller.nextTurn())
		{
			case ISnakeController.TurnDirection.NONE:
				break;
			case ISnakeController.TurnDirection.RIGHT:
				snake.turnRight();
				break;
			case ISnakeController.TurnDirection.LEFT:
				snake.turnLeft();
				break;
		}
	}
}
