import java.lang.reflect.Constructor;
import java.util.Scanner;

/**
 * The class to manage the input and output
 * 
 * @author Diogo Fonseca a79858
 * @version 18/03/2024
 */
public class Client
{
    /**
     * The main method
     * @param args ignored
     */
    public static void main(String[] args) throws Exception
    {
        Scanner sc = new Scanner(System.in);
        String polyStr = sc.nextLine();
        String centroidStr = sc.nextLine();
        String[] polyTokens = polyStr.split(" ", 2);
        String[] centroidTokens = centroidStr.split(" ");
        
        try
        {
            Class<?> cl = Class.forName(capital(polyTokens[0]));
            Constructor<?> constructor = cl.getConstructor(String.class);
            Poligono poly = (Poligono) constructor.newInstance(polyTokens[1]);
            Point centroid = new Point(Double.parseDouble(centroidTokens[0]), Double.parseDouble(centroidTokens[1]));
            System.out.println(poly.moveCentroid(centroid).toString());
        }
        catch (ClassNotFoundException cnfe)
        {
            System.out.println("Não foi encontrada a classe: " + cnfe.getMessage());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            sc.close();
        }
    }

    /**
     * Capitalizes a string
     * @param s the string to capitalize
     * @return the capitalized string
     */
    public static String capital(String s)
    {
        if (s == null || s.isEmpty())
            return s;
        return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
    }
}
