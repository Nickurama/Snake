package SnakeGame;

import Geometry.*;

import java.util.Arrays;
import java.util.Collections;

import GameEngine.*;
import SnakeGame.*;

public class GameManager
{
	public static enum ControlMethod
	{
		MANUAL,
		AUTO,
	}

	private static GameManager instance = null;
	private final Point INIT_POS;

	private Scene scene;
	private Snake snake;

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
	}

	public static GameManager getInstance()
	{
		if (instance == null)
			instance = new GameManager();

		return instance;
	}

	public void init(int mapWidth, int mapHeight, char mapChar, Point relativeSnakePos, Snake.Direction snakeDir,
		int snakeSize, boolean isFilled, char snakeTail, char snakeHead, boolean isTextual,
		GameEngineFlags.UpdateMethod updateMethod, ControlMethod controlMethod) throws SnakeGameException
	{
		validateDimensions(mapWidth, mapHeight);
		validateRatio(mapWidth, mapHeight, snakeSize);
		validateSnakePos(mapWidth, mapHeight, snakeSize, relativeSnakePos);

		Rectangle camera = generateCamera(mapWidth, mapHeight);

		ISnakeController controller = generateController(controlMethod);

		try
		{
			this.scene = setupScene(camera, mapWidth, mapHeight, mapChar, relativeSnakePos, snakeDir, snakeSize,
				isFilled, snakeTail, snakeHead, controller);
		}
		catch (Exception e)
		{
			Logger.log(Logger.Level.FATAL, "Error generating scene.\n" + e);
			throw new SnakeGameException("Error generating scene: " + e.getMessage());
		}

		try
		{
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
		if (mapWidth < 1 || mapHeight < 1)
		{
			Logger.log(Logger.Level.FATAL, "Map dimensions cannot be lower than 1");
			throw new SnakeGameException("Map dimensions cannot be lower than 1");
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
	
	private void validateSnakePos(int mapWidth, int mapHeight, int snakeSize, Point snakePos) throws SnakeGameException
	{
		Point[] allPossiblePositions = generateAllPossibleSnakePositions(mapWidth, mapHeight, snakeSize);
		if (Arrays.stream(allPossiblePositions).anyMatch(snakePos::equals))
		{
			Logger.log(Logger.Level.FATAL, "Snake was placed in an invalid position: " + snakePos.toString());
			throw new SnakeGameException("Snake was placed in an invalid position: " + snakePos.toString());
		}
	}

	private Point[] generateAllPossibleSnakePositions(int mapWidth, int mapHeight, int snakeSize)
	{
		Point[] points = null;
		try
		{
			double coordinate = ((double)snakeSize / 2.0) - 0.5;
			Point relativeInitialPoint = new Point(coordinate, coordinate);
			Point absoluteInitialPoint = relativeInitialPoint.translate(new Vector(INIT_POS));

			int verticalPositions = snakeSize / mapHeight;
			int horizontalPositions = snakeSize / mapWidth;
			int maxPositions = verticalPositions * horizontalPositions;

			points = new Point[maxPositions];

			for (int i = 0; i < verticalPositions; i++)
			{
				for (int j = 0; j < horizontalPositions; j++)
				{
					Vector vector = new Vector(j * snakeSize, i * snakeSize);
					Point newPoint = absoluteInitialPoint.translate(vector);
					points[i * verticalPositions + j] = newPoint;
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

	private Scene setupScene(Rectangle camera, int mapWidth, int mapHeight, char mapChar, Point relativeSnakePos,
		Snake.Direction snakeDir, int snakeSize, boolean isFilled, char snakeTail, char snakeHead,
		ISnakeController snakeController) throws SnakeGameException, GeometricException
	{
		Point absoluteSnakePos = relativeSnakePos.translate(new Vector(INIT_POS.X(), INIT_POS.Y()));

		GameMap map = generateMap(mapWidth, mapHeight, mapChar);
		this.snake = new Snake(absoluteSnakePos, snakeDir, snakeSize, isFilled, snakeTail, snakeHead);
		GameObject overlay = generateOverlay(snake, camera);
		SnakeController controller = new SnakeController(snake, snakeController);

		Scene scene = new Scene();
		scene.add(map);
		scene.add(this.snake);
		scene.add(overlay);
		scene.add(controller);
		scene.add((GameObject)snakeController);

		return scene;
	}

	private GameMap generateMap(int mapWidth, int mapHeight, char mapChar)
	{
		GameMap map;
		try
		{
			Rectangle mapRect = new Rectangle(INIT_POS, INIT_POS.translate(new Vector(mapWidth - 1, mapHeight - 1)));
			map = new GameMap(mapRect, mapChar);
		}
		catch (Exception e)
		{
			Logger.log(Logger.Level.FATAL, "Error generating game map: " + e);
			throw new RuntimeException("Error generating game map: " + e.getMessage());
		}
		return map;
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
