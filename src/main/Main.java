import Geometry.*;
import GameEngine.*;
import java.lang.reflect.Constructor;
import java.util.Scanner;

/**
 * The class to manage the input and output
 * 
 * @author Diogo Fonseca a79858
 * @version 18/03/2024
 */
public class Main
{
    /**
     * The main method
     * @param args ignored
     */
    public static void main(String[] args) throws Exception
    {	
	//   Polygon poly = new Polygon(new Point[]
		// {
		// 	new Point(1, 1),
		// 	new Point(1, 2),
		// 	new Point(2, 4),
		// 	new Point(2, 10),
		// 	new Point(3, 12),
		// 	new Point(4, 10),
		// 	new Point(4, 4),
		// 	new Point(5, 2),
		// 	new Point(5, 1),
		// 	new Point(4, 2),
		// 	new Point(4, 1),
		// 	new Point(2, 1),
		// 	new Point(2, 2),
		// });
		
		// Polygon poly = new Polygon(new Point[]
		// {
		// 	new Point(1, 1),
		// 	new Point(5, 9),
		// 	new Point(21, 9),
		// 	new Point(29, 21),
		// 	new Point(29, 1),
		// });

		Polygon poly = new Polygon(new Point[]
		{
			new Point(0.6, 0.6),
			new Point(0.6, 1.4),
			new Point(1.4, 1.4),
			new Point(1.4, 0.6),
		});

		poly = poly.moveCentroid(new Point(150, 120));

		Rectangle camera = new Rectangle(new Point[]
		{
			new Point(100, 100),
			new Point(100, 140),
			new Point(250, 140),
			new Point(250, 100),
		});

		Renderer.getInstance().render(poly, camera, '-', 'x');

		// Polygon poly = new Polygon(new Point[]
		// {
		// 	new Point(1, 1),
		// 	new Point(1, 10),
		// 	new Point(5, 14),
		// 	new Point(7, 12),
		// 	new Point(7, 14),
		// 	new Point(8, 14),
		// 	new Point(8, 11),
		// 	new Point(9, 10),
		// 	new Point(9, 1),
		// });

	// 	Polygon poly = new Polygon(new Point[]
	// 	{
	// 		new Point(1, 1),
	// 		new Point(21, 1),
	// 		new Point(21, 21),
	// 		new Point(1, 21),
	// 	});
	//
	//
	// 	int startX = 100;
	// 	int startY = 100;
	// 	int width = 250;
	// 	int height = 140;
	// 
	// 	Character[][] screen = new Character[width + 1][height + 1];
	// 	
	// 	NaturalPoint[] rasterPoints = Renderer.rasterize(poly);
	//
	// 	double xOffset = 0;
	// 	double xDirection = 1;
	// 	double yOffset = 0;
	// 	double yDirection = 1;
	// 	while (true)
	// 	{
	// 		// clear the console
	// 		// System.out.println("\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r");
	//
	// 		// clear the screen
	// 		for (int i = 0; i < width; i++)
	// 			for (int j = 0; j < height; j++)
	// 				screen[i][j] = '-';
	//
	// 		// draw polygon
	// 		for (NaturalPoint rasterPoint : rasterPoints)
	// 			if (rasterPoint.intX() >= startX && rasterPoint.intX() <= width && rasterPoint.intY() >= startY && rasterPoint.intY() <= height)
	// 				screen[rasterPoint.intX()][rasterPoint.intY()] = 'x';
	//
	// 		// print screen
	// 		StringBuilder builder = new StringBuilder();
	// 		for (int i = height - 1; i >= startY; i--)
	// 		{
	// 			for (int j = startX; j < width; j++)
	// 				builder.append(screen[j][i]);
	// 				// System.out.print(screen[i][j]);
	// 			builder.append('\n');
	// 			// System.out.println();
	// 		}
	// 		System.out.println(builder.toString());
	//
	// 		// fun
	// 		xOffset += 0.03 * xDirection;
	// 		if (150 + xOffset > width)
	// 			xDirection = -1;
	// 		if (150 + xOffset < startX)
	// 			xDirection = 1;
	//
	// 		yOffset += -0.02 * yDirection;
	// 		if (120 + yOffset > height)
	// 			yDirection = 1;
	// 		if (120 + yOffset < startY)
	// 			yDirection = -1;
	//
	// 		poly = poly.rotate(- Math.PI / 1000);
	// 		poly = poly.moveCentroid(new Point(150 + xOffset, 120 + yOffset));
	// 		rasterPoints = Renderer.rasterize(poly);
	// 		Thread.sleep(2);
	// 	}
    }
}
