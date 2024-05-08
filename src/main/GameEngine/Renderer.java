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
		if (scene.getOverlay() != null)
			rasterize(scene.getOverlay());
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

	private void rasterize(IOverlay overlay)
	{
		char[][] overlayRaster = overlay.getOverlay();
		for (int i = 0; i < raster.length; i++)
		{
			for (int j = 0; j < raster[0].length; j++)
			{
				if (overlayRaster[i][j] == '\0')
					continue;
				raster[i][j] = overlayRaster[i][j];
			}
		}
	}

	private void rasterize(RenderData<?>[] renderDataArr)
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

	private void rasterize(Circle circle)
	{
		rasterize(circle, true);
	}

	private void rasterizeSides(Circle circle)
	{
		rasterize(circle, false);
	}

	private void rasterize(Circle circle, boolean fill)
	{
		int r = (int)Math.round(circle.radius());
		int xCircle = (int)Math.round(circle.getCentroid().X());
		int yCircle = (int)Math.round(circle.getCentroid().Y());

		int x = 0;
		int y = r;
		int d = 3 - (2 * r);
		drawCircle(xCircle, yCircle, x, y, fill);
		while (x <= y)
		{
			x++;

			if (d > 0)
			{
				y--;
				d = d + 4 * (x - y) + 10;
			}
			else
			{
				d = d + (4 * x) + 6;
			}
			drawCircle(xCircle, yCircle, x, y, fill);
		}
	}

	private void drawCircle(int xCircle, int yCircle, int x, int y, boolean fill)
	{
		if (fill)
			drawCircleFill(xCircle, yCircle, x, y);
		else
			drawCircle(xCircle, yCircle, x, y);
	}

	private void drawCircleFill(int xCircle, int yCircle, int x, int y)
	{
		drawHorizontalLine(xCircle - x, xCircle + x, yCircle + y);
		drawHorizontalLine(xCircle - x, xCircle + x, yCircle - y);
		drawHorizontalLine(xCircle - y, xCircle + y, yCircle + x);
		drawHorizontalLine(xCircle - y, xCircle + y, yCircle - x);
	}

	private void drawCircle(int xCircle, int yCircle, int x, int y)
	{
		draw(xCircle + x, yCircle + y);
		draw(xCircle - x, yCircle + y);
		draw(xCircle + x, yCircle - y);
		draw(xCircle - x, yCircle - y);
		draw(xCircle + y, yCircle + x);
		draw(xCircle - y, yCircle + x);
		draw(xCircle + y, yCircle - x);
		draw(xCircle - y, yCircle - x);
	}

	private void rasterize(Polygon poly)
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
			scanLine(poly, currY);

		// Add sides
		rasterizeSides(poly);
	}

	private void scanLine(Polygon poly, int currY)
	{
		// generate current scanline
		Line scanLine = getScanLine(currY);

		// calculate all intersections
		ArrayList<RasterPoint> intersections = getAllScanIntersections(poly, scanLine, currY);

		// sort intersections
		Collections.sort(intersections, (rp0, rp1) -> MathUtil.areEqual(rp0.x(), rp1.x()) ? 0 : (rp0.x() < rp1.x() ? -1 : 1));

		// add all raster points to list
		drawScanlinePoints(intersections, scanLine, currY);
	}

	private Line getScanLine(int currY)
	{
		try
		{
			return new Line(new Point(0.0, currY), new Point(1.0, currY));
		}
		catch (GeometricException e)
		{
			throw new Error("Should not happen, the scanline should always be valid.");
		}
	}

	private ArrayList<RasterPoint> getAllScanIntersections(Polygon poly, Line scanLine, int currY)
	{
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
		return intersections;
	}

	private void drawScanlinePoints(ArrayList<RasterPoint> intersections, Line scanLine, int currY)
	{
		if (intersections.size() == 0)
			return;

		RasterPoint last = intersections.getFirst();
		boolean isPrinting = true;
		boolean isHorizontal = false;
		boolean lastHorizontalMin = false;
		for (int i = 1; i < intersections.size(); i++)
		{
			RasterPoint curr = intersections.get(i);

			if (MathUtil.areEqual(last.x(), curr.x()))
			{
				VirtualPoint intersection = new VirtualPoint(curr.x(), currY);
				boolean isMinPointOfLastSegment = intersection.equals(getMinPoint(last));
				boolean isMinPointOfCurrSegment = intersection.equals(getMinPoint(curr));
				if (scanLine.isParalel(last.segment().line()) || scanLine.isParalel(curr.segment().line()))
				{
					boolean currHorizontalMin = isMinPointOfLastSegment && isMinPointOfCurrSegment;
					if (!isHorizontal)
					{
						isHorizontal = true;
						lastHorizontalMin = currHorizontalMin;
						continue;
					}
					else
					{
						isHorizontal = false;
						if (currHorizontalMin == lastHorizontalMin)
							isPrinting = !isPrinting;
					}
				}

				if (isMinPointOfLastSegment != isMinPointOfCurrSegment)
					continue;
			}
			
			if (isPrinting)
				drawHorizontalLine((int) Math.ceil(last.x()), (int)Math.floor(curr.x()), currY);
			isPrinting = !isPrinting;
			last = curr;
		}
	}

	private VirtualPoint getMinPoint(RasterPoint minPoint)
	{
		return minPoint.segment().firstPoint().Y() < minPoint.segment().secondPoint().Y() ? minPoint.segment().firstPoint() : minPoint.segment().secondPoint();

	}

	private void drawHorizontalLine(int xFrom, int xTo, int y)
	{
		for (int i = xFrom; i <= xTo; i++)
			draw(i, y);
	}

	private void rasterizeSides(Polygon poly)
	{
		for (LineSegment side : poly.sides())
			rasterize(side);
	}

	private void rasterize(LineSegment segment)
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
