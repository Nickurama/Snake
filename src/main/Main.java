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
		Polygon poly = new Polygon(new Point[]
		{
			new Point(1, 1),
			new Point(5, 9),
			new Point(21, 9),
			new Point(29, 21),
			new Point(29, 1),
		});
		poly = poly.moveCentroid(new Point(30, 30));

		int startX = 0;
		int startY = 0;
		int width = 60;
		int height = 60;
	
		Character[][] screen = new Character[width][height];
		
		NaturalPoint[] rasterPoints = Renderer.rasterize(poly);

		while (true)
		{
			// clear the console
			System.out.println("\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r");

			// clear the screen
			for (int i = 0; i < height; i++)
				for (int j = 0; j < width; j++)
					screen[i][j] = '-';

			// draw polygon
			for (NaturalPoint rasterPoint : rasterPoints)
				if (rasterPoint.intX() >= startX && rasterPoint.intX() <= width && rasterPoint.intY() >= startY && rasterPoint.intY() <= height)
					screen[rasterPoint.intY()][rasterPoint.intX()] = 'x';

			// print screen
			StringBuilder builder = new StringBuilder();
			for (int i = height - 1; i >= startY; i--)
			{
				for (int j = startX; j < width; j++)
					builder.append(screen[i][j]);
					// System.out.print(screen[i][j]);
				builder.append('\n');
				// System.out.println();
			}
			System.out.println(builder.toString());

			poly = poly.rotate(Math.PI / 5120);
			rasterPoints = Renderer.rasterize(poly);
			Thread.sleep(2);
		}
    }
}
