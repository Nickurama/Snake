package Geometry;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class CircleTests
{
	@Test
	public void ShouldGetRadius() throws GeometricException
	{
		// Arrange
		double expected = 1.5;
		Circle c = new Circle(new Point(2, 2), expected);

		// Act
		double radius = c.radius();
		
		// Assert
		assertTrue(MathUtil.areEqual(expected, radius));
	}

	@Test
	public void ShouldGetCenter() throws GeometricException
	{
		// Arrange
		Point expected = new Point(2, 2);
		Circle c = new Circle(expected, 1);

		// Act
		Point center = c.getCentroid();
		
		// Assert
		assertEquals(expected, center);
	}

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
		String expected = "Circle: center=(3,3) radius=2.0";

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
	public void ShouldIntersectCircle() throws GeometricException
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
	public void ShouldNotIntersectWhenOverlappingCircle() throws GeometricException
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
	public void ShouldNotIntersectCircle() throws GeometricException
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
	public void ShouldNotIntersectCircleInside() throws GeometricException
	{
		// Arrange
		Circle c0 = new Circle(new Point(3, 3), 3);
		Circle c1 = new Circle(new Point(4, 4), 1);

		// Act
		boolean intersects = c0.intersects(c1);

		// Arrange
		assertFalse(intersects);
	}

	@Test
	public void ShouldIntersectCircleInside() throws GeometricException
	{
		// Arrange
		Circle c0 = new Circle(new Point(3, 3), 3);
		Circle c1 = new Circle(new Point(4, 4), 2);

		// Act
		boolean intersects = c0.intersects(c1);

		// Arrange
		assertTrue(intersects);
	}

	@Test
	public void ShouldIntersectSegment() throws GeometricException
	{
		// Arrange
		Circle c = new Circle(new Point(3, 3), 2);
		LineSegment segment = new LineSegment(new Point(3, 5), new Point(6, 6));

		// Act
		boolean intersects = c.intersects(segment);

		// Arrange
		assertFalse(intersects);
	}

	@Test
	public void ShouldNotIntersectSegmentOnEdge() throws GeometricException
	{
		// Arrange
		Circle c = new Circle(new Point(3, 3), 2);
		LineSegment segment = new LineSegment(new Point(7, 5), new Point(6, 3));

		// Act
		boolean intersects = c.intersects(segment);

		// Arrange
		assertFalse(intersects);
	}

	@Test
	public void ShouldNotIntersectSegment() throws GeometricException
	{
		// Arrange
		Circle c = new Circle(new Point(3, 3), 2);
		LineSegment segment = new LineSegment(new Point(9, 3), new Point(9, 1));

		// Act
		boolean intersects = c.intersects(segment);

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
		assertThrows(GeometricException.class, () -> c.rotate(Math.PI, new Point(1, 0)));
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
		assertEquals(expected, translate);
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
		assertEquals(expected, translate);
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
		IGeometricShape<Circle> circle = new Circle(new Point(4, 4), 2);
		double expectedPerimeter = 2 * 2 * Math.PI;
		boolean expectedIntersects = true;
		boolean expectedContains = true;
		IGeometricShape<Circle> expectedRotated = new Circle(new Point(6, 4), 2);
		IGeometricShape<Circle> expectedTranslated = new Circle(new Point(5, 5), 2);
		IGeometricShape<Circle> expectedMoveCentroid = new Circle(new Point(6, 4), 2);
		boolean expectedIntersectsInclusive = true;

		// Act
		double perimeter = circle.perimeter();
		boolean intersects = circle.intersects(new Polygon(new Point[] { new Point(7, 2), new Point(4, 7), new Point(7, 7)}));
		boolean contains = circle.contains(new Polygon(new Point[] { new Point(5, 3), new Point(4, 6), new Point(3, 4)}));
		IGeometricShape<?> rotated = circle.rotate(Math.PI, new Point(5, 4));
		IGeometricShape<?> rotatedDegrees = circle.rotateDegrees(180, new Point(5, 4));
		IGeometricShape<?> translated = circle.translate(new Vector(1, 1));
		IGeometricShape<?> moveCentroid = circle.moveCentroid(new Point(6, 4));
		boolean equals = circle.equals(new Polygon(new Point[] { new Point(1, 1), new Point(1, 2), new Point(2, 1) }));
		boolean intersectsInclusive = circle.intersectsInclusive(new Polygon(new Point[] { new Point(6, 3), new Point(6, 5), new Point(8, 5), new Point(8, 3)}));

		// Assert
		assertTrue(MathUtil.areEqual(expectedPerimeter, perimeter));
		assertEquals(expectedIntersects, intersects);
		assertEquals(expectedContains, contains);
		assertEquals(expectedRotated, rotated);
		assertEquals(expectedRotated, rotatedDegrees);
		assertEquals(expectedTranslated, translated);
		assertEquals(expectedMoveCentroid, moveCentroid);
		assertEquals(expectedIntersectsInclusive, intersectsInclusive);
		assertFalse(equals);
	}

	@Test
	public void ShouldIntersectPolygon() throws GeometricException
	{
		// Arrange
		Circle c = new Circle(new Point(4, 4), 2);
		Polygon p = new Polygon(new Point[] { new Point(7, 2), new Point(4, 7), new Point(7, 7)});

		// Act
		boolean intercepts = c.intersects(p);

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
		boolean intercepts = c.intersects(p);

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
		boolean intercepts = c.intersects(p);

		// Assert
		assertFalse(intercepts);
	}

	@Test
	public void ShouldContainCircle() throws GeometricException
	{
		// Arrange
		Circle outer = new Circle(new Point(3, 3), 3);
		Circle inner = new Circle(new Point(2, 4), 1.5);

		// Act
		boolean contains = outer.contains(inner);

		// Assert
		assertTrue(contains);
	}

	@Test
	public void ShouldContainItself() throws GeometricException
	{
		// Arrange
		Circle outer = new Circle(new Point(3, 3), 1.3);
		Circle inner = new Circle(new Point(3, 3), 1.3);

		// Act
		boolean contains = outer.contains(inner);

		// Assert
		assertTrue(contains);
	}

	@Test
	public void ShouldContainCircleOnEdge() throws GeometricException
	{
		// Arrange
		Circle outer = new Circle(new Point(3, 3), 3);
		Circle inner = new Circle(new Point(1, 3), 1);

		// Act
		boolean contains = outer.contains(inner);

		// Assert
		assertTrue(contains);
	}

	@Test
	public void ShouldNotContainCircle() throws GeometricException
	{
		// Arrange
		Circle outer = new Circle(new Point(3, 3), 3);
		Circle inner = new Circle(new Point(2, 5), 1);

		// Act
		boolean contains = outer.contains(inner);

		// Assert
		assertFalse(contains);
	}

	@Test
	public void ShouldContainPoint() throws GeometricException
	{
		// Arrange
		Circle outer = new Circle(new Point(3, 3), 3);
		Point inner = new Point(4, 5);

		// Act
		boolean contains = outer.contains(inner);

		// Assert
		assertTrue(contains);
	}

	@Test
	public void ShouldContainPointOnEdge() throws GeometricException
	{
		// Arrange
		Circle outer = new Circle(new Point(3, 3), 3);
		Point inner = new Point(3, 6);

		// Act
		boolean contains = outer.contains(inner);

		// Assert
		assertTrue(contains);
	}

	@Test
	public void ShouldNotContainPoint() throws GeometricException
	{
		// Arrange
		Circle outer = new Circle(new Point(3, 3), 3);
		Point inner = new Point(0.5, 0.5);

		// Act
		boolean contains = outer.contains(inner);

		// Assert
		assertFalse(contains);
	}

	@Test
	public void ShouldContainPolygon() throws GeometricException
	{
		// Arrange
		Circle outer = new Circle(new Point(3, 3), 3);
		Polygon inner = new Polygon(new Point[]
		{
			new Point(1, 3),
			new Point(2, 5),
			new Point(3, 5),
			new Point(5, 3),
			new Point(3, 4),
			new Point(3, 1),
		});

		// Act
		boolean contains = outer.contains(inner);

		// Assert
		assertTrue(contains);
	}

	@Test
	public void ShouldNotContainPolygon() throws GeometricException
	{
		// Arrange
		Circle outer = new Circle(new Point(3, 3), 3);
		Polygon inner = new Polygon(new Point[]
		{
			new Point(1, 3),
			new Point(2, 5),
			new Point(3, 5),
			new Point(5, 3),
			new Point(3, 4),
			new Point(3, 1),
		});
		Polygon p0 = inner.translate(new Vector(0, 1));
		Polygon p1 = inner.translate(new Vector(1, 2));
		Polygon p2 = inner.translate(new Vector(10, 10));

		// Act
		boolean contains0 = outer.contains(p0);
		boolean contains1 = outer.contains(p1);
		boolean contains2 = outer.contains(p2);

		// Assert
		assertFalse(contains0);
		assertFalse(contains1);
		assertFalse(contains2);
	}

	@Test
	public void ShouldContainPolygonWhenVerticesOnEdge() throws GeometricException
	{
		// Arrange
		Circle outer = new Circle(new Point(3, 3), 3);
		Polygon inner = new Polygon(new Point[]
		{
			new Point(3, 6),
			new Point(5, 5.2360679775),
			new Point(6, 3),
			new Point(3, 1),
			new Point(2, 4),
		});

		// Act
		boolean contains = outer.contains(inner);

		// Assert
		assertTrue(contains);
	}

	@Test
	public void ShouldIntersectInclusive() throws GeometricException
	{
		// Arrange
		Circle circle0 = new Circle(new Point(1, 1), 1);
		Circle circle1 = new Circle(new Point(3, 1), 1);

		// Act
		boolean intersects = circle0.intersectsInclusive(circle1);

		// Assert
		assertTrue(intersects);
	}
}
