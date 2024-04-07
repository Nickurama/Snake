package GameEngineTests;

import GameEngine.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class SceneTests
{
	@Test
	public void ShouldAddGameObjects()
	{
		// Arrange
		Scene s = new Scene();
		int obj0number = 11;
		int obj1number = 12;
		int obj2number = 13;
		int obj3number = 14;
		IGameObject obj0 = new MockGameObject(obj0number);
		IGameObject obj1 = new MockGameObject(obj1number);
		IGameObject obj2 = new MockGameObject(obj2number);
		IGameObject obj3 = new MockGameObject(obj3number);

		// Act
		s.add(obj0);
		s.add(obj1);
		s.add(obj2);
		s.add(obj3);

		// Assert
		assertEquals(((MockGameObject) s.get(0)).getNumber(), obj0number);
		assertEquals(((MockGameObject) s.get(1)).getNumber(), obj1number);
		assertEquals(((MockGameObject) s.get(2)).getNumber(), obj2number);
		assertEquals(((MockGameObject) s.get(3)).getNumber(), obj3number);
	}

	@Test
	public void ShouldIterateInForLoop()
	{
		// Arrange
		Scene s = new Scene();
		int obj0number = 11;
		int obj1number = 12;
		int obj2number = 13;
		int obj3number = 14;
		IGameObject obj0 = new MockGameObject(obj0number);
		IGameObject obj1 = new MockGameObject(obj1number);
		IGameObject obj2 = new MockGameObject(obj2number);
		IGameObject obj3 = new MockGameObject(obj3number);
		int[] ints = new int[4];
		int i = 0;
		s.add(obj0);
		s.add(obj1);
		s.add(obj2);
		s.add(obj3);

		// Act
		for (IGameObject obj : s)
			ints[i++] = ((MockGameObject)obj).getNumber();

		// Assert
		assertEquals(ints[0], obj0number);
		assertEquals(ints[1], obj1number);
		assertEquals(ints[2], obj2number);
		assertEquals(ints[3], obj3number);
	}

	@Test
	public void ShouldGetSceneSize()
	{
		// Arrange
		Scene s = new Scene();
		int expectedLen = 7;

		// Act
		int sizeBefore = s.size();
		for (int i = 0; i < expectedLen; i++)
			s.add(new MockGameObject(i));
		int sizeAfter = s.size();

		// Arrange
		assertEquals(0, sizeBefore);
		assertEquals(expectedLen, sizeAfter);
	}
}
