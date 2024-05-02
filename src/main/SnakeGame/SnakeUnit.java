package SnakeGame;

import Geometry.*;
import GameEngine.*;

public class SnakeUnit extends Unit implements ICollider
{
	private Snake snakeHandle;

	public SnakeUnit(Snake snake, Point position, char drawChar) throws SnakeGameException
	{
		super(position, snake.unitSize(), snake.isFilled(), drawChar, Snake.LAYER);
		this.snakeHandle = snake;
	}

	public GameObject getGameObject() { return this; }

	public IGeometricShape<Polygon> getCollider() { return super.unitSquare(); }
	
	public void onCollision(GameObject other)
	{
		if (other instanceof SnakeUnit || other instanceof IObstacle)
			snakeHandle.die();
	}
}
