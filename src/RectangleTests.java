import static org.junit.jupiter.api.Assertions.*;

import java.text.ParseException;

import org.junit.jupiter.api.Test;

public class RectangleTests
{
    @Test
    public void ShouldIncludeNameInString() throws GeometricException
    {
        // Arrange
        Rectangle s = new Rectangle(new Point[] {
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
    public void ShouldTakeStringInConstructor() throws GeometricException, ParseException
    {
        // Arrange
        String str = "1 1 1 2 4 2 4 1";
        String expected = "Retangulo: [(1,1), (1,2), (4,2), (4,1)]";

        // Act
        Rectangle r = new Rectangle(str);
        String gen = r.toString();

        // Assert
        assertTrue(gen.equals(expected));
    }

    @Test
    public void ShouldRotate() throws GeometricException
    {
        // Arrange
        Rectangle rect = new Rectangle(new Point[] {
            new Point(1, 1),
            new Point(3, 1),
            new Point(3, 5),
            new Point(1, 5),
        });
        Rectangle expected = new Rectangle(new Point[] {
            new Point(4, 2),
            new Point(4, 4),
            new Point(0, 4),
            new Point(0, 2),
        });

        // Act
        Rectangle rotated0 = rect.rotate(Math.PI / 2);
        Rectangle rotated1 = rect.rotateDegrees(90);

        // Assert
        assertTrue(rotated0.equals(expected));
        assertTrue(rotated1.equals(expected));
    }

    @Test
    public void ShouldTranslate() throws GeometricException
    {
        // Assert
        Rectangle rect = new Rectangle(new Point[] {
            new Point(1, 1),
            new Point(1, 3),
            new Point(2, 3),
            new Point(2, 1),
        });
        Rectangle expected = new Rectangle(new Point[] {
            new Point(0, 0),
            new Point(0, 2),
            new Point(1, 2),
            new Point(1, 0),
        });

        // Act
        Rectangle translated = rect.translate(new Vector(-1, -1));

        // Arrange
        assertTrue(translated.equals(expected));
    }

    @Test
    public void ShouldMoveCentroid() throws GeometricException
    {
        // Assert
        Rectangle rect = new Rectangle(new Point[] {
            new Point(1, 1),
            new Point(1, 3),
            new Point(2, 3),
            new Point(2, 1),
        });
        Point newCentroid = new Point(2, 2);
        Rectangle expected = new Rectangle(new Point[] {
            new Point(1.5, 1),
            new Point(1.5, 3),
            new Point(2.5, 3),
            new Point(2.5, 1),
        });

        // Act
        Rectangle moved = rect.moveCentroid(newCentroid);

        // Arrange
        assertTrue(moved.equals(expected));
    }

	@Test
	public void ShouldThrowExceptionIfNotRectangle() throws GeometricException
	{
		// Arrange
		Point[] points0 = new Point[] {
			new Point(0, 0),
			new Point(1, 4),
			new Point(5, 4),
			new Point(4, 0),
		};
		Point[] points1 = new Point[] {
			new Point(0, 0),
			new Point(0, 4),
			new Point(4, 4),
			new Point(4, 0),
			new Point(2, 1),
		};

		// Act
		// Assert
		assertThrows(GeometricException.class, () -> new Rectangle(points0));
		assertThrows(GeometricException.class, () -> new Rectangle(points1));
	}
}
