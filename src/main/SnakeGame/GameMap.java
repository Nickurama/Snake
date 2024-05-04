package SnakeGame;

import Geometry.*;
import GameEngine.*;

public class GameMap extends GameObject implements IRenderable
{
	private static final int LAYER = 0;
	private Rectangle map;
	private RenderData<Rectangle> rData;
	// private IGeometricShape<Polygon> collider;
	private StaticObstacle topBound;
	private StaticObstacle bottomBound;
	private StaticObstacle leftBound;
	private StaticObstacle rightBound;

	public GameMap(Rectangle mapRect, char drawChar) throws SnakeGameException
	{
		this.map = mapRect;
		this.rData = new RenderData<Rectangle>(map, true, LAYER, drawChar);
		try
		{
			buildBounds();
		}
		catch (GeometricException e)
		{
			Logger.log(Logger.Level.ERROR, "Could not build map colliders, map is too close to origin.\n" + e);
			throw new SnakeGameException("Could not build map colliders, map is too close to origin.");
		}
	}

	private void buildBounds() throws GeometricException
	{
		BoundingBox bounds = new BoundingBox(map);
		Point min = new Point(bounds.minPoint().X() - 2, bounds.minPoint().Y() - 2);
		Point max = new Point(bounds.maxPoint().X() + 2, bounds.maxPoint().Y() + 2);

		Polygon topPoly = new Polygon(new Point[] {
			new Point(min.X(), max.Y() - 1),
			new Point(min.X(), max.Y()),
			new Point(max.X(), max.Y()),
			new Point(max.X(), max.Y() - 1),
		});
		this.topBound = new StaticObstacle(topPoly, false, ' ');

		Polygon bottomPoly = new Polygon(new Point[] {
			new Point(min.X(), min.Y()),
			new Point(min.X(), min.Y() + 1),
			new Point(max.X(), min.Y() + 1),
			new Point(max.X(), min.Y()),
		});
		this.bottomBound = new StaticObstacle(bottomPoly, false, ' ');

		Polygon leftPoly = new Polygon(new Point[] {
			new Point(min.X(), min.Y()),
			new Point(min.X(), max.Y()),
			new Point(min.X() + 1, max.Y()),
			new Point(min.X() + 1, min.Y()),
		});
		this.leftBound = new StaticObstacle(leftPoly, false, ' ');

		Polygon rightPoly = new Polygon(new Point[] {
			new Point(max.X() - 1, min.Y()),
			new Point(max.X() - 1, max.Y()),
			new Point(max.X(), max.Y()),
			new Point(max.X(), min.Y()),
		});
		this.rightBound = new StaticObstacle(rightPoly, false, ' ');
	}

	@Override
	public void start()
	{
		super.sceneHandle().add(topBound);
		super.sceneHandle().add(bottomBound);
		super.sceneHandle().add(leftBound);
		super.sceneHandle().add(rightBound);
	}

	public RenderData<Rectangle> getRenderData() { return this.rData; }

	public GameObject getGameObject() { return this; }

	public void onCollision(GameObject other) { } // do nothing
}
