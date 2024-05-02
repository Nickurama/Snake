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
    public static void main(String[] args) throws Exception
    {	
		Polygon poly = new Polygon(new Point[] {
			new Point(1, 1),
			new Point(1, 2),
			new Point(2, 2),
			new Point(2, 1),
		});
		System.out.println(poly.intersectsInclusive(poly));
    }
}
