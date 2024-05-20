package SnakeGame;

import GameEngine.Logger;

/**
 * Represents a direction in the snake game, being only
 * possible the 4 cardinal directions
 * 
 * @author Diogo Fonseca a79858
 * @version 08/05/2024
 */
public enum Direction
{
	UP,
	LEFT,
	DOWN,
	RIGHT;

	/**
	 * Represents a turning direction, being only possible to turn
	 * left, right or not turn
	*/
	public enum TurnDirection
	{
		NONE,
		LEFT,
		RIGHT
	};

	/**
	 * Gets the opposite of the given direction
	 * @param dir the direction to find the opposite of
	 * @return the opposite of the given direction
	 */
	public static Direction opposite(Direction dir)
	{
		Direction opposite;
		switch (dir)
		{
			case UP:
				opposite = DOWN;
				break;
			case DOWN:
				opposite = UP;
				break;
			case LEFT:
				opposite = RIGHT;
				break;
			case RIGHT:
				opposite = LEFT;
				break;
			default:
				Logger.log(Logger.Level.FATAL, "Unrecognized direction.");
				throw new RuntimeException("Unrecognized direction.");
		}
		return opposite;
	}

	/**
	 * Gets the relative turning direction,
	 * from the absolute direction desired to turn to
	 * and the current direction
	 * @param absoluteDir the absolute direction to turn to
	 * @param currentDir the current direction
	 * @return the relative turning direction
	 */
	public static TurnDirection getRelativeDir(Direction absoluteDir, Direction currentDir)
	{
		TurnDirection result = TurnDirection.NONE;

		if (absoluteDir == null)
			return TurnDirection.NONE;
		else if (isLeftTurn(absoluteDir, currentDir))
			result = TurnDirection.LEFT;
		else if (isRightTurn(absoluteDir, currentDir))
			result = TurnDirection.RIGHT;

		return result;
	}

	/**
	 * Checks from an absolute direction, and the current direction
	 * if one relative to the other means a left turn
	 * @param absoluteDir the absolute direction
	 * @param currentDir the current direction
	 * @return if changing the current direction to the absolute direction means a left turn
	 */
	private static boolean isLeftTurn(Direction absoluteDir, Direction currentDir)
	{
		switch (currentDir)
		{
			case UP:
				if (absoluteDir.equals(LEFT))
					return true;
				break;
			case DOWN:
				if (absoluteDir.equals(RIGHT))
					return true;
				break;
			case LEFT:
				if (absoluteDir.equals(Direction.DOWN))
					return true;
				break;
			case RIGHT:
				if (absoluteDir.equals(Direction.UP))
					return true;
				break;
		}
		return false;
	}

	/**
	 * Checks from an absolute direction, and the current direction
	 * if one relative to the other means a right turn
	 * @param absoluteDir the absolute direction
	 * @param currentDir the current direction
	 * @return if changing the current direction to the absolute direction means a right turn
	 */
	private static boolean isRightTurn(Direction absoluteDir, Direction currentDir)
	{
		switch (currentDir)
		{
			case UP:
				if (absoluteDir.equals(Direction.RIGHT))
					return true;
				break;
			case DOWN:
				if (absoluteDir.equals(Direction.LEFT))
					return true;
				break;
			case LEFT:
				if (absoluteDir.equals(Direction.UP))
					return true;
				break;
			case RIGHT:
				if (absoluteDir.equals(Direction.DOWN))
					return true;
				break;
		}
		return false;
	}

}
