package GameEngineTests;

import GameEngine.*;
import Geometry.*;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;

import org.junit.jupiter.api.Test;

public class RendererTests
{
	// @Test
	// public void ShouldSwitchBetweenTextualAndGraphical()
	// {
	// 	// Arrange
	// 	Renderer renderer = new Renderer();
	// 	
	// 	// Act
	// 	renderer.setTextual(true);
	//
	// 	// Assert
	// 	assertTrue(renderer.isTextual());
	// }
	//
	// @Test
	// public void ShouldRenderDataWithoutRasterization() throws GeometricException, GameEngineException
	// {
	// 	// Arrange
	// 	Renderer renderer = new Renderer();
	// 	Scene scene = new Scene();
	// 	class MockRenderable extends GameObject implements IRenderable
	// 	{
	// 		private RenderData rData;
	// 		public MockRenderable() throws GeometricException
	// 		{
	// 			Polygon shape = new Polygon(new Point[]
	// 				{
	// 					new Point(1, 1),
	// 					new Point(1, 2),
	// 					new Point(2, 2),
	// 					new Point(2, 1),
	// 			});
	// 			this.rData = new RenderData(shape, false, 0, 'x');
	// 		}
	// 		public RenderData getRenderData() { return this.rData; }
	// 	}
	// 	MockRenderable mockObj = new MockRenderable();
	//
	// 	scene.add(mockObj);
	// 	ByteArrayOutputStream out = TestUtil.TestUtil.setIOstreams("");
	// 	Rectangle camera = new Rectangle(new Point[]
	// 	{
	// 		new Point(0, 0),
	// 		new Point(3, 3),
	// 	}); // set a way of defining square with just two points
	// 	Character emptyChar = '*';
	// 	String expected = 	"****\n" +
	// 						"*xx*\n" +
	// 						"*xx*\n" +
	// 						"****\n";
	//
	// 	// Act
	// 	renderer.render(scene, camera, emptyChar);
	// 	String outStr = out.toString();
	//
	// 	// Assert
	// 	assertEquals(expected, outStr);
	// }
	//
	// @Test
	// public void ShouldRenderDataWithoutRasterizationMakingHoleInMiddle() throws GeometricException, GameEngineException
	// {
	// 	// Arrange
	// 	Renderer renderer = new Renderer();
	// 	Scene scene = new Scene();
	// 	class MockRenderable extends GameObject implements IRenderable
	// 	{
	// 		private RenderData rData;
	// 		public MockRenderable() throws GeometricException
	// 		{
	// 			Polygon shape = new Polygon(new Point[]
	// 				{
	// 					new Point(0, 2),
	// 					new Point(2, 4),
	// 					new Point(4, 2),
	// 					new Point(2, 0),
	// 			});
	// 			this.rData = new RenderData(shape, false, 0, 'x');
	// 		}
	// 		public RenderData getRenderData() { return this.rData; }
	// 	}
	// 	MockRenderable mockObj = new MockRenderable();
	//
	// 	scene.add(mockObj);
	// 	ByteArrayOutputStream out = TestUtil.TestUtil.setIOstreams("");
	// 	Rectangle camera = new Rectangle(new Point[]
	// 	{
	// 		new Point(0, 0),
	// 		new Point(4, 4),
	// 	}); // set a way of defining square with just two points
	// 	Character emptyChar = '*';
	// 	String expected = 	"**x**\n" +
	// 						"*x*x*\n" +
	// 						"x***x\n" +
	// 						"*x*x*\n" +
	// 						"**x**\n" +
	//
	// 	// Act
	// 	renderer.render(scene, camera, emptyChar);
	// 	String outStr = out.toString();
	//
	// 	// Assert
	// 	assertEquals(expected, outStr);
	// }
	//
	// @Test
	// public void ShouldRenderDataWithoutRasterizationWithDiagonalLines() throws GeometricException, GameEngineException
	// {
	// 	// Arrange
	// 	Renderer renderer = new Renderer();
	// 	Scene scene = new Scene();
	// 	class MockRenderable extends GameObject implements IRenderable
	// 	{
	// 		private RenderData rData;
	// 		public MockRenderable() throws GeometricException
	// 		{
	// 			Polygon shape = new Polygon(new Point[]
	// 				{
	// 					new Point(0, 3),
	// 					new Point(3, 4),
	// 					new Point(4, 1),
	// 					new Point(1, 0),
	// 			});
	// 			this.rData = new RenderData(shape, false, 0, 'x');
	// 		}
	// 		public RenderData getRenderData() { return this.rData; }
	// 	}
	// 	MockRenderable mockObj = new MockRenderable();
	//
	// 	scene.add(mockObj);
	// 	ByteArrayOutputStream out = TestUtil.TestUtil.setIOstreams("");
	// 	Rectangle camera = new Rectangle(new Point[]
	// 	{
	// 		new Point(0, 0),
	// 		new Point(4, 4),
	// 	}); // set a way of defining square with just two points
	// 	Character emptyChar = '*';
	// 	String expected = 	"***x*\n" +
	// 						"xxxx*\n" +
	// 						"*x*x*\n" +
	// 						"*xxxx\n" +
	// 						"*x***\n" +
	//
	// 	// Act
	// 	renderer.render(scene, camera, emptyChar);
	// 	String outStr = out.toString();
	//
	// 	// Assert
	// 	assertEquals(expected, outStr);
	// }
}
