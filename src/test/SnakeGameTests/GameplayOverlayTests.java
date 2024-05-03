package SnakeGameTests;

import SnakeGame.*;
import GameEngine.*;
import Geometry.*;
import TestUtil.*;

import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayOutputStream;
import java.time.LocalDate;

public class GameplayOverlayTests
{
	@Test
	public void ShouldSetGameplayOverlay() throws GeometricException, GameEngineException, SnakeGameException
	{
		// Arrange
		Rectangle camera = new Rectangle(new Point[] { new Point(0, 0), new Point(0, 20), new Point(20, 20), new Point(20, 0)});
		Snake snake = new Snake(new Point(5, 5), Snake.Direction.UP, 3, true, 'o', 'x');
		GameplayOverlay overlay = new GameplayOverlay(snake, camera, '╔', '╗', '╚', '╝', '║', '═');

		Scene sc = new Scene();
		sc.add(snake);
		sc.add(overlay);

		GameEngineFlags flags = new GameEngineFlags();
		flags.setUpdateMethod(GameEngineFlags.UpdateMethod.CODE);

		GameEngine engine = GameEngine.getInstance();
		engine.init(flags, sc, camera);

		ByteArrayOutputStream out = TestUtil.setIOstreams("");

		String expected =	"╔═══════════════════╗\n" +
							"║                   ║\n" +
							"║                   ║\n" +
							"║                   ║\n" +
							"║                   ║\n" +
							"║                   ║\n" +
							"║                   ║\n" +
							"║                   ║\n" +
							"║                   ║\n" +
							"║                   ║\n" +
							"║                   ║\n" +
							"║                   ║\n" +
							"║                   ║\n" +
							"║                   ║\n" +
							"║   xxx             ║\n" +
							"║   xxx             ║\n" +
							"║   xxx             ║\n" +
							"║                   ║\n" +
							"║                   ║\n" +
							"║Dir: 90    Score: 1║\n" +
							"╚═══════════════════╝\n";

		// Act
		engine.start();
		String render = out.toString();
		out.reset();

		// Assert
		assertEquals(expected, render);
	}

	@Test
	public void ShouldSwitchGameplayVariables() throws GeometricException, GameEngineException, SnakeGameException
	{
		// Arrange
		Rectangle camera = new Rectangle(new Point[] { new Point(0, 0), new Point(0, 20), new Point(20, 20), new Point(20, 0)});
		Snake snake = new Snake(new Point(11, 11), Snake.Direction.UP, 3, true, 'o', 'x');
		FoodSquare food = new FoodSquare(new Point(11, 14), 1, true, 'F', 0);
		GameplayOverlay overlay = new GameplayOverlay(snake, camera, '╔', '╗', '╚', '╝', '║', '═');

		Scene sc = new Scene();
		sc.add(snake);
		sc.add(food);
		sc.add(overlay);

		GameEngineFlags flags = new GameEngineFlags();
		flags.setUpdateMethod(GameEngineFlags.UpdateMethod.CODE);

		GameEngine engine = GameEngine.getInstance();
		engine.init(flags, sc, camera);

		ByteArrayOutputStream out = TestUtil.setIOstreams("");

		String expected0 =	"╔═══════════════════╗\n" +
							"║                   ║\n" +
							"║                   ║\n" +
							"║                   ║\n" +
							"║                   ║\n" +
							"║                   ║\n" +
							"║          F        ║\n" +
							"║                   ║\n" +
							"║         xxx       ║\n" +
							"║         xxx       ║\n" +
							"║         xxx       ║\n" +
							"║                   ║\n" +
							"║                   ║\n" +
							"║                   ║\n" +
							"║                   ║\n" +
							"║                   ║\n" +
							"║                   ║\n" +
							"║                   ║\n" +
							"║                   ║\n" +
							"║Dir: 90    Score: 1║\n" +
							"╚═══════════════════╝\n";
		String expected1 =	"╔═══════════════════╗\n" +
							"║                   ║\n" +
							"║                   ║\n" +
							"║                   ║\n" +
							"║                   ║\n" +
							"║         xxx       ║\n" +
							"║         xxx       ║\n" +
							"║         xxx       ║\n" +
							"║                   ║\n" +
							"║                   ║\n" +
							"║                   ║\n" +
							"║                   ║\n" +
							"║                   ║\n" +
							"║                   ║\n" +
							"║                   ║\n" +
							"║                   ║\n" +
							"║                   ║\n" +
							"║                   ║\n" +
							"║                   ║\n" +
							"║Dir: 90    Score: 2║\n" +
							"╚═══════════════════╝\n";
		String expected2 =	"╔═══════════════════╗\n" +
							"║                   ║\n" +
							"║         xxx       ║\n" +
							"║         xxx       ║\n" +
							"║         xxx       ║\n" +
							"║         ooo       ║\n" +
							"║         ooo       ║\n" +
							"║         ooo       ║\n" +
							"║                   ║\n" +
							"║                   ║\n" +
							"║                   ║\n" +
							"║                   ║\n" +
							"║                   ║\n" +
							"║                   ║\n" +
							"║                   ║\n" +
							"║                   ║\n" +
							"║                   ║\n" +
							"║                   ║\n" +
							"║                   ║\n" +
							"║Dir: 90    Score: 2║\n" +
							"╚═══════════════════╝\n";

		// Act
		engine.start();
		String render0 = out.toString();
		out.reset();
		snake.awake();
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

