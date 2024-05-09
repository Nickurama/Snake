package SnakeGame;

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
		GameEngine engine = GameEngine.getInstance();
		engine.stop();

		new GameManagerBuilder()
			.setMapWidth(18)
			.setMapHeight(18)
			.setSnakePos(new Point(4, 4))
			.setSnakeSize(3)
			.setTextual(true)
			.setFoodPos(new Point(0, 0))
			.setFoodSize(1)
			.setSnakeDir(Direction.UP)
			.setFoodScore(2)
			.setFoodType(GameManager.FoodType.SQUARE)
			.setFilled(true)
			.setUpdateMethod(GameEngineFlags.UpdateMethod.CODE)
			.setControlMethod(GameManager.ControlMethod.MANUAL)
			.setMapChar(' ')
			.setSnakeHeadChar('x')
			.setSnakeTailChar('o')
			.setFoodChar('f')
			.build();


		ByteArrayOutputStream out = TestUtil.setIOstreams("");

		String expected =	"╔══════════════════╗\n" +
							"║                  ║\n" +
							"║                  ║\n" +
							"║                  ║\n" +
							"║                  ║\n" +
							"║                  ║\n" +
							"║                  ║\n" +
							"║                  ║\n" +
							"║                  ║\n" +
							"║                  ║\n" +
							"║                  ║\n" +
							"║                  ║\n" +
							"║                  ║\n" +
							"║   xxx            ║\n" +
							"║   xxx            ║\n" +
							"║   xxx            ║\n" +
							"║                  ║\n" +
							"║                  ║\n" +
							"║f                 ║\n" +
							"║Dir: 90   Score: 0║\n" +
							"╚══════════════════╝\n";

		// Act
		engine.start();
		String render = out.toString();
		out.reset();
		engine.stop();

		// Assert
		assertEquals(expected, render);
	}

	@Test
	public void ShouldSwitchGameplayVariables() throws GeometricException, GameEngineException, SnakeGameException
	{
		// Arrange
		GameEngine engine = GameEngine.getInstance();
		engine.stop();

		new GameManagerBuilder()
			.setMapWidth(18)
			.setMapHeight(18)
			.setSnakePos(new Point(10, 10))
			.setSnakeSize(3)
			.setTextual(true)
			.setFoodPos(new Point(10, 13))
			.setFoodSize(1)
			.setSnakeDir(Direction.UP)
			.setFoodScore(2)
			.setFoodType(GameManager.FoodType.SQUARE)
			.setFilled(true)
			.setUpdateMethod(GameEngineFlags.UpdateMethod.CODE)
			.setControlMethod(GameManager.ControlMethod.MANUAL)
			.setMapChar(' ')
			.setSnakeHeadChar('x')
			.setSnakeTailChar('o')
			.setFoodChar('f')
			.build();


		ByteArrayOutputStream out = TestUtil.setIOstreams("");

		String expected0 =	"╔══════════════════╗\n" +
							"║                  ║\n" +
							"║                  ║\n" +
							"║                  ║\n" +
							"║                  ║\n" +
							"║          f       ║\n" +
							"║                  ║\n" +
							"║         xxx      ║\n" +
							"║         xxx      ║\n" +
							"║         xxx      ║\n" +
							"║                  ║\n" +
							"║                  ║\n" +
							"║                  ║\n" +
							"║                  ║\n" +
							"║                  ║\n" +
							"║                  ║\n" +
							"║                  ║\n" +
							"║                  ║\n" +
							"║                  ║\n" +
							"║Dir: 90   Score: 0║\n" +
							"╚══════════════════╝\n";
		String expected1 =	"╔══════════════════╗\n" +
							"║      f           ║\n" +
							"║                  ║\n" +
							"║                  ║\n" +
							"║         xxx      ║\n" +
							"║         xxx      ║\n" +
							"║         xxx      ║\n" +
							"║                  ║\n" +
							"║                  ║\n" +
							"║                  ║\n" +
							"║                  ║\n" +
							"║                  ║\n" +
							"║                  ║\n" +
							"║                  ║\n" +
							"║                  ║\n" +
							"║                  ║\n" +
							"║                  ║\n" +
							"║                  ║\n" +
							"║                  ║\n" +
							"║Dir: 90   Score: 2║\n" +
							"╚══════════════════╝\n";
		String expected2 =	"╔══════════════════╗\n" +
							"║      f  xxx      ║\n" +
							"║         xxx      ║\n" +
							"║         xxx      ║\n" +
							"║         ooo      ║\n" +
							"║         ooo      ║\n" +
							"║         ooo      ║\n" +
							"║                  ║\n" +
							"║                  ║\n" +
							"║                  ║\n" +
							"║                  ║\n" +
							"║                  ║\n" +
							"║                  ║\n" +
							"║                  ║\n" +
							"║                  ║\n" +
							"║                  ║\n" +
							"║                  ║\n" +
							"║                  ║\n" +
							"║                  ║\n" +
							"║Dir: 90   Score: 2║\n" +
							"╚══════════════════╝\n";

		// Act
		// engine.start();
		GameManager.getInstance().play();
		String render0 = out.toString();
		out.reset();
		engine.step();
		String render1 = out.toString();
		out.reset();
		engine.step();
		String render2 = out.toString();
		out.reset();
		engine.stop();

		// Assert
		assertEquals(expected0, render0);
		assertEquals(expected1, render1);
		assertEquals(expected2, render2);
	}
}

