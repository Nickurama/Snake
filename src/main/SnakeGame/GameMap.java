package SnakeGame;

import Geometry.*;

import java.util.ArrayList;
import java.util.Random;

import GameEngine.*;

public class GameMap extends GameObject implements IRenderable
{
	private class FillerUnit extends Unit implements ICollider
	{
		private boolean isDeepCollision;
		public FillerUnit(Point position, int size, boolean isDeepCollision) throws SnakeGameException
		{
			super(position, size, true, ' ', 0);
			this.isDeepCollision = isDeepCollision;
		}

		public IGeometricShape<Polygon> getCollider() { return super.getRenderData().getShape(); }
		public GameObject getGameObject() { return this; }
		public void onCollision(GameObject other) { }
		public boolean isDeepCollision() { return this.isDeepCollision; }
	}


	private static final int LAYER = 0;
	private static final long DEFAULT_SEED = 0;
	private Rectangle map;
	private RenderData<Rectangle> rData;
	private StaticObstacle topBound;
	private StaticObstacle bottomBound;
	private StaticObstacle leftBound;
	private StaticObstacle rightBound;
	private BoundingBox bounds;
	private int width;
	private int height;
	private long seed;
	private Random rng;

	public GameMap(Rectangle mapRect, char drawChar) throws SnakeGameException
	{
		initialize(mapRect, drawChar);
	}

	public GameMap(int width, int height, Point startPos, char drawChar) throws SnakeGameException
	{
		if (width <= 1 || height <= 1)
		{
			Logger.log(Logger.Level.ERROR, "Cannot initialize a game map with width/height of 1 or less.");
			throw new SnakeGameException("Cannot initialize a game map with width/height of 1 or less.");
		}

		Rectangle mapRect;
		try
		{
			mapRect = new Rectangle(startPos, startPos.translate(new Vector(width - 1, height - 1)));
		}
		catch (GeometricException e)
		{
			Logger.log(Logger.Level.FATAL, "Could not .\n" + e);
			throw new RuntimeException("Could not build map colliders, map is too close to origin.");
		}
		initialize(mapRect, drawChar);
	}

	private void initialize(Rectangle mapRect, char drawChar) throws SnakeGameException
	{
		this.seed = DEFAULT_SEED;
		this.map = mapRect;
		this.rData = new RenderData<Rectangle>(map, true, LAYER, drawChar);
		this.bounds = new BoundingBox(mapRect);
		try
		{
			buildBounds();
		}
		catch (GeometricException e)
		{
			Logger.log(Logger.Level.ERROR, "Could not build map colliders, map is too close to origin.\n" + e);
			throw new SnakeGameException("Could not build map colliders, map is too close to origin.");
		}

		this.width = (int)Math.round(this.bounds.maxPoint().X() - this.bounds.minPoint().X() + 1);
		this.height = (int)Math.round(this.bounds.maxPoint().Y() - this.bounds.minPoint().Y() + 1);
	}

	private void buildBounds() throws GeometricException
	{
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

	public Point getRandomUnitSpawnPosition(int size)
	{
		return getRandomPoint(getAllValidUnitSpawnPositions(size));
	}

	public Point getRandomInnerUnitSpawnPosition(int innerSize, int outerSize)
	{
		return getRandomPoint(getAllValidInnerUnitSpawnPositions(innerSize, outerSize));
	}

	public Point getRandomPoint(Point[] arr)
	{
		if (arr == null || arr.length == 0)
			return null;
		return arr[new Random(this.seed).nextInt(arr.length)];
	}

	public Point[] getAllValidInnerUnitSpawnPositions(int innerSize, int outerSize)
	{
		Point[] points = null;
		try
		{
			Point[] outerPositions = getAllValidUnitSpawnPositions(outerSize);
			points = new Point[getNumberOfInnerUnitPositions(innerSize, outerSize) * outerPositions.length];

			int n = 0;
			for (Point outerPos : outerPositions)
			{
				Point[] innerPositions = getPossibleInnerUnitPositions(outerPos, innerSize, outerSize);
				System.arraycopy(innerPositions, 0, points, n, innerPositions.length);
				n += innerPositions.length;
			}
		}
		catch (GeometricException e)
		{
			Logger.log(Logger.Level.FATAL, "All possible inner unit positions should be valid!\n" + e);
			throw new RuntimeException("All possible inner unit positions should be valid!\n" + e.getMessage());
		}
		return points;
	}

	private int getNumberOfInnerUnitPositions(int innerSize, int outerSize)
	{
		int difference = outerSize - (int)Math.round(innerSize);
		int numOffsets = difference + 1;
		return numOffsets * numOffsets;
	}

	private Point[] getPossibleInnerUnitPositions(Point position, int innerSize, int outerSize) throws GeometricException
	{
		int difference = outerSize - (int)Math.round(innerSize);
		int numOffsets = difference + 1;
		int num = numOffsets * numOffsets;
		Point[] points = new Point[num];

		double maxOffset = (double)difference / 2.0;
		for (int i = 0; i < numOffsets; i++)
			for (int j = 0; j < numOffsets; j++)
				points[i * numOffsets + j] = position.translate(new Vector(-maxOffset + j, -maxOffset + i));
		return points;
	}

	public Point[] getAllPossibleInnerUnitPositions(int innerSize, int outerSize)
	{
		Point[] points = null;
		try
		{
			Point[] outerPositions = getAllPossibleUnitSpawnPositions(outerSize);
			points = new Point[getNumberOfInnerUnitPositions(innerSize, outerSize) * outerPositions.length];

			int n = 0;
			for (Point outerPos : outerPositions)
			{
				Point[] innerPositions = getPossibleInnerUnitPositions(outerPos, innerSize, outerSize);
				System.arraycopy(innerPositions, 0, points, n, innerPositions.length);
				n += innerPositions.length;
			}
		}
		catch (GeometricException e)
		{
			Logger.log(Logger.Level.FATAL, "All possible inner unit positions should be valid!\n" + e);
			throw new RuntimeException("All possible inner unit positions should be valid!\n" + e.getMessage());
		}
		return points;
	}

	public Point[] getAllValidUnitSpawnPositions(int size)
	{
		ArrayList<Point> validPositions = new ArrayList<Point>();
		Point[] allSpawnPositions = getAllPossibleUnitSpawnPositions(size);
		if (super.sceneHandle() == null)
			return allSpawnPositions;
		for (Point p : allSpawnPositions)
			if (!isUnitOccupied(p, size))
				validPositions.add(p);
		return validPositions.toArray(new Point[0]);
	}

	public boolean doUnitsOverlap(Point pos0, double size0, Point pos1, double size1)
	{
		try
		{
			FillerUnit unit0 = new FillerUnit(pos0, (int)Math.round(size0), true);
			FillerUnit unit1 = new FillerUnit(pos1, (int)Math.round(size1), true);
			return CollisionManager.collides(unit0, unit1);
		}
		catch (SnakeGameException e)
		{
			Logger.log(Logger.Level.FATAL, "Should never pass arguments that have invalid coordinates.\n" + e);
			throw new RuntimeException("Should never pass arguments that have invalid coordinates.");
		}
	}

	public Point[] getAllPossibleUnitSpawnPositions(int size)
	{
		Point[] points = null;
		try
		{
			Point initialPoint = getFirstPossiblePos(size);
			int verticalPositions = this.height / size; 
			int horizontalPositions = this.width / size;
			int maxPositions = verticalPositions * horizontalPositions;

			points = new Point[maxPositions];

			for (int i = 0; i < verticalPositions; i++)
			{
				for (int j = 0; j < horizontalPositions; j++)
				{
					Vector vector = new Vector(j * size, i * size);
					Point newPoint = initialPoint.translate(vector);
					points[i * horizontalPositions + j] = newPoint;
				}
			}
		}
		catch (GeometricException e)
		{
			Logger.log(Logger.Level.FATAL, "Should not happen! All possible unit spawn position should be valid!\n" + e);
			throw new RuntimeException("Should not happen! All possible unit spawn position should be valid!\n" + e.getMessage());
		}
		return points;
	}

	private Point getFirstPossiblePos(int size) throws GeometricException
	{
		double coordinate = ((double)size / 2.0) - 0.5;
		Point relativeInitialPoint = new Point(coordinate, coordinate);
		return getAbsolute(relativeInitialPoint);
	}

	private Point getAbsolute(Point relative) throws GeometricException
	{
		if (relative == null)
			return null;
		try
		{
			return relative.translate(new Vector(this.bounds.minPoint()));
		}
		catch (GeometricException e)
		{
			Logger.log(Logger.Level.FATAL, "Should never happen. the absolute points should always be valid.\n" + e);
			throw new RuntimeException("Should never happen. the absolute points should always be valid.");
		}
	}

	public int width() { return this.width; }

	public int height() { return this.height; }

	public void setSeed(long seed) { this.seed = seed; }

	public boolean isUnitOccupied(Point pos, double unitSize)
	{
		FillerUnit unit;
		try
		{
			unit = new FillerUnit(pos, (int)Math.round(unitSize), true);
		}
		catch (SnakeGameException e)
		{
			return false;
		}
		return CollisionManager.collidesAny(unit, super.sceneHandle());
	}

	public GameObject[] getObjectsOccupyingUnit(Point pos, double unitSize)
	{
		FillerUnit unit;
		try
		{
			unit = new FillerUnit(pos, (int)Math.round(unitSize), true);
		}
		catch (SnakeGameException e)
		{
			return null;
		}
		return CollisionManager.getCollisions(unit, super.sceneHandle());
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
