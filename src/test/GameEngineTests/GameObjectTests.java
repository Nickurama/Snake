
package GameEngineTests;

import GameEngine.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class GameObjectTests
{
	// Must be first test!
	@Test
	public void ShouldHaveHashCodePerInstantiationOrder()
	{
		// Arrange
		GameObject obj0 = new GameObject();
		GameObject obj1 = new GameObject();
		GameObject obj2 = new GameObject();
		GameObject obj3 = new GameObject();

		// Act
		int hash0 = obj0.hashCode();
		int hash1 = obj1.hashCode();
		int hash2 = obj2.hashCode();
		int hash3 = obj3.hashCode();

		// Assert
		assertEquals(hash0 + 1, hash1);
		assertEquals(hash0 + 2, hash2);
		assertEquals(hash0 + 3, hash3);
	}

	@Test
	public void ShouldOnlyBeEqualsIfSameID()
	{
		// Arrange
		GameObject obj0 = new GameObject();
		GameObject obj1 = new GameObject();
		GameObject obj2 = new GameObject();
		GameObject obj3 = new GameObject();

		// Act
		boolean eq0 = obj0.equals(obj0);
		boolean eq1 = obj2.equals(obj2);
		boolean eq2 = obj0.equals(obj1);
		boolean eq3 = obj0.equals(obj2);
		boolean eq4 = obj3.equals(obj1);

		// Assert
		assertTrue(eq0);
		assertTrue(eq1);
		assertFalse(eq2);
		assertFalse(eq3);
		assertFalse(eq4);
	}
}
