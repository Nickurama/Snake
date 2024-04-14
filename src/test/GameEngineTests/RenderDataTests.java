package GameEngineTests;

import GameEngine.*;
import Geometry.*;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.jupiter.api.Test;

public class RenderDataTests
{
	@Test
	public void ShouldGetDrawShape() throws GeometricException
	{
		// Arrange
		Polygon shape = new Polygon(new Point[]
		{
			new Point(1, 1),
			new Point(1, 2),
			new Point(2, 2),
			new Point(2, 1)
		});
		boolean isRasterized = true;
		int layer = 5;
		Character character = 'x';

		// Act
		RenderData rData = new RenderData(shape, isRasterized, layer, character);

		// Assert
		assertEquals(shape, rData.getShape());
	}

	@Test
	public void ShouldGetRasterizationProperty() throws GeometricException
	{
		// Arrange
		Polygon shape = new Polygon(new Point[]
		{
			new Point(1, 1),
			new Point(1, 2),
			new Point(2, 2),
			new Point(2, 1)
		});
		boolean isRasterized = true;
		int layer = 5;
		Character character = 'x';

		// Act
		RenderData rData = new RenderData(shape, isRasterized, layer, character);

		// Assert
		assertTrue(rData.isRasterized());
	}

	@Test
	public void ShouldGetLayer() throws GeometricException
	{
		// Arrange
		Polygon shape = new Polygon(new Point[]
		{
			new Point(1, 1),
			new Point(1, 2),
			new Point(2, 2),
			new Point(2, 1)
		});
		boolean isRasterized = true;
		int layer = 5;
		Character character = 'x';

		// Act
		RenderData rData = new RenderData(shape, isRasterized, layer, character);

		// Assert
		assertEquals(5, rData.getLayer());
	}

	@Test
	public void ShouldGetTextualCharacter() throws GeometricException
	{
		// Arrange
		Polygon shape = new Polygon(new Point[]
		{
			new Point(1, 1),
			new Point(1, 2),
			new Point(2, 2),
			new Point(2, 1)
		});
		boolean isRasterized = true;
		int layer = 5;
		Character character = 'x';

		// Act
		RenderData rData = new RenderData(shape, isRasterized, layer, character);

		// Assert
		assertEquals('x', rData.getCharacter());
	}

	@Test
	public void ShouldMakeDeepCopyOnShape() throws GeometricException
	{
		// Arrange
		Polygon shape = new Polygon(new Point[]
		{
			new Point(1, 1),
			new Point(1, 2),
			new Point(2, 2),
			new Point(2, 1)
		});
		boolean isRasterized = true;
		int layer = 5;
		Character character = 'x';

		// Act
		RenderData rData = new RenderData(shape, isRasterized, layer, character);
		Polygon old = new Polygon(shape);
		shape = new Polygon(new Point[]
		{
			new Point(0, 0),
			new Point(0, 1),
			new Point(1, 1),
			new Point(1, 0)
		});

		// Assert
		assertEquals(old, rData.getShape());
	}

	@Test
	public void ShouldCompareByLayer() throws GeometricException
	{
		// Arrange
		Polygon shape = new Polygon(new Point[]
		{
			new Point(1, 1),
			new Point(1, 2),
			new Point(2, 2),
			new Point(2, 1)
		});
		boolean isRasterized = true;
		Character character = 'x';

		// Act
		RenderData rData0 = new RenderData(shape, isRasterized, 4, character);
		RenderData rData1 = new RenderData(shape, isRasterized, 9, character);
		RenderData rData2 = new RenderData(shape, isRasterized, 3, character);
		RenderData rData3 = new RenderData(shape, isRasterized, 1, character);
		RenderData rData4 = new RenderData(shape, isRasterized, 3, character);

		// Assert
		assertTrue(rData0.compareTo(rData1) < 0);
		assertTrue(rData2.compareTo(rData4) == 0);
		assertTrue(rData2.compareTo(rData3) > 0);
	}

	@Test
	public void ShouldSortByLayer() throws GeometricException
	{
		// Arrange
		Polygon shape = new Polygon(new Point[]
		{
			new Point(1, 1),
			new Point(1, 2),
			new Point(2, 2),
			new Point(2, 1)
		});
		boolean isRasterized = true;
		Character character = 'x';
		RenderData rData0 = new RenderData(shape, isRasterized, 4, character);
		RenderData rData1 = new RenderData(shape, isRasterized, 9, character);
		RenderData rData2 = new RenderData(shape, isRasterized, 3, character);
		RenderData rData3 = new RenderData(shape, isRasterized, 8, character);
		RenderData rData4 = new RenderData(shape, isRasterized, 1, character);
		RenderData rData5 = new RenderData(shape, isRasterized, 3, character);
		ArrayList<RenderData> list = new ArrayList<RenderData>();
		list.add(rData0);
		list.add(rData1);
		list.add(rData2);
		list.add(rData3);
		list.add(rData4);
		list.add(rData5);
		ArrayList<Integer> expected = new ArrayList<Integer>();
		expected.add(1);
		expected.add(3);
		expected.add(3);
		expected.add(4);
		expected.add(8);
		expected.add(9);

		// Act
		Collections.sort(list);

		// Assert
		for (int i = 0; i < 6; i++)
			assertEquals(expected.get(i), list.get(i).getLayer());
	}
}
