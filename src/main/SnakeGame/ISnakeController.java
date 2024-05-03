package SnakeGame;

public interface ISnakeController
{
	public static enum TurnDirection
	{
		NONE,
		LEFT,
		RIGHT
	};
	public TurnDirection nextTurn();
}
