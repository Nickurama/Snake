package Geometry;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;


public class LineTests
{
    @Test
    public void ShouldBeCollinearWithHorizontalCollinearity() throws GeometricException
    {
        // Arrange
        Line horizontal = new Line(new Point(1, 1), new Point(2, 1));
        Point horizontalCollinear = new Point(3, 1);

        // Act
        boolean horizontalIsCollinear = horizontal.isCollinear(horizontalCollinear);

        // Assert
        assertTrue(horizontalIsCollinear);
    }

    @Test
    public void ShouldBeCollinearWithVerticalCollinearity() throws GeometricException
    {
        // Arrange
        Line vertical = new Line(new Point(1, 1), new Point(1, 2));
        Point verticalCollinear = new Point(1, 3);

        // Act
        boolean verticalIsCollinear = vertical.isCollinear(verticalCollinear);

        // Assert
        assertTrue(verticalIsCollinear);
    }

    @Test
    public void ShouldBeCollinearWithDiagonalCollinearity() throws GeometricException
    {
        // Arrange
        Line diagonal = new Line(new Point(1, 1), new Point(2, 2));
        Point diagonalCollinear = new Point(3, 3);

        // Act
        boolean diagonalIsCollinear = diagonal.isCollinear(diagonalCollinear);

        // Assert
        assertTrue(diagonalIsCollinear);
    }

    @Test void ShouldNotBeCollinearWhenPointIsntCollinear() throws GeometricException
    {
       // Arrange
        Line vertical = new Line(new Point(1, 1), new Point(1, 2));
        Point verticalCollinear = new Point(1, 3);
        Line diagonal = new Line(new Point(1, 1), new Point(2, 2));
        Point horizontalCollinear = new Point(3, 1);

        // Act
        boolean verticalIsCollinearWithHorizontal = vertical.isCollinear(horizontalCollinear);
        boolean diagonalIsCollinearWithVertical = diagonal.isCollinear(verticalCollinear);

        // Assert
        assertFalse(verticalIsCollinearWithHorizontal);
        assertFalse(diagonalIsCollinearWithVertical);
    }

    @Test
    public void ShouldCalculateIntersectionForHorizontalIntersections() throws GeometricException
    {
        // Assert
        Line l0 = new Line(new Point(1, 1), new Point(2, 1));
        Line l1 = new Line(new Point(5, 3), new Point(5, 4));
        VirtualPoint expected = new VirtualPoint(5, 1);

        // Act
        VirtualPoint vp = l0.intersection(l1);

        // Arrange
        assertTrue(vp.equals(expected));
    }

    @Test
    public void ShouldCalculateIntersectionsForVerticalIntersections() throws GeometricException
    {
        // Assert
        Line l0 = new Line(new Point(1, 1), new Point(1, 2));
        Line l1 = new Line(new Point(3, 5), new Point(4, 5));
        VirtualPoint expected = new VirtualPoint(1, 5);

        // Act
        VirtualPoint vp = l0.intersection(l1);

        // Arrange
        assertTrue(vp.equals(expected));
    }

    @Test
    public void ShouldCalculateIntersectionsForNonIntegerIntersections() throws GeometricException
    {
        // Arrange
        Line l0 = new Line(new Point(0, 5), new Point(1, 0));
        Line l1 = new Line(new Point(0, 4), new Point(10, 0));
        VirtualPoint expected = new VirtualPoint(0.21739130434, 3.91304347826);

        // Act
        VirtualPoint vp = l0.intersection(l1);

        // Assert
        assertTrue(vp.equals(expected));
    }

    @Test
    public void ShouldCalculateIntersectionWhenIntersectionIsNegative() throws GeometricException
    {
        // Arrange
        Line l0 = new Line(new Point(0, 6), new Point(1, 8));
        Line l1 = new Line(new Point(4, 0), new Point(6, 1));
        VirtualPoint expected = new VirtualPoint(-5.3333333333, -4.6666666666);

        // Act
        VirtualPoint vp = l0.intersection(l1);

        // Assert
        assertTrue(vp.equals(expected));
    }

    @Test
    public void ShouldBeParalelWhenHorizontalParalelism() throws GeometricException
    {
        // Arrange
        Line l0 = new Line(new Point(1, 1), new Point(2, 1));
        Line l1 = new Line(new Point(2, 2), new Point(4, 2));

        // Act
        boolean isParalel = l0.isParalel(l1);

        // Arrange
        assertTrue(isParalel);
    }

    @Test
    public void ShouldBeParalelWhenVerticalParalelism() throws GeometricException
    {
        // Arrange
        Line l0 = new Line(new Point(1, 1), new Point(1, 2));
        Line l1 = new Line(new Point(2, 2), new Point(2, 4));

        // Act
        boolean isParalel = l0.isParalel(l1);

        // Arrange
        assertTrue(isParalel);
    }

    @Test
    public void ShouldBeParalelWhenDiagonalParalelism() throws GeometricException
    {
        // Arrange
        Line l0 = new Line(new Point(4, 0), new Point(14, 2));
        Line l1 = new Line(new Point(6, 0), new Point(16, 2));

        // Act
        boolean isParalel = l0.isParalel(l1);

        // Arrange
        assertTrue(isParalel);
    }

    @Test
    public void ShouldNotBeParalel() throws GeometricException
    {
        // Arrange
        Line l0 = new Line(new Point(4, 0), new Point(14, 2));
        Line l1 = new Line(new Point(5, 0), new Point(16, 2));

        // Act
        boolean isParalel = l0.isParalel(l1);

        // Arrange
        assertFalse(isParalel);
    }

    @Test
    public void ShouldBeEqualsWithExactSameLine() throws GeometricException
    {
        // Arrange
        Line l = new Line(new Point(0, 0), new Point(1, 1));

        // Act
        boolean isEqual = l.equals(l);

        // Arrange
        assertTrue(isEqual);
    }

    @Test
    public void ShouldBeEqualsWithEqualLines() throws GeometricException
    {
        // Arrange
        Line l0 = new Line(new Point(0, 0), new Point(1, 1));
        Line l1 = new Line(new Point(0, 0), new Point(1, 1));

        // Act
        boolean isEqual = l0.equals(l1);

        // Arrange
        assertTrue(isEqual);
    }

    @Test
    public void ShouldBeEqualsWithEquivalentLines() throws GeometricException
    {
        // Arrange
        Line l0 = new Line(new Point(2, 2), new Point(3, 3));
        Line l1 = new Line(new Point(1, 1), new Point(4, 4));

        Line l2 = new Line(new Point(2, 2), new Point(3, 3));
        Line l3 = new Line(new Point(4, 4), new Point(1, 1));

        // Act
        boolean isEqual0 = l0.equals(l1);
        boolean isEqual1 = l2.equals(l3);

        // Arrange
        assertTrue(isEqual0);
        assertTrue(isEqual1);
    }

    @Test
    public void ShouldNotBeEqualsWhenLinesAreParalel() throws GeometricException
    {
        // Arrange
        Line l0 = new Line(new Point(0, 0), new Point(1, 0));
        Line l1 = new Line(new Point(0, 1), new Point(1, 1));

        // Act
        boolean isEqual = l0.equals(l1);

        // Arrange
        assertFalse(isEqual);
    }

    @Test
    public void ShouldNotBeEqualsWhenLinesAreNotEqual() throws GeometricException
    {
        // Arrange
        Line l0 = new Line(new Point(0, 0), new Point(1, 1));
        Line l1 = new Line(new Point(0, 1), new Point(1, 1));

        // Act
        boolean isEqual = l0.equals(l1);

        // Arrange
        assertFalse(isEqual);
    }

    @Test
    public void ShouldBeImmutable() throws GeometricException
    {
        // Arrange
        Point p0 = new Point(0, 0);
        Point p1 = new Point(1, 1);
        Line l0 = new Line(p0, p1);
        Line l1 = new Line(new Point(0, 0), new Point(1, 1));

        // Act
        p0 = new Point(1, 0);
        p1 = new Point(3, 1);

        // Assert
        assertTrue(l0.equals(l1));
    }

    @Test
    public void ShouldCalculateIfIsPerpendicular() throws GeometricException
    {
        // Arrange
        Line l0 = new Line(new Point(1, 1), new Point(1, 2));
        Line l1 = new Line(new Point(2, 2), new Point(3, 2));
        Line l2 = new Line(new Point(0, 1), new Point(1, 3));
        Line l3 = new Line(new Point(0, 1), new Point(2, 0));

        // Act
        boolean isPerpendicular0 = l0.isPerpendicular(l1);
        boolean isPerpendicular1 = l2.isPerpendicular(l3);
        boolean isPerpendicular2 = l0.isPerpendicular(l3);

        // Arrange
        assertTrue(isPerpendicular0);
        assertTrue(isPerpendicular1);
        assertFalse(isPerpendicular2);
    }

	@Test
	public void ShouldThrowWhenConstructingWithEqualPoints() throws GeometricException
	{
		// Arrange
		Point p0 = new Point(2, 5);
		Point p1 = new Point(2, 5);

		// Act
		// Assert
		assertThrows(GeometricException.class, () -> new Line(p0, p1));
	}

	@Test
	public void ShouldGeneratePerpendicularWhenHorizontal() throws GeometricException
	{
		// Arrange
		Line horizontal = new Line(new Point(1, 1), new Point(2, 1));
		Line expected = new Line(new Point(5, 1), new Point(5, 2));

		// Act
		Line perpendicular = horizontal.generatePerpendicular(new Point(5, 3));

		// Arrange
		assertTrue(perpendicular.isPerpendicular(horizontal));
		assertEquals(expected, perpendicular);
	}

	@Test
	public void ShouldGeneratePerpendicularWhenVertical() throws GeometricException
	{
		// Arrange
		Line vertical = new Line(new Point(1, 1), new Point(1, 2));
		Line expected = new Line(new Point(1, 3), new Point(2, 3));

		// Act
		Line perpendicular = vertical.generatePerpendicular(new Point(5, 3));

		// Arrange
		assertTrue(perpendicular.isPerpendicular(vertical));
		assertEquals(expected, perpendicular);
	}

	@Test
	public void ShouldGeneratePerpendicular() throws GeometricException
	{
		// Arrange
		Line line0 = new Line(new Point(1, 2), new Point(2, 1));
		Line expected0 = new Line(new Point(2, 2), new Point(1, 1));
		Line line1 = new Line(new Point(1, 1), new Point(3, 2));
		Line expected1 = new Line(new Point(1, 3), new Point(2, 1));

		// Act
		Line perpendicular0 = line0.generatePerpendicular(new Point(1, 1));
		Line perpendicular1 = line1.generatePerpendicular(new Point(2, 1));

		// Arrange
		assertTrue(perpendicular0.isPerpendicular(line0));
		assertEquals(expected0, perpendicular0);
		assertTrue(perpendicular1.isPerpendicular(line1));
		assertEquals(expected1, perpendicular1);
	}

	@Test
	public void ShouldInitializeHorizontalLineFromQuotients() throws GeometricException
	{
		// Arrange
		Point p = new Point(3, 5);
		Line expected = new Line(new Point(1, 5), new Point(2, 5));

		// Act
		Line gotten = new Line(0, 1, p);

		// Arrange
		assertEquals(expected, gotten);
	}

	@Test
	public void ShouldInitializeVerticalLineFromQuotients() throws GeometricException
	{
		// Arrange
		Point p = new Point(3, 5);
		Line expected = new Line(new Point(3, 1), new Point(3, 2));

		// Act
		Line gotten = new Line(1, 0, p);

		// Arrange
		assertEquals(expected, gotten);
	}

	@Test
	public void ShouldInitializeLineFromQuotients() throws GeometricException
	{
		// Arrange
		Point p = new Point(3, 5);
		Line expected = new Line(new Point(1, 6), new Point(5, 4));

		// Act
		Line gotten = new Line(1, 2, p);

		// Arrange
		assertEquals(expected, gotten);
	}

	@Test
	public void ShouldThrowExceptionWhenBothQuotientsAreZero() throws GeometricException
	{
		// Arrange
		// Act
		// Assert
		assertThrows(GeometricException.class, () -> new Line(0, 0, new Point(1, 1)));
	}
}
