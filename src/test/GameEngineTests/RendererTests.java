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
	// @Test
	// public void ShouldRasterizeHorizontalLines() throws GeometricException
	// {
	// 	// Arrange
	// 	LineSegment s0 = new LineSegment(new Point(10, 10), new Point(20, 10));
	// 	LineSegment s1 = new LineSegment(new Point(10, 10), new Point(0, 10));
	// 	Rectangle camera0 = new Rectangle(new Point[] { new Point(9, 9), new Point(9, 11), new Point(21, 11), new Point(21, 9)});
	// 	Rectangle camera1 = new Rectangle(new Point[] { new Point(0, 9), new Point(0, 11), new Point(10, 11), new Point(10, 9)});
	// 	ByteArrayOutputStream out = TestUtil.setIOstreams("");
	//
	// 	String expected0 =	"------------\r\n" +
	// 						"-xxxxxxxxxx-\r\n" +
	// 						"------------\r\n";
	//
	// 	String expected1 =	"-----------\r\n" +
	// 						"xxxxxxxxxx-\r\n" +
	// 						"-----------\r\n";
	//
	// 	// Act
	// 	Renderer.getInstance().render(s0, camera0, '-', 'x');
	// 	String render0 = out.toString();
	// 	out.reset();
	//
	// 	Renderer.getInstance().render(s1, camera1, '-', 'x');
	// 	String render1 = out.toString();
	// 	out.reset();
	//
	// 	// Assert
	// 	assertEquals(expected0, render0);
	// 	assertEquals(expected1, render1);
	// }
	//
	// @Test
	// public void ShouldRasterizeVerticalLines() throws GeometricException
	// {
	// 	// Arrange
	// 	LineSegment s0 = new LineSegment(new Point(10, 10), new Point(10, 20));
	// 	LineSegment s1 = new LineSegment(new Point(10, 10), new Point(10, 0));
	// 	Rectangle camera0 = new Rectangle(new Point[] { new Point(9, 9), new Point(9, 21), new Point(11, 21), new Point(11, 9)});
	// 	Rectangle camera1 = new Rectangle(new Point[] { new Point(9, 0), new Point(9, 11), new Point(11, 11), new Point(11, 0)});
	// 	ByteArrayOutputStream out = TestUtil.setIOstreams("");
	//
	// 	String expected0 =	"---\r\n" +
	// 						"-x-\r\n" +
	// 						"-x-\r\n" +
	// 						"-x-\r\n" +
	// 						"-x-\r\n" +
	// 						"-x-\r\n" +
	// 						"-x-\r\n" +
	// 						"-x-\r\n" +
	// 						"-x-\r\n" +
	// 						"-x-\r\n" +
	// 						"-x-\r\n" +
	// 						"-x-\r\n" +
	// 						"---\r\n";
	//
	// 	String expected1 =	"---\r\n" +
	// 						"-x-\r\n" +
	// 						"-x-\r\n" +
	// 						"-x-\r\n" +
	// 						"-x-\r\n" +
	// 						"-x-\r\n" +
	// 						"-x-\r\n" +
	// 						"-x-\r\n" +
	// 						"-x-\r\n" +
	// 						"-x-\r\n" +
	// 						"-x-\r\n" +
	// 						"-x-\r\n";
	//
	// 	// Act
	// 	Renderer.getInstance().render(s0, camera0, '-', 'x');
	// 	String render0 = out.toString();
	// 	out.reset();
	//
	// 	Renderer.getInstance().render(s1, camera1, '-', 'x');
	// 	String render1 = out.toString();
	// 	out.reset();
	//
	// 	// Assert
	// 	assertEquals(expected0, render0);
	// 	assertEquals(expected1, render1);
	// }
	//
	// @Test
	// public void ShouldRasterizeOverDiagonalLines() throws GeometricException
	// {
	// 	// Arrange
	// 	LineSegment s0 = new LineSegment(new Point(10, 10), new Point(20, 20));
	// 	LineSegment s1 = new LineSegment(new Point(10, 10), new Point(0, 20));
	// 	LineSegment s2 = new LineSegment(new Point(10, 10), new Point(0, 0));
	// 	LineSegment s3 = new LineSegment(new Point(10, 10), new Point(20, 0));
	// 	Rectangle camera = new Rectangle(new Point[] { new Point(0, 0), new Point(0, 20), new Point(20, 20), new Point(20, 0)});
	// 	ByteArrayOutputStream out = TestUtil.setIOstreams("");
	//
	// 	String expected0 =	"--------------------x\r\n" +
	// 						"-------------------x-\r\n" +
	// 						"------------------x--\r\n" +
	// 						"-----------------x---\r\n" +
	// 						"----------------x----\r\n" +
	// 						"---------------x-----\r\n" +
	// 						"--------------x------\r\n" +
	// 						"-------------x-------\r\n" +
	// 						"------------x--------\r\n" +
	// 						"-----------x---------\r\n" +
	// 						"----------x----------\r\n" +
	// 						"---------------------\r\n" +
	// 						"---------------------\r\n" +
	// 						"---------------------\r\n" +
	// 						"---------------------\r\n" +
	// 						"---------------------\r\n" +
	// 						"---------------------\r\n" +
	// 						"---------------------\r\n" +
	// 						"---------------------\r\n" +
	// 						"---------------------\r\n" +
	// 						"---------------------\r\n";
	//
	// 	String expected1 =	"x--------------------\r\n" +
	// 						"-x-------------------\r\n" +
	// 						"--x------------------\r\n" +
	// 						"---x-----------------\r\n" +
	// 						"----x----------------\r\n" +
	// 						"-----x---------------\r\n" +
	// 						"------x--------------\r\n" +
	// 						"-------x-------------\r\n" +
	// 						"--------x------------\r\n" +
	// 						"---------x-----------\r\n" +
	// 						"----------x----------\r\n" +
	// 						"---------------------\r\n" +
	// 						"---------------------\r\n" +
	// 						"---------------------\r\n" +
	// 						"---------------------\r\n" +
	// 						"---------------------\r\n" +
	// 						"---------------------\r\n" +
	// 						"---------------------\r\n" +
	// 						"---------------------\r\n" +
	// 						"---------------------\r\n" +
	// 						"---------------------\r\n";
	//
	// 	String expected2 =	"---------------------\r\n" +
	// 						"---------------------\r\n" +
	// 						"---------------------\r\n" +
	// 						"---------------------\r\n" +
	// 						"---------------------\r\n" +
	// 						"---------------------\r\n" +
	// 						"---------------------\r\n" +
	// 						"---------------------\r\n" +
	// 						"---------------------\r\n" +
	// 						"---------------------\r\n" +
	// 						"----------x----------\r\n" +
	// 						"---------x-----------\r\n" +
	// 						"--------x------------\r\n" +
	// 						"-------x-------------\r\n" +
	// 						"------x--------------\r\n" +
	// 						"-----x---------------\r\n" +
	// 						"----x----------------\r\n" +
	// 						"---x-----------------\r\n" +
	// 						"--x------------------\r\n" +
	// 						"-x-------------------\r\n" +
	// 						"x--------------------\r\n";
	//
	// 	String expected3 =	"---------------------\r\n" +
	// 						"---------------------\r\n" +
	// 						"---------------------\r\n" +
	// 						"---------------------\r\n" +
	// 						"---------------------\r\n" +
	// 						"---------------------\r\n" +
	// 						"---------------------\r\n" +
	// 						"---------------------\r\n" +
	// 						"---------------------\r\n" +
	// 						"---------------------\r\n" +
	// 						"----------x----------\r\n" +
	// 						"-----------x---------\r\n" +
	// 						"------------x--------\r\n" +
	// 						"-------------x-------\r\n" +
	// 						"--------------x------\r\n" +
	// 						"---------------x-----\r\n" +
	// 						"----------------x----\r\n" +
	// 						"-----------------x---\r\n" +
	// 						"------------------x--\r\n" +
	// 						"-------------------x-\r\n" +
	// 						"--------------------x\r\n";
	//
	// 	// Act
	// 	Renderer.getInstance().render(s0, camera, '-', 'x');
	// 	String render0 = out.toString();
	// 	out.reset();
	//
	// 	Renderer.getInstance().render(s1, camera, '-', 'x');
	// 	String render1 = out.toString();
	// 	out.reset();
	//
	// 	Renderer.getInstance().render(s2, camera, '-', 'x');
	// 	String render2 = out.toString();
	// 	out.reset();
	//
	// 	Renderer.getInstance().render(s3, camera, '-', 'x');
	// 	String render3 = out.toString();
	// 	out.reset();
	//
	// 	// Assert
	// 	assertEquals(expected0, render0);
	// 	assertEquals(expected1, render1);
	// 	assertEquals(expected2, render2);
	// 	assertEquals(expected3, render3);
	// }
	//
	// @Test
	// public void ShouldRasterizeSegmentOverFirstOctant() throws GeometricException
	// {
	// 	// Arrange
	// 	LineSegment segment = new LineSegment(new Point(10, 10), new Point(20, 15));
	// 	Rectangle camera = new Rectangle(new Point[] { new Point(0, 0), new Point(0, 20), new Point(20, 20), new Point(20, 0)});
	// 	ByteArrayOutputStream out = TestUtil.setIOstreams("");
	//
	// 	String expected =	"---------------------\r\n" +
	// 						"---------------------\r\n" +
	// 						"---------------------\r\n" +
	// 						"---------------------\r\n" +
	// 						"---------------------\r\n" +
	// 						"-------------------xx\r\n" +
	// 						"-----------------xx--\r\n" +
	// 						"---------------xx----\r\n" +
	// 						"-------------xx------\r\n" +
	// 						"-----------xx--------\r\n" +
	// 						"----------x----------\r\n" +
	// 						"---------------------\r\n" +
	// 						"---------------------\r\n" +
	// 						"---------------------\r\n" +
	// 						"---------------------\r\n" +
	// 						"---------------------\r\n" +
	// 						"---------------------\r\n" +
	// 						"---------------------\r\n" +
	// 						"---------------------\r\n" +
	// 						"---------------------\r\n" +
	// 						"---------------------\r\n";
	//
	// 	// Act
	// 	Renderer.getInstance().render(segment, camera, '-', 'x');
	// 	String render = out.toString();
	// 	out.reset();
	//
	// 	// Assert
	// 	assertEquals(expected, render);
	// }
	//
	// @Test
	// public void ShouldRasterizeSegmentOverSecondOctant() throws GeometricException
	// {
	// 	// Arrange
	// 	LineSegment segment = new LineSegment(new Point(10, 10), new Point(18, 20));
	// 	Rectangle camera = new Rectangle(new Point[] { new Point(0, 0), new Point(0, 20), new Point(20, 20), new Point(20, 0)});
	// 	ByteArrayOutputStream out = TestUtil.setIOstreams("");
	//
	// 	String expected =	"------------------x--\r\n" +
	// 						"-----------------x---\r\n" +
	// 						"----------------x----\r\n" +
	// 						"----------------x----\r\n" +
	// 						"---------------x-----\r\n" +
	// 						"--------------x------\r\n" +
	// 						"-------------x-------\r\n" +
	// 						"------------x--------\r\n" +
	// 						"------------x--------\r\n" +
	// 						"-----------x---------\r\n" +
	// 						"----------x----------\r\n" +
	// 						"---------------------\r\n" +
	// 						"---------------------\r\n" +
	// 						"---------------------\r\n" +
	// 						"---------------------\r\n" +
	// 						"---------------------\r\n" +
	// 						"---------------------\r\n" +
	// 						"---------------------\r\n" +
	// 						"---------------------\r\n" +
	// 						"---------------------\r\n" +
	// 						"---------------------\r\n";
	//
	// 	// Act
	// 	Renderer.getInstance().render(segment, camera, '-', 'x');
	// 	String render = out.toString();
	// 	out.reset();
	//
	// 	// Assert
	// 	assertEquals(expected, render);
	// }
	//
	// @Test
	// public void ShouldRasterizeSegmentOverThirdOctant() throws GeometricException
	// {
	// 	// Arrange
	// 	LineSegment segment = new LineSegment(new Point(10, 10), new Point(8, 20));
	// 	Rectangle camera = new Rectangle(new Point[] { new Point(0, 0), new Point(0, 20), new Point(20, 20), new Point(20, 0)});
	// 	ByteArrayOutputStream out = TestUtil.setIOstreams("");
	//
	// 	String expected =	"--------x------------\r\n" +
	// 						"--------x------------\r\n" +
	// 						"--------x------------\r\n" +
	// 						"---------x-----------\r\n" +
	// 						"---------x-----------\r\n" +
	// 						"---------x-----------\r\n" +
	// 						"---------x-----------\r\n" +
	// 						"---------x-----------\r\n" +
	// 						"----------x----------\r\n" +
	// 						"----------x----------\r\n" +
	// 						"----------x----------\r\n" +
	// 						"---------------------\r\n" +
	// 						"---------------------\r\n" +
	// 						"---------------------\r\n" +
	// 						"---------------------\r\n" +
	// 						"---------------------\r\n" +
	// 						"---------------------\r\n" +
	// 						"---------------------\r\n" +
	// 						"---------------------\r\n" +
	// 						"---------------------\r\n" +
	// 						"---------------------\r\n";
	//
	// 	// Act
	// 	Renderer.getInstance().render(segment, camera, '-', 'x');
	// 	String render = out.toString();
	// 	out.reset();
	//
	// 	// Assert
	// 	assertEquals(expected, render);
	// }
	//
	// @Test
	// public void ShouldRasterizeSegmentOverFourthOctant() throws GeometricException
	// {
	// 	// Arrange
	// 	LineSegment segment = new LineSegment(new Point(10, 10), new Point(0, 19));
	// 	Rectangle camera = new Rectangle(new Point[] { new Point(0, 0), new Point(0, 20), new Point(20, 20), new Point(20, 0)});
	// 	ByteArrayOutputStream out = TestUtil.setIOstreams("");
	//
	// 	String expected =	"---------------------\r\n" +
	// 						"x--------------------\r\n" +
	// 						"-x-------------------\r\n" +
	// 						"--x------------------\r\n" +
	// 						"---x-----------------\r\n" +
	// 						"----xx---------------\r\n" +
	// 						"------x--------------\r\n" +
	// 						"-------x-------------\r\n" +
	// 						"--------x------------\r\n" +
	// 						"---------x-----------\r\n" +
	// 						"----------x----------\r\n" +
	// 						"---------------------\r\n" +
	// 						"---------------------\r\n" +
	// 						"---------------------\r\n" +
	// 						"---------------------\r\n" +
	// 						"---------------------\r\n" +
	// 						"---------------------\r\n" +
	// 						"---------------------\r\n" +
	// 						"---------------------\r\n" +
	// 						"---------------------\r\n" +
	// 						"---------------------\r\n";
	//
	// 	// Act
	// 	Renderer.getInstance().render(segment, camera, '-', 'x');
	// 	String render = out.toString();
	// 	out.reset();
	//
	// 	// Assert
	// 	assertEquals(expected, render);
	// }
	//
	// @Test
	// public void ShouldRasterizeSegmentOverFifthOctant() throws GeometricException
	// {
	// 	// Arrange
	// 	LineSegment segment = new LineSegment(new Point(10, 10), new Point(0, 6));
	// 	Rectangle camera = new Rectangle(new Point[] { new Point(0, 0), new Point(0, 20), new Point(20, 20), new Point(20, 0)});
	// 	ByteArrayOutputStream out = TestUtil.setIOstreams("");
	//
	// 	String expected =	"---------------------\r\n" +
	// 						"---------------------\r\n" +
	// 						"---------------------\r\n" +
	// 						"---------------------\r\n" +
	// 						"---------------------\r\n" +
	// 						"---------------------\r\n" +
	// 						"---------------------\r\n" +
	// 						"---------------------\r\n" +
	// 						"---------------------\r\n" +
	// 						"---------------------\r\n" +
	// 						"---------xx----------\r\n" +
	// 						"-------xx------------\r\n" +
	// 						"----xxx--------------\r\n" +
	// 						"--xx-----------------\r\n" +
	// 						"xx-------------------\r\n" +
	// 						"---------------------\r\n" +
	// 						"---------------------\r\n" +
	// 						"---------------------\r\n" +
	// 						"---------------------\r\n" +
	// 						"---------------------\r\n" +
	// 						"---------------------\r\n";
	//
	// 	// Act
	// 	Renderer.getInstance().render(segment, camera, '-', 'x');
	// 	String render = out.toString();
	// 	out.reset();
	//
	// 	// Assert
	// 	assertEquals(expected, render);
	// }
	//
	// @Test
	// public void ShouldRasterizeSegmentOverSixthOctant() throws GeometricException
	// {
	// 	// Arrange
	// 	LineSegment segment = new LineSegment(new Point(10, 10), new Point(9, 0));
	// 	Rectangle camera = new Rectangle(new Point[] { new Point(0, 0), new Point(0, 20), new Point(20, 20), new Point(20, 0)});
	// 	ByteArrayOutputStream out = TestUtil.setIOstreams("");
	//
	// 	String expected =	"---------------------\r\n" +
	// 						"---------------------\r\n" +
	// 						"---------------------\r\n" +
	// 						"---------------------\r\n" +
	// 						"---------------------\r\n" +
	// 						"---------------------\r\n" +
	// 						"---------------------\r\n" +
	// 						"---------------------\r\n" +
	// 						"---------------------\r\n" +
	// 						"---------------------\r\n" +
	// 						"----------x----------\r\n" +
	// 						"----------x----------\r\n" +
	// 						"----------x----------\r\n" +
	// 						"----------x----------\r\n" +
	// 						"----------x----------\r\n" +
	// 						"----------x----------\r\n" +
	// 						"---------x-----------\r\n" +
	// 						"---------x-----------\r\n" +
	// 						"---------x-----------\r\n" +
	// 						"---------x-----------\r\n" +
	// 						"---------x-----------\r\n";
	//
	// 	// Act
	// 	Renderer.getInstance().render(segment, camera, '-', 'x');
	// 	String render = out.toString();
	// 	out.reset();
	//
	// 	// Assert
	// 	assertEquals(expected, render);
	// }
	//
	// @Test
	// public void ShouldRasterizeSegmentOverSeventhOctant() throws GeometricException
	// {
	// 	// Arrange
	// 	LineSegment segment = new LineSegment(new Point(10, 10), new Point(13, 0));
	// 	Rectangle camera = new Rectangle(new Point[] { new Point(0, 0), new Point(0, 20), new Point(20, 20), new Point(20, 0)});
	// 	ByteArrayOutputStream out = TestUtil.setIOstreams("");
	//
	// 	String expected =	"---------------------\r\n" +
	// 						"---------------------\r\n" +
	// 						"---------------------\r\n" +
	// 						"---------------------\r\n" +
	// 						"---------------------\r\n" +
	// 						"---------------------\r\n" +
	// 						"---------------------\r\n" +
	// 						"---------------------\r\n" +
	// 						"---------------------\r\n" +
	// 						"---------------------\r\n" +
	// 						"----------x----------\r\n" +
	// 						"----------x----------\r\n" +
	// 						"-----------x---------\r\n" +
	// 						"-----------x---------\r\n" +
	// 						"-----------x---------\r\n" +
	// 						"-----------x---------\r\n" +
	// 						"------------x--------\r\n" +
	// 						"------------x--------\r\n" +
	// 						"------------x--------\r\n" +
	// 						"-------------x-------\r\n" +
	// 						"-------------x-------\r\n";
	//
	// 	// Act
	// 	Renderer.getInstance().render(segment, camera, '-', 'x');
	// 	String render = out.toString();
	// 	out.reset();
	//
	// 	// Assert
	// 	assertEquals(expected, render);
	// }
	//
	// @Test
	// public void ShouldRasterizeSegmentOverEightOctant() throws GeometricException
	// {
	// 	// Arrange
	// 	LineSegment segment = new LineSegment(new Point(10, 10), new Point(20, 7));
	// 	Rectangle camera = new Rectangle(new Point[] { new Point(0, 0), new Point(0, 20), new Point(20, 20), new Point(20, 0)});
	// 	ByteArrayOutputStream out = TestUtil.setIOstreams("");
	//
	// 	String expected =	"---------------------\r\n" +
	// 						"---------------------\r\n" +
	// 						"---------------------\r\n" +
	// 						"---------------------\r\n" +
	// 						"---------------------\r\n" +
	// 						"---------------------\r\n" +
	// 						"---------------------\r\n" +
	// 						"---------------------\r\n" +
	// 						"---------------------\r\n" +
	// 						"---------------------\r\n" +
	// 						"----------xx---------\r\n" +
	// 						"------------xxxx-----\r\n" +
	// 						"----------------xxx--\r\n" +
	// 						"-------------------xx\r\n" +
	// 						"---------------------\r\n" +
	// 						"---------------------\r\n" +
	// 						"---------------------\r\n" +
	// 						"---------------------\r\n" +
	// 						"---------------------\r\n" +
	// 						"---------------------\r\n" +
	// 						"---------------------\r\n";
	//
	// 	// Act
	// 	Renderer.getInstance().render(segment, camera, '-', 'x');
	// 	String render = out.toString();
	// 	out.reset();
	//
	// 	// Assert
	// 	assertEquals(expected, render);
	// }
	//
	// @Test
	// public void ShouldRasterizeSegmentWithFloats() throws GeometricException
	// {
	// 	// Arrange
	// 	LineSegment segment = new LineSegment(new Point(25.2, 28.9), new Point(21.5, 23.1));
	// 	Rectangle camera = new Rectangle(new Point[] { new Point(20, 22), new Point(20, 30), new Point(26, 30), new Point(26, 22)});
	// 	ByteArrayOutputStream out = TestUtil.setIOstreams("");
	//
	// 	String expected =	"-------\r\n" +
	// 						"-----x-\r\n" +
	// 						"-----x-\r\n" +
	// 						"----x--\r\n" +
	// 						"----x--\r\n" +
	// 						"---x---\r\n" +
	// 						"---x---\r\n" +
	// 						"--x----\r\n" +
	// 						"-------\r\n";
	//
	// 	// Act
	// 	Renderer.getInstance().render(segment, camera, '-', 'x');
	// 	String render = out.toString();
	// 	out.reset();
	//
	// 	// Assert
	// 	assertEquals(expected, render);
	// }
	//
	// @Test
	// public void ShouldRasterizeSegmentWithSamePoint() throws GeometricException
	// {
	// 	// Arrange
	// 	LineSegment segment = new LineSegment(new Point(10.1, 10.1), new Point(9.9, 9.9));
	// 	Rectangle camera = new Rectangle(new Point[] { new Point(9, 9), new Point(9, 11), new Point(11, 11), new Point(11, 9)});
	// 	ByteArrayOutputStream out = TestUtil.setIOstreams("");
	//
	// 	String expected =	"---\r\n" +
	// 						"-x-\r\n" +
	// 						"---\r\n";
	//
	// 	// Act
	// 	Renderer.getInstance().render(segment, camera, '-', 'x');
	// 	String render = out.toString();
	// 	out.reset();
	//
	// 	// Assert
	// 	assertEquals(expected, render);
	// }
	//
	// @Test
	// public void ShouldRasterizePolygonSides() throws GeometricException
	// {
	// 	// Arrange
	// 	Polygon poly = new Polygon(new Point[]
	// 	{
	// 		new Point(0, 5),
	// 		new Point(5, 7),
	// 		new Point(7, 2),
	// 		new Point(2, 0),
	// 	});
	// 	Rectangle camera = new Rectangle(new Point[] { new Point(0, 0), new Point(0, 8), new Point(8, 8), new Point(8, 0)});
	// 	ByteArrayOutputStream out = TestUtil.setIOstreams("");
	//
	// 	String expected =	"---------\r\n" +
	// 						"----xx---\r\n" +
	// 						"--xx-x---\r\n" +
	// 						"xx----x--\r\n" +
	// 						"x-----x--\r\n" +
	// 						"-x-----x-\r\n" +
	// 						"-x----xx-\r\n" +
	// 						"--x-xx---\r\n" +
	// 						"--xx-----\r\n";
	//
	// 	// Act
	// 	Renderer.getInstance().render(segment, camera, '-', 'x');
	// 	String render = out.toString();
	// 	out.reset();
	//
	// 	// Assert
	// 	assertEquals(expected, render);
	// }
	//
	// @Test
	// public void ShouldRasterizePolygon() throws GeometricException
	// {
	// 	// Arrange
	// 	Polygon poly = new Polygon(new Point[]
	// 	{
	// 		new Point(1, 2),
	// 		new Point(2, 8),
	// 		new Point(8, 7),
	// 		new Point(7, 1),
	// 	});
	// 	Rectangle camera = new Rectangle(new Point[] { new Point(0, 0), new Point(0, 9), new Point(9, 9), new Point(9, 0)});
	// 	ByteArrayOutputStream out = TestUtil.setIOstreams("");
	//
	// 	String expected =	"----------" +
	// 						"--xxx-----" +
	// 						"--xxxxxxx-" +
	// 						"--xxxxxxx-" +
	// 						"--xxxxxxx-" +
	// 						"-xxxxxxxx-" +
	// 						"-xxxxxxx--" +
	// 						"-xxxxxxx--" +
	// 						"----xxxx--" +
	// 						"----------";
	//
	// 	// Act
	// 	Renderer.getInstance().render(segment, camera, '-', 'x');
	// 	String render = out.toString();
	// 	out.reset();
	//
	// 	// Assert
	// 	assertEquals(expected, render);
	// }
	//
	// @Test
	// public void ShouldRasterizePolygonWhenThereAreGaps() throws GeometricException
	// {
	// 	// Arrange
	// 	Polygon poly = new Polygon(new Point[]
	// 	{
	// 		new Point(1, 10),
	// 		new Point(1, 6),
	// 		new Point(3, 5),
	// 		new Point(3, 1),
	// 		new Point(4, 1),
	// 		new Point(4, 5),
	// 		new Point(6, 6),
	// 		new Point(6, 10),
	// 		new Point(5, 6),
	// 		new Point(4, 10),
	// 		new Point(3, 6),
	// 	});
	// 	Rectangle camera = new Rectangle(new Point[] { new Point(0, 0), new Point(0, 11), new Point(7, 11), new Point(7, 0)});
	// 	ByteArrayOutputStream out = TestUtil.setIOstreams("");
	//
	// 	String expected =	"--------" +
	// 						"-x--x-x-" +
	// 						"-x--x-x-" +
	// 						"-xx-x-x-" +
	// 						"-xxxxxx-" +
	// 						"-xxxxxx-" +
	// 						"--xxx---" +
	// 						"---xx---" +
	// 						"---xx---" +
	// 						"---xx---" +
	// 						"---xx---" +
	// 						"--------";
	//
	// 	// Act
	// 	Renderer.getInstance().render(segment, camera, '-', 'x');
	// 	String render = out.toString();
	// 	out.reset();
	//
	// 	// Assert
	// 	assertEquals(expected, render);
	// }
	//
	// @Test
	// public void ShouldRasterizePolygonWhenParalelLines() throws GeometricException
	// {
	// 	// Arrange
	// 	Polygon poly = new Polygon(new Point[]
	// 	{
	// 		new Point(1, 1),
	// 		new Point(6, 1),
	// 		new Point(5, 3),
	// 		new Point(2, 3),
	// 	});
	// 	Rectangle camera = new Rectangle(new Point[] { new Point(0, 0), new Point(0, 4), new Point(7, 4), new Point(7, 0)});
	// 	ByteArrayOutputStream out = TestUtil.setIOstreams("");
	//
	// 	String expected =	"--------" +
	// 						"--xxxx--" +
	// 						"--xxxx--" +
	// 						"-xxxxxx-" +
	// 						"--------";
	//
	// 	// Act
	// 	Renderer.getInstance().render(segment, camera, '-', 'x');
	// 	String render = out.toString();
	// 	out.reset();
	//
	// 	// Assert
	// 	assertEquals(expected, render);
	// }
	//
	// @Test
	// public void ShouldRasterizePolygonWhenParalelLinesThenGap() throws GeometricException
	// {
	// 	// Arrange
	// 	Polygon poly = new Polygon(new Point[]
	// 	{
	// 		new Point(1, 1),
	// 		new Point(2, 3),
	// 		new Point(5, 3),
	// 		new Point(7, 6),
	// 		new Point(7, 1),
	// 	});
	// 	Rectangle camera = new Rectangle(new Point[] { new Point(0, 0), new Point(0, 7), new Point(8, 7), new Point(8, 0)});
	// 	ByteArrayOutputStream out = TestUtil.setIOstreams("");
	//
	// 	String expected =	"---------" +
	// 						"-------x-" +
	// 						"------xx-" +
	// 						"------xx-" +
	// 						"--xxxxxx-" +
	// 						"--xxxxxx-" +
	// 						"-xxxxxxx-" +
	// 						"---------";
	//
	// 	// Act
	// 	Renderer.getInstance().render(segment, camera, '-', 'x');
	// 	String render = out.toString();
	// 	out.reset();
	//
	// 	// Assert
	// 	assertEquals(expected, render);
	// }
	//
	// @Test
	// public void ShouldDrawWhenOffCamera()
	// {
	// 	// Arrange
	// 	Polygon poly = new Polygon(new Point[]
	// 	{
	// 		new Point(1, 1),
	// 		new Point(2, 3),
	// 		new Point(5, 3),
	// 		new Point(7, 6),
	// 		new Point(7, 1),
	// 	});
	// 	Rectangle camera = new Rectangle(new Point[] { new Point(0, 0), new Point(0, 7), new Point(5, 7), new Point(5, 0)});
	// 	ByteArrayOutputStream out = TestUtil.setIOstreams("");
	//
	// 	String expected =	"------" +
	// 						"------" +
	// 						"------" +
	// 						"------" +
	// 						"--xxxx" +
	// 						"--xxxx" +
	// 						"-xxxxx" +
	// 						"------";
	//
	// 	// Act
	// 	Renderer.getInstance().render(segment, camera, '-', 'x');
	// 	String render = out.toString();
	// 	out.reset();
	//
	// 	// Assert
	// 	assertEquals(expected, render);
	// }
	//
	// @Test
	// public void ShouldRenderScene()
	// {
	// 	// Arrange
	// 	Polygon poly = new Polygon(new Point[]
	// 	{
	// 		new Point(1, 2),
	// 		new Point(2, 8),
	// 		new Point(8, 7),
	// 		new Point(7, 1),
	// 	});
	// 	RenderData rData = new RenderData(poly, true, 0, 'x');
	// 	MockRenderable mockRenderable = new MockRenderable(rData);
	// 	Rectangle camera = new Rectangle(new Point[] { new Point(0, 0), new Point(0, 9), new Point(9, 9), new Point(9, 0)});
	// 	Scene sc = new Scene();
	// 	sc.add(mockRenderable);
	// 	ByteArrayOutputStream out = TestUtil.setIOstreams("");
	//
	// 	String expected =	"----------" +
	// 						"--xxx-----" +
	// 						"--xxxxxxx-" +
	// 						"--xxxxxxx-" +
	// 						"--xxxxxxx-" +
	// 						"-xxxxxxxx-" +
	// 						"-xxxxxxx--" +
	// 						"-xxxxxxx--" +
	// 						"----xxxx--" +
	// 						"----------";
	//
	// 	// Act
	// 	Renderer.getInstance().render(sc, camera, '-');
	// 	String render = out.toString();
	// 	out.reset();
	//
	// 	// Assert
	// 	assertEquals(expected, render);
	// }
	//
	// @Test
	// public void ShouldRenderEmptyScene()
	// {
	// 	// Arrange
	// 	Rectangle camera = new Rectangle(new Point[] { new Point(0, 0), new Point(0, 9), new Point(9, 9), new Point(9, 0)});
	// 	Scene sc = new Scene();
	// 	ByteArrayOutputStream out = TestUtil.setIOstreams("");
	//
	// 	String expected =	"5555555555" +
	// 						"5555555555" +
	// 						"5555555555" +
	// 						"5555555555" +
	// 						"5555555555" +
	// 						"5555555555" +
	// 						"5555555555" +
	// 						"5555555555" +
	// 						"5555555555" +
	// 						"5555555555" +
	//
	// 	// Act
	// 	Renderer.getInstance().render(sc, camera, '5');
	// 	String render = out.toString();
	// 	out.reset();
	//
	// 	// Assert
	// 	assertEquals(expected, render);
	// }
	//
	// @Test
	// public void ShouldRenderNoFill() throws GeometricException, GameEngineException
	// {
	// 	// Arrange
	// 	Polygon poly = new Polygon(new Point[]
	// 	{
	// 		new Point(0, 5),
	// 		new Point(5, 7),
	// 		new Point(7, 2),
	// 		new Point(2, 0),
	// 	});
	// 	RenderData rData = new RenderData(poly, false, 0, 'x');
	// 	MockRenderable mockRenderable = new MockRenderable(rData);
	// 	Scene sc = new Scene();
	// 	sc.add(mockRenderable);
	// 	Rectangle camera = new Rectangle(new Point[] { new Point(0, 0), new Point(0, 8), new Point(8, 8), new Point(8, 0)});
	// 	ByteArrayOutputStream out = TestUtil.setIOstreams("");
	//
	// 	String expected =	"---------\r\n" +
	// 						"----xx---\r\n" +
	// 						"--xx-x---\r\n" +
	// 						"xx----x--\r\n" +
	// 						"x-----x--\r\n" +
	// 						"-x-----x-\r\n" +
	// 						"-x----xx-\r\n" +
	// 						"--x-xx---\r\n" +
	// 						"--xx-----\r\n";
	//
	// 	// Act
	// 	Renderer.getInstance().render(sc, camera, '-');
	// 	String render = out.toString();
	// 	out.reset();
	//
	// 	// Assert
	// 	assertEquals(expected, render);
	// }
	//
	// @Test
	// public void ShouldRenderSceneByLayer()
	// {
	// 	// Arrange
	// 	Polygon poly0 = new Polygon(new Point[]
	// 	{
	// 		new Point(1, 2),
	// 		new Point(2, 8),
	// 		new Point(8, 7),
	// 		new Point(7, 1),
	// 	});
	// 	RenderData rData0 = new RenderData(poly0, true, 1, 'x');
	// 	MockRenderable mockRenderable0 = new MockRenderable(rData0);
	// 	Polygon poly1 = new Polygon(new Point[]
	// 	{
	// 		new Point(1, 7),
	// 		new Point(1, 8),
	// 		new Point(8, 8),
	// 		new Point(8, 7),
	// 	});
	// 	RenderData rData1 = new RenderData(poly1, true, 0, 'y');
	// 	MockRenderable mockRenderable1 = new MockRenderable(rData1);
	//
	// 	Rectangle camera = new Rectangle(new Point[] { new Point(0, 0), new Point(0, 9), new Point(9, 9), new Point(9, 0)});
	// 	Scene sc = new Scene();
	// 	sc.add(mockRenderable0);
	// 	sc.add(mockRenderable1);
	// 	ByteArrayOutputStream out = TestUtil.setIOstreams("");
	//
	// 	String expected =	"----------" +
	// 						"-yxxxyyyy-" +
	// 						"-yxxxxxxx-" +
	// 						"--xxxxxxx-" +
	// 						"--xxxxxxx-" +
	// 						"-xxxxxxxx-" +
	// 						"-xxxxxxx--" +
	// 						"-xxxxxxx--" +
	// 						"----xxxx--" +
	// 						"----------";
	//
	// 	// Act
	// 	Renderer.getInstance().render(sc, camera, '-');
	// 	String render = out.toString();
	// 	out.reset();
	//
	// 	// Assert
	// 	assertEquals(expected, render);
	// }
	//
	//
	// @Test
	// public void ShouldRenderOverlay()
	// {
	// 	// Arrange
	// 	Character[][] overlay = new Character[][]
	// 	{
	// 		{'d', null, null, null, null, null, null, null, null, 'c'},
	// 		{null, 'o', null, null, null, null, null, null, null, null},
	// 		{null, null, 'o', null, null, null, null, null, null, null},
	// 		{null, null, null, 'o', null, null, null, null, null, null},
	// 		{null, null, null, null, 'o', null, null, null, null, null},
	// 		{null, null, null, null, null, 'o', null, null, null, null},
	// 		{null, null, null, null, null, null, 'o', null, null, null},
	// 		{null, null, null, null, null, null, null, 'o', null, null},
	// 		{null, null, null, null, null, null, null, null, 'o', null},
	// 		{'a', null, null, null, null, null, null, null, null, 'b'},
	// 	};
	// 	MockOverlay mockOverlay = new MockOverlay(overlay);
	// 	Rectangle camera = new Rectangle(new Point[] { new Point(0, 0), new Point(0, 9), new Point(9, 9), new Point(9, 0)});
	// 	Scene sc = new Scene();
	// 	sc.add(mockOverlay);
	// 	ByteArrayOutputStream out = TestUtil.setIOstreams("");
	//
	// 	String expected =	"d--------c" +
	// 						"-o--------" +
	// 						"--o-------" +
	// 						"---o------" +
	// 						"----o-----" +
	// 						"-----o----" +
	// 						"------o---" +
	// 						"-------o--" +
	// 						"--------o-" +
	// 						"a--------b";
	//
	// 	// Act
	// 	Renderer.getInstance().render(sc, camera, '-');
	// 	String render = out.toString();
	// 	out.reset();
	//
	// 	// Assert
	// 	assertEquals(expected, render);
	// }
	//
	//
	// @Test
	// public void ShouldApplyOverlayToScene()
	// {
	// 	// Arrange
	// 	Polygon poly = new Polygon(new Point[]
	// 		{
	// 			new Point(1, 2),
	// 			new Point(2, 8),
	// 			new Point(8, 7),
	// 			new Point(7, 1),
	// 	});
	// 	RenderData rData = new RenderData(poly, true, 0, 'x');
	// 	MockRenderable mockRenderable = new MockRenderable(rData);
	// 	Character[][] overlay = new Character[][]
	// 	{
	// 		{'d', null, null, null, null, null, null, null, null, 'c'},
	// 		{null, 'o', null, null, null, null, null, null, null, null},
	// 		{null, null, 'o', null, null, null, null, null, null, null},
	// 		{null, null, null, 'o', null, null, null, null, null, null},
	// 		{null, null, null, null, 'o', null, null, null, null, null},
	// 		{null, null, null, null, null, 'o', null, null, null, null},
	// 		{null, null, null, null, null, null, 'o', null, null, null},
	// 		{null, null, null, null, null, null, null, 'o', null, null},
	// 		{null, null, null, null, null, null, null, null, 'o', null},
	// 		{'a', null, null, null, null, null, null, null, null, 'b'},
	// 	};
	// 	MockOverlay mockOverlay = new MockOverlay(overlay);
	// 	Rectangle camera = new Rectangle(new Point[] { new Point(0, 0), new Point(0, 9), new Point(9, 9), new Point(9, 0)});
	// 	Scene sc = new Scene();
	// 	sc.add(mockOverlay);
	// 	sc.add(mockRenderable);
	// 	ByteArrayOutputStream out = TestUtil.setIOstreams("");
	//
	// 	String expected =	"d--------c" +
	// 						"-oxxx-----" +
	// 						"--oxxxxxx-" +
	// 						"--xoxxxxx-" +
	// 						"--xxoxxxx-" +
	// 						"-xxxxoxxx-" +
	// 						"-xxxxxox--" +
	// 						"-xxxxxxo--" +
	// 						"----xxxxo-" +
	// 						"a--------b";
	//
	// 	// Act
	// 	Renderer.getInstance().render(sc, camera, '-');
	// 	String render = out.toString();
	// 	out.reset();
	//
	// 	// Assert
	// 	assertEquals(expected, render);
	// }
}
