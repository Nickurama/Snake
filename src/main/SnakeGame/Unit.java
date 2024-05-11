package SnakeGame;

import Geometry.*;
import GameEngine.*;

/**
 * Represents a unit:
 * A square that holds a position, can be moved and can be rendered.
 *
 * To be sure the square is rendered with the same ammount of characters
 * as it's size, the square is placed at a 1/2 offset from the grid, making it
 * contain size * size integer values in the coordinate space
 * 
 * As an example, a unit of of size 2 placed at (1.5,1.5) will contain the coordinates
 * (1,1) (2,1) (1, 2) (2, 2)
 *
 * The unit must be placed in the appropriate position to hold that property.
 * it's coordinates must end in .5 when the size is even and they must be
 * a whole number when the size is odd
 * 
 * @author Diogo Fonseca a79858
 * @version 06/05/2024
 *
 * @see Geometry.Square
 * @see GameEngine.IRenderable
 * @see ISpatialComponent
 */
public class Unit extends GameObject implements IRenderable, ISpatialComponent
{
	public static final double UNIT_OFFSET = 0.001;
	private Square unit;
	private double size;
	private RenderData<Square> rData;
	private Point position;

	/**
	 * Instantiates a unit.
	 * Same as {@link Unit#Unit(Point,double,boolean,char,Colour.Foreground,int)} but with no colour.
	 */
	public Unit(Point position, double size, boolean isFilled, char drawChar, int layer) throws SnakeGameException
	{
		this(position, size, isFilled, drawChar, null, layer);
	}

	/**
	 * Instantiates a unit.
	 * Cannot use zero-based coordinates.
	 * @param position the position to instantiate the unit at (should end in .5 for even sizes, but otherwise is supported)
	 * @param size the length of each side
	 * @param isFilled if the unit should be filled
	 * @param drawChar the character to draw the unit with
	 * @param colour the colour to draw the unit with (can be null)
	 * @param layer the layer to draw the unit in
	 * @throws SnakeGameException if it's placed in an invalid position (including zero-based coordiantes)
	 * @pre size > 0
	 */
	public Unit(Point position, double size, boolean isFilled, char drawChar, Colour.Foreground colour, int layer) throws SnakeGameException
	{
		this.position = position;
		this.size = size;
		generateUnit();
		this.rData = new RenderData<Square>(this.unit, isFilled, layer, drawChar, colour);
	}

	/**
	 * generates the unit
	 * @throws SnakeGameException if the unit is in an invalid position
	 */
	private void generateUnit() throws SnakeGameException
	{
		if (this.size <= 0)
		{
			Logger.log(Logger.Level.ERROR, "Unit size cannot be 0 or lower.");
			throw new SnakeGameException("Unit size cannot be 0 or lower.");
		}

		try
		{
			double unitSize = this.size - UNIT_OFFSET;
			unit = new Square(new Point[] {
				new Point(UNIT_OFFSET, UNIT_OFFSET),
				new Point(UNIT_OFFSET, unitSize),
				new Point(unitSize, unitSize),
				new Point(unitSize, UNIT_OFFSET),
			});
		}
		catch (GeometricException e)
		{
			Logger.log(Logger.Level.FATAL, "Error generating Unit\n" + e);
			throw new Error("Shouldn't happen... with Unit size > 0, the square should always be valid");
		}

		move(this.position);
	}

	/**
	 * Moves the unit to a new position
	 * @param newPos the position to move the unit to
	 * @throws SnakeGameException if the unit is moved to an invalid position
	 */
	public void move(Point newPos) throws SnakeGameException
	{
		try
		{
			this.unit = unit.moveCentroid(newPos);
			this.position = newPos;
		}
		catch (GeometricException e)
		{
			Logger.log(Logger.Level.ERROR, "Unit was placed in a position where a coordinate was negative.\n" + e);
			throw new SnakeGameException("Unit was placed in a position where a coordinate was negative.");
		}
	}

	/**
	 * Sets the character to draw the unit with
	 * @param newChar the character to draw the unit with
	 */
	public void setDrawChar(char newChar)
	{
		this.rData = new RenderData<Square>(this.rData.getShape(), this.rData.isFilled(), this.rData.getLayer(), newChar, this.rData.getColour());
	}

	/**
	 * Sets the colour to draw the unit with
	 * @param newColour the colour to draw the unit with
	 */
	public void setDrawColour(Colour.Foreground newColour)
	{
		this.rData = new RenderData<Square>(this.rData.getShape(), this.rData.isFilled(), this.rData.getLayer(), this.rData.getCharacter(), newColour);
	}

	@Override
	public Point position() { return this.position; }

	@Override
	public RenderData<Square> getRenderData()
	{
		return this.rData = new RenderData<Square>(this.unit, this.rData.isFilled(), this.rData.getLayer(), this.rData.getCharacter(), this.rData.getColour());
	}

	/**
	 * The square that represents this unit
	 * @return the square that represents this unit
	 */
	public Square unitSquare() { return this.unit; }

	/**
	 * The size of the unit
	 * @return the size of the unit
	 */
	public double size() { return this.size; }
}
