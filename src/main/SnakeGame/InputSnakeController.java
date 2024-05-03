package SnakeGame;

import GameEngine.*;

public class InputSnakeController extends GameObject implements ISnakeController, IInputListener
{
	private static final String LEFT_INPUT_STR = "left";
	private static final String RIGHT_INPUT_STR = "right";
	private static final String FRONT_INPUT_STR = "front";

	private TurnDirection nextDir;

	public InputSnakeController()
	{
		this.nextDir = TurnDirection.NONE;
	}

	public void onInputReceived(String input)
	{
		switch(input)
		{
			case LEFT_INPUT_STR:
				this.nextDir = TurnDirection.LEFT;
				break;
			case RIGHT_INPUT_STR:
				this.nextDir = TurnDirection.RIGHT;
				break;
			case FRONT_INPUT_STR:
				this.nextDir = TurnDirection.NONE;
				break;
		}
	}

	public TurnDirection nextTurn()
	{
		TurnDirection result = this.nextDir;
		this.nextDir = TurnDirection.NONE;
		return result;
	}
}
