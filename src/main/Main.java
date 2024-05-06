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
		GameManager.resetInstance();
		if (GameEngine.getInstance().isRunning())
			GameEngine.getInstance().stop();

		GameManager gameManager = GameManager.getInstance();
		gameManager.init(40, 12, new Point(13.5, 5.5), null, 4, true, new Point(25, 11), 1, FoodType.CIRCLE, 1,
			true, UpdateMethod.STEP, ControlMethod.MANUAL, 137);


		gameManager.play();

		// long seed = 7208301651677690248L;
		// // long seed = new Random().nextLong();
		// System.out.println("Seed: " + seed);
		// GameManager.getInstance().init(39, 9, 3, false, 2, FoodType.SQUARE, 1, true, UpdateMethod.STEP, ControlMethod.MANUAL, seed);
		// GameManager.getInstance().play();
    }
}
