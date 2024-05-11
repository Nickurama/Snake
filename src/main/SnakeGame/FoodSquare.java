package SnakeGame;

import GameEngine.*;
import Geometry.*;

/**
 * Represents a food item that is a square.
 * 
 * @author Diogo Fonseca a79858
 * @version 09/05/2024
 * 
 * @inv is removed from it's scene when consumed
 * @see IFood
 * @see Unit
 */
public class FoodSquare extends Unit implements IFood
{
	private static final int LAYER = 1;
	private static final boolean IS_DEEP_COLLISION = true;

	private boolean wasConsumed;

	/**
	 * Initializes a FoodSquare without any colour
	 * @param position the position to spawn the food in
	 * @param size the size of the food
	 * @param isFilled if the food should be drawn filled
	 * @param drawChar the character the food should be drawn with
	 * @throws SnakeGameException if it's placed in an invalid position or if size is invalid
	 * @pre size > 0
	 */
	public FoodSquare(Point position, double size, boolean isFilled, char drawChar) throws SnakeGameException
	{
		this(position, size, isFilled, drawChar, null);
	}

	/**
	 * Initializes a FoodSquare without any colour
	 * @param position the position to spawn the food in
	 * @param size the size of the food
	 * @param isFilled if the food should be drawn filled
	 * @param drawChar the character the food should be drawn with
	 * @param colour the colour the food should be drawn with (can be null)
	 * @throws SnakeGameException if it's placed in an invalid position
	 */
	public FoodSquare(Point position, double size, boolean isFilled, char drawChar, Colour.Foreground colour) throws SnakeGameException
	{
		super(position, size, isFilled, drawChar, colour, LAYER);
		this.wasConsumed = false;
	}

	@Override
	public void consume()
	{
		super.sceneHandle().remove(this);
		this.wasConsumed = true;
	}

	@Override
	public boolean wasConsumed() { return this.wasConsumed; }

	@Override
	public IGeometricShape<Polygon> getCollider() { return super.unitSquare(); }
	
	@Override
	public void onCollision(GameObject other) { }

	@Override
	public boolean isDeepCollision() { return IS_DEEP_COLLISION; }
}
