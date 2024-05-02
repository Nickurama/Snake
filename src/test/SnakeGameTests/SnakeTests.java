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
		snake.awake();

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
		snake.awake();

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
		snake.awake();

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
		snake.awake();

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
		// Arrange
		Snake snake = new Snake(new Point(5, 5), Snake.Direction.UP, 5, true, 'x', 'x');
		FoodSquare food0 = new FoodSquare(new Point(5, 5), 3, true, 'f', 0);
		FoodSquare food1 = new FoodSquare(new Point(4.5, 4.5), 2, true, 'f', 0);
		FoodCircle food2 = new FoodCircle(new Point(5, 5), 1, true, 'f', 0);

		Scene sc = new Scene();
		sc.add(snake);
		sc.add(food0);
		sc.add(food1);
		sc.add(food2);

		GameEngineFlags flags = new GameEngineFlags();
		flags.setUpdateMethod(GameEngineFlags.UpdateMethod.CODE);
		GameEngine engine = GameEngine.getInstance();
		engine.init(flags, sc);
		engine.start();

		// Act
		engine.step();

		// Arrange
		assertEquals(4, snake.length());
	}

	@Test
	public void ShouldNotGrowWhenCollidingWithFoodButNotContaining() throws GeometricException, GameEngineException, SnakeGameException
	{
		// Arrange
		Snake snake = new Snake(new Point(5, 5), Snake.Direction.UP, 5, true, 'x', 'x');
		FoodSquare food = new FoodSquare(new Point(3, 3), 3, true, 'f', 0);

		Scene sc = new Scene();
		sc.add(snake);
		sc.add(food);

		GameEngineFlags flags = new GameEngineFlags();
		flags.setUpdateMethod(GameEngineFlags.UpdateMethod.CODE);
		GameEngine engine = GameEngine.getInstance();
		engine.init(flags, sc);
		engine.start();

		// Act
		engine.step();

		// Arrange
		assertEquals(1, snake.length());
	}

	@Test
	public void ShouldGrowVisiblyWhenGrowing() throws GeometricException, GameEngineException, SnakeGameException
	{
		// Arrange
		Rectangle camera = new Rectangle(new Point[] { new Point(0, 0), new Point(0, 9), new Point(39, 9), new Point(39, 0) });

		Snake snake = new Snake(new Point(20.5, 4.5), Snake.Direction.RIGHT, 2, true, 'o', 'x');
		snake.awake();

		FoodSquare food = new FoodSquare(new Point(22.5, 4.5), 2, true, 'f', 0);

		Scene sc = new Scene();
		sc.add(snake);
		sc.add(food);

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
							"                      ooxx              \n" +
							"                      ooxx              \n" +
							"                                        \n" +
							"                                        \n" +
							"                                        \n" +
							"                                        \n";

		// Act
		engine.step(); // snake walks into food
		out.reset();
		engine.step(); // snake walks over food, extending it's body
		String render = out.toString();
		out.reset();

		// Assert
		assertEquals(2, snake.length());
		assertEquals(expected, render);
	}

	@Test
	public void ShouldDragBodyAlong() throws GeometricException, GameEngineException, SnakeGameException
	{
		// Arrange
		Rectangle camera = new Rectangle(new Point[] { new Point(0, 0), new Point(0, 9), new Point(39, 9), new Point(39, 0) });

		Snake snake = new Snake(new Point(20.5, 4.5), Snake.Direction.RIGHT, 2, true, 'o', 'x');

		FoodSquare food0 = new FoodSquare(new Point(22.5, 4.5), 2, true, 'f', 0);
		FoodSquare food1 = new FoodSquare(new Point(24.5, 4.5), 2, true, 'f', 0);

		Scene sc = new Scene();
		sc.add(snake);
		sc.add(food0);
		sc.add(food1);

		GameEngineFlags flags = new GameEngineFlags();
		flags.setUpdateMethod(GameEngineFlags.UpdateMethod.CODE);
		GameEngine engine = GameEngine.getInstance();
		engine.init(flags, sc, camera);
		engine.start();

		ByteArrayOutputStream out = TestUtil.setIOstreams("");
		String expected0 =	"                                        \n" +
							"                                        \n" +
							"                                        \n" +
							"                                        \n" +
							"                    xxffff              \n" +
							"                    xxffff              \n" +
							"                                        \n" +
							"                                        \n" +
							"                                        \n" +
							"                                        \n";

		String expected1 =	"                                        \n" +
							"                                        \n" +
							"                                        \n" +
							"                                        \n" +
							"                      xxff              \n" +
							"                      xxff              \n" +
							"                                        \n" +
							"                                        \n" +
							"                                        \n" +
							"                                        \n";

		String expected2 =	"                                        \n" +
							"                                        \n" +
							"                                        \n" +
							"                                        \n" +
							"                      ooxx              \n" +
							"                      ooxx              \n" +
							"                                        \n" +
							"                                        \n" +
							"                                        \n" +
							"                                        \n";

		String expected3 =	"                                        \n" +
							"                                        \n" +
							"                                        \n" +
							"                                        \n" +
							"                      ooooxx            \n" +
							"                      ooooxx            \n" +
							"                                        \n" +
							"                                        \n" +
							"                                        \n" +
							"                                        \n";

		String expected4 =	"                                        \n" +
							"                                        \n" +
							"                                        \n" +
							"                                        \n" +
							"                        ooooxx          \n" +
							"                        ooooxx          \n" +
							"                                        \n" +
							"                                        \n" +
							"                                        \n" +
							"                                        \n";

		// Act
		engine.step(); // snake is at first position
		String render0 = out.toString();
		out.reset();
		snake.awake(); // snake is awoken
		engine.step(); // snake walks into first food
		String render1 = out.toString();
		out.reset();
		engine.step(); // snake walks over first food, extending it's body, snake walks into second food
		String render2 = out.toString();
		out.reset();
		engine.step(); // snake walks over second food, extending it's body
		String render3 = out.toString();
		out.reset();
		engine.step(); // snake walks, pulling body along
		String render4 = out.toString();
		out.reset();

		// Assert
		assertEquals(3, snake.length());
		assertEquals(expected0, render0);
		assertEquals(expected1, render1);
		assertEquals(expected2, render2);
		assertEquals(expected3, render3);
		assertEquals(expected4, render4);
	}

	@Test
	public void ShouldDragBodyAlongWhenTurningUp() throws GeometricException, GameEngineException, SnakeGameException
	{
		// Arrange
		Rectangle camera = new Rectangle(new Point[] { new Point(0, 0), new Point(0, 9), new Point(39, 9), new Point(39, 0) });

		Snake snake = new Snake(new Point(20.5, 4.5), Snake.Direction.RIGHT, 2, true, 'o', 'x');

		Scene sc = new Scene();
		sc.add(snake);
		sc.add(new FoodSquare(new Point(20.5, 4.5), 2, true, 'f', 0));
		sc.add(new FoodSquare(new Point(20.5, 4.5), 2, true, 'f', 0));
		sc.add(new FoodSquare(new Point(20.5, 4.5), 2, true, 'f', 0));

		GameEngineFlags flags = new GameEngineFlags();
		flags.setUpdateMethod(GameEngineFlags.UpdateMethod.CODE);
		GameEngine engine = GameEngine.getInstance();
		engine.init(flags, sc, camera);
		engine.start();

		ByteArrayOutputStream out = TestUtil.setIOstreams("");
		snake.awake();
		engine.step();
		engine.step();
		engine.step();

		String expected =	"                                        \n" +
							"                                        \n" +
							"                          xx            \n" +
							"                          xx            \n" +
							"                      oooooo            \n" +
							"                      oooooo            \n" +
							"                                        \n" +
							"                                        \n" +
							"                                        \n" +
							"                                        \n";

		// Act
		out.reset();
		snake.turnLeft();
		engine.step();
		String render = out.toString();
		out.reset();

		// Assert
		assertEquals(expected, render);
	}

	@Test
	public void ShouldDragBodyAlongWhenTurningDown() throws GeometricException, GameEngineException, SnakeGameException
	{
		// Arrange
		Rectangle camera = new Rectangle(new Point[] { new Point(0, 0), new Point(0, 9), new Point(39, 9), new Point(39, 0) });

		Snake snake = new Snake(new Point(20.5, 4.5), Snake.Direction.RIGHT, 2, true, 'o', 'x');

		Scene sc = new Scene();
		sc.add(snake);
		sc.add(new FoodSquare(new Point(20.5, 4.5), 2, true, 'f', 0));
		sc.add(new FoodSquare(new Point(20.5, 4.5), 2, true, 'f', 0));
		sc.add(new FoodSquare(new Point(20.5, 4.5), 2, true, 'f', 0));

		GameEngineFlags flags = new GameEngineFlags();
		flags.setUpdateMethod(GameEngineFlags.UpdateMethod.CODE);
		GameEngine engine = GameEngine.getInstance();
		engine.init(flags, sc, camera);
		engine.start();

		ByteArrayOutputStream out = TestUtil.setIOstreams("");
		snake.awake();
		engine.step();
		engine.step();
		engine.step();

		String expected =	"                                        \n" +
							"                                        \n" +
							"                                        \n" +
							"                                        \n" +
							"                      oooooo            \n" +
							"                      oooooo            \n" +
							"                          xx            \n" +
							"                          xx            \n" +
							"                                        \n" +
							"                                        \n";

		// Act
		out.reset();
		snake.turnRight();
		engine.step();
		String render = out.toString();
		out.reset();

		// Assert
		assertEquals(expected, render);
	}

	@Test
	public void ShouldDragBodyAlongWhenTurningLeft() throws GeometricException, GameEngineException, SnakeGameException
	{
		// Arrange
		Rectangle camera = new Rectangle(new Point[] { new Point(1, 1), new Point(1, 10), new Point(40, 10), new Point(40, 1) });

		Snake snake = new Snake(new Point(21.5, 7.5), Snake.Direction.DOWN, 2, true, 'o', 'x');

		Scene sc = new Scene();
		sc.add(snake);
		sc.add(new FoodSquare(new Point(21.5, 7.5), 2, true, 'f', 0));
		sc.add(new FoodSquare(new Point(21.5, 7.5), 2, true, 'f', 0));
		sc.add(new FoodSquare(new Point(21.5, 7.5), 2, true, 'f', 0));

		GameEngineFlags flags = new GameEngineFlags();
		flags.setUpdateMethod(GameEngineFlags.UpdateMethod.CODE);
		GameEngine engine = GameEngine.getInstance();
		engine.init(flags, sc, camera);
		engine.start();

		ByteArrayOutputStream out = TestUtil.setIOstreams("");
		snake.awake();
		engine.step();
		engine.step();
		engine.step();

		String expected =	"                                        \n" +
							"                                        \n" +
							"                                        \n" +
							"                                        \n" +
							"                    oo                  \n" +
							"                    oo                  \n" +
							"                    oo                  \n" +
							"                    oo                  \n" +
							"                  xxoo                  \n" +
							"                  xxoo                  \n";

		// Act
		out.reset();
		snake.turnRight();
		engine.step();
		String render = out.toString();
		out.reset();

		// Assert
		assertEquals(expected, render);
	}

	@Test
	public void ShouldDragBodyAlongWhenTurningRight() throws GeometricException, GameEngineException, SnakeGameException
	{
		// Arrange
		Rectangle camera = new Rectangle(new Point[] { new Point(1, 1), new Point(1, 10), new Point(40, 10), new Point(40, 1) });

		Snake snake = new Snake(new Point(21.5, 7.5), Snake.Direction.DOWN, 2, true, 'o', 'x');

		Scene sc = new Scene();
		sc.add(snake);
		sc.add(new FoodSquare(new Point(21.5, 7.5), 2, true, 'f', 0));
		sc.add(new FoodSquare(new Point(21.5, 7.5), 2, true, 'f', 0));
		sc.add(new FoodSquare(new Point(21.5, 7.5), 2, true, 'f', 0));

		GameEngineFlags flags = new GameEngineFlags();
		flags.setUpdateMethod(GameEngineFlags.UpdateMethod.CODE);
		GameEngine engine = GameEngine.getInstance();
		engine.init(flags, sc, camera);
		engine.start();

		ByteArrayOutputStream out = TestUtil.setIOstreams("");
		snake.awake();
		engine.step();
		engine.step();
		engine.step();

		String expected =	"                                        \n" +
							"                                        \n" +
							"                                        \n" +
							"                                        \n" +
							"                    oo                  \n" +
							"                    oo                  \n" +
							"                    oo                  \n" +
							"                    oo                  \n" +
							"                    ooxx                \n" +
							"                    ooxx                \n";

		// Act
		out.reset();
		snake.turnLeft();
		engine.step();
		String render = out.toString();
		out.reset();

		// Assert
		assertEquals(expected, render);
	}

	@Test
	public void ShouldDieWhenCollidingWithOwnBody() throws GeometricException, GameEngineException, SnakeGameException
	{
		// Arrange
		Snake snake = new Snake(new Point(21.5, 7.5), Snake.Direction.DOWN, 2, true, 'o', 'x');

		Scene sc = new Scene();
		sc.add(snake);
		sc.add(new FoodSquare(new Point(21.5, 7.5), 2, true, 'f', 0));
		sc.add(new FoodSquare(new Point(21.5, 7.5), 2, true, 'f', 0));
		sc.add(new FoodSquare(new Point(21.5, 7.5), 2, true, 'f', 0));
		sc.add(new FoodSquare(new Point(21.5, 7.5), 2, true, 'f', 0));

		GameEngineFlags flags = new GameEngineFlags();
		flags.setUpdateMethod(GameEngineFlags.UpdateMethod.CODE);
		GameEngine engine = GameEngine.getInstance();
		engine.init(flags, sc);
		engine.start();

		snake.awake();
		engine.step();
		engine.step();
		engine.step();
		snake.turnLeft();
		engine.step();
		snake.turnLeft();
		engine.step();
		boolean isDead0 = snake.isDead();

		// Act
		snake.turnLeft();
		engine.step();
		boolean isDead1 = snake.isDead();

		// Assert
		assertFalse(isDead0);
		assertTrue(isDead1);
	}

	@Test
	public void ShouldDieWhenMovingIntoObstacle() throws GeometricException, GameEngineException, SnakeGameException
	{
		// Arrange
		Snake snake = new Snake(new Point(21.5, 7.5), Snake.Direction.DOWN, 2, true, 'o', 'x');
		Polygon obstaclePoly = new Polygon(new Point[] { new Point(21, 1), new Point(21, 2), new Point(22, 2), new Point(22, 1)});
		StaticObstacle obstacle = new StaticObstacle(obstaclePoly, true, 'r');

		Scene sc = new Scene();
		sc.add(snake);
		sc.add(obstacle);

		GameEngineFlags flags = new GameEngineFlags();
		flags.setUpdateMethod(GameEngineFlags.UpdateMethod.CODE);
		GameEngine engine = GameEngine.getInstance();
		engine.init(flags, sc);
		engine.start();

		snake.awake();
		engine.step();
		engine.step();
		boolean isDead0 = snake.isDead();

		// Act
		engine.step();
		boolean isDead1 = snake.isDead();

		// Assert
		assertFalse(isDead0);
		assertTrue(isDead1);
	}

	@Test
	public void ShouldNotMoveWhenNotAwake() throws GeometricException, GameEngineException, SnakeGameException
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
							"                    xx                  \n" +
							"                    xx                  \n" +
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
	public void ShouldNotGrowWhenDead() throws GeometricException, GameEngineException, SnakeGameException
	{
		// Arrange
		Snake snake = new Snake(new Point(5, 5), Snake.Direction.UP, 5, true, 'x', 'x');
		FoodSquare food0 = new FoodSquare(new Point(5, 5), 3, true, 'f', 0);
		FoodSquare food1 = new FoodSquare(new Point(4.5, 4.5), 2, true, 'f', 0);
		FoodCircle food2 = new FoodCircle(new Point(5, 5), 1, true, 'f', 0);

		Scene sc = new Scene();
		sc.add(snake);
		sc.add(food0);
		sc.add(food1);
		sc.add(food2);

		GameEngineFlags flags = new GameEngineFlags();
		flags.setUpdateMethod(GameEngineFlags.UpdateMethod.CODE);
		GameEngine engine = GameEngine.getInstance();
		engine.init(flags, sc);

		// Act
		snake.die();
		engine.start();
		engine.step();

		// Arrange
		assertEquals(1, snake.length());
	}

	@Test
	public void ShouldNotMoveWhenDead() throws GeometricException, GameEngineException, SnakeGameException
	{
		// Arrange
		Rectangle camera = new Rectangle(new Point[] { new Point(0, 0), new Point(0, 9), new Point(39, 9), new Point(39, 0) });

		Snake snake = new Snake(new Point(20.5, 4.5), Snake.Direction.RIGHT, 2, true, 'o', 'x');
		snake.awake();

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
							"                    xx                  \n" +
							"                    xx                  \n" +
							"                                        \n" +
							"                                        \n" +
							"                                        \n" +
							"                                        \n";

		// Act
		snake.die();
		engine.step();
		String render = out.toString();
		out.reset();

		// Assert
		assertEquals(expected, render);
	}
}
