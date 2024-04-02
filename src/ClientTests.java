import java.io.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class ClientTests
{
    @Test
    public void ShouldTakePolygonAsInput()
    {
        // Arrange
        String input0 = "Poligono 6 1 0 0 2 1 4 3 4 4 2 3 0\n";
        String input1 = "8 3";
        ByteArrayOutputStream os = TestUtil.setIOstreams(input0 + input1);
        String expected = "Poligono de 6 vertices: [(7,1), (6,3), (7,5), (9,5), (10,3), (9,1)]";

        // Act
        try
        {
            Client.main(null);
        }
        catch (Exception e) { }
        String output = os.toString();

        // Assert
        assertTrue(output.equals(expected));
    }

    @Test
    public void ShouldTakeTrianglesAsInput()
    {
        // Arrange
        String input0 = "Triangulo 1 0 3 0 2 3\n";
        String input1 = "2 3\n";
        ByteArrayOutputStream os = TestUtil.setIOstreams(input0 + input1);
        String expected = "Triangulo: [(1,2), (3,2), (2,5)]";

        // Act
        try
        {
            Client.main(null);
        }
        catch (Exception e) { }
        String output = os.toString();

        // Assert
        assertTrue(output.equals(expected));
    }

    @Test
    public void ShouldTakeRectanglesAsInput()
    {
        // Arrange
        String input0 = "Retangulo 1 1 5 1 5 3 1 3\n";
        String input1 = "3 3\n";
        ByteArrayOutputStream os = TestUtil.setIOstreams(input0 + input1);
        String expected = "Retangulo: [(1,2), (5,2), (5,4), (1,4)]";

        // Act
        try
        {
            Client.main(null);
        }
        catch (Exception e) { }
        String output = os.toString();

        // Assert
        assertTrue(output.equals(expected));
    }

    @Test
    public void ShouldTakeSquareAsInput()
    {
        // Arrange
        String input0 = "Quadrado 0 0 0 2 2 2 2 0\n";
        String input1 = "1 4\n";
        ByteArrayOutputStream os = TestUtil.setIOstreams(input0 + input1);
        String expected = "Quadrado: [(0,3), (0,5), (2,5), (2,3)]";

        // Act
        try
        {
            Client.main(null);
        }
        catch (Exception e) { }
        String output = os.toString();

        // Assert
        assertTrue(output.equals(expected));
    }

    @Test
    public void ShouldBeCaseInsensitive()
    {
        // Arrange
        String input0 = "QuAdRaDo 0 0 0 2 2 2 2 0\n";
        String input1 = "1 4\n";
        ByteArrayOutputStream os = TestUtil.setIOstreams(input0 + input1);
        String expected = "Quadrado: [(0,3), (0,5), (2,5), (2,3)]";


        // Act
        try
        {
            Client.main(null);
        }
        catch (Exception e) { }
        String output = os.toString();

        // Assert
        assertTrue(output.equals(expected));
    }
}
