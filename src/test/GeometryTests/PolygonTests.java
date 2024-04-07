package GeometryTests;

import Geometry.*;
import static org.junit.jupiter.api.Assertions.*;
import java.text.ParseException;
import org.junit.jupiter.api.Test;

public class PolygonTests
{
    @Test
    public void ShouldNotInterceptWhenPolygonsAreSeparated() throws GeometricException
    {
        // Arrange
        Polygon poly1 = new Polygon(new Point[] {
                new Point(3, 1),
                new Point(4, 1),
                new Point(4, 2),
                new Point(3, 2),
        });
        Polygon poly2 = new Polygon(new Point[] {
                new Point(1, 1),
                new Point(2, 1),
                new Point(2, 2),
                new Point(1, 2),
        });

        // Act
        boolean intercepts = poly1.intersects(poly2);

        // Assert
        assertFalse(intercepts);
    }

    @Test
    public void ShouldNotInterceptWhenPointsInPolygonsOverlap() throws GeometricException
    {
        // Arrange
        Polygon poly1 = new Polygon(new Point[] {
                new Point(1, 1),
                new Point(2, 1),
                new Point(2, 2),
                new Point(1, 2),
        });
        Polygon poly2 = new Polygon(new Point[] {
                new Point(1, 3),
                new Point(3, 1),
                new Point(3, 3),
        });

        // Act
        boolean intercepts = poly1.intersects(poly2);

        // Assert
        assertFalse(intercepts);
    }

    @Test
    public void ShouldNotInterceptWhenPolygonSegmentsOverlap() throws GeometricException
    {
        // Arrange
        Polygon poly1 = new Polygon(new Point[] {
                new Point(1, 1),
                new Point(3, 1),
                new Point(1, 3),
        });
        Polygon poly2 = new Polygon(new Point[] {
                new Point(1, 3),
                new Point(3, 1),
                new Point(3, 3),
        });

        // Act
        boolean intercepts = poly1.intersects(poly2);

        // Assert
        assertFalse(intercepts);
    }

    @Test
    public void ShouldInterceptWhenSegmentOfPolygonIntercepts() throws GeometricException
    {
        // Arrange
        Polygon poly1 = new Polygon(new Point[] {
                new Point(1, 1),
                new Point(4, 1),
                new Point(4, 4),
                new Point(1, 4),
        });
        Polygon poly2 = new Polygon(new Point[] {
                new Point(3, 3),
                new Point(5, 3),
                new Point(5, 5),
                new Point(3, 5),
        });

        // Act
        boolean intercepts = poly1.intersects(poly2);

        // Assert
        assertTrue(intercepts);
    }

    @Test
    public void ShouldInterceptWhenSegmentIntercepts() throws GeometricException
    {
        // Arrange
        Polygon poly = new Polygon(new Point[] {
            new Point(1, 1),
            new Point(3, 1),
            new Point(2, 3),
        });
        LineSegment segment = new LineSegment(new Point(2, 2), new Point(3, 3));

        // Act
        boolean intercepts = poly.intersects(segment);

        // Assert
        assertTrue(intercepts);
    }

    @Test
    public void ShouldNotInterceptWhenSegmentOverlaps() throws GeometricException
    {
        // Arrange
        Polygon poly = new Polygon(new Point[] {
            new Point(2, 1),
            new Point(4, 1),
            new Point(4, 3),
            new Point(2, 3),
        });
        LineSegment segment = new LineSegment(new Point(1, 3), new Point(5, 3));

        // Act
        boolean intercepts = poly.intersects(segment);

        // Assert
        assertFalse(intercepts);
    }

    @Test
    public void ShouldNotInterceptWhenPointsOnSegmentOverlap() throws GeometricException
    {
        // Arrange
        Polygon poly = new Polygon(new Point[] {
            new Point(2, 1),
            new Point(4, 1),
            new Point(4, 3),
            new Point(2, 3),
        });
        LineSegment segment = new LineSegment(new Point(5, 4), new Point(4, 3));

        // Act
        boolean intercepts = poly.intersects(segment);

        // Assert
        assertFalse(intercepts);
    }

    @Test
    public void ShouldInterceptOnLastSide() throws GeometricException
    {
        // Arrange
        Polygon poly = new Polygon(new Point[] {
            new Point(2, 4),
            new Point(2, 1),
            new Point(5, 1),
        });
        LineSegment segment = new LineSegment(new Point(3, 0), new Point(3, 2));

        // Act
        boolean intercepts = poly.intersects(segment);

        // Assert
        assertTrue(intercepts);
    }

    @Test
    public void ShouldCalculatePerimeter() throws GeometricException
    {
        // Arrange
        Polygon poly0 = new Polygon(new Point[] {
            new Point(0, 0),
            new Point(0, 1),
            new Point(1, 1),
            new Point(1, 0),
        });
        double expected0 = 4;

        Polygon poly1 = new Polygon(new Point[] {
            new Point(0, 0),
            new Point(2, 2),
            new Point(0, 4),
            new Point(0, 3),
            new Point(1, 2),
            new Point(0, 1),
        });
        double expected1 = 10.4852813742;

        Polygon poly2 = new Polygon(new Point[] {
            new Point(0, 2),
            new Point(2, 2),
            new Point(6, 6),
            new Point(6, 8),
            new Point(8, 6),
            new Point(2, 0),
        });
        double expected2 = 23.7989898732;

        Polygon poly3 = new Polygon(new Point[] {
            new Point(0, 0),
            new Point(2, 0),
            new Point(1, 1),
            new Point(1, 2),
            new Point(2, 5),
            new Point(0, 5),
            new Point(1, 4),
            new Point(1, 3),
        });
        double expected3 = 15.1529824451;

        Polygon poly4 = new Polygon(new Point[] {
            new Point(0, 0),
            new Point(1, 1),
            new Point(2, 1),
            new Point(0, 2),
        });
        double expected4 = 6.65028153987;

        // Act
        double obtained0 = poly0.perimeter();
        double obtained1 = poly1.perimeter();
        double obtained2 = poly2.perimeter();
        double obtained3 = poly3.perimeter();
        double obtained4 = poly4.perimeter();

        // Assert
        assertTrue(MathUtil.areEqual(obtained0, expected0));
        assertTrue(MathUtil.areEqual(obtained1, expected1));
        assertTrue(MathUtil.areEqual(obtained2, expected2));
        assertTrue(MathUtil.areEqual(obtained3, expected3));
        assertTrue(MathUtil.areEqual(obtained4, expected4));
    }

    @Test
    public void ShouldBeImmutable() throws GeometricException
    {
        // Arrange
        Point p0 = new Point(1, 1);
        Point p1 = new Point(1, 2);
        Point p2 = new Point(2, 2);
        Point p3 = new Point(2, 1);
        Polygon poly = new Polygon(new Point[] { p0, p1, p2, p3 });
        LineSegment segment = new LineSegment(new Point(0, 4), new Point(3, 0));

        // Act
        p0 = new Point(0, 0);
        p1 = new Point(0, 0);
        p2 = new Point(0, 0);
        p3 = new Point(0, 0);

        // Assert
        assertTrue(poly.intersects(segment));
    }

    @Test
    public void ShouldIncludeAllVerticesInString() throws GeometricException
    {
        Polygon poly = new Polygon(new Point[] {
            new Point(3, 5),
            new Point(9, 312),
            new Point(512, 1024),
            new Point(7, 1.5),
            new Point(0, 0),
            new Point(0, 12),
        });
        String expected = "Poligono de 6 vertices: [(3,5), (9,312), (512,1024), (7,1), (0,0), (0,12)]";

        // Act
        String str = poly.toString();

        // Arrange
        assertTrue(str.equals(expected));
    }

    @Test
    public void ShouldTakeConstructorWithString() throws GeometricException, ParseException
    {
        // Arrange
        Polygon poly = new Polygon("5 1 1 1 3 2 5 7 2 5 1");
        String expected = "Poligono de 5 vertices: [(1,1), (1,3), (2,5), (7,2), (5,1)]";

        // Act
        String polyString = poly.toString();

        // Assert
        assertTrue(polyString.equals(expected));
    }

    @Test
    public void ShouldEquals() throws GeometricException
    {
        // Arrange
        Polygon p0 = new Polygon(new Point[] {
            new Point(4, 5),
            new Point(7, 8),
            new Point(10, 12),
            new Point(16, 16),
            new Point(16, 0),
        });
        Polygon p1 = new Polygon(new Point[] {
            new Point(4, 5),
            new Point(7, 8),
            new Point(10, 12),
            new Point(16, 16),
            new Point(16, 0),
        });
        System.out.println(-1 % 5);

        // Act
        boolean equals = p0.equals(p1);

        // Arrange
        assertTrue(equals);
    }

    @Test
    public void ShouldEqualsWhenSamePolygonButInvertedOrderOfVertices() throws GeometricException
    {
        // Arrange
        Polygon p0 = new Polygon(new Point[] {
            new Point(4, 5),
            new Point(7, 8),
            new Point(10, 12),
            new Point(16, 16),
            new Point(16, 0),
        });
        Polygon p1 = new Polygon(new Point[] {
            new Point(16, 0),
            new Point(16, 16),
            new Point(10, 12),
            new Point(7, 8),
            new Point(4, 5),
        });
        System.out.println(-1 % 5);

        // Act
        boolean equals = p0.equals(p1);

        // Arrange
        assertTrue(equals);

    }

    @Test
    public void ShouldEqualsWhenSamePolygonButOffset() throws GeometricException
    {
        // Arrange
        Polygon p0 = new Polygon(new Point[] {
            new Point(4, 5),
            new Point(7, 8),
            new Point(10, 12),
            new Point(16, 16),
            new Point(16, 0),
        });
        Polygon p1 = new Polygon(new Point[] {
            new Point(7, 8),
            new Point(4, 5),
            new Point(16, 0),
            new Point(16, 16),
            new Point(10, 12),
        });
        System.out.println(-1 % 5);

        // Act
        boolean equals = p0.equals(p1);

        // Arrange
        assertTrue(equals);

    }

    @Test
    public void ShouldNotEqualsWhenDifferentValues() throws GeometricException
    {
        // Arrange
        Polygon p0 = new Polygon(new Point[] {
            new Point(4, 5),
            new Point(7, 8),
            new Point(10, 16),
            new Point(16, 16),
            new Point(16, 0),
        });
        Polygon p1 = new Polygon(new Point[] {
            new Point(4, 5),
            new Point(7, 8),
            new Point(10, 12),
            new Point(16, 12),
            new Point(16, 0),
        });

        // Act
        boolean equals = p0.equals(p1);

        // Arrange
        assertFalse(equals);
    }

    @Test
    public void ShouldNotEqualsWhenDifferentNumberOfVertices() throws GeometricException
    {
        // Arrange
        Polygon p0 = new Polygon(new Point[] {
            new Point(4, 5),
            new Point(7, 8),
            new Point(10, 12),
            new Point(16, 16),
            new Point(16, 0),
        });
        Polygon p1 = new Polygon(new Point[] {
            new Point(4, 5),
            new Point(7, 8),
            new Point(10, 12),
            new Point(16, 16),
        });

        // Act
        boolean equals = p0.equals(p1);

        // Arrange
        assertFalse(equals);
    }

    @Test
    public void ShouldRotateAroundAnchor() throws GeometricException
    {
        // Arrange
        Polygon poly = new Polygon(new Point[] {
            new Point(4, 3),
            new Point(5, 6),
            new Point(6, 6),
            new Point(5, 5),
            new Point(6, 2),
        });
        Point anchor = new Point(8, 12);
        Polygon expected = new Polygon(new Point [] {
            new Point(13.7942286341, 4.03589838486),
            new Point(11.6961524227, 6.40192378865),
            new Point(12.1961524227, 7.26794919243),
            new Point(12.5621778265, 5.90192378865),
            new Point(15.6602540378, 5.26794919243),
        });

        // Act
        Polygon rotated = poly.rotate(Math.PI / 3, anchor);

        // Arrange
        assertTrue(rotated.equals(expected));
    }

    @Test
    public void ShouldRotateAroundNegativeAnchor() throws GeometricException
    {
        // Arrange
        Polygon poly = new Polygon(new Point[] {
            new Point(4, 3),
            new Point(5, 6),
            new Point(6, 6),
            new Point(5, 5),
            new Point(6, 2),
        });
        VirtualPoint anchor = new VirtualPoint(10, -12);
        Polygon expected = new Polygon(new Point [] {
            new Point(5.60079557681, 3.54499921013),
            new Point(6.90890286198, 6.42403643297),
            new Point(7.90342475734, 6.3195079697),
            new Point(6.80437439871, 5.4295145376),
            new Point(7.48531090427, 2.34142038823),
        });

        // Act
        Polygon rotated = poly.rotate(-Math.PI / 30, anchor);

        // Arrange
        assertTrue(rotated.equals(expected));
    }

    @Test
    public void ShouldRotateAroundCentroid() throws GeometricException
    {
        // Arrange
        Polygon poly = new Polygon(new Point[] {
            new Point(4, 3),
            new Point(5, 6),
            new Point(6, 6),
            new Point(5, 5),
            new Point(6, 2),
        });
        Polygon expected = new Polygon(new Point [] {
            new Point(3.36152236891, 4.25857864376),
            new Point(6.18994949366, 5.67279220614),
            new Point(6.89705627485, 4.96568542495),
            new Point(5.48284271247, 4.96568542495),
            new Point(4.0686291501, 2.1372583002),
        });

        // Act
        Polygon rotated = poly.rotate(-Math.PI / 4);

        // Arrange
        assertTrue(rotated.equals(expected));
    }

    @Test
    public void ShouldTakeDegrees() throws GeometricException
    {
        // Arrange
        Polygon poly = new Polygon(new Point[] {
            new Point(4, 3),
            new Point(5, 6),
            new Point(6, 6),
            new Point(5, 5),
            new Point(6, 2),
        });
        Polygon expected = new Polygon(new Point [] {
            new Point(3.36152236891, 4.25857864376),
            new Point(6.18994949366, 5.67279220614),
            new Point(6.89705627485, 4.96568542495),
            new Point(5.48284271247, 4.96568542495),
            new Point(4.0686291501, 2.1372583002),
        });

        // Act
        Polygon rotated = poly.rotateDegrees(-45);

        // Arrange
        assertTrue(rotated.equals(expected));

    }

    @Test
    public void ShouldBeImmutableWhenRotating() throws GeometricException
    {
        // Arrange
        Polygon poly = new Polygon(new Point[] {
            new Point(4, 3),
            new Point(5, 6),
            new Point(6, 6),
            new Point(5, 5),
            new Point(6, 2),
        });
        Polygon replica = new Polygon(new Point[] {
            new Point(4, 3),
            new Point(5, 6),
            new Point(6, 6),
            new Point(5, 5),
            new Point(6, 2),
        });

        // Act
        poly.rotate(Math.PI / 2);

        // Arrange
        assertTrue(poly.equals(replica));
    }

    @Test
    public void ShouldTranslatePositionWithPositiveVector() throws GeometricException
    {
        // Arrange
        Polygon poly = new Polygon(new Point[] {
            new Point(3, 3),
            new Point(3, 4),
            new Point(4, 4),
            new Point(4, 3),
        });
        Vector v = new Vector(1, 3);
        Polygon expected = new Polygon(new Point[] {
            new Point(4, 6),
            new Point(4, 7),
            new Point(5, 7),
            new Point(5, 6),
        });

        // Act
        Polygon translated = poly.translate(v);

        // assert
        assertTrue(translated.equals(expected));
    }

    @Test
    public void ShouldTranslatePositionWithNegativeVector() throws GeometricException
    {
        // Arrange
        Polygon poly = new Polygon(new Point[] {
            new Point(3, 3),
            new Point(3, 4),
            new Point(4, 4),
            new Point(4, 3),
        });
        Vector v = new Vector(-2, -1);
        Polygon expected = new Polygon(new Point[] {
            new Point(1,2),
            new Point(1,3),
            new Point(2,3),
            new Point(2,2),
        });

        // Act
        Polygon translated = poly.translate(v);

        // assert
        assertTrue(translated.equals(expected));
    }

    @Test
    public void ShouldMoveCentroid() throws GeometricException
    {
        // Arrange
        Polygon poly = new Polygon(new Point[] {
            new Point(4, 4),
            new Point(5, 3),
            new Point(6, 4),
            new Point(6, 6),
            new Point(5, 7),
            new Point(4, 6),
        });
        Point newCentroid0 = new Point(9, 6);
        Point newCentroid1 = new Point(1, 2);
        Polygon expected0 = new Polygon(new Point[] {
            new Point(8, 5),
            new Point(9, 4),
            new Point(10, 5),
            new Point(10, 7),
            new Point(9, 8),
            new Point(8, 7),
        });
        Polygon expected1 = new Polygon(new Point[] {
            new Point(0, 1),
            new Point(1, 0),
            new Point(2, 1),
            new Point(2, 3),
            new Point(1, 4),
            new Point(0, 3),
        });

        // Act
        Polygon translated0 = poly.moveCentroid(newCentroid0);
        Polygon translated1 = poly.moveCentroid(newCentroid1);

        // Assert
        assertTrue(translated0.equals(expected0));
        assertTrue(translated1.equals(expected1));
    }

    @Test
    public void ShouldBeImmutableWhenMovingCentroid() throws GeometricException
    {
        // Arrange
        Polygon poly = new Polygon(new Point[] {
            new Point(4, 4),
            new Point(5, 3),
            new Point(6, 4),
            new Point(6, 6),
            new Point(5, 7),
            new Point(5, 6),
        });
        Polygon replica = new Polygon(new Point[] {
            new Point(4, 4),
            new Point(5, 3),
            new Point(6, 4),
            new Point(6, 6),
            new Point(5, 7),
            new Point(5, 6),
        });
        Point newCentroid0 = new Point(9, 6);

        // Act
        poly.moveCentroid(newCentroid0);

        // Assert
        assertTrue(poly.equals(replica));
    }

	@Test
	public void ShouldThrowExceptionWhenConstructingAPolygonWithLessThanThreePoints() throws GeometricException
	{
		// Arrange
		Point p0 = new Point(0, 0);
		Point p1 = new Point(0, 1);
		Point[] points = new Point[] { p0, p1 };

		// Act
		// Assert
		assertThrows(GeometricException.class, () -> new Polygon(points));
	}

	@Test
	public void ShouldThrowExceptionWhenThereAreCollinearPoints() throws GeometricException
	{
		// Arrange
		Point[] points0 = new Point[] { 
			new Point(0, 0),
			new Point(0, 2),
			new Point(1, 3),
			new Point(2, 4),
			new Point(4, 2),
		};
		Point[] points1 = new Point[] { 
			new Point(0, 0),
			new Point(0, 2),
			new Point(2, 2),
			new Point(1, 1),
		};

		// Act
		// Assert
		assertThrows(GeometricException.class, () -> new Polygon(points0));
		assertThrows(GeometricException.class, () -> new Polygon(points1));
	}

	@Test
	public void ShouldThrowExceptionWhenTwoSubsequentPointsAreEqual() throws GeometricException
	{
		// Arrange
		Point[] points = new Point[] { 
			new Point(0, 0),
			new Point(0, 2),
			new Point(0, 2),
			new Point(2, 4),
			new Point(4, 2),
		};

		// Act
		// Assert
		assertThrows(GeometricException.class, () -> new Polygon(points));
	}

	@Test
	public void ShouldThrowExceptionWhenTwoSidesIntersect() throws GeometricException
	{
		// Arrange
		Point[] points = new Point[] { 
			new Point(0, 0),
			new Point(2, 1),
			new Point(2, 0),
			new Point(0, 2),
		};

		// Act
		// Assert
		assertThrows(GeometricException.class, () -> new Polygon(points));
	}

	@Test
	public void ShouldThrowExceptionWhenRotatingToNegativeCoords() throws GeometricException
	{
		// Arrange
		Point[] points0 = new Point[] {
			new Point(1, 1),
			new Point(2, 3),
			new Point(3, 3),
		};
		Point[] points1 = new Point[] {
			new Point(1, 1),
			new Point(10, 1),
			new Point(10, 2),
			new Point(1, 2),
		};

		// Act
		Polygon poly0 = new Polygon(points0);
		Polygon poly1 = new Polygon(points1);

		// Assert
		assertThrows(GeometricException.class, () -> poly0.rotate(- Math.PI / 2, new VirtualPoint(0, 0)));
		assertThrows(GeometricException.class, () -> poly1.rotateDegrees(-90));
	}

	@Test
	public void ShouldThrowExceptionWhenTranslatingToNegativeCoords() throws GeometricException
	{
		// Arrange
		Point[] points = new Point[] {
			new Point(1, 1),
			new Point(2, 3),
			new Point(3, 3),
		};

		// Act
		Polygon poly = new Polygon(points);

		// Assert
		assertThrows(GeometricException.class, () -> poly.translate(new Vector(-4, 0)));
	}

	@Test
	public void ShouldThrowExceptionWhenMovingCentroidToNegativeCoords() throws GeometricException
	{
		// Arrange
		Point[] points = new Point[] {
			new Point(0, 5),
			new Point(2, 5),
			new Point(1, 1),
		};

		// Act
		Polygon poly = new Polygon(points);

		// Assert
		assertThrows(GeometricException.class, () -> poly.moveCentroid(new Point(1, 1)));
	}

	@Test
	public void ShouldThrowExceptionWhenInvalidParseString()
	{
		// Arrange
		String parseStr0 = "a 2 3 5 4 1 9";
		String parseStr1 = "4 2 3 5 4 1 9";
		String parseStr2 = "3 2 3 5 b 1 9";
		// String parseStr3 = "3 1 1 2 3 5 1 5 0";

		// Act
		// Arrange
		assertThrows(ParseException.class, () -> new Polygon(parseStr0));
		assertThrows(ParseException.class, () -> new Polygon(parseStr1));
		assertThrows(ParseException.class, () -> new Polygon(parseStr2));
		// assertThrows(ParseException.class, () -> new Polygon(parseStr3));
	}

	@Test
	public void ShouldThrowExceptionWhenValidParseStringButInvalidPolygon()
	{
		// Arrange
		String parseStr = "4 0 0 0 3 3 3 2 2";

		// Act
		// Arrange
		assertThrows(GeometricException.class, () -> new Polygon(parseStr));
	}
}
