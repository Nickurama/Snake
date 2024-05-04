package SnakeGame;

import Geometry.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import GameEngine.*;
import SnakeGame.*;

public class GameManager
{
	public static enum ControlMethod
	{
		MANUAL,
		AUTO,
	}

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


	private static GameManager instance = null;
	private final Point INIT_POS;
	private static final char DEFAULT_MAP = '.';
	private static final char DEFAULT_SNAKE_HEAD = 'H';
	private static final char DEFAULT_SNAKE_TAIL = 'T';

	private Scene scene;
	private Snake snake;

	private Point[] allPossibleSnakePositions;

	private GameManager() // Singleton
	{
		try
		{
			INIT_POS = new Point(10, 10);
		}
		catch(GeometricException e)
		{
			Logger.log(Logger.Level.FATAL, "Should never happen. initial position should always be valid.\n" + e);
			throw new RuntimeException("Should never happen. initial position should always be valid.\n" + e.getMessage());
		}

		this.snake = null;
		this.allPossibleSnakePositions = null;
	}

	public static GameManager getInstance()
	{
		if (instance == null)
			instance = new GameManager();

		return instance;
	}

	public void init(int mapWidth, int mapHeight, int snakeSize, boolean isFilled, boolean isTextual,
		GameEngineFlags.UpdateMethod updateMethod, ControlMethod controlMethod, long seed) throws SnakeGameException
	{
		this.init(mapWidth, mapHeight, DEFAULT_MAP, null, null,
			snakeSize, isFilled, DEFAULT_SNAKE_TAIL, DEFAULT_SNAKE_HEAD, isTextual, updateMethod, controlMethod, seed);
	}

	private Snake.Direction getRandomSnakeDir(long seed)
	{
		Random rng = new Random(seed);
		Snake.Direction[] values = Snake.Direction.values();
		Snake.Direction result = values[rng.nextInt(values.length)];
		return result;
	}

	public void init(int mapWidth, int mapHeight, char mapChar, Point relativeSnakePos, Snake.Direction snakeDir,
		int snakeSize, boolean isFilled, char snakeTail, char snakeHead, boolean isTextual,
		GameEngineFlags.UpdateMethod updateMethod, ControlMethod controlMethod, long seed) throws SnakeGameException
	{
		try
		{
			Point absoluteSnakePos = getAbsolute(relativeSnakePos);
			validate(absoluteSnakePos, snakeSize, mapWidth, mapHeight);
			initScene(mapWidth, mapHeight, mapChar, absoluteSnakePos, snakeDir, snakeSize, isFilled, snakeTail, snakeHead, controlMethod, seed);
			initGameEngine(this.scene, mapWidth, mapHeight, isTextual, updateMethod);
		}
		catch (Exception e)
		{
			Logger.log(Logger.Level.FATAL, "Error initializing Game Manager.\n" + e);
			throw new SnakeGameException("Error initializing Game Manager: " + e.getMessage());
		}
	}

	private void validate(Point absoluteSnakePos, int snakeSize, int mapWidth, int mapHeight) throws SnakeGameException, GeometricException
	{
		validateDimensions(mapWidth, mapHeight);
		validateRatio(mapWidth, mapHeight, snakeSize);
		if (absoluteSnakePos != null)
			validateSnakePos(mapWidth, mapHeight, snakeSize, absoluteSnakePos);
	}

	private void initScene(int mapWidth, int mapHeight, char mapChar, Point absoluteSnakePos,
		Snake.Direction snakeDir, int snakeSize, boolean isFilled, char snakeTail, char snakeHead,
		ControlMethod ctlMethod, long seed) throws SnakeGameException
	{
		try
		{
			setupScene(mapWidth, mapHeight, mapChar, absoluteSnakePos, snakeDir, snakeSize,
				isFilled, snakeTail, snakeHead, ctlMethod, seed);
		}
		catch (Exception e)
		{
			Logger.log(Logger.Level.FATAL, "Error generating scene.\n" + e);
			throw new SnakeGameException("Error generating scene: " + e.getMessage());
		}
	}

	private void initGameEngine(Scene scene, int mapWidth, int mapHeight, boolean isTextual,
		GameEngineFlags.UpdateMethod updateMethod) throws SnakeGameException
	{
		try
		{
			Rectangle camera = generateCamera(mapWidth, mapHeight);
			setupGameEngine(this.scene, camera, isTextual, updateMethod);
		}
		catch (Exception e)
		{
			Logger.log(Logger.Level.FATAL, "Error setting up game engine.\n" + e);
			throw new SnakeGameException("Error setting up game engine: " + e.getMessage());
		}
	}

	private void validateDimensions(int mapWidth, int mapHeight) throws SnakeGameException
	{
		if (mapWidth < 2 || mapHeight < 2)
		{
			Logger.log(Logger.Level.FATAL, "Map dimensions cannot be lower than 2");
			throw new SnakeGameException("Map dimensions cannot be lower than 2");
		}
	}

	private void validateRatio(int mapWidth, int mapHeight, int snakeSize) throws SnakeGameException
	{
		if (mapWidth % snakeSize != 0 || mapHeight % snakeSize != 0)
		{
			Logger.log(Logger.Level.FATAL, "Map dimensions must attune to the snake's length.");
			throw new SnakeGameException("Map dimensions must attune to the snake's length.");
		}
	}
	
	private void validateSnakePos(int mapWidth, int mapHeight, int snakeSize, Point absoluteSnakePos) throws SnakeGameException, GeometricException
	{
		this.allPossibleSnakePositions = generateAllPossiblePositions(mapWidth, mapHeight, snakeSize);
		if (!Arrays.stream(allPossibleSnakePositions).anyMatch(absoluteSnakePos::equals))
		{
			Logger.log(Logger.Level.FATAL, "Snake was placed in an invalid position: " + getRelative(absoluteSnakePos));
			throw new SnakeGameException("Snake was placed in an invalid position: " + getRelative(absoluteSnakePos));
		}
	}

	private Point getAbsolute(Point relative) throws GeometricException
	{
		if (relative == null)
			return null;
		return relative.translate(new Vector(INIT_POS));
	}

	private Point getRelative(Point absolute)
	{
		try
		{
			return absolute.translate(new Vector(-INIT_POS.X(), -INIT_POS.Y()));
		}
		catch (GeometricException e)
		{
			Logger.log(Logger.Level.FATAL, "Should never happen, as the absolute point is created from the relative point.\n" + e);
			throw new RuntimeException("Should never happen, as the absolute point is created from the relative point.");
		}
	}

	private Point[] generateAllPossiblePositions(int mapWidth, int mapHeight, int size)
	{
		Point[] points = null;
		try
		{
			double coordinate = ((double)size / 2.0) - 0.5;
			Point relativeInitialPoint = new Point(coordinate, coordinate);
			Point absoluteInitialPoint = getAbsolute(relativeInitialPoint);

			int verticalPositions = mapHeight / size; 
			int horizontalPositions = mapWidth / size;
			int maxPositions = verticalPositions * horizontalPositions;

			points = new Point[maxPositions];

			for (int i = 0; i < verticalPositions; i++)
			{
				for (int j = 0; j < horizontalPositions; j++)
				{
					Vector vector = new Vector(j * size, i * size);
					Point newPoint = absoluteInitialPoint.translate(vector);
					points[i * horizontalPositions + j] = newPoint;
				}
			}
		}
		catch (GeometricException e)
		{
			Logger.log(Logger.Level.FATAL, "All possible snake points must be valid!\n" + e);
			throw new RuntimeException("All possible snake points must be valid!\n" + e.getMessage());
		}
		return points;
	}

	private ISnakeController generateController(ControlMethod controlMethod)
	{
		ISnakeController controller = null;
		
		switch(controlMethod)
		{
			case ControlMethod.MANUAL:
				controller = new InputSnakeController();
				break;
			case ControlMethod.AUTO:
				// TODO !!!!!!!!
				break;
		}

		return controller;
	}

	private void setupScene(int mapWidth, int mapHeight, char mapChar, Point absoluteSnakePos,
		Snake.Direction snakeDir, int snakeSize, boolean isFilled, char snakeTail, char snakeHead,
		ControlMethod controlMethod, long seed) throws SnakeGameException
	{
		this.scene = new Scene();

		GameMap map = generateMap(mapWidth, mapHeight, mapChar);
		this.scene.add(map);

		if (absoluteSnakePos == null)
			absoluteSnakePos = getRandomEmptySnakePos(seed, mapWidth, mapHeight, snakeSize);
		if (snakeDir == null)
			snakeDir = getRandomSnakeDir(seed);
		this.snake = new Snake(absoluteSnakePos, snakeDir, snakeSize, isFilled, snakeTail, snakeHead);
		this.scene.add(this.snake);

		ISnakeController snakeController = generateController(controlMethod);
		this.scene.add((GameObject)snakeController);

		SnakeController controller = new SnakeController(snake, snakeController);
		this.scene.add(controller);

		GameObject overlay = generateOverlay(snake, generateCamera(mapWidth, mapHeight));
		this.scene.add(overlay);
	}

	private GameMap generateMap(int mapWidth, int mapHeight, char mapChar)
	{
		GameMap map;
		try
		{
			Rectangle mapRect = new Rectangle(INIT_POS, INIT_POS.translate(new Vector(mapWidth - 1, mapHeight - 1))); // -1 to behave as expected, not inclusive of last coord
			map = new GameMap(mapRect, mapChar);
		}
		catch (Exception e)
		{
			Logger.log(Logger.Level.FATAL, "Error generating game map: " + e);
			throw new RuntimeException("Error generating game map: " + e.getMessage());
		}
		return map;
	}

	private Point getRandomEmptySnakePos(long seed, int mapWidth, int mapHeight, int snakeSize)
	{
		Point[] validSpawnPositions = getValidSpawnPositions(mapWidth, mapHeight, snakeSize);
		Random rng = new Random(seed);
		return validSpawnPositions[rng.nextInt(validSpawnPositions.length)];
	}

	public Point[] getValidSpawnPositions(int mapWidth, int mapHeight, int size)
	{
		if (this.allPossibleSnakePositions == null)
			this.allPossibleSnakePositions = generateAllPossiblePositions(mapWidth, mapHeight, size);

		ArrayList<Point> validSpawnPositions = new ArrayList<Point>();

		for (Point position : allPossibleSnakePositions)
		{
			FillerUnit unit = generateFillerUnit(position, size);
			if (!CollisionManager.collidesAny(unit, this.scene))
				validSpawnPositions.add(position);
		}

		return validSpawnPositions.toArray(new Point[0]);
	}

	private FillerUnit generateFillerUnit(Point position, int size)
	{
		try
		{
			return new FillerUnit(position, size, true);
		}
		catch (SnakeGameException e)
		{
			Logger.log(Logger.Level.FATAL, "Should never happen. Position should always be passed as a valid unit position.\n" + e);
			throw new RuntimeException("Should never happen. Position should always be passed as a valid unit position.");
		}
	}

	private Rectangle generateCamera(int mapWidth, int mapHeight)
	{
		Rectangle camera;
		try
		{
			Point p0 = INIT_POS.translate(new Vector(-1, -2));
			Point p1 = INIT_POS.translate(new Vector(mapWidth, mapHeight)); // already is +1 because it's inclusive
			camera = new Rectangle(p0, p1);
		}
		catch (Exception e)
		{
			Logger.log(Logger.Level.FATAL, "Error generating game camera: " + e);
			throw new RuntimeException("Error generating game camera: " + e.getMessage());
		}
		return camera;
	}

	private GameObject generateOverlay(Snake snake, Rectangle bounds)
	{
		TextOverlayOutline outline = new TextOverlayOutline();
		GameplayOverlay overlay = new GameplayOverlay(snake, bounds, outline);
		return overlay;
	}

	private void setupGameEngine(Scene scene, Rectangle camera, boolean isTextual, GameEngineFlags.UpdateMethod updateMethod)
	{
		GameEngine engine = GameEngine.getInstance();
		if (engine.isRunning())
		{
			Logger.log(Logger.Level.FATAL, "Tried to setup engine while it was still running.");
			throw new RuntimeException("Tried to setup engine while it was still running.");
		}

		GameEngineFlags flags = new GameEngineFlags();
		flags.setTextual(isTextual);
		flags.setUpdateMethod(updateMethod);
		engine.init(flags, scene, camera);
	}

	public void play()
	{
		this.snake.awake();
		GameEngine.getInstance().start();
	}
}
