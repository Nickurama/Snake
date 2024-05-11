package SnakeGame;

import Geometry.*;
import GameEngine.*;

/**
 * Represents an obstacle that is rotating in a periodic motion
 * 
 * @author Diogo Fonseca a79858
 * @version 09/05/2024
 * 
 * @inv the speed of rotation / the period is not affected by framerate
 * @see IObstacle
 */
public class DynamicObstacle extends GameObject implements IObstacle
{
	private static final int LAYER = 1;
	private VirtualPoint rotationPoint;
	private float speed;
	private RenderData<Polygon> rData;
	private IGeometricShape<Polygon> collider;
	private boolean isDeepCollision;
	
	/**
	 * Initializes a DynamicObstacle
	 * @param obstacle the obstacle's shape
	 * @param isFilled if the obstacle should be drawn filled
	 * @param drawChar the character to draw the obstacle with
	 * @param rotationPoint the point the obstacle will rotate around (can be null, rotating around it's centroid)
	 * @param speed the speed the obstacle should rotate at (rad/ms)
	 * @param colour the colour the obstacle should be drawn with
	 */
	public DynamicObstacle(Polygon obstacle, boolean isFilled, char drawChar, VirtualPoint rotationPoint, float speed, Colour.Foreground colour)
	{
		this.rData = new RenderData<Polygon>(obstacle, isFilled, LAYER, drawChar, colour);
		this.collider = obstacle;
		this.rotationPoint = rotationPoint;
		if (rotationPoint == null)
			this.rotationPoint = obstacle.getCentroid();
		this.speed = speed;
		this.isDeepCollision = false;
	}

	/**
	 * Initializes a DynamicObstacle without colour
	 * @param obstacle the obstacle's shape
	 * @param isFilled if the obstacle should be drawn filled
	 * @param drawChar the character to draw the obstacle with
	 * @param rotationPoint the point the obstacle will rotate around (can be null, rotating around it's centroid)
	 * @param speed the speed the obstacle should rotate at (rad/ms)
	 */
	public DynamicObstacle(Polygon obstacle, boolean isFilled, char drawChar, VirtualPoint rotationPoint, float speed)
	{
		this(obstacle, isFilled, drawChar, rotationPoint, speed, null);
	}

	/**
	 * Initializes a colorless DynamicObstacle rotating around it's centroid
	 * @param obstacle the obstacle's shape
	 * @param isFilled if the obstacle should be drawn filled
	 * @param drawChar the character to draw the obstacle with
	 * @param speed the speed the obstacle should rotate at (rad/ms)
	 */
	public DynamicObstacle(Polygon obstacle, boolean isFilled, char drawChar, float speed)
	{
		this(obstacle, isFilled, drawChar, null, speed);
	}

	/**
	 * Copy constructor
	 * @param obstacle the dynamic obstacle to copy
	 */
	public DynamicObstacle(DynamicObstacle obstacle)
	{
		this((Polygon)obstacle.collider, obstacle.rData.isFilled(), obstacle.rData.getCharacter(), obstacle.rotationPoint, obstacle.speed, obstacle.rData.getColour());
	}

	@Override
	public void update(int deltaT)
	{
		rotate(deltaT * this.speed);
	}

	/**
	 * Rotates the obstacle
	 * @param radians the angle to rotate the obstacle by
	 * @throws GeometricException if the obstacle was rotated to an invalid position
	 */
	private void rotate(float radians)
	{
		try
		{
			this.collider = collider.rotate(radians, this.rotationPoint);
		}
		catch (GeometricException e)
		{
			Logger.log(Logger.Level.FATAL, "Dynamic Obstacle rotated to an invalid coordinate.\n" + e);
			throw new RuntimeException("Dynamic Obstacle rotated to an invalid coordinate.");
		}
	}

	@Override
	public RenderData<Polygon> getRenderData()
	{
		this.rData = new RenderData<Polygon>((Polygon)this.collider, this.rData.isFilled(), this.rData.getLayer(), this.rData.getCharacter(), this.rData.getColour());
		return this.rData;
	}

	@Override
	public void onCollision(GameObject other) { } // do nothing
	
	@Override
	public IGeometricShape<Polygon> getCollider() { return this.collider; }

	@Override
	public boolean isDeepCollision() { return this.isDeepCollision; }

	/**
	 * The speed at which the object is rotating at
	 * @return the speed at which the object is rotating at
	 */
	public float speed() { return this.speed; }

	/**
	 * The point the object is rotating around
	 * @return the point the object is rotating around
	 */
	public VirtualPoint rotationPoint() { return this.rotationPoint; }
}
