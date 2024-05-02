package SnakeGame;

import Geometry.*;
import GameEngine.*;

public class StaticObstacle extends GameObject implements IObstacle
{
	private static final int LAYER = 1;
	private RenderData<Polygon> rData;
	private IGeometricShape<Polygon> shape;
	private boolean isDeepCollision;

	public StaticObstacle(Polygon obstacle, boolean isFilled, char drawChar)
	{
		this.rData = new RenderData<Polygon>(obstacle, isFilled, LAYER, drawChar);
		this.shape = obstacle;
		this.isDeepCollision = false;
	}

	public RenderData<Polygon> getRenderData() { return this.rData; }

	public GameObject getGameObject() { return this; }

	public void onCollision(GameObject other) { } // do nothing
	
	public IGeometricShape<Polygon> getCollider() { return this.shape; }

	public boolean isDeepCollision() { return this.isDeepCollision; }
}
