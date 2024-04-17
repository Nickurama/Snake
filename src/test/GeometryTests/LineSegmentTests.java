package GeometryTests;

import Geometry.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.Test;


public class LineSegmentTests
{
    @Test
    public void ShouldIntercept() throws GeometricException
    {
        // Arrange
        LineSegment s0 = new LineSegment(new Point(1, 1), new Point(3, 2));
        LineSegment s1 = new LineSegment(new Point(1, 3), new Point(2, 0));
        
        // Act
        boolean intercepts = s0.intersects(s1);

        // Assert
        assertTrue(intercepts);
    }

    @Test
    public void ShouldNotInterceptEvenIfInherentLinesIntercept() throws GeometricException
    {
        // Arrange
        LineSegment s0 = new LineSegment(new Point(1, 1), new Point(3, 4));
        LineSegment s1 = new LineSegment(new Point(4, 1), new Point(3, 2));
        
        // Act
        boolean intercepts = s0.intersects(s1);

        // Assert
        assertFalse(intercepts);
    }

    @Test
    public void ShouldNotInterceptIfSegmentsOverlap() throws GeometricException
    {
        // Arrange
        LineSegment s0 = new LineSegment(new Point(1, 1), new Point(3, 1));
        LineSegment s1 = new LineSegment(new Point(1, 1), new Point(4, 1));
        
        // Act
        boolean intercepts = s0.intersects(s1);

        // Assert
        assertFalse(intercepts);
    }

    @Test
    public void ShouldNotInterceptIfOnlyTheEndsOverlap() throws GeometricException
    {
        // Arrange
        LineSegment s0 = new LineSegment(new Point(1, 1), new Point(3, 3));
        LineSegment s1 = new LineSegment(new Point(3, 1), new Point(3, 3));
        LineSegment s2 = new LineSegment(new Point(1, 1), new Point(1, 3));
        
        // Act
        boolean intercepts0 = s0.intersects(s1);
        boolean intercepts1 = s0.intersects(s2);

        // Assert
        assertFalse(intercepts0);
        assertFalse(intercepts1);
    }

    @Test
    public void ShouldNotInterceptIfOneEndTouchesTheSegment() throws GeometricException
    {
        // Arrange
        LineSegment s0 = new LineSegment(new Point(1, 1), new Point(1, 3));
        LineSegment s1 = new LineSegment(new Point(1, 2), new Point(3, 2));
        
        // Act
        boolean intercepts = s0.intersects(s1);

        // Assert
        assertFalse(intercepts);
    }

    @Test
    public void ShouldCalculateLength() throws GeometricException
    {
        // Arrange
        LineSegment s0 = new LineSegment(new Point(0, 0), new Point(0, 5));
        double expected0 = 5;
        LineSegment s1 = new LineSegment(new Point(0, 0), new Point(5, 0));
        double expected1 = 5;
        LineSegment s2 = new LineSegment(new Point(1, 1), new Point(3, 5));
        double expected2 = 4.472135955;

        // Act
        double len0 = s0.length();
        double len1 = s1.length();
        double len2 = s2.length();

        // Assert
        assertTrue(MathUtil.areEqual(len0, expected0));
        assertTrue(MathUtil.areEqual(len1, expected1));
        assertTrue(MathUtil.areEqual(len2, expected2));
    }

    @Test
    public void ShouldBeImmutable() throws GeometricException
    {
        // Arrange
        Point p = new Point(0, 0);
        LineSegment s = new LineSegment(p, new Point(5, 5));

        // Act
        p = new Point(1, 1);

        // 
        assertFalse(p.equals(s.getFirstPoint()));
    }

	@Test
	public void ShouldThrowExceptionWhenConstructingWithEqualPoints() throws GeometricException
	{
		// Arrange
		Point p0 = new Point(7, 2);
		Point p1 = new Point(7, 2);

		// Act
		// Assert
		assertThrows(GeometricException.class, () -> new LineSegment(p0, p1));
	}

	@Test
	public void ShouldRasterizeHorizontalLines() throws GeometricException
	{
		// Arrange
		LineSegment s0 = new LineSegment(new Point(10, 10), new Point(20, 10));
		LineSegment s1 = new LineSegment(new Point(10, 10), new Point(0, 10));
		Point[] expected0 = new Point[] 
		{
			new Point(10, 10),
			new Point(11, 10),
			new Point(12, 10),
			new Point(13, 10),
			new Point(14, 10),
			new Point(15, 10),
			new Point(16, 10),
			new Point(17, 10),
			new Point(18, 10),
			new Point(19, 10),
			new Point(20, 10),
		};

		Point[] expected1 = new Point[] 
		{
			new Point(10, 10),
			new Point(9, 10),
			new Point(8, 10),
			new Point(7, 10),
			new Point(6, 10),
			new Point(5, 10),
			new Point(4, 10),
			new Point(3, 10),
			new Point(2, 10),
			new Point(1, 10),
			new Point(0, 10),
		};

		// Act
		Point[] points0 = s0.rasterize();
		Point[] points1 = s1.rasterize();

		// Assert
		assertEquals(expected0.length, points0.length);
		for (Point p : expected0)
			assertTrue(Arrays.asList(points0).contains(p));
		assertEquals(expected1.length, points1.length);
		for (Point p : expected1)
			assertTrue(Arrays.asList(points1).contains(p));
	}

	@Test
	public void ShouldRasterizeVerticalLines() throws GeometricException
	{
		// Arrange
		LineSegment s0 = new LineSegment(new Point(10, 10), new Point(10, 20));
		LineSegment s1 = new LineSegment(new Point(10, 10), new Point(10, 0));
		Point[] expected0 = new Point[] 
		{
			new Point(10, 10),
			new Point(10, 11),
			new Point(10, 12),
			new Point(10, 13),
			new Point(10, 14),
			new Point(10, 15),
			new Point(10, 16),
			new Point(10, 17),
			new Point(10, 18),
			new Point(10, 19),
			new Point(10, 20),
		};

		Point[] expected1 = new Point[] 
		{
			new Point(10, 10),
			new Point(10, 9),
			new Point(10, 8),
			new Point(10, 7),
			new Point(10, 6),
			new Point(10, 5),
			new Point(10, 4),
			new Point(10, 3),
			new Point(10, 2),
			new Point(10, 1),
			new Point(10, 0),
		};

		// Act
		Point[] points0 = s0.rasterize();
		Point[] points1 = s1.rasterize();

		// Assert
		assertEquals(expected0.length, points0.length);
		for (Point p : expected0)
			assertTrue(Arrays.asList(points0).contains(p));
		assertEquals(expected1.length, points1.length);
		for (Point p : expected1)
			assertTrue(Arrays.asList(points1).contains(p));
	}

	@Test
	public void ShouldRasterizeOverDiagonalLines() throws GeometricException
	{
		// Arrange
		LineSegment s0 = new LineSegment(new Point(10, 10), new Point(20, 20));
		LineSegment s1 = new LineSegment(new Point(10, 10), new Point(0, 20));
		LineSegment s2 = new LineSegment(new Point(10, 10), new Point(0, 0));
		LineSegment s3 = new LineSegment(new Point(10, 10), new Point(20, 0));

		Point[] expected0 = new Point[] 
		{
			new Point(10, 10),
			new Point(11, 11),
			new Point(12, 12),
			new Point(13, 13),
			new Point(14, 14),
			new Point(15, 15),
			new Point(16, 16),
			new Point(17, 17),
			new Point(18, 18),
			new Point(19, 19),
			new Point(20, 20),
		};

		Point[] expected1 = new Point[] 
		{
			new Point(10, 10),
			new Point(9, 11),
			new Point(8, 12),
			new Point(7, 13),
			new Point(6, 14),
			new Point(5, 15),
			new Point(4, 16),
			new Point(3, 17),
			new Point(2, 18),
			new Point(1, 19),
			new Point(0, 20),
		};

		Point[] expected2 = new Point[] 
		{
			new Point(10, 10),
			new Point(9, 9),
			new Point(8, 8),
			new Point(7, 7),
			new Point(6, 6),
			new Point(5, 5),
			new Point(4, 4),
			new Point(3, 3),
			new Point(2, 2),
			new Point(1, 1),
			new Point(0, 0),
		};

		Point[] expected3 = new Point[] 
		{
			new Point(10, 10),
			new Point(11, 9),
			new Point(12, 8),
			new Point(13, 7),
			new Point(14, 6),
			new Point(15, 5),
			new Point(16, 4),
			new Point(17, 3),
			new Point(18, 2),
			new Point(19, 1),
			new Point(20, 0),
		};

		// Act
		Point[] points0 = s0.rasterize();
		Point[] points1 = s1.rasterize();
		Point[] points2 = s2.rasterize();
		Point[] points3 = s3.rasterize();

		// Assert
		assertEquals(expected0.length, points0.length);
		for (Point p : expected0)
			assertTrue(Arrays.asList(points0).contains(p));
		assertEquals(expected1.length, points1.length);
		for (Point p : expected1)
			assertTrue(Arrays.asList(points1).contains(p));
		assertEquals(expected2.length, points2.length);
		for (Point p : expected2)
			assertTrue(Arrays.asList(points2).contains(p));
		assertEquals(expected3.length, points3.length);
		for (Point p : expected3)
			assertTrue(Arrays.asList(points3).contains(p));
	}

	@Test
	public void ShouldRasterizeOverFirstOctant() throws GeometricException
	{
		// Arrange
		LineSegment segment = new LineSegment(new Point(10, 10), new Point(20, 15));
		Point[] expected = new Point[] 
		{
			new Point(10, 10),
			new Point(11, 11),
			new Point(12, 11),
			new Point(13, 12),
			new Point(14, 12),
			new Point(15, 13),
			new Point(16, 13),
			new Point(17, 14),
			new Point(18, 14),
			new Point(19, 15),
			new Point(20, 15),
		};

		// Act
		Point[] points = segment.rasterize();

		// Assert
		assertEquals(expected.length, points.length);
		for (Point p : expected)
			assertTrue(Arrays.asList(points).contains(p));
	}

	@Test
	public void ShouldRasterizeOverSecondOctant() throws GeometricException
	{
		// Arrange
		LineSegment segment = new LineSegment(new Point(10, 10), new Point(18, 20));
		Point[] expected = new Point[] 
		{
			new Point(10, 10),
			new Point(11, 11),
			new Point(12, 12),
			new Point(12, 13),
			new Point(13, 14),
			new Point(14, 15),
			new Point(15, 16),
			new Point(16, 17),
			new Point(16, 18),
			new Point(17, 19),
			new Point(18, 20),
		};

		// Act
		Point[] points = segment.rasterize();

		// Assert
		assertEquals(expected.length, points.length);
		for (Point p : expected)
			assertTrue(Arrays.asList(points).contains(p));
	}

	@Test
	public void ShouldRasterizeOverThirdOctant() throws GeometricException
	{
		// Arrange
		LineSegment segment = new LineSegment(new Point(10, 10), new Point(8, 20));
		Point[] expected = new Point[] 
		{
			new Point(10, 10),
			new Point(10, 11),
			new Point(10, 12),
			new Point(9, 13),
			new Point(9, 14),
			new Point(9, 15),
			new Point(9, 16),
			new Point(9, 17),
			new Point(8, 18),
			new Point(8, 19),
			new Point(8, 20),
		};

		// Act
		Point[] points = segment.rasterize();

		// Assert
		assertEquals(expected.length, points.length);
		for (Point p : expected)
			assertTrue(Arrays.asList(points).contains(p));
	}

	@Test
	public void ShouldRasterizeOverFourthOctant() throws GeometricException
	{
		// Arrange
		LineSegment segment = new LineSegment(new Point(10, 10), new Point(0, 19));
		Point[] expected = new Point[] 
		{
			new Point(10, 10),
			new Point(9, 11),
			new Point(8, 12),
			new Point(7, 13),
			new Point(6, 14),
			new Point(5, 14),
			new Point(4, 15),
			new Point(3, 16),
			new Point(2, 17),
			new Point(1, 18),
			new Point(0, 19),
		};

		// Act
		Point[] points = segment.rasterize();

		// Assert
		assertEquals(expected.length, points.length);
		for (Point p : expected)
			assertTrue(Arrays.asList(points).contains(p));
	}

	@Test
	public void ShouldRasterizeOverFifthOctant() throws GeometricException
	{
		// Arrange
		LineSegment segment = new LineSegment(new Point(10, 10), new Point(0, 6));
		Point[] expected = new Point[] 
		{
			new Point(10, 10),
			new Point(9, 10),
			new Point(8, 9),
			new Point(7, 9),
			new Point(6, 8),
			new Point(5, 8),
			new Point(4, 8),
			new Point(3, 7),
			new Point(2, 7),
			new Point(1, 6),
			new Point(0, 6),
		};

		// Act
		Point[] points = segment.rasterize();

		// Assert
		assertEquals(expected.length, points.length);
		for (Point p : expected)
			assertTrue(Arrays.asList(points).contains(p));
	}

	@Test
	public void ShouldRasterizeOverSixthOctant() throws GeometricException
	{
		// Arrange
		LineSegment segment = new LineSegment(new Point(10, 10), new Point(9, 0));
		Point[] expected = new Point[] 
		{
			new Point(10, 10),
			new Point(10, 9),
			new Point(10, 8),
			new Point(10, 7),
			new Point(10, 6),
			new Point(10, 5),
			new Point(9, 4),
			new Point(9, 3),
			new Point(9, 2),
			new Point(9, 1),
			new Point(9, 0),
		};

		// Act
		Point[] points = segment.rasterize();

		// Assert
		assertEquals(expected.length, points.length);
		for (Point p : expected)
			assertTrue(Arrays.asList(points).contains(p));
	}

	@Test
	public void ShouldRasterizeOverSeventhOctant() throws GeometricException
	{
		// Arrange
		LineSegment segment = new LineSegment(new Point(10, 10), new Point(13, 0));
		Point[] expected = new Point[] 
		{
			new Point(10, 10),
			new Point(10, 9),
			new Point(11, 8),
			new Point(11, 7),
			new Point(11, 6),
			new Point(11, 5),
			new Point(12, 4),
			new Point(12, 3),
			new Point(12, 2),
			new Point(13, 1),
			new Point(13, 0),
		};

		// Act
		Point[] points = segment.rasterize();

		// Assert
		assertEquals(expected.length, points.length);
		for (Point p : expected)
			assertTrue(Arrays.asList(points).contains(p));
	}

	@Test
	public void ShouldRasterizeOverEightOctant() throws GeometricException
	{
		// Arrange
		LineSegment segment = new LineSegment(new Point(10, 10), new Point(20, 7));
		Point[] expected = new Point[] 
		{
			new Point(10, 10),
			new Point(11, 10),
			new Point(12, 9),
			new Point(13, 9),
			new Point(14, 9),
			new Point(15, 8),
			new Point(16, 8),
			new Point(17, 8),
			new Point(18, 8),
			new Point(19, 7),
			new Point(20, 7),
		};

		// Act
		Point[] points = segment.rasterize();

		// Assert
		assertEquals(expected.length, points.length);
		for (Point p : expected)
			assertTrue(Arrays.asList(points).contains(p));
	}

	@Test
	public void ShouldRasterizeWithFloats() throws GeometricException
	{
		// Arrange
		LineSegment segment = new LineSegment(new Point(25.2, 28.9), new Point(21.5, 23.1));
		Point[] expected = new Point[] 
		{
			new Point(25, 29),
			new Point(25, 28),
			new Point(24, 27),
			new Point(24, 26),
			new Point(23, 25),
			new Point(23, 24),
			new Point(22, 23),
		};

		// Act
		Point[] points = segment.rasterize();

		// Assert

		assertEquals(expected.length, points.length);
		for (Point p : expected)
			assertTrue(Arrays.asList(points).contains(p));
	}

	@Test
	public void ShouldRasterizeWithSamePoint() throws GeometricException
	{
		// Arrange
		LineSegment segment = new LineSegment(new Point(10.1, 10.1), new Point(9.9, 9.9));
		Point[] expected = new Point[] 
		{
			new Point(10, 10),
		};

		// Act
		Point[] points = segment.rasterize();

		// Assert
		assertEquals(expected.length, points.length);
		for (Point p : expected)
			assertTrue(Arrays.asList(points).contains(p));
	}
}
