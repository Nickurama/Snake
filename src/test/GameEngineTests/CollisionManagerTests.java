package GameEngineTests;

import GameEngine.*;
import Geometry.*;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class CollisionManagerTests
{
	@Test
	public void ShouldDetectCollisionsAndCallOnCollision() throws GeometricException, GameEngineException
	{
		// Arrange
		class MockCollider extends GameObject implements ICollider
		{
			private Polygon collider;
			boolean onCollisionCalled;
			GameObject lastCollider;
			public MockCollider(Polygon collider)
			{
				this.collider = collider;
				this.onCollisionCalled = false;
				this.lastCollider = null;
			}

			public void onCollision(GameObject other)
			{
				this.onCollisionCalled = true;
				this.lastCollider = other;
			}

			public boolean collided() { return this.onCollisionCalled; }
			public Polygon getCollider() { return this.collider; }
			public GameObject lastCollider() { return this.lastCollider; }
		}

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
		assertTrue(mockCollider0.collided());
		assertEquals(mockCollider0.lastCollider().id(), mockCollider1.id());

		assertTrue(mockCollider1.collided());
		assertEquals(mockCollider1.lastCollider().id(), mockCollider0.id());

		assertFalse(mockCollider2.collided());
	}
}
