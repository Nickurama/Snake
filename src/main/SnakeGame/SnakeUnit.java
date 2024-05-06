package SnakeGame;

import Geometry.*;
import GameEngine.*;

public class SnakeUnit extends Unit implements ICollider
{
	private Snake snakeHandle;
	private boolean isDeepCollision;
	private boolean isHead;

	public SnakeUnit(Snake snake, Point position, char drawChar) throws SnakeGameException
	{
		super(position, snake.unitSize(), snake.isFilled(), drawChar, Snake.LAYER);
		this.snakeHandle = snake;
		this.isDeepCollision = true;
	}

	public GameObject getGameObject() { return this; }

	public IGeometricShape<Polygon> getCollider() { return super.unitSquare(); }
	
	public void onCollision(GameObject other)
	{
		if (other instanceof SnakeUnit || other instanceof IObstacle)
			snakeHandle.die();
		else if (this.isHead && other instanceof IFood && (this.getCollider().contains(((IFood)other).getCollider())))
			snakeHandle.eat((IFood)other);
	}

	public void setHead(boolean isHead)
	{
		this.isHead = isHead;
		char drawChar = isHead ? this.snakeHandle.headChar() : this.snakeHandle.tailChar();
		this.setDrawChar(drawChar);
	}

	public boolean isDeepCollision() { return this.isDeepCollision; }
}
