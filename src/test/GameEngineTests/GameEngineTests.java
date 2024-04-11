package GameEngineTests;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;

import org.junit.jupiter.api.Test;

import GameEngine.*;
import TestUtil.TestUtil;

public class GameEngineTests
{
	@Test
	public void ShouldInitializeGameObjects() throws GameEngineException
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
		engine.step();

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
		engine.step();

		// Arrange
		assertEquals(MockGameObject.Operation.NONE, obj.lastOperation());
	}

	@Test
	public void ShouldNotChangeFlagsWhenUpdatingFlagsReference() throws GameEngineException
	{
		// Arrange
		GameEngineFlags flags = new GameEngineFlags();
		flags.setUpdateMethod(GameEngineFlags.UpdateMethod.STEP);
		MockGameObject obj = new MockGameObject();
		Scene scene = new Scene();
		scene.add(obj);
		GameEngine engine = new GameEngine(flags, scene);

		// Act
		flags.setUpdateMethod(GameEngineFlags.UpdateMethod.CODE);

		// Arrange
		assertThrows(GameEngineException.class, () -> engine.step());
	}

	@Test
	public void ShouldNotUpdateThroughCodeIfFlagIsSetDifferently() throws GameEngineException
	{
		// Arrange
		GameEngineFlags flags = new GameEngineFlags();
		flags.setUpdateMethod(GameEngineFlags.UpdateMethod.STEP);
		MockGameObject obj = new MockGameObject();
		Scene scene = new Scene();
		scene.add(obj);
		GameEngine engine = new GameEngine(flags, scene);

		// Act
		// Arrange
		assertThrows(GameEngineException.class, () -> engine.step());
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
		engine.step();
		engine.start();

		// Arrange
		assertEquals(MockGameObject.Operation.UPDATED, obj.lastOperation());
	}

	@Test
	public void ShouldCallStartOnGameObjectWhenObjectIsInstantiatedAfterStart() throws GameEngineException
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

	@Test
	public void ShouldUpdateStepped() throws GameEngineException
	{
		// Arrange
		GameEngineFlags flags = new GameEngineFlags();
		flags.setUpdateMethod(GameEngineFlags.UpdateMethod.STEP);
		MockGameObject obj = new MockGameObject();
		Scene scene = new Scene();
		scene.add(obj);
		GameEngine engine = new GameEngine(flags, scene);

		String input0 = "step\n";
		String input1 = "stop\n";
        TestUtil.setIOstreams(input0 + input0 + input1);

		// Act
		engine.start();

		// Arrange
		assertEquals(MockGameObject.Operation.STOPPED, obj.lastOperation());
		assertEquals(2, obj.updateCount());
	}

	@Test
	public void ShouldStop() throws GameEngineException
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
		engine.stop();

		// Arrange
		assertEquals(MockGameObject.Operation.STOPPED, obj.lastOperation());
	}

	@Test
	public void ShouldNotTriggerInputListenerOnStepModeWhenOnlyEngineCommands() throws GameEngineException
	{
		// Arrange
		GameEngineFlags flags = new GameEngineFlags();
		flags.setUpdateMethod(GameEngineFlags.UpdateMethod.STEP);
		MockGameObject obj = new MockGameObject();
		Scene scene = new Scene();
		scene.add(obj);
		GameEngine engine = new GameEngine(flags, scene);

		String input0 = "step\n";
		String input1 = "stop\n";
        TestUtil.setIOstreams(input0 + input0 + input1);

		// Act
		engine.start();

		// Arrange
		assertEquals(null, obj.inputReceived());
	}

	@Test
	public void ShouldTriggerInputListenerOnStepMode() throws GameEngineException
	{
		// Arrange
		GameEngineFlags flags = new GameEngineFlags();
		flags.setUpdateMethod(GameEngineFlags.UpdateMethod.STEP);
		MockGameObject obj = new MockGameObject();
		Scene scene = new Scene();
		scene.add(obj);
		GameEngine engine = new GameEngine(flags, scene);

		String input0 = "test input 137\n";
		String input1 = "stop\n";
        TestUtil.setIOstreams(input0 + input1);

		// Act
		engine.start();

		// Arrange
		assertEquals("test input 137", obj.inputReceived());
	}

	@Test
	public void ShouldSetScene() throws GameEngineException
	{
		// Arrange
		Scene s0 = new Scene();
		Scene s1 = new Scene();
		GameEngineFlags flags = new GameEngineFlags();
		flags.setUpdateMethod(GameEngineFlags.UpdateMethod.CODE);
		GameEngine engine = new GameEngine(flags, s0);
		engine.start();

		// Act
		engine.stop();
		engine.setScene(s1);
		engine.start();

		// Assert
		assertTrue(s1.isActive());
		assertFalse(s0.isActive());
	}

	@Test
	public void ShouldNotSetSceneIfEngineIsNotStopped() throws GameEngineException
	{
		// Arrange
		Scene s0 = new Scene();
		Scene s1 = new Scene();
		GameEngineFlags flags = new GameEngineFlags();
		flags.setUpdateMethod(GameEngineFlags.UpdateMethod.CODE);
		GameEngine engine = new GameEngine(flags, s0);
		engine.start();

		// Act
		// Assert
		assertThrows(GameEngineException.class, () -> engine.setScene(s1));
	}

	@Test
	public void ShouldTakeDebugCommandInStepMode()
	{
		// Arrange
		GameEngineFlags flags = new GameEngineFlags();
		flags.setUpdateMethod(GameEngineFlags.UpdateMethod.STEP);
		MockGameObject obj = new MockGameObject();
		Scene scene = new Scene();
		GameEngine engine = new GameEngine(flags, scene);

		String input0 = "debug\n";
		String input1 = "stop\n";
        ByteArrayOutputStream os = TestUtil.setIOstreams(input0 + input1);

		// Act
		engine.start();

		// Arrange
		assertTrue(os.toString().contains("Started debugging"));
	}
}
