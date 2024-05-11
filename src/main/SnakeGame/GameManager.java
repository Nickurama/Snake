package SnakeGame;

import Geometry.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import GameEngine.*;
import GameEngine.GameEngineFlags.*;
import SnakeGame.InputSnakeController.*;

/**
 * Class responsible for managing the game, being also the one that
 * initializes it.
 * Has the necessary methods to initialize the game, and place everything where it should be.
 * Links the necessary classes together on initialization.
 * 
 * @author Diogo Fonseca a79858
 * @version 09/05/2024
 * 
 * @inv Respawns the food when it is consumed
 * @see Snake
 * @see GameMap
 * @see IFood
 * @see IObstacle
 * @see ISnakeController
 * @see GameplayOverlay
 * @see GameoverOverlay
 * @see HighscoresOverlay
 */
public class GameManager extends GameObject implements IInputListener
{
	/**
	 * The snake's control method setting.
	*/
	public static enum ControlMethod
	{
		/**
		 * Control the snake manually.
		 */
		MANUAL,

		/**
		 * Control the snake autonomously.
		 */
		AUTO,
	}

	/**
	 * The type of food to spawn.
	*/
	public static enum FoodType
	{
		SQUARE,
		CIRCLE,
	}

	/**
	 * A representation of the current game's state
	*/
	private static enum GameState
	{
		INITIALIZATION,
		GAMEPLAY,
		GAMEOVER,
		HIGHSCORES,
	}

	private static GameManager instance = null;
	private static final char DEFAULT_MAP = '.';
	private static final char DEFAULT_FOOD = 'F';
	private static final char DEFAULT_OBSTACLE = 'O';
	private static final char DEFAULT_SNAKE_HEAD = 'H';
	private static final char DEFAULT_SNAKE_TAIL = 'T';
	private static final InputPreset DEFAULT_INPUT_PRESET = InputPreset.WASD;
	public static final long DEFAULT_SEED = 137;
	private static final Colour.Background DEFAULT_COLOUR_BG = null;
	private static final Colour.Foreground DEFAULT_COLOUR_SNAKE = null;
	private static final Colour.Foreground DEFAULT_COLOUR_FOOD = null;
	private static final Colour.Foreground DEFAULT_COLOUR_OBSTACLES = null;

	private Point initialPosition;
	private char mapChar;
	private char foodChar;
	private char obstacleChar;
	private char snakeHeadChar;
	private char snakeTailChar;
	private boolean isFirstSetup;
	private Random rng;

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
	private Colour.Background bgColour;
	private Colour.Foreground snakeColour;
	private Colour.Foreground foodColour;
	private Colour.Foreground obstaclesColour;

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

	/**
	 * Singleton instantiation.
	 * Sets default values.
	 */
	private GameManager()
	{
		this.inputPreset = DEFAULT_INPUT_PRESET;
		this.mapChar = DEFAULT_MAP;
		this.foodChar = DEFAULT_FOOD;
		this.obstacleChar = DEFAULT_OBSTACLE;
		this.snakeHeadChar = DEFAULT_SNAKE_HEAD;
		this.snakeTailChar = DEFAULT_SNAKE_TAIL;
		this.seed = DEFAULT_SEED;
		this.bgColour = DEFAULT_COLOUR_BG;
		this.snakeColour = DEFAULT_COLOUR_SNAKE;
		this.foodColour = DEFAULT_COLOUR_FOOD;
		this.obstaclesColour = DEFAULT_COLOUR_OBSTACLES;
		this.rng = new Random(this.seed);
		try
		{
			initialPosition = new Point(100, 100);
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

	/**
	 * Gets the GameManager instance
	 * @return the GameManager instance
	 */
	public static GameManager getInstance()
	{
		if (instance == null)
			instance = new GameManager();

		return instance;
	}

	/**
	 * Resets the GameManager instance
	 */
	public static void resetInstance()
	{
		if (instance != null)
			instance = new GameManager();
	}

	/**
	 * Clears all the obstacles to be instantiated
	 */
	public void clearObstacles()
	{
		this.staticObstacles = new ArrayList<Polygon>();
		this.dynamicObstacles = new ArrayList<DynamicObstacle>();
	}

	/**
	 * Same as {@link GameManager#init(int,int,Point,Direction,int,boolean,Point,double,FoodType,int,boolean,UpdateMethod,ControlMethod,long)}
	 * but generates the snake direction, snake position and food position randomly
	 */
	public void init(int mapWidth, int mapHeight, int snakeSize, boolean isFilled, double foodSize, FoodType foodType,
		int foodScore, boolean isTextual, UpdateMethod updateMethod,
		ControlMethod controlMethod, long seed) throws SnakeGameException
	{
		init(mapWidth, mapHeight, null, null, snakeSize, isFilled, null, foodSize, foodType, foodScore,
			isTextual, updateMethod, controlMethod, seed);
	}

	/**
	 * Same as {@link GameManager#init(int,int,Point,Direction,int,boolean,Point,double,FoodType,int,boolean,UpdateMethod,ControlMethod,long)}
	 * but doesn't generate random values and doesn't take null values.
	 * @pre no null values
	 */
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

	/**
	 * Initializes a GameManager, making the game ready to be played.
	 * if the snake direction, snake position or food positions are null
	 * they will be generated randomly.
	 *
	 * @param mapWidth the map's width
	 * @param mapHeight the map's height
	 * @param relativeSnakePos the initial snake position, taking the map's origin as (0,0) (can be null)
	 * @param snakeDir the initial snake direction (can be null)
	 * @param snakeSize the snake's size
	 * @param isFilled if the {@link GameObject GameObjects} should be filled}
	 * @param relativeFoodPos the initial food position, taking the map's origin as (0,0) (can be null)
	 * @param foodSize the food's size
	 * @param foodType the type of food to spawn
	 * @param foodScore the score value of a food
	 * @param isTextual if the game should be rendered textually
	 * @param updateMethod the method in which to update the engine
	 * @param controlMethod the method the snake should be controlled as
	 * @param seed the seed for random usage (can be null)
	 * @throws SnakeGameException if the initialization parameters are invalid
	 * @pre mapWidth and mapHeight should be a multiple of the snake's size
	 * @pre foodSize <= snakeSize
	 * @pre relativeSnakePos should be valid according to it's size
	 * @pre relativeFoodPos should be valid according to it's size
	 * @post game is ready to be played by calling {@link GameManager#play() play}.
	 */
	public void init(int mapWidth, int mapHeight, Point relativeSnakePos, Direction snakeDir, int snakeSize,
		boolean isFilled, Point relativeFoodPos, double foodSize, FoodType foodType, int foodScore, boolean isTextual,
		UpdateMethod updateMethod, ControlMethod controlMethod, long seed)
		throws SnakeGameException
	{
		try
		{
			this.gameState = GameState.INITIALIZATION;
			this.initialPosition = new Point(snakeSize + 10, snakeSize + 10);
			this.scene = null;
			this.snake = null;
			this.food = null;
			this.hasWon = false;
			this.hasShowedHighscores = false;
			this.mapWidth = mapWidth;
			this.mapHeight = mapHeight;
			this.seed = seed;
			this.rng = new Random(seed);
			GameMap map = new GameMap(mapWidth, mapHeight, initialPosition, ' ', seed);
			this.startingSnakePos = map.getAbsolute(relativeSnakePos);
			this.startingSnakeDir = snakeDir;
			this.snakeSize = snakeSize;
			this.isFilled = isFilled;
			this.startingFoodPos = map.getAbsolute(relativeFoodPos);
			this.foodSize = foodSize;
			this.foodType = foodType;
			this.foodScore = foodScore;
			this.isTextual = isTextual;
			this.updateMethod = updateMethod;
			this.controlMethod = controlMethod;
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

	/**
	 * Validates the game's objects, checking if everything is valid
	 * @throws SnakeGameException if a SnakeGame component was not valid
	 * @throws GeometricException if a Geometry component was not valid
	 */
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

	/**
	 * Checks if the map's dimentions are valid
	 * @throws SnakeGameException if the map's dimentions are not valid
	 */
	private void validateDimensions() throws SnakeGameException
	{
		if (this.mapWidth < 2 || this.mapHeight < 2)
		{
			Logger.log(Logger.Level.FATAL, "Map dimensions cannot be lower than 2");
			throw new SnakeGameException("Map dimensions cannot be lower than 2");
		}
	}

	/**
	 * Checks if the map-to-snake ratio is valid
	 * @throws SnakeGameException if the map-to-snake ratio is not valid
	 */
	private void validateRatio() throws SnakeGameException
	{
		if (this.mapWidth % this.snakeSize != 0 || this.mapHeight % this.snakeSize != 0)
		{
			Logger.log(Logger.Level.FATAL, "Map dimensions must attune to the snake's length.");
			throw new SnakeGameException("Map dimensions must attune to the snake's length.");
		}
	}

	/**
	 * Checks if the food-to-snake ratio is valid
	 * @throws SnakeGameException if the food-to-snake ratio is valid
	 */
	private void validateFoodRatio() throws SnakeGameException
	{
		if (this.foodSize > this.snakeSize)
		{
			Logger.log(Logger.Level.FATAL, "Food cannot be larger than snake.");
			throw new SnakeGameException("Food cannot be larger than snake.");
		}
	}
	
	/**
	 * Checks if the snake's position is valid
	 * @throws SnakeGameException if the snake's position isn't valid
	 */
	private void validateSnakePos() throws SnakeGameException
	{
		if (this.startingSnakePos == null)
			return;
		Point[] allPossibleSnakePositions = this.map.getAllPossibleUnitSpawnPositions(this.snakeSize);
		if (!Arrays.stream(allPossibleSnakePositions).anyMatch(this.startingSnakePos::equals))
		{
			Logger.log(Logger.Level.FATAL, "Snake was placed in an invalid position: " + this.map.getRelative(new VirtualPoint(this.startingSnakePos)));
			throw new SnakeGameException("Snake was placed in an invalid position: " + this.map.getRelative(new VirtualPoint(this.startingSnakePos)));
		}
	}

	/**
	 * Checks if the snake is colliding with something
	 * @throws SnakeGameException if the snake is colliding with something
	 */
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

	/**
	 * Checks if the food position is valid
	 * @throws SnakeGameException if the food position is not valid
	 */
	private void validateFoodPos() throws SnakeGameException
	{
		if (this.startingFoodPos == null)
			return;
		Point[] allPossibleFoodPositions = this.map.getAllPossibleInnerUnitPositions((int)Math.round(this.foodSize), this.snakeSize);
		boolean isInValidPosition = Arrays.stream(allPossibleFoodPositions).anyMatch(this.startingFoodPos::equals);
		if (!isInValidPosition)
		{
			Logger.log(Logger.Level.FATAL, "Food was placed in an invalid position: " + this.map.getRelative(new VirtualPoint(this.startingFoodPos)));
			throw new SnakeGameException("Food was placed in an invalid position: " + this.map.getRelative(new VirtualPoint(this.startingFoodPos)));
		}
	}

	/**
	 * Checks if the food is colliding with something
	 * @throws SnakeGameException if the food is colliging with something
	 */
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

	/**
	 * Initializes the game's {@link Scene scene}
	 * @throws SnakeGameException if an error occurred while generating the scene
	 * @post {@link Scene Scene} initialized
	 */
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

	/**
	 * Initializes the {@link GameEngine game engine}
	 * @throws SnakeGameException if an error occurred while generating the game engine
	 * @pre Scene initialized
	 * @post {@link GameEngine GameEngine} initialized
	 */
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


	/**
	 * Generates the snake's {@link ISnakeController controller}
	 * @return the snake's controller
	 * @pre {@link Scene Scene} initialized
	 * @post {@link Controller Controller} initialized
	 */
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

	/**
	 * Generates the game's {@link Scene scene}
	 * @throws SnakeGameException if there was an error generating the scene
	 * @post {@link Scene Scene} initialized
	 * @post {@link Snake Snake} initialized
	 * @post {@link GameMap Map} initialized
	 * @Post {@link IFood Food} initialized
	 * @Post {@link IObstacle Obstacles} initialized
	 */
	private void generateScene() throws SnakeGameException
	{
		this.scene = new Scene();

		this.map = generateMap();
		scene.add(map);

		IObstacle[] obstacles = generateObstacles(map);
		if (obstacles != null)
			for (IObstacle obstacle : obstacles)
				scene.add((GameObject)obstacle);

		if (this.startingSnakePos != null) // if should generate the snake first
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

	/**
	 * Generates the {@link Snake snake}
	 * @param map the map to generate the snake on
	 * @return the generated snake
	 * @throws SnakeGameException if an error occurred while generating the snake
	 */
	private Snake generateSnake(GameMap map) throws SnakeGameException
	{
		Point snakePos = this.startingSnakePos == null ? map.getRandomUnitSpawnPosition(snakeSize) : this.startingSnakePos;
		Direction snakeDir = this.startingSnakeDir == null ? getRandomSnakeDir() : this.startingSnakeDir;
		return new Snake(snakePos, snakeDir, this.snakeSize, this.isFilled, this.snakeTailChar, this.snakeHeadChar, this.snakeColour);
	}

	/**
	 * Generates the {@link Map map}
	 * @return the generated map
	 */
	private GameMap generateMap()
	{
		GameMap map;
		try
		{
			map = new GameMap(this.mapWidth, this.mapHeight, initialPosition, this.mapChar, this.seed);
		}
		catch (Exception e)
		{
			Logger.log(Logger.Level.FATAL, "Error generating game map: " + e);
			throw new RuntimeException("Error generating game map: " + e.getMessage());
		}
		return map;
	}

	/**
	 * Adds a {@link StaticObstacle static obstacle} to be added upon initialization
	 * @param obstacle the obstacle's shape
	 * @pre hasn't {@link GameManager#init() initiated}
	 */
	public void addStaticObstacle(Polygon obstacle)
	{
		this.staticObstacles.add(obstacle);
	}

	/**
	 * Adds a {@link DynamicObstacle dynamic obstacle} to be added upon initialization
	 * @param obstacle the obstacle's shape
	 * @param anchor the point the obstacle should rotate around
	 * @param speed the speed at which the obstacle should rotate around the anchor
	 * @pre hasn't {@link GameManager#init() initiated}
	 */
	public void addDynamicObstacle(Polygon obstacle, VirtualPoint anchor, float speed)
	{
		this.dynamicObstacles.add(new DynamicObstacle(obstacle, this.isFilled, this.obstacleChar, anchor, speed));
	}

	/**
	 * Sets the maximum scores to be displayed
	 * @param maxScoresDisplay the maximum ammount of scores to display
	 * @pre hasn't {@link GameManager#init() initiated}
	 */
	public void setMaxScoresDisplay(int maxScoresDisplay)
	{
		this.maxScoresDisplay = maxScoresDisplay;
	}

	/**
	 * Sets the {@link InputPreset input preset}.
	 * @param preset the preset to use as input
	 */
	public void setInputPresetMethod(InputPreset preset)
	{
		this.inputPreset = preset;
	}

	/**
	 * Generates the game obstacles from the added obstacles
	 * @param map the map to add the obstacles to
	 * @return the generated obstacles
	 */
	private IObstacle[] generateObstacles(GameMap map)
	{
		IObstacle[] obstacles = new IObstacle[this.staticObstacles.size() + this.dynamicObstacles.size()];
		int n = 0;
		for (Polygon poly : this.staticObstacles)
		{
			Polygon absoluteCollider = map.getAbsolute(poly);
			obstacles[n++] = new StaticObstacle(absoluteCollider, this.isFilled, this.obstacleChar, this.obstaclesColour);
		}
		for (DynamicObstacle obstacle : this.dynamicObstacles)
		{
			Polygon absoluteCollider = map.getAbsolute((Polygon)obstacle.getCollider());
			VirtualPoint absoluteAnchor = map.getAbsolute(((DynamicObstacle)obstacle).rotationPoint());
			obstacles[n++] = new DynamicObstacle(absoluteCollider, this.isFilled, this.obstacleChar, absoluteAnchor, obstacle.speed(), this.obstaclesColour);
		}
		return obstacles;
	}

	/**
	 * Generates a random direction
	 * @return a random direction
	 */
	private Direction getRandomSnakeDir()
	{
		Direction[] values = Direction.values();
		return values[rng.nextInt(values.length)];
	}

	/**
	 * Generates the game's camera
	 * @return the generated camera
	 * @throws SnakeGameException there was an error while generating the camera
	 * @pre {@link GameMap map} initialized
	 */
	private Rectangle generateCamera() throws SnakeGameException
	{
		Rectangle camera;
		try
		{
			Point p0 = initialPosition.translate(new Vector(-1, -2));
			Point p1 = initialPosition.translate(new Vector(this.mapWidth, this.mapHeight)); // already is +1 because it's inclusive
			camera = new Rectangle(p0, p1);
		}
		catch (Exception e)
		{
			Logger.log(Logger.Level.FATAL, "Error generating game camera: " + e);
			throw new SnakeGameException("Error generating game camera: " + e.getMessage());
		}
		return camera;
	}

	/**
	 * Generates the game's {@link IFood food}
	 * @param map the map to generate the food on
	 * @return the generated food
	 * @throws SnakeGameException if an error occurred while generating the food
	 */
	private IFood generateFood(GameMap map) throws SnakeGameException
	{
		return this.startingFoodPos == null ? generateRandomFruit(map) : generateFood(this.startingFoodPos);
	}

	/**
	 * Generates a random food
	 * @param map the map to generate the food on
	 * @return the generated food
	 * @throws SnakeGameException if an error occurred while generating the food
	 */
	private IFood generateRandomFruit(GameMap map) throws SnakeGameException
	{
		Point pos = map.getRandomInnerUnitSpawnPosition((int)Math.round(this.foodSize), this.snakeSize);
		if (pos == null)
			return null;
		IFood foodGenerated = generateFood(pos);
		return foodGenerated;
	}

	/**
	 * Generates the food at a position
	 * @param foodPos the position to generate the food at
	 * @return the generated food
	 * @throws SnakeGameException if an error occurred while generating the food
	 */
	private IFood generateFood(Point foodPos) throws SnakeGameException
	{
		IFood food;
		try
		{
			switch(foodType)
			{
				case FoodType.SQUARE:
					food = new FoodSquare(foodPos, this.foodSize, this.isFilled, this.foodChar, this.foodColour);
					break;
				case FoodType.CIRCLE:
					food = new FoodCircle(foodPos, this.foodSize / 2, this.isFilled, this.foodChar, this.foodColour);
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

	/**
	 * Generates the gameplay overlay
	 * @return the gameplay overlay generated
	 * @pre {@link Snake snake} initialized
	 * @pre Camera initialized
	 */
	private IOverlay generateOverlay()
	{
		TextOverlayOutline outline = new TextOverlayOutline();
		GameplayOverlay overlay = new GameplayOverlay(new SnakeStats(this.snake), this.camera, outline);
		return overlay;
	}

	/**
	 * Sets up the game engine for execution
	 */
	private void setupGameEngine()
	{
		GameEngine engine = GameEngine.getInstance();
		if (engine.isRunning())
		{
			Logger.log(Logger.Level.FATAL, "Tried to setup with game engine still running.");
			throw new RuntimeException("Tried to setup with game engine still running.");
		}

		GameEngineFlags flags = new GameEngineFlags();
		flags.setTextual(this.isTextual);
		flags.setUpdateMethod(this.updateMethod);
		engine.init(flags, this.scene, this.camera);
		Renderer.getInstance().setBackgroundColour(this.bgColour);
	}

	/**
	 * runs the game engine on the the initialized scene.
	 * @pre must {@link GameManager#init() initialize} first.
	 */
	public void play()
	{
		this.gameState = GameState.GAMEPLAY;
		this.snake.awake();
		GameEngine.getInstance().start();
		this.isFirstSetup = true;
	}

	/**
	 * The current score
	 * @return the current score
	 * @pre GameManager was {@link GameManager#init() initialized}.
	 */
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

	/**
	 * Restarts the scene to the initial values set when {@link GameManager#init() init} was called.
	 * Except for the snake's direction and position, and for the food's position. Which will be
	 * generated randomly upon restarting
	 */
	private void restart()
	{
		try
		{
			this.sceneHandle().remove(this);
			init(this.mapWidth, this.mapHeight, this.map.getRelative(this.startingSnakePos), this.startingSnakeDir,
				this.snakeSize, this.isFilled, this.map.getRelative(this.startingFoodPos), this.foodSize, this.foodType,
				this.foodScore, this.isTextual, this.updateMethod, this.controlMethod, rng.nextLong());

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

	/**
	 * Respawns the food
	 * @pre game is {@link GameManager#init() initialized}.
	 */
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

	/**
	 * Goes into the game over state.
	 * Sets the game over overlay
	 */
	private void gameover()
	{
		this.gameState = GameState.GAMEOVER;
		GameObject overlay = new GameoverOverlay(new SnakeStats(this.snake), this.camera, new TextOverlayOutline());
		this.scene.add(overlay);
	}

	@Override
	public void onInputReceived(String input)
	{
		if (this.gameState.equals(GameState.GAMEOVER))
		{
			registerScore(input);
			highscores();
		}
	}

	/**
	 * Registers a new score when one is generated
	 * @param name the name of the user who'se score was set
	 */
	private void registerScore(String name)
	{
		if (name.isEmpty())
		{
			System.out.println("Score not saved.");
			return;
		}

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

	/**
	 * Goes into the highscores state.
	 * Sets the highscores overlay
	 */
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

	/**
	 * The current food's position
	 * @return the current food's position
	 */
	public Point foodPos() { return this.food.position(); }

	/**
	 * Sets the background colour
	 * @param colour the background colour to be set to
	 * @pre was not {@link GameManager#init() initialized}
	 */
	public void setBackgroundColour(Colour.Background colour) { this.bgColour = colour; }

	/**
	 * Sets the snake's colour
	 * @param colour the new snake's colour
	 * @pre was not {@link GameManager#init() initialized}
	 */
	public void setSnakeColour(Colour.Foreground colour) { this.snakeColour = colour; }

	/**
	 * Sets the food's colour
	 * @param colour the new food's colour
	 * @pre was not {@link GameManager#init() initialized}
	 */
	public void setFoodColour(Colour.Foreground colour) { this.foodColour = colour; }

	/**
	 * Sets the obstacle's colour
	 * @param colour the new obstacle's colour
	 * @pre was not {@link GameManager#init() initialized}
	 */
	public void setObstaclesColour(Colour.Foreground colour) { this.obstaclesColour = colour; }

	/**
	 * Sets the map's draw character
	 * @param c the new map's draw character
	 * @pre was not {@link GameManager#init() initialized}
	 */
	public void setMap(char c) { this.mapChar = c; }

	/**
	 * Sets the obstacle's draw character
	 * @param c the new obstacle's draw character
	 * @pre was not {@link GameManager#init() initialized}
	 */
	public void setObstacle(char c) { this.obstacleChar = c; }

	/**
	 * Sets the food's draw character
	 * @param c the new food's draw character
	 * @pre was not {@link GameManager#init() initialized}
	 */
	public void setFood(char c) { this.foodChar = c; }

	/**
	 * Sets the snake's head draw character
	 * @param c the new snake'e head draw character
	 * @pre was not {@link GameManager#init() initialized}
	 */
	public void setSnakeHead(char c) { this.snakeHeadChar = c; }

	/**
	 * Sets the snake's tail draw character
	 * @param c the new snake's tail draw character
	 * @pre was not {@link GameManager#init() initialized}
	 */
	public void setSnakeTail(char c) { this.snakeTailChar = c; }
}
