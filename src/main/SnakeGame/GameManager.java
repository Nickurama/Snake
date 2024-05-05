package SnakeGame;

import Geometry.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import GameEngine.*;
import GameEngine.GameEngineFlags.*;
import SnakeGame.*;
import SnakeGame.Snake.*;

public class GameManager
{
	public static enum ControlMethod
	{
		MANUAL,
		AUTO,
	}

	public static enum FoodType
	{
		SQUARE,
		CIRCLE,
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
	private static final char DEFAULT_FOOD = 'F';
	private static final char DEFAULT_OBSTACLE = 'O';
	private static final char DEFAULT_SNAKE_HEAD = 'H';
	private static final char DEFAULT_SNAKE_TAIL = 'T';
	private char mapChar;
	private char foodChar;
	private char obstacleChar;
	private char snakeHeadChar;
	private char snakeTailChar;

	private boolean isFilled;
	private int foodScore;
	private int mapWidth;
	private int mapHeight;
	private Point startingSnakePos;
	private Direction startingSnakeDir;
	private int snakeSize;
	private Point startingFoodPos;
	private double foodSize;
	private FoodType foodType;
	private boolean isTextual;
	private UpdateMethod updateMethod;
	private ControlMethod controlMethod;
	private long seed;
	private Scene scene;
	private Snake snake;
	private IFood food;
	private Rectangle camera;
	ArrayList<Polygon> staticObstacles;
	ArrayList<DynamicObstacle> dynamicObstacles;

	private Point[] allPossibleSnakePositions;
	private Point[] allPossibleFoodPositions;

	private GameManager() // Singleton
	{
		this.mapChar = DEFAULT_MAP;
		this.foodChar = DEFAULT_FOOD;
		this.obstacleChar = DEFAULT_OBSTACLE;
		this.snakeHeadChar = DEFAULT_SNAKE_HEAD;
		this.snakeTailChar = DEFAULT_SNAKE_TAIL;
		try
		{
			INIT_POS = new Point(10, 10);
		}
		catch(GeometricException e)
		{
			Logger.log(Logger.Level.FATAL, "Should never happen. initial position should always be valid.\n" + e);
			throw new RuntimeException("Should never happen. initial position should always be valid.\n" + e.getMessage());
		}

		this.scene = null;
		this.snake = null;
		this.food = null;
		this.allPossibleSnakePositions = null;
		this.staticObstacles = new ArrayList<Polygon>();
		this.dynamicObstacles = new ArrayList<DynamicObstacle>();
	}

	public static GameManager getInstance()
	{
		if (instance == null)
			instance = new GameManager();

		return instance;
	}

	public static void resetInstance()
	{
		if (instance != null)
			instance = new GameManager();
	}

	public void clearObstacles()
	{
		this.staticObstacles = new ArrayList<Polygon>();
		this.dynamicObstacles = new ArrayList<DynamicObstacle>();
	}

	public void init(int mapWidth, int mapHeight, int snakeSize, boolean isFilled, double foodSize, FoodType foodType,
		int foodScore, boolean isTextual, UpdateMethod updateMethod,
		ControlMethod controlMethod, long seed) throws SnakeGameException
	{
		init(mapWidth, mapHeight, null, null, snakeSize, isFilled, null, foodSize, foodType, foodScore,
			isTextual, updateMethod, controlMethod, seed);
	}

	public void init(int mapWidth, int mapHeight, Point relativeSnakePos, Direction snakeDir, int snakeSize,
		boolean isFilled, Point relativeFoodPos, double foodSize, FoodType foodType, int foodScore, boolean isTextual,
		UpdateMethod updateMethod, ControlMethod controlMethod)
		throws SnakeGameException
	{
		if (relativeSnakePos == null || snakeDir == null)
		{
			Logger.log(Logger.Level.FATAL, "Error initializing Game Manager: Cannot have null snake position or snake direction when not providing seed.");
			throw new SnakeGameException("Error initializing Game Manager: Cannot have null snake position or snake direction when not providing seed.");
		}

		init(mapWidth, mapHeight, relativeSnakePos, snakeDir, snakeSize, isFilled, relativeFoodPos,
			foodSize, foodType, foodScore, isTextual, updateMethod, controlMethod, 0);
	}

	public void init(int mapWidth, int mapHeight, Point relativeSnakePos, Direction snakeDir, int snakeSize,
		boolean isFilled, Point relativeFoodPos, double foodSize, FoodType foodType, int foodScore, boolean isTextual,
		UpdateMethod updateMethod, ControlMethod controlMethod, long seed)
		throws SnakeGameException
	{
		try
		{
			this.mapWidth = mapWidth;
			this.mapHeight = mapHeight;
			this.startingSnakePos = getAbsolute(relativeSnakePos);
			this.startingSnakeDir = snakeDir;
			this.snakeSize = snakeSize;
			this.isFilled = isFilled;
			this.startingFoodPos = getAbsolute(relativeFoodPos);
			this.foodSize = foodSize;
			this.foodType = foodType;
			this.foodScore = foodScore;
			this.isTextual = isTextual;
			this.updateMethod = updateMethod;
			this.controlMethod = controlMethod;
			this.seed = seed;
			this.camera = generateCamera();
			initScene();
			initGameEngine();
			validate();
		}
		catch (Exception e)
		{
			Logger.log(Logger.Level.FATAL, "(Seed: " + seed + ") Error initializing Game Manager.\n" + e);
			throw new SnakeGameException("(Seed: " + seed + ") Error initializing Game Manager: " + e.getMessage());
		}
	}

	private void validate() throws SnakeGameException, GeometricException
	{
		validateDimensions();
		validateRatio();
		validateSnakePos();
		validateSnakeColliding();
		validateFoodRatio();
		validateFoodPos();
		validateFoodColliding();
	}

	private void validateDimensions() throws SnakeGameException
	{
		if (this.mapWidth < 2 || this.mapHeight < 2)
		{
			Logger.log(Logger.Level.FATAL, "Map dimensions cannot be lower than 2");
			throw new SnakeGameException("Map dimensions cannot be lower than 2");
		}
	}

	private void validateRatio() throws SnakeGameException
	{
		if (this.mapWidth % this.snakeSize != 0 || this.mapHeight % this.snakeSize != 0)
		{
			Logger.log(Logger.Level.FATAL, "Map dimensions must attune to the snake's length.");
			throw new SnakeGameException("Map dimensions must attune to the snake's length.");
		}
	}

	private void validateFoodRatio() throws SnakeGameException
	{
		if (this.foodSize > this.snakeSize)
		{
			Logger.log(Logger.Level.FATAL, "Food cannot be larger than snake.");
			throw new SnakeGameException("Food cannot be larger than snake.");
		}
	}
	
	private void validateSnakePos() throws SnakeGameException
	{
		if (this.startingSnakePos == null)
			return;
		this.allPossibleSnakePositions = generateAllPossiblePositions(this.snakeSize);
		if (!Arrays.stream(allPossibleSnakePositions).anyMatch(this.startingSnakePos::equals))
		{
			Logger.log(Logger.Level.FATAL, "Snake was placed in an invalid position: " + getRelative(this.startingSnakePos));
			throw new SnakeGameException("Snake was placed in an invalid position: " + getRelative(this.startingSnakePos));
		}
	}

	private void validateSnakeColliding() throws SnakeGameException
	{
		if (this.startingSnakePos == null)
			return;
		if (isSnakeColliding())
		{
			Logger.log(Logger.Level.FATAL, "Snake was placed in a position where it is colliding with another game object.");
			throw new SnakeGameException("Snake was placed in a position where it is colliding with another game object.");
		}
	}

	private void validateFoodPos() throws SnakeGameException
	{
		if (this.startingFoodPos == null)
			return;
		this.allPossibleFoodPositions = generateAllPossibleFoodPositions();
		boolean isInValidPosition = Arrays.stream(allPossibleFoodPositions).anyMatch(this.startingFoodPos::equals);
		if (!isInValidPosition)
		{
			Logger.log(Logger.Level.FATAL, "Food was placed in an invalid position: " + getRelative(this.startingFoodPos));
			throw new SnakeGameException("Food was placed in an invalid position: " + getRelative(this.startingFoodPos));
		}
	}

	private void validateFoodColliding() throws SnakeGameException
	{
		if (this.startingFoodPos == null)
			return;
		if (isFoodColliding())
		{
			Logger.log(Logger.Level.FATAL, "Food was placed in a position where it is colliding with another game object.");
			throw new SnakeGameException("Food was placed in a position where it is colliding with another game object.");
		}
	}

	private void initScene() throws SnakeGameException
	{
		try
		{
			generateScene();
		}
		catch (Exception e)
		{
			Logger.log(Logger.Level.FATAL, "Error generating scene.\n" + e);
			throw new SnakeGameException("Error generating scene: " + e.getMessage());
		}
	}

	private void initGameEngine() throws SnakeGameException
	{
		try
		{
			setupGameEngine();
		}
		catch (Exception e)
		{
			Logger.log(Logger.Level.FATAL, "Error setting up game engine.\n" + e);
			throw new SnakeGameException("Error setting up game engine: " + e.getMessage());
		}
	}

	private Point getAbsolute(Point relative) throws GeometricException
	{
		if (relative == null)
			return null;
		try
		{
			return relative.translate(new Vector(INIT_POS));
		}
		catch (GeometricException e)
		{
			Logger.log(Logger.Level.FATAL, "Should never happen. the absolute points should always be valid.\n" + e);
			throw new RuntimeException("Should never happen. the absolute points should always be valid.");
		}
	}

	private Polygon getAbsolute(Polygon relative)
	{
		try
		{
			return relative.translate(new Vector(INIT_POS));
		}
		catch (GeometricException e)
		{
			Logger.log(Logger.Level.FATAL, "Should never happen. the absolute points should always be valid.\n" + e);
			throw new RuntimeException("Should never happen. the absolute points should always be valid.");
		}
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

	private boolean isSnakeColliding()
	{
		return CollisionManager.collidesAny(generateFillerUnit(this.startingSnakePos, this.snakeSize), this.scene);
	}

	private boolean isFoodColliding()
	{
		for (GameObject collision : CollisionManager.getCollisions(this.food, this.scene))
			if (!(collision instanceof IFood))
				return true;

		if (this.startingSnakePos != null)
		{
			FillerUnit snakeUnit = generateFillerUnit(this.startingSnakePos, this.snakeSize);
			if (CollisionManager.collides(this.food, snakeUnit))
				return true;
		}

		return false;
	}

	private Point[] generateAllPossibleFoodPositions()
	{
		Point[] points = null;
		try
		{
			Point initialPos = getFirstPossiblePos(this.snakeSize);
			Point[] allSnakePositions = generateAllPossiblePositions(this.snakeSize);
			Point[] firstSquarePoints = generateFoodPositions(initialPos);
			points = new Point[allSnakePositions.length * firstSquarePoints.length];

			int n = 0;
			for (Point p : allSnakePositions)
			{
				Point[] foodPositions = generateFoodPositions(p);
				System.arraycopy(foodPositions, 0, points, n, foodPositions.length);
				n += foodPositions.length;
			}
		}
		catch (GeometricException e)
		{
			Logger.log(Logger.Level.FATAL, "All possible food points must be valid!\n" + e);
			throw new RuntimeException("All possible food points must be valid!\n" + e.getMessage());
		}
		return points;
	}

	private Point[] generateFoodPositions(Point initialPos) throws GeometricException
	{
		int difference = this.snakeSize - (int)Math.round(this.foodSize);
		int numOffsets = difference + 1;
		int num = numOffsets * numOffsets;
		Point[] points = new Point[num];

		double maxOffset = (double)difference / 2.0;
		for (int i = 0; i < numOffsets; i++)
			for (int j = 0; j < numOffsets; j++)
				points[i * numOffsets + j] = initialPos.translate(new Vector(-maxOffset + j, -maxOffset + i));
		return points;
	}

	private Point[] generateAllPossiblePositions(int size)
	{
		Point[] points = null;
		try
		{
			Point absoluteInitialPoint = getFirstPossiblePos(size);

			int verticalPositions = this.mapHeight / size; 
			int horizontalPositions = this.mapWidth / size;
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

	private Point getFirstPossiblePos(int size) throws GeometricException
	{
		double coordinate = ((double)size / 2.0) - 0.5;
		Point relativeInitialPoint = new Point(coordinate, coordinate);
		return getAbsolute(relativeInitialPoint);
	}

	private ISnakeController generateController()
	{
		ISnakeController controller = null;
		
		switch(this.controlMethod)
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

	private void generateScene() throws SnakeGameException
	{
		this.scene = new Scene();
		IObstacle[] obstacles = generateObstacles();
		if (obstacles != null)
			for (IObstacle obstacle : obstacles)
				scene.add((GameObject)obstacle);

		GameMap map = generateMap();
		scene.add(map);

		this.snake = generateSnake();
		scene.add(snake);

		this.food = generateFood();
		scene.add((GameObject)food);

		ISnakeController controller = generateController();
		scene.add((GameObject)controller);
		scene.add(new SnakeController(snake, controller));

		IOverlay overlay = generateOverlay();
		scene.add((GameObject)overlay);
		// this.scene = generateScene(map, this.food, this.snake, controller, obstacles, overlay);
	}

	private Snake generateSnake() throws SnakeGameException
	{
		Point snakePos = this.startingSnakePos == null ? getRandomEmptySnakePos() : this.startingSnakePos;
		Snake.Direction snakeDir = this.startingSnakeDir == null ? getRandomSnakeDir() : this.startingSnakeDir;
		return new Snake(snakePos, snakeDir, this.snakeSize, this.isFilled, this.snakeTailChar, this.snakeHeadChar);
	}

	private GameMap generateMap()
	{
		GameMap map;
		try
		{
			Rectangle mapRect = new Rectangle(INIT_POS, INIT_POS.translate(new Vector(this.mapWidth - 1, this.mapHeight - 1))); // -1 to behave as expected, not inclusive of last coord
			map = new GameMap(mapRect, this.mapChar);
		}
		catch (Exception e)
		{
			Logger.log(Logger.Level.FATAL, "Error generating game map: " + e);
			throw new RuntimeException("Error generating game map: " + e.getMessage());
		}
		return map;
	}

	public void addStaticObstacle(Polygon obstacle)
	{
		this.staticObstacles.add(getAbsolute(obstacle));
	}

	public void addDynamicObstacle(Polygon obstacle, VirtualPoint anchor, float speed)
	{
		this.dynamicObstacles.add(new DynamicObstacle(getAbsolute(obstacle), this.isFilled, this.obstacleChar, anchor, speed));
	}

	private IObstacle[] generateObstacles()
	{
		IObstacle[] obstacles = new IObstacle[this.staticObstacles.size() + this.dynamicObstacles.size()];
		int n = 0;
		for (Polygon poly : this.staticObstacles)
			obstacles[n++] = new StaticObstacle(poly, this.isFilled, this.obstacleChar);
		for (DynamicObstacle obstacle : this.dynamicObstacles)
			obstacles[n++] = new DynamicObstacle(obstacle);
		return obstacles;
	}

	private Snake.Direction getRandomSnakeDir()
	{
		Random rng = new Random(this.seed);
		Snake.Direction[] values = Snake.Direction.values();
		Snake.Direction result = values[rng.nextInt(values.length)];
		return result;
	}

	private Point getRandomEmptySnakePos()
	{
		Point[] validSpawnPositions = getValidSpawnPositions(this.snakeSize);
		Random rng = new Random(this.seed);
		return validSpawnPositions[rng.nextInt(validSpawnPositions.length)];
	}

	public Point[] getValidSpawnPositions(int size)
	{
		if (this.allPossibleSnakePositions == null)
			this.allPossibleSnakePositions = generateAllPossiblePositions(size);

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

	private Rectangle generateCamera() throws SnakeGameException
	{
		Rectangle camera;
		try
		{
			Point p0 = INIT_POS.translate(new Vector(-1, -2));
			Point p1 = INIT_POS.translate(new Vector(this.mapWidth, this.mapHeight)); // already is +1 because it's inclusive
			camera = new Rectangle(p0, p1);
		}
		catch (Exception e)
		{
			Logger.log(Logger.Level.FATAL, "Error generating game camera: " + e);
			throw new SnakeGameException("Error generating game camera: " + e.getMessage());
		}
		return camera;
	}

	private IFood generateFood() throws SnakeGameException
	{
		IFood food;
		Point foodPos = this.startingFoodPos == null ? generateRandomFoodPos() : this.startingFoodPos;
		try
		{
			switch(foodType)
			{
				case FoodType.SQUARE:
					food = new FoodSquare(foodPos, this.foodSize, this.isFilled, this.foodChar);
					break;
				case FoodType.CIRCLE:
					food = new FoodCircle(foodPos, this.foodSize / 2, this.isFilled, this.foodChar);
					break;
				default:
					Logger.log(Logger.Level.FATAL, "Unrecognized food type.");
					throw new SnakeGameException("Unrecognized food type.");
			}
		}
		catch (SnakeGameException e)
		{
			Logger.log(Logger.Level.FATAL, "Error generating food: " + e);
			throw new SnakeGameException("Error generating food: " + e.getMessage());
		}
		return food;
	}

	private Point generateRandomFoodPos()
	{
		Point[] validSpawnPositions = getValidFoodSpawnPositions();
		Random rng = new Random(this.seed);
		return validSpawnPositions[rng.nextInt(validSpawnPositions.length)];
	}

	public Point[] getValidFoodSpawnPositions()
	{
		if (this.allPossibleFoodPositions == null)
			this.allPossibleFoodPositions = generateAllPossibleFoodPositions();

		ArrayList<Point> validSpawnPositions = new ArrayList<Point>();

		for (Point position : this.allPossibleFoodPositions)
		{
			FillerUnit unit = generateFillerUnit(position, (int)Math.round(this.foodSize));
			if (!CollisionManager.collidesAny(unit, this.scene))
				validSpawnPositions.add(position);
		}

		return validSpawnPositions.toArray(new Point[0]);
	}

	private IOverlay generateOverlay()
	{
		TextOverlayOutline outline = new TextOverlayOutline();
		GameplayOverlay overlay = new GameplayOverlay(this.camera, outline);
		return overlay;
	}

	private void setupGameEngine()
	{
		GameEngine engine = GameEngine.getInstance();
		if (engine.isRunning())
			engine.stop();

		GameEngineFlags flags = new GameEngineFlags();
		flags.setTextual(this.isTextual);
		flags.setUpdateMethod(this.updateMethod);
		engine.init(flags, this.scene, this.camera);
	}

	public void setMap(char c)
	{
		this.mapChar = c;
	}

	public void setObstacle(char c)
	{
		this.obstacleChar = c;
	}

	public void setFood(char c)
	{
		this.foodChar = c;
	}

	public void setSnakeHead(char c)
	{
		this.snakeHeadChar = c;
	}

	public void setSnakeTail(char c)
	{
		this.snakeTailChar = c;
	}

	public void play()
	{
		this.snake.awake();
		GameEngine.getInstance().start();
	}

	public int score()
	{
		return (this.snake.length() - 1) * this.foodScore;
	}

	public Snake.Direction snakeDir()
	{
		return this.snake.direction();
	}
}
