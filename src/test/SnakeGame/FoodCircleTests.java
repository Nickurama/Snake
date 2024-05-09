package SnakeGame;

import GameEngine.*;
import Geometry.*;
import TestUtil.*;

import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayOutputStream;

public class FoodCircleTests
{
	@Test
	public void ShouldBeRendered() throws GeometricException, GameEngineException, SnakeGameException
	{
		// Arrange
		Rectangle camera = new Rectangle(new Point[] { new Point(3, 3), new Point(3, 7), new Point(7, 7), new Point(7, 3)});
		FoodCircle food = new FoodCircle(new Point(5, 5), 2, false, 'x');

		Scene sc = new Scene();
		sc.add(food);

		GameEngineFlags flags = new GameEngineFlags();
		flags.setUpdateMethod(GameEngineFlags.UpdateMethod.CODE);
		GameEngine engine = GameEngine.getInstance();
		engine.init(flags, sc, camera);
		engine.start();

		ByteArrayOutputStream out = TestUtil.setIOstreams("");
		String expected =	" xxx \n" +
							"x   x\n" +
							"x   x\n" +
							"x   x\n" +
							" xxx \n";

		// Act
		engine.step();
		String render = out.toString();
		out.reset();

		// Assert
		assertEquals(expected, render);
	}

	@Test
	public void ShouldCollide() throws GeometricException, GameEngineException, SnakeGameException
	{
		// Arrange
		Polygon colliderPoly = new Polygon(new Point[] { new Point(1, 1), new Point(1, 3), new Point(3, 3), new Point(3, 1) });
		FoodCircle food = new FoodCircle(new Point(2.5, 2.5), 2, true, 'x');
		MockCollider collider = new MockCollider(colliderPoly, false);

		Scene sc = new Scene();
		sc.add(food);
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
	}

	@Test
	public void ShouldDisappearWhenConsumed() throws GeometricException, GameEngineException, SnakeGameException
	{
		// Arrange
		Rectangle camera = new Rectangle(new Point[] { new Point(3, 3), new Point(3, 7), new Point(7, 7), new Point(7, 3)});
		FoodCircle food = new FoodCircle(new Point(5, 5), 2, true, 'x');

		Scene sc = new Scene();
		sc.add(food);

		GameEngineFlags flags = new GameEngineFlags();
		flags.setUpdateMethod(GameEngineFlags.UpdateMethod.CODE);
		GameEngine engine = GameEngine.getInstance();
		engine.init(flags, sc, camera);
		engine.start();

		ByteArrayOutputStream out = TestUtil.setIOstreams("");
		String expected0 =	" xxx \n" +
							"xxxxx\n" +
							"xxxxx\n" +
							"xxxxx\n" +
							" xxx \n";

		String expected1 =	"     \n" +
							"     \n" +
							"     \n" +
							"     \n" +
							"     \n";

		// Act
		engine.step();
		String render0 = out.toString();
		out.reset();

		food.consume();

		engine.step();
		String render1 = out.toString();
		out.reset();

		// Assert
		assertEquals(expected0, render0);
		assertEquals(expected1, render1);
	}
}
