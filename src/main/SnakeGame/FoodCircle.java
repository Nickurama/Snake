package SnakeGame;

import GameEngine.*;
import Geometry.*;

public class FoodCircle extends GameObject implements IFood
{
	private static final int LAYER = 1;

	private RenderData<Circle> rData;
	private IGeometricShape<Circle> collider;
	private boolean isDeepCollision;

	public FoodCircle(Point position, double radius, boolean isFilled, char drawChar) throws SnakeGameException
	{
		Circle circle = null;

		try
		{
			circle = new Circle(position, radius - Unit.UNIT_OFFSET);
		}
		catch (GeometricException e)
		{
			Logger.log(Logger.Level.FATAL, "Error creating the food circle.\n" + e);
			throw new RuntimeException("Error creating the food circle: " + e.getMessage());
		}

		this.rData = new RenderData<Circle>(circle, isFilled, LAYER, drawChar);
		this.collider = circle;
		this.isDeepCollision = true;
	}

	public void consume()
	{
		super.sceneHandle().remove(this);
	}

	public RenderData<Circle> getRenderData() { return this.rData; }

	public GameObject getGameObject() { return this; }

	public IGeometricShape<Circle> getCollider() { return this.collider; }
	
	public void onCollision(GameObject other) { }

	public boolean isDeepCollision() { return this.isDeepCollision; }
}
