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
// public class FoodSquareTests
// {
// 	public void ShouldBeRendered()
// 	{
// 		// Arrange
// 		Rectangle camera = new Rectangle(new Point[] { new Point(0, 0), new Point(0, 4), new Point(4, 4), new Point(4, 0)});
// 		GameMap map = new GameMap(camera);
// 		FoodSquare food = new FoodSquare(GameMap, 5);
//
// 		Scene sc = new Scene();
// 		sc.add(food);
//
// 		GameEngineFlags flags = new GameEngineFlags();
// 		flags.setUpdateMethod(GameEngineFlags.UpdateMethod.CODE);
// 		flags.setRasterized(false);
// 		GameEngine engine = GameEngine.getInstance();
// 		engine.init(flags, sc, camera);
// 		engine.start();
//
// 		ByteArrayOutputStream out = TestUtil.setIOstreams("");
// 		String expected =	"xxxxx" +
// 							"x---x" +
// 							"x---x" +
// 							"x---x" +
// 							"xxxxx";
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
// 	public void ShouldCollide()
// 	{
// 		// Arrange
// 		Polygon colliderPoly = new Polygon(new Point[] { new Point(1, 1), new Point(1, 3), new Point(3, 3), new Point(3, 1) });
// 		Rectangle mapRect = new Rectangle(new Point[] { new Point(0, 0), new Point(0, 4), new Point(4, 4), new Point(4, 0)});
// 		GameMap map = new GameMap(mapRect);
// 		FoodSquare food = new FoodSquare(map, 3);
// 		MockCollider collider = new MockCollider(colliderPoly);
//
// 		Scene sc = new Scene();
// 		sc.add(food);
// 		sc.add(collider);
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
// 		assertTrue(collider.hasCollided());
// 	}
//
// 	public void ShouldRespawnInReachableArea()
// 	{
// 		// Arrange
// 		Rectangle mapRect = new Rectangle(new Point[] { new Point(0, 0), new Point(0, 4), new Point(4, 4), new Point(4, 0)});
// 		GameMap map = new GameMap(mapRect);
// 		FoodSquare food = new FoodSquare(map, 2);
//
// 		Scene sc = new Scene();
// 		sc.add(food);
//
// 		GameEngineFlags flags = new GameEngineFlags();
// 		flags.setUpdateMethod(GameEngineFlags.UpdateMethod.CODE);
// 		GameEngine engine = GameEngine.getInstance();
// 		engine.init(flags, sc);
// 		engine.start();
//
// 		Point[] expectedPoints = new Point[]
// 		{
// 			new Point(0.5, 3.5),
// 			new Point(1.5, 3.5),
// 			new Point(2.5, 3.5),
// 			new Point(3.5, 3.5),
// 			new Point(0.5, 2.5),
// 			new Point(1.5, 2.5),
// 			new Point(2.5, 2.5),
// 			new Point(3.5, 2.5),
// 			new Point(0.5, 1.5),
// 			new Point(1.5, 1.5),
// 			new Point(2.5, 1.5),
// 			new Point(3.5, 1.5),
// 			new Point(0.5, 0.5),
// 			new Point(1.5, 0.5),
// 			new Point(2.5, 0.5),
// 			new Point(3.5, 0.5),
// 		};
//
// 		// Act
// 		boolean hasSpawnedWithinExpectedPoints = false;
// 		for (Point expectedPoint : expectedPoints)
// 		{
// 			if (food.position().equals(expectedPoint))
// 			{
// 				hasSpawnedWithinExpectedPoints = true;
// 				break;
// 			}
// 		}
// 		
// 		// Arrange
// 		assertTrue(hasSpawnedWithinExpectedPoints);
// 	}
//
// 	public void ShouldRespawnOutsideOfObstacles()
// 	{
// 		// Arrange
// 		Polygon colliderPoly = new Polygon(new Point[]
// 		{
// 			new Point(0, 2),
// 			new Point(2, 2),
// 			new Point(2, 4),
// 			new Point(4, 4),
// 			new Point(0, 4),
// 			new Point(0, 0)
// 		});
// 		Rectangle mapRect = new Rectangle(new Point[]
// 		{
// 			new Point(0, 0),
// 			new Point(0, 4),
// 			new Point(4, 4),
// 			new Point(4, 0)
// 		});
// 		GameMap map = new GameMap(mapRect);
// 		FoodSquare food = new FoodSquare(map, 3);
// 		MockCollider collider = new MockCollider(colliderPoly);
//
// 		Scene sc = new Scene();
// 		sc.add(collider);
// 		sc.add(food);
//
// 		GameEngineFlags flags = new GameEngineFlags();
// 		flags.setUpdateMethod(GameEngineFlags.UpdateMethod.CODE);
// 		GameEngine engine = GameEngine.getInstance();
// 		engine.init(flags, sc);
// 		
// 		Point expected = new Point(1, 3);
//
// 		// Act
// 		engine.start();
//
// 		
// 		// Arrange
// 		assertEquals(expected, food.position());
// 	}
//
// 	public void ShouldRespawnOutsideOfSnake()
// 	{
// 		Rectangle mapRect = new Rectangle(new Point[]
// 		{
// 			new Point(0, 0),
// 			new Point(0, 4),
// 			new Point(4, 4),
// 			new Point(4, 0)
// 		});
// 		GameMap map = new GameMap(mapRect);
// 		FoodSquare food = new FoodSquare(map, 3);
// 		Snake snake = new Snake(map, 3);
// 		SnakeUnit unit0 = new SnakeUnit(snake, new Point(1, 1));
// 		SnakeUnit unit1 = new SnakeUnit(snake, new Point(3, 1));
// 		SnakeUnit unit2 = new SnakeUnit(snake, new Point(3, 3));
//
// 		Scene sc = new Scene();
// 		sc.add(unit0);
// 		sc.add(unit1);
// 		sc.add(unit2);
// 		sc.add(food);
//
// 		GameEngineFlags flags = new GameEngineFlags();
// 		flags.setUpdateMethod(GameEngineFlags.UpdateMethod.CODE);
// 		GameEngine engine = GameEngine.getInstance();
// 		engine.init(flags, sc);
// 		
// 		Point expected = new Point(1, 3);
//
// 		// Act
// 		engine.start();
//
// 		
// 		// Arrange
// 		assertEquals(expected, food.position());
// 	}
//
// 	public void ShouldCallWinWhenCantRespawn()
// 	{
// 		Rectangle mapRect = new Rectangle(new Point[]
// 		{
// 			new Point(0, 0),
// 			new Point(0, 4),
// 			new Point(4, 4),
// 			new Point(4, 0)
// 		});
// 		GameMap map = new GameMap(mapRect);
// 		FoodSquare food = new FoodSquare(map, 3);
// 		Snake snake = new Snake(map, 3);
// 		SnakeUnit unit0 = new SnakeUnit(snake, new Point(1, 1));
// 		SnakeUnit unit1 = new SnakeUnit(snake, new Point(3, 1));
// 		SnakeUnit unit2 = new SnakeUnit(snake, new Point(3, 3));
// 		SnakeUnit unit3 = new SnakeUnit(snake, new Point(1, 3));
//
// 		Scene sc = new Scene();
// 		sc.add(unit0);
// 		sc.add(unit1);
// 		sc.add(unit2);
// 		sc.add(unit3);
// 		sc.add(food);
//
// 		GameEngineFlags flags = new GameEngineFlags();
// 		flags.setUpdateMethod(GameEngineFlags.UpdateMethod.CODE);
// 		GameEngine engine = GameEngine.getInstance();
// 		engine.init(flags, sc);
// 		
// 		Point expected = new Point(1, 3);
//
// 		// Act
// 		engine.start();
//
// 		
// 		// Arrange
// 		assertEquals(GameManager.GameState.GAMEOVER, GameManager.getInstance().state());
// 	}
// }
