import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class TriangleTests
{
    @Test
    public void ShouldIncludeNameInString()
    {
        // Arrange
        Triangle s = new Triangle(new Point[] {
            new Point(1, 1),
            new Point(3, 1),
            new Point(2, 2),
        });
        String expected = "Triangulo: [(1,1), (3,1), (2,2)]";

        // Act
        String str = s.toString();

        // Assert
        assertTrue(str.equals(expected));
    }

    @Test
    public void ShouldTakeStringInConstructor()
    {
        // Arrange
        String str = "7 1 9 1 9 3";
        String expected = "Triangulo: [(7,1), (9,1), (9,3)]";

        // Act
        Triangle t = new Triangle(str);
        String gen = t.toString();

        // Assert
        assertTrue(gen.equals(expected));

    }

    @Test
    public void ShouldRotate()
    {
        // Arrange
        Triangle tri = new Triangle(new Point[] {
            new Point(2, 1),
            new Point(4, 1),
            new Point(3, 4),
        });
        Triangle expected = new Triangle(new Point[] {
            new Point(4, 3),
            new Point(2, 3),
            new Point(3, 0),
        });

        // Act
        Triangle rotated0 = tri.rotate(Math.PI);
        Triangle rotated1 = tri.rotateDegrees(180);

        // Assert
        assertTrue(rotated0.equals(expected));
        assertTrue(rotated1.equals(expected));
    }

    @Test
    public void ShouldTranslate()
    {
        // Assert
        Triangle tri = new Triangle(new Point[] {
            new Point(1, 1),
            new Point(2, 2),
            new Point(3, 1),
        });
        Triangle expected = new Triangle(new Point[] {
            new Point(0, 0),
            new Point(1, 1),
            new Point(2, 0),
        });

        // Act
        Triangle translated = tri.translate(new Vector(-1, -1));

        // Arrange
        assertTrue(translated.equals(expected));
    }

    @Test
    public void ShouldMoveCentroid()
    {
        // Assert
        Triangle tri = new Triangle(new Point[] {
            new Point(1, 1),
            new Point(2, 2),
            new Point(3, 1),
        });
        Point newCentroid = new Point(2, 3.3333333333);
        Triangle expected = new Triangle(new Point[] {
            new Point(1, 3),
            new Point(2, 4),
            new Point(3, 3),
        });

        // Act
        Triangle moved = tri.moveCentroid(newCentroid);

        // Arrange
        assertTrue(moved.equals(expected));
    }
}
