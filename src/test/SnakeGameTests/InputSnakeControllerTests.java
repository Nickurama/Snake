package SnakeGameTests;

import SnakeGame.*;
import GameEngine.*;
import Geometry.*;
import TestUtil.*;

import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayOutputStream;

public class InputSnakeControllerTests
{
	@Test
	public void ShouldTurnSnakeLeftFromInput() throws GeometricException, SnakeGameException, GameEngineException
	{
		// Arrange
		Snake snake = new Snake(new Point(30, 30), Snake.Direction.UP, 3, true, 'T', 'H');

		InputSnakeController inputController = new InputSnakeController();
		SnakeController controller = new SnakeController(snake, inputController);

		Scene sc = new Scene();
		sc.add(snake);
		sc.add(controller);
		sc.add(inputController);

		GameEngineFlags flags = new GameEngineFlags();
		flags.setUpdateMethod(GameEngineFlags.UpdateMethod.STEP);
		GameEngine engine = GameEngine.getInstance();
		engine.init(flags, sc);

		Snake.Direction expectedDir0 = Snake.Direction.UP;
		Snake.Direction expectedDir1 = Snake.Direction.LEFT;

		// Act
		Snake.Direction dir0 = snake.direction();

		TestUtil.setIOstreams(	"left\n" +
								"step\n" +
								"stop\n");
		engine.start();
		Snake.Direction dir1 = snake.direction();
		//
		// Assert
		assertEquals(expectedDir0, dir0);
		assertEquals(expectedDir1, dir1);
	}

	@Test
	public void ShouldTurnSnakeTwiceFromInput() throws GeometricException, SnakeGameException, GameEngineException
	{
		// Arrange
		Snake snake = new Snake(new Point(30, 30), Snake.Direction.UP, 3, true, 'T', 'H');

		InputSnakeController inputController = new InputSnakeController();
		SnakeController controller = new SnakeController(snake, inputController);

		Scene sc = new Scene();
		sc.add(snake);
		sc.add(controller);
		sc.add(inputController);

		GameEngineFlags flags = new GameEngineFlags();
		flags.setUpdateMethod(GameEngineFlags.UpdateMethod.STEP);
		GameEngine engine = GameEngine.getInstance();
		engine.init(flags, sc);

		Snake.Direction expectedDir = Snake.Direction.DOWN;

		// Act
		TestUtil.setIOstreams(	"left\n" +
								"step\n" +
								"left\n" +
								"step\n" +
								"stop\n");
		engine.start();
		Snake.Direction dir = snake.direction();

		// Assert
		assertEquals(expectedDir, dir);
	}

	@Test
	public void ShouldTurnSnakeLeftThenFrontFromInput() throws GeometricException, SnakeGameException, GameEngineException
	{
		// Arrange
		Snake snake = new Snake(new Point(30, 30), Snake.Direction.UP, 3, true, 'T', 'H');

		InputSnakeController inputController = new InputSnakeController();
		SnakeController controller = new SnakeController(snake, inputController);

		Scene sc = new Scene();
		sc.add(snake);
		sc.add(controller);
		sc.add(inputController);

		GameEngineFlags flags = new GameEngineFlags();
		flags.setUpdateMethod(GameEngineFlags.UpdateMethod.STEP);
		GameEngine engine = GameEngine.getInstance();
		engine.init(flags, sc);

		Snake.Direction expectedDir = Snake.Direction.DOWN;

		// Act
		TestUtil.setIOstreams(	"left\n" +
								"step\n" +
								"left\n" +
								"step\n" +
								"step\n" +
								"stop\n");
		engine.start();
		Snake.Direction dir = snake.direction();

		// Assert
		assertEquals(expectedDir, dir);
	}

	@Test
	public void ShouldTurnSnakeLeftThenRightFromInput() throws GeometricException, SnakeGameException, GameEngineException
	{
		// Arrange
		Snake snake = new Snake(new Point(30, 30), Snake.Direction.UP, 3, true, 'T', 'H');

		InputSnakeController inputController = new InputSnakeController();
		SnakeController controller = new SnakeController(snake, inputController);

		Scene sc = new Scene();
		sc.add(snake);
		sc.add(controller);
		sc.add(inputController);

		GameEngineFlags flags = new GameEngineFlags();
		flags.setUpdateMethod(GameEngineFlags.UpdateMethod.STEP);
		GameEngine engine = GameEngine.getInstance();
		engine.init(flags, sc);

		Snake.Direction expectedDir = Snake.Direction.LEFT;

		// Act
		TestUtil.setIOstreams(	"left\n" +
								"step\n" +
								"left\n" +
								"step\n" +
								"step\n" +
								"right\n" +
								"step\n" +
								"stop\n");
		engine.start();
		Snake.Direction dir = snake.direction();

		// Assert
		assertEquals(expectedDir, dir);
	}
}