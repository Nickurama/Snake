package GameEngine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import Geometry.*;

public class Renderer
{
	private static class RasterPoint
	{
		private LineSegment segment;
		private double x;

		public RasterPoint(double x, LineSegment segment)
		{
			this.x = x;
			this.segment = segment;
		}

		public double x() { return this.x; }
		public LineSegment segment() { return this.segment; }
	}

	private static Renderer instance = null;
	private BoundingBox camera;
	private char backgroundChar;
	private char drawChar;
	private char[][] raster;

	private Renderer()
	{
		// Singleton
	}

	public static Renderer getInstance()
	{
		if (instance == null)
			instance = new Renderer();

		return instance;
	}

	public static void resetInstance()
	{
		if (instance != null)
			instance = new Renderer();
	}

	public void init(Rectangle camera, char backgroundChar)
	{
		this.camera = new BoundingBox(camera);
		this.backgroundChar = backgroundChar;
		generateRaster(this.camera);
	}

	private void generateRaster(BoundingBox cam)
	{
		int dy = (int)Math.floor(cam.maxPoint().Y()) - (int)Math.ceil(cam.minPoint().Y()) + 1;
		int dx = (int)Math.floor(cam.maxPoint().X()) - (int)Math.ceil(cam.minPoint().X()) + 1;

		raster = new char[dy][dx];
		for (int i = 0; i < dy; i++) // iterate over y
		{
			raster[i] = new char[dx];
			for (int j = 0; j < dx; j++) // iterate over x
			{
				raster[i][j] = backgroundChar;
			}
		}
	}

	private void draw(int x, int y)
	{
		if (x < camera.minPoint().X() || x > camera.maxPoint().X())
			return;
		if (y < camera.minPoint().Y() || y > camera.maxPoint().Y())
			return;

		x = x - (int)Math.ceil(camera.minPoint().X());
		y = (raster.length - 1) - (y - (int)Math.ceil(camera.minPoint().Y()));

		raster[y][x] = drawChar;
	}

	private void print()
	{
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < raster.length; i++) // iterate over y
		{
			builder.append(new String(raster[i]));
			builder.append('\n');
		}
		System.out.print(builder.toString());
	}

	public void render(Scene scene, Rectangle camera, char backgroundChar)
	{
		this.init(camera, backgroundChar);
		try
		{
			render(scene);
		}
		catch (GameEngineException e)
		{
			throw new Error("Should not have happened. Renderer should have been initialized correctly on full function call.");
		}
	}

	public void renderSides(Circle circle, Rectangle camera, char backgroundChar, char drawChar)
	{
		this.init(camera, backgroundChar);
		try
		{
			renderSides(circle, drawChar);
		}
		catch (GameEngineException e)
		{
			throw new Error("Should not have happened. Renderer should have been initialized correctly on full function call.");
		}
	}

	public void render(Circle circle, Rectangle camera, char backgroundChar, char drawChar)
	{
		this.init(camera, backgroundChar);
		try
		{
			render(circle, drawChar);
		}
		catch (GameEngineException e)
		{
			throw new Error("Should not have happened. Renderer should have been initialized correctly on full function call.");
		}
	}

	public void render(Polygon poly, Rectangle camera, char backgroundChar, char drawChar)
	{
		this.init(camera, backgroundChar);
		try
		{
			render(poly, drawChar);
		}
		catch (GameEngineException e)
		{
			throw new Error("Should not have happened. Renderer should have been initialized correctly on full function call.");
		}
	}

	public void renderSides(Polygon poly, Rectangle camera, char backgroundChar, char drawChar)
	{
		this.init(camera, backgroundChar);
		try
		{
			renderSides(poly, drawChar);
		}
		catch (GameEngineException e)
		{
			throw new Error("Should not have happened. Renderer should have been initialized correctly on full function call.");
		}
	}

	public void render(LineSegment segment, Rectangle camera, char backgroundChar, char drawChar)
	{
		this.init(camera, backgroundChar);
		try
		{
			render(segment, drawChar);
		}
		catch (GameEngineException e)
		{
			throw new Error("Should not have happened. Renderer should have been initialized correctly on full function call.");
		}
	}

	public void render(Scene scene) throws GameEngineException
	{
		validateRender();
		rasterize(scene.renderablesArr());
		print();
	}

	public void render(Circle circle, char drawChar) throws GameEngineException
	{
		validateRender();
		this.drawChar = drawChar;
		rasterize(circle);
		print();
	}

	public void renderSides(Circle circle, char drawChar) throws GameEngineException
	{
		validateRender();
		this.drawChar = drawChar;
		rasterizeSides(circle);
		print();
	}

	public void render(Polygon poly, char drawChar) throws GameEngineException
	{
		validateRender();
		this.drawChar = drawChar;
		rasterize(poly);
		print();
	}

	public void renderSides(Polygon poly, char drawChar) throws GameEngineException
	{
		validateRender();
		this.drawChar = drawChar;
		rasterizeSides(poly);
		print();
	}

	public void render(LineSegment segment, char drawChar) throws GameEngineException
	{
		validateRender();
		this.drawChar = drawChar;
		rasterize(segment);
		print();
	}

	private void validateRender() throws GameEngineException
	{
		if (this.camera == null)
			throw new GameEngineException("Must initialize renderer or pass all arguments.");
	}

	public void rasterize(RenderData<?>[] renderDataArr)
	{
		Arrays.sort(renderDataArr);

		for (RenderData<?> rData : renderDataArr)
		{
			IGeometricShape<?> shape = rData.getShape();
			this.drawChar = rData.getCharacter();
			if (shape instanceof Polygon)
			{
				Polygon poly = (Polygon)shape;
				if (rData.isFilled())
					rasterize(poly);
				else
					rasterizeSides(poly);
			}
			else if (shape instanceof Circle)
			{
				Circle circle = (Circle)shape;
				if (rData.isFilled())
					rasterize(circle);
				else
					rasterizeSides(circle);
			}
			else
			{
				throw new UnsupportedOperationException("Unrecognized shape to render: " + shape.getClass());
			}
		}
	}

	public void rasterize(Circle circle)
	{

	}

	public void rasterizeSides(Circle circle)
	{

	}

	public void rasterize(Polygon poly)
	{
		// find minY and maxY
		Point[] vertices = poly.vertices();
		int minY = (int) vertices[0].Y();
		int maxY = minY;
		for (Point vertice : poly.vertices())
		{
			minY = Math.min((int) vertice.Y(), minY);
			maxY = Math.max((int) Math.ceil(vertice.Y()), maxY);
		}

		// scan every Y
		for (int currY = minY; currY <= maxY; currY++)
		{
			// generate current scanline
			Line scanLine;
			try
			{
				scanLine = new Line(new Point(0.0, currY), new Point(1.0, currY));
			}
			catch (GeometricException e)
			{
				throw new Error("Should not happen, the scanline should always be valid.");
			}


			// calculate all intersections
			ArrayList<RasterPoint> intersections = new ArrayList<RasterPoint>();
			for (LineSegment segment : poly.sides())
			{
				if (segment.line().isParalel(scanLine) && MathUtil.areEqual(segment.firstPoint().Y(), currY))
				{
					double firstX = segment.firstPoint().X(); // TODO can be joined with below statements
					double secondX = segment.secondPoint().X();

					intersections.add(new RasterPoint(firstX, segment));
					intersections.add(new RasterPoint(secondX, segment));
				}
				else if (segment.intersectsInclusive(scanLine))
				{
					double x = segment.line().intersection(scanLine).X();
					intersections.add(new RasterPoint(x, segment));
				}
			}

			// sort intersections
			Collections.sort(intersections, (rp0, rp1) -> MathUtil.areEqual(rp0.x(), rp1.x()) ? 0 : (rp0.x() < rp1.x() ? -1 : 1));

			// add all raster points to list
			RasterPoint last = null;
			boolean isPrinting = false;
			boolean isHorizontal = false;
			boolean lastHorizontalMin = false;
			for (RasterPoint curr : intersections)
			{
				if (last == null)
				{
					last = curr;
					isPrinting = true;
					continue;
				}

				if (MathUtil.areEqual(last.x(), curr.x()))
				{

					VirtualPoint intersection = new VirtualPoint(curr.x(), currY);
					VirtualPoint minPointLast = last.segment().firstPoint().Y() < last.segment().secondPoint().Y() ? last.segment().firstPoint() : last.segment().secondPoint();
					VirtualPoint minPointCurr = curr.segment().firstPoint().Y() < curr.segment().secondPoint().Y() ? curr.segment().firstPoint() : curr.segment().secondPoint();

					boolean isMinPointOfLastSegment = intersection.equals(minPointLast);
					boolean isMinPointOfCurrSegment = intersection.equals(minPointCurr);
					if (scanLine.isParalel(last.segment().line()) || scanLine.isParalel(curr.segment().line()))
					{
						if (!isHorizontal)
						{
							isHorizontal = true;
							lastHorizontalMin = isMinPointOfLastSegment && isMinPointOfCurrSegment;
							continue;
						}
						else
						{
							isHorizontal = false;
							boolean currHorizontalMin = isMinPointOfLastSegment && isMinPointOfCurrSegment;
							boolean changedDirection = currHorizontalMin == lastHorizontalMin;

							if (isPrinting)
							{
								if (changedDirection)
									isPrinting = false;
								// else
								// 	isPrinting = true;
							}
							else
							{
								if (changedDirection)
									isPrinting = true;
								// else
								// 	isPrinting = false;
							}
						}
					}

					if ((!isMinPointOfLastSegment && isMinPointOfCurrSegment) || (isMinPointOfLastSegment && !isMinPointOfCurrSegment))
					{
						// if reached this part then it's a connection point between segments but not a valley or peak
						// should not draw yet as it only has the first point
						continue;
					}
					// if reached this part then it's a peak or a valley, so it should continue and draw that one point
				}
				
				if (isPrinting)
				{
					// draw from last to curr
					for (int x = (int) Math.ceil(last.x()); x <= Math.floor(curr.x()); x++)
						draw(x, currY);
						// rasterPoints.add(new NaturalPoint(x, currY));
					isPrinting = false;
				}
				else
				{
					isPrinting = true;
				}
				last = curr;
			}
		}

		// Add sides
		rasterizeSides(poly);
	}

	public void rasterizeSides(Polygon poly)
	{
		for (LineSegment side : poly.sides())
			rasterize(side);
	}

	public void rasterize(LineSegment segment)
	{
		int x = (int)Math.round(segment.firstPoint().X());
		int y = (int)Math.round(segment.firstPoint().Y());
		int x1 = (int)Math.round(segment.secondPoint().X());
		int y1 = (int)Math.round(segment.secondPoint().Y());

		int dx = Math.abs(x1 - x);
		int dy = Math.abs(y1 - y);
		int len = Math.max(dx, dy) + 1;

		boolean isRight = x1 > x;
		boolean isUp = y1 >= y;
		boolean isCloserToVerticalCenter = dy >= dx;

		if (isRight && isUp && !isCloserToVerticalCenter) // first octave
			rasterizeLine(x, y, dx, dy, len, false, false);
		else if (isRight && isUp && isCloserToVerticalCenter) // second octave
			rasterizeLine(y, x, dy, dx, len, true, false);
		else if (!isRight && isUp && isCloserToVerticalCenter) // third octave
			rasterizeLine(y, x, dy, dx, len, true, true);
		else if (!isRight && isUp && !isCloserToVerticalCenter) // fourth octave
			rasterizeLine(x1, y1, dx, dy, len, false, true);
		else if (!isRight && !isUp && !isCloserToVerticalCenter) // fifth octave
			rasterizeLine(x1, y1, dx, dy, len, false, false);
		else if (!isRight && !isUp && isCloserToVerticalCenter) // sixth octave
			rasterizeLine(y1, x1, dy, dx, len, true, false);
		else if (isRight && !isUp && isCloserToVerticalCenter) // seventh octave
			rasterizeLine(y1, x1, dy, dx, len, true, true);
		else // eith octave
			rasterizeLine(x, y, dx, dy, len, false, true);
	}

	private void rasterizeLine(int x, int y, int dx, int dy, int len, boolean swapAxis, boolean decrementY)
	{
		int P = 2 * dy - dx;

		for (int i = 0; i < len; i++)
		{
			if (swapAxis)
				draw(y, x);
			else
				draw(x, y);

			x++;
			if (P < 0)
				P = P + 2 * dy;
			else
			{
				P = P + 2 * dy - 2 * dx;
				y += decrementY ? -1 : 1;
			}
		}
	}
}
