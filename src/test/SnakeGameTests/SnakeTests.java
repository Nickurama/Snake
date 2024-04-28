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
// public class SnakeTests
// {
// 	@Test
// 	public void ShouldDieWhenCollidingWithObstacle()
// 	{
// 		// Arrange
// 		Snake snake = new Snake(3, new Point(1, 1), Snake.Direction.UP);
//
// 		Polygon p = new Polygon(new Point[] { new Point(1, 1), new Point(1, 3), new Point(3, 3), new Point(3, 1) });
// 		Obstacle obstacle = new Obstacle(p);
//
// 		Scene sc = new Scene();
// 		sc.add(snake);
// 		sc.add(obstacle);
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
// 		assertTrue(snake.isDead());
// 	}
//
// 	@Test
// 	public void ShouldDieWhenCollidingWithWall()
// 	{
// 		// Arrange
// 		Rectangle mapRect = new Rectangle(new Point[]
// 		{
// 			new Point(0, 0),
// 			new Point(0, 4),
// 			new Point(4, 4),
// 			new Point(4, 0)
// 		});
// 		GameMap map = new GameMap(mapRect);
// 		Snake snake = new Snake(3, new Point(1, 1), Snake.Direction.DOWN);
//
// 		Scene sc = new Scene();
// 		sc.add(snake);
// 		sc.add(map);
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
// 		assertTrue(snake.isDead());
// 	}
//
// 	@Test
// 	public void ShouldGrowWhenContainingFood()
// 	{
// 		// Arrange
// 		Snake snake = new Snake(3, new Point(1, 1), Snake.Direction.UP);
// 		FoodSquare food = new FoodSquare(map, 3, new Point(1, 3));
//
// 		Scene sc = new Scene();
// 		sc.add(snake);
// 		sc.add(food);
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
// 		assertEquals(2, snake.length());
// 	}
//
// 	@Test
// 	public void ShouldNotGrowWhenCollidingWithFoodButNotContaining()
// 	{
// 		// Arrange
// 		Snake snake = new Snake(3, new Point(1, 1), Snake.Direction.UP);
// 		FoodSquare food = new FoodSquare(map, 3, new Point(2, 2));
//
// 		Scene sc = new Scene();
// 		sc.add(snake);
// 		sc.add(food);
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
// 		assertEquals(1, snake.length());
// 	}
//
// 	@Test
// 	public void ShouldMoveOnUpdate()
// 	{
// 		// Arrange
// 		Snake snake = new Snake(3, new Point(1, 1), Snake.Direction.UP);
// 		FoodSquare food = new FoodSquare(map, 3, new Point(2, 2));
//
// 		Scene sc = new Scene();
// 		sc.add(snake);
// 		sc.add(food);
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
// 		assertEquals(1, snake.length());
// 	}
//
// 	@Test
// 	public void ShouldGrowVisiblyWhenGrowing()
// 	{
// 		// Arrange
// 		Rectangle camera = new Rectangle(new Point[]
// 		{
// 			new Point(0, 0),
// 			new Point(0, 4),
// 			new Point(4, 4),
// 			new Point(4, 0)
// 		});
// 		Snake snake = new Snake(3, new Point(1, 1), Snake.Direction.UP);
// 		FoodSquare food = new FoodSquare(map, 3, new Point(2, 2));
//
// 		Scene sc = new Scene();
// 		sc.add(snake);
//
// 		GameEngineFlags flags = new GameEngineFlags();
// 		flags.setUpdateMethod(GameEngineFlags.UpdateMethod.CODE);
// 		flags.setRasterized(true);
// 		GameEngine engine = GameEngine.getInstance();
// 		engine.init(flags, sc, camera);
// 		engine.start();
//
// 		ByteArrayOutputStream out = TestUtil.setIOstreams("");
// 		String expected =	"xxx--" +
// 							"xxx--" +
// 							"xxx--" +
// 							"xxx--" +
// 							"xxx--";
//
// 		// Act
// 		engine.step();
// 		String render = out.toString();
// 		out.reset();
//
// 		// Assert
// 		assertEquals(expected, render);
// 	}
//
// 	@Test
// 	public void ShouldMantainDirection()
// 	{
// 		// Arrange
// 		Snake snake = new Snake(3, new Point(1, 1), Snake.Direction.UP);
//
// 		Scene sc = new Scene();
// 		sc.add(snake);
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
// 		assertEquals(1, snake.length());
// 	}
//
// 	@Test
// 	public void ShouldTurnLeftWhenGoingLeft()
// 	{
// 		// Arrange
// 		Snake snake = new Snake(3, new Point(1, 1), Snake.Direction.LEFT);
//
// 		// Act
// 		snake.turnLeft();
// 		
// 		// Arrange
// 		assertEquals(Snake.Direction.DOWN, snake.direction());
// 	}
//
// 	@Test
// 	public void ShouldTurnRightWhenGoingLeft()
// 	{
// 		// Arrange
// 		Snake snake = new Snake(3, new Point(1, 1), Snake.Direction.LEFT);
//
// 		// Act
// 		snake.turnLeft();
// 		
// 		// Arrange
// 		assertEquals(Snake.Direction.UP, snake.direction());
// 	}
//
// 	@Test
// 	public void ShouldTurnLeftWhenGoingUp()
// 	{
// 		// Arrange
// 		Snake snake = new Snake(3, new Point(1, 1), Snake.Direction.UP);
//
// 		// Act
// 		snake.turnLeft();
// 		
// 		// Arrange
// 		assertEquals(Snake.Direction.LEFT, snake.direction());
// 	}
//
// 	@Test
// 	public void ShouldTurnRightWhenGoingUp()
// 	{
// 		// Arrange
// 		Snake snake = new Snake(3, new Point(1, 1), Snake.Direction.UP);
//
// 		// Act
// 		snake.turnLeft();
// 		
// 		// Arrange
// 		assertEquals(Snake.Direction.RIGHT, snake.direction());
// 	}
//
// 	@Test
// 	public void ShouldTurnLeftWhenGoingRight()
// 	{
// 		// Arrange
// 		Snake snake = new Snake(3, new Point(1, 1), Snake.Direction.RIGHT);
//
// 		// Act
// 		snake.turnLeft();
// 		
// 		// Arrange
// 		assertEquals(Snake.Direction.UP, snake.direction());
// 	}
//
// 	@Test
// 	public void ShouldTurnRightWhenGoingRight()
// 	{
// 		// Arrange
// 		Snake snake = new Snake(3, new Point(1, 1), Snake.Direction.RIGHT);
//
// 		// Act
// 		snake.turnLeft();
// 		
// 		// Arrange
// 		assertEquals(Snake.Direction.DOWN, snake.direction());
// 	}
//
// 	@Test
// 	public void ShouldTurnLeftWhenGoingDown()
// 	{
// 		// Arrange
// 		Snake snake = new Snake(3, new Point(1, 1), Snake.Direction.DOWN);
//
// 		// Act
// 		snake.turnLeft();
// 		
// 		// Arrange
// 		assertEquals(Snake.Direction.RIGHT, snake.direction());
// 	}
//
// 	@Test
// 	public void ShouldTurnRightWhenGoingDown()
// 	{
// 		// Arrange
// 		Snake snake = new Snake(new Point(1, 1), Snake.Direction.DOWN);
//
// 		// Act
// 		snake.turnLeft();
// 		
// 		// Arrange
// 		assertEquals(Snake.Direction.LEFT, snake.direction());
// 	}
//
// }
