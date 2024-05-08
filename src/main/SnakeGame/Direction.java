package SnakeGame;

import SnakeGame.ISnakeController.*;

public enum Direction
{
	UP,
	LEFT,
	DOWN,
	RIGHT;

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

	private static boolean isLeftTurn(Direction absoluteDir, Direction currentDir)
	{
		switch (currentDir)
		{
			case Direction.UP:
				if (absoluteDir.equals(Direction.LEFT))
					return true;
				break;
			case Direction.DOWN:
				if (absoluteDir.equals(Direction.RIGHT))
					return true;
				break;
			case Direction.LEFT:
				if (absoluteDir.equals(Direction.DOWN))
					return true;
				break;
			case Direction.RIGHT:
				if (absoluteDir.equals(Direction.UP))
					return true;
				break;
		}
		return false;
	}

	private static boolean isRightTurn(Direction absoluteDir, Direction currentDir)
	{
		switch (currentDir)
		{
			case Direction.UP:
				if (absoluteDir.equals(Direction.RIGHT))
					return true;
				break;
			case Direction.DOWN:
				if (absoluteDir.equals(Direction.LEFT))
					return true;
				break;
			case Direction.LEFT:
				if (absoluteDir.equals(Direction.UP))
					return true;
				break;
			case Direction.RIGHT:
				if (absoluteDir.equals(Direction.DOWN))
					return true;
				break;
		}
		return false;
	}

}
