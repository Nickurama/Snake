package SnakeGame;

/**
 * Represents a class that will give where the snake should turn next
 * 
 * @author Diogo Fonseca a79858
 * @version 03/05/2024
 * 
 * @see Direction
 */
public interface ISnakeController
{
	public Direction.TurnDirection nextTurn();
}
