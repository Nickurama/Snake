package SnakeGameTests;

import SnakeGame.*;
import GameEngine.*;
import Geometry.*;
import TestUtil.*;
import GameEngine.GameEngineFlags.*;
import SnakeGame.Snake.*;
import SnakeGame.GameManager.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayOutputStream;

public class GameManagerTests
{
	private static final long SEED = 137;
	private final String TEST_FILE = TestUtil.TEST_FILES_PATH + "testGameManager.ser";

	@Test
	public void ShouldPlayInSteppedManualMode() throws SnakeGameException, GeometricException
	{
		// Arrange
		GameManager.resetInstance();
		if (GameEngine.getInstance().isRunning())
			GameEngine.getInstance().stop();

		GameManager gameManager = GameManager.getInstance();
		gameManager.init(39, 9, new Point(13, 4), Direction.RIGHT, 3, true, new Point(25, 4), 3, FoodType.SQUARE, 1,
			true, UpdateMethod.STEP, ControlMethod.MANUAL, SEED);

		String expected =	"╔═══════════════════════════════════════╗\n" +
							"║.......................................║\n" +
							"║.......................................║\n" +
							"║.......................................║\n" +
							"║............HHH.........FFF............║\n" +
							"║............HHH.........FFF............║\n" +
							"║............HHH.........FFF............║\n" +
							"║.......................................║\n" +
							"║.......................................║\n" +
							"║.......................................║\n" +
							"║Dir: 0                         Score: 0║\n" +
							"╚═══════════════════════════════════════╝\n" +
							"Stopping...\n";

		// Act
		ByteArrayOutputStream out = TestUtil.setIOstreams("stop");
		gameManager.play();
		String render = out.toString();

		// Assert
		assertEquals(expected, render);
	}

	@Test
	public void ShouldStepInSteppedManualMode() throws SnakeGameException, GeometricException
	{
		// Arrange
		GameManager.resetInstance();
		if (GameEngine.getInstance().isRunning())
			GameEngine.getInstance().stop();

		GameManager gameManager = GameManager.getInstance();
		gameManager.init(39, 9, new Point(13, 4), Direction.RIGHT, 3, true, new Point(25, 4), 3, FoodType.SQUARE, 1,
			true, UpdateMethod.STEP, ControlMethod.MANUAL, SEED);

		String expected =	"╔═══════════════════════════════════════╗\n" +
							"║.......................................║\n" +
							"║.......................................║\n" +
							"║.......................................║\n" +
							"║............HHH.........FFF............║\n" +
							"║............HHH.........FFF............║\n" +
							"║............HHH.........FFF............║\n" +
							"║.......................................║\n" +
							"║.......................................║\n" +
							"║.......................................║\n" +
							"║Dir: 0                         Score: 0║\n" +
							"╚═══════════════════════════════════════╝\n" +
							"Stepping...\n" +
							"╔═══════════════════════════════════════╗\n" +
							"║.......................................║\n" +
							"║.......................................║\n" +
							"║.......................................║\n" +
							"║...............HHH......FFF............║\n" +
							"║...............HHH......FFF............║\n" +
							"║...............HHH......FFF............║\n" +
							"║.......................................║\n" +
							"║.......................................║\n" +
							"║.......................................║\n" +
							"║Dir: 0                         Score: 0║\n" +
							"╚═══════════════════════════════════════╝\n" +
							"Stopping...\n";

		// Act
		ByteArrayOutputStream out = TestUtil.setIOstreams("step\n" + "stop");
		gameManager.play();
		String render = out.toString();

		// Assert
		assertEquals(expected, render);
	}

	@Test
	public void ShouldTurnInSteppedManualMode() throws SnakeGameException, GeometricException
	{
		// Arrange
		GameManager.resetInstance();
		if (GameEngine.getInstance().isRunning())
			GameEngine.getInstance().stop();

		GameManager gameManager = GameManager.getInstance();
		gameManager.init(39, 9, new Point(13, 4), Direction.RIGHT, 3, true, new Point(25, 4), 3, FoodType.SQUARE, 1,
			true, UpdateMethod.STEP, ControlMethod.MANUAL, SEED);

		String expected =	"╔═══════════════════════════════════════╗\n" +
							"║.......................................║\n" +
							"║.......................................║\n" +
							"║.......................................║\n" +
							"║............HHH.........FFF............║\n" +
							"║............HHH.........FFF............║\n" +
							"║............HHH.........FFF............║\n" +
							"║.......................................║\n" +
							"║.......................................║\n" +
							"║.......................................║\n" +
							"║Dir: 0                         Score: 0║\n" +
							"╚═══════════════════════════════════════╝\n" +
							"Snake will turn right.\n" +
							"Stepping...\n" +
							"╔═══════════════════════════════════════╗\n" +
							"║.......................................║\n" +
							"║.......................................║\n" +
							"║.......................................║\n" +
							"║........................FFF............║\n" +
							"║........................FFF............║\n" +
							"║........................FFF............║\n" +
							"║............HHH........................║\n" +
							"║............HHH........................║\n" +
							"║............HHH........................║\n" +
							"║Dir: 270                       Score: 0║\n" +
							"╚═══════════════════════════════════════╝\n" +
							"Stopping...\n";

		// Act
		ByteArrayOutputStream out = TestUtil.setIOstreams("right\n" + "step\n" + "stop");
		gameManager.play();
		String render = out.toString();

		// Assert
		assertEquals(expected, render);
	}

	@Test
	public void ShouldThrowWhenInvalidMapSnakeRatio() throws SnakeGameException, GeometricException
	{
		// Arrange
		GameManager.resetInstance();
		GameManager.resetInstance();
		if (GameEngine.getInstance().isRunning())
			GameEngine.getInstance().stop();

		GameManager gameManager = GameManager.getInstance();

		// Act
		// Assert
		assertThrows(SnakeGameException.class, () -> gameManager.init(
			39, 10, new Point(13, 4), Direction.RIGHT, 3, true, new Point(25, 4), 3, FoodType.SQUARE, 1,
			true, UpdateMethod.STEP, ControlMethod.MANUAL, SEED));
		assertThrows(SnakeGameException.class, () -> gameManager.init(
			38, 9, new Point(13, 4), Direction.RIGHT, 3, true, new Point(25, 4), 3, FoodType.SQUARE, 1,
			true, UpdateMethod.STEP, ControlMethod.MANUAL, SEED));
	}

	@Test
	public void ShouldThrowWhenInvalidSnakePosition() throws SnakeGameException, GeometricException
	{
		// Arrange
		GameManager.resetInstance();
		if (GameEngine.getInstance().isRunning())
			GameEngine.getInstance().stop();

		GameManager gameManager = GameManager.getInstance();

		// Act
		// Assert
		assertThrows(SnakeGameException.class, () -> gameManager.init(
			38, 8, new Point(13.5, 4.5), Direction.RIGHT, 2, true, new Point(25, 4), 2, FoodType.SQUARE, 1,
			true, UpdateMethod.STEP, ControlMethod.MANUAL, SEED));
		assertThrows(SnakeGameException.class, () -> gameManager.init(
			39, 9, new Point(2, 2), Direction.RIGHT, 3, true, new Point(25.5, 4.5), 2, FoodType.SQUARE, 1,
			true, UpdateMethod.STEP, ControlMethod.MANUAL, SEED));
	}

	@Test
	public void ShouldThrowWhenInvalidMapDimensions()
	{
		// Arrange
		GameManager.resetInstance();
		if (GameEngine.getInstance().isRunning())
			GameEngine.getInstance().stop();

		GameManager gameManager = GameManager.getInstance();

		// Act
		// Assert
		assertThrows(SnakeGameException.class, () -> gameManager.init(
			3, 1, new Point(13.5, 4.5), Direction.RIGHT, 2, true, new Point(25.5, 4.5), 2, FoodType.SQUARE, 1,
			true, UpdateMethod.STEP, ControlMethod.MANUAL, SEED));
		assertThrows(SnakeGameException.class, () -> gameManager.init(
			1, 3, new Point(13.5, 4.5), Direction.RIGHT, 2, true, new Point(25.5, 4.5), 2, FoodType.SQUARE, 1,
			true, UpdateMethod.STEP, ControlMethod.MANUAL, SEED));
		assertThrows(SnakeGameException.class, () -> gameManager.init(
			-1, -5, new Point(13.5, 4.5), Direction.RIGHT, 2, true, new Point(25.5, 4.5), 2, FoodType.SQUARE, 1,
			true, UpdateMethod.STEP, ControlMethod.MANUAL, SEED));
	}

	@Test
	public void ShouldThrowWhenFoodSquareBiggerThanSnake()
	{
		// Arrange
		GameManager.resetInstance();
		if (GameEngine.getInstance().isRunning())
			GameEngine.getInstance().stop();

		GameManager gameManager = GameManager.getInstance();

		// Act
		// Assert
		assertThrows(SnakeGameException.class, () -> gameManager.init(
			39, 9, new Point(13.5, 4.5), Direction.RIGHT, 2, true, new Point(25, 4), 3, FoodType.SQUARE, 1,
			true, UpdateMethod.STEP, ControlMethod.MANUAL, SEED));
		assertThrows(SnakeGameException.class, () -> gameManager.init(
			39, 9, new Point(13, 4), Direction.RIGHT, 3, true, new Point(25, 4), 5, FoodType.SQUARE, 1,
			true, UpdateMethod.STEP, ControlMethod.MANUAL, SEED));
	}

	@Test
	public void ShouldGenerateRandomSnakeDirection() throws GeometricException, SnakeGameException
	{
		// Arrange
		GameManager.resetInstance();
		if (GameEngine.getInstance().isRunning())
			GameEngine.getInstance().stop();

		GameManager gameManager = GameManager.getInstance();
		gameManager.init(39, 9, new Point(13, 4), null, 3, true, new Point(25, 4), 3, FoodType.CIRCLE, 1,
			true, UpdateMethod.STEP, ControlMethod.MANUAL, SEED);

		String expected =	"╔═══════════════════════════════════════╗\n" +
							"║.......................................║\n" +
							"║.......................................║\n" +
							"║.......................................║\n" +
							"║............HHH..........F.............║\n" +
							"║............HHH.........FFF............║\n" +
							"║............HHH..........F.............║\n" +
							"║.......................................║\n" +
							"║.......................................║\n" +
							"║.......................................║\n" +
							"║Dir: 270                       Score: 0║\n" +
							"╚═══════════════════════════════════════╝\n" +
							"Stopping...\n";

		// Act
		ByteArrayOutputStream out = TestUtil.setIOstreams("stop");
		gameManager.play();
		String render = out.toString();

		// Assert
		assertEquals(expected, render);
	}

	@Test
	public void ShouldGenerateRandomSnakePosition() throws GeometricException, SnakeGameException
	{
		// Arrange
		GameManager.resetInstance();
		if (GameEngine.getInstance().isRunning())
			GameEngine.getInstance().stop();

		GameManager gameManager = GameManager.getInstance();
		gameManager.init(39, 9, null, null, 3, true, new Point(25, 4), 3, FoodType.SQUARE, 1,
			true, UpdateMethod.STEP, ControlMethod.MANUAL, SEED);

		String expected =	"╔═══════════════════════════════════════╗\n" +
							"║..............................HHH......║\n" +
							"║..............................HHH......║\n" +
							"║..............................HHH......║\n" +
							"║........................FFF............║\n" +
							"║........................FFF............║\n" +
							"║........................FFF............║\n" +
							"║.......................................║\n" +
							"║.......................................║\n" +
							"║.......................................║\n" +
							"║Dir: 270                       Score: 0║\n" +
							"╚═══════════════════════════════════════╝\n" +
							"Stopping...\n";

		// Act
		ByteArrayOutputStream out = TestUtil.setIOstreams("stop");
		gameManager.play();
		String render = out.toString();

		// Assert
		assertEquals(expected, render);
	}

	@Test
	public void ShouldThrowWhenFoodCircleIsBiggerThanSnake()
	{
		// Arrange
		GameManager.resetInstance();
		if (GameEngine.getInstance().isRunning())
			GameEngine.getInstance().stop();

		GameManager gameManager = GameManager.getInstance();

		// Act
		// Assert
		assertThrows(SnakeGameException.class, () -> gameManager.init(
			39, 9, new Point(13.5, 4.5), Direction.RIGHT, 2, true, new Point(25, 4), 3, FoodType.SQUARE, 1,
			true, UpdateMethod.STEP, ControlMethod.MANUAL, SEED));
		assertThrows(SnakeGameException.class, () -> gameManager.init(
			39, 9, new Point(13, 4), Direction.RIGHT, 3, true, new Point(25, 4), 5, FoodType.SQUARE, 1,
			true, UpdateMethod.STEP, ControlMethod.MANUAL, SEED));
	}

	@Test
	public void ShouldThrowOnInvalidFoodPosition()
	{
		// Arrange
		if (GameEngine.getInstance().isRunning())
			GameEngine.getInstance().stop();

		GameManager gameManager = GameManager.getInstance();

		// Act
		// Assert
		assertThrows(SnakeGameException.class, () -> gameManager.init(
			39, 9, new Point(13, 4), Direction.RIGHT, 3, true, new Point(26, 4), 3, FoodType.SQUARE, 1,
			true, UpdateMethod.STEP, ControlMethod.MANUAL, SEED));
		assertThrows(SnakeGameException.class, () -> gameManager.init(
			39, 9, new Point(13, 4), Direction.RIGHT, 3, true, new Point(25, 5), 3, FoodType.SQUARE, 1,
			true, UpdateMethod.STEP, ControlMethod.MANUAL, SEED));
		assertThrows(SnakeGameException.class, () -> gameManager.init(
			39, 9, new Point(13, 4), Direction.RIGHT, 3, true, new Point(23.5, 3.5), 2, FoodType.SQUARE, 1,
			true, UpdateMethod.STEP, ControlMethod.MANUAL, SEED));
		assertThrows(SnakeGameException.class, () -> gameManager.init(
			39, 9, new Point(0.5, 0.5), Direction.RIGHT, 2, true, new Point(23.5, 3.5), 2, FoodType.SQUARE, 1,
			true, UpdateMethod.STEP, ControlMethod.MANUAL, SEED));
	}

	@Test
	public void ShouldThrowWhenFoodIsPlacedOnTopOfSnake()
	{
		// Arrange
		GameManager.resetInstance();
		if (GameEngine.getInstance().isRunning())
			GameEngine.getInstance().stop();

		GameManager gameManager = GameManager.getInstance();

		// Act
		// Assert
		assertThrows(SnakeGameException.class, () -> gameManager.init(
			39, 9, new Point(13, 4), Direction.RIGHT, 3, true, new Point(13, 4), 3, FoodType.SQUARE, 1,
			true, UpdateMethod.STEP, ControlMethod.MANUAL, SEED));
		assertThrows(SnakeGameException.class, () -> gameManager.init(
			39, 9, new Point(13, 4), Direction.RIGHT, 3, true, new Point(13, 4), 1, FoodType.SQUARE, 1,
			true, UpdateMethod.STEP, ControlMethod.MANUAL, SEED));
		assertThrows(SnakeGameException.class, () -> gameManager.init(
			39, 9, new Point(13, 4), Direction.RIGHT, 3, true, new Point(13, 4), 3, FoodType.CIRCLE, 1,
			true, UpdateMethod.STEP, ControlMethod.MANUAL, SEED));
	}

	@Test
	public void ShouldThrowWhenFoodIsPlacedOnTopOfObstacle() throws GeometricException
	{
		// Arrange
		GameManager.resetInstance();
		if (GameEngine.getInstance().isRunning())
			GameEngine.getInstance().stop();

		Polygon obstacle = new Polygon(new Point[] {
			new Point(12, 3),
			new Point(14, 3),
			new Point(14, 5),
			new Point(12, 5),
		});

		GameManager gameManager = GameManager.getInstance();
		gameManager.addStaticObstacle(obstacle);

		// Act
		// Assert
		assertThrows(SnakeGameException.class, () -> gameManager.init(
			39, 9, new Point(25, 4), Direction.RIGHT, 3, true, new Point(13, 4), 3, FoodType.SQUARE, 1,
			true, UpdateMethod.STEP, ControlMethod.MANUAL, SEED));
		assertThrows(SnakeGameException.class, () -> gameManager.init(
			39, 9, new Point(25, 4), Direction.RIGHT, 1, true, new Point(13, 4), 1, FoodType.CIRCLE, 1,
			true, UpdateMethod.STEP, ControlMethod.MANUAL, SEED));
	}

	@Test
	public void ShouldThrowWhenSnakeIsPlacedOnTopOfObstacle() throws GeometricException
	{
		// Arrange
		GameManager.resetInstance();
		if (GameEngine.getInstance().isRunning())
			GameEngine.getInstance().stop();

		Polygon obstacle = new Polygon(new Point[] {
			new Point(12, 3),
			new Point(14, 3),
			new Point(14, 5),
			new Point(12, 5),
		});

		GameManager gameManager = GameManager.getInstance();
		gameManager.addStaticObstacle(obstacle);

		// Act
		// Assert
		assertThrows(SnakeGameException.class, () -> gameManager.init(
			39, 9, new Point(13, 4), Direction.RIGHT, 3, true, new Point(25, 4), 3, FoodType.SQUARE, 1,
			true, UpdateMethod.STEP, ControlMethod.MANUAL, SEED));
		assertThrows(SnakeGameException.class, () -> gameManager.init(
			39, 9, new Point(13, 4), Direction.RIGHT, 1, true, new Point(25, 4), 1, FoodType.CIRCLE, 1,
			true, UpdateMethod.STEP, ControlMethod.MANUAL, SEED));
	}

	@Test
	public void ShouldPutObstacle() throws GeometricException, SnakeGameException
	{
		// Arrange
		GameManager.resetInstance();
		if (GameEngine.getInstance().isRunning())
			GameEngine.getInstance().stop();

		Polygon obstacle = new Polygon(new Rectangle(new Point(1, 1), new Point(5, 5)));

		GameManager gameManager = GameManager.getInstance();
		gameManager.addStaticObstacle(obstacle);
		gameManager.init(39, 9, new Point(13, 4), Direction.RIGHT, 3, true, new Point(25, 4), 3, FoodType.SQUARE, 1,
			true, UpdateMethod.STEP, ControlMethod.MANUAL, SEED);

		String expected =	"╔═══════════════════════════════════════╗\n" +
							"║.......................................║\n" +
							"║.......................................║\n" +
							"║.......................................║\n" +
							"║.OOOOO......HHH.........FFF............║\n" +
							"║.OOOOO......HHH.........FFF............║\n" +
							"║.OOOOO......HHH.........FFF............║\n" +
							"║.OOOOO.................................║\n" +
							"║.OOOOO.................................║\n" +
							"║.......................................║\n" +
							"║Dir: 0                         Score: 0║\n" +
							"╚═══════════════════════════════════════╝\n" +
							"Stopping...\n";

		// Act
		ByteArrayOutputStream out = TestUtil.setIOstreams("stop");
		gameManager.play();
		String render = out.toString();

		// Assert
		assertEquals(expected, render);
	}

	@Test
	public void ShouldGenerateRandomFoodPosition() throws GeometricException, SnakeGameException
	{
		// Arrange
		GameManager.resetInstance();
		if (GameEngine.getInstance().isRunning())
			GameEngine.getInstance().stop();

		GameManager gameManager = GameManager.getInstance();
		gameManager.init(40, 12, new Point(13.5, 5.5), null, 4, true, null, 2, FoodType.CIRCLE, 1,
			true, UpdateMethod.STEP, ControlMethod.MANUAL, SEED);

		String expected =	"╔════════════════════════════════════════╗\n" +
							"║........................................║\n" +
							"║FF......................................║\n" +
							"║FF......................................║\n" +
							"║........................................║\n" +
							"║............HHHH........................║\n" +
							"║............HHHH........................║\n" +
							"║............HHHH........................║\n" +
							"║............HHHH........................║\n" +
							"║........................................║\n" +
							"║........................................║\n" +
							"║........................................║\n" +
							"║........................................║\n" +
							"║Dir: 270                        Score: 0║\n" +
							"╚════════════════════════════════════════╝\n" +
							"Stopping...\n";

		// Act
		ByteArrayOutputStream out = TestUtil.setIOstreams("stop");
		gameManager.play();
		String render = out.toString();

		// Assert
		assertEquals(expected, render);
	}

	@Test
	public void ShouldRespawnFoodInReachableArea() throws GeometricException, GameEngineException, SnakeGameException
	{
		// Arrange
		Rectangle obstacle = new Rectangle(new Point(0, 0), new Point(1, 0.4));

		new GameManagerBuilder()
			.setSeed(SEED)
			.addObstacle(obstacle)
			.setMapWidth(2)
			.setMapHeight(2)
			.setSnakePos(new Point(1, 1))
			.setSnakeSize(1)
			.setTextual(true)
			.setFoodSize(1)
			.setSnakeDir(Snake.Direction.UP)
			.setFoodScore(2)
			.setFoodType(GameManager.FoodType.SQUARE)
			.setFilled(true)
			.setUpdateMethod(GameEngineFlags.UpdateMethod.CODE)
			.setControlMethod(GameManager.ControlMethod.MANUAL)
			.setMapChar(' ')
			.setSnakeHeadChar('x')
			.setSnakeTailChar('o')
			.setFoodChar('f')
			.build();

		GameEngine engine = GameEngine.getInstance();

		ByteArrayOutputStream out = TestUtil.setIOstreams("");

		String expected =	"╔══╗\n" +
							"║fx║\n" +
							"║OO║\n" +
							"║  ║\n" +
							"╚══╝\n";

		// Act
		engine.start();
		String render = out.toString();
		out.reset();
		engine.stop();

		// Assert
		assertEquals(expected, render);
	}

	@Test
	public void ShouldCallWinWhenCantRespawnFood() throws GeometricException, GameEngineException, SnakeGameException
	{
		// Arrange
		Rectangle obstacle0 = new Rectangle(new Point(2, 0), new Point(39, 9));
		Rectangle obstacle1 = new Rectangle(new Point(0, 2), new Point(39, 9));

		new GameManagerBuilder()
			.addObstacle(obstacle0)
			.addObstacle(obstacle1)
			.setSeed(SEED)
			.setMapWidth(40)
			.setMapHeight(10)
			.setSnakePos(new Point(0, 0))
			.setSnakeSize(1)
			.setTextual(true)
			.setFoodSize(1)
			.setSnakeDir(Snake.Direction.RIGHT)
			.setFoodPos(new Point(1, 0))
			.setFoodScore(2)
			.setFoodType(GameManager.FoodType.SQUARE)
			.setFilled(true)
			.setUpdateMethod(GameEngineFlags.UpdateMethod.STEP)
			.setControlMethod(GameManager.ControlMethod.MANUAL)
			.setMapChar(' ')
			.setSnakeHeadChar('h')
			.setSnakeTailChar('t')
			.setObstacleChar('-')
			.setFoodChar('F')
			.build();

		ByteArrayOutputStream out = TestUtil.setIOstreams(	"step\n" +
															"left\n" +
															"step\n" +
															"left\n" +
															"step\n" +
															"left\n" +
															"step\n" +
															"left\n" +
															"step\n" +
															"left\n" +
															"step\n" +
															"left\n" +
															"step\n" +
															"stop\n"
		);

		String expected =	"╔════════════════════════════════════════╗\n" +
							"║----------------------------------------║\n" +
							"║----------------------------------------║\n" +
							"║----------------------------------------║\n" +
							"║----------------------------------------║\n" +
							"║----------------------------------------║\n" +
							"║----------------------------------------║\n" +
							"║----------------------------------------║\n" +
							"║----------------------------------------║\n" +
							"║  --------------------------------------║\n" +
							"║hF--------------------------------------║\n" +
							"║Dir: 0                          Score: 0║\n" +
							"╚════════════════════════════════════════╝\n" +
							"Stepping...\n" +
							"╔════════════════════════════════════════╗\n" +
							"║----------------------------------------║\n" +
							"║----------------------------------------║\n" +
							"║----------------------------------------║\n" +
							"║----------------------------------------║\n" +
							"║----------------------------------------║\n" +
							"║----------------------------------------║\n" +
							"║----------------------------------------║\n" +
							"║----------------------------------------║\n" +
							"║  --------------------------------------║\n" +
							"║Fh--------------------------------------║\n" +
							"║Dir: 0                          Score: 2║\n" +
							"╚════════════════════════════════════════╝\n" +
							"Snake will turn left.\n" +
							"Stepping...\n" +
							"╔════════════════════════════════════════╗\n" +
							"║----------------------------------------║\n" +
							"║----------------------------------------║\n" +
							"║----------------------------------------║\n" +
							"║----------------------------------------║\n" +
							"║----------------------------------------║\n" +
							"║----------------------------------------║\n" +
							"║----------------------------------------║\n" +
							"║----------------------------------------║\n" +
							"║ h--------------------------------------║\n" +
							"║Ft--------------------------------------║\n" +
							"║Dir: 90                         Score: 2║\n" +
							"╚════════════════════════════════════════╝\n" +
							"Snake will turn left.\n" +
							"Stepping...\n" +
							"╔════════════════════════════════════════╗\n" +
							"║----------------------------------------║\n" +
							"║----------------------------------------║\n" +
							"║----------------------------------------║\n" +
							"║----------------------------------------║\n" +
							"║----------------------------------------║\n" +
							"║----------------------------------------║\n" +
							"║----------------------------------------║\n" +
							"║----------------------------------------║\n" +
							"║ht--------------------------------------║\n" +
							"║F --------------------------------------║\n" +
							"║Dir: 180                        Score: 2║\n" +
							"╚════════════════════════════════════════╝\n" +
							"Snake will turn left.\n" +
							"Stepping...\n" +
							"╔════════════════════════════════════════╗\n" +
							"║----------------------------------------║\n" +
							"║----------------------------------------║\n" +
							"║----------------------------------------║\n" +
							"║----------------------------------------║\n" +
							"║----------------------------------------║\n" +
							"║----------------------------------------║\n" +
							"║----------------------------------------║\n" +
							"║----------------------------------------║\n" +
							"║tF--------------------------------------║\n" +
							"║h --------------------------------------║\n" +
							"║Dir: 270                        Score: 4║\n" +
							"╚════════════════════════════════════════╝\n" +
							"Snake will turn left.\n" +
							"Stepping...\n" +
							"╔════════════════════════════════════════╗\n" +
							"║----------------------------------------║\n" +
							"║----------------------------------------║\n" +
							"║----------------------------------------║\n" +
							"║----------------------------------------║\n" +
							"║----------------------------------------║\n" +
							"║----------------------------------------║\n" +
							"║----------------------------------------║\n" +
							"║----------------------------------------║\n" +
							"║tF--------------------------------------║\n" +
							"║th--------------------------------------║\n" +
							"║Dir: 0                          Score: 4║\n" +
							"╚════════════════════════════════════════╝\n" +
							"Snake will turn left.\n" +
							"Stepping...\n" +
							"╔════════════════════════════════════════╗\n" +
							"║----------------------------------------║\n" +
							"║----------------------------------------║\n" +
							"║----------------------------------------║\n" +
							"║----------------------------------------║\n" +
							"║----------------------------------------║\n" +
							"║----------------------------------------║\n" +
							"║----------------------------------------║\n" +
							"║----------------------------------------║\n" +
							"║Fh--------------------------------------║\n" +
							"║tt--------------------------------------║\n" +
							"║Dir: 90                         Score: 6║\n" +
							"╚════════════════════════════════════════╝\n" +
							"Snake will turn left.\n" +
							"Stepping...\n" +
							"╔════════════════════════════════════════╗\n" +
							"║                                        ║\n" +
							"║                                        ║\n" +
							"║               Game Over!               ║\n" +
							"║                                        ║\n" +
							"║                                        ║\n" +
							"║           Score: 2147483647            ║\n" +
							"║                                        ║\n" +
							"║                                        ║\n" +
							"║           What is your name?           ║\n" +
							"║                                        ║\n" +
							"║                                        ║\n" +
							"╚════════════════════════════════════════╝\n" +
							"Stopping...\n";

		// Act
		GameManager.getInstance().play();
		String render = out.toString();
		out.reset();

		// Assert
		assertEquals(expected, render);
	}

	@Test
	public void ShouldCallGameOverWhenSnakeDies() throws SnakeGameException, GeometricException
	{
		new GameManagerBuilder()
			.setSeed(SEED)
			.setMapWidth(40)
			.setMapHeight(10)
			.setSnakePos(new Point(1, 8))
			.setSnakeSize(1)
			.setTextual(true)
			.setFoodSize(1)
			.setSnakeDir(Snake.Direction.UP)
			.setFoodPos(new Point(1, 0))
			.setFoodScore(2)
			.setFoodType(GameManager.FoodType.SQUARE)
			.setFilled(true)
			.setUpdateMethod(GameEngineFlags.UpdateMethod.STEP)
			.setControlMethod(GameManager.ControlMethod.MANUAL)
			.setMapChar(' ')
			.setSnakeHeadChar('h')
			.setSnakeTailChar('t')
			.setObstacleChar('-')
			.setFoodChar('f')
			.build();

		ByteArrayOutputStream out = TestUtil.setIOstreams("step\nstep\nstop\n");


		String expected =	"╔════════════════════════════════════════╗\n" +
							"║                                        ║\n" +
							"║ h                                      ║\n" +
							"║                                        ║\n" +
							"║                                        ║\n" +
							"║                                        ║\n" +
							"║                                        ║\n" +
							"║                                        ║\n" +
							"║                                        ║\n" +
							"║                                        ║\n" +
							"║ f                                      ║\n" +
							"║Dir: 90                         Score: 0║\n" +
							"╚════════════════════════════════════════╝\n" +
							"Stepping...\n" +
							"╔════════════════════════════════════════╗\n" +
							"║ h                                      ║\n" +
							"║                                        ║\n" +
							"║                                        ║\n" +
							"║                                        ║\n" +
							"║                                        ║\n" +
							"║                                        ║\n" +
							"║                                        ║\n" +
							"║                                        ║\n" +
							"║                                        ║\n" +
							"║ f                                      ║\n" +
							"║Dir: 90                         Score: 0║\n" +
							"╚════════════════════════════════════════╝\n" +
							"Stepping...\n" +
							"╔════════════════════════════════════════╗\n" +
							"║                                        ║\n" +
							"║                                        ║\n" +
							"║               Game Over!               ║\n" +
							"║                                        ║\n" +
							"║                                        ║\n" +
							"║                Score: 0                ║\n" +
							"║                                        ║\n" +
							"║                                        ║\n" +
							"║           What is your name?           ║\n" +
							"║                                        ║\n" +
							"║                                        ║\n" +
							"╚════════════════════════════════════════╝\n" +
							"Stopping...\n";

		// Act
		GameManager.getInstance().play();
		String render = out.toString();
		out.reset();

		// Assert
		assertEquals(expected, render);
	}

	@Test
	public void ShouldDisplayScoresAfterGameOver() throws SnakeGameException, GeometricException
	{
		// Arrange
		Scoreboard scoreboard = Scoreboard.getInstance();
		scoreboard.setFile(TEST_FILE);
		scoreboard.purgeScores();

		new GameManagerBuilder()
			.setSeed(SEED)
			.setMapWidth(40)
			.setMapHeight(10)
			.setSnakePos(new Point(1, 8))
			.setSnakeSize(1)
			.setTextual(true)
			.setFoodSize(1)
			.setSnakeDir(Snake.Direction.UP)
			.setFoodPos(new Point(1, 9))
			.setFoodScore(5)
			.setFoodType(GameManager.FoodType.SQUARE)
			.setFilled(true)
			.setUpdateMethod(GameEngineFlags.UpdateMethod.STEP)
			.setControlMethod(GameManager.ControlMethod.MANUAL)
			.setMapChar(' ')
			.setSnakeHeadChar('h')
			.setSnakeTailChar('t')
			.setObstacleChar('-')
			.setFoodChar('f')
			.build();

		ByteArrayOutputStream out = TestUtil.setIOstreams("step\nstep\ntest name\nstep\nstop\n");


		String expected =	"╔════════════════════════════════════════╗\n" +
							"║ f                                      ║\n" +
							"║ h                                      ║\n" +
							"║                                        ║\n" +
							"║                                        ║\n" +
							"║                                        ║\n" +
							"║                                        ║\n" +
							"║                                        ║\n" +
							"║                                        ║\n" +
							"║                                        ║\n" +
							"║                                        ║\n" +
							"║Dir: 90                         Score: 0║\n" +
							"╚════════════════════════════════════════╝\n" +
							"Stepping...\n" +
							"╔════════════════════════════════════════╗\n" +
							"║ h                                      ║\n" +
							"║                                        ║\n" +
							"║  f                                     ║\n" +
							"║                                        ║\n" +
							"║                                        ║\n" +
							"║                                        ║\n" +
							"║                                        ║\n" +
							"║                                        ║\n" +
							"║                                        ║\n" +
							"║                                        ║\n" +
							"║Dir: 90                         Score: 5║\n" +
							"╚════════════════════════════════════════╝\n" +
							"Stepping...\n" +
							"╔════════════════════════════════════════╗\n" +
							"║                                        ║\n" +
							"║                                        ║\n" +
							"║               Game Over!               ║\n" +
							"║                                        ║\n" +
							"║                                        ║\n" +
							"║                Score: 5                ║\n" +
							"║                                        ║\n" +
							"║                                        ║\n" +
							"║           What is your name?           ║\n" +
							"║                                        ║\n" +
							"║                                        ║\n" +
							"╚════════════════════════════════════════╝\n" +
							"Score saved! :3\n" +
							"Stepping...\n" +
							"╔════════════════════════════════════════╗\n" +
							"║                                        ║\n" +
							"║               Highscores               ║\n" +
							"║                                        ║\n" +
							"║ 1. test name  5  06/05/2024            ║\n" +
							"║                                        ║\n" +
							"║                                        ║\n" +
							"║                                        ║\n" +
							"║                                        ║\n" +
							"║                                        ║\n" +
							"║                                        ║\n" +
							"║                                        ║\n" +
							"╚════════════════════════════════════════╝\n" +
							"Stopping...\n";

		// Act
		GameManager.getInstance().play();
		String render = out.toString();
		out.reset();

		// Assert
		assertEquals(expected, render);
	}

	@Test
	public void ShouldStartPlayingAgainAfterGameOver() throws SnakeGameException, GeometricException
	{
		// Arrange
		Scoreboard scoreboard = Scoreboard.getInstance();
		scoreboard.setFile(TEST_FILE);
		scoreboard.purgeScores();

		Polygon obstacle = new Rectangle(new Point(0, 0), new Point(5, 3));

		Polygon dynamicObstacle = new Rectangle(new Point(30, 4), new Point(31, 5));
		Point anchor = new Point(31, 4.5);
		float speed = (float)Math.PI / 2;

		new GameManagerBuilder()
			.addObstacle(obstacle)
			.addObstacle(dynamicObstacle, anchor, speed)
			.setSeed(137)
			.setMapWidth(40)
			.setMapHeight(10)
			.setSnakePos(new Point(1, 8))
			.setSnakeSize(1)
			.setTextual(true)
			.setFoodSize(1)
			.setSnakeDir(Snake.Direction.UP)
			.setFoodPos(new Point(1, 9))
			.setFoodScore(5)
			.setFoodType(GameManager.FoodType.SQUARE)
			.setFilled(true)
			.setUpdateMethod(GameEngineFlags.UpdateMethod.STEP)
			.setControlMethod(GameManager.ControlMethod.MANUAL)
			.setMapChar(' ')
			.setSnakeHeadChar('h')
			.setSnakeTailChar('t')
			.setObstacleChar('-')
			.setFoodChar('f')
			.build();

		ByteArrayOutputStream out = TestUtil.setIOstreams("step\nstep\ntest name\nstep\nstep\nstop");


		String expected =	"╔════════════════════════════════════════╗\n" +
							"║ f                                      ║\n" +
							"║ h                                      ║\n" +
							"║                                        ║\n" +
							"║                                        ║\n" +
							"║                              OO        ║\n" +
							"║                              OO        ║\n" +
							"║------                                  ║\n" +
							"║------                                  ║\n" +
							"║------                                  ║\n" +
							"║------                                  ║\n" +
							"║Dir: 90                         Score: 0║\n" +
							"╚════════════════════════════════════════╝\n" +
							"Stepping...\n" +
							"╔════════════════════════════════════════╗\n" +
							"║ h                                      ║\n" +
							"║                                        ║\n" +
							"║                                        ║\n" +
							"║                                        ║\n" +
							"║                                O       ║\n" +
							"║                               OO     f ║\n" +
							"║------                         O        ║\n" +
							"║------                                  ║\n" +
							"║------                                  ║\n" +
							"║------                                  ║\n" +
							"║Dir: 90                         Score: 5║\n" +
							"╚════════════════════════════════════════╝\n" +
							"Stepping...\n" +
							"╔════════════════════════════════════════╗\n" +
							"║                                        ║\n" +
							"║                                        ║\n" +
							"║               Game Over!               ║\n" +
							"║                                        ║\n" +
							"║                                        ║\n" +
							"║                Score: 5                ║\n" +
							"║                                        ║\n" +
							"║                                        ║\n" +
							"║           What is your name?           ║\n" +
							"║                                        ║\n" +
							"║                                        ║\n" +
							"╚════════════════════════════════════════╝\n" +
							"Score saved! :3\n" +
							"Stepping...\n" +
							"╔════════════════════════════════════════╗\n" +
							"║                                        ║\n" +
							"║               Highscores               ║\n" +
							"║                                        ║\n" +
							"║ 1. test name  5  06/05/2024            ║\n" +
							"║                                        ║\n" +
							"║                                        ║\n" +
							"║                                        ║\n" +
							"║                                        ║\n" +
							"║                                        ║\n" +
							"║                                        ║\n" +
							"║                                        ║\n" +
							"╚════════════════════════════════════════╝\n" +
							"Stepping...\n" +
							"╔════════════════════════════════════════╗\n" +
							"║ f                                      ║\n" +
							"║ h                                      ║\n" +
							"║                                        ║\n" +
							"║                                        ║\n" +
							"║                              OO        ║\n" +
							"║                              OO        ║\n" +
							"║------                                  ║\n" +
							"║------                                  ║\n" +
							"║------                                  ║\n" +
							"║------                                  ║\n" +
							"║Dir: 90                         Score: 0║\n" +
							"╚════════════════════════════════════════╝\n" +
							"Stopping...\n";
		// Act
		GameManager.getInstance().play();
		String render = out.toString();
		out.reset();

		// Assert
		assertEquals(expected, render);
	}
}
