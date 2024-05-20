package SnakeGame;

import Geometry.*;

import java.awt.Color;
import java.util.LinkedList;
import java.util.Queue;

import GameEngine.*;

/**
 * Represents the snake
 * the snake has a series of SnakeUnits as it's body.
 * 
 * @author Diogo Fonseca a79858
 * @version 09/05/2024
 * 
 * @inv the minimum length for the snake is 1 (when it only has a head)
 * @inv the snake will not move untill {@link Snake#awake() awake} is called
 * @inv the snake will not move, eat or grow if is dead.
 * @see SnakeUnit
 */
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
	private TerminalColour.Foreground terminalColour;
	private Color graphicalColor;

	/**
	 * Instantiates a snake.
	 * same as {@link Snake#Snake(Point,Direction,int,boolean,char,char,TerminalColour.Foreground)} but with no colour
	 */
	public Snake(Point initialPos, Direction initialDir, int unitSize, boolean isFilled, char tailChar, char headChar) throws SnakeGameException
	{
		this(initialPos, initialDir, unitSize, isFilled, tailChar, headChar, null, null);
	}

	/**
	 * Instantiates a snake.
	 * @param initialPos the initial snake position
	 * @param initialDir the initial snake direction
	 * @param unitSize the size of one snake segment
	 * @param isFilled if the snake should be drawn with filled segments
	 * @param tailChar the character for the snake's tail
	 * @param headChar the character for the snake's head
	 * @param colour the colour of the snake
	 * @throws SnakeGameException if trying to instantiate a snake in an invalid position
	 */
	public Snake(Point initialPos, Direction initialDir, int unitSize, boolean isFilled, char tailChar, char headChar,
		TerminalColour.Foreground terminalColour, Color graphicalColor) throws SnakeGameException
	{
		this.terminalColour = terminalColour;
		this.graphicalColor = graphicalColor;
		this.units = new LinkedList<SnakeUnit>();
		this.unitSize = unitSize;
		this.currDir = initialDir;
		this.isFilled = isFilled;
		this.tailChar = tailChar;
		this.headChar = headChar;
		createHead(initialPos);
		this.isDead = false;
		this.length = 1;
		this.toGrow = 0;
		this.isAwake = false;
	}

	/**
	 * Sets a snake unit to be the snake's head
	 * @param unit the unit to set as the head
	 */
	private void setHead(SnakeUnit unit)
	{
		this.head.setHead(false);
		unit.setHead(true);
		this.head = unit;
	}

	/**
	 * Creates a snake unit as a head and appends it to the snake.
	 * Instantiates the snake unit in the scene
	 * @param initialPos the position of the head to create
	 * @throws SnakeGameException if the position is invalid
	 */
	private void createHead(Point initialPos) throws SnakeGameException
	{
		this.head = new SnakeUnit(this, initialPos, headChar);
		this.head.setTerminalDrawColour(this.terminalColour);
		this.head.setGraphicalDrawColor(this.graphicalColor);
		head.setHead(true);
		this.units.add(this.head);
		if (super.sceneHandle() != null)
			super.sceneHandle().add(this.head);
	}

	/**
	 * Kills the snake, setting it's state to dead.
	 */
	public void die()
	{
		if (isDead)
			return;

		this.isDead = true;
	}

	/**
	 * Moves the snake by one unit in the current direction set
	 * Grows the snake if it has eated food
	 */
	private void move()
	{
		head.setHead(false);

		if (toGrow > 0)
			growUnit();
		else
			swapTailWithHead();
		
		switch(currDir)
		{
			case UP:
				moveUp();
				break;
			case DOWN:
				moveDown();
				break;
			case LEFT:
				moveLeft();
				break;
			case RIGHT:
				moveRight();
				break;
		}
	}

	/**
	 * Grows the snake by one unit
	 */
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

	/**
	 * Swaps the last tail unit with the head unit, setting it as the new head
	 */
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

	/**
	 * Moves the snake's head up by one unit
	 */
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

	/**
	 * Moves the snake's head down by one unit
	 */
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

	/**
	 * Moves the snake's head left by one unit
	 */
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

	/**
	 * Moves the snake's head right by one unit
	 */
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

	/**
	 * Makes the snake turn left on next update;
	 */
	public void turnLeft()
	{
		switch(currDir)
		{
			case UP:
				currDir = Direction.LEFT;
				break;
			case DOWN:
				currDir = Direction.RIGHT;
				break;
			case LEFT:
				currDir = Direction.DOWN;
				break;
			case RIGHT:
				currDir = Direction.UP;
				break;
		}
	}

	/**
	 * Makes the snake turn right on next update;
	 */
	public void turnRight()
	{
		switch(currDir)
		{
			case UP:
				currDir = Direction.RIGHT;
				break;
			case DOWN:
				currDir = Direction.LEFT;
				break;
			case LEFT:
				currDir = Direction.UP;
				break;
			case RIGHT:
				currDir = Direction.DOWN;
				break;
		}
	}

	/**
	 * Awakes the snake, making it start moving
	 */
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

	/**
	 * Eats a food, consuming it.
	 * Grows the snake on next update.
	 * @param food the food to eat
	 */
	public void eat(IFood food)
	{
		if (isDead)
			return;

		food.consume();
		grow();
	}

	/**
	 * Sets the snake to grow on next update
	 */
	public void grow()
	{
		this.length++;
		this.toGrow++;
	}

	/**
	 * The snake's unit size
	 * @return the snake's unit size
	 */
	public int unitSize() { return this.unitSize; }

	/**
	 * If the snake should be filled
	 * @return if the snake should be filled
	 */
	public boolean isFilled() { return this.isFilled; }

	/**
	 * If the snake is dead
	 * @return if the snake is dead
	 */
	public boolean isDead() { return this.isDead; }

	/**
	 * The snake's direction
	 * @return the snake's direction
	 */
	public Direction direction() { return this.currDir; }

	/**
	 * The snake's current length
	 * @return the snake's current length
	 */
	public int length() { return this.length; }

	/**
	 * The snake's head character
	 * @return the snake's head character
	 */
	public char headChar() { return this.headChar; }

	/**
	 * The snake's tail character
	 * @return the snake's tail character
	 */
	public char tailChar() { return this.tailChar; }
	
	@Override
	public Point position() { return this.head.position(); }
}
