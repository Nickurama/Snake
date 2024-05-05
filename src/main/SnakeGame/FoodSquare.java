package SnakeGame;

import GameEngine.*;
import Geometry.*;

public class FoodSquare extends Unit implements IFood
{
	private static final int LAYER = 1;

	private boolean isDeepCollision;

	public FoodSquare(Point position, double size, boolean isFilled, char drawChar) throws SnakeGameException
	{
		super(position, size, isFilled, drawChar, LAYER);
		this.isDeepCollision = true;
	}

	public void consume()
	{
		super.sceneHandle().remove(this);
	}

	public GameObject getGameObject() { return this; }

	public IGeometricShape<Polygon> getCollider() { return super.unitSquare(); }
	
	public void onCollision(GameObject other) { }

	public boolean isDeepCollision() { return this.isDeepCollision; }
}
