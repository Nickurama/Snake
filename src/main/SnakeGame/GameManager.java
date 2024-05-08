package SnakeGame;

import Geometry.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import GameEngine.*;
import GameEngine.GameEngineFlags.*;
import SnakeGame.*;
import SnakeGame.InputSnakeController.*;

public class GameManager extends GameObject implements IInputListener
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

	private static enum GameState
	{
		INITIALIZATION,
		GAMEPLAY,
		GAMEOVER,
		HIGHSCORES,
	}

	private static GameManager instance = null;
	private final Point INIT_POS;
	private static final char DEFAULT_MAP = '.';
	private static final char DEFAULT_FOOD = 'F';
	private static final char DEFAULT_OBSTACLE = 'O';
	private static final char DEFAULT_SNAKE_HEAD = 'H';
	private static final char DEFAULT_SNAKE_TAIL = 'T';
	private static final InputPreset DEFAULT_INPUT_PRESET = InputPreset.WASD;
	private char mapChar;
	private char foodChar;
	private char obstacleChar;
	private char snakeHeadChar;
	private char snakeTailChar;
	private boolean isFirstSetup;

	private InputPreset inputPreset;
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
	private Integer maxScoresDisplay;
	private long seed;

	private Scene scene;
	private Snake snake;
	private IFood food;
	private GameMap map;
	private Rectangle camera;
	ArrayList<Polygon> staticObstacles;
	ArrayList<DynamicObstacle> dynamicObstacles;
	private GameState gameState;
	private boolean hasWon;
	private boolean hasShowedHighscores;

	private GameManager() // Singleton
	{
		this.inputPreset = DEFAULT_INPUT_PRESET;
		this.mapChar = DEFAULT_MAP;
		this.foodChar = DEFAULT_FOOD;
		this.obstacleChar = DEFAULT_OBSTACLE;
		this.snakeHeadChar = DEFAULT_SNAKE_HEAD;
		this.snakeTailChar = DEFAULT_SNAKE_TAIL;
		try
		{
			INIT_POS = new Point(100, 100);
		}
		catch(GeometricException e)
		{
			Logger.log(Logger.Level.FATAL, "Should never happen. initial position should always be valid.\n" + e);
			throw new RuntimeException("Should never happen. initial position should always be valid.\n" + e.getMessage());
		}
		this.maxScoresDisplay = null;

		this.isFirstSetup = true;
		this.scene = null;
		this.snake = null;
		this.food = null;
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
			this.gameState = GameState.INITIALIZATION;
			this.scene = null;
			this.snake = null;
			this.food = null;
			this.hasWon = false;
			this.hasShowedHighscores = false;
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
			if (this.isFirstSetup)
				initGameEngine();
			else
				GameEngine.getInstance().setScene(this.scene);
			validate();
			this.isFirstSetup = false;
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
		Point[] allPossibleSnakePositions = this.map.getAllPossibleUnitSpawnPositions(this.snakeSize);
		if (!Arrays.stream(allPossibleSnakePositions).anyMatch(this.startingSnakePos::equals))
		{
			Logger.log(Logger.Level.FATAL, "Snake was placed in an invalid position: " + getRelative(new VirtualPoint(this.startingSnakePos)));
			throw new SnakeGameException("Snake was placed in an invalid position: " + getRelative(new VirtualPoint(this.startingSnakePos)));
		}
	}

	private void validateSnakeColliding() throws SnakeGameException
	{
		if (this.startingSnakePos == null)
			return;
		
		GameObject[] objectsOccupying = this.map.getObjectsOccupyingUnit(this.startingSnakePos, this.snakeSize);
		for (GameObject obj : objectsOccupying)
		{
			if (!(obj instanceof SnakeUnit))
			{
				Logger.log(Logger.Level.FATAL, "Snake was placed in a position where it is colliding with another game object.");
				throw new SnakeGameException("Snake was placed in a position where it is colliding with another game object.");
			}
		}
	}

	private void validateFoodPos() throws SnakeGameException
	{
		if (this.startingFoodPos == null)
			return;
		Point[] allPossibleFoodPositions = this.map.getAllPossibleInnerUnitPositions((int)Math.round(this.foodSize), this.snakeSize);
		boolean isInValidPosition = Arrays.stream(allPossibleFoodPositions).anyMatch(this.startingFoodPos::equals);
		if (!isInValidPosition)
		{
			Logger.log(Logger.Level.FATAL, "Food was placed in an invalid position: " + getRelative(new VirtualPoint(this.startingFoodPos)));
			throw new SnakeGameException("Food was placed in an invalid position: " + getRelative(new VirtualPoint(this.startingFoodPos)));
		}
	}

	private void validateFoodColliding() throws SnakeGameException
	{
		if (this.startingFoodPos == null)
			return;

		GameObject[] objectsOccupying = this.map.getObjectsOccupyingUnit(this.startingFoodPos, this.foodSize);
		for (GameObject obj : objectsOccupying)
		{
			if (!(obj instanceof IFood))
			{
				Logger.log(Logger.Level.FATAL, "Food was placed in a position where it is colliding with another game object.");
				throw new SnakeGameException("Food was placed in a position where it is colliding with another game object.");
			}
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

	private VirtualPoint getAbsolute(VirtualPoint relative)
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

	private VirtualPoint getRelative(VirtualPoint absolute)
	{
		if (absolute == null)
			return null;
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

	private Point getRelative(Point absolute)
	{
		if (absolute == null)
			return null;
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

	private ISnakeController generateController()
	{
		ISnakeController controller = null;
		
		switch(this.controlMethod)
		{
			case ControlMethod.MANUAL:
				controller = new InputSnakeController(this.inputPreset, new SnakeStats(this.snake));
				break;
			case ControlMethod.AUTO:
				controller = new AISnakeController(new SnakeStats(this.snake), this.snakeSize, new FoodStats(), this.map);
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

		this.map = generateMap();
		scene.add(map);

		if (shouldGenerateSnakeFirst())
		{
			this.snake = generateSnake(map);
			scene.add(snake);
			this.food = generateFood(map);
			scene.add((GameObject)food);
		}
		else
		{
			this.food = generateFood(map);
			scene.add((GameObject)food);
			this.snake = generateSnake(map);
			scene.add(snake);
		}

		if (this.food == null)
		{
			Logger.log(Logger.Level.ERROR, "Could not spawn in fruit! The whole map is occupied.");
			throw new SnakeGameException("Could not spawn in fruit! The whole map is occupied.");
		}

		ISnakeController controller = generateController();
		scene.add((GameObject)controller);
		scene.add(new SnakeController(snake, controller));

		IOverlay overlay = generateOverlay();
		scene.add((GameObject)overlay);

		scene.add(this);
	}

	private boolean shouldGenerateSnakeFirst()
	{
		return this.startingSnakePos != null;
	}

	private Snake generateSnake(GameMap map) throws SnakeGameException
	{
		Point snakePos = this.startingSnakePos == null ? map.getRandomUnitSpawnPosition(snakeSize) : this.startingSnakePos;
		Direction snakeDir = this.startingSnakeDir == null ? getRandomSnakeDir() : this.startingSnakeDir;
		return new Snake(snakePos, snakeDir, this.snakeSize, this.isFilled, this.snakeTailChar, this.snakeHeadChar);
	}

	private GameMap generateMap()
	{
		GameMap map;
		try
		{
			map = new GameMap(this.mapWidth, this.mapHeight, INIT_POS, this.mapChar);
			map.setSeed(this.seed);
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
		this.dynamicObstacles.add(new DynamicObstacle(getAbsolute(obstacle), this.isFilled, this.obstacleChar, getAbsolute(anchor), speed));
	}

	public void setMaxScoresDisplay(int maxScoresDisplay)
	{
		this.maxScoresDisplay = maxScoresDisplay;
	}

	public void setInputPresetMethod(InputPreset preset)
	{
		this.inputPreset = preset;
	}

	private IObstacle[] generateObstacles()
	{
		IObstacle[] obstacles = new IObstacle[this.staticObstacles.size() + this.dynamicObstacles.size()];
		int n = 0;
		for (Polygon poly : this.staticObstacles)
			obstacles[n++] = new StaticObstacle(poly, this.isFilled, this.obstacleChar);
		for (DynamicObstacle obstacle : this.dynamicObstacles)
			obstacles[n++] = new DynamicObstacle((Polygon)obstacle.getCollider(), this.isFilled, this.obstacleChar, obstacle.speed());
		return obstacles;
	}

	private Direction getRandomSnakeDir()
	{
		Random rng = new Random(this.seed);
		Direction[] values = Direction.values();
		return values[rng.nextInt(values.length)];
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

	private IFood generateFood(GameMap map) throws SnakeGameException
	{
		return this.startingFoodPos == null ? generateRandomFruit(map) : generateFood(this.startingFoodPos);
	}

	private IFood generateRandomFruit(GameMap map) throws SnakeGameException
	{
		Point pos = map.getRandomInnerUnitSpawnPosition((int)Math.round(this.foodSize), this.snakeSize);
		if (pos == null)
			return null;
		IFood foodGenerated = generateFood(pos);
		return foodGenerated;
	}

	private IFood generateFood(Point foodPos) throws SnakeGameException
	{
		IFood food;
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

	private IOverlay generateOverlay()
	{
		TextOverlayOutline outline = new TextOverlayOutline();
		GameplayOverlay overlay = new GameplayOverlay(new SnakeStats(this.snake), this.camera, outline);
		return overlay;
	}

	private void setupGameEngine()
	{
		GameEngine engine = GameEngine.getInstance();
		if (engine.isRunning())
		{
			Logger.log(Logger.Level.FATAL, "Tried to setup with game engine still running.");
			throw new RuntimeException("Tried to setup with game engine still running.");
		}
		// 	engine.stop();

		GameEngineFlags flags = new GameEngineFlags();
		flags.setTextual(this.isTextual);
		flags.setUpdateMethod(this.updateMethod);
		engine.init(flags, this.scene, this.camera);
	}

	public void setMap(char c) { this.mapChar = c; }

	public void setObstacle(char c) { this.obstacleChar = c; }

	public void setFood(char c) { this.foodChar = c; }

	public void setSnakeHead(char c) { this.snakeHeadChar = c; }

	public void setSnakeTail(char c) { this.snakeTailChar = c; }

	public void play()
	{
		this.gameState = GameState.GAMEPLAY;
		this.snake.awake();
		GameEngine.getInstance().start();
		this.isFirstSetup = true;
	}

	public int score()
	{
		if (this.snake == null)
			return 0;
			
		int score = (this.snake.length() - 1) * this.foodScore;
		if (hasWon)
			score = Integer.MAX_VALUE;
		return score;
	}

	@Override
	public void lateUpdate()
	{
		if (this.gameState.equals(GameState.GAMEPLAY))
		{
			if (this.food.wasConsumed())
				respawnFood();

			if (this.snake.isDead())
				gameover();
		}
	}

	@Override
	public void update(int deltaT)
	{
		if (this.hasShowedHighscores)
			restart();
		else if (this.gameState.equals(GameState.HIGHSCORES) && !this.hasShowedHighscores)
			this.hasShowedHighscores = true;
	}

	private void restart()
	{
		try
		{
			this.sceneHandle().remove(this);
			init(this.mapWidth, this.mapHeight, getRelative(this.startingSnakePos), this.startingSnakeDir,
				this.snakeSize, this.isFilled, getRelative(this.startingFoodPos), this.foodSize, this.foodType,
				this.foodScore, this.isTextual, this.updateMethod, this.controlMethod, this.seed);
			this.gameState = GameState.GAMEPLAY;
			this.snake.awake();
			// play();
		}
		catch(SnakeGameException e)
		{
			Logger.log(Logger.Level.FATAL, "Should never happen. GameManager was valid, so with the same parameters it should still be valid.\n" + e);
			throw new RuntimeException("Should never happen. GameManager was valid, so with the same parameters it should still be valid.\n" + e.getMessage());
		}
	}

	private void respawnFood()
	{
		try
		{
			this.food = generateRandomFruit(this.map);
		}
		catch (SnakeGameException e)
		{
			Logger.log(Logger.Level.FATAL, "Error while respawning fruit: ");
			throw new RuntimeException("Error while respawning fruit: " + e.getMessage());
		}

		if (this.food == null)
		{
			this.hasWon = true;
			gameover();
		}
		else
			this.scene.add((GameObject)this.food);
	}

	private void gameover()
	{
		this.gameState = GameState.GAMEOVER;
		GameObject overlay = new GameoverOverlay(new SnakeStats(this.snake), this.camera, new TextOverlayOutline());
		this.scene.add(overlay);
	}

	public void onInputReceived(String input)
	{
		if (this.gameState.equals(GameState.GAMEOVER))
		{
			registerScore(input);
			highscores();
		}
	}

	private void registerScore(String name)
	{
		Score newScore = new Score(name, LocalDate.now(), this.score());
		try
		{
			Scoreboard.getInstance().addEntry(newScore);
			System.out.println("Score saved! :3");
		}
		catch (SnakeGameException e)
		{
			System.out.println("Could not save score! :(\n" + e.getMessage());
		}
	}

	private void highscores()
	{
		this.gameState = GameState.HIGHSCORES;
		GameObject overlay;
		if (maxScoresDisplay == null)
			overlay = new HighscoresOverlay(Scoreboard.getInstance(), this.camera, new TextOverlayOutline());
		else
			overlay = new HighscoresOverlay(Scoreboard.getInstance(), this.camera, this.maxScoresDisplay, new TextOverlayOutline());
		this.scene.add(overlay);
	}

	public Point foodPos()
	{
		return this.food.position();
	}
}
