package GameEngineTests;

import GameEngine.*;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class SceneTests
{
	@Test
	public void ShouldAddGameObjects() throws GameEngineException
	{
		// Arrange
		Scene s = new Scene();
		GameObject obj0 = new MockGameObject();
		GameObject obj1 = new MockGameObject();
		GameObject obj2 = new MockGameObject();
		GameObject obj3 = new MockGameObject();

		// Act
		s.add(obj0);
		s.add(obj1);
		s.add(obj2);
		s.add(obj3);

		// Assert
		assertTrue(s.contains(obj0));
		assertTrue(s.contains(obj1));
		assertTrue(s.contains(obj2));
		assertTrue(s.contains(obj3));
	}

	@Test
	public void ShouldIterateInForLoop() throws GameEngineException
	{
		// Arrange
		Scene s = new Scene();
		int obj0number = 11;
		int obj1number = 12;
		int obj2number = 13;
		int obj3number = 14;
		GameObject obj0 = new MockGameObject(obj0number);
		GameObject obj1 = new MockGameObject(obj1number);
		GameObject obj2 = new MockGameObject(obj2number);
		GameObject obj3 = new MockGameObject(obj3number);
		int[] ints = new int[4];
		int i = 0;
		s.add(obj0);
		s.add(obj1);
		s.add(obj2);
		s.add(obj3);

		// Act
		for (GameObject obj : s)
			ints[i++] = ((MockGameObject)obj).getNumber();

		// Assert
		assertEquals(ints[0], obj0number);
		assertEquals(ints[1], obj1number);
		assertEquals(ints[2], obj2number);
		assertEquals(ints[3], obj3number);
	}

	@Test
	public void ShouldGetSceneSize() throws GameEngineException
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

	@Test
	public void ShouldCallOnStartWhenAddingToActiveScene() throws GameEngineException
	{
		// Arrange
		MockGameObject obj = new MockGameObject();
		Scene sc = new Scene();
		sc.setActive(true);

		// Act
		sc.add(obj);

		// Assert
		assertEquals(obj.lastOperation(), MockGameObject.Operation.STARTED);
	}

	@Test
	public void ShouldRemove() throws GameEngineException
	{
		// Arrange
		MockGameObject mock = new MockGameObject();
		Scene sc = new Scene();
		sc.add(mock);
		int sizeBefore = sc.size();

		// Act
		sc.remove(mock);
		for (GameObject obj : sc)
			obj.update(0);
		int sizeAfter = sc.size();

		// Assert
		assertEquals(mock.lastOperation(), MockGameObject.Operation.NONE);
		assertEquals(1, sizeBefore);
		assertEquals(0, sizeAfter);
	}

	@Test
	public void ShouldCallOnStopWhenRemovingFromActiveScene()
	{
		// Arrange
		MockGameObject obj = new MockGameObject();
		Scene sc = new Scene();
		sc.setActive(true);

		// Act
		sc.remove(obj);

		// Assert
		assertEquals(obj.lastOperation(), MockGameObject.Operation.STOPPED);
	}

	@Test
	public void ShouldAddInputListenersToDedicatedList() throws GameEngineException
	{
		// Arrange
		class MockInputListener extends GameObject implements IInputListener
		{
			private String inputReceived;
			public MockInputListener() { this.inputReceived = ""; }
			public void onInputReceived(String input) { this.inputReceived = input; }
			public String inputReceived() { return this.inputReceived; }
		}
		MockInputListener mockListener = new MockInputListener();
		Scene sc = new Scene();

		// Act
		sc.add(mockListener);
		for (IInputListener listener : sc.inputListeners())
			listener.onInputReceived("mock input");

		// Assert
		assertEquals("mock input", mockListener.inputReceived());
	}

	@Test
	public void ShouldNotBeAbleToShareGameObjectBetweenScenes() throws GameEngineException
	{
		// Arrange
		Scene s0 = new Scene();
		Scene s1 = new Scene();
		GameObject obj = new GameObject();

		// Act
		s0.add(obj);

		// Assert
		assertThrows(GameEngineException.class, () -> s1.add(obj));
	}

	@Test
	public void ShouldNotBeAbleToAddGameObjectEvenIfRemovedFromOtherScene() throws GameEngineException
	{
		// Arrange
		Scene s0 = new Scene();
		Scene s1 = new Scene();
		GameObject obj = new GameObject();

		// Act
		s0.add(obj);
		s0.remove(obj);

		// Assert
		assertThrows(GameEngineException.class, () -> s1.add(obj));
	}

	@Test
	public void ShouldGenerateObjectIdsPerOrder() throws GameEngineException
	{
		// Arrange
		Scene sc = new Scene();
		GameObject obj0 = new GameObject();
		GameObject obj1 = new GameObject();
		GameObject obj2 = new GameObject();

		// Act
		sc.add(obj0);
		sc.add(obj1);
		sc.add(obj2);

		// Arrange
		assertEquals(0, obj0.id());
		assertEquals(1, obj1.id());
		assertEquals(2, obj2.id());
	}
}
