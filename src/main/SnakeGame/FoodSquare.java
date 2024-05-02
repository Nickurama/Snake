package SnakeGame;

import GameEngine.*;
import Geometry.*;

public class FoodSquare extends Unit implements IFood
{
	public FoodSquare(Point position, int size, boolean isFilled, char drawChar, int layer) throws SnakeGameException
	{
		super(position, size, isFilled, drawChar, layer);
	}

	public void consume()
	{
		super.sceneHandle.remove(this);
	}

	public GameObject getGameObject() { return this; }

	public IGeometricShape<Polygon> getCollider() { return super.unitSquare(); }
	
	public void onCollision(GameObject other) { }
}
