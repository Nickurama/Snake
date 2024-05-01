package SnakeGame;

import Geometry.*;
import GameEngine.*;

public class Snake extends GameObject
{
	public static enum Direction
	{
		UP,
		RIGHT,
		DOWN,
		LEFT,
	};

	protected static final int LAYER = 2;
	private boolean isFilled;
	private char tailChar;
	private char headChar;
	private int unitSize;
	private boolean isDead;
	private SnakeUnit head;
	private Direction currDir;

	public Snake(Point initialPos, Direction currDir, int unitSize, boolean isFilled, char tailChar, char headChar) throws SnakeGameException
	{
		this.unitSize = unitSize;
		this.currDir = currDir;
		this.isFilled = isFilled;
		this.tailChar = tailChar;
		this.headChar = headChar;
		this.head = new SnakeUnit(this, initialPos, headChar);
		this.isDead = false;
	}

	public void die()
	{
		if (isDead)
			return;

		this.isDead = true;
	}

	private void move()
	{
		switch(currDir)
		{
			case Direction.UP:
				moveUp();
				break;
			case Direction.DOWN:
				moveDown();
				break;
			case Direction.LEFT:
				moveLeft();
				break;
			case Direction.RIGHT:
				moveRight();
				break;
		}
	}

	private void moveUp()
	{
		try
		{
			head.move(new Point(head.position().X(), head.position().Y() + this.unitSize()));
		}
		catch (Exception e)
		{
			Logger.log(Logger.Level.FATAL, "Error while moving up.\n" + e);
			throw new RuntimeException("Error while moving: " + e.getMessage());
		}
	}

	private void moveDown()
	{
		try
		{
			head.move(new Point(head.position().X(), head.position().Y() - this.unitSize()));
		}
		catch (Exception e)
		{
			Logger.log(Logger.Level.FATAL, "Error while moving up.\n" + e);
			throw new RuntimeException("Error while moving: " + e.getMessage());
		}
	}

	private void moveLeft()
	{
		try
		{
			head.move(new Point(head.position().X() - this.unitSize(), head.position().Y()));
		}
		catch (Exception e)
		{
			Logger.log(Logger.Level.FATAL, "Error while moving up.\n" + e);
			throw new RuntimeException("Error while moving: " + e.getMessage());
		}
	}

	private void moveRight()
	{
		try
		{
			head.move(new Point(head.position().X() + this.unitSize(), head.position().Y()));
		}
		catch (Exception e)
		{
			Logger.log(Logger.Level.FATAL, "Error while moving up.\n" + e);
			throw new RuntimeException("Error while moving: " + e.getMessage());
		}
	}

	public void turnLeft()
	{
		switch(currDir)
		{
			case Direction.UP:
				currDir = Direction.LEFT;
				break;
			case Direction.DOWN:
				currDir = Direction.RIGHT;
				break;
			case Direction.LEFT:
				currDir = Direction.DOWN;
				break;
			case Direction.RIGHT:
				currDir = Direction.UP;
				break;
		}
	}

	public void turnRight()
	{
		switch(currDir)
		{
			case Direction.UP:
				currDir = Direction.RIGHT;
				break;
			case Direction.DOWN:
				currDir = Direction.LEFT;
				break;
			case Direction.LEFT:
				currDir = Direction.UP;
				break;
			case Direction.RIGHT:
				currDir = Direction.DOWN;
				break;
		}
	}

	@Override
	public void start()
	{
		super.sceneHandle.add(this.head);
	}

	@Override
	public void update(int deltaT)
	{
		move();
	}

	public int unitSize() { return this.unitSize; }

	public boolean isFilled() { return this.isFilled; }

	public boolean isDead() { return this.isDead; }

	public Direction direction() { return this.currDir; }
}
