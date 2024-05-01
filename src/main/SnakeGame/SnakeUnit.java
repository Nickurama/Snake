package SnakeGame;

import Geometry.*;
import GameEngine.*;

public class SnakeUnit extends GameObject implements ICollider, IRenderable
{
	private static final double UNIT_OFFSET = 0.001;
	private Snake snakeHandle;
	private Square unit;
	private RenderData<Square> rData;
	private Point position;

	public SnakeUnit(Snake snake, Point position, char drawChar) throws SnakeGameException
	{
		this.snakeHandle = snake;
		this.position = position;
		generateUnit();
		this.rData = new RenderData<Square>(unit, snake.isFilled(), Snake.LAYER, drawChar);
	}

	private void generateUnit() throws SnakeGameException
	{
		if (snakeHandle.unitSize() <= 0)
		{
			Logger.log(Logger.Level.ERROR, "Snake unit size cannot be 0 or lower.");
			throw new SnakeGameException("Snake unit size cannot be 0 or lower.");
		}

		try
		{
			double unitSize = snakeHandle.unitSize() - UNIT_OFFSET;
			unit = new Square(new Point[] {
				new Point(UNIT_OFFSET, UNIT_OFFSET),
				new Point(UNIT_OFFSET, unitSize),
				new Point(unitSize, unitSize),
				new Point(unitSize, UNIT_OFFSET),
			});
		}
		catch (GeometricException e)
		{
			Logger.log(Logger.Level.FATAL, "Error generating SnakeUnit unit\n" + e);
			throw new Error("Shouldn't happen... with unitSize > 0, the square should always be valid");
		}

		move(this.position);
	}

	public void move(Point newPos) throws SnakeGameException
	{
		try
		{
			this.unit = unit.moveCentroid(newPos);
			this.position = newPos;
		}
		catch (GeometricException e)
		{
			Logger.log(Logger.Level.ERROR, "Snake Unit was placed in a position where a coordinate was negative.\n" + e);
			throw new SnakeGameException("Snake Unit was placed in a position where a coordinate was negative.");
		}
	}

	public Point position() { return this.position; }

	public RenderData<Square> getRenderData()
	{
		return this.rData = new RenderData<Square>(unit, this.rData.isFilled(), Snake.LAYER, this.rData.getCharacter());
	}

	public GameObject getGameObject() { return this; }

	public IGeometricShape<Polygon> getCollider() { return this.unit; }
	
	public void onCollision(GameObject other)
	{
		if (other instanceof SnakeUnit || other instanceof IObstacle)
			snakeHandle.die();
	}
}
