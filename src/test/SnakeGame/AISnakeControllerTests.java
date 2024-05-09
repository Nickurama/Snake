package SnakeGame;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

import GameEngine.*;
import Geometry.*;

public class AISnakeControllerTests
{
	public static final long SEED = 137;

	@Test
	public void ShouldGetShortestPath() throws SnakeGameException, GeometricException, GameEngineException
	{
		// Arrange
		GameEngine.getInstance().stop();
		Polygon obstacle = new Rectangle(new Point(59.7, 0), new Point(60.3, 14));

		new GameManagerBuilder()
			.addObstacle(obstacle)
			.setSeed(SEED)
			.setMapWidth(120)
			.setMapHeight(30)
			.setSnakePos(new Point(117, 2))
			.setSnakeSize(5)
			.setTextual(true)
			.setFoodSize(4)
			.setMaxScoresDisplay(10)
			.setSnakeDir(Direction.UP)
			.setFoodPos(new Point(1.5, 1.5))
			.setFoodScore(5)
			.setFoodType(GameManager.FoodType.CIRCLE)
			.setFilled(true)
			.setUpdateMethod(GameEngineFlags.UpdateMethod.CODE)
			.setControlMethod(GameManager.ControlMethod.AUTO)
			.setMapChar(' ')
			.setSnakeHeadChar('H')
			.setSnakeTailChar('T')
			.setObstacleChar('O')
			.setFoodChar('F')
			.build();

		GameManager.getInstance().play();
		GameEngine engine = GameEngine.getInstance();
		Point initialFoodPos = GameManager.getInstance().foodPos();

		TestUtil.TestUtil.setIOstreams("");

		int expectedMoves = 28;
		
		// Act
		for (int i = 0; i < expectedMoves; i++)
			engine.step();
		Point before = GameManager.getInstance().foodPos();
		engine.step();
		Point after = GameManager.getInstance().foodPos();

		// Assert
		assertEquals(initialFoodPos, before);
		assertNotEquals(initialFoodPos, after);
	}
}
