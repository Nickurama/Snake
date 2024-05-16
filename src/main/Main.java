import Geometry.*;

import java.util.Random;


import GameEngine.*;
import SnakeGame.*;
import SnakeGame.InputSnakeController.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.*;

public class Main
{
	public static class TestPane extends JPanel
	{
		private BufferedImage img;

		public TestPane()
		{
			img = new BufferedImage(1000, 1000, BufferedImage.TYPE_INT_RGB);

			Graphics2D g2d = img.createGraphics();
            g2d.setColor(Color.WHITE);
            g2d.fillRect(0, 0, img.getWidth(), img.getHeight());
            g2d.dispose();
			// for (int x = 0; x < img.getWidth(); x++)
			// 	for (int y = 0; y < img.getHeight(); y++)
			// 		img.setRGB(x, y, Color.RED.getRGB());
		}

		public void draw(int x, int y)
		{
			draw(x, y, Color.BLACK);
		}

		public void draw(int x, int y, Color color)
		{
			// if (x < 0 || x >= img.getWidth() || y < 0 || y >= img.getHeight())
			// 	return;
			img.setRGB(x, y, color.getRGB());
		}

		int x = 0;

		@Override
		public void paintComponent(Graphics graphics)
		{
			super.paintComponents(graphics);
			// graphics.setColor(Color.GREEN);
			// graphics.fillRect(0, 0, 1000, 1000);

			for (int i = 0; i < 1000; i++)
				for (int j = 0; j < 1000; j++)
					draw(i, j, Color.RED);
			System.out.println("called " + (x++) + " times.");

			Graphics2D g2d = (Graphics2D) graphics.create();
			g2d.drawImage(img, 0, 0, this);
			g2d.dispose();
		}
	}

    public static void main(String[] args) throws Exception
    {	
		JFrame frame = new JFrame("Testing");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		TestPane pane = new TestPane();
		frame.add(pane);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setSize(1000, 1000);
		frame.setVisible(true);


		Random r = new Random();
		int x = 500;
		int y = 500;
		long ns = System.nanoTime();
		while(true)
		{
			// if (x >= 500)
			// {
			// 	// x = 0;
			// 	for (int i = x - 10; i < x + 10; i++)
			// 		for (int j = y - 10; j < y + 10; j++)
			// 			pane.draw(i, j, Color.RED);
			// 	System.out.println("Right side won");
			// 	break;
			// }
			// if (x < 0)
			// {
			// 	// x = 499;
			// 	for (int i = x - 10; i < x + 10; i++)
			// 		for (int j = y - 10; j < y + 10; j++)
			// 			pane.draw(i, j, Color.RED);
			// 	System.out.println("Left side won");
			// 	break;
			// }
			// if (y >= 500)
			// {
			// 	// y = 0;
			// 	for (int i = x - 10; i < x + 10; i++)
			// 		for (int j = y - 10; j < y + 10; j++)
			// 			pane.draw(i, j, Color.RED);
			// 	System.out.println("Down side won");
			// 	break;
			// }
			// if (y < 0)
			// {
			// 	// y = 499;
			// 	for (int i = x - 10; i < x + 10; i++)
			// 		for (int j = y - 10; j < y + 10; j++)
			// 			pane.draw(i, j, Color.RED);
			// 	System.out.println("Up side won");
			// 	break;
			// }

			// int size = 500;
			// for (int i = x - size; i < x + size; i++)
			// 	for (int j = y - size; j < y + size; j++)
			// 		pane.draw(i, j);

			// pane.draw(x, y);
			boolean increment = r.nextBoolean();

			long newNs = System.nanoTime();
			long deltaNs = newNs - ns;
			ns = newNs;
			double deltaS = (double)deltaNs / 1000000000;
			double fps = 1 / deltaS;
			System.out.println(fps);

			if (increment)
			{
				if (r.nextBoolean())
					x++;
				else
					x--;
			}

			increment = r.nextBoolean();
			if (increment)
			{
				if (r.nextBoolean())
					y++;
				else
					y--;
			}

			// for (int i = 0; i < 1000; i++)
			// 	for (int j = 0; j < 1000; j++)
			// 		pane.draw(i, j, Color.RED);
			frame.repaint();
			// Thread.sleep(0, 100000);
		}




		// long seed = new Random().nextLong();
		// System.out.println("Seed: " + seed);
		//
		// defaultExample(seed);
		// // colourExample(seed);
		// // circleExample(seed);
		// // AIExample(seed);
		// // dynamicExample(seed);
		//
		// GameManager.getInstance().play();
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
			.setBackgroundColour(Colour.Background.BLACK)
			.setSnakeColour(Colour.Foreground.GREEN)
			.setFoodColour(Colour.Foreground.RED)
			.setObstaclesColour(Colour.Foreground.MAGENTA)

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
			.setBackgroundColour(Colour.Background.BLACK)
			.setSnakeColour(Colour.Foreground.GREEN)
			.setFoodColour(Colour.Foreground.RED)
			.setObstaclesColour(Colour.Foreground.MAGENTA)

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
			.setBackgroundColour(Colour.Background.BLACK)
			.setSnakeColour(Colour.Foreground.GREEN)
			.setFoodColour(Colour.Foreground.RED)
			.setObstaclesColour(Colour.Foreground.MAGENTA)

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
			.setBackgroundColour(Colour.Background.BLACK)
			.setSnakeColour(Colour.Foreground.GREEN)
			.setFoodColour(Colour.Foreground.RED)
			.setObstaclesColour(Colour.Foreground.MAGENTA)

			// Character setup
			.setMapChar(' ')
			.setSnakeHeadChar('░')
			.setSnakeTailChar('█')
			.setObstacleChar('▓')
			.setFoodChar('■')

			.build();
	}
}
