package SnakeGameTests;

import SnakeGame.*;
import GameEngine.*;
import Geometry.*;
import TestUtil.*;

import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayOutputStream;

public class StaticObstacleTests
{
	@Test
	public void ShouldBeRendered() throws GameEngineException, GeometricException
	{
		// Arrange
		Rectangle camera = new Rectangle(new Point[] { new Point(0, 0), new Point(0, 4), new Point(4, 4), new Point(4, 0)});
		Polygon p = new Polygon(new Point[] { new Point(1, 1), new Point(1, 3), new Point(3, 3), new Point(3, 1) });
		StaticObstacle obstacle = new StaticObstacle(p, true, 'x');

		Scene sc = new Scene();
		sc.add(obstacle);

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
	public void ShouldBeRenderedNoFill() throws GameEngineException, GeometricException
	{
		// Arrange
		Rectangle camera = new Rectangle(new Point[] { new Point(0, 0), new Point(0, 4), new Point(4, 4), new Point(4, 0)});
		Polygon p = new Polygon(new Point[] { new Point(1, 1), new Point(1, 3), new Point(3, 3), new Point(3, 1) });
		StaticObstacle obstacle = new StaticObstacle(p, false, 'x');

		Scene sc = new Scene();
		sc.add(obstacle);

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
							" x x \n" +
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
	public void ShouldCollide() throws GameEngineException, GeometricException
	{
		// Arrange
		Polygon p0 = new Polygon(new Point[] { new Point(1, 1), new Point(1, 3), new Point(3, 3), new Point(3, 1) });
		Polygon p1 = new Polygon(new Point[] { new Point(2, 2), new Point(2, 4), new Point(4, 4), new Point(4, 2) });
		StaticObstacle obstacle = new StaticObstacle(p0, true, 'x');
		MockCollider collider = new MockCollider(p1, false);

		Scene sc = new Scene();
		sc.add(obstacle);
		sc.add(collider);

		GameEngineFlags flags = new GameEngineFlags();
		flags.setUpdateMethod(GameEngineFlags.UpdateMethod.CODE);
		GameEngine engine = GameEngine.getInstance();
		engine.init(flags, sc);
		engine.start();

		// Act
		engine.step();
		
		// Arrange
		assertTrue(collider.hasCollided());
		assertEquals(collider.getOtherShape(), p0);
	}
}
