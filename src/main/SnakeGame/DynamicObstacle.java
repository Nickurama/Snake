package SnakeGame;

import Geometry.*;
import GameEngine.*;

public class DynamicObstacle extends GameObject implements IObstacle
{
	private static final int LAYER = 1;
	private VirtualPoint rotationPoint;
	private float speed;
	private RenderData<Polygon> rData;
	private IGeometricShape<Polygon> collider;
	
	public DynamicObstacle(Polygon obstacle, boolean isFilled, char drawChar, VirtualPoint rotationPoint, float speed)
	{
		this.rData = new RenderData<Polygon>(obstacle, isFilled, LAYER, drawChar);
		this.collider = obstacle;
		this.rotationPoint = rotationPoint;
		this.speed = speed;
	}

	public DynamicObstacle(Polygon obstacle, boolean isFilled, char drawChar, float speed)
	{
		this(obstacle, isFilled, drawChar, obstacle.getCentroid(), speed);
	}

	@Override
	public void update(int deltaT)
	{
		rotate(deltaT * this.speed);
	}

	private void rotate(float radians)
	{
		try
		{
			this.collider = collider.rotate(radians, this.rotationPoint);
		}
		catch (GeometricException e)
		{
			Logger.log(Logger.Level.FATAL, "Dynamic Obstacle rotated to an invalid coordinate.\n" + e);
			throw new Error("Dynamic Obstacle rotated to an invalid coordinate.");
		}
	}

	public RenderData<Polygon> getRenderData()
	{
		this.rData = new RenderData<Polygon>((Polygon)this.collider, this.rData.isFilled(), this.rData.getLayer(), this.rData.getCharacter());
		return this.rData;
	}

	public GameObject getGameObject() { return this; }

	public void onCollision(GameObject other) { } // do nothing
	
	public IGeometricShape<Polygon> getCollider() { return this.collider; }
}
