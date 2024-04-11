
package GameEngineTests;

import GameEngine.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class GameObjectTests
{
	// @Test
	// public void ShouldHaveHashCodePerInstantiationOrder()
	// {
	// 	// Arrange
	// 	GameObject obj0 = new GameObject();
	// 	GameObject obj1 = new GameObject();
	// 	GameObject obj2 = new GameObject();
	// 	GameObject obj3 = new GameObject();
	//
	// 	// Act
	// 	int hash0 = obj0.hashCode();
	// 	int hash1 = obj1.hashCode();
	// 	int hash2 = obj2.hashCode();
	// 	int hash3 = obj3.hashCode();
	//
	// 	// Assert
	// 	assertEquals(hash0 + 1, hash1);
	// 	assertEquals(hash0 + 2, hash2);
	// 	assertEquals(hash0 + 3, hash3);
	// }

	@Test
	public void ShouldHaveSceneHandle() throws GameEngineException
	{
		// Arrange
		Scene sc = new Scene();
		GameObject obj = new GameObject()
		{
			@Override
			public void start()
			{
				try
				{
					this.sceneHandle.add(new GameObject());
				}
				catch (GameEngineException e) {}
			}
		};

		// Act
		sc.add(obj);
		for (GameObject scObj : sc)
			scObj.start();

		// Assert
		assertEquals(2, sc.size());
	}

	@Test
	public void ShouldThrowExceptionWhenSettingSceneMoreThanOnce() throws GameEngineException
	{
		// Arrange
		Scene s0 = new Scene();
		Scene s1 = new Scene();
		GameObject obj = new GameObject();

		// Act
		obj.setScene(s0, 0);

		// Assert
		assertThrows(GameEngineException.class, () -> obj.setScene(s1, 0));
	}
}
