package SnakeGame;

import Geometry.*;
import GameEngine.*;

public class Obstacle extends GameObject implements IRenderable, ICollider
{
	private final int LAYER = 1;
	private RenderData<Polygon> rData;
	private IGeometricShape<Polygon> collider;

	public Obstacle(Polygon obstacle, boolean isFilled, char drawChar)
	{
		this.rData = new RenderData<Polygon>(obstacle, isFilled, LAYER, drawChar);
		this.collider = obstacle;
	}

	public RenderData<Polygon> getRenderData() { return this.rData; }

	public GameObject getGameObject() { return this; }

	public void onCollision(GameObject other) { } // do nothing
	
	public IGeometricShape<Polygon> getCollider() { return this.collider; }
}
