package GeometryTests;

import Geometry.*;
import static org.junit.jupiter.api.Assertions.*;
import java.text.ParseException;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class CircleTests
{
	@Test
	public void ShouldThrowWhenZeroOrNegativeRadius() throws GeometricException
	{
		// Arrange
		Point p = new Point(1, 1);
		double r0 = 0;
		double r1 = -3;

		// Act
		// Assert
		assertThrows(GeometricException.class, () -> new Circle(p, r0));
		assertThrows(GeometricException.class, () -> new Circle(p, r1));
	}

	@Test
	public void ShouldThrowWhenRadiusGoesOntoNegativeCoordinates() throws GeometricException
	{
		// Arrange
		// Act
		// Assert
		assertThrows(GeometricException.class, () -> new Circle(new Point(1, 1), 2));
	}

	@Test
	public void ShouldParseToString() throws GeometricException
	{
		// Arrange
		Circle c = new Circle(new Point(3, 3), 2);
		String expected = "Circle: center=(3,3) radius=2";

		// Act
		String s = c.toString();

		// Assert
		assertEquals(expected, s);
	}

	@Test
	public void ShouldEquals() throws GeometricException
	{
		// Arrange
		Circle c0 = new Circle(new Point(3, 3), 2);
		Circle c1 = new Circle(new Point(3, 3), 2);

		// Act
		boolean equals = c0.equals(c1);

		// Arrange
		assertTrue(equals);
	}

	@Test
	public void ShouldNotEquals() throws GeometricException
	{
		// Arrange
		Circle c0 = new Circle(new Point(3, 3), 2);
		Circle c1 = new Circle(new Point(3, 4), 2);
		Circle c2 = new Circle(new Point(3, 3), 3);

		// Act
		boolean equals0 = c0.equals(c1);
		boolean equals1 = c0.equals(c2);

		// Arrange
		assertFalse(equals0);
		assertFalse(equals1);
	}

	@Test
	public void ShouldGetPerimeter() throws GeometricException
	{
		// Arrange
		Circle c = new Circle(new Point(1, 1), 1);
		double expected = 2.0 * Math.PI;

		// Act
		double perimeter = c.perimeter();

		// Assert
		assertTrue(MathUtil.areEqual(expected, perimeter));
	}

	@Test
	public void ShouldIntersect() throws GeometricException
	{
		// Arrange
		Circle c0 = new Circle(new Point(3, 3), 2);
		Circle c1 = new Circle(new Point(6, 4), 2);

		// Act
		boolean intersects = c0.intersects(c1);

		// Arrange
		assertTrue(intersects);
	}

	@Test
	public void ShouldIntersect() throws GeometricException
	{
		// Arrange
		Circle c0 = new Circle(new Point(3, 3), 2);
		Circle c1 = new Circle(new Point(6, 4), 2);

		// Act
		boolean intersects = c0.intersects(c1);

		// Arrange
		assertTrue(intersects);
	}

	@Test
	public void ShouldIntersect() throws GeometricException
	{
		// Arrange
		Circle c0 = new Circle(new Point(3, 3), 2);
		Circle c1 = new Circle(new Point(6, 4), 2);

		// Act
		boolean intersects = c0.intersects(c1);

		// Arrange
		assertTrue(intersects);
	}

	@Test
	public void ShouldNotIntersectWhenOverlapping() throws GeometricException
	{
		// Arrange
		Circle c0 = new Circle(new Point(3, 3), 2);
		Circle c1 = new Circle(new Point(7, 3), 2);

		// Act
		boolean intersects = c0.intersects(c1);

		// Arrange
		assertFalse(intersects);
	}

	@Test
	public void ShouldNotIntersect() throws GeometricException
	{
		// Arrange
		Circle c0 = new Circle(new Point(3, 3), 2);
		Circle c1 = new Circle(new Point(6, 8), 3);

		// Act
		boolean intersects = c0.intersects(c1);

		// Arrange
		assertFalse(intersects);
	}

	@Test
	public void ShouldMakeDeepCopy() throws GeometricException
	{
		// Arrange
		Circle c = new Circle(new Point(5, 6), 4);

		// Act
		Circle copy = new Circle(c);

		// Assert
		assertEquals(c, copy);
	}

	@Test
	public void ShouldRotate() throws GeometricException
	{
		// Arrange
		Circle c = new Circle(new Point(5, 6), 1);
		Circle expected = new Circle(new Point(10, 11), 1);

		// Act
		Circle rotated = c.rotate(- Math.PI / 2, new Point(10, 6));

		// Assert
		assertEquals(expected, rotated);
	}

	@Test
	public void ShouldRotateDegrees() throws GeometricException
	{
		// Arrange
		Circle c = new Circle(new Point(5, 6), 1);
		Circle expected = new Circle(new Point(5, 2), 1);

		// Act
		Circle rotated = c.rotateDegrees(180, new Point(5, 4));

		// Assert
		assertEquals(expected, rotated);
	}

	@Test
	public void ShouldBeImmutableOnRotate() throws GeometricException
	{
		// Arrange
		Circle c = new Circle(new Point(5, 6), 1);
		Circle duplicate = new Circle(new Point(5, 6), 1);

		// Act
		c.rotate(- Math.PI / 2, new Point(10, 6));
		c.rotateDegrees(-90, new Point(10, 6));

		// Assert
		assertEquals(c, duplicate);
	}

	@Test
	public void ShouldThrowExceptionWhenRotatingIntoNegativeCoordinates() throws GeometricException
	{
		// Arrange
		Circle c = new Circle(new Point(1, 1), 1);

		// Act
		// Assert
		assertThrows(GeometricException.class, () -> c.rotate(Math.PI, new Point(1, 0));
	}

	@Test
	public void ShouldTranslate() throws GeometricException
	{
		// Arrange
		Circle c = new Circle(new Point(4, 4), 2);
		Circle expected = new Circle(new Point(5, 3), 2);

		// Act
		Circle translate = c.translate(new Vector(1, -1));

		// Assert
		assertEquals(expected, c);
	}

	@Test
	public void ShouldBeImmutableOnTranslate() throws GeometricException
	{
		// Arrange
		Circle c = new Circle(new Point(4, 4), 2);
		Circle replica = new Circle(new Point(4, 4), 2);

		// Act
		c.translate(new Vector(1, -1));

		// Assert
		assertEquals(replica, c);
	}

	@Test
	public void ShouldThrowExceptionWhenTranslatingIntoNegativeCoordinates() throws GeometricException
	{
		// Arrange
		Circle c = new Circle(new Point(1, 1), 1);

		// Act
		// Assert
		assertThrows(GeometricException.class, () -> c.translate(new Vector(0, -1)));
	}

	@Test
	public void ShouldMoveCentroid() throws GeometricException
	{
		// Arrange
		Circle c = new Circle(new Point(4, 4), 2);
		Circle expected = new Circle(new Point(7, 9), 2);

		// Act
		Circle translate = c.moveCentroid(new Point(7, 9));

		// Assert
		assertEquals(expected, c);
	}

	@Test
	public void ShouldBeImmutableOnMoveCentroid() throws GeometricException
	{
		// Arrange
		Circle c = new Circle(new Point(4, 4), 2);
		Circle replica = new Circle(new Point(4, 4), 2);

		// Act
		c.moveCentroid(new Point(2, 2));

		// Assert
		assertEquals(replica, c);
	}

	@Test
	public void ShouldThrowExceptionWhenMovingCentroidIntoNegativeCoordinates() throws GeometricException
	{
		// Arrange
		Circle c = new Circle(new Point(1, 1), 1);

		// Act
		// Assert
		assertThrows(GeometricException.class, () -> c.moveCentroid(new Point(0, 1)));
	}

	@Test
	public void ShouldBeGeometricShape() throws GeometricException
	{
		// Arrange
		IGeometricShape circle = new Circle(new Point(4, 4), 2);
		double expectedPerimeter = 2 * 2 * Math.PI;
		boolean expectedIntersects = true;
		IGeometricShape expectedRotated = new Circle(new Point(6, 4), 2);
		IGeometricShape expectedTranslated = new Circle(new Point(5, 5), 2);
		IGeometricShape expectedMoveCentroid = new Circle(new Point(6, 4), 2);


		// Act
		double perimeter = circle.perimeter();
		boolean intersects = circle.intersects(new Polygon(new Point[] { new Point(7, 2), new Point(4, 7), new Point(7, 7)}));
		IGeometricShape rotated = circle.rotate(Math.PI, new Point(5, 4));
		IGeometricShape rotatedDegrees = circle.rotateDegrees(180, new Point(5, 4));
		IGeometricShape translated = circle.translate(new Vector(1, 1));
		IGeometricShape moveCentroid = circle.moveCentroid(new Point(6, 4));

		// Assert
		assertTrue(MathUtil.areEqual(expectedPerimeter, perimeter));
		assertEquals(expectedIntersects, intersects);
		assertEquals(expectedRotated, rotated);
		assertEquals(expectedRotated, rotatedDegrees);
		assertEquals(expectedTranslated, translated);
		assertEquals(expectedMoveCentroid, moveCentroid);
	}

	@Test
	public void ShouldIntersectPolygon() throws GeometricException
	{
		// Arrange
		Circle c = new Circle(new Point(4, 4), 2);
		Polygon p = new Polygon(new Point[] { new Point(7, 2), new Point(4, 7), new Point(7, 7)});

		// Act
		boolean intercepts = c.intercepts(p);

		// Assert
		assertTrue(intercepts);
	}

	@Test
	public void ShouldNotIntersectPolygon() throws GeometricException
	{
		// Arrange
		Circle c = new Circle(new Point(4, 4), 2);
		Polygon p = new Polygon(new Point[] { new Point(7, 2), new Point(6, 7), new Point(7, 7)});

		// Act
		boolean intercepts = c.intercepts(p);

		// Assert
		assertFalse(intercepts);
	}

	@Test
	public void ShouldNotIntersectPolygonWhenEdgeOverlaps() throws GeometricException
	{
		// Arrange
		Circle c = new Circle(new Point(4, 4), 2);
		Polygon p = new Polygon(new Point[] { new Point(6, 2), new Point(6, 7), new Point(7, 7)});

		// Act
		boolean intercepts = c.intercepts(p);

		// Assert
		assertFalse(intercepts);
	}
}
