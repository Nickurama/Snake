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
		MockCollider mockCollider0 = new MockCollider(collider0);
		MockCollider mockCollider1 = new MockCollider(collider1);
		MockCollider mockCollider2 = new MockCollider(collider2);

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
		MockCollider mockCollider0 = new MockCollider(collider0);
		MockCollider mockCollider1 = new MockCollider(collider1);
		MockCollider mockCollider2 = new MockCollider(collider2);
		MockCollider mockCollider3 = new MockCollider(collider3);

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
}
