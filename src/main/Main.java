import Geometry.*;

import java.util.Random;

import GameEngine.*;
import SnakeGame.*;

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
		System.out.println("Seed: " + seed);
		GameManager.getInstance().init(40, 10, 2, true, true, GameEngineFlags.UpdateMethod.STEP, GameManager.ControlMethod.MANUAL, new Random().nextLong());
		GameManager.getInstance().play();
    }
}
