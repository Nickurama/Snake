import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class RetanguloTests
{
    @Test
    public void ShouldIncludeNameInString()
    {
        // Arrange
        Retangulo s = new Retangulo(new Point[] {
            new Point(1, 1),
            new Point(1, 2),
            new Point(5, 2),
            new Point(5, 1),
        });
        String expected = "Retangulo: [(1,1), (1,2), (5,2), (5,1)]";

        // Act
        String str = s.toString();

        // Assert
        assertTrue(str.equals(expected));
    }

    @Test
    public void ShouldTakeStringInConstructor()
    {
        // Arrange
        String str = "1 1 1 2 4 2 4 1";
        String expected = "Retangulo: [(1,1), (1,2), (4,2), (4,1)]";

        // Act
        Retangulo r = new Retangulo(str);
        String gen = r.toString();

        // Assert
        assertTrue(gen.equals(expected));
    }

    @Test
    public void ShouldRotate()
    {
        // Arrange
        Retangulo rect = new Retangulo(new Point[] {
            new Point(1, 1),
            new Point(3, 1),
            new Point(3, 5),
            new Point(1, 5),
        });
        Retangulo expected = new Retangulo(new Point[] {
            new Point(4, 2),
            new Point(4, 4),
            new Point(0, 4),
            new Point(0, 2),
        });

        // Act
        Retangulo rotated0 = rect.rotate(Math.PI / 2);
        Retangulo rotated1 = rect.rotateDegrees(90);

        // Assert
        assertTrue(rotated0.equals(expected));
        assertTrue(rotated1.equals(expected));
    }

    @Test
    public void ShouldTranslate()
    {
        // Assert
        Retangulo rect = new Retangulo(new Point[] {
            new Point(1, 1),
            new Point(1, 3),
            new Point(2, 3),
            new Point(2, 1),
        });
        Retangulo expected = new Retangulo(new Point[] {
            new Point(0, 0),
            new Point(0, 2),
            new Point(1, 2),
            new Point(1, 0),
        });

        // Act
        Retangulo translated = rect.translate(new Vector(-1, -1));

        // Arrange
        assertTrue(translated.equals(expected));
    }

    @Test
    public void ShouldMoveCentroid()
    {
        // Assert
        Retangulo rect = new Retangulo(new Point[] {
            new Point(1, 1),
            new Point(1, 3),
            new Point(2, 3),
            new Point(2, 1),
        });
        Point newCentroid = new Point(2, 2);
        Retangulo expected = new Retangulo(new Point[] {
            new Point(1.5, 1),
            new Point(1.5, 3),
            new Point(2.5, 3),
            new Point(2.5, 1),
        });

        // Act
        Retangulo moved = rect.moveCentroid(newCentroid);

        // Arrange
        assertTrue(moved.equals(expected));
    }
}
