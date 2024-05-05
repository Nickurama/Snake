package SnakeGameTests;

import SnakeGame.*;
import GameEngine.*;
import Geometry.*;
import TestUtil.*;
import GameEngine.GameEngineFlags.*;
import SnakeGame.Snake.*;
import SnakeGame.GameManager.*;

import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayOutputStream;

public class GameManagerTests
{
	private static final long SEED = 137;

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
							"║.......................................║\n" +
							"║.......................................║\n" +
							"║.......................................║\n" +
							"║........................FFF............║\n" +
							"║........................FFF............║\n" +
							"║........................FFF............║\n" +
							"║..................HHH..................║\n" +
							"║..................HHH..................║\n" +
							"║..................HHH..................║\n" +
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
							"║........................................║\n" +
							"║........................................║\n" +
							"║........................................║\n" +
							"║............HHHH........................║\n" +
							"║............HHHH........................║\n" +
							"║............HHHH........................║\n" +
							"║............HHHH........................║\n" +
							"║.................F......................║\n" +
							"║................FFF.....................║\n" +
							"║.................F......................║\n" +
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
}
