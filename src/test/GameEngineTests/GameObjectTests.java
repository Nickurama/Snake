
package GameEngineTests;

import GameEngine.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class GameObjectTests
{

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
				this.sceneHandle.add(new GameObject());
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
