import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class QuadradoTests
{
    @Test
    public void ShouldIncludeNameInString()
    {
        // Arrange
        Quadrado s = new Quadrado(new Point[] {
            new Point(1, 1),
            new Point(1, 2),
            new Point(2, 2),
            new Point(2, 1),
        });
        String expected = "Quadrado: [(1,1), (1,2), (2,2), (2,1)]";

        // Act
        String str = s.toString();

        // Assert
        assertTrue(str.equals(expected));
    }

    @Test
    public void ShouldTakeStringInConstructor()
    {
        // Arrange
        String str = "1 1 1 2 2 2 2 1";
        String expected = "Quadrado: [(1,1), (1,2), (2,2), (2,1)]";

        // Act
        Quadrado s = new Quadrado(str);
        String gen = s.toString();

        // Assert
        assertTrue(gen.equals(expected));
    }

    @Test
    public void ShouldRotate()
    {
        // Arrange
        Quadrado sq = new Quadrado(new Point[] {
            new Point(1, 1),
            new Point(1, 2),
            new Point(2, 2),
            new Point(2, 1),
        });
        Quadrado expected = new Quadrado(new Point[] {
            new Point(2, 1),
            new Point(1, 1),
            new Point(1, 2),
            new Point(2, 2),
        });

        // Act
        Quadrado rotated0 = sq.rotate(Math.PI / 2);
        Quadrado rotated1 = sq.rotateDegrees(90);

        // Assert
        assertTrue(rotated0.equals(expected));
        assertTrue(rotated1.equals(expected));
    }

    @Test
    public void ShouldTranslate()
    {
        // Assert
        Quadrado sq = new Quadrado(new Point[] {
            new Point(1, 1),
            new Point(1, 2),
            new Point(2, 2),
            new Point(2, 1),
        });
        Quadrado expected = new Quadrado(new Point[] {
            new Point(0, 0),
            new Point(0, 1),
            new Point(1, 1),
            new Point(1, 0),
        });

        // Act
        Quadrado translated = sq.translate(new Vector(-1, -1));

        // Arrange
        assertTrue(translated.equals(expected));
    }

    @Test
    public void ShouldMoveCentroid()
    {
        // Assert
        Quadrado sq = new Quadrado(new Point[] {
            new Point(1, 1),
            new Point(1, 2),
            new Point(2, 2),
            new Point(2, 1),
        });
        Point newCentroid = new Point(4, 5);
        Quadrado expected = new Quadrado(new Point[] {
            new Point(3.5, 4.5),
            new Point(4.5, 4.5),
            new Point(4.5, 5.5),
            new Point(3.5, 5.5),
        });

        // Act
        Quadrado moved = sq.moveCentroid(newCentroid);

        // Arrange
        assertTrue(moved.equals(expected));
    }
}
