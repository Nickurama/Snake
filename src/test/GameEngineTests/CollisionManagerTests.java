package GameEngineTests;

import GameEngine.*;
import Geometry.*;
import TestUtil.*;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class CollisionManagerTests
{
	@Test
	public void ShouldDetectCollisionsAndCallOnCollision() throws GeometricException, GameEngineException
	{
		// Arrange
		Polygon collider0 = new Polygon(new Point[] { new Point(1, 1), new Point(1, 3), new Point(3, 3), new Point(3, 1) });
		Polygon collider1 = new Polygon(new Point[] { new Point(2, 2), new Point(2, 4), new Point(4, 4), new Point(4, 2) });
		Polygon collider2 = new Polygon(new Point[] { new Point(10, 10), new Point(10, 11), new Point(11, 11), new Point(11, 10) });
		MockCollider mockCollider0 = new MockCollider(collider0, false);
		MockCollider mockCollider1 = new MockCollider(collider1, false);
		MockCollider mockCollider2 = new MockCollider(collider2, false);

		Scene sc = new Scene();
		sc.add(mockCollider0);
		sc.add(mockCollider1);
		sc.add(mockCollider2);

		// Act
		CollisionManager.detectCollisions(sc);

		// Assert
		assertTrue(mockCollider0.hasCollided());
		assertEquals(mockCollider0.getOther().id(), mockCollider1.id());

		assertTrue(mockCollider1.hasCollided());
		assertEquals(mockCollider1.getOther().id(), mockCollider0.id());

		assertFalse(mockCollider2.hasCollided());
	}

	@Test
	public void ShouldDetectCollisionsAndCallOnCollisionWithCircles() throws GeometricException, GameEngineException
	{
		// Arrange
		Polygon collider0 = new Polygon(new Point[] { new Point(1, 1), new Point(1, 3), new Point(3, 3), new Point(3, 1) });
		Polygon collider1 = new Polygon(new Point[] { new Point(2, 2), new Point(2, 4), new Point(4, 4), new Point(4, 2) });
		Polygon collider2 = new Polygon(new Point[] { new Point(10, 10), new Point(10, 11), new Point(11, 11), new Point(11, 10) });
		Circle collider3 = new Circle(new Point(5, 5), 2);
		MockCollider mockCollider0 = new MockCollider(collider0, false);
		MockCollider mockCollider1 = new MockCollider(collider1, false);
		MockCollider mockCollider2 = new MockCollider(collider2, false);
		MockCollider mockCollider3 = new MockCollider(collider3, false);

		Scene sc = new Scene();
		sc.add(mockCollider0);
		sc.add(mockCollider1);
		sc.add(mockCollider2);
		sc.add(mockCollider3);

		// Act
		CollisionManager.detectCollisions(sc);

		// Assert
		assertTrue(mockCollider0.hasCollided());
		assertEquals(mockCollider0.getOther().id(), mockCollider1.id());

		assertTrue(mockCollider1.hasCollided());
		assertEquals(mockCollider1.getOther().id(), mockCollider3.id());

		assertFalse(mockCollider2.hasCollided());

		assertTrue(mockCollider3.hasCollided());
		assertEquals(mockCollider3.getOther().id(), mockCollider1.id());
	}

	@Test
	public void ShouldDetectDeepCollisionWhenPolygonContainsPolygon() throws GeometricException, GameEngineException
	{
		Polygon collider0 = new Polygon(new Point[] { new Point(1, 1), new Point(1, 4), new Point(4, 4), new Point(4, 1) });
		Polygon collider1 = new Polygon(new Point[] { new Point(2, 2), new Point(2, 3), new Point(3, 3), new Point(3, 2) });
		Polygon collider2 = new Polygon(new Point[] { new Point(10, 10), new Point(10, 11), new Point(11, 11), new Point(11, 10) });
		MockCollider mockCollider0 = new MockCollider(collider0, true);
		MockCollider mockCollider1 = new MockCollider(collider1, true);
		MockCollider mockCollider2 = new MockCollider(collider2, true);

		Scene sc = new Scene();
		sc.add(mockCollider0);
		sc.add(mockCollider1);
		sc.add(mockCollider2);

		// Act
		CollisionManager.detectCollisions(sc);

		// Assert
		assertTrue(mockCollider0.hasCollided());
		assertEquals(mockCollider0.getOther().id(), mockCollider1.id());

		assertTrue(mockCollider1.hasCollided());
		assertEquals(mockCollider1.getOther().id(), mockCollider0.id());

		assertFalse(mockCollider2.hasCollided());
	}

	@Test
	public void ShouldDetectDeepCollisionWhenPolygonContainsCircle() throws GeometricException, GameEngineException
	{
		Polygon collider0 = new Polygon(new Point[] { new Point(1, 1), new Point(1, 4), new Point(4, 4), new Point(4, 1) });
		Circle collider1 = new Circle(new Point(2.5, 2.5), 0.5);
		Circle collider2 = new Circle(new Point(10, 10), 1);
		MockCollider mockCollider0 = new MockCollider(collider0, true);
		MockCollider mockCollider1 = new MockCollider(collider1, true);
		MockCollider mockCollider2 = new MockCollider(collider2, true);

		Scene sc = new Scene();
		sc.add(mockCollider0);
		sc.add(mockCollider1);
		sc.add(mockCollider2);

		// Act
		CollisionManager.detectCollisions(sc);

		// Assert
		assertTrue(mockCollider0.hasCollided());
		assertEquals(mockCollider0.getOther().id(), mockCollider1.id());

		assertTrue(mockCollider1.hasCollided());
		assertEquals(mockCollider1.getOther().id(), mockCollider0.id());

		assertFalse(mockCollider2.hasCollided());
	}

	@Test
	public void ShouldDetectDeepCollisionWhenCircleContainsPolygon() throws GeometricException, GameEngineException
	{
		Circle collider0 = new Circle(new Point(3.5, 3.5), 3);
		Polygon collider1 = new Polygon(new Point[] { new Point(3, 3), new Point(3, 4), new Point(4, 4), new Point(4, 3) });
		Polygon collider2 = new Polygon(new Point[] { new Point(10, 10), new Point(10, 11), new Point(11, 11), new Point(11, 10) });
		MockCollider mockCollider0 = new MockCollider(collider0, true);
		MockCollider mockCollider1 = new MockCollider(collider1, true);
		MockCollider mockCollider2 = new MockCollider(collider2, true);

		Scene sc = new Scene();
		sc.add(mockCollider0);
		sc.add(mockCollider1);
		sc.add(mockCollider2);

		// Act
		CollisionManager.detectCollisions(sc);

		// Assert
		assertTrue(mockCollider0.hasCollided());
		assertEquals(mockCollider0.getOther().id(), mockCollider1.id());

		assertTrue(mockCollider1.hasCollided());
		assertEquals(mockCollider1.getOther().id(), mockCollider0.id());

		assertFalse(mockCollider2.hasCollided());
	}

	@Test
	public void ShouldDetectDeepCollisionWhenCircleContainsCircle() throws GeometricException, GameEngineException
	{
		Circle collider0 = new Circle(new Point(3.5, 3.5), 3);
		Circle collider1 = new Circle(new Point(3.5, 3.5), 1);
		Circle collider2 = new Circle(new Point(10, 10), 1);
		MockCollider mockCollider0 = new MockCollider(collider0, true);
		MockCollider mockCollider1 = new MockCollider(collider1, true);
		MockCollider mockCollider2 = new MockCollider(collider2, true);

		Scene sc = new Scene();
		sc.add(mockCollider0);
		sc.add(mockCollider1);
		sc.add(mockCollider2);

		// Act
		CollisionManager.detectCollisions(sc);

		// Assert
		assertTrue(mockCollider0.hasCollided());
		assertEquals(mockCollider0.getOther().id(), mockCollider1.id());

		assertTrue(mockCollider1.hasCollided());
		assertEquals(mockCollider1.getOther().id(), mockCollider0.id());

		assertFalse(mockCollider2.hasCollided());
	}

	@Test
	public void ShouldReturnTrueOnAnyCollision() throws GeometricException, GameEngineException
	{
		// Arrange
		Polygon collider0 = new Polygon(new Point[] { new Point(1, 1), new Point(1, 3), new Point(3, 3), new Point(3, 1) });
		Polygon collider1 = new Polygon(new Point[] { new Point(2, 2), new Point(2, 4), new Point(4, 4), new Point(4, 2) });
		Polygon collider2 = new Polygon(new Point[] { new Point(10, 10), new Point(10, 11), new Point(11, 11), new Point(11, 10) });
		MockCollider mockCollider0 = new MockCollider(collider0, false);
		MockCollider mockCollider1 = new MockCollider(collider1, false);
		MockCollider mockCollider2 = new MockCollider(collider2, false);

		Scene sc = new Scene();
		sc.add(mockCollider0);
		sc.add(mockCollider1);
		sc.add(mockCollider2);

		// Act
		boolean collidesAny0 = CollisionManager.collidesAny(mockCollider0, sc);
		boolean collidesAny1 = CollisionManager.collidesAny(mockCollider1, sc);
		boolean collidesAny2 = CollisionManager.collidesAny(mockCollider2, sc);

		// Assert
		assertTrue(collidesAny0);
		assertTrue(collidesAny1);
		assertFalse(collidesAny2);
	}
}
