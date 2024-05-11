package SnakeGame;

import Geometry.*;
import GameEngine.*;

/**
 * Represents an immovable obstacle the snake should avoid
 * 
 * @author Diogo Fonseca a79858
 * @version 09/05/2024
 * 
 * @inv the obstacle is immovable
 * @see IObstacle
 */
public class StaticObstacle extends GameObject implements IObstacle
{
	private static final int LAYER = 1;
	private RenderData<Polygon> rData;
	private IGeometricShape<Polygon> shape;
	private boolean isDeepCollision;

	/**
	 * Instantiates a StaticObstacle without colour
	 * @param obstacle the obstacle's shape
	 * @param isFilled if the obstacle should be drawn as filled
	 * @param drawChar the character the obstacle should be drawn with
	 */
	public StaticObstacle(Polygon obstacle, boolean isFilled, char drawChar)
	{
		this(obstacle, isFilled, drawChar, null);
	}

	/**
	 * Instantiates a StaticObstacle
	 * @param obstacle the 'hitbox' of the obstacle
	 * @param isFilled if the obstacle should be drawn as filled
	 * @param drawChar the character the obstacle should be drawn with
	 * @param colour the colour to draw the obstacle with (can be null)
	 */
	public StaticObstacle(Polygon obstacle, boolean isFilled, char drawChar, Colour.Foreground colour)
	{
		this.rData = new RenderData<Polygon>(obstacle, isFilled, LAYER, drawChar, colour);
		this.shape = obstacle;
		this.isDeepCollision = false;
	}

	@Override
	public RenderData<Polygon> getRenderData() { return this.rData; }

	@Override
	public void onCollision(GameObject other) { } // do nothing
	
	@Override
	public IGeometricShape<Polygon> getCollider() { return this.shape; }

	@Override
	public boolean isDeepCollision() { return this.isDeepCollision; }
}
