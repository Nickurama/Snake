package SnakeGame;

import Geometry.*;
import GameEngine.*;

public class GameMap extends GameObject implements IRenderable, ICollider
{
	Rectangle map;
	RenderData<Rectangle> rData;
	IGeometricShape<Polygon> collider;

	public GameMap(Rectangle mapRect, char drawChar) throws SnakeGameException
	{
		this.map = mapRect;
		this.rData = new RenderData<Rectangle>(map, true, 0, drawChar);
		try
		{
			this.collider = buildCollider();
		}
		catch (GeometricException e)
		{
			Logger.log(Logger.Level.ERROR, "Could not build map colliders, map is too close to origin.\n" + e);
			throw new SnakeGameException("Could not build map colliders, map is too close to origin.");
		}
	}

	private IGeometricShape<Polygon> buildCollider() throws GeometricException
	{
		BoundingBox bounds = new BoundingBox(map);
		IGeometricShape<Polygon> outer = new Rectangle(new Point[]
		{
			new Point(bounds.minPoint().X() - 1, bounds.minPoint().Y() - 1),
			new Point(bounds.minPoint().X() - 1, bounds.maxPoint().Y() + 1),
			new Point(bounds.maxPoint().X() + 1, bounds.maxPoint().Y() + 1),
			new Point(bounds.maxPoint().X() + 1, bounds.minPoint().Y() - 1),
		});
		return outer;
	}

	public RenderData<Rectangle> getRenderData()
	{
		return this.rData;
	}

	public GameObject getGameObject()
	{
		return this;
	}

	public void onCollision(GameObject other)
	{
		// do nothing
	}

	public IGeometricShape<Polygon> getCollider()
	{
		return this.collider;
	}
}
