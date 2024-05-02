package SnakeGame;

import GameEngine.*;
import Geometry.*;

public class FoodCircle extends GameObject implements IFood
{
	private RenderData<Circle> rData;
	private IGeometricShape<Circle> collider;

	public FoodCircle(Point position, int radius, boolean isFilled, char drawChar, int layer) throws SnakeGameException
	{
		Circle circle = null;

		try
		{
			circle = new Circle(position, radius);
		}
		catch (GeometricException e)
		{
			Logger.log(Logger.Level.FATAL, "Error creating the food circle.\n" + e);
			throw new RuntimeException("Error creating the food circle: " + e.getMessage());
		}

		this.rData = new RenderData<Circle>(circle, isFilled, layer, drawChar);
		this.collider = circle;
	}

	public void consume()
	{
		super.sceneHandle.remove(this);
	}

	public RenderData<Circle> getRenderData() { return this.rData; }

	public GameObject getGameObject() { return this; }

	public IGeometricShape<Circle> getCollider() { return this.collider; }
	
	public void onCollision(GameObject other) { }
}
