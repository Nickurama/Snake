package SnakeGame;

import Geometry.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import GameEngine.*;

/**
 * Represents the game map, where the game will take place and the {@link GameObject GameObjects} will be in.
 * Provides the utility necessary to instantiate and do operations on a grid-like map.
 * Holds the edge obstacles that maintain the snake from leaving the map.
 * Is built such that the rendered map will have the width and height dimentions
 * 
 * @author Diogo Fonseca a79858
 * @version 08/05/2024
 * 
 * @see Unit
 */
public class GameMap extends GameObject implements IRenderable
{
	/**
	 * A unit meant to act as filler, just for probing
	*/
	private class FillerUnit extends Unit implements ICollider
	{
		private boolean isDeepCollision;

		/**
		 * Instantiates a FillerUnit
		 * @param position the position to place the unit in
		 * @param size the size of the unit
		 * @param isDeepCollision if it should check for deep collisions
		 * @throws SnakeGameException if placed at an invalid position
		 */
		public FillerUnit(Point position, int size, boolean isDeepCollision) throws SnakeGameException
		{
			super(position, size, true, ' ', 0);
			this.isDeepCollision = isDeepCollision;
		}

		@Override
		public IGeometricShape<Polygon> getCollider() { return super.getRenderData().getShape(); }
		@Override
		public void onCollision(GameObject other) { }
		@Override
		public boolean isDeepCollision() { return this.isDeepCollision; }
	}

	private static final int LAYER = 0;
	private Rectangle map;
	private RenderData<Rectangle> rData;
	private StaticObstacle topBound;
	private StaticObstacle bottomBound;
	private StaticObstacle leftBound;
	private StaticObstacle rightBound;
	private BoundingBox bounds;
	private int width;
	private int height;
	private Random rng;

	/**
	 * Instantiates a GameMap
	 * Can't be close to origin since it has to generate surrounding colliders
	 * @param mapRect the rectangle to base the map on
	 * @param drawChar the character used to draw the map
	 * @param seed the seed for random usage
	 * @throws SnakeGameException if the map is instantiated in an invalid position
	 */
	public GameMap(Rectangle mapRect, char drawChar, long seed) throws SnakeGameException
	{
		initialize(mapRect, drawChar, seed);
	}

	/**
	 * Instantiates a GameMap
	 * Can't be close to origin since it has to generate surrounding colliders
	 * @param width the width of the map
	 * @param height the height of the map
	 * @param startPos the position the map should start at
	 * @param drawChar the character used to draw the map
	 * @param seed the seed for random usage
	 * @throws SnakeGameException if the map is instantiated in an invalid position
	 */
	public GameMap(int width, int height, Point startPos, char drawChar, long seed) throws SnakeGameException
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
		initialize(mapRect, drawChar, seed);
	}

	/**
	 * Initializes the GameMap
	 * @param mapRect the rectangle to use as the map
	 * @param drawChar the character to draw the map as
	 * @param seed the seed for random usage
	 * @throws SnakeGameException if the map is placed in an invalid position
	 */
	private void initialize(Rectangle mapRect, char drawChar, long seed) throws SnakeGameException
	{
		this.rng = new Random(seed);
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

	/**
	 * Builds the Obstacles surrounding the map
	 * @throws GeometricException if the obstacles can't be placed
	 */
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

	/**
	 * Generates a random valid spawn position
	 * @param size the size of the unit to spawn
	 * @return a valid random spawn position
	 */
	public Point getRandomUnitSpawnPosition(int size)
	{
		return getRandomPoint(getAllValidUnitSpawnPositions(size));
	}

	/**
	 * Generates a random valid spawn position for an unit that must be
	 * contained within another one
	 * @param innerSize the size of the inner unit
	 * @param outerSize the size of the outer unit
	 * @return a valid random spawn position for the inner unit
	 * @pre innerSize > 0
	 * @pre outerSize > innerSize
	 */
	public Point getRandomInnerUnitSpawnPosition(int innerSize, int outerSize)
	{
		return getRandomPoint(getAllValidInnerUnitSpawnPositions(innerSize, outerSize));
	}

	/**
	 * Generates a random point from an array of points
	 * @param arr the sample of points to pick from
	 * @return a random point from the array
	 */
	public Point getRandomPoint(Point[] arr)
	{
		if (arr == null || arr.length == 0)
			return null;
		return arr[rng.nextInt(arr.length)];
	}

	/**
	 * Generates all the valid spawn positions for an unit that must be
	 * contained within another one
	 * @param innerSize the size of the inner unit
	 * @param outerSize the size of the outer unit
	 * @return all the valid positions for the inner unit
	 * @pre innerSize > 0
	 * @pre outerSize > innerSize
	 */
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

	/**
	 * Calculates the ammount of possible inner positions in the map
	 * @param innerSize the size of the inner unit
	 * @param outerSize the size of the outer unit
	 * @return the ammount of possible inner positions
	 * @pre innerSize > 0
	 * @pre outerSize > innerSize
	 */
	private int getNumberOfInnerUnitPositions(int innerSize, int outerSize)
	{
		int difference = outerSize - (int)Math.round(innerSize);
		int numOffsets = difference + 1;
		return numOffsets * numOffsets;
	}

	/**
	 * Generates all possible inner unit positions in the map
	 * @param outerPos the position of the outer unit
	 * @param innerSize the size of the inner unit
	 * @param outerSize the size of the outer unit
	 * @return all possible inner unit positions on the map
	 * @pre innerSize > 0
	 * @pre outerSize > innerSize
	 * @throws GeometricException if the innerSize/outerSize are negative
	 */
	private Point[] getPossibleInnerUnitPositions(Point outerPos, int innerSize, int outerSize) throws GeometricException
	{
		int difference = outerSize - (int)Math.round(innerSize);
		int numOffsets = difference + 1;
		int num = numOffsets * numOffsets;
		Point[] points = new Point[num];

		double maxOffset = (double)difference / 2.0;
		for (int i = 0; i < numOffsets; i++)
			for (int j = 0; j < numOffsets; j++)
				points[i * numOffsets + j] = outerPos.translate(new Vector(-maxOffset + j, -maxOffset + i));
		return points;
	}

	/**
	 * Generates all the possible inner unit positions based on the first possible position
	 * @param innerSize the size of the inner unit
	 * @param outerSize the size of the outer unit
	 * @return all possible inner unit positions on the map
	 * @pre innerSize > 0
	 * @pre outerSize > innerSize
	 */
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

	/**
	 * Generates all valid unit spawn positions
	 * @param size the size of the unit
	 * @return all valid unit spawn positions
	 * @pre size > 0
	 */
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

	/**
	 * Checks if two units are overlapping
	 * @param pos0 position of the first unit
	 * @param size0 size of the first unit
	 * @param pos1 position of the second unit
	 * @param size1 size of the second unit
	 * @return if the two units overlap
	 * @pre size0 > 0
	 * @pre size1 > 0
	 */
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

	/**
	 * Parses the map to a boolean map array representation, where the values
	 * are true on the location of the given points
	 * @param points the points to mark as true on the map array
	 * @param size the size of each unit represented in the map array
	 * @return the map boolean array representation of the current map
	 * @pre size > 0
	 */
	public boolean[][] asArray(Point[] points, int size)
	{
		boolean[][] mapArray = new boolean[this.height / size][this.width / size];
		for (Point point : points)
		{
			Point index = getUnitIndex(point, size);
			mapArray[(int)index.Y()][(int)index.X()] = true;
		}
		return mapArray;
	}

	/**
	 * Gets the the index of an unit on the map given it's
	 * position and the size of units
	 * @param position the position to get the index of
	 * @param size the size of an unit
	 * @return the index of the position
	 * @pre size > 0
	 */
	public Point getUnitIndex(Point position, int size)
	{
		try
		{
			int x = (int)(position.X() + 0.5 - this.bounds.minPoint().X()) / size;
			int y = (int)(position.Y() + 0.5 - this.bounds.minPoint().Y()) / size;
			return new Point(x, y);
		}
		catch (GeometricException e)
		{
			Logger.log(Logger.Level.FATAL, "Should not happen! All possible indexes should be valid!\n" + e);
			throw new RuntimeException("Should not happen! All possible indexes should be valid!\n" + e.getMessage());
		}
	}

	/**
	 * Gets the point of the unit at the given index
	 * @param index the index of the unit in the map
	 * @param size the size of an unit
	 * @return the point of the unit
	 * @pre size > 0
	 */
	public Point getUnitPoint(Point index, int size)
	{
		try
		{
			Vector offset = new Vector(index).multiply(size);
			return getFirstPossiblePos(size).translate(offset);
		}
		catch (GeometricException e)
		{
			Logger.log(Logger.Level.FATAL, "Should never happen. the point should always have a positive value.\n" + e);
			throw new RuntimeException("Should never happen. the point should always have a positive value.");
		}
	}

	/**
	 * Gets all possible unit spawn positions
	 * @param size the size of an unit
	 * @return all the possible unit spawn positions
	 * @pre size > 0
	 */
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

	/**
	 * Calculates the position of an outer unit based on the location
	 * of an unit inside the outer unit (inner unit)
	 * @param inner the inner unit
	 * @param outerSize the size of the outer unit
	 * @return the position of the outer unit
	 * @pre outerSize > 0
	 */
	public Point getOuterPosition(Point inner, int outerSize)
	{
		return getUnitPoint(getUnitIndex(inner, outerSize), outerSize);
	}

	/**
	 * Calculates the first possible position for an unit on the map
	 * @param size the size of an unit
	 * @return the first possible position for an unit on the map
	 * @throws GeometricException if the size is lower than one
	 * @pre size >= 1
	 */
	private Point getFirstPossiblePos(int size) throws GeometricException
	{
		double coordinate = ((double)size / 2.0) - 0.5;
		Point relativeInitialPoint = new Point(coordinate, coordinate);
		return getAbsolute(relativeInitialPoint);
	}

	/**
	 * Checks if a unit is occupied at a given position
	 * @param pos the position of the unit to check if is occupied
	 * @param unitSize the size of an unit
	 * @return if the unit is occupied
	 */
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

	/**
	 * Gets the objects occupying a given unit
	 * @param pos the position of the unit
	 * @param unitSize the size of the unit
	 * @return the objects occupying the unit
	 */
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

	/**
	 * Gets the absolute value of a point relative to the map (witht the origin set as the minimum point of the map)
	 * @param relative the relative point
	 * @return the absolute point
	 */
	public Point getAbsolute(Point relative)
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

	/**
	 * Calculates the absolute position based on the relative position in relation to the map for the given point
	 * @param relative the point relative to the map
	 * @return the absolute position
	 */
	public VirtualPoint getAbsolute(VirtualPoint relative)
	{
		if (relative == null)
			return null;
		try
		{
			return relative.translate(new Vector(bounds.minPoint()));
		}
		catch (GeometricException e)
		{
			Logger.log(Logger.Level.FATAL, "Should never happen. the absolute points should always be valid.\n" + e);
			throw new RuntimeException("Should never happen. the absolute points should always be valid.");
		}
	}

	/**
	 * Calculates the absolute position for a polygon relative to the map
	 * @param relative the polygon in relation to the map
	 * @return the absolute polygon
	 */
	public Polygon getAbsolute(Polygon relative)
	{
		if (relative == null)
			return null;
		try
		{
			return relative.translate(new Vector(bounds.minPoint()));
		}
		catch (GeometricException e)
		{
			Logger.log(Logger.Level.FATAL, "Should never happen. the absolute points should always be valid.\n" + e);
			throw new RuntimeException("Should never happen. the absolute points should always be valid.");
		}
	}

	/**
	 * Calculates the relative position of a point to the map's origin
	 * @param absolute the point to get the relative position
	 * @return the position of the point in relation to the map
	 */
	public VirtualPoint getRelative(VirtualPoint absolute)
	{
		if (absolute == null)
			return null;
		try
		{
			return absolute.translate(new Vector(-bounds.minPoint().X(), -bounds.minPoint().Y()));
		}
		catch (GeometricException e)
		{
			Logger.log(Logger.Level.FATAL, "Should never happen, as the absolute point is created from the relative point.\n" + e);
			throw new RuntimeException("Should never happen, as the absolute point is created from the relative point.");
		}
	}

	/**
	 * Calculates the relative position of a point to the map's origin
	 * @param absolute the point to get the relative position
	 * @return the position of the point in relation to the map
	 */
	public Point getRelative(Point absolute)
	{
		if (absolute == null)
			return null;
		try
		{
			return absolute.translate(new Vector(-bounds.minPoint().X(), -bounds.minPoint().Y()));
		}
		catch (GeometricException e)
		{
			Logger.log(Logger.Level.FATAL, "Should never happen, as the absolute point is created from the relative point.\n" + e);
			throw new RuntimeException("Should never happen, as the absolute point is created from the relative point.");
		}
	}

	/**
	 * The maps's width
	 * @return the map's width
	 */
	public int width() { return this.width; }

	/**
	 * The map's height
	 * @return the map's height
	 */
	public int height() { return this.height; }

	public void setGraphicalColor(Color color)
	{
		this.rData = new RenderData<Rectangle>(this.rData.getShape(), this.rData.isFilled(), this.rData.getLayer(), this.rData.getCharacter(),
			this.rData.getTerminalColour(), color);
	}

	@Override
	public void start()
	{
		super.sceneHandle().add(topBound);
		super.sceneHandle().add(bottomBound);
		super.sceneHandle().add(leftBound);
		super.sceneHandle().add(rightBound);
	}

	@Override
	public RenderData<Rectangle> getRenderData() { return this.rData; }
}
