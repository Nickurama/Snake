package GeometryTests;

import Geometry.*;
import static org.junit.jupiter.api.Assertions.*;
import java.text.ParseException;
import org.junit.jupiter.api.Test;

public class SquareTests
{
    @Test
    public void ShouldIncludeNameInString() throws GeometricException
    {
        // Arrange
        Square s = new Square(new Point[] {
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
    public void ShouldTakeStringInConstructor() throws GeometricException, ParseException
    {
        // Arrange
        String str = "1 1 1 2 2 2 2 1";
        String expected = "Quadrado: [(1,1), (1,2), (2,2), (2,1)]";

        // Act
        Square s = new Square(str);
        String gen = s.toString();

        // Assert
        assertTrue(gen.equals(expected));
    }

    @Test
    public void ShouldRotate() throws GeometricException
    {
        // Arrange
        Square sq = new Square(new Point[] {
            new Point(1, 1),
            new Point(1, 2),
            new Point(2, 2),
            new Point(2, 1),
        });
        Square expected = new Square(new Point[] {
            new Point(2, 1),
            new Point(1, 1),
            new Point(1, 2),
            new Point(2, 2),
        });

        // Act
        Square rotated0 = sq.rotate(Math.PI / 2);
        Square rotated1 = sq.rotateDegrees(90);

        // Assert
        assertTrue(rotated0.equals(expected));
        assertTrue(rotated1.equals(expected));
    }

    @Test
    public void ShouldTranslate() throws GeometricException
    {
        // Assert
        Square sq = new Square(new Point[] {
            new Point(1, 1),
            new Point(1, 2),
            new Point(2, 2),
            new Point(2, 1),
        });
        Square expected = new Square(new Point[] {
            new Point(0, 0),
            new Point(0, 1),
            new Point(1, 1),
            new Point(1, 0),
        });

        // Act
        Square translated = sq.translate(new Vector(-1, -1));

        // Arrange
        assertTrue(translated.equals(expected));
    }

    @Test
    public void ShouldMoveCentroid() throws GeometricException
    {
        // Assert
        Square sq = new Square(new Point[] {
            new Point(1, 1),
            new Point(1, 2),
            new Point(2, 2),
            new Point(2, 1),
        });
        Point newCentroid = new Point(4, 5);
        Square expected = new Square(new Point[] {
            new Point(3.5, 4.5),
            new Point(4.5, 4.5),
            new Point(4.5, 5.5),
            new Point(3.5, 5.5),
        });

        // Act
        Square moved = sq.moveCentroid(newCentroid);

        // Arrange
        assertTrue(moved.equals(expected));
    }

	@Test
	public void ShouldThrowExceptionWhenNotSquare() throws GeometricException
	{
		// Assert
		Point[] points0 = new Point[] {
			new Point(1, 1),
			new Point(2, 2),
			new Point(2 + Math.sqrt(2), 2),
			new Point(1 + Math.sqrt(2), 1),
		};
		Point[] points1 = new Point[] {
			new Point(0, 0),
			new Point(0, 1),
			new Point(1, 1),
			new Point(1, 0),
			new Point(0.5, 0.5),
		};
		Point[] points2 = new Point[] {
			new Point(0, 0),
			new Point(0, 2),
			new Point(1, 2),
			new Point(1, 0),
		};

		// Act
		// Assert
		assertThrows(GeometricException.class, () -> new Square(points0));
		assertThrows(GeometricException.class, () -> new Square(points1));
		assertThrows(GeometricException.class, () -> new Square(points2));
	}
}
