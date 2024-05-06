import Geometry.*;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Random;

import GameEngine.*;
import GameEngine.GameEngineFlags.*;
import SnakeGame.*;
import SnakeGame.Snake.*;
import SnakeGame.GameManager.*;

/**
 * The class to manage the input and output
 * 
 * @author Diogo Fonseca a79858
 * @version 18/03/2024
 */
public class Main
{
    public static void main(String[] args) throws Exception
    {	
		long seed = new Random().nextLong();

		new GameManagerBuilder()
			.setSeed(seed)
			.setMapWidth(40)
			.setMapHeight(10)
			.setSnakePos(new Point(1, 8))
			.setSnakeSize(1)
			.setTextual(true)
			.setFoodSize(1)
			.setSnakeDir(Snake.Direction.UP)
			.setFoodPos(new Point(1, 9))
			.setFoodScore(5)
			.setFoodType(GameManager.FoodType.SQUARE)
			.setFilled(true)
			.setUpdateMethod(GameEngineFlags.UpdateMethod.STEP)
			.setControlMethod(GameManager.ControlMethod.MANUAL)
			.setMapChar(' ')
			.setSnakeHeadChar('h')
			.setSnakeTailChar('t')
			.setObstacleChar('-')
			.setFoodChar('F')
			.build();

		GameManager.getInstance().play();

		// System.out.println("Seed: " + seed);
		// GameManager.getInstance().init(39, 9, 3, false, 2, FoodType.SQUARE, 1, true, UpdateMethod.STEP, ControlMethod.MANUAL, seed);
		// GameManager.getInstance().play();
    }
}
