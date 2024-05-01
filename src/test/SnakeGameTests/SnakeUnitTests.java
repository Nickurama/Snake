package SnakeGameTests;

import SnakeGame.*;
import GameEngine.*;
import Geometry.*;
import TestUtil.*;

import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayOutputStream;

public class SnakeUnitTests
{
	@Test
	public void ShouldThrowWhenZeroOrLessUnitSize()
	{
		// Arrange
		// Act
		// Assert
		assertThrows(SnakeGameException.class, () -> new Snake(new Point(2.5, 2.5), Snake.Direction.UP, 0, true, 't', 'h'));
	}

	@Test
	public void ShouldThrowWhenPlacedInInvalidPosition()
	{
		// Arrange
		// Act
		// Assert
		assertThrows(SnakeGameException.class, () -> new Snake(new Point(1, 1), Snake.Direction.UP, 5, true, 't', 'h'));
	}

	@Test
	public void ShouldNotifySnakeOfCollisions() throws GeometricException, GameEngineException, SnakeGameException
	{
		// Arrange
		Snake snake = new Snake(new Point(2.5, 2.5), Snake.Direction.UP, 2, true, 't', 'h');

		Polygon obstaclePoly = new Polygon(new Point[] { new Point(1, 1), new Point(1, 3), new Point(3, 3), new Point(3, 1) });
		StaticObstacle obstacle = new StaticObstacle(obstaclePoly, true, 'x');

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

		// Assert
		assertTrue(snake.isDead());
	}

	@Test
	public void ShouldRender() throws GeometricException, GameEngineException, SnakeGameException
	{
		// Arrange
		Snake snake = new Snake(new Point(2.5, 2.5), Snake.Direction.UP, 2, true, 't', 'h');
		System.out.println(snake.unitSize());

		Scene sc = new Scene();
		sc.add(snake);

		Rectangle camera = new Rectangle(new Point[]
		{
			new Point(0, 0),
			new Point(0, 5),
			new Point(5, 5),
			new Point(5, 0)
		});

		GameEngineFlags flags = new GameEngineFlags();
		flags.setUpdateMethod(GameEngineFlags.UpdateMethod.CODE);
		flags.setRasterized(true);
		GameEngine engine = GameEngine.getInstance();
		engine.init(flags, sc, camera);
		engine.start();

		ByteArrayOutputStream out = TestUtil.setIOstreams("");
		String expected =	"      \n" +
							"      \n" +
							"  hh  \n" +
							"  hh  \n" +
							"      \n" +
							"      \n";

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
		Snake snake = new Snake(new Point(2.5, 2.5), Snake.Direction.UP, 2, true, 't', 'h');
		SnakeUnit unit = new SnakeUnit(snake, new Point(3.5, 3.5), 'x');
		Point expected = new Point(6.5, 10.5);

		// Act
		unit.move(expected);

		// Assert
		assertEquals(expected, unit.position());
	}
}
