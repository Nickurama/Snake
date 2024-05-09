package GameEngine;

import Geometry.*;

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
		RenderData<Polygon> rData = new RenderData<Polygon>(shape, isRasterized, layer, character);

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
		RenderData<Polygon> rData = new RenderData<Polygon>(shape, isRasterized, layer, character);

		// Assert
		assertTrue(rData.isFilled());
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
		RenderData<Polygon> rData = new RenderData<Polygon>(shape, isRasterized, layer, character);

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
		RenderData<Polygon> rData = new RenderData<Polygon>(shape, isRasterized, layer, character);

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
		RenderData<Polygon> rData = new RenderData<Polygon>(shape, isRasterized, layer, character);
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
		RenderData<Polygon> rData0 = new RenderData<Polygon>(shape, isRasterized, 4, character);
		RenderData<Polygon> rData1 = new RenderData<Polygon>(shape, isRasterized, 9, character);
		RenderData<Polygon> rData2 = new RenderData<Polygon>(shape, isRasterized, 3, character);
		RenderData<Polygon> rData3 = new RenderData<Polygon>(shape, isRasterized, 1, character);
		RenderData<Polygon> rData4 = new RenderData<Polygon>(shape, isRasterized, 3, character);

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
		RenderData<Polygon> rData0 = new RenderData<Polygon>(shape, isRasterized, 4, character);
		RenderData<Polygon> rData1 = new RenderData<Polygon>(shape, isRasterized, 9, character);
		RenderData<Polygon> rData2 = new RenderData<Polygon>(shape, isRasterized, 3, character);
		RenderData<Polygon> rData3 = new RenderData<Polygon>(shape, isRasterized, 8, character);
		RenderData<Polygon> rData4 = new RenderData<Polygon>(shape, isRasterized, 1, character);
		RenderData<Polygon> rData5 = new RenderData<Polygon>(shape, isRasterized, 3, character);
		ArrayList<RenderData<Polygon>> list = new ArrayList<RenderData<Polygon>>();
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

	@Test
	public void ShouldWorkWithAnyGeometricShape() throws GeometricException
	{
		// Arrange
		Polygon poly = new Polygon(new Point[]
		{
			new Point(1, 1),
			new Point(1, 2),
			new Point(2, 2),
			new Point(2, 1)
		});
		Triangle tri = new Triangle(new Point[]
		{
			new Point(3, 3),
			new Point(3, 4),
			new Point(4, 3),
		});
		Circle cir = new Circle(new Point(3, 3), 2);

		boolean isRasterized = true;
		int layer = 5;
		Character character = 'x';

		// Act
		RenderData<Polygon> polyData = new RenderData<Polygon>(poly, isRasterized, layer, character);
		RenderData<Triangle> triData = new RenderData<Triangle>(tri, isRasterized, layer, character);
		RenderData<Circle> cirData = new RenderData<Circle>(cir, isRasterized, layer, character);

		// Assert
		assertEquals(poly, polyData.getShape());
		assertEquals(tri, triData.getShape());
		assertEquals(cir, cirData.getShape());
	}
}
