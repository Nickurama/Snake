package GameEngineTests;

import GameEngine.*;
import Geometry.*;
import TestUtil.*;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class RendererTests
{
	@Test
	public void ShouldInitializeVariables() throws GeometricException, GameEngineException
	{
		// Arrange
		LineSegment s = new LineSegment(new Point(10, 10), new Point(20, 10));
		Rectangle camera = new Rectangle(new Point[] { new Point(9, 9), new Point(9, 11), new Point(21, 11), new Point(21, 9)});
		ByteArrayOutputStream out = TestUtil.setIOstreams("");

		String expected =	"0000000000000\n" +
							"0111111111110\n" +
							"0000000000000\n";

		// Act
		Renderer.getInstance().init(camera, '0');
		Renderer.getInstance().render(s, '1');
		String render = out.toString();
		out.reset();

		// Assert
		assertEquals(expected, render);
	}

	@Test
	public void ShouldThrowExceptionIfVariablesNotInitializedOnShortRenderCall() throws GeometricException
	{
		// Arrange
		Renderer.resetInstance();
		LineSegment s = new LineSegment(new Point(10, 10), new Point(20, 10));

		// Act
		// assert
		assertThrows(GameEngineException.class, () -> Renderer.getInstance().render(s, 'x'));
	}

	@Test
	public void ShouldRasterizeHorizontalLines() throws GeometricException
	{
		// Arrange
		LineSegment s0 = new LineSegment(new Point(10, 10), new Point(20, 10));
		LineSegment s1 = new LineSegment(new Point(10, 10), new Point(0, 10));
		Rectangle camera0 = new Rectangle(new Point[] { new Point(9, 9), new Point(9, 11), new Point(21, 11), new Point(21, 9)});
		Rectangle camera1 = new Rectangle(new Point[] { new Point(0, 9), new Point(0, 11), new Point(11, 11), new Point(11, 9)});
		ByteArrayOutputStream out = TestUtil.setIOstreams("");

		String expected0 =	"-------------\n" +
							"-xxxxxxxxxxx-\n" +
							"-------------\n";

		String expected1 =	"------------\n" +
							"xxxxxxxxxxx-\n" +
							"------------\n";

		// Act
		Renderer.getInstance().render(s0, camera0, '-', 'x');
		String render0 = out.toString();
		out.reset();

		Renderer.getInstance().render(s1, camera1, '-', 'x');
		String render1 = out.toString();
		out.reset();

		// Assert
		assertEquals(expected0, render0);
		assertEquals(expected1, render1);
	}

	@Test
	public void ShouldRasterizeVerticalLines() throws GeometricException
	{
		// Arrange
		LineSegment s0 = new LineSegment(new Point(10, 10), new Point(10, 20));
		LineSegment s1 = new LineSegment(new Point(10, 10), new Point(10, 0));
		Rectangle camera0 = new Rectangle(new Point[] { new Point(9, 9), new Point(9, 21), new Point(11, 21), new Point(11, 9)});
		Rectangle camera1 = new Rectangle(new Point[] { new Point(9, 0), new Point(9, 11), new Point(11, 11), new Point(11, 0)});
		ByteArrayOutputStream out = TestUtil.setIOstreams("");

		String expected0 =	"---\n" +
							"-x-\n" +
							"-x-\n" +
							"-x-\n" +
							"-x-\n" +
							"-x-\n" +
							"-x-\n" +
							"-x-\n" +
							"-x-\n" +
							"-x-\n" +
							"-x-\n" +
							"-x-\n" +
							"---\n";

		String expected1 =	"---\n" +
							"-x-\n" +
							"-x-\n" +
							"-x-\n" +
							"-x-\n" +
							"-x-\n" +
							"-x-\n" +
							"-x-\n" +
							"-x-\n" +
							"-x-\n" +
							"-x-\n" +
							"-x-\n";

		// Act
		Renderer.getInstance().render(s0, camera0, '-', 'x');
		String render0 = out.toString();
		out.reset();

		Renderer.getInstance().render(s1, camera1, '-', 'x');
		String render1 = out.toString();
		out.reset();

		// Assert
		assertEquals(expected0, render0);
		assertEquals(expected1, render1);
	}

	@Test
	public void ShouldRasterizeOverDiagonalLines() throws GeometricException
	{
		// Arrange
		LineSegment s0 = new LineSegment(new Point(10, 10), new Point(20, 20));
		LineSegment s1 = new LineSegment(new Point(10, 10), new Point(0, 20));
		LineSegment s2 = new LineSegment(new Point(10, 10), new Point(0, 0));
		LineSegment s3 = new LineSegment(new Point(10, 10), new Point(20, 0));
		Rectangle camera = new Rectangle(new Point[] { new Point(0, 0), new Point(0, 20), new Point(20, 20), new Point(20, 0)});
		ByteArrayOutputStream out = TestUtil.setIOstreams("");

		String expected0 =	"--------------------x\n" +
							"-------------------x-\n" +
							"------------------x--\n" +
							"-----------------x---\n" +
							"----------------x----\n" +
							"---------------x-----\n" +
							"--------------x------\n" +
							"-------------x-------\n" +
							"------------x--------\n" +
							"-----------x---------\n" +
							"----------x----------\n" +
							"---------------------\n" +
							"---------------------\n" +
							"---------------------\n" +
							"---------------------\n" +
							"---------------------\n" +
							"---------------------\n" +
							"---------------------\n" +
							"---------------------\n" +
							"---------------------\n" +
							"---------------------\n";

		String expected1 =	"x--------------------\n" +
							"-x-------------------\n" +
							"--x------------------\n" +
							"---x-----------------\n" +
							"----x----------------\n" +
							"-----x---------------\n" +
							"------x--------------\n" +
							"-------x-------------\n" +
							"--------x------------\n" +
							"---------x-----------\n" +
							"----------x----------\n" +
							"---------------------\n" +
							"---------------------\n" +
							"---------------------\n" +
							"---------------------\n" +
							"---------------------\n" +
							"---------------------\n" +
							"---------------------\n" +
							"---------------------\n" +
							"---------------------\n" +
							"---------------------\n";

		String expected2 =	"---------------------\n" +
							"---------------------\n" +
							"---------------------\n" +
							"---------------------\n" +
							"---------------------\n" +
							"---------------------\n" +
							"---------------------\n" +
							"---------------------\n" +
							"---------------------\n" +
							"---------------------\n" +
							"----------x----------\n" +
							"---------x-----------\n" +
							"--------x------------\n" +
							"-------x-------------\n" +
							"------x--------------\n" +
							"-----x---------------\n" +
							"----x----------------\n" +
							"---x-----------------\n" +
							"--x------------------\n" +
							"-x-------------------\n" +
							"x--------------------\n";

		String expected3 =	"---------------------\n" +
							"---------------------\n" +
							"---------------------\n" +
							"---------------------\n" +
							"---------------------\n" +
							"---------------------\n" +
							"---------------------\n" +
							"---------------------\n" +
							"---------------------\n" +
							"---------------------\n" +
							"----------x----------\n" +
							"-----------x---------\n" +
							"------------x--------\n" +
							"-------------x-------\n" +
							"--------------x------\n" +
							"---------------x-----\n" +
							"----------------x----\n" +
							"-----------------x---\n" +
							"------------------x--\n" +
							"-------------------x-\n" +
							"--------------------x\n";

		// Act
		Renderer.getInstance().render(s0, camera, '-', 'x');
		String render0 = out.toString();
		out.reset();

		Renderer.getInstance().render(s1, camera, '-', 'x');
		String render1 = out.toString();
		out.reset();

		Renderer.getInstance().render(s2, camera, '-', 'x');
		String render2 = out.toString();
		out.reset();

		Renderer.getInstance().render(s3, camera, '-', 'x');
		String render3 = out.toString();
		out.reset();

		// Assert
		assertEquals(expected0, render0);
		assertEquals(expected1, render1);
		assertEquals(expected2, render2);
		assertEquals(expected3, render3);
	}

	@Test
	public void ShouldRasterizeSegmentOverFirstOctant() throws GeometricException
	{
		// Arrange
		LineSegment segment = new LineSegment(new Point(10, 10), new Point(20, 15));
		Rectangle camera = new Rectangle(new Point[] { new Point(0, 0), new Point(0, 20), new Point(20, 20), new Point(20, 0)});
		ByteArrayOutputStream out = TestUtil.setIOstreams("");

		String expected =	"---------------------\n" +
							"---------------------\n" +
							"---------------------\n" +
							"---------------------\n" +
							"---------------------\n" +
							"-------------------xx\n" +
							"-----------------xx--\n" +
							"---------------xx----\n" +
							"-------------xx------\n" +
							"-----------xx--------\n" +
							"----------x----------\n" +
							"---------------------\n" +
							"---------------------\n" +
							"---------------------\n" +
							"---------------------\n" +
							"---------------------\n" +
							"---------------------\n" +
							"---------------------\n" +
							"---------------------\n" +
							"---------------------\n" +
							"---------------------\n";

		// Act
		Renderer.getInstance().render(segment, camera, '-', 'x');
		String render = out.toString();
		out.reset();

		// Assert
		assertEquals(expected, render);
	}

	@Test
	public void ShouldRasterizeSegmentOverSecondOctant() throws GeometricException
	{
		// Arrange
		LineSegment segment = new LineSegment(new Point(10, 10), new Point(18, 20));
		Rectangle camera = new Rectangle(new Point[] { new Point(0, 0), new Point(0, 20), new Point(20, 20), new Point(20, 0)});
		ByteArrayOutputStream out = TestUtil.setIOstreams("");

		String expected =	"------------------x--\n" +
							"-----------------x---\n" +
							"----------------x----\n" +
							"----------------x----\n" +
							"---------------x-----\n" +
							"--------------x------\n" +
							"-------------x-------\n" +
							"------------x--------\n" +
							"------------x--------\n" +
							"-----------x---------\n" +
							"----------x----------\n" +
							"---------------------\n" +
							"---------------------\n" +
							"---------------------\n" +
							"---------------------\n" +
							"---------------------\n" +
							"---------------------\n" +
							"---------------------\n" +
							"---------------------\n" +
							"---------------------\n" +
							"---------------------\n";

		// Act
		Renderer.getInstance().render(segment, camera, '-', 'x');
		String render = out.toString();
		out.reset();

		// Assert
		assertEquals(expected, render);
	}

	@Test
	public void ShouldRasterizeSegmentOverThirdOctant() throws GeometricException
	{
		// Arrange
		LineSegment segment = new LineSegment(new Point(10, 10), new Point(8, 20));
		Rectangle camera = new Rectangle(new Point[] { new Point(0, 0), new Point(0, 20), new Point(20, 20), new Point(20, 0)});
		ByteArrayOutputStream out = TestUtil.setIOstreams("");

		String expected =	"--------x------------\n" +
							"--------x------------\n" +
							"--------x------------\n" +
							"---------x-----------\n" +
							"---------x-----------\n" +
							"---------x-----------\n" +
							"---------x-----------\n" +
							"---------x-----------\n" +
							"----------x----------\n" +
							"----------x----------\n" +
							"----------x----------\n" +
							"---------------------\n" +
							"---------------------\n" +
							"---------------------\n" +
							"---------------------\n" +
							"---------------------\n" +
							"---------------------\n" +
							"---------------------\n" +
							"---------------------\n" +
							"---------------------\n" +
							"---------------------\n";

		// Act
		Renderer.getInstance().render(segment, camera, '-', 'x');
		String render = out.toString();
		out.reset();

		// Assert
		assertEquals(expected, render);
	}

	@Test
	public void ShouldRasterizeSegmentOverFourthOctant() throws GeometricException
	{
		// Arrange
		LineSegment segment = new LineSegment(new Point(10, 10), new Point(0, 19));
		Rectangle camera = new Rectangle(new Point[] { new Point(0, 0), new Point(0, 20), new Point(20, 20), new Point(20, 0)});
		ByteArrayOutputStream out = TestUtil.setIOstreams("");

		String expected =	"---------------------\n" +
							"x--------------------\n" +
							"-x-------------------\n" +
							"--x------------------\n" +
							"---x-----------------\n" +
							"----x----------------\n" +
							"-----xx--------------\n" +
							"-------x-------------\n" +
							"--------x------------\n" +
							"---------x-----------\n" +
							"----------x----------\n" +
							"---------------------\n" +
							"---------------------\n" +
							"---------------------\n" +
							"---------------------\n" +
							"---------------------\n" +
							"---------------------\n" +
							"---------------------\n" +
							"---------------------\n" +
							"---------------------\n" +
							"---------------------\n";

		// Act
		Renderer.getInstance().render(segment, camera, '-', 'x');
		String render = out.toString();
		out.reset();

		// Assert
		assertEquals(expected, render);
	}

	@Test
	public void ShouldRasterizeSegmentOverFifthOctant() throws GeometricException
	{
		// Arrange
		LineSegment segment = new LineSegment(new Point(10, 10), new Point(0, 6));
		Rectangle camera = new Rectangle(new Point[] { new Point(0, 0), new Point(0, 20), new Point(20, 20), new Point(20, 0)});
		ByteArrayOutputStream out = TestUtil.setIOstreams("");

		String expected =	"---------------------\n" +
							"---------------------\n" +
							"---------------------\n" +
							"---------------------\n" +
							"---------------------\n" +
							"---------------------\n" +
							"---------------------\n" +
							"---------------------\n" +
							"---------------------\n" +
							"---------------------\n" +
							"---------xx----------\n" +
							"-------xx------------\n" +
							"----xxx--------------\n" +
							"--xx-----------------\n" +
							"xx-------------------\n" +
							"---------------------\n" +
							"---------------------\n" +
							"---------------------\n" +
							"---------------------\n" +
							"---------------------\n" +
							"---------------------\n";

		// Act
		Renderer.getInstance().render(segment, camera, '-', 'x');
		String render = out.toString();
		out.reset();

		// Assert
		assertEquals(expected, render);
	}

	@Test
	public void ShouldRasterizeSegmentOverSixthOctant() throws GeometricException
	{
		// Arrange
		LineSegment segment = new LineSegment(new Point(10, 10), new Point(9, 0));
		Rectangle camera = new Rectangle(new Point[] { new Point(0, 0), new Point(0, 20), new Point(20, 20), new Point(20, 0)});
		ByteArrayOutputStream out = TestUtil.setIOstreams("");

		String expected =	"---------------------\n" +
							"---------------------\n" +
							"---------------------\n" +
							"---------------------\n" +
							"---------------------\n" +
							"---------------------\n" +
							"---------------------\n" +
							"---------------------\n" +
							"---------------------\n" +
							"---------------------\n" +
							"----------x----------\n" +
							"----------x----------\n" +
							"----------x----------\n" +
							"----------x----------\n" +
							"----------x----------\n" +
							"----------x----------\n" +
							"---------x-----------\n" +
							"---------x-----------\n" +
							"---------x-----------\n" +
							"---------x-----------\n" +
							"---------x-----------\n";

		// Act
		Renderer.getInstance().render(segment, camera, '-', 'x');
		String render = out.toString();
		out.reset();

		// Assert
		assertEquals(expected, render);
	}

	@Test
	public void ShouldRasterizeSegmentOverSeventhOctant() throws GeometricException
	{
		// Arrange
		LineSegment segment = new LineSegment(new Point(10, 10), new Point(13, 0));
		Rectangle camera = new Rectangle(new Point[] { new Point(0, 0), new Point(0, 20), new Point(20, 20), new Point(20, 0)});
		ByteArrayOutputStream out = TestUtil.setIOstreams("");

		String expected =	"---------------------\n" +
							"---------------------\n" +
							"---------------------\n" +
							"---------------------\n" +
							"---------------------\n" +
							"---------------------\n" +
							"---------------------\n" +
							"---------------------\n" +
							"---------------------\n" +
							"---------------------\n" +
							"----------x----------\n" +
							"----------x----------\n" +
							"-----------x---------\n" +
							"-----------x---------\n" +
							"-----------x---------\n" +
							"-----------x---------\n" +
							"------------x--------\n" +
							"------------x--------\n" +
							"------------x--------\n" +
							"-------------x-------\n" +
							"-------------x-------\n";

		// Act
		Renderer.getInstance().render(segment, camera, '-', 'x');
		String render = out.toString();
		out.reset();

		// Assert
		assertEquals(expected, render);
	}

	@Test
	public void ShouldRasterizeSegmentOverEightOctant() throws GeometricException
	{
		// Arrange
		LineSegment segment = new LineSegment(new Point(10, 10), new Point(20, 7));
		Rectangle camera = new Rectangle(new Point[] { new Point(0, 0), new Point(0, 20), new Point(20, 20), new Point(20, 0)});
		ByteArrayOutputStream out = TestUtil.setIOstreams("");

		String expected =	"---------------------\n" +
							"---------------------\n" +
							"---------------------\n" +
							"---------------------\n" +
							"---------------------\n" +
							"---------------------\n" +
							"---------------------\n" +
							"---------------------\n" +
							"---------------------\n" +
							"---------------------\n" +
							"----------xx---------\n" +
							"------------xxx------\n" +
							"---------------xxxx--\n" +
							"-------------------xx\n" +
							"---------------------\n" +
							"---------------------\n" +
							"---------------------\n" +
							"---------------------\n" +
							"---------------------\n" +
							"---------------------\n" +
							"---------------------\n";

		// Act
		Renderer.getInstance().render(segment, camera, '-', 'x');
		String render = out.toString();
		out.reset();

		// Assert
		assertEquals(expected, render);
	}

	@Test
	public void ShouldRasterizeSegmentWithFloats() throws GeometricException
	{
		// Arrange
		LineSegment segment = new LineSegment(new Point(25.2, 28.9), new Point(21.5, 23.1));
		Rectangle camera = new Rectangle(new Point[] { new Point(20, 22), new Point(20, 30), new Point(26, 30), new Point(26, 22)});
		ByteArrayOutputStream out = TestUtil.setIOstreams("");

		String expected =	"-------\n" +
							"-----x-\n" +
							"-----x-\n" +
							"----x--\n" +
							"----x--\n" +
							"---x---\n" +
							"---x---\n" +
							"--x----\n" +
							"-------\n";

		// Act
		Renderer.getInstance().render(segment, camera, '-', 'x');
		String render = out.toString();
		out.reset();

		// Assert
		assertEquals(expected, render);
	}

	@Test
	public void ShouldRasterizeSegmentWithSamePoint() throws GeometricException
	{
		// Arrange
		LineSegment segment = new LineSegment(new Point(10.1, 10.1), new Point(9.9, 9.9));
		Rectangle camera = new Rectangle(new Point[] { new Point(9, 9), new Point(9, 11), new Point(11, 11), new Point(11, 9)});
		ByteArrayOutputStream out = TestUtil.setIOstreams("");

		String expected =	"---\n" +
							"-x-\n" +
							"---\n";

		// Act
		Renderer.getInstance().render(segment, camera, '-', 'x');
		String render = out.toString();
		out.reset();

		// Assert
		assertEquals(expected, render);
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
		Rectangle camera = new Rectangle(new Point[] { new Point(0, 0), new Point(0, 8), new Point(8, 8), new Point(8, 0)});
		ByteArrayOutputStream out = TestUtil.setIOstreams("");

		String expected =	"---------\n" +
							"----xx---\n" +
							"--xx-x---\n" +
							"xx----x--\n" +
							"x-----x--\n" +
							"-x-----x-\n" +
							"-x----xx-\n" +
							"--x-xx---\n" +
							"--xx-----\n";

		// Act
		Renderer.getInstance().renderSides(poly, camera, '-', 'x');
		String render = out.toString();
		out.reset();

		// Assert
		assertEquals(expected, render);
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
		Rectangle camera = new Rectangle(new Point[] { new Point(0, 0), new Point(0, 9), new Point(9, 9), new Point(9, 0)});
		ByteArrayOutputStream out = TestUtil.setIOstreams("");

		String expected =	"----------\n" +
							"--xxx-----\n" +
							"--xxxxxxx-\n" +
							"--xxxxxxx-\n" +
							"--xxxxxxx-\n" +
							"-xxxxxxxx-\n" +
							"-xxxxxxx--\n" +
							"-xxxxxxx--\n" +
							"----xxxx--\n" +
							"----------\n";

		// Act
		Renderer.getInstance().render(poly, camera, '-', 'x');
		String render = out.toString();
		out.reset();

		// Assert
		assertEquals(expected, render);
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
		Rectangle camera = new Rectangle(new Point[] { new Point(0, 0), new Point(0, 11), new Point(7, 11), new Point(7, 0)});
		ByteArrayOutputStream out = TestUtil.setIOstreams("");

		String expected =	"--------\n" +
							"-x--x-x-\n" +
							"-x--x-x-\n" +
							"-xx-x-x-\n" +
							"-xxxxxx-\n" +
							"-xxxxxx-\n" +
							"--xxx---\n" +
							"---xx---\n" +
							"---xx---\n" +
							"---xx---\n" +
							"---xx---\n" +
							"--------\n";

		// Act
		Renderer.getInstance().render(poly, camera, '-', 'x');
		String render = out.toString();
		out.reset();

		// Assert
		assertEquals(expected, render);
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
		Rectangle camera = new Rectangle(new Point[] { new Point(0, 0), new Point(0, 4), new Point(7, 4), new Point(7, 0)});
		ByteArrayOutputStream out = TestUtil.setIOstreams("");

		String expected =	"--------\n" +
							"--xxxx--\n" +
							"--xxxx--\n" +
							"-xxxxxx-\n" +
							"--------\n";

		// Act
		Renderer.getInstance().render(poly, camera, '-', 'x');
		String render = out.toString();
		out.reset();

		// Assert
		assertEquals(expected, render);
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
		Rectangle camera = new Rectangle(new Point[] { new Point(0, 0), new Point(0, 7), new Point(8, 7), new Point(8, 0)});
		ByteArrayOutputStream out = TestUtil.setIOstreams("");

		String expected =	"---------\n" +
							"-------x-\n" +
							"------xx-\n" +
							"------xx-\n" +
							"--xxxxxx-\n" +
							"--xxxxxx-\n" +
							"-xxxxxxx-\n" +
							"---------\n";

		// Act
		Renderer.getInstance().render(poly, camera, '-', 'x');
		String render = out.toString();
		out.reset();

		// Assert
		assertEquals(expected, render);
	}

	@Test
	public void ShouldDrawWhenOffCamera() throws GeometricException
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
		Rectangle camera = new Rectangle(new Point[] { new Point(0, 0), new Point(0, 7), new Point(5, 7), new Point(5, 0)});
		ByteArrayOutputStream out = TestUtil.setIOstreams("");

		String expected =	"------\n" +
							"------\n" +
							"------\n" +
							"------\n" +
							"--xxxx\n" +
							"--xxxx\n" +
							"-xxxxx\n" +
							"------\n";

		// Act
		Renderer.getInstance().render(poly, camera, '-', 'x');
		String render = out.toString();
		out.reset();

		// Assert
		assertEquals(expected, render);
	}

	@Test
	public void ShouldRenderScene() throws GeometricException, GameEngineException
	{
		// Arrange
		Polygon poly = new Polygon(new Point[]
		{
			new Point(1, 2),
			new Point(2, 8),
			new Point(8, 7),
			new Point(7, 1),
		});
		RenderData<Polygon> rData = new RenderData<Polygon>(poly, true, 0, 'x');
		MockRenderable mockRenderable = new MockRenderable(rData);
		Rectangle camera = new Rectangle(new Point[] { new Point(0, 0), new Point(0, 9), new Point(9, 9), new Point(9, 0)});
		Scene sc = new Scene();
		sc.add(mockRenderable);
		ByteArrayOutputStream out = TestUtil.setIOstreams("");

		String expected =	"----------\n" +
							"--xxx-----\n" +
							"--xxxxxxx-\n" +
							"--xxxxxxx-\n" +
							"--xxxxxxx-\n" +
							"-xxxxxxxx-\n" +
							"-xxxxxxx--\n" +
							"-xxxxxxx--\n" +
							"----xxxx--\n" +
							"----------\n";

		// Act
		Renderer.getInstance().render(sc, camera, '-');
		String render = out.toString();
		out.reset();

		// Assert
		assertEquals(expected, render);
	}

	@Test
	public void ShouldRenderEmptyScene() throws GeometricException
	{
		// Arrange
		Rectangle camera = new Rectangle(new Point[] { new Point(0, 0), new Point(0, 9), new Point(9, 9), new Point(9, 0)});
		Scene sc = new Scene();
		ByteArrayOutputStream out = TestUtil.setIOstreams("");

		String expected =	"5555555555\n" +
							"5555555555\n" +
							"5555555555\n" +
							"5555555555\n" +
							"5555555555\n" +
							"5555555555\n" +
							"5555555555\n" +
							"5555555555\n" +
							"5555555555\n" +
							"5555555555\n";

		// Act
		Renderer.getInstance().render(sc, camera, '5');
		String render = out.toString();
		out.reset();

		// Assert
		assertEquals(expected, render);
	}

	@Test
	public void ShouldRenderNoFill() throws GeometricException, GameEngineException
	{
		// Arrange
		Polygon poly = new Polygon(new Point[]
		{
			new Point(0, 5),
			new Point(5, 7),
			new Point(7, 2),
			new Point(2, 0),
		});
		RenderData<Polygon> rData = new RenderData<Polygon>(poly, false, 0, 'x');
		MockRenderable mockRenderable = new MockRenderable(rData);
		Scene sc = new Scene();
		sc.add(mockRenderable);
		Rectangle camera = new Rectangle(new Point[] { new Point(0, 0), new Point(0, 8), new Point(8, 8), new Point(8, 0)});
		ByteArrayOutputStream out = TestUtil.setIOstreams("");

		String expected =	"---------\n" +
							"----xx---\n" +
							"--xx-x---\n" +
							"xx----x--\n" +
							"x-----x--\n" +
							"-x-----x-\n" +
							"-x----xx-\n" +
							"--x-xx---\n" +
							"--xx-----\n";

		// Act
		Renderer.getInstance().render(sc, camera, '-');
		String render = out.toString();
		out.reset();

		// Assert
		assertEquals(expected, render);
	}

	@Test
	public void ShouldRenderSceneByLayer() throws GeometricException, GameEngineException
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
		ByteArrayOutputStream out = TestUtil.setIOstreams("");

		String expected =	"----------\n" +
							"-yxxxyyyy-\n" +
							"-yxxxxxxx-\n" +
							"--xxxxxxx-\n" +
							"--xxxxxxx-\n" +
							"-xxxxxxxx-\n" +
							"-xxxxxxx--\n" +
							"-xxxxxxx--\n" +
							"----xxxx--\n" +
							"----------\n";

		// Act
		Renderer.getInstance().render(sc, camera, '-');
		String render = out.toString();
		out.reset();

		// Assert
		assertEquals(expected, render);
	}

	@Test
	public void ShouldRenderCircleNoFill() throws GeometricException
	{
		// Arrange
		Circle circle = new Circle(new Point(5, 5), 3);
		Rectangle camera = new Rectangle(new Point[] { new Point(0, 0), new Point(0, 10), new Point(10, 10), new Point(10, 0)});
		ByteArrayOutputStream out = TestUtil.setIOstreams("");

		String expected =	"-----------\n" +
							"-----------\n" +
							"----xxx----\n" +
							"---x---x---\n" +
							"--x-----x--\n" +
							"--x-----x--\n" +
							"--x-----x--\n" +
							"---x---x---\n" +
							"----xxx----\n" +
							"-----------\n" +
							"-----------\n";


		// Act
		Renderer.getInstance().renderSides(circle, camera, '-', 'x');
		String render = out.toString();
		out.reset();

		// Assert
		assertEquals(expected, render);
	}

	@Test
	public void ShouldRenderCircleFilled() throws GeometricException
	{
		// Arrange
		Circle circle = new Circle(new Point(7, 7), 6);
		Rectangle camera = new Rectangle(new Point[] { new Point(0, 0), new Point(0, 14), new Point(14, 14), new Point(14, 0)});
		ByteArrayOutputStream out = TestUtil.setIOstreams("");

		String expected =	"---------------\n" +
							"------xxx------\n" +
							"----xxxxxxx----\n" +
							"---xxxxxxxxx---\n" +
							"--xxxxxxxxxxx--\n" +
							"--xxxxxxxxxxx--\n" +
							"-xxxxxxxxxxxxx-\n" +
							"-xxxxxxxxxxxxx-\n" +
							"-xxxxxxxxxxxxx-\n" +
							"--xxxxxxxxxxx--\n" +
							"--xxxxxxxxxxx--\n" +
							"---xxxxxxxxx---\n" +
							"----xxxxxxx----\n" +
							"------xxx------\n" +
							"---------------\n";


		// Act
		Renderer.getInstance().render(circle, camera, '-', 'x');
		String render = out.toString();
		out.reset();

		// Assert
		assertEquals(expected, render);
	}

	@Test
	public void ShouldRenderCircleInScene() throws GeometricException, GameEngineException
	{
		// Arrange
		Circle circle = new Circle(new Point(7, 7), 6);
		RenderData<Circle> rData = new RenderData<Circle>(circle, false, 1, 'x');
		MockRenderable mockRenderable = new MockRenderable(rData);

		Rectangle camera = new Rectangle(new Point[] { new Point(0, 0), new Point(0, 14), new Point(14, 14), new Point(14, 0)});
		Scene sc = new Scene();
		sc.add(mockRenderable);
		ByteArrayOutputStream out = TestUtil.setIOstreams("");

		String expected =	"---------------\n" +
							"------xxx------\n" +
							"----xx---xx----\n" +
							"---x-------x---\n" +
							"--x---------x--\n" +
							"--x---------x--\n" +
							"-x-----------x-\n" +
							"-x-----------x-\n" +
							"-x-----------x-\n" +
							"--x---------x--\n" +
							"--x---------x--\n" +
							"---x-------x---\n" +
							"----xx---xx----\n" +
							"------xxx------\n" +
							"---------------\n";

		// Act
		Renderer.getInstance().render(sc, camera, '-');
		String render = out.toString();
		out.reset();

		// Assert
		assertEquals(expected, render);
	}

	@Test
	public void ShouldRenderOverlay() throws GeometricException, GameEngineException
	{
		// Arrange
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
		ByteArrayOutputStream out = TestUtil.setIOstreams("");

		String expected =	"d--------c\n" +
							"-o--------\n" +
							"--o-------\n" +
							"---o------\n" +
							"----o-----\n" +
							"-----o----\n" +
							"------o---\n" +
							"-------o--\n" +
							"--------o-\n" +
							"a--------b\n";

		// Act
		Renderer.getInstance().render(sc, camera, '-');
		String render = out.toString();
		out.reset();

		// Assert
		assertEquals(expected, render);
	}


	@Test
	public void ShouldApplyOverlayToScene() throws GeometricException, GameEngineException
	{
		// Arrange
		Polygon poly = new Polygon(new Point[]
			{
				new Point(1, 2),
				new Point(2, 8),
				new Point(8, 7),
				new Point(7, 1),
		});
		RenderData<Polygon> rData = new RenderData<Polygon>(poly, true, 0, 'x');
		MockRenderable mockRenderable = new MockRenderable(rData);
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
		sc.add(mockRenderable);
		ByteArrayOutputStream out = TestUtil.setIOstreams("");

		String expected =	"d--------c\n" +
							"-oxxx-----\n" +
							"--oxxxxxx-\n" +
							"--xoxxxxx-\n" +
							"--xxoxxxx-\n" +
							"-xxxxoxxx-\n" +
							"-xxxxxox--\n" +
							"-xxxxxxo--\n" +
							"----xxxxo-\n" +
							"a--------b\n";

		// Act
		Renderer.getInstance().render(sc, camera, '-');
		String render = out.toString();
		out.reset();

		// Assert
		assertEquals(expected, render);
	}
}
