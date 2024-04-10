package GameEngineTests;

import static org.junit.Assert.*;

import org.junit.jupiter.api.Test;

import GameEngine.*;

public class GameEngineTests
{
	@Test
	public void ShouldInitializeGameObjects()
	{
		// Arrange
		GameEngineFlags flags = new GameEngineFlags();
		flags.setUpdateMethod(GameEngineFlags.UpdateMethod.CODE);
		MockGameObject obj = new MockGameObject();
		Scene scene = new Scene();
		scene.add(obj);
		GameEngine engine = new GameEngine(flags, scene);

		// Act
		engine.start();

		// Arrange
		assertEquals(obj.lastOperation(), MockGameObject.Operation.STARTED);
	}

	@Test
	public void ShouldUpdateGameObjects() throws GameEngineException
	{
		// Arrange
		GameEngineFlags flags = new GameEngineFlags();
		flags.setUpdateMethod(GameEngineFlags.UpdateMethod.CODE);
		MockGameObject obj = new MockGameObject();
		Scene scene = new Scene();
		scene.add(obj);
		GameEngine engine = new GameEngine(flags, scene);

		// Act
		engine.start();
		engine.update();

		// Arrange
		assertEquals(MockGameObject.Operation.UPDATED, obj.lastOperation());
	}
	
	@Test
	public void ShouldNotUpdateWhenNotStarted() throws GameEngineException
	{
		// Arrange
		GameEngineFlags flags = new GameEngineFlags();
		flags.setUpdateMethod(GameEngineFlags.UpdateMethod.CODE);
		MockGameObject obj = new MockGameObject();
		Scene scene = new Scene();
		scene.add(obj);
		GameEngine engine = new GameEngine(flags, scene);

		// Act
		engine.update();

		// Arrange
		assertEquals(MockGameObject.Operation.NONE, obj.lastOperation());
	}

	@Test
	public void ShouldNotChangeFlagsWhenUpdatingFlagsReference()
	{
		throw new Error("Test not made");
	}

	@Test
	public void ShouldNotUpdateThroughCodeIfFlagIsSetDifferently()
	{
		throw new Error("Test not made");
	}

	@Test
	public void ShouldNotStartWhenAlreadyRunning() throws GameEngineException
	{
		// Arrange
		GameEngineFlags flags = new GameEngineFlags();
		flags.setUpdateMethod(GameEngineFlags.UpdateMethod.CODE);
		MockGameObject obj = new MockGameObject();
		Scene scene = new Scene();
		scene.add(obj);
		GameEngine engine = new GameEngine(flags, scene);

		// Act
		engine.start();
		engine.update();
		engine.start();

		// Arrange
		assertEquals(MockGameObject.Operation.UPDATED, obj.lastOperation());
	}

	@Test
	public void ShouldCallStartOnGameObjectWhenObjectIsInstantiatedAfterStart()
	{
		// Arrange
		GameEngineFlags flags = new GameEngineFlags();
		flags.setUpdateMethod(GameEngineFlags.UpdateMethod.CODE);
		MockGameObject obj = new MockGameObject();
		Scene scene = new Scene();
		GameEngine engine = new GameEngine(flags, scene);

		// Act
		engine.start();
		scene.add(obj);

		// Arrange
		assertEquals(MockGameObject.Operation.STARTED, obj.lastOperation());
	}
}
