package SnakeGameTests;

import SnakeGame.*;
import GameEngine.*;
import Geometry.*;
import TestUtil.*;

import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayOutputStream;

public class UnitTests
{
	@Test
	public void ShouldThrowWhenZeroOrLessUnitSize()
	{
		// Arrange
		// Act
		// Assert
		assertThrows(SnakeGameException.class, () -> new Unit(new Point(2.5, 2.5), 0, true, 'x', 1));
		assertThrows(SnakeGameException.class, () -> new Unit(new Point(2.5, 2.5), -1, true, 'x', 1));
	}

	@Test
	public void ShouldThrowWhenPlacedInInvalidPosition()
	{
		// Arrange
		// Act
		// Assert
		assertThrows(SnakeGameException.class, () -> new Unit(new Point(2.5, 2.5), 6, true, 'x', 1));
	}

	@Test
	public void ShouldRender() throws GeometricException, GameEngineException, SnakeGameException
	{
		// Arrange
		Unit unit = new Unit(new Point(2, 2), 3, true, 'x', 1);

		Scene sc = new Scene();
		sc.add(unit);

		Rectangle camera = new Rectangle(new Point[]
		{
			new Point(0, 0),
			new Point(0, 4),
			new Point(4, 4),
			new Point(4, 0)
		});

		GameEngineFlags flags = new GameEngineFlags();
		flags.setUpdateMethod(GameEngineFlags.UpdateMethod.CODE);
		flags.setRasterized(true);
		GameEngine engine = GameEngine.getInstance();
		engine.init(flags, sc, camera);
		ByteArrayOutputStream out = TestUtil.setIOstreams("");
		engine.start();
		out.reset();

		String expected =	"     \n" +
							" xxx \n" +
							" xxx \n" +
							" xxx \n" +
							"     \n";

		// Act
		engine.step();
		String render = out.toString();
		out.reset();

		// Assert
		assertEquals(expected, render);
	}

	@Test
	public void ShouldBeMoved() throws GeometricException, GameEngineException, SnakeGameException
	{
		// Arrange
		Unit unit = new Unit(new Point(2, 2), 3, true, 'x', 1);
		Point expected = new Point(6.5, 10.5);

		// Act
		unit.move(expected);

		// Assert
		assertEquals(expected, unit.position());
	}

	@Test
	public void ShouldSetDrawChar() throws GeometricException, GameEngineException, SnakeGameException
	{
		// Arrange
		Unit unit = new Unit(new Point(2, 2), 3, true, 'x', 1);

		Scene sc = new Scene();
		sc.add(unit);

		Rectangle camera = new Rectangle(new Point[]
		{
			new Point(0, 0),
			new Point(0, 4),
			new Point(4, 4),
			new Point(4, 0)
		});

		GameEngineFlags flags = new GameEngineFlags();
		flags.setUpdateMethod(GameEngineFlags.UpdateMethod.CODE);
		flags.setRasterized(true);
		GameEngine engine = GameEngine.getInstance();
		engine.init(flags, sc, camera);
		ByteArrayOutputStream out = TestUtil.setIOstreams("");
		engine.start();
		out.reset();

		String expected =	"     \n" +
							" ooo \n" +
							" ooo \n" +
							" ooo \n" +
							"     \n";

		// Act
		unit.setDrawChar('o');
		engine.step();
		String render = out.toString();
		out.reset();

		// Assert
		assertEquals(expected, render);
	}
}
