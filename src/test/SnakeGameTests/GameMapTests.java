package SnakeGameTests;

import SnakeGame.*;
import GameEngine.*;
import Geometry.*;
import TestUtil.*;

import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;

public class GameMapTests
{
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
		flags.setRasterized(true);
		flags.setTextual(true);

		GameEngine engine = GameEngine.getInstance();
		engine.init(flags, sc, camera);
		engine.start();

		ByteArrayOutputStream out = TestUtil.setIOstreams("");
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
		flags.setRasterized(true);
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
}
