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
// public class SnakeUnitTests
// {
// 	@Test
// 	public void ShouldNotifySnakeOfCollisions()
// 	{
// 		// Arrange
// 		class MockSnake extends Snake
// 		{
// 			boolean hasCollided;
// 			public MockSnake(GameMap map, int size)
// 			{
// 				super(map, size);
// 				this.hasCollided = false;
// 			}
// 			@Override
// 			public void onCollision(GameObject other) { hasCollided = true; }
// 			public boolean hasCollided() { return this.hasCollided; }
// 		}
//
// 		Rectangle mapRect = new Rectangle(new Point[]
// 		{
// 			new Point(0, 0),
// 			new Point(0, 4),
// 			new Point(4, 4),
// 			new Point(4, 0)
// 		});
// 		GameMap map = new GameMap(mapRect);
// 		MockSnake snake = new MockSnake(map, 3);
// 		SnakeUnit unit = new SnakeUnit(snake, new Point(1, 1));
//
// 		Polygon obstaclePoly = new Polygon(new Point[] { new Point(1, 1), new Point(1, 3), new Point(3, 3), new Point(3, 1) });
// 		Obstacle obstacle = new Obstacle(obstaclePoly);
//
// 		Scene sc = new Scene();
// 		sc.add(unit);
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
// 		// Assert
// 		assertTrue(snake.hasCollided());
// 	}
//
// 	@Test
// 	public void ShouldRender()
// 	{
// 		// Arrange
// 		class MockSnake extends Snake
// 		{
// 			boolean hasCollided;
// 			public MockSnake(GameMap map, int size)
// 			{
// 				super(map, size);
// 				this.hasCollided = false;
// 			}
// 			@Override
// 			public void onCollision(GameObject other) { hasCollided = true; }
// 			public boolean hasCollided() { return this.hasCollided; }
// 		}
//
// 		Rectangle mapRect = new Rectangle(new Point[]
// 		{
// 			new Point(0, 0),
// 			new Point(0, 4),
// 			new Point(4, 4),
// 			new Point(4, 0)
// 		});
// 		GameMap map = new GameMap(mapRect);
// 		MockSnake snake = new MockSnake(map, 3);
// 		SnakeUnit unit = new SnakeUnit(snake, new Point(1, 1));
//
// 		Scene sc = new Scene();
// 		sc.add(unit);
//
// 		GameEngineFlags flags = new GameEngineFlags();
// 		flags.setUpdateMethod(GameEngineFlags.UpdateMethod.CODE);
// 		flags.setRasterized(true);
// 		GameEngine engine = GameEngine.getInstance();
// 		engine.init(flags, sc, mapRect);
// 		engine.start();
//
// 		ByteArrayOutputStream out = TestUtil.setIOstreams("");
// 		String expected =	"-----" +
// 							"-----" +
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
// 	public void ShouldSetPosition()
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
// 		MockSnake snake = new MockSnake(map, 3);
// 		SnakeUnit unit = new SnakeUnit(snake, new Point(1, 1));
// 		Point expected = new Point(2, 2);
//
//
// 		// Act
// 		unit.setPosition(expected);
//
// 		// Assert
// 		assertEquals(expected, unit.position());
// 	}
// }
