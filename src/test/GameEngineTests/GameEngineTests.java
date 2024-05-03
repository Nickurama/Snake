package GameEngineTests;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;

import org.junit.jupiter.api.Test;

import GameEngine.*;
import Geometry.*;
import TestUtil.*;

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
		GameEngine engine = GameEngine.getInstance();
		engine.init(flags, scene);

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
		GameEngine engine = GameEngine.getInstance();
		engine.init(flags, scene);

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
		GameEngine engine = GameEngine.getInstance();
		engine.init(flags, scene);

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
		GameEngine engine = GameEngine.getInstance();
		engine.init(flags, scene);

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
		GameEngine engine = GameEngine.getInstance();
		engine.init(flags, scene);

		// Act
		// Arrange
		assertThrows(GameEngineException.class, () -> engine.step());
	}

	// @Test
	// public void ShouldNotStartWhenAlreadyRunning() throws GameEngineException
	// {
	// 	// Arrange
	// 	GameEngineFlags flags = new GameEngineFlags();
	// 	flags.setUpdateMethod(GameEngineFlags.UpdateMethod.CODE);
	// 	MockGameObject obj = new MockGameObject();
	// 	Scene scene = new Scene();
	// 	scene.add(obj);
	// 	GameEngine engine = GameEngine.getInstance();
	// 	engine.init(flags, scene);
	//
	// 	// Act
	// 	engine.start();
	// 	engine.step();
	// 	engine.start();
	//
	// 	// Arrange
	// 	assertEquals(MockGameObject.Operation.UPDATED, obj.lastOperation());
	// }

	@Test
	public void ShouldThrowIfStartingWhenAlreadyRunning() throws GameEngineException
	{
		// Arrange
		GameEngineFlags flags = new GameEngineFlags();
		flags.setUpdateMethod(GameEngineFlags.UpdateMethod.CODE);
		Scene scene = new Scene();
		GameEngine engine = GameEngine.getInstance();
		engine.init(flags, scene);
		engine.start();

		// Act
		// Arrange
		assertThrows(RuntimeException.class, () -> engine.start());
	}

	@Test
	public void ShouldCallStartOnGameObjectWhenObjectIsInstantiatedAfterStart() throws GameEngineException
	{
		// Arrange
		GameEngineFlags flags = new GameEngineFlags();
		flags.setUpdateMethod(GameEngineFlags.UpdateMethod.CODE);
		MockGameObject obj = new MockGameObject();
		Scene scene = new Scene();
		GameEngine engine = GameEngine.getInstance();
		engine.init(flags, scene);

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
		GameEngine engine = GameEngine.getInstance();
		engine.init(flags, scene);

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
		GameEngine engine = GameEngine.getInstance();
		engine.init(flags, scene);

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
		GameEngine engine = GameEngine.getInstance();
		engine.init(flags, scene);

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
		GameEngine engine = GameEngine.getInstance();
		engine.init(flags, scene);

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
		GameEngine engine = GameEngine.getInstance();
		engine.init(flags, s0);
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
		GameEngine engine = GameEngine.getInstance();
		engine.init(flags, s0);
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
		// MockGameObject obj = new MockGameObject();
		Scene scene = new Scene();
		GameEngine engine = GameEngine.getInstance();
		engine.init(flags, scene);

		String input0 = "debug\n";
		String input1 = "stop\n";
        ByteArrayOutputStream os = TestUtil.setIOstreams(input0 + input1);

		// Act
		engine.start();

		// Arrange
		assertTrue(os.toString().contains("Started debugging"));
	}

	@Test
	public void ShouldRenderGameObjects() throws GeometricException, GameEngineException
	{
		// Arrange
		Polygon poly0 = new Polygon(new Point[]
		{
			new Point(1, 2),
			new Point(2, 8),
			new Point(8, 7),
			new Point(7, 1),
		});
		RenderData<Polygon> rData0 = new RenderData<Polygon>(poly0, true, 1, 'x');
		MockRenderable mockRenderable0 = new MockRenderable(rData0);
		Polygon poly1 = new Polygon(new Point[]
		{
			new Point(1, 7),
			new Point(1, 8),
			new Point(8, 8),
			new Point(8, 7),
		});
		RenderData<Polygon> rData1 = new RenderData<Polygon>(poly1, true, 0, 'y');
		MockRenderable mockRenderable1 = new MockRenderable(rData1);

		Rectangle camera = new Rectangle(new Point[] { new Point(0, 0), new Point(0, 9), new Point(9, 9), new Point(9, 0)});
		Scene sc = new Scene();
		sc.add(mockRenderable0);
		sc.add(mockRenderable1);
		GameEngineFlags flags = new GameEngineFlags();
		flags.setUpdateMethod(GameEngineFlags.UpdateMethod.CODE);
		flags.setTextual(true);

		GameEngine engine = GameEngine.getInstance();
		engine.init(flags, sc, camera);
		engine.start();

		ByteArrayOutputStream out = TestUtil.setIOstreams("");
		String expected =	"          \n" +
							" yxxxyyyy \n" +
							" yxxxxxxx \n" +
							"  xxxxxxx \n" +
							"  xxxxxxx \n" +
							" xxxxxxxx \n" +
							" xxxxxxx  \n" +
							" xxxxxxx  \n" +
							"    xxxx  \n" +
							"          \n";

		// Act
		engine.step();
		String render = out.toString();
		out.reset();

		// Assert
		assertEquals(expected, render);
	}

	@Test
	public void ShouldRenderOverlay() throws GeometricException, GameEngineException
	{
		// Arrange
		Polygon poly0 = new Polygon(new Point[]
		{
			new Point(1, 2),
			new Point(2, 8),
			new Point(8, 7),
			new Point(7, 1),
		});
		RenderData<Polygon> rData0 = new RenderData<Polygon>(poly0, true, 1, 'x');
		MockRenderable mockRenderable0 = new MockRenderable(rData0);
		Polygon poly1 = new Polygon(new Point[]
		{
			new Point(1, 7),
			new Point(1, 8),
			new Point(8, 8),
			new Point(8, 7),
		});
		RenderData<Polygon> rData1 = new RenderData<Polygon>(poly1, true, 0, 'y');
		MockRenderable mockRenderable1 = new MockRenderable(rData1);

		char[][] overlay = new char[][]
		{
			{'d', '\0', '\0', '\0', '\0', '\0', '\0', '\0', '\0', 'c'},
			{'\0', 'o', '\0', '\0', '\0', '\0', '\0', '\0', '\0', '\0'},
			{'\0', '\0', 'o', '\0', '\0', '\0', '\0', '\0', '\0', '\0'},
			{'\0', '\0', '\0', 'o', '\0', '\0', '\0', '\0', '\0', '\0'},
			{'\0', '\0', '\0', '\0', 'o', '\0', '\0', '\0', '\0', '\0'},
			{'\0', '\0', '\0', '\0', '\0', 'o', '\0', '\0', '\0', '\0'},
			{'\0', '\0', '\0', '\0', '\0', '\0', 'o', '\0', '\0', '\0'},
			{'\0', '\0', '\0', '\0', '\0', '\0', '\0', 'o', '\0', '\0'},
			{'\0', '\0', '\0', '\0', '\0', '\0', '\0', '\0', 'o', '\0'},
			{'a', '\0', '\0', '\0', '\0', '\0', '\0', '\0', '\0', 'b'},
		};
		MockOverlay mockOverlay = new MockOverlay(overlay);
		Rectangle camera = new Rectangle(new Point[] { new Point(0, 0), new Point(0, 9), new Point(9, 9), new Point(9, 0)});
		
		Scene sc = new Scene();
		sc.add(mockOverlay);
		sc.add(mockRenderable0);
		sc.add(mockRenderable1);

		GameEngineFlags flags = new GameEngineFlags();
		flags.setUpdateMethod(GameEngineFlags.UpdateMethod.CODE);
		flags.setTextual(true);

		GameEngine engine = GameEngine.getInstance();
		engine.init(flags, sc, camera);
		engine.start();

		ByteArrayOutputStream out = TestUtil.setIOstreams("");
		String expected =	"d        c\n" +
							" oxxxyyyy \n" +
							" yoxxxxxx \n" +
							"  xoxxxxx \n" +
							"  xxoxxxx \n" +
							" xxxxoxxx \n" +
							" xxxxxox  \n" +
							" xxxxxxo  \n" +
							"    xxxxo \n" +
							"a        b\n";

		// Act
		engine.step();
		String render = out.toString();
		out.reset();

		// Assert
		assertEquals(expected, render);
	}

	@Test
	public void ShouldNotifyGameObjectsWhenCollision() throws GeometricException, GameEngineException
	{
		Polygon p0 = new Polygon(new Point[] { new Point(1, 1), new Point(1, 3), new Point(3, 3), new Point(3, 1) });
		MockCollider c0 = new MockCollider(p0, false);
		Polygon p1 = new Polygon(new Point[] { new Point(2, 2), new Point(2, 4), new Point(4, 4), new Point(4, 2) });
		MockCollider c1 = new MockCollider(p1, false);
		Polygon p2 = new Polygon(new Point[] { new Point(10, 10), new Point(10, 11), new Point(11, 11), new Point(11, 10) });
		MockCollider c2 = new MockCollider(p2, false);
		
		Scene sc = new Scene();
		sc.add(c0);
		sc.add(c1);
		sc.add(c2);

		GameEngineFlags flags = new GameEngineFlags();
		flags.setUpdateMethod(GameEngineFlags.UpdateMethod.CODE);

		GameEngine engine = GameEngine.getInstance();
		engine.init(flags, sc);
		engine.start();

		// Act
		engine.step();

		// Assert
		assertTrue(c0.hasCollided());
		assertEquals(c0.getOtherShape(), c1.getCollider());

		assertTrue(c1.hasCollided());
		assertEquals(c1.getOtherShape(), c0.getCollider());

		assertFalse(c2.hasCollided());
	}

	@Test
	public void ShouldStepWithDeltaT() throws GameEngineException
	{
		// Arrange
		GameEngineFlags flags = new GameEngineFlags();
		flags.setUpdateMethod(GameEngineFlags.UpdateMethod.CODE);

		MockGameObject obj = new MockGameObject();
		Scene scene = new Scene();
		scene.add(obj);

		GameEngine engine = GameEngine.getInstance();
		engine.init(flags, scene);
		engine.start();

		long expected = 137;

		// Act
		engine.step(expected);

		// Arrange
		assertEquals(expected, obj.getLastDelta());
	}

	@Test
	public void ShouldCallLateUpdateAfterUpdate() throws GameEngineException
	{
		// Arrange
		class MockGameObjectLate extends MockGameObject
		{
			@Override
			public void lateUpdate()
			{
				super.updateCount = 0;
				super.lateUpdateCount++;
			}
		}
		MockGameObjectLate obj = new MockGameObjectLate();

		Scene scene = new Scene();
		scene.add(obj);

		GameEngineFlags flags = new GameEngineFlags();
		flags.setUpdateMethod(GameEngineFlags.UpdateMethod.CODE);
		GameEngine engine = GameEngine.getInstance();
		engine.init(flags, scene);
		engine.start();

		// Act
		engine.step();

		// Arrange
		assertEquals(1, obj.lateUpdateCount());
		assertEquals(0, obj.updateCount());
	}

	@Test
	public void ShouldCallEarlyUpdateBeforeUpdate() throws GameEngineException
	{
		// Arrange
		class MockGameObjectEarly extends MockGameObject
		{
			@Override
			public void earlyUpdate()
			{
				super.updateCount = -1;
				super.earlyUpdateCount++;
			}
		}
		MockGameObjectEarly obj = new MockGameObjectEarly();

		Scene scene = new Scene();
		scene.add(obj);

		GameEngineFlags flags = new GameEngineFlags();
		flags.setUpdateMethod(GameEngineFlags.UpdateMethod.CODE);
		GameEngine engine = GameEngine.getInstance();
		engine.init(flags, scene);
		engine.start();

		// Act
		engine.step();

		// Arrange
		assertEquals(1, obj.earlyUpdateCount());
		assertEquals(0, obj.updateCount());
	}
}
