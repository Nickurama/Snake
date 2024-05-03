package SnakeGameTests;

import SnakeGame.*;
import GameEngine.*;
import Geometry.*;
import TestUtil.*;

import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayOutputStream;

public class SnakeControllerTests
{
	@Test
	public void ShouldTurnSnake() throws GeometricException, SnakeGameException, GameEngineException
	{
		// Arrange
		Snake snake = new Snake(new Point(30, 30), Snake.Direction.UP, 3, true, 'T', 'H');

		MockSnakeController mockController = new MockSnakeController();
		SnakeController controller = new SnakeController(snake, mockController);

		Scene sc = new Scene();
		sc.add(snake);
		sc.add(controller);

		GameEngineFlags flags = new GameEngineFlags();
		flags.setUpdateMethod(GameEngineFlags.UpdateMethod.CODE);
		GameEngine engine = GameEngine.getInstance();
		engine.init(flags, sc);
		engine.start();

		Snake.Direction expectedDir0 = Snake.Direction.UP;
		Snake.Direction expectedDir1 = Snake.Direction.LEFT;
		Snake.Direction expectedDir2 = Snake.Direction.DOWN;
		Snake.Direction expectedDir3 = Snake.Direction.DOWN;
		Snake.Direction expectedDir4 = Snake.Direction.LEFT;

		// Act
		Snake.Direction dir0 = snake.direction();

		mockController.setNextTurn(ISnakeController.TurnDirection.LEFT);
		engine.step();
		Snake.Direction dir1 = snake.direction();

		engine.step();
		Snake.Direction dir2 = snake.direction();

		mockController.setNextTurn(ISnakeController.TurnDirection.NONE);
		engine.step();
		Snake.Direction dir3 = snake.direction();

		mockController.setNextTurn(ISnakeController.TurnDirection.RIGHT);
		engine.step();
		Snake.Direction dir4 = snake.direction();

		// Assert
		assertEquals(expectedDir0, dir0);
		assertEquals(expectedDir1, dir1);
		assertEquals(expectedDir2, dir2);
		assertEquals(expectedDir3, dir3);
		assertEquals(expectedDir4, dir4);
	}
}
