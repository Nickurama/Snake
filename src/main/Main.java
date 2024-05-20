import Geometry.*;

import java.util.Random;


import GameEngine.*;
import SnakeGame.*;
import SnakeGame.InputSnakeController.*;
import java.awt.Color;

public class Main
{
    public static void main(String[] args) throws Exception
    {	
		long seed = new Random().nextLong();
		System.out.println("Seed: " + seed);

		testing(seed);
		// defaultExample(seed);
		// colourExample(seed);
		// circleExample(seed);
		// AIExample(seed);
		// dynamicExample(seed);

		GameManager.getInstance().play();
    }

	private static void testing(long seed) throws Exception
	{
		// Polygon poly = new Polygon(new Point[] {
		// 	new Point(175, 0),
		// 	new Point(225, 0),
		// 	new Point(200, 200),
		// });

		Polygon poly = new Polygon(new Point[] {
			new Point(10, 60),
			new Point(10, 100),
			new Point(30, 60),
			new Point(40, 100),
			new Point(50, 60),
			new Point(60, 100),
			new Point(60, 60),
			new Point(40, 50),
			new Point(40, 10),
			new Point(30, 10),
			new Point(30, 50),
		});

		new GameManagerBuilder()
			// .addObstacle(poly)
			.setSeed(seed)
			.setTextual(false)
			.setFilled(true)
			.setMapWidth(500)
			.setMapHeight(500)
			.setSnakeSize(50)
			.setFoodSize(50)
			.setFoodScore(5)
			.setInputPreset(InputPreset.WASD)
			.setFoodType(GameManager.FoodType.CIRCLE)
			.setUpdateMethod(GameEngineFlags.UpdateMethod.STEP)
			.setMaxFps(10)
			.setControlMethod(GameManager.ControlMethod.MANUAL)

			// setting colors
			.setGraphicalBackgroundColour(Color.black)
			.setGraphicalSnakeColour(Color.green)
			.setGraphicalFoodColour(Color.red)
			.setGraphicalObstaclesColour(Color.magenta)

			.build();
	}

	private static void defaultExample(long seed) throws Exception
	{
		new GameManagerBuilder()
			.setSeed(seed)
			.setTextual(true)
			.setFilled(true)
			.setMapWidth(80)
			.setMapHeight(40)
			.setSnakeSize(4)
			.setFoodSize(2)
			.setFoodScore(5)
			.setInputPreset(InputPreset.WASD)
			.setFoodType(GameManager.FoodType.SQUARE)
			.setUpdateMethod(GameEngineFlags.UpdateMethod.STEP)
			.setControlMethod(GameManager.ControlMethod.MANUAL)
			.build();
	}

	private static void colourExample(long seed) throws Exception
	{
		new GameManagerBuilder()
			.setSeed(seed)
			.setTextual(true)
			.setFilled(true)
			.setMapWidth(80)
			.setMapHeight(40)
			.setSnakeSize(4)
			.setFoodSize(2)
			.setFoodScore(5)
			.setInputPreset(InputPreset.WASD)
			.setFoodType(GameManager.FoodType.SQUARE)
			.setUpdateMethod(GameEngineFlags.UpdateMethod.STEP)
			.setControlMethod(GameManager.ControlMethod.MANUAL)

			// setting colors
			.setTerminalBackgroundColour(TerminalColour.Background.BLACK)
			.setTerminalSnakeColour(TerminalColour.Foreground.GREEN)
			.setTerminalFoodColour(TerminalColour.Foreground.RED)
			.setTerminalObstaclesColour(TerminalColour.Foreground.MAGENTA)

			// setting drawing characters
			.setMapChar(' ')
			.setSnakeHeadChar('░')
			.setSnakeTailChar('█')
			.setObstacleChar('▓')
			.setFoodChar('■')
			.build();
	}

	private static void circleExample(long seed) throws Exception
	{
		new GameManagerBuilder()
			.setSeed(seed)
			.setTextual(true)
			.setFilled(false)
			.setMapWidth(90)
			.setMapHeight(30)
			.setSnakeSize(5)
			.setFoodSize(5)
			.setFoodScore(5)
			.setInputPreset(InputPreset.WASD)
			.setFoodType(GameManager.FoodType.CIRCLE)
			.setUpdateMethod(GameEngineFlags.UpdateMethod.STEP)
			.setControlMethod(GameManager.ControlMethod.MANUAL)

			// setting colors
			.setTerminalBackgroundColour(TerminalColour.Background.BLACK)
			.setTerminalSnakeColour(TerminalColour.Foreground.GREEN)
			.setTerminalFoodColour(TerminalColour.Foreground.RED)
			.setTerminalObstaclesColour(TerminalColour.Foreground.MAGENTA)

			// setting drawing characters
			.setMapChar(' ')
			.setSnakeHeadChar('░')
			.setSnakeTailChar('█')
			.setObstacleChar('▓')
			.setFoodChar('■')
			.build();
	}

	private static void AIExample(long seed) throws Exception
	{
		Triangle obstacle0 = new Triangle(new Point[] {
			new Point(38, 40),
			new Point(42, 40),
			new Point(40, 25),
		});

		Triangle obstacle1 = new Triangle(new Point[] {
			new Point(38, 0),
			new Point(42, 0),
			new Point(40, 15),
		});

		new GameManagerBuilder()
			// Global setup
			.setSeed(seed)
			.setTextual(true)
			.setFilled(true)
			.setUpdateMethod(GameEngineFlags.UpdateMethod.STEP)
			.setControlMethod(GameManager.ControlMethod.AUTO)

			// Obstacle setup
			.addObstacle(obstacle0)
			.addObstacle(obstacle1)

			// Map setup
			.setMapWidth(80)
			.setMapHeight(40)

			// Snake setup
			.setSnakeSize(2)
			.setSnakePos(new Point(60.5, 20.5))
			.setSnakeDir(Direction.LEFT)

			// Food setup
			.setFoodSize(2)
			.setFoodScore(5)
			.setFoodPos(new Point(4.5, 4.5))
			.setFoodType(GameManager.FoodType.SQUARE)

			// Colour setup
			.setTerminalBackgroundColour(TerminalColour.Background.BLACK)
			.setTerminalSnakeColour(TerminalColour.Foreground.GREEN)
			.setTerminalFoodColour(TerminalColour.Foreground.RED)
			.setTerminalObstaclesColour(TerminalColour.Foreground.MAGENTA)

			// Character setup
			.setMapChar(' ')
			.setSnakeHeadChar('░')
			.setSnakeTailChar('█')
			.setObstacleChar('▓')
			.setFoodChar('■')

			.build();
	}

	private static void dynamicExample(long seed) throws Exception
	{
		Polygon obstacle0 = new Polygon(new Point[] {
			new Point(40, 24),
			new Point(41, 21),
			new Point(44, 20),
			new Point(41, 19),
			new Point(40, 16),
			new Point(39, 19),
			new Point(36, 20),
			new Point(39, 21),
		});
		float speed0 = -0.5f;


		Square obstacle1 = new Square(new Point[] {
			new Point(39, 38),
			new Point(39, 40),
			new Point(41, 40),
			new Point(41, 38),
		});
		Point rotationPoint1 = new Point(40, 20);
		float speed1 = 0.25f;

		new GameManagerBuilder()
			// Global setup
			.setSeed(seed)
			.setTextual(true)
			.setFilled(true)
			.setMaxScoresDisplay(10)
			.setUpdateMethod(GameEngineFlags.UpdateMethod.STEP)
			.setInputPreset(InputPreset.WASD)
			.setControlMethod(GameManager.ControlMethod.MANUAL)

			// Obstacle setup
			.addObstacle(obstacle0, null, speed0)
			.addObstacle(obstacle1, rotationPoint1, speed1)

			// Map setup
			.setMapWidth(80)
			.setMapHeight(40)

			// Snake setup
			.setSnakeSize(2)
			.setSnakePos(new Point(60.5, 20.5))
			.setSnakeDir(Direction.LEFT)

			// Food setup
			.setFoodSize(2)
			.setFoodScore(5)
			.setFoodPos(new Point(4.5, 4.5))
			.setFoodType(GameManager.FoodType.SQUARE)

			// Colour setup
			.setTerminalBackgroundColour(TerminalColour.Background.BLACK)
			.setTerminalSnakeColour(TerminalColour.Foreground.GREEN)
			.setTerminalFoodColour(TerminalColour.Foreground.RED)
			.setTerminalObstaclesColour(TerminalColour.Foreground.MAGENTA)

			// Character setup
			.setMapChar(' ')
			.setSnakeHeadChar('░')
			.setSnakeTailChar('█')
			.setObstacleChar('▓')
			.setFoodChar('■')

			.build();
	}
}
