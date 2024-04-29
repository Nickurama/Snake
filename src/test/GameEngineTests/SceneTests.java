package GameEngineTests;

import GameEngine.*;
import Geometry.*;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.Arrays;

public class SceneTests
{
	// @Test
	// public void ShouldAddGameObjects() throws GameEngineException
	// {
	// 	// Arrange
	// 	Scene s = new Scene();
	// 	GameObject obj0 = new MockGameObject();
	// 	GameObject obj1 = new MockGameObject();
	// 	GameObject obj2 = new MockGameObject();
	// 	GameObject obj3 = new MockGameObject();
	//
	// 	// Act
	// 	s.add(obj0);
	// 	s.add(obj1);
	// 	s.add(obj2);
	// 	s.add(obj3);
	//
	// 	// Assert
	// 	assertTrue(s.contains(obj0));
	// 	assertTrue(s.contains(obj1));
	// 	assertTrue(s.contains(obj2));
	// 	assertTrue(s.contains(obj3));
	// }
	//
	// @Test
	// public void ShouldIterateInForLoop() throws GameEngineException
	// {
	// 	// Arrange
	// 	Scene s = new Scene();
	// 	int obj0number = 11;
	// 	int obj1number = 12;
	// 	int obj2number = 13;
	// 	int obj3number = 14;
	// 	GameObject obj0 = new MockGameObject(obj0number);
	// 	GameObject obj1 = new MockGameObject(obj1number);
	// 	GameObject obj2 = new MockGameObject(obj2number);
	// 	GameObject obj3 = new MockGameObject(obj3number);
	// 	int[] ints = new int[4];
	// 	int i = 0;
	// 	s.add(obj0);
	// 	s.add(obj1);
	// 	s.add(obj2);
	// 	s.add(obj3);
	//
	// 	// Act
	// 	for (GameObject obj : s)
	// 		ints[i++] = ((MockGameObject)obj).getNumber();
	//
	// 	// Assert
	// 	assertEquals(ints[0], obj0number);
	// 	assertEquals(ints[1], obj1number);
	// 	assertEquals(ints[2], obj2number);
	// 	assertEquals(ints[3], obj3number);
	// }
	//
	// @Test
	// public void ShouldGetSceneSize() throws GameEngineException
	// {
	// 	// Arrange
	// 	Scene s = new Scene();
	// 	int expectedLen = 7;
	//
	// 	// Act
	// 	int sizeBefore = s.size();
	// 	for (int i = 0; i < expectedLen; i++)
	// 		s.add(new MockGameObject(i));
	// 	int sizeAfter = s.size();
	//
	// 	// Arrange
	// 	assertEquals(0, sizeBefore);
	// 	assertEquals(expectedLen, sizeAfter);
	// }
	//
	// @Test
	// public void ShouldCallOnStartWhenAddingToActiveScene() throws GameEngineException
	// {
	// 	// Arrange
	// 	MockGameObject obj = new MockGameObject();
	// 	Scene sc = new Scene();
	// 	sc.setActive(true);
	//
	// 	// Act
	// 	sc.add(obj);
	//
	// 	// Assert
	// 	assertEquals(obj.lastOperation(), MockGameObject.Operation.STARTED);
	// }
	//
	// @Test
	// public void ShouldRemove() throws GameEngineException
	// {
	// 	// Arrange
	// 	MockGameObject mock = new MockGameObject();
	// 	Scene sc = new Scene();
	// 	sc.add(mock);
	// 	int sizeBefore = sc.size();
	//
	// 	// Act
	// 	sc.remove(mock);
	// 	for (GameObject obj : sc)
	// 		obj.update(0);
	// 	int sizeAfter = sc.size();
	//
	// 	// Assert
	// 	assertEquals(mock.lastOperation(), MockGameObject.Operation.NONE);
	// 	assertEquals(1, sizeBefore);
	// 	assertEquals(0, sizeAfter);
	// }
	//
	// @Test
	// public void ShouldCallOnStopWhenRemovingFromActiveScene()
	// {
	// 	// Arrange
	// 	MockGameObject obj = new MockGameObject();
	// 	Scene sc = new Scene();
	// 	sc.setActive(true);
	//
	// 	// Act
	// 	sc.remove(obj);
	//
	// 	// Assert
	// 	assertEquals(obj.lastOperation(), MockGameObject.Operation.STOPPED);
	// }
	//
	// @Test
	// public void ShouldAddInputListenersToDedicatedList() throws GameEngineException
	// {
	// 	// Arrange
	// 	class MockInputListener extends GameObject implements IInputListener
	// 	{
	// 		private String inputReceived;
	// 		public MockInputListener() { this.inputReceived = ""; }
	// 		public void onInputReceived(String input) { this.inputReceived = input; }
	// 		public String inputReceived() { return this.inputReceived; }
	// 	}
	// 	MockInputListener mockListener = new MockInputListener();
	// 	Scene sc = new Scene();
	//
	// 	// Act
	// 	sc.add(mockListener);
	// 	for (IInputListener listener : sc.inputListeners())
	// 		listener.onInputReceived("mock input");
	//
	// 	// Assert
	// 	assertEquals("mock input", mockListener.inputReceived());
	// }
	//
	// @Test
	// public void ShouldIterateOverInputListeners() throws GameEngineException
	// {
	// 	// Arrange
	// 	class MockInputListener extends GameObject implements IInputListener
	// 	{
	// 		private String inputReceived;
	// 		public MockInputListener() { this.inputReceived = ""; }
	// 		public void onInputReceived(String input) { this.inputReceived = input; }
	// 		public String inputReceived() { return this.inputReceived; }
	// 	}
	// 	MockInputListener mockListener0 = new MockInputListener();
	// 	MockInputListener mockListener1 = new MockInputListener();
	// 	MockInputListener mockListener2 = new MockInputListener();
	// 	MockInputListener mockListener3 = new MockInputListener();
	// 	MockInputListener mockListener4 = new MockInputListener();
	// 	Scene sc = new Scene();
	//
	// 	// Act
	// 	sc.add(mockListener0);
	// 	sc.add(mockListener1);
	// 	sc.add(mockListener2);
	// 	sc.add(mockListener3);
	// 	sc.add(mockListener4);
	// 	int i = 0;
	// 	for (IInputListener listener : sc.inputListeners())
	// 		listener.onInputReceived("mock input " + (i++));
	//
	// 	// Assert
	// 	i = 0;
	// 	for (IInputListener listener : sc.inputListeners())
	// 		assertEquals("mock input " + (i++), ((MockInputListener)listener).inputReceived());
	// }
	//
	// @Test
	// public void ShouldNotBeAbleToShareGameObjectBetweenScenes() throws GameEngineException
	// {
	// 	// Arrange
	// 	Scene s0 = new Scene();
	// 	Scene s1 = new Scene();
	// 	GameObject obj = new GameObject();
	//
	// 	// Act
	// 	s0.add(obj);
	//
	// 	// Assert
	// 	assertThrows(GameEngineException.class, () -> s1.add(obj));
	// }
	//
	// @Test
	// public void ShouldNotBeAbleToAddGameObjectEvenIfRemovedFromOtherScene() throws GameEngineException
	// {
	// 	// Arrange
	// 	Scene s0 = new Scene();
	// 	Scene s1 = new Scene();
	// 	GameObject obj = new GameObject();
	//
	// 	// Act
	// 	s0.add(obj);
	// 	s0.remove(obj);
	//
	// 	// Assert
	// 	assertThrows(GameEngineException.class, () -> s1.add(obj));
	// }
	//
	// @Test
	// public void ShouldGenerateObjectIdsPerOrder() throws GameEngineException
	// {
	// 	// Arrange
	// 	Scene sc = new Scene();
	// 	GameObject obj0 = new GameObject();
	// 	GameObject obj1 = new GameObject();
	// 	GameObject obj2 = new GameObject();
	//
	// 	// Act
	// 	sc.add(obj0);
	// 	sc.add(obj1);
	// 	sc.add(obj2);
	//
	// 	// Arrange
	// 	assertEquals(0, obj0.id());
	// 	assertEquals(1, obj1.id());
	// 	assertEquals(2, obj2.id());
	// }
	//
	// @Test
	// public void ShouldAddRenderablesToDedicatedList() throws GeometricException, GameEngineException	{
	// 	// Arrange
	// 	class MockRenderable extends GameObject implements IRenderable
	// 	{
	// 		private RenderData rData;
	// 		public MockRenderable(RenderData data)
	// 		{
	// 			this.rData = data;
	// 		}
	// 		public RenderData getRenderData() { return this.rData; }
	// 	}
	//
	// 	Polygon expectedShape = new Polygon(new Point[]
	// 	{
	// 		new Point(0, 0),
	// 		new Point(0, 1),
	// 		new Point(1, 0),
	// 	});
	// 	boolean expectedIsRasterized = false;
	// 	int expectedLayer = 6;
	// 	Character expectedChar = 'f';
	// 	RenderData expected = new RenderData(expectedShape, expectedIsRasterized, expectedLayer, expectedChar);
	// 	MockRenderable mockRenderable = new MockRenderable(expected);
	// 	Scene sc = new Scene();
	//
	// 	// Act
	// 	sc.add(mockRenderable);
	// 	RenderData got = null;
	// 	for (IRenderable renderable : sc.renderables())
	// 		got = renderable.getRenderData();
	//
	// 	// Assert
	// 	assertEquals(expectedShape, got.getShape());
	// 	assertEquals(expectedIsRasterized, got.isFilled());
	// 	assertEquals(expectedLayer, got.getLayer());
	// 	assertEquals(expectedChar, got.getCharacter());
	// }
	//
	// @Test
	// public void ShouldGetRenderablesArray() throws GeometricException, GameEngineException	{
	// 	// Arrange
	// 	class MockRenderable extends GameObject implements IRenderable
	// 	{
	// 		private RenderData rData;
	// 		public MockRenderable(RenderData data)
	// 		{
	// 			this.rData = data;
	// 		}
	// 		public RenderData getRenderData() { return this.rData; }
	// 	}
	//
	// 	Polygon expectedShape = new Polygon(new Point[]
	// 	{
	// 		new Point(0, 0),
	// 		new Point(0, 1),
	// 		new Point(1, 0),
	// 	});
	// 	boolean expectedIsRasterized = false;
	// 	int expectedLayer = 6;
	// 	Character expectedChar = 'f';
	// 	RenderData expected = new RenderData(expectedShape, expectedIsRasterized, expectedLayer, expectedChar);
	// 	MockRenderable mockRenderable = new MockRenderable(expected);
	// 	Scene sc = new Scene();
	// 	sc.add(mockRenderable);
	//
	// 	// Act
	// 	RenderData got = sc.renderablesArr()[0];
	//
	// 	// Assert
	// 	assertEquals(expectedShape, got.getShape());
	// 	assertEquals(expectedIsRasterized, got.isFilled());
	// 	assertEquals(expectedLayer, got.getLayer());
	// 	assertEquals(expectedChar, got.getCharacter());
	// }
	//
	// @Test
	// public void ShouldIterateOverRenderables() throws GeometricException, GameEngineException
	// {
	// 	// Arrange
	// 	class MockRenderable extends GameObject implements IRenderable
	// 	{
	// 		private RenderData rData;
	// 		public MockRenderable(RenderData data)
	// 		{
	// 			this.rData = data;
	// 		}
	// 		public RenderData getRenderData() { return this.rData; }
	// 	}
	//
	// 	Polygon expectedShape = new Polygon(new Point[]
	// 	{
	// 		new Point(0, 0),
	// 		new Point(0, 1),
	// 		new Point(1, 0),
	// 	});
	// 	boolean expectedIsRasterized = false;
	// 	int startLayer = 2;
	// 	int expectedLayer = startLayer;
	// 	Character expectedChar = 'f';
	// 	RenderData expected = new RenderData(expectedShape, expectedIsRasterized, expectedLayer++, expectedChar);
	// 	MockRenderable mockRenderable0 = new MockRenderable(expected);
	// 	expected = new RenderData(expectedShape, expectedIsRasterized, expectedLayer++, expectedChar);
	// 	MockRenderable mockRenderable1 = new MockRenderable(expected);
	// 	expected = new RenderData(expectedShape, expectedIsRasterized, expectedLayer++, expectedChar);
	// 	MockRenderable mockRenderable2 = new MockRenderable(expected);
	// 	expected = new RenderData(expectedShape, expectedIsRasterized, expectedLayer++, expectedChar);
	// 	MockRenderable mockRenderable3 = new MockRenderable(expected);
	// 	expected = new RenderData(expectedShape, expectedIsRasterized, expectedLayer++, expectedChar);
	// 	MockRenderable mockRenderable4 = new MockRenderable(expected);
	// 	Scene sc = new Scene();
	//
	// 	// Act
	// 	sc.add(mockRenderable0);
	// 	sc.add(mockRenderable1);
	// 	sc.add(mockRenderable2);
	// 	sc.add(mockRenderable3);
	// 	sc.add(mockRenderable4);
	//
	// 	// Assert
	// 	int i = startLayer;
	// 	for (IRenderable renderable : sc.renderables())
	// 		assertEquals(i++, ((MockRenderable)renderable).getRenderData().getLayer());
	// }
	//
	// @Test
	// public void ShouldIterateOverColliders() throws GeometricException, GameEngineException
	// {
	// 	// Arrange
	// 	class MockCollider extends GameObject implements ICollider
	// 	{
	// 		private Polygon collider;
	// 		boolean onCollisionCalled;
	// 		public MockCollider(Polygon collider)
	// 		{
	// 			this.collider = collider;
	// 			this.onCollisionCalled = false;
	// 		}
	//
	// 		public void onCollision(GameObject other)
	// 		{
	// 			this.onCollisionCalled = true;
	// 		}
	//
	// 		public Polygon getCollider()
	// 		{
	// 			return this.collider;
	// 		}
	// 	}
	//
	// 	Polygon[] colliders = {
	// 		new Polygon(new Point[] { new Point(0, 0), new Point(0, 1), new Point(1, 0), }),
	// 		new Polygon(new Point[] { new Point(1, 1), new Point(1, 2), new Point(2, 1), }),
	// 		new Polygon(new Point[] { new Point(2, 2), new Point(2, 3), new Point(3, 2), })
	// 	};
	// 	MockCollider mockCollider0 = new MockCollider(colliders[0]);
	// 	MockCollider mockCollider1 = new MockCollider(colliders[1]);
	// 	MockCollider mockCollider2 = new MockCollider(colliders[2]);
	// 	Scene sc = new Scene();
	//
	// 	// Act
	// 	sc.add(mockCollider0);
	// 	sc.add(mockCollider1);
	// 	sc.add(mockCollider2);
	//
	// 	// Assert
	// 	int i = 0;
	// 	for (ICollider collider : sc.colliders())
	// 		assertEquals(collider[i++], ((MockCollider)collider).getCollider());
	// }
	//
	// @Test
	// public void ShouldGetNullOverlayIfHasntBeenSet()
	//
	// @Test
	// public void ShouldGetOverlay()
	// {
	// 	// Arrange
	// 	class MockOverlay extends GameObject implements IOverlay
	// 	{
	// 		private Character[][] overlay;
	// 		public MockOverlay(Character[][] overlay)
	// 		{
	// 			this.overlay = overlay;
	// 		}
	//
	// 		public Character[][] getOverlayArray()
	// 		{
	// 			return this.overlay;
	// 		}
	// 	}
	//
	// 	Character[][] overlay = new Character[][] { {0, 1}, {2, 3} };
	// 	MockOverlay mockOverlay = new MockOverlay(overlay);
	// 	GameObject obj0 = new GameObject();
	// 	GameObject obj1 = new GameObject();
	// 	Scene sc = new Scene();
	//
	// 	// Act
	// 	sc.add(obj0);
	// 	sc.add(mockOverlay);
	// 	sc.add(obj1);
	//
	// 	// Assert
	// 	int i = 0;
	// 	assertTrue(Arrays.equals(sc.getOverlay().getOverlayArray(), overlay));
	// }
	//
	// @Test
	// public void ShouldGetLatestOverlayOnly()
	// {
	// 	// Arrange
	// 	class MockOverlay extends GameObject implements IOverlay
	// 	{
	// 		private Character[][] overlay;
	// 		public MockOverlay(Character[][] overlay)
	// 		{
	// 			this.overlay = overlay;
	// 		}
	//
	// 		public Character[][] getOverlayArray()
	// 		{
	// 			return this.overlay;
	// 		}
	// 	}
	//
	// 	Character[][] overlay0 = new Character[][] { {0, 1}, {2, 3} };
	// 	Character[][] overlay1 = new Character[][] { {2, 3}, {4, 5} };
	// 	MockOverlay mockOverlay0 = new MockOverlay(overlay0);
	// 	MockOverlay mockOverlay1 = new MockOverlay(overlay1);
	// 	GameObject obj0 = new GameObject();
	// 	Scene sc = new Scene();
	//
	// 	// Act
	// 	sc.add(obj0);
	// 	sc.add(mockOverlay0);
	// 	sc.add(mockOverlay1);
	//
	// 	// Assert
	// 	int i = 0;
	// 	assertTrue(Arrays.equals(sc.getOverlay().getOverlayArray(), overlay1));
	// }
}
