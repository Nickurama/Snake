package SnakeGame;

import Geometry.*;
import GameEngine.*;

/**
 * Represents a unit of a snake
 * 
 * @author Diogo Fonseca a79858
 * @version 06/05/2024
 * 
 * @inv isHead should be true if the current SnakeUnit is the head of the {@link Snake snake}
 * @see Snake
 */
public class SnakeUnit extends Unit implements ICollider
{
	private Snake snakeHandle;
	private boolean isDeepCollision;
	private boolean isHead;

	/**
	 * Instantiates a SnakeUnit
	 * @param snake the snake it belongs to
	 * @param position the position it should be instantiated in 
	 * @param drawChar the character the unit should be drawn with
	 * @throws SnakeGameException if the unit is positioned in an invalid position
	 */
	public SnakeUnit(Snake snake, Point position, char drawChar) throws SnakeGameException
	{
		super(position, snake.unitSize(), snake.isFilled(), drawChar, Snake.LAYER);
		this.snakeHandle = snake;
		this.isDeepCollision = true;
	}

	/**
	 * Sets the current SnakeUnit as the {@link Snake Snake}'s head or not
	 * @param isHead if the current SnakeUnit should be interpreted as the head or not
	 */
	public void setHead(boolean isHead)
	{
		this.isHead = isHead;
		char drawChar = isHead ? this.snakeHandle.headChar() : this.snakeHandle.tailChar();
		this.setDrawChar(drawChar);
	}

	@Override
	public IGeometricShape<Polygon> getCollider() { return super.unitSquare(); }
	
	@Override
	public void onCollision(GameObject other)
	{
		if (other instanceof SnakeUnit || other instanceof IObstacle)
			snakeHandle.die();
		else if (this.isHead && other instanceof IFood && (this.getCollider().contains(((IFood)other).getCollider())))
			snakeHandle.eat((IFood)other);
	}

	@Override
	public boolean isDeepCollision() { return this.isDeepCollision; }
}
