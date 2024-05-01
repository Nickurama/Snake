package SnakeGameTests;

import SnakeGame.*;
import GameEngine.*;
import Geometry.*;
import TestUtil.*;

import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayOutputStream;

public class SnakeTests
{
	@Test
	public void ShouldDieWhenCollidingWithObstacle() throws GeometricException, GameEngineException, SnakeGameException
	{
		// Arrange
		Snake snake = new Snake(new Point(2, 2), Snake.Direction.UP, 3, true, 't', 'h');

		Polygon p = new Polygon(new Point[] { new Point(2, 2), new Point(2, 5), new Point(5, 5), new Point(5, 2) });
		StaticObstacle obstacle = new StaticObstacle(p, true, 'x');

		Scene sc = new Scene();
		sc.add(snake);
		sc.add(obstacle);

		GameEngineFlags flags = new GameEngineFlags();
		flags.setUpdateMethod(GameEngineFlags.UpdateMethod.CODE);
		GameEngine engine = GameEngine.getInstance();
		engine.init(flags, sc);
		engine.start();

		// Act
		engine.step();
		
		// Arrange
		assertTrue(snake.isDead());
	}

	@Test
	public void ShouldDieWhenCollidingWithWall() throws GeometricException, GameEngineException, SnakeGameException
	{
		// Arrange
		Rectangle mapRect = new Rectangle(new Point[]
		{
			new Point(4, 4),
			new Point(4, 14),
			new Point(14, 14),
			new Point(14, 4)
		});
		GameMap map = new GameMap(mapRect, ' ');
		Snake snake = new Snake(new Point(2, 2), Snake.Direction.UP, 3, true, 't', 'h');

		Scene sc = new Scene();
		sc.add(snake);
		sc.add(map);

		GameEngineFlags flags = new GameEngineFlags();
		flags.setUpdateMethod(GameEngineFlags.UpdateMethod.CODE);
		GameEngine engine = GameEngine.getInstance();
		engine.init(flags, sc);
		engine.start();

		// Act
		engine.step();
		
		// Arrange
		assertTrue(snake.isDead());
	}

	@Test
	public void ShouldGoUp() throws GeometricException, GameEngineException, SnakeGameException
	{
		// Arrange
		Rectangle camera = new Rectangle(new Point[] { new Point(0, 0), new Point(0, 9), new Point(39, 9), new Point(39, 0) });

		Snake snake = new Snake(new Point(20.5, 4.5), Snake.Direction.UP, 2, true, 'o', 'x');

		Scene sc = new Scene();
		sc.add(snake);

		GameEngineFlags flags = new GameEngineFlags();
		flags.setUpdateMethod(GameEngineFlags.UpdateMethod.CODE);
		GameEngine engine = GameEngine.getInstance();
		engine.init(flags, sc, camera);
		engine.start();

		ByteArrayOutputStream out = TestUtil.setIOstreams("");
		String expected =	"                                        \n" +
							"                                        \n" +
							"                    xx                  \n" +
							"                    xx                  \n" +
							"                                        \n" +
							"                                        \n" +
							"                                        \n" +
							"                                        \n" +
							"                                        \n" +
							"                                        \n";

		// Act
		engine.step();
		String render = out.toString();
		out.reset();

		// Assert
		assertEquals(expected, render);
	}

	@Test
	public void ShouldGoDown() throws GeometricException, GameEngineException, SnakeGameException
	{
		// Arrange
		Rectangle camera = new Rectangle(new Point[] { new Point(0, 0), new Point(0, 9), new Point(39, 9), new Point(39, 0) });

		Snake snake = new Snake(new Point(20.5, 4.5), Snake.Direction.DOWN, 2, true, 'o', 'x');

		Scene sc = new Scene();
		sc.add(snake);

		GameEngineFlags flags = new GameEngineFlags();
		flags.setUpdateMethod(GameEngineFlags.UpdateMethod.CODE);
		GameEngine engine = GameEngine.getInstance();
		engine.init(flags, sc, camera);
		engine.start();

		ByteArrayOutputStream out = TestUtil.setIOstreams("");
		String expected =	"                                        \n" +
							"                                        \n" +
							"                                        \n" +
							"                                        \n" +
							"                                        \n" +
							"                                        \n" +
							"                    xx                  \n" +
							"                    xx                  \n" +
							"                                        \n" +
							"                                        \n";

		// Act
		engine.step();
		String render = out.toString();
		out.reset();

		// Assert
		assertEquals(expected, render);
	}

	@Test
	public void ShouldGoLeft() throws GeometricException, GameEngineException, SnakeGameException
	{
		// Arrange
		Rectangle camera = new Rectangle(new Point[] { new Point(0, 0), new Point(0, 9), new Point(39, 9), new Point(39, 0) });

		Snake snake = new Snake(new Point(20.5, 4.5), Snake.Direction.LEFT, 2, true, 'o', 'x');

		Scene sc = new Scene();
		sc.add(snake);

		GameEngineFlags flags = new GameEngineFlags();
		flags.setUpdateMethod(GameEngineFlags.UpdateMethod.CODE);
		GameEngine engine = GameEngine.getInstance();
		engine.init(flags, sc, camera);
		engine.start();

		ByteArrayOutputStream out = TestUtil.setIOstreams("");
		String expected =	"                                        \n" +
							"                                        \n" +
							"                                        \n" +
							"                                        \n" +
							"                  xx                    \n" +
							"                  xx                    \n" +
							"                                        \n" +
							"                                        \n" +
							"                                        \n" +
							"                                        \n";

		// Act
		engine.step();
		String render = out.toString();
		out.reset();

		// Assert
		assertEquals(expected, render);
	}

	@Test
	public void ShouldGoRight() throws GeometricException, GameEngineException, SnakeGameException
	{
		// Arrange
		Rectangle camera = new Rectangle(new Point[] { new Point(0, 0), new Point(0, 9), new Point(39, 9), new Point(39, 0) });

		Snake snake = new Snake(new Point(20.5, 4.5), Snake.Direction.RIGHT, 2, true, 'o', 'x');

		Scene sc = new Scene();
		sc.add(snake);

		GameEngineFlags flags = new GameEngineFlags();
		flags.setUpdateMethod(GameEngineFlags.UpdateMethod.CODE);
		GameEngine engine = GameEngine.getInstance();
		engine.init(flags, sc, camera);
		engine.start();

		ByteArrayOutputStream out = TestUtil.setIOstreams("");
		String expected =	"                                        \n" +
							"                                        \n" +
							"                                        \n" +
							"                                        \n" +
							"                      xx                \n" +
							"                      xx                \n" +
							"                                        \n" +
							"                                        \n" +
							"                                        \n" +
							"                                        \n";

		// Act
		engine.step();
		String render = out.toString();
		out.reset();

		// Assert
		assertEquals(expected, render);
	}

	@Test
	public void ShouldTurnLeftWhenGoingLeft() throws GeometricException, GameEngineException, SnakeGameException
	{
		// Arrange
		Snake snake = new Snake(new Point(2, 2), Snake.Direction.LEFT, 3, true, 't', 'h');

		// Act
		snake.turnLeft();
		
		// Arrange
		assertEquals(Snake.Direction.DOWN, snake.direction());
	}

	@Test
	public void ShouldTurnRightWhenGoingLeft() throws GeometricException, GameEngineException, SnakeGameException
	{
		// Arrange
		Snake snake = new Snake(new Point(2, 2), Snake.Direction.LEFT, 3, true, 't', 'h');

		// Act
		snake.turnRight();
		
		// Arrange
		assertEquals(Snake.Direction.UP, snake.direction());
	}

	@Test
	public void ShouldTurnLeftWhenGoingUp() throws GeometricException, GameEngineException, SnakeGameException
	{
		// Arrange
		Snake snake = new Snake(new Point(2, 2), Snake.Direction.UP, 3, true, 't', 'h');

		// Act
		snake.turnLeft();
		
		// Arrange
		assertEquals(Snake.Direction.LEFT, snake.direction());
	}

	@Test
	public void ShouldTurnRightWhenGoingUp() throws GeometricException, GameEngineException, SnakeGameException
	{
		// Arrange
		Snake snake = new Snake(new Point(2, 2), Snake.Direction.UP, 3, true, 't', 'h');

		// Act
		snake.turnRight();
		
		// Arrange
		assertEquals(Snake.Direction.RIGHT, snake.direction());
	}

	@Test
	public void ShouldTurnLeftWhenGoingRight() throws GeometricException, GameEngineException, SnakeGameException
	{
		// Arrange
		Snake snake = new Snake(new Point(2, 2), Snake.Direction.RIGHT, 3, true, 't', 'h');

		// Act
		snake.turnLeft();
		
		// Arrange
		assertEquals(Snake.Direction.UP, snake.direction());
	}

	@Test
	public void ShouldTurnRightWhenGoingRight() throws GeometricException, GameEngineException, SnakeGameException
	{
		// Arrange
		Snake snake = new Snake(new Point(2, 2), Snake.Direction.RIGHT, 3, true, 't', 'h');

		// Act
		snake.turnRight();
		
		// Arrange
		assertEquals(Snake.Direction.DOWN, snake.direction());
	}

	@Test
	public void ShouldTurnLeftWhenGoingDown() throws GeometricException, GameEngineException, SnakeGameException
	{
		// Arrange
		Snake snake = new Snake(new Point(2, 2), Snake.Direction.DOWN, 3, true, 't', 'h');

		// Act
		snake.turnLeft();
		
		// Arrange
		assertEquals(Snake.Direction.RIGHT, snake.direction());
	}

	@Test
	public void ShouldTurnRightWhenGoingDown() throws GeometricException, GameEngineException, SnakeGameException
	{
		// Arrange
		Snake snake = new Snake(new Point(2, 2), Snake.Direction.DOWN, 3, true, 't', 'h');

		// Act
		snake.turnRight();
		
		// Arrange
		assertEquals(Snake.Direction.LEFT, snake.direction());
	}

	@Test
	public void ShouldGrowWhenContainingFood() throws GeometricException, GameEngineException, SnakeGameException
	{
		throw new Error();
		// // Arrange
		// Snake snake = new Snake(3, new Point(1, 1), Snake.Direction.UP);
		// FoodSquare food = new FoodSquare(map, 3, new Point(1, 3));
		//
		// Scene sc = new Scene();
		// sc.add(snake);
		// sc.add(food);
		//
		// GameEngineFlags flags = new GameEngineFlags();
		// flags.setUpdateMethod(GameEngineFlags.UpdateMethod.CODE);
		// GameEngine engine = GameEngine.getInstance();
		// engine.init(flags, sc);
		// engine.start();
		//
		// // Act
		// engine.step();
		// 
		// // Arrange
		// assertEquals(2, snake.length());
	}

	@Test
	public void ShouldNotGrowWhenCollidingWithFoodButNotContaining() throws GeometricException, GameEngineException, SnakeGameException
	{
		throw new Error();
		// // Arrange
		// Snake snake = new Snake(3, new Point(1, 1), Snake.Direction.UP);
		// FoodSquare food = new FoodSquare(map, 3, new Point(2, 2));
		//
		// Scene sc = new Scene();
		// sc.add(snake);
		// sc.add(food);
		//
		// GameEngineFlags flags = new GameEngineFlags();
		// flags.setUpdateMethod(GameEngineFlags.UpdateMethod.CODE);
		// GameEngine engine = GameEngine.getInstance();
		// engine.init(flags, sc);
		// engine.start();
		//
		// // Act
		// engine.step();
		// 
		// // Arrange
		// assertEquals(1, snake.length());
	}

	@Test
	public void ShouldGrowVisiblyWhenGrowing() throws GeometricException, GameEngineException, SnakeGameException
	{
		throw new Error();
		// // Arrange
		// Rectangle camera = new Rectangle(new Point[]
		// {
		// 	new Point(0, 0),
		// 	new Point(0, 4),
		// 	new Point(4, 4),
		// 	new Point(4, 0)
		// });
		// Snake snake = new Snake(3, new Point(1, 1), Snake.Direction.UP);
		// FoodSquare food = new FoodSquare(map, 3, new Point(2, 2));
		//
		// Scene sc = new Scene();
		// sc.add(snake);
		//
		// GameEngineFlags flags = new GameEngineFlags();
		// flags.setUpdateMethod(GameEngineFlags.UpdateMethod.CODE);
		// flags.setRasterized(true);
		// GameEngine engine = GameEngine.getInstance();
		// engine.init(flags, sc, camera);
		// engine.start();
		//
		// ByteArrayOutputStream out = TestUtil.setIOstreams("");
		// String expected =	"xxx--" +
		// 					"xxx--" +
		// 					"xxx--" +
		// 					"xxx--" +
		// 					"xxx--";
		//
		// // Act
		// engine.step();
		// String render = out.toString();
		// out.reset();
		//
		// // Assert
		// assertEquals(expected, render);
	}

	
	@Test
	public void ShouldDragBodyAlong()
	{
		throw new Error();
	}

	@Test
	public void ShouldDragBodyAlongWhenTurning()
	{
		throw new Error();
	}

	@Test
	public void ShouldDieWhenCollidingWithOwnBody()
	{
		throw new Error();
	}
}
