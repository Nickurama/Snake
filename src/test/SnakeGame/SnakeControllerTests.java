package SnakeGame;

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
		Snake snake = new Snake(new Point(30, 30), Direction.UP, 3, true, 'T', 'H');

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

		Direction expectedDir0 = Direction.UP;
		Direction expectedDir1 = Direction.LEFT;
		Direction expectedDir2 = Direction.DOWN;
		Direction expectedDir3 = Direction.DOWN;
		Direction expectedDir4 = Direction.LEFT;

		// Act
		Direction dir0 = snake.direction();

		mockController.setNextTurn(ISnakeController.TurnDirection.LEFT);
		engine.step();
		Direction dir1 = snake.direction();

		engine.step();
		Direction dir2 = snake.direction();

		mockController.setNextTurn(ISnakeController.TurnDirection.NONE);
		engine.step();
		Direction dir3 = snake.direction();

		mockController.setNextTurn(ISnakeController.TurnDirection.RIGHT);
		engine.step();
		Direction dir4 = snake.direction();

		// Assert
		assertEquals(expectedDir0, dir0);
		assertEquals(expectedDir1, dir1);
		assertEquals(expectedDir2, dir2);
		assertEquals(expectedDir3, dir3);
		assertEquals(expectedDir4, dir4);
	}
}
