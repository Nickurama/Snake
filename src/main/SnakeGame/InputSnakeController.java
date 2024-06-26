package SnakeGame;

import java.awt.event.KeyEvent;

import GameEngine.*;

/**
 * Responsible for turning the snake via input
 * 
 * @author Diogo Fonseca a79858
 * @version 08/05/2024
 * 
 * @see ISnakeController
 * @see GameEngine.IInputListener
 */
public class InputSnakeController extends GameObject implements ISnakeController, IInputListener
{
	/**
	 * Represents a set of inputs that translate to snake movement
	*/
	public enum InputPreset
	{
		/**
		 * Uses:
		 * 'w' to go up
		 * 'a' to go left
		 * 'd' to go right
		 * 's' to go down
		 */
		WASD,

		/**
		 * Uses:
		 * 'k' to go up
		 * 'h' to go left
		 * 'l' to go right
		 * 'j' to go down
		 */
		VIM,

		/**
		 * Uses:
		 * 'up' or 'u' to go up
		 * 'left' or 'l' to go left
		 * 'right' or 'r' to go right
		 * 'down' or 'd' to go down
		 */
		ABSOLUTE,

		/**
		 * Uses:
		 * 'foward' or 'f' (or nothing) to walk foward
		 * 'left' or 'l' to turn left
		 * 'right' or 'r' to turn right
		 */
		RELATIVE,

		/**
		 * Uses:
		 * up arrow to go up
		 * left arrow to go left
		 * right arrow to go right
		 * down arrow to go down
		 */
		ARROW_KEYS,
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

	// ARROW_KEYS
	private static final int ARROW_KEYS_UP_KEYCODE = KeyEvent.VK_UP;
	private static final int ARROW_KEYS_DOWN_KEYCODE = KeyEvent.VK_DOWN;
	private static final int ARROW_KEYS_LEFT_KEYCODE = KeyEvent.VK_LEFT;
	private static final int ARROW_KEYS_RIGHT_KEYCODE = KeyEvent.VK_RIGHT;

	private Direction.TurnDirection nextDir;
	private InputPreset inputPreset;
	private ISnakeStats snake;

	/**
	 * Instantiates an InputSnakeController
	 * @param preset the preset to interpret input as
	 * @param snake the snake's properties
	 */
	public InputSnakeController(InputPreset preset, ISnakeStats snake)
	{
		this.inputPreset = preset;
		this.snake = snake;

		this.nextDir = Direction.TurnDirection.NONE;
	}

	@Override
	public Direction.TurnDirection nextTurn()
	{
		Direction.TurnDirection result = this.nextDir;
		this.nextDir = Direction.TurnDirection.NONE;
		return result;
	}

	@Override
	public void onInputReceived(String input)
	{
		interpretInput(input.toLowerCase());
	}

	@Override
	public void onKeyPressed(KeyEvent event)
	{
		interpretInput(Character.toString(event.getKeyChar()));
		interpretInput(event);
	}

	@Override
	public void onKeyReleased(KeyEvent event) { }

	@Override
	public void onKeyTyped(KeyEvent event) { }

	/**
	 * Interprets input from input string
	 * @param input the input string
	 */
	private void interpretInput(String input)
	{
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
			default:
				break;
		}
	}

	/**
	 * Interprets input from KeyEvents
	 * @param event the KeyEvent to interpret
	 */
	private void interpretInput(KeyEvent event)
	{
		Character.toString(event.getKeyChar());

		switch(inputPreset)
		{
			case ARROW_KEYS:
				turnArrows(event);
				break;
			default:
				break;
		}
	}

	/**
	 * Moves the snake with the wasd keys as input
	 * @param input the player input
	 */
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

	/**
	 * Moves the snake with the vim keybindings as input
	 * @param input the player input
	 */
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

	/**
	 * Moves the snake with absolute directions as input
	 * @param input the player input
	 */
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

	/**
	 * Moves the snake with relative turn directions as input
	 * @param input the player input
	 */
	private void turnRelative(String input)
	{
		switch(input)
		{
			case RELATIVE_LEFT_INPUT_STR_0:
			case RELATIVE_LEFT_INPUT_STR_1:
				this.nextDir = Direction.TurnDirection.LEFT;
				break;
			case RELATIVE_RIGHT_INPUT_STR_0:
			case RELATIVE_RIGHT_INPUT_STR_1:
				this.nextDir = Direction.TurnDirection.RIGHT;
				break;
			case RELATIVE_FOWARD_INPUT_STR_0:
			case RELATIVE_FOWARD_INPUT_STR_1:
				this.nextDir = Direction.TurnDirection.NONE;
				break;
		}
	}

	/**
	 * Moves the snake with the arrow keys
	 * @param event the player input
	 */
	private void turnArrows(KeyEvent event)
	{
		switch (event.getKeyCode())
		{
			case ARROW_KEYS_UP_KEYCODE:
				this.nextDir = Direction.getRelativeDir(Direction.UP, snake.direction());
				break;
			case ARROW_KEYS_DOWN_KEYCODE:
				this.nextDir = Direction.getRelativeDir(Direction.DOWN, snake.direction());
				break;
			case ARROW_KEYS_LEFT_KEYCODE:
				this.nextDir = Direction.getRelativeDir(Direction.LEFT, snake.direction());
				break;
			case ARROW_KEYS_RIGHT_KEYCODE:
				this.nextDir = Direction.getRelativeDir(Direction.RIGHT, snake.direction());
				break;
		}
	}
}
