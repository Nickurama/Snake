package SnakeGame;

import Geometry.*;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import GameEngine.*;

public class Snake extends GameObject implements ISpatialComponent
{
	protected static final int LAYER = 2;
	private boolean isFilled;
	private char tailChar;
	private char headChar;
	private int unitSize;
	private boolean isDead;
	private SnakeUnit head;
	private Direction currDir;
	private int length;
	private int toGrow;
	private boolean isAwake;
	private Queue<SnakeUnit> units;

	public Snake(Point initialPos, Direction currDir, int unitSize, boolean isFilled, char tailChar, char headChar) throws SnakeGameException
	{
		this.units = new LinkedList<SnakeUnit>();
		this.unitSize = unitSize;
		this.currDir = currDir;
		this.isFilled = isFilled;
		this.tailChar = tailChar;
		this.headChar = headChar;
		createHead(initialPos);
		this.isDead = false;
		this.length = 1;
		this.toGrow = 0;
		this.isAwake = false;
	}

	private void setHead(SnakeUnit unit)
	{
		this.head.setHead(false);
		unit.setHead(true);
		this.head = unit;
	}

	private void createHead(Point initialPos) throws SnakeGameException
	{
		this.head = new SnakeUnit(this, initialPos, headChar);
		head.setHead(true);
		this.units.add(this.head);
		if (super.sceneHandle() != null)
			super.sceneHandle().add(this.head);
	}

	public void die()
	{
		if (isDead)
			return;

		this.isDead = true;
	}

	private void move()
	{
		head.setHead(false);

		if (toGrow > 0)
			growUnit();
		else
			swapTailWithHead();
		
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

	private void growUnit()
	{
		try
		{
			createHead(head.position());
			toGrow--;
		}
		catch (SnakeGameException e)
		{
			Logger.log(Logger.Level.FATAL, "Should not happen. there is already a snake unit here therefore it can be created.\n" + e);
			throw new RuntimeException("Should not happen. there is already a snake unit here therefore it can be created.");
		}
	}

	private void swapTailWithHead()
	{
		SnakeUnit newHead = units.remove();

		try
		{
			newHead.move(head.position());
		}
		catch(SnakeGameException e)
		{
			Logger.log(Logger.Level.ERROR, "Snake encountered an error while trying to move.\n" + e);
			throw new RuntimeException("Snake encountered an error while trying to move: " + e.getMessage());
		}

		setHead(newHead);
		units.add(newHead);
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
			throw new RuntimeException("Error while moving up: " + e.getMessage());
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
			Logger.log(Logger.Level.FATAL, "Error while moving down.\n" + e);
			throw new RuntimeException("Error while moving down: " + e.getMessage());
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
			Logger.log(Logger.Level.FATAL, "Error while moving left.\n" + e);
			throw new RuntimeException("Error while moving left: " + e.getMessage());
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
			Logger.log(Logger.Level.FATAL, "Error while moving right.\n" + e);
			throw new RuntimeException("Error while moving right: " + e.getMessage());
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

	public void awake()
	{
		this.isAwake = true;
	}

	@Override
	public void initialize()
	{
		super.sceneHandle().add(this.head);
	}

	@Override
	public void start()
	{
	}

	@Override
	public void update(int deltaT)
	{
		if (!isDead && isAwake)
			move();
	}

	public void eat(IFood food)
	{
		if (isDead)
			return;

		food.consume();
		grow();
	}

	public void grow()
	{
		this.length++;
		this.toGrow++;
	}

	public int unitSize() { return this.unitSize; }

	public boolean isFilled() { return this.isFilled; }

	public boolean isDead() { return this.isDead; }

	public Direction direction() { return this.currDir; }

	public int length() { return this.length; }

	public char headChar() { return this.headChar; }

	public char tailChar() { return this.tailChar; }
	
	public Point position() { return this.head.position(); }
}
