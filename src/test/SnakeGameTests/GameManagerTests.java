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
	@Test
	public void ShouldPlayInSteppedManualMode() throws SnakeGameException, GeometricException
	{
		// Arrange
		GameManager gameManager = GameManager.getInstance();
		gameManager.init(39, 9, '.', new Point(13, 4), Snake.Direction.RIGHT, 3, true, 'T', 'H', true, GameEngineFlags.UpdateMethod.STEP, GameManager.ControlMethod.MANUAL);

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
		GameManager gameManager = GameManager.getInstance();
		gameManager.init(39, 9, '.', new Point(13, 4), Snake.Direction.RIGHT, 3, true, 'T', 'H', true, GameEngineFlags.UpdateMethod.STEP, GameManager.ControlMethod.MANUAL);

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
		GameManager gameManager = GameManager.getInstance();
		gameManager.init(39, 9, '.', new Point(13, 4), Snake.Direction.RIGHT, 3, true, 'T', 'H', true, GameEngineFlags.UpdateMethod.STEP, GameManager.ControlMethod.MANUAL);

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
	public void ShouldThrowWhenInvalidMapSnakeRatio()
	{
		throw new Error();
	}

	@Test
	public void ShouldThrowWhenInvalidSnakePosition()
	{
		throw new Error();
	}

	@Test
	public void ShouldThrowWhenInvalidMapDimensions()
	{
		throw new Error();
	}
}
