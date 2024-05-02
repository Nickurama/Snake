package SnakeGameTests;

import SnakeGame.*;
import GameEngine.*;
import Geometry.*;
import TestUtil.*;

import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayOutputStream;

public class DynamicObstacleTests
{
	@Test
	public void ShouldBeRendered() throws GameEngineException, GeometricException
	{
		// Arrange
		Rectangle camera = new Rectangle(new Point[] { new Point(0, 0), new Point(0, 4), new Point(4, 4), new Point(4, 0)});
		Polygon p = new Polygon(new Point[] { new Point(1, 1), new Point(1, 3), new Point(3, 3), new Point(3, 1) });
		DynamicObstacle obstacle = new DynamicObstacle(p, true, 'x', 0);

		Scene sc = new Scene();
		sc.add(obstacle);

		GameEngineFlags flags = new GameEngineFlags();
		flags.setUpdateMethod(GameEngineFlags.UpdateMethod.CODE);
		flags.setRasterized(true);
		GameEngine engine = GameEngine.getInstance();
		engine.init(flags, sc, camera);
		engine.start();

		ByteArrayOutputStream out = TestUtil.setIOstreams("");
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
		DynamicObstacle obstacle = new DynamicObstacle(p, false, 'x', 0);

		Scene sc = new Scene();
		sc.add(obstacle);

		GameEngineFlags flags = new GameEngineFlags();
		flags.setUpdateMethod(GameEngineFlags.UpdateMethod.CODE);
		flags.setRasterized(true);
		GameEngine engine = GameEngine.getInstance();
		engine.init(flags, sc, camera);
		engine.start();

		ByteArrayOutputStream out = TestUtil.setIOstreams("");
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
		DynamicObstacle obstacle = new DynamicObstacle(p0, true, 'x', 0);
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

	@Test
	public void ShouldRotate() throws GeometricException, GameEngineException
	{
		// Arrange
		Rectangle camera = new Rectangle(new Point[] { new Point(0, 0), new Point(0, 10), new Point(10, 10), new Point(10, 0)});
		Polygon p = new Polygon(new Point[] { new Point(7, 4), new Point(7, 6), new Point(9, 6), new Point(9, 4) });
		DynamicObstacle obstacle = new DynamicObstacle(p, true, 'x', new VirtualPoint(5, 5), (float)Math.PI / 2);

		Scene sc = new Scene();
		sc.add(obstacle);

		GameEngineFlags flags = new GameEngineFlags();
		flags.setUpdateMethod(GameEngineFlags.UpdateMethod.CODE);
		flags.setRasterized(true);
		GameEngine engine = GameEngine.getInstance();
		engine.init(flags, sc, camera);
		engine.start();

		ByteArrayOutputStream out = TestUtil.setIOstreams("");
		String expected0 =	"           \n" +
							"    xxx    \n" +
							"    xxx    \n" +
							"    xxx    \n" +
							"           \n" +
							"           \n" +
							"           \n" +
							"           \n" +
							"           \n" +
							"           \n" +
							"           \n";

		String expected1 =	"           \n" +
							"           \n" +
							"           \n" +
							"           \n" +
							" xxx       \n" +
							" xxx       \n" +
							" xxx       \n" +
							"           \n" +
							"           \n" +
							"           \n" +
							"           \n";

		String expected2 =	"           \n" +
							"           \n" +
							"           \n" +
							"           \n" +
							"           \n" +
							"           \n" +
							"           \n" +
							"    xxx    \n" +
							"    xxx    \n" +
							"    xxx    \n" +
							"           \n";

		// Act
		engine.step(1);
		String render0 = out.toString();
		out.reset();
		engine.step(1);
		String render1 = out.toString();
		out.reset();
		engine.step(1);
		String render2 = out.toString();
		out.reset();

		// Assert
		assertEquals(expected0, render0);
		assertEquals(expected1, render1);
		assertEquals(expected2, render2);
	}

	@Test
	public void ShouldRotateAroundCenter() throws GeometricException, GameEngineException
	{
		// Arrange
		Rectangle camera = new Rectangle(new Point[] { new Point(0, 0), new Point(0, 10), new Point(10, 10), new Point(10, 0)});
		Polygon p = new Polygon(new Point[] { new Point(4, 4), new Point(4, 6), new Point(6, 6), new Point(6, 4) });
		DynamicObstacle obstacle = new DynamicObstacle(p, true, 'x', (float)Math.PI / 4);

		Scene sc = new Scene();
		sc.add(obstacle);

		GameEngineFlags flags = new GameEngineFlags();
		flags.setUpdateMethod(GameEngineFlags.UpdateMethod.CODE);
		flags.setRasterized(true);
		GameEngine engine = GameEngine.getInstance();
		engine.init(flags, sc, camera);
		engine.start();

		ByteArrayOutputStream out = TestUtil.setIOstreams("");
		String expected =	"           \n" +
							"           \n" +
							"           \n" +
							"           \n" +
							"     x     \n" +
							"    xxx    \n" +
							"     x     \n" +
							"           \n" +
							"           \n" +
							"           \n" +
							"           \n";

		// Act
		engine.step(1);
		String render = out.toString();
		out.reset();

		// Assert
		assertEquals(expected, render);
	}
}
