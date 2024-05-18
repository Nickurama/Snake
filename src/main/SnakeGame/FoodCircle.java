package SnakeGame;

import java.awt.Color;

import GameEngine.*;
import Geometry.*;

/**
 * Represents a food item that is a circle.
 * 
 * @author Diogo Fonseca a79858
 * @version 09/05/2024
 * 
 * @inv is removed from it's scene when consumed
 * @see IFood
 */
public class FoodCircle extends GameObject implements IFood
{
	private static final double CIRCLE_OFFSET = 0.1;
	private static final int LAYER = 1;

	private RenderData<?> rData;
	private IGeometricShape<Circle> collider;
	private boolean isDeepCollision;
	private boolean wasConsumed;

	/**
	 * Initializes a FoodCircle without colour
	 * @param position the position to spawn the food in
	 * @param radius the radius of the circle
	 * @param isFilled if the circle should be filled
	 * @param drawChar the character the circle should be drawn with
	 * @throws SnakeGameException if placed in an invalid position or if it doesn't describe a circle
	 * @pre radius > 0
	 */
	public FoodCircle(Point position, double radius, boolean isFilled, char drawChar) throws SnakeGameException
	{
		this(position, radius, isFilled, drawChar, null, null);
	}

	/**
	 * Initializes a FoodCircle
	 * @param position the position to spawn the food in
	 * @param radius the radius of the circle
	 * @param isFilled if the circle should be filled
	 * @param drawChar the character the circle should be drawn with
	 * @param terminalColor the colour to draw the circle with in the terminal (can be null)
	 * @param graphicalColor the colour to draw the circle with in the graphical interface (can be null)
	 * @throws SnakeGameException if placed in an invalid position or if it doesn't describe a circle
	 * @pre radius > 0
	 */
	public FoodCircle(Point position, double radius, boolean isFilled, char drawChar, TerminalColour.Foreground terminalColor, Color graphicalColor) throws SnakeGameException
	{
		Circle circle = null;

		try
		{
			circle = new Circle(position, radius - Unit.UNIT_OFFSET - CIRCLE_OFFSET);
			if (radius <= 1)
				this.rData = new FoodSquare(position, radius * 2, isFilled, drawChar, terminalColor, graphicalColor).getRenderData();
			else
				this.rData = new RenderData<Circle>(circle, isFilled, LAYER, drawChar, terminalColor, graphicalColor);
		}
		catch (GeometricException e)
		{
			Logger.log(Logger.Level.FATAL, "Error creating the food circle.\n" + e);
			throw new RuntimeException("Error creating the food circle: " + e.getMessage());
		}

		this.collider = circle;
		this.isDeepCollision = true;
		this.wasConsumed = false;
	}

	@Override
	public void consume()
	{
		super.sceneHandle().remove(this);
		this.wasConsumed = true;
	}

	@Override
	public Point position() { return this.collider.getCentroid(); }

	@Override
	public boolean wasConsumed() { return this.wasConsumed; }

	@Override
	public RenderData<?> getRenderData() { return this.rData; }

	@Override
	public IGeometricShape<Circle> getCollider() { return this.collider; }
	
	@Override
	public void onCollision(GameObject other) { }

	@Override
	public boolean isDeepCollision() { return this.isDeepCollision; }
}
