package SnakeGame;

import GameEngine.*;
import Geometry.*;

public class FoodCircle extends GameObject implements IFood
{
	private static final double CIRCLE_OFFSET = 0.1;
	private static final int LAYER = 1;

	private RenderData<?> rData;
	private IGeometricShape<Circle> collider;
	private boolean isDeepCollision;
	private boolean wasConsumed;

	public FoodCircle(Point position, double radius, boolean isFilled, char drawChar) throws SnakeGameException
	{
		this(position, radius, isFilled, drawChar, null);
	}

	public FoodCircle(Point position, double radius, boolean isFilled, char drawChar, Colour.Foreground colour) throws SnakeGameException
	{
		Circle circle = null;

		try
		{
			circle = new Circle(position, radius - Unit.UNIT_OFFSET - CIRCLE_OFFSET);
			if (radius <= 1)
				this.rData = new FoodSquare(position, radius * 2, isFilled, drawChar, colour).getRenderData();
			else
				this.rData = new RenderData<Circle>(circle, isFilled, LAYER, drawChar, colour);
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

	public void consume()
	{
		super.sceneHandle().remove(this);
		this.wasConsumed = true;
	}

	public Point position() { return this.collider.getCentroid(); }

	public boolean wasConsumed() { return this.wasConsumed; }

	public RenderData<?> getRenderData() { return this.rData; }

	public GameObject getGameObject() { return this; }

	public IGeometricShape<Circle> getCollider() { return this.collider; }
	
	public void onCollision(GameObject other) { }

	public boolean isDeepCollision() { return this.isDeepCollision; }
}
