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
}
