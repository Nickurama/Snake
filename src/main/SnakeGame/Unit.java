package SnakeGame;

import Geometry.*;
import GameEngine.*;

public class Unit extends GameObject implements IRenderable
{
	private static final double UNIT_OFFSET = 0.001;
	private Square unit;
	private int size;
	private RenderData<Square> rData;
	private Point position;

	// cannot use zero based coordinates
	public Unit(Point position, int size, boolean isFilled, char drawChar, int layer) throws SnakeGameException
	{
		this.position = position;
		this.size = size;
		generateUnit();
		this.rData = new RenderData<Square>(this.unit, isFilled, layer, drawChar);
	}

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

	public void setDrawChar(char newChar)
	{
		this.rData = new RenderData<Square>(this.rData.getShape(), this.rData.isFilled(), this.rData.getLayer(), newChar);
	}

	public Point position() { return this.position; }

	public RenderData<Square> getRenderData()
	{
		return this.rData = new RenderData<Square>(this.unit, this.rData.isFilled(), this.rData.getLayer(), this.rData.getCharacter());
	}

	public Square unitSquare() { return this.unit; }
}
