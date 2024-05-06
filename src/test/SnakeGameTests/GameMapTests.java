package SnakeGameTests;

import SnakeGame.*;
import GameEngine.*;
import Geometry.*;
import TestUtil.*;

import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.stream.Stream;

public class GameMapTests
{
	private static final long SEED = 137;

	@Test
	public void ShouldRenderMap() throws GeometricException, GameEngineException, SnakeGameException
	{
		// Arrange
		Rectangle mapRect = new Rectangle(new Point[]
		{
			new Point(3, 3),
			new Point(3, 6),
			new Point(12, 6),
			new Point(12, 3)
		});
		GameMap map = new GameMap(mapRect, 'o');

		Rectangle camera = new Rectangle(new Point[] { new Point(3, 3), new Point(3, 6), new Point(12, 6), new Point(12, 3)});
		Scene sc = new Scene();
		sc.add(map);

		GameEngineFlags flags = new GameEngineFlags();
		flags.setUpdateMethod(GameEngineFlags.UpdateMethod.CODE);
		flags.setTextual(true);

		GameEngine engine = GameEngine.getInstance();
		engine.init(flags, sc, camera);

		ByteArrayOutputStream out = TestUtil.setIOstreams("");
		engine.start();
		out.reset();

		Character expectedChar = map.getRenderData().getCharacter();
		StringBuilder expectedBuilder = new StringBuilder();
		for (int i = 0; i < 4; i++)
		{
			for (int j = 0; j < 10; j++)
				expectedBuilder.append(expectedChar);
			expectedBuilder.append("\n");
		}
		String expected = expectedBuilder.toString();

		// Act
		engine.step();
		String render = out.toString();
		out.reset();

		// Assert
		assertEquals(expected, render);
	}

	@Test
	public void ShouldThrowWhenCreatingNearOrigin() throws GeometricException, GameEngineException, SnakeGameException
	{
		// Arrange
		Rectangle mapRect = new Rectangle(new Point[]
		{
			new Point(0, 0),
			new Point(0, 6),
			new Point(12, 6),
			new Point(12, 0)
		});

		// Act
		// Assert
		assertThrows(SnakeGameException.class, () -> new GameMap(mapRect, 'o'));
	}

	@Test
	public void ShouldCollideWhenObjectLeavesMap()  throws GeometricException, GameEngineException, SnakeGameException
	{
		// Arrange
		Rectangle mapRect = new Rectangle(new Point[]
		{
			new Point(3, 3),
			new Point(3, 6),
			new Point(12, 6),
			new Point(12, 3)
		});
		GameMap map = new GameMap(mapRect, 'o');

		Square colliderSq = new Square(new Point[] {
			new Point(0, 0),
			new Point(0, 1),
			new Point(1, 1),
			new Point(1, 0),
		});
		MockCollider collider0 = new MockCollider(colliderSq.moveCentroid(new Point(2, 2)), false);
		MockCollider collider1 = new MockCollider(colliderSq.moveCentroid(new Point(2, 7)), false);
		MockCollider collider2 = new MockCollider(colliderSq.moveCentroid(new Point(13, 7)), false);
		MockCollider collider3 = new MockCollider(colliderSq.moveCentroid(new Point(13, 2)), false);

		Scene sc = new Scene();
		sc.add(map);
		sc.add(collider0);
		sc.add(collider1);
		sc.add(collider2);
		sc.add(collider3);

		GameEngineFlags flags = new GameEngineFlags();
		flags.setUpdateMethod(GameEngineFlags.UpdateMethod.CODE);
		flags.setTextual(true);

		GameEngine engine = GameEngine.getInstance();
		engine.init(flags, sc);
		engine.start();

		// Act
		engine.step();

		// Assert
		assertTrue(collider0.hasCollided());
		assertTrue(collider1.hasCollided());
		assertTrue(collider2.hasCollided());
		assertTrue(collider3.hasCollided());
	}

	@Test
	public void ShouldCreateMapWithWidthAndLength() throws GeometricException, SnakeGameException
	{
		// Arrange
		Rectangle expected = new Rectangle(new Point(5, 5), new Point(9, 14));
		GameMap map;

		// Act
		map = new GameMap(5, 10, new Point(5, 5), 'o');
		Rectangle mapRect = map.getRenderData().getShape();

		// Assert
		assertEquals(expected, mapRect);
	}

	@Test
	public void ShouldThrowWhenCreatingMapWithInvalidProportions()
	{
		// Arrange
		// Act
		// Assert
		assertThrows(SnakeGameException.class, () -> new GameMap(-1, 4, new Point(5, 5), 'o'));
		assertThrows(SnakeGameException.class, () -> new GameMap(6, 0, new Point(5, 5), 'o'));
	}

	@Test
	public void ShouldGetAllPossibleUnitSpawnPositions() throws GeometricException, SnakeGameException
	{
		// Arrange
		Rectangle mapRect = new Rectangle(new Point(10, 10), new Point(19, 15));
		GameMap map = new GameMap(mapRect, 'o');
		int size0 = 2;
		int size1 = 3;
		int size2 = 7;
		Point[] expected0 = new Point[] {
			new Point(10.5, 10.5), new Point(12.5, 10.5), new Point(14.5, 10.5), new Point(16.5, 10.5), new Point(18.5, 10.5),
			new Point(10.5, 12.5), new Point(12.5, 12.5), new Point(14.5, 12.5), new Point(16.5, 12.5), new Point(18.5, 12.5),
			new Point(10.5, 14.5), new Point(12.5, 14.5), new Point(14.5, 14.5), new Point(16.5, 14.5), new Point(18.5, 14.5),
		};

		Point[] expected1 = new Point[] {
			new Point(11, 11), new Point(14, 11), new Point(17, 11),
			new Point(11, 14), new Point(14, 14), new Point(17, 14),
		};

		Point[] expected2 = new Point[] {
		};

		// Act
		Point[] allPossibleUnitSpawnPositions0 = map.getAllPossibleUnitSpawnPositions(size0);
		Point[] allPossibleUnitSpawnPositions1 = map.getAllPossibleUnitSpawnPositions(size1);
		Point[] allPossibleUnitSpawnPositions2 = map.getAllPossibleUnitSpawnPositions(size2);

		// Assert
		assertTrue(Arrays.equals(expected0, allPossibleUnitSpawnPositions0));
		assertTrue(Arrays.equals(expected1, allPossibleUnitSpawnPositions1));
		assertTrue(Arrays.equals(expected2, allPossibleUnitSpawnPositions2));
	}

	@Test
	public void ShouldGetAllValidUnitSpawnPositions() throws GeometricException, SnakeGameException
	{
		// Arrange
		Scene scene = new Scene();
		GameEngineFlags flags = new GameEngineFlags();
		flags.setUpdateMethod(GameEngineFlags.UpdateMethod.CODE);
		flags.setTextual(true);
		GameEngine engine = GameEngine.getInstance();
		engine.init(flags, scene);

		Snake snake = new Snake(new Point(16.5, 10.5), Snake.Direction.UP, 2, true, 'T', 'H');
		Polygon obstaclePoly = new Polygon(new Point[] {
			new Point(10, 10),
			new Point(10, 13),
			new Point(17, 15),
		});
		StaticObstacle obstacle = new StaticObstacle(obstaclePoly, true, 'O');

		Rectangle mapRect = new Rectangle(new Point(10, 10), new Point(19, 15));
		GameMap map = new GameMap(mapRect, 'o');

		scene.add(snake);
		scene.add(obstacle);
		scene.add(map);


		Point[] expected = new Point[] {
			new Point(14.5, 10.5), new Point(18.5, 10.5),
			new Point(16.5, 12.5), new Point(18.5, 12.5),
			new Point(10.5, 14.5), new Point(18.5, 14.5),
		};

		// Act
		engine.start();
		Point[] allPossibleUnitSpawnPositions = map.getAllValidUnitSpawnPositions(2);

		// Assert
		assertTrue(Arrays.equals(expected, allPossibleUnitSpawnPositions));
	}

	@Test
	public void ShouldGetAllValidInnerUnitSpawnPositions() throws GeometricException, SnakeGameException
	{
		// Arrange
		Scene scene = new Scene();
		GameEngineFlags flags = new GameEngineFlags();
		flags.setUpdateMethod(GameEngineFlags.UpdateMethod.CODE);
		flags.setTextual(true);
		GameEngine engine = GameEngine.getInstance();
		engine.init(flags, scene);

		Polygon obstaclePoly = new Polygon(new Point[] {
			new Point(21, 13),
			new Point(15, 16),
			new Point(21, 15),
		});
		StaticObstacle obstacle = new StaticObstacle(obstaclePoly, true, 'O');

		GameMap map = new GameMap(13, 8, new Point(10, 10), ' ');

		scene.add(obstacle);
		scene.add(map);


		Point[] expected0 = new Point[] {
			new Point(10.5, 10.5), new Point(11.5, 10.5), new Point(12.5, 10.5), new Point(13.5, 10.5),
			new Point(10.5, 11.5), new Point(11.5, 11.5), new Point(12.5, 11.5), new Point(13.5, 11.5),
			new Point(10.5, 12.5), new Point(11.5, 12.5), new Point(12.5, 12.5), new Point(13.5, 12.5),
			new Point(10.5, 13.5), new Point(11.5, 13.5), new Point(12.5, 13.5), new Point(13.5, 13.5),
		};

		Point[] expected1 = new Point[] {
			new Point(11, 11), new Point(12, 11), new Point(13, 11),
			new Point(11, 12), new Point(12, 12), new Point(13, 12),
			new Point(11, 13), new Point(12, 13), new Point(13, 13),
		};

		Point[] expected2 = new Point[] {
			new Point(11.5, 11.5), new Point(12.5, 11.5),
			new Point(11.5, 12.5), new Point(12.5, 12.5),
		};

		Point[] expected3 = new Point[] {
			new Point(12, 12),
		};

		Point[] expected4 = new Point[] {
		};

		Point[] expected5 = new Point[] {
			new Point(10.5, 10.5), new Point(11.5, 10.5), new Point(12.5, 10.5),
			new Point(10.5, 11.5), new Point(11.5, 11.5), new Point(12.5, 11.5),
			new Point(10.5, 12.5), new Point(11.5, 12.5), new Point(12.5, 12.5),
			
			new Point(14.5, 10.5), new Point(15.5, 10.5), new Point(16.5, 10.5),
			new Point(14.5, 11.5), new Point(15.5, 11.5), new Point(16.5, 11.5),
			new Point(14.5, 12.5), new Point(15.5, 12.5), new Point(16.5, 12.5),
			
			new Point(10.5, 14.5), new Point(11.5, 14.5), new Point(12.5, 14.5),
			new Point(10.5, 15.5), new Point(11.5, 15.5), new Point(12.5, 15.5),
			new Point(10.5, 16.5), new Point(11.5, 16.5), new Point(12.5, 16.5),
		};

		Point[] expected6 = new Point[] {
			new Point(11, 11), new Point(12, 11),
			new Point(11, 12), new Point(12, 12),

			new Point(15, 11), new Point(16, 11),
			new Point(15, 12), new Point(16, 12),

			new Point(11, 15), new Point(12, 15),
			new Point(11, 16), new Point(12, 16),
		};

		// Act
		engine.start();
		Point[] got0 = map.getAllValidInnerUnitSpawnPositions(2, 5);
		Point[] got1 = map.getAllValidInnerUnitSpawnPositions(3, 5);
		Point[] got2 = map.getAllValidInnerUnitSpawnPositions(4, 5);
		Point[] got3 = map.getAllValidInnerUnitSpawnPositions(5, 5);
		Point[] got4 = map.getAllValidInnerUnitSpawnPositions(6, 5);

		Point[] got5 = map.getAllValidInnerUnitSpawnPositions(2, 4);
		Point[] got6 = map.getAllValidInnerUnitSpawnPositions(3, 4);

		// Assert
		assertTrue(Arrays.equals(expected0, got0));
		assertTrue(Arrays.equals(expected1, got1));
		assertTrue(Arrays.equals(expected2, got2));
		assertTrue(Arrays.equals(expected3, got3));
		assertTrue(Arrays.equals(expected4, got4));
		assertTrue(Arrays.equals(expected5, got5));
		assertTrue(Arrays.equals(expected6, got6));
	}

	@Test
	public void ShouldGetRandomValidUnitSpawnPosition() throws GeometricException, SnakeGameException
	{
		// Arrange
		Scene scene = new Scene();
		GameEngineFlags flags = new GameEngineFlags();
		flags.setUpdateMethod(GameEngineFlags.UpdateMethod.CODE);
		flags.setTextual(true);
		GameEngine engine = GameEngine.getInstance();
		engine.init(flags, scene);

		Polygon obstaclePoly = new Polygon(new Point[] {
			new Point(21, 13),
			new Point(15, 16),
			new Point(21, 15),
		});
		StaticObstacle obstacle = new StaticObstacle(obstaclePoly, true, 'O');

		GameMap map = new GameMap(13, 8, new Point(10, 10), ' ');

		scene.add(obstacle);
		scene.add(map);

		int size = 3;

		// Act
		map.setSeed(SEED);
		Point randomSpawnPosition = map.getRandomUnitSpawnPosition(size);

		// Assert
		assertTrue(Stream.of(map.getAllValidUnitSpawnPositions(size)).anyMatch(randomSpawnPosition::equals));
	}

	@Test
	public void ShouldGetRandomValidInnerUnitSpawnPosition() throws GeometricException, SnakeGameException
	{
		// Arrange
		Scene scene = new Scene();
		GameEngineFlags flags = new GameEngineFlags();
		flags.setUpdateMethod(GameEngineFlags.UpdateMethod.CODE);
		flags.setTextual(true);
		GameEngine engine = GameEngine.getInstance();
		engine.init(flags, scene);

		Polygon obstaclePoly = new Polygon(new Point[] {
			new Point(21, 13),
			new Point(15, 16),
			new Point(21, 15),
		});
		StaticObstacle obstacle = new StaticObstacle(obstaclePoly, true, 'O');

		GameMap map = new GameMap(13, 8, new Point(10, 10), ' ');

		scene.add(obstacle);
		scene.add(map);

		int size = 3;
		int outerSize = 5;

		// Act
		map.setSeed(SEED);
		Point randomSpawnPosition = map.getRandomInnerUnitSpawnPosition(size, outerSize);

		// Assert
		assertTrue(Stream.of(map.getAllValidInnerUnitSpawnPositions(size, outerSize)).anyMatch(randomSpawnPosition::equals));
	}

	@Test
	public void ShouldGetNullWhenCallingRandomPositionButThereIsNoPosition() throws GeometricException, SnakeGameException
	{
		// Arrange
		GameMap map0 = new GameMap(13, 8, new Point(10, 10), ' ');
		map0.setSeed(SEED);
		GameMap map1 = new GameMap(2, 2, new Point(10, 10), ' ');
		map1.setSeed(SEED);

		// Act
		Point randomSpawnPosition0 = map0.getRandomInnerUnitSpawnPosition(10, 5);
		Point randomSpawnPosition1 = map1.getRandomUnitSpawnPosition(5);

		// Assert
		assertEquals(null, randomSpawnPosition0);
		assertEquals(null, randomSpawnPosition1);
	}

	@Test
	public void ShouldGetObjectsOccupyingUnit() throws GeometricException, SnakeGameException
	{
		// Arrange
		Scene scene = new Scene();
		GameEngineFlags flags = new GameEngineFlags();
		flags.setUpdateMethod(GameEngineFlags.UpdateMethod.CODE);
		flags.setTextual(true);
		GameEngine engine = GameEngine.getInstance();
		engine.init(flags, scene);

		Polygon obstaclePoly0 = new Polygon(new Point[] {
			new Point(21, 13),
			new Point(15, 16),
			new Point(21, 15),
		});
		Polygon obstaclePoly1 = new Polygon(new Point[] {
			new Point(17, 13),
			new Point(19, 16),
			new Point(21, 13),
		});
		StaticObstacle obstacle0 = new StaticObstacle(obstaclePoly0, true, 'O');
		StaticObstacle obstacle1 = new StaticObstacle(obstaclePoly1, true, 'O');

		GameMap map = new GameMap(13, 8, new Point(10, 10), ' ');

		scene.add(obstacle0);
		scene.add(obstacle1);
		scene.add(map);

		// Act
		GameObject[] objs = map.getObjectsOccupyingUnit(new Point(19, 14), 3);

		// Assert
		assertEquals(2, objs.length);
		boolean contains0 = false;
		for (GameObject obj : objs)
			if(obstaclePoly0.equals(((ICollider)obj).getCollider()))
				contains0 = true;
		boolean contains1 = false;
		for (GameObject obj : objs)
			if(obstaclePoly0.equals(((ICollider)obj).getCollider()))
				contains1 = true;
		assertTrue(contains0);
		assertTrue(contains1);
	}
}
