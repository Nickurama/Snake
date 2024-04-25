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
	public void ShouldRotate()
	{
		// Arrange
		Rectangle camera = new Rectangle(new Point[] { new Point(0, 0), new Point(0, 4), new Point(4, 4), new Point(4, 0)});
		Polygon p = new Polygon(new Point[] { new Point(1, 1), new Point(1, 3), new Point(3, 3), new Point(3, 1) });
		DynamicObstacle obstacle = new DynamicObstacle(p, Math.PI / 2, new VirtualPoint(5, 5));

		Scene sc = new Scene();
		sc.add(obstacle);

		GameEngineFlags flags = new GameEngineFlags();
		flags.setUpdateMethod(GameEngineFlags.UpdateMethod.CODE);
		flags.setRasterized(true);
		GameEngine engine = GameEngine.getInstance();
		engine.init(flags, sc, camera);
		engine.start();

		ByteArrayOutputStream out = TestUtil.setIOstreams("");
		String expected0 =	"-----------" +
							"----xxx----" +
							"----xxx----" +
							"----xxx----" +
							"-----------" +
							"-----------" +
							"-----------" +
							"-----------" +
							"-----------" +
							"-----------" +
							"-----------";

		String expected1 =	"-----------" +
							"-----------" +
							"-----------" +
							"-----------" +
							"-xxx-------" +
							"-xxx-------" +
							"-xxx-------" +
							"-----------" +
							"-----------" +
							"-----------" +
							"-----------";

		String expected2 =	"-----------" +
							"-----------" +
							"-----------" +
							"-----------" +
							"-----------" +
							"-----------" +
							"-----------" +
							"----xxx----" +
							"----xxx----" +
							"----xxx----" +
							"-----------";

		// Act
		engine.step();
		String render0 = out.toString();
		out.reset();
		engine.step();
		String render1 = out.toString();
		out.reset();
		engine.step();
		String render2 = out.toString();
		out.reset();

		// Assert
		assertEquals(expected0, render0);
		assertEquals(expected1, render1);
		assertEquals(expected2, render2);
	}
}
