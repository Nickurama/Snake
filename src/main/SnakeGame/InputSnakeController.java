package SnakeGame;

import GameEngine.*;

public class InputSnakeController extends GameObject implements ISnakeController, IInputListener
{
	public enum InputPreset
	{
		WASD,
		VIM,
		ABSOLUTE,
		RELATIVE,
	}

	// WASD
	private static final String WASD_UP_INPUT_STR = "w";
	private static final String WASD_DOWN_INPUT_STR = "s";
	private static final String WASD_LEFT_INPUT_STR = "a";
	private static final String WASD_RIGHT_INPUT_STR = "d";

	// VIM
	private static final String VIM_UP_INPUT_STR = "k";
	private static final String VIM_DOWN_INPUT_STR = "j";
	private static final String VIM_LEFT_INPUT_STR = "h";
	private static final String VIM_RIGHT_INPUT_STR = "l";

	// ABSOLUTE
	private static final String ABSOLUTE_UP_INPUT_STR_0 = "up";
	private static final String ABSOLUTE_UP_INPUT_STR_1 = "u";
	private static final String ABSOLUTE_DOWN_INPUT_STR_0 = "down";
	private static final String ABSOLUTE_DOWN_INPUT_STR_1 = "d";
	private static final String ABSOLUTE_LEFT_INPUT_STR_0 = "left";
	private static final String ABSOLUTE_LEFT_INPUT_STR_1 = "l";
	private static final String ABSOLUTE_RIGHT_INPUT_STR_0 = "right";
	private static final String ABSOLUTE_RIGHT_INPUT_STR_1 = "r";

	// RELATIVE
	private static final String RELATIVE_LEFT_INPUT_STR_0 = "left";
	private static final String RELATIVE_LEFT_INPUT_STR_1 = "l";
	private static final String RELATIVE_RIGHT_INPUT_STR_0 = "right";
	private static final String RELATIVE_RIGHT_INPUT_STR_1 = "r";
	private static final String RELATIVE_FOWARD_INPUT_STR_0 = "foward";
	private static final String RELATIVE_FOWARD_INPUT_STR_1 = "f";

	private TurnDirection nextDir;
	private InputPreset inputPreset;
	private ISnakeStats snake;

	public InputSnakeController(InputPreset preset, ISnakeStats snake)
	{
		this.inputPreset = preset;
		this.snake = snake;

		this.nextDir = TurnDirection.NONE;
	}

	public void onInputReceived(String input)
	{
		input = input.toLowerCase();
		switch(inputPreset)
		{
			case WASD:
				turnWasd(input);
				break;
			case VIM:
				turnVim(input);
				break;
			case ABSOLUTE:
				turnAbsolute(input);
				break;
			case RELATIVE:
				turnRelative(input);
				break;
		}
	}

	private void turnWasd(String input)
	{
		switch (input)
		{
			case WASD_UP_INPUT_STR:
				this.nextDir = Direction.getRelativeDir(Direction.UP, snake.direction());
				break;
			case WASD_DOWN_INPUT_STR:
				this.nextDir = Direction.getRelativeDir(Direction.DOWN, snake.direction());
				break;
			case WASD_LEFT_INPUT_STR:
				this.nextDir = Direction.getRelativeDir(Direction.LEFT, snake.direction());
				break;
			case WASD_RIGHT_INPUT_STR:
				this.nextDir = Direction.getRelativeDir(Direction.RIGHT, snake.direction());
				break;
		}
	}

	private void turnVim(String input)
	{
		switch (input)
		{
			case VIM_UP_INPUT_STR:
				this.nextDir = Direction.getRelativeDir(Direction.UP, snake.direction());
				break;
			case VIM_DOWN_INPUT_STR:
				this.nextDir = Direction.getRelativeDir(Direction.DOWN, snake.direction());
				break;
			case VIM_LEFT_INPUT_STR:
				this.nextDir = Direction.getRelativeDir(Direction.LEFT, snake.direction());
				break;
			case VIM_RIGHT_INPUT_STR:
				this.nextDir = Direction.getRelativeDir(Direction.RIGHT, snake.direction());
				break;
		}
	}

	private void turnAbsolute(String input)
	{
		switch(input)
		{
			case ABSOLUTE_UP_INPUT_STR_0:
			case ABSOLUTE_UP_INPUT_STR_1:
				this.nextDir = Direction.getRelativeDir(Direction.UP, snake.direction());
				break;
			case ABSOLUTE_DOWN_INPUT_STR_0:
			case ABSOLUTE_DOWN_INPUT_STR_1:
				this.nextDir = Direction.getRelativeDir(Direction.DOWN, snake.direction());
				break;
			case ABSOLUTE_LEFT_INPUT_STR_0:
			case ABSOLUTE_LEFT_INPUT_STR_1:
				this.nextDir = Direction.getRelativeDir(Direction.LEFT, snake.direction());
				break;
			case ABSOLUTE_RIGHT_INPUT_STR_0:
			case ABSOLUTE_RIGHT_INPUT_STR_1:
				this.nextDir = Direction.getRelativeDir(Direction.RIGHT, snake.direction());
				break;
		}
	}

	private void turnRelative(String input)
	{
		switch(input)
		{
			case RELATIVE_LEFT_INPUT_STR_0:
			case RELATIVE_LEFT_INPUT_STR_1:
				this.nextDir = TurnDirection.LEFT;
				break;
			case RELATIVE_RIGHT_INPUT_STR_0:
			case RELATIVE_RIGHT_INPUT_STR_1:
				this.nextDir = TurnDirection.RIGHT;
				break;
			case RELATIVE_FOWARD_INPUT_STR_0:
			case RELATIVE_FOWARD_INPUT_STR_1:
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
