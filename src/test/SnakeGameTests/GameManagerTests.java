package SnakeGameTests;

import SnakeGame.*;
import GameEngine.*;
import Geometry.*;
import TestUtil.*;

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
		if (GameEngine.getInstance().isRunning())
			GameEngine.getInstance().stop();

		GameManager gameManager = GameManager.getInstance();
		gameManager.init(39, 9, '.', new Point(13, 4), Snake.Direction.RIGHT, 3, true, 'T', 'H'
			, true, GameEngineFlags.UpdateMethod.STEP, GameManager.ControlMethod.MANUAL, SEED);

		String expected =	"╔═══════════════════════════════════════╗\n" +
							"║.......................................║\n" +
							"║.......................................║\n" +
							"║.......................................║\n" +
							"║............HHH........................║\n" +
							"║............HHH........................║\n" +
							"║............HHH........................║\n" +
							"║.......................................║\n" +
							"║.......................................║\n" +
							"║.......................................║\n" +
							"║Dir: 0                         Score: 1║\n" +
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
		if (GameEngine.getInstance().isRunning())
			GameEngine.getInstance().stop();

		GameManager gameManager = GameManager.getInstance();
		gameManager.init(39, 9, '.', new Point(13, 4), Snake.Direction.RIGHT, 3, true, 'T', 'H', true, GameEngineFlags.UpdateMethod.STEP, GameManager.ControlMethod.MANUAL, SEED);

		String expected =	"╔═══════════════════════════════════════╗\n" +
							"║.......................................║\n" +
							"║.......................................║\n" +
							"║.......................................║\n" +
							"║............HHH........................║\n" +
							"║............HHH........................║\n" +
							"║............HHH........................║\n" +
							"║.......................................║\n" +
							"║.......................................║\n" +
							"║.......................................║\n" +
							"║Dir: 0                         Score: 1║\n" +
							"╚═══════════════════════════════════════╝\n" +
							"Stepping...\n" +
							"╔═══════════════════════════════════════╗\n" +
							"║.......................................║\n" +
							"║.......................................║\n" +
							"║.......................................║\n" +
							"║...............HHH.....................║\n" +
							"║...............HHH.....................║\n" +
							"║...............HHH.....................║\n" +
							"║.......................................║\n" +
							"║.......................................║\n" +
							"║.......................................║\n" +
							"║Dir: 0                         Score: 1║\n" +
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
		if (GameEngine.getInstance().isRunning())
			GameEngine.getInstance().stop();

		GameManager gameManager = GameManager.getInstance();
		gameManager.init(39, 9, '.', new Point(13, 4), Snake.Direction.RIGHT, 3, true, 'T', 'H', true, GameEngineFlags.UpdateMethod.STEP, GameManager.ControlMethod.MANUAL, SEED);

		String expected =	"╔═══════════════════════════════════════╗\n" +
							"║.......................................║\n" +
							"║.......................................║\n" +
							"║.......................................║\n" +
							"║............HHH........................║\n" +
							"║............HHH........................║\n" +
							"║............HHH........................║\n" +
							"║.......................................║\n" +
							"║.......................................║\n" +
							"║.......................................║\n" +
							"║Dir: 0                         Score: 1║\n" +
							"╚═══════════════════════════════════════╝\n" +
							"Snake will turn right.\n" +
							"Stepping...\n" +
							"╔═══════════════════════════════════════╗\n" +
							"║.......................................║\n" +
							"║.......................................║\n" +
							"║.......................................║\n" +
							"║.......................................║\n" +
							"║.......................................║\n" +
							"║.......................................║\n" +
							"║............HHH........................║\n" +
							"║............HHH........................║\n" +
							"║............HHH........................║\n" +
							"║Dir: 270                       Score: 1║\n" +
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
		if (GameEngine.getInstance().isRunning())
			GameEngine.getInstance().stop();

		GameManager gameManager = GameManager.getInstance();

		// Act
		// Assert
		assertThrows(SnakeGameException.class, () ->
			gameManager.init(39, 10, '.', new Point(13, 4), Snake.Direction.RIGHT, 3, true, 'T', 'H', true, GameEngineFlags.UpdateMethod.STEP, GameManager.ControlMethod.MANUAL, SEED));
		assertThrows(SnakeGameException.class, () ->
			gameManager.init(38, 9, '.', new Point(13, 4), Snake.Direction.RIGHT, 3, true, 'T', 'H', true, GameEngineFlags.UpdateMethod.STEP, GameManager.ControlMethod.MANUAL, SEED));
	}

	@Test
	public void ShouldThrowWhenInvalidSnakePosition() throws SnakeGameException, GeometricException
	{
		// Arrange
		if (GameEngine.getInstance().isRunning())
			GameEngine.getInstance().stop();

		GameManager gameManager = GameManager.getInstance();

		// Act
		// Assert
		assertThrows(SnakeGameException.class, () ->
			gameManager.init(38, 8, '.', new Point(13.5, 4.5), Snake.Direction.RIGHT, 2, true, 'T', 'H', true, GameEngineFlags.UpdateMethod.STEP, GameManager.ControlMethod.MANUAL, SEED));
		assertThrows(SnakeGameException.class, () ->
			gameManager.init(39, 9, '.', new Point(2, 2), Snake.Direction.RIGHT, 3, true, 'T', 'H', true, GameEngineFlags.UpdateMethod.STEP, GameManager.ControlMethod.MANUAL, SEED));
	}

	@Test
	public void ShouldThrowWhenInvalidMapDimensions()
	{
		// Arrange
		if (GameEngine.getInstance().isRunning())
			GameEngine.getInstance().stop();

		GameManager gameManager = GameManager.getInstance();

		// Act
		// Assert
		assertThrows(SnakeGameException.class, () ->
			gameManager.init(3, 1, '.', new Point(13.5, 4.5), Snake.Direction.RIGHT, 2, true, 'T', 'H', true, GameEngineFlags.UpdateMethod.STEP, GameManager.ControlMethod.MANUAL, SEED));
		assertThrows(SnakeGameException.class, () ->
			gameManager.init(1, 3, '.', new Point(13.5, 4.5), Snake.Direction.RIGHT, 2, true, 'T', 'H', true, GameEngineFlags.UpdateMethod.STEP, GameManager.ControlMethod.MANUAL, SEED));
		assertThrows(SnakeGameException.class, () ->
			gameManager.init(-1, -5, '.', new Point(13.5, 4.5), Snake.Direction.RIGHT, 2, true, 'T', 'H', true, GameEngineFlags.UpdateMethod.STEP, GameManager.ControlMethod.MANUAL, SEED));
	}

	@Test
	public void ShouldGenerateRandomSnakeDirection() throws GeometricException, SnakeGameException
	{
		// Arrange
		if (GameEngine.getInstance().isRunning())
			GameEngine.getInstance().stop();

		GameManager gameManager = GameManager.getInstance();
		gameManager.init(39, 9, '.', new Point(13, 4), null, 3, true, 'T', 'H'
			, true, GameEngineFlags.UpdateMethod.STEP, GameManager.ControlMethod.MANUAL, SEED);

		String expected =	"╔═══════════════════════════════════════╗\n" +
							"║.......................................║\n" +
							"║.......................................║\n" +
							"║.......................................║\n" +
							"║............HHH........................║\n" +
							"║............HHH........................║\n" +
							"║............HHH........................║\n" +
							"║.......................................║\n" +
							"║.......................................║\n" +
							"║.......................................║\n" +
							"║Dir: 270                       Score: 1║\n" +
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
		if (GameEngine.getInstance().isRunning())
			GameEngine.getInstance().stop();

		GameManager gameManager = GameManager.getInstance();
		gameManager.init(39, 9, 3, true, true, GameEngineFlags.UpdateMethod.STEP, GameManager.ControlMethod.MANUAL, SEED);

		String expected =	"╔═══════════════════════════════════════╗\n" +
							"║.......................................║\n" +
							"║.......................................║\n" +
							"║.......................................║\n" +
							"║.......................................║\n" +
							"║.......................................║\n" +
							"║.......................................║\n" +
							"║..................HHH..................║\n" +
							"║..................HHH..................║\n" +
							"║..................HHH..................║\n" +
							"║Dir: 270                       Score: 1║\n" +
							"╚═══════════════════════════════════════╝\n" +
							"Stopping...\n";

		// Act
		ByteArrayOutputStream out = TestUtil.setIOstreams("stop");
		gameManager.play();
		String render = out.toString();

		// Assert
		assertEquals(expected, render);
	}
}
