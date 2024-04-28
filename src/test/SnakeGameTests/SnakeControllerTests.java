// package SnakeGameTests;
//
// import SnakeGame.*;
// import GameEngine.*;
// import Geometry.*;
// import TestUtil.*;
//
// import org.junit.jupiter.api.Test;
// import static org.junit.Assert.assertThrows;
// import static org.junit.jupiter.api.Assertions.*;
// import java.io.ByteArrayOutputStream;
//
// public class SnakeControllerTests
// {
// 	@Test
// 	public void ShouldMoveSnakeFromInput()
// 	{
// 		// Arrange
// 		Snake snake = new Snake(3, new Point(1, 3), Snake.Direction.UP);
// 		SnakeController controller = new SnakeController(snake, false);
//
// 		Scene sc = new Scene();
// 		sc.add(snake);
// 		sc.add(controller);
//
// 		GameEngineFlags flags = new GameEngineFlags();
// 		flags.setUpdateMethod(GameEngineFlags.UpdateMethod.STEP);
// 		GameEngine engine = GameEngine.getInstance();
// 		engine.init(flags, sc);
// 		engine.start();
//
// 		// Act
// 		TestUtil.setIOstreams("right\r\nstop\r\n");
//
// 		// Arrange
// 		assertEquals(Snake.Direction.RIGHT, snake.direction());
// 	}
//
// 	@Test
// 	public void ShouldMoveSnakeFromAI()
// 	{
// 		// Arrange
// 		Snake snake = new Snake(3, new Point(1, 3), Snake.Direction.UP);
// 		SnakeController controller = new SnakeController(snake, false);
//
// 		Scene sc = new Scene();
// 		sc.add(snake);
// 		sc.add(controller);
//
// 		GameEngineFlags flags = new GameEngineFlags();
// 		flags.setUpdateMethod(GameEngineFlags.UpdateMethod.CODE);
// 		GameEngine engine = GameEngine.getInstance();
// 		engine.init(flags, sc);
// 		engine.start();
//
// 		// Act
// 		engine.step();
//
// 		// Arrange
// 		assertEquals(Snake.Direction.RIGHT, snake.direction());
// 	}
// }
