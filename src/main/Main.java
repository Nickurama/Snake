import Geometry.*;

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
		long seed = 7208301651677690248L;
		// long seed = new Random().nextLong();
		System.out.println("Seed: " + seed);
		GameManager.getInstance().init(39, 9, 3, false, 2, FoodType.SQUARE, 1, true, UpdateMethod.STEP, ControlMethod.MANUAL, seed);
		GameManager.getInstance().play();
    }
}
