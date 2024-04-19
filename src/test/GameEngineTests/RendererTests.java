package GameEngineTests;

import GameEngine.*;
import Geometry.*;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class RendererTests
{
	@Test
	public void ShouldRasterizeHorizontalLines() throws GeometricException
	{
		// Arrange
		LineSegment s0 = new LineSegment(new Point(10, 10), new Point(20, 10));
		LineSegment s1 = new LineSegment(new Point(10, 10), new Point(0, 10));
		Point[] expected0 = new Point[] 
		{
			new Point(10, 10),
			new Point(11, 10),
			new Point(12, 10),
			new Point(13, 10),
			new Point(14, 10),
			new Point(15, 10),
			new Point(16, 10),
			new Point(17, 10),
			new Point(18, 10),
			new Point(19, 10),
			new Point(20, 10),
		};

		Point[] expected1 = new Point[] 
		{
			new Point(10, 10),
			new Point(9, 10),
			new Point(8, 10),
			new Point(7, 10),
			new Point(6, 10),
			new Point(5, 10),
			new Point(4, 10),
			new Point(3, 10),
			new Point(2, 10),
			new Point(1, 10),
			new Point(0, 10),
		};

		// Act
		Point[] points0 = Renderer.rasterize(s0);
		Point[] points1 = Renderer.rasterize(s1);

		// Assert
		assertEquals(expected0.length, points0.length);
		for (Point p : expected0)
			assertTrue(Arrays.asList(points0).contains(p));
		assertEquals(expected1.length, points1.length);
		for (Point p : expected1)
			assertTrue(Arrays.asList(points1).contains(p));
	}

	@Test
	public void ShouldRasterizeVerticalLines() throws GeometricException
	{
		// Arrange
		LineSegment s0 = new LineSegment(new Point(10, 10), new Point(10, 20));
		LineSegment s1 = new LineSegment(new Point(10, 10), new Point(10, 0));
		Point[] expected0 = new Point[] 
		{
			new Point(10, 10),
			new Point(10, 11),
			new Point(10, 12),
			new Point(10, 13),
			new Point(10, 14),
			new Point(10, 15),
			new Point(10, 16),
			new Point(10, 17),
			new Point(10, 18),
			new Point(10, 19),
			new Point(10, 20),
		};

		Point[] expected1 = new Point[] 
		{
			new Point(10, 10),
			new Point(10, 9),
			new Point(10, 8),
			new Point(10, 7),
			new Point(10, 6),
			new Point(10, 5),
			new Point(10, 4),
			new Point(10, 3),
			new Point(10, 2),
			new Point(10, 1),
			new Point(10, 0),
		};

		// Act
		Point[] points0 = Renderer.rasterize(s0);
		Point[] points1 = Renderer.rasterize(s1);

		// Assert
		assertEquals(expected0.length, points0.length);
		for (Point p : expected0)
			assertTrue(Arrays.asList(points0).contains(p));
		assertEquals(expected1.length, points1.length);
		for (Point p : expected1)
			assertTrue(Arrays.asList(points1).contains(p));
	}

	@Test
	public void ShouldRasterizeOverDiagonalLines() throws GeometricException
	{
		// Arrange
		LineSegment s0 = new LineSegment(new Point(10, 10), new Point(20, 20));
		LineSegment s1 = new LineSegment(new Point(10, 10), new Point(0, 20));
		LineSegment s2 = new LineSegment(new Point(10, 10), new Point(0, 0));
		LineSegment s3 = new LineSegment(new Point(10, 10), new Point(20, 0));

		Point[] expected0 = new Point[] 
		{
			new Point(10, 10),
			new Point(11, 11),
			new Point(12, 12),
			new Point(13, 13),
			new Point(14, 14),
			new Point(15, 15),
			new Point(16, 16),
			new Point(17, 17),
			new Point(18, 18),
			new Point(19, 19),
			new Point(20, 20),
		};

		Point[] expected1 = new Point[] 
		{
			new Point(10, 10),
			new Point(9, 11),
			new Point(8, 12),
			new Point(7, 13),
			new Point(6, 14),
			new Point(5, 15),
			new Point(4, 16),
			new Point(3, 17),
			new Point(2, 18),
			new Point(1, 19),
			new Point(0, 20),
		};

		Point[] expected2 = new Point[] 
		{
			new Point(10, 10),
			new Point(9, 9),
			new Point(8, 8),
			new Point(7, 7),
			new Point(6, 6),
			new Point(5, 5),
			new Point(4, 4),
			new Point(3, 3),
			new Point(2, 2),
			new Point(1, 1),
			new Point(0, 0),
		};

		Point[] expected3 = new Point[] 
		{
			new Point(10, 10),
			new Point(11, 9),
			new Point(12, 8),
			new Point(13, 7),
			new Point(14, 6),
			new Point(15, 5),
			new Point(16, 4),
			new Point(17, 3),
			new Point(18, 2),
			new Point(19, 1),
			new Point(20, 0),
		};

		// Act
		Point[] points0 = Renderer.rasterize(s0);
		Point[] points1 = Renderer.rasterize(s1);
		Point[] points2 = Renderer.rasterize(s2);
		Point[] points3 = Renderer.rasterize(s3);

		// Assert
		assertEquals(expected0.length, points0.length);
		for (Point p : expected0)
			assertTrue(Arrays.asList(points0).contains(p));
		assertEquals(expected1.length, points1.length);
		for (Point p : expected1)
			assertTrue(Arrays.asList(points1).contains(p));
		assertEquals(expected2.length, points2.length);
		for (Point p : expected2)
			assertTrue(Arrays.asList(points2).contains(p));
		assertEquals(expected3.length, points3.length);
		for (Point p : expected3)
			assertTrue(Arrays.asList(points3).contains(p));
	}

	@Test
	public void ShouldRasterizeSegmentOverFirstOctant() throws GeometricException
	{
		// Arrange
		LineSegment segment = new LineSegment(new Point(10, 10), new Point(20, 15));
		Point[] expected = new Point[] 
		{
			new Point(10, 10),
			new Point(11, 11),
			new Point(12, 11),
			new Point(13, 12),
			new Point(14, 12),
			new Point(15, 13),
			new Point(16, 13),
			new Point(17, 14),
			new Point(18, 14),
			new Point(19, 15),
			new Point(20, 15),
		};

		// Act
		Point[] points = Renderer.rasterize(segment);

		// Assert
		assertEquals(expected.length, points.length);
		for (Point p : expected)
			assertTrue(Arrays.asList(points).contains(p));
	}

	@Test
	public void ShouldRasterizeSegmentOverSecondOctant() throws GeometricException
	{
		// Arrange
		LineSegment segment = new LineSegment(new Point(10, 10), new Point(18, 20));
		Point[] expected = new Point[] 
		{
			new Point(10, 10),
			new Point(11, 11),
			new Point(12, 12),
			new Point(12, 13),
			new Point(13, 14),
			new Point(14, 15),
			new Point(15, 16),
			new Point(16, 17),
			new Point(16, 18),
			new Point(17, 19),
			new Point(18, 20),
		};

		// Act
		Point[] points = Renderer.rasterize(segment);

		// Assert
		assertEquals(expected.length, points.length);
		for (Point p : expected)
			assertTrue(Arrays.asList(points).contains(p));
	}

	@Test
	public void ShouldRasterizeSegmentOverThirdOctant() throws GeometricException
	{
		// Arrange
		LineSegment segment = new LineSegment(new Point(10, 10), new Point(8, 20));
		Point[] expected = new Point[] 
		{
			new Point(10, 10),
			new Point(10, 11),
			new Point(10, 12),
			new Point(9, 13),
			new Point(9, 14),
			new Point(9, 15),
			new Point(9, 16),
			new Point(9, 17),
			new Point(8, 18),
			new Point(8, 19),
			new Point(8, 20),
		};

		// Act
		Point[] points = Renderer.rasterize(segment);

		// Assert
		assertEquals(expected.length, points.length);
		for (Point p : expected)
			assertTrue(Arrays.asList(points).contains(p));
	}

	@Test
	public void ShouldRasterizeSegmentOverFourthOctant() throws GeometricException
	{
		// Arrange
		LineSegment segment = new LineSegment(new Point(10, 10), new Point(0, 19));
		Point[] expected = new Point[] 
		{
			new Point(10, 10),
			new Point(9, 11),
			new Point(8, 12),
			new Point(7, 13),
			new Point(6, 14),
			new Point(5, 14),
			new Point(4, 15),
			new Point(3, 16),
			new Point(2, 17),
			new Point(1, 18),
			new Point(0, 19),
		};

		// Act
		Point[] points = Renderer.rasterize(segment);

		// Assert
		assertEquals(expected.length, points.length);
		for (Point p : expected)
			assertTrue(Arrays.asList(points).contains(p));
	}

	@Test
	public void ShouldRasterizeSegmentOverFifthOctant() throws GeometricException
	{
		// Arrange
		LineSegment segment = new LineSegment(new Point(10, 10), new Point(0, 6));
		Point[] expected = new Point[] 
		{
			new Point(10, 10),
			new Point(9, 10),
			new Point(8, 9),
			new Point(7, 9),
			new Point(6, 8),
			new Point(5, 8),
			new Point(4, 8),
			new Point(3, 7),
			new Point(2, 7),
			new Point(1, 6),
			new Point(0, 6),
		};

		// Act
		Point[] points = Renderer.rasterize(segment);

		// Assert
		assertEquals(expected.length, points.length);
		for (Point p : expected)
			assertTrue(Arrays.asList(points).contains(p));
	}

	@Test
	public void ShouldRasterizeSegmentOverSixthOctant() throws GeometricException
	{
		// Arrange
		LineSegment segment = new LineSegment(new Point(10, 10), new Point(9, 0));
		Point[] expected = new Point[] 
		{
			new Point(10, 10),
			new Point(10, 9),
			new Point(10, 8),
			new Point(10, 7),
			new Point(10, 6),
			new Point(10, 5),
			new Point(9, 4),
			new Point(9, 3),
			new Point(9, 2),
			new Point(9, 1),
			new Point(9, 0),
		};

		// Act
		Point[] points = Renderer.rasterize(segment);

		// Assert
		assertEquals(expected.length, points.length);
		for (Point p : expected)
			assertTrue(Arrays.asList(points).contains(p));
	}

	@Test
	public void ShouldRasterizeSegmentOverSeventhOctant() throws GeometricException
	{
		// Arrange
		LineSegment segment = new LineSegment(new Point(10, 10), new Point(13, 0));
		Point[] expected = new Point[] 
		{
			new Point(10, 10),
			new Point(10, 9),
			new Point(11, 8),
			new Point(11, 7),
			new Point(11, 6),
			new Point(11, 5),
			new Point(12, 4),
			new Point(12, 3),
			new Point(12, 2),
			new Point(13, 1),
			new Point(13, 0),
		};

		// Act
		Point[] points = Renderer.rasterize(segment);

		// Assert
		assertEquals(expected.length, points.length);
		for (Point p : expected)
			assertTrue(Arrays.asList(points).contains(p));
	}

	@Test
	public void ShouldRasterizeSegmentOverEightOctant() throws GeometricException
	{
		// Arrange
		LineSegment segment = new LineSegment(new Point(10, 10), new Point(20, 7));
		Point[] expected = new Point[] 
		{
			new Point(10, 10),
			new Point(11, 10),
			new Point(12, 9),
			new Point(13, 9),
			new Point(14, 9),
			new Point(15, 8),
			new Point(16, 8),
			new Point(17, 8),
			new Point(18, 8),
			new Point(19, 7),
			new Point(20, 7),
		};

		// Act
		Point[] points = Renderer.rasterize(segment);

		// Assert
		assertEquals(expected.length, points.length);
		for (Point p : expected)
			assertTrue(Arrays.asList(points).contains(p));
	}

	@Test
	public void ShouldRasterizeSegmentWithFloats() throws GeometricException
	{
		// Arrange
		LineSegment segment = new LineSegment(new Point(25.2, 28.9), new Point(21.5, 23.1));
		Point[] expected = new Point[] 
		{
			new Point(25, 29),
			new Point(25, 28),
			new Point(24, 27),
			new Point(24, 26),
			new Point(23, 25),
			new Point(23, 24),
			new Point(22, 23),
		};

		// Act
		Point[] points = Renderer.rasterize(segment);

		// Assert

		assertEquals(expected.length, points.length);
		for (Point p : expected)
			assertTrue(Arrays.asList(points).contains(p));
	}

	@Test
	public void ShouldRasterizeSegmentWithSamePoint() throws GeometricException
	{
		// Arrange
		LineSegment segment = new LineSegment(new Point(10.1, 10.1), new Point(9.9, 9.9));
		Point[] expected = new Point[] 
		{
			new Point(10, 10),
		};

		// Act
		Point[] points = Renderer.rasterize(segment);

		// Assert
		assertEquals(expected.length, points.length);
		for (Point p : expected)
			assertTrue(Arrays.asList(points).contains(p));
	}

	@Test
	public void ShouldRasterizePolygonSides() throws GeometricException
	{
		// Arrange
		Polygon poly = new Polygon(new Point[]
		{
			new Point(0, 5),
			new Point(5, 7),
			new Point(7, 2),
			new Point(2, 0),
		});
		Point[] expected = new Point[]
		{
			new Point(0, 5),
			new Point(1, 5),
			new Point(2, 6),
			new Point(3, 6),
			new Point(4, 7),
			new Point(5, 7),
			new Point(5, 6),
			new Point(6, 5),
			new Point(6, 4),
			new Point(7, 3),
			new Point(7, 2),
			new Point(6, 2),
			new Point(5, 1),
			new Point(4, 1),
			new Point(3, 0),
			new Point(2, 0),
			new Point(2, 1),
			new Point(1, 2),
			new Point(1, 3),
			new Point(0, 4),
		};

		// Act
		Point[] points = Renderer.rasterizeSides(poly);

		// Arrange
		// NOTE: the length might not be the same, as there may be repeating points
		for (Point p : expected)
			assertTrue(Arrays.asList(points).contains(p));
		for (Point p : points)
			assertTrue(Arrays.asList(expected).contains(p));
	}

	@Test
	public void ShouldRasterizePolygon() throws GeometricException
	{
		// Arrange
		Polygon poly = new Polygon(new Point[]
		{
			new Point(1, 2),
			new Point(2, 8),
			new Point(8, 7),
			new Point(7, 1),
		});
		Point[] expected = new Point[]
		{
			new Point(2, 8), new Point(3, 8), new Point(4, 8), 
			new Point(2, 7), new Point(3, 7), new Point(4, 7), new Point(5, 7), new Point(6, 7), new Point(7, 7), new Point(8, 7),
			new Point(2, 6), new Point(3, 6), new Point(4, 6), new Point(5, 6), new Point(6, 6), new Point(7, 6), new Point(8, 6),
			new Point(2, 5), new Point(3, 5), new Point(4, 5), new Point(5, 5), new Point(6, 5), new Point(7, 5), new Point(8, 5),
			new Point(1, 4), new Point(2, 4), new Point(3, 4), new Point(4, 4), new Point(5, 4), new Point(6, 4), new Point(7, 4), new Point(8, 4),
			new Point(1, 3), new Point(2, 3), new Point(3, 3), new Point(4, 3), new Point(5, 3), new Point(6, 3), new Point(7, 3),
			new Point(1, 2), new Point(2, 2), new Point(3, 2), new Point(4, 2), new Point(5, 2), new Point(6, 2), new Point(7, 2),
			new Point(4, 1), new Point(5, 1), new Point(6, 1), new Point(7, 1),
		};

		// Act
		Point[] points = Renderer.rasterize(poly);

		// Arrange
		// NOTE: the length might not be the same, as there may be repeating points
		for (Point p : expected)
			assertTrue(Arrays.asList(points).contains(p));
		for (Point p : points)
			assertTrue(Arrays.asList(expected).contains(p));
	}

	@Test
	public void ShouldRasterizePolygonWhenThereAreGaps() throws GeometricException
	{
		// Arrange
		Polygon poly = new Polygon(new Point[]
		{
			new Point(1, 10),
			new Point(1, 6),
			new Point(3, 5),
			new Point(3, 1),
			new Point(4, 1),
			new Point(4, 5),
			new Point(6, 6),
			new Point(6, 10),
			new Point(5, 6),
			new Point(4, 10),
			new Point(3, 6),
		});
		Point[] expected = new Point[]
		{
			new Point(1, 10), new Point(4, 10), new Point(6, 10),
			new Point(1, 9), new Point(4, 9), new Point(6, 9),
			new Point(1, 8), new Point(2, 8), new Point(4, 8), new Point(6, 8),
			new Point(1, 7), new Point(2, 7), new Point(3, 7), new Point(4, 7), new Point(5, 7), new Point(6, 7),
			new Point(1, 6), new Point(2, 6), new Point(3, 6), new Point(4, 6), new Point(5, 6), new Point(6, 6),
			new Point(2, 5), new Point(3, 5), new Point(4, 5),
			new Point(3, 4), new Point(4, 4),
			new Point(3, 3), new Point(4, 3),
			new Point(3, 2), new Point(4, 2),
			new Point(3, 1), new Point(4, 1),
		};

		// Act
		Point[] points = Renderer.rasterize(poly);

		// Arrange
		// NOTE: the length might not be the same, as there may be repeating points
		for (Point p : expected)
			assertTrue(Arrays.asList(points).contains(p));
		for (Point p : points)
			assertTrue(Arrays.asList(expected).contains(p));
	}

	@Test
	public void ShouldRasterizePolygonWhenParalelLines() throws GeometricException
	{
		// Arrange
		Polygon poly = new Polygon(new Point[]
		{
			new Point(1, 1),
			new Point(6, 1),
			new Point(5, 3),
			new Point(2, 3),
		});
		Point[] expected = new Point[]
		{
			new Point(1, 1),
			new Point(2, 1),
			new Point(3, 1),
			new Point(4, 1),
			new Point(5, 1),
			new Point(6, 1),
			new Point(2, 2),
			new Point(3, 2),
			new Point(4, 2),
			new Point(5, 2),
			new Point(2, 3),
			new Point(3, 3),
			new Point(4, 3),
			new Point(5, 3),
		};

		// Act
		Point[] points = Renderer.rasterize(poly);

		// Arrange
		// NOTE: the length might not be the same, as there may be repeating points
		for (Point p : expected)
			assertTrue(Arrays.asList(points).contains(p));
		for (Point p : points)
			assertTrue(Arrays.asList(expected).contains(p));
	}

	@Test
	public void ShouldRasterizePolygonWhenParalelLinesThenGap() throws GeometricException
	{
		// Arrange
		Polygon poly = new Polygon(new Point[]
		{
			new Point(1, 1),
			new Point(2, 3),
			new Point(5, 3),
			new Point(7, 6),
			new Point(7, 1),
		});
		Point[] expected = new Point[]
		{
			new Point(1, 1),
			new Point(2, 1),
			new Point(3, 1),
			new Point(4, 1),
			new Point(5, 1),
			new Point(6, 1),
			new Point(7, 1),
			new Point(2, 2),
			new Point(3, 2),
			new Point(4, 2),
			new Point(5, 2),
			new Point(6, 2),
			new Point(7, 2),
			new Point(2, 3),
			new Point(3, 3),
			new Point(4, 3),
			new Point(5, 3),
			new Point(6, 3),
			new Point(7, 3),
			new Point(7, 3),
			new Point(6, 4),
			new Point(7, 4),
			new Point(6, 5),
			new Point(7, 5),
			new Point(7, 6),
		};

		// Act
		Point[] points = Renderer.rasterize(poly);

		System.out.println("Gotten:");
		for (Point p : points)
			System.out.println(p);
		System.out.println("Expected:");
		for (Point p : expected)
			System.out.println(p);

		// Arrange
		// NOTE: the length might not be the same, as there may be repeating points
		for (Point p : expected)
			assertTrue(Arrays.asList(points).contains(p));
		for (Point p : points)
			assertTrue(Arrays.asList(expected).contains(p));
	}
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
