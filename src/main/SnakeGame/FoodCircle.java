package SnakeGame;

import GameEngine.*;
import Geometry.*;

public class FoodCircle extends GameObject implements IFood
{
	private static final int LAYER = 1;

	private RenderData<?> rData;
	private IGeometricShape<Circle> collider;
	private boolean isDeepCollision;
	private boolean wasConsumed;

	public FoodCircle(Point position, double radius, boolean isFilled, char drawChar) throws SnakeGameException
	{
		Circle circle = null;

		try
		{
			// circle = new Circle(position.translate(new Vector(Unit.UNIT_OFFSET, -Unit.UNIT_OFFSET)), radius - Unit.UNIT_OFFSET);
			circle = new Circle(position, radius - Unit.UNIT_OFFSET);
			if (radius <= 1)
				this.rData = new FoodSquare(position, radius * 2, isFilled, drawChar).getRenderData();
			else
			{
				this.rData = new RenderData<Circle>(circle, isFilled, LAYER, drawChar);
			}
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
