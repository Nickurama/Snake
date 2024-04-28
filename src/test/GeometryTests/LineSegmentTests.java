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
        assertFalse(p.equals(s.firstPoint()));
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
	public void ShouldIntersectWithLine() throws GeometricException
	{
		// Arrange
		LineSegment segment = new LineSegment(new Point(1, 1), new Point(6, 3));
		Line line = new Line(new Point(0, 4), new Point(1, 3));

		// Act
		boolean intercepts = segment.intersects(line);

		// Assert
		assertTrue(intercepts);
	}

	@Test
	public void ShouldNotIntersectWithLine() throws GeometricException
	{
		// Arrange
		LineSegment segment = new LineSegment(new Point(2, 3), new Point(2, 5));
		Line line = new Line(new Point(0, 2), new Point(1, 2));

		// Act
		boolean intercepts = segment.intersects(line);

		// Assert
		assertFalse(intercepts);
	}

	@Test
	public void ShouldIntersectWithHorizontalLineWhenVerticalSegment() throws GeometricException
	{
		// Arrange
		LineSegment segment = new LineSegment(new Point(2, 1), new Point(2, 5));
		Line line = new Line(new Point(0, 2), new Point(1, 2));

		// Act
		boolean intercepts = segment.intersects(line);

		// Assert
		assertTrue(intercepts);
	}

	@Test
	public void ShouldIntersectWithVerticalLineWhenHorizontalSegment() throws GeometricException
	{
		// Arrange
		LineSegment segment = new LineSegment(new Point(1, 1), new Point(4, 1));
		Line line = new Line(new Point(3, 5), new Point(3, 2));

		// Act
		boolean intercepts = segment.intersects(line);

		// Assert
		assertTrue(intercepts);
	}

	@Test
	public void ShouldNotIntersectWhenOnlyIntersectsPoint() throws GeometricException
	{
		// Arrange
		LineSegment segment = new LineSegment(new Point(1, 1), new Point(4, 1));
		Line line = new Line(new Point(4, 5), new Point(4, 2));

		// Act
		boolean intercepts = segment.intersects(line);

		// Assert
		assertFalse(intercepts);
	}

	@Test
	public void ShouldIntersectInclusiveWhenOnlyIntersectsPointOnLine() throws GeometricException
	{
		// Arrange
		LineSegment segment = new LineSegment(new Point(1, 1), new Point(4, 1));
		Line line = new Line(new Point(4, 5), new Point(4, 2));

		// Act
		boolean intercepts = segment.intersectsInclusive(line);

		// Assert
		assertTrue(intercepts);
	}
	
	@Test
	public void ShouldIntersectInclusiveWhenOnlyIntersectsPointOnLineSegment() throws GeometricException
	{
		// Arrange
		LineSegment segment0 = new LineSegment(new Point(1, 1), new Point(4, 1));
		LineSegment segment1 = new LineSegment(new Point(4, 1), new Point(3, 3));

		// Act
		boolean intercepts = segment0.intersectsInclusive(segment1);

		// Assert
		assertTrue(intercepts);
	}

	@Test
	public void ShouldContainCollinearPointOnVerticalSegment() throws GeometricException
	{
		// Arrange
		Point point0 = new Point(1, 2);
		Point point1 = new Point(1, 3);
		LineSegment segment = new LineSegment(new Point(1, 1), new Point(1, 3));

		// Act
		boolean contains0 = segment.contains(point0);
		boolean contains1 = segment.contains(point1);

		// Arrange
		assertTrue(contains0);
		assertTrue(contains1);
	}
	@Test

	public void ShouldNotContainCollinearPointOnVerticalSegment() throws GeometricException
	{
		// Arrange
		Point point = new Point(1, 5);
		LineSegment segment = new LineSegment(new Point(1, 1), new Point(1, 3));

		// Act
		boolean contains = segment.contains(point);

		// Arrange
		assertFalse(contains);
	}

	@Test
	public void ShouldContainCollinearPointOnHorizontalSegment() throws GeometricException
	{
		// Arrange
		Point point0 = new Point(2, 1);
		Point point1 = new Point(3, 1);
		LineSegment segment = new LineSegment(new Point(1, 1), new Point(3, 1));

		// Act
		boolean contains0 = segment.contains(point0);
		boolean contains1 = segment.contains(point1);

		// Arrange
		assertTrue(contains0);
		assertTrue(contains1);
	}

	@Test
	public void ShouldNotContainCollinearPointOnHorizontalSegment() throws GeometricException
	{
		// Arrange
		Point point = new Point(0, 1);
		LineSegment segment = new LineSegment(new Point(1, 1), new Point(3, 1));

		// Act
		boolean contains = segment.contains(point);

		// Arrange
		assertFalse(contains);
	}

	@Test
	public void ShouldContainCollinearPointOnSegment() throws GeometricException
	{
		// Arrange
		Point point0 = new Point(2, 3);
		Point point1 = new Point(3, 5);
		LineSegment segment = new LineSegment(new Point(1, 1), new Point(3, 5));

		// Act
		boolean contains0 = segment.contains(point0);
		boolean contains1 = segment.contains(point1);

		// Arrange
		assertTrue(contains0);
		assertTrue(contains1);
	}

	@Test
	public void ShouldNotContainCollinearPointOnSegment() throws GeometricException
	{
		// Arrange
		Point point = new Point(2/3, 0);
		LineSegment segment = new LineSegment(new Point(1, 1), new Point(3, 5));

		// Act
		boolean contains = segment.contains(point);

		// Arrange
		assertFalse(contains);
	}

	@Test
	public void ShouldNotContainsNonCollinearPoint() throws GeometricException
	{
		// Arrange
		Point point0 = new Point(2, 2);
		LineSegment vertical = new LineSegment(new Point(1, 1), new Point(1, 3));
		LineSegment horizontal = new LineSegment(new Point(1, 1), new Point(3, 1));

		Point point1 = new Point(3, 2);
		Point point2 = new Point(2, 3);
		LineSegment segment = new LineSegment(new Point(1, 1), new Point(4, 4));

		// Act
		boolean contains0 = vertical.contains(point0);
		boolean contains1 = horizontal.contains(point0);
		boolean contains2 = segment.contains(point1);
		boolean contains3 = segment.contains(point2);

		// Arrange
		assertFalse(contains0);
		assertFalse(contains1);
		assertFalse(contains2);
		assertFalse(contains3);
	}
}
