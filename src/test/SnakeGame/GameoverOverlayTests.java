package SnakeGame;

import GameEngine.*;
import Geometry.*;
import TestUtil.*;

import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayOutputStream;
import java.time.LocalDate;

public class GameoverOverlayTests
{

	class MockScore implements ISnakeStats
	{
		Snake snake;
		public MockScore(Snake snake) { this.snake = snake; }
		public Point position() { return this.snake.position(); }
		public Direction direction() { return this.snake.direction(); }
		public int score() { return this.snake.length() - 1; }
	}

	@Test
	public void ShouldSetGameOverOverlay() throws GeometricException, GameEngineException, SnakeGameException
	{
		// Arrange
		Rectangle camera = new Rectangle(new Point[] { new Point(0, 0), new Point(0, 10), new Point(40, 10), new Point(40, 0)});
		Snake snake = new Snake(new Point(5, 5), Direction.UP, 3, true, 'o', 'x');
		snake.awake();
		MockScore mScore = new MockScore(snake);
		GameoverOverlay overlay = new GameoverOverlay(mScore, camera, '╔', '╗', '╚', '╝', '║', '═');

		Scene sc = new Scene();
		sc.add(new FoodSquare(new Point(5, 5), 1, true, 'F'));
		sc.add(new FoodSquare(new Point(5, 5), 1, true, 'F'));
		sc.add(new FoodSquare(new Point(5, 5), 1, true, 'F'));
		sc.add(new FoodSquare(new Point(5, 5), 1, true, 'F'));
		sc.add(new FoodSquare(new Point(5, 5), 1, true, 'F'));
		sc.add(new FoodSquare(new Point(5, 5), 1, true, 'F'));
		sc.add(new FoodSquare(new Point(5, 5), 1, true, 'F'));
		sc.add(new FoodSquare(new Point(5, 5), 1, true, 'F'));
		sc.add(new FoodSquare(new Point(5, 5), 1, true, 'F'));
		sc.add(new FoodSquare(new Point(5, 5), 1, true, 'F'));
		sc.add(snake);

		GameEngineFlags flags = new GameEngineFlags();
		flags.setUpdateMethod(GameEngineFlags.UpdateMethod.CODE);

		GameEngine engine = GameEngine.getInstance();
		engine.init(flags, sc, camera);
		ByteArrayOutputStream out = TestUtil.setIOstreams("");
		engine.start();
		out.reset();

		String expected =	"╔═══════════════════════════════════════╗\n" +
							"║                                       ║\n" +
							"║                                       ║\n" +
							"║              Game Over!               ║\n" +
							"║                                       ║\n" +
							"║               Score: 10               ║\n" +
							"║                                       ║\n" +
							"║          What is your name?           ║\n" +
							"║                                       ║\n" +
							"║                                       ║\n" +
							"╚═══════════════════════════════════════╝\n";

		// Act
		sc.add(overlay);
		engine.step();
		String render = out.toString();
		out.reset();

		// Assert
		assertEquals(expected, render);
	}

	@Test
	public void ShouldNotShowAnythingUnderGameOverOverlay() throws GameEngineException, GeometricException, SnakeGameException
	{
		// Arrange
		Rectangle camera = new Rectangle(new Point[] { new Point(0, 0), new Point(0, 10), new Point(40, 10), new Point(40, 0)});
		StaticObstacle obstacle = new StaticObstacle(camera, true, 'x');
		Snake snake = new Snake(new Point(5, 5), Direction.UP, 3, true, 'o', 'x');
		MockScore mScore = new MockScore(snake);
		GameoverOverlay overlay = new GameoverOverlay(mScore, camera, '╔', '╗', '╚', '╝', '║', '═');
		Scene sc = new Scene();
		sc.add(snake);
		sc.add(overlay);
		sc.add(obstacle);

		GameEngineFlags flags = new GameEngineFlags();
		flags.setUpdateMethod(GameEngineFlags.UpdateMethod.CODE);

		GameEngine engine = GameEngine.getInstance();
		engine.init(flags, sc, camera);
		engine.start();

		ByteArrayOutputStream out = TestUtil.setIOstreams("");
		String expected =	"╔═══════════════════════════════════════╗\n" +
							"║                                       ║\n" +
							"║                                       ║\n" +
							"║              Game Over!               ║\n" +
							"║                                       ║\n" +
							"║               Score: 0                ║\n" +
							"║                                       ║\n" +
							"║          What is your name?           ║\n" +
							"║                                       ║\n" +
							"║                                       ║\n" +
							"╚═══════════════════════════════════════╝\n";

		// Act
		engine.step();
		String render = out.toString();
		out.reset();

		// Assert
		assertEquals(expected, render);
	}
}

