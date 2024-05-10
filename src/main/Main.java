import Geometry.*;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Random;

import GameEngine.*;
import GameEngine.GameEngineFlags.*;
import SnakeGame.*;
import SnakeGame.InputSnakeController.*;
import SnakeGame.GameManager.*;

public class Main
{
    public static void main(String[] args) throws Exception
    {	
		long seed = new Random().nextLong();
		// long seed = 3947318154746772141L;
		System.out.println("Seed: " + seed);

		// Arrange
		// Polygon obstacle = new Rectangle(new Point(0, 0), new Point(5, 3));
		// Polygon dynamicObstacle = new Rectangle(new Point(30, 4), new Point(31, 5));
		// Point anchor = new Point(31, 4.5);
		// float speed = (float)Math.PI / 2;

		Polygon dynamicObstacle = new Polygon(new Point[]{
			new Point(3, 10),
			new Point(4, 13),
			new Point(8, 6),
			new Point(7, 5),
		});
		Point anchor = new Point(6, 7);
		float speed = 0.1f;

		new GameManagerBuilder()
			// .addObstacle(dynamicObstacle, anchor, speed)
			// .addObstacle(obstacle)
			// .addObstacle(dynamicObstacle, anchor, speed)
			.setInputPreset(InputPreset.WASD)
			// .addObstacle(dynamicObstacle, anchor, speed)
			.setSeed(seed)
			.setMapWidth(80)
			.setMapHeight(40)
			// .setSnakePos(new Point(1, 8))
			.setSnakeSize(4)
			.setTextual(true)
			.setFoodSize(2)
			.setMaxScoresDisplay(10)
			// .setSnakeDir(Snake.Direction.UP)
			// .setFoodPos(new Point(1, 9))
			.setFoodScore(5)
			.setFoodType(GameManager.FoodType.SQUARE)
			.setFilled(true)
			.setUpdateMethod(GameEngineFlags.UpdateMethod.STEP)
			.setControlMethod(GameManager.ControlMethod.AUTO)
			.setMapChar(' ')
			.setSnakeHeadChar('■')
			.setSnakeHeadChar('░')
			.setSnakeTailChar('█')
			.setObstacleChar('▓')
			.setFoodChar('■')
			.setBackgroundColour(Colour.Background.BLACK)
			.setSnakeColour(Colour.Foreground.GREEN)
			.setFoodColour(Colour.Foreground.RED)
			.setObstaclesColour(Colour.Foreground.MAGENTA)
			.build();

		GameManager.getInstance().play();
    }
}
