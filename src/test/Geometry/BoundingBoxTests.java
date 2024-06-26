package Geometry;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class BoundingBoxTests
{
	@Test
	public void ShouldGetMinimumPoint() throws GeometricException
	{
        // Arrange
        BoundingBox box = new BoundingBox(new Point[] {
            new Point(1, 2),
            new Point(2, 1),
            new Point(2, 2),
        });
		Point expected = new Point(1, 1);

		// Act
		Point gotten = box.minPoint();

		// Arrange
		assertEquals(expected, gotten);
	}

	@Test
	public void ShouldGetMaximumPoint() throws GeometricException
	{
        // Arrange
        BoundingBox box = new BoundingBox(new Point[] {
            new Point(1, 1),
            new Point(1, 2),
            new Point(2, 1),
        });
		Point expected = new Point(2, 2);

		// Act
		Point gotten = box.maxPoint();

		// Arrange
		assertEquals(expected, gotten);
	}

	@Test
	public void ShouldWorkWithPolygons() throws GeometricException
	{
        // Arrange
        Polygon poly = new Polygon(new Point[] {
            new Point(1, 1),
            new Point(1, 2),
            new Point(2, 1),
        });

		Point expected = new Point(2, 2);

		// Act
		BoundingBox box = new BoundingBox(poly);
		Point gotten = box.maxPoint();

		// Arrange
		assertEquals(expected, gotten);
	}

    @Test
    public void ShouldNotInterceptWhenBoxesDontIntercept() throws GeometricException
    {
        // Arrange
        BoundingBox bb0 = new BoundingBox(new Point[] {
            new Point(1, 1),
            new Point(2, 1),
            new Point(1, 2),
        });
        BoundingBox bb1 = new BoundingBox(new Point[] {
            new Point(4, 3),
            new Point(3, 4),
            new Point(3, 3),
        });

        // Act
        boolean intercepts = bb0.intersects(bb1);

        // Assert
        assertFalse(intercepts);
    }

    @Test
    public void ShouldInterceptWhenSegmentsDontInterceptButAreClose() throws GeometricException
    {
        // Arrange
        BoundingBox bb0 = new BoundingBox(new Point[] {
            new Point(1, 6),
            new Point(2, 9),
            new Point(3, 6),
            new Point(2, 8),
        });
        BoundingBox bb1 = new BoundingBox(new Point[] {
            new Point(1, 4),
            new Point(2, 7),
            new Point(3, 4),
            new Point(2, 6),
        });

        // Act
        boolean intercepts = bb0.intersects(bb1);

        // Assert
        assertTrue(intercepts);
    }

    @Test
    public void ShouldNotInterceptWhenSideOverlaps() throws GeometricException
    {
        // Arrange
        BoundingBox bb0 = new BoundingBox(new Point[] {
            new Point(1, 1),
            new Point(2, 1),
            new Point(2, 2),
            new Point(1, 2),
        });
        BoundingBox bb1 = new BoundingBox(new Point[] {
            new Point(1, 2),
            new Point(2, 2),
            new Point(2, 3),
            new Point(1, 3),
        });

        // Act
        boolean intercepts = bb0.intersects(bb1);

        // Assert
        assertFalse(intercepts);
    }

	@Test
	public void ShouldThrowExceptionWhenNoPointsProvided()
	{
		// Arrange
		Point[] points = new Point[] {};

		// Act

		// Assert
		assertThrows(GeometricException.class, () -> new BoundingBox(points));
	}
}
