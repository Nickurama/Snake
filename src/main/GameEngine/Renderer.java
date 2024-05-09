package GameEngine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import Geometry.*;

/**
 * Singleton class responsible for rendering a shape to the screen.
 * The class also renders {@link RenderData RenderData}, which provides the
 * renderer with information as to how to render a shape, such as colour, or
 * if the shape should be filled or not.
 *
 * Colour is completely optional, especially in textual mode, since some terminals
 * don't interpret the ANSI color codes.
 *
 * {@link IOverlay Overlays} are rendered differently, being overlayed on top of the
 * raster after all the rasterization was completed.
 * 
 * @author Diogo Fonseca a79858
 * @version 09/05/2024
 *
 * @inv singleton
 * @see Scene
 * @see RenderData
 * @see IOverlay
 * @see IRenderable
 * @see Geometry.IGeometricShape
 */
public class Renderer
{
	/**
	 * Helper class for the Scanline algorithm, representing an x coordinate and a segment it belongs to
	 * Can be ordered by x value
	*/
	private static class ScanlinePoint implements Comparable<ScanlinePoint>
	{
		private LineSegment segment;
		private double x;

		/**
		 * Initializes a scanline point
		 * @param x the x coordinate
		 * @param segment the segment the x coordinate belongs to
		 */
		public ScanlinePoint(double x, LineSegment segment)
		{
			this.x = x;
			this.segment = segment;
		}

		/**
		 * The x coordinate.
		 * @return the x coordinate
		 */
		public double x() { return this.x; }

		/**
		 * The line segment the x coordinate belongs to.
		 * @return the line segment the x coordinate belongs to.
		 */
		public LineSegment segment() { return this.segment; }

		@Override
		public int compareTo(ScanlinePoint that)
		{
			return MathUtil.areEqual(this.x(), that.x()) ? 0 : (this.x() < that.x() ? -1 : 1);
		}
	}

	private static Renderer instance = null;
	private BoundingBox camera;
	private Colour.Background bgColour;
	private char backgroundChar;
	private char drawChar;
	private Colour.Foreground drawColour;
	private char[][] raster;
	private boolean isFrameUsingColour;
	private String[][] colourRaster;

	/**
	 * Singleton Renderer initialization
	 */
	private Renderer()
	{
		this.bgColour = null;
	}

	/**
	 * Gets the Renderer instance
	 * @return the renderer instance
	 */
	public static Renderer getInstance()
	{
		if (instance == null)
			instance = new Renderer();

		return instance;
	}

	/**
	 * Resets the Renderer instance.
	 */
	public static void resetInstance()
	{
		if (instance != null)
			instance = new Renderer();
	}

	/**
	 * Initializes the Renderer.
	 * If the renderer had already been initialized, the settings are overwritten.
	 * @param camera the location to render
	 * @param backgroundChar the default character to render where there is nothing to render.
	 * @post initialized renderer, ready for rendering
	 */
	public void init(Rectangle camera, char backgroundChar)
	{
		this.isFrameUsingColour = false;
		this.camera = new BoundingBox(camera);
		this.backgroundChar = backgroundChar;
		generateRaster(this.camera);
		generateColourRaster(this.camera);
	}

	/**
	 * Sets the background colour
	 * Can be null (disabling background colour)
	 * @param colour the background colour (can be null)
	 */
	public void setBackgroundColour(Colour.Background colour)
	{
		this.bgColour = colour;
	}

	/**
	 * Generates the empty rendering raster (with the background character)
	 * @param cam the location to render
	 */
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

	/**
	 * Generates the empty colour raster (with the background colour, in case there is one)
	 * @param cam the location to render
	 */
	private void generateColourRaster(BoundingBox cam)
	{
		int dy = (int)Math.floor(cam.maxPoint().Y()) - (int)Math.ceil(cam.minPoint().Y()) + 1;
		int dx = (int)Math.floor(cam.maxPoint().X()) - (int)Math.ceil(cam.minPoint().X()) + 1;

		colourRaster = new String[dy][dx];
		for (int i = 0; i < dy; i++) // iterate over y
			colourRaster[i] = new String[dx];
	}

	/**
	 * Draws the current drawChar to the raster
	 * with the current drawColour (in case there is any)
	 * @param x the x coordinate in space to draw on the raster
	 * @param y the y coordinate in space to draw on the raster
	 */
	private void draw(int x, int y)
	{
		if (x < camera.minPoint().X() || x > camera.maxPoint().X())
			return;
		if (y < camera.minPoint().Y() || y > camera.maxPoint().Y())
			return;

		x = x - (int)Math.ceil(camera.minPoint().X());
		y = (raster.length - 1) - (y - (int)Math.ceil(camera.minPoint().Y()));

		raster[y][x] = drawChar;

		if (drawColour != null)
			drawColour(x, y);
	}

	/**
	 * Draws the current drawColour to the raster
	 * @param x the x coordinate IN THE RASTER to draw on the raster
	 * @param y the y coordinate IN THE RASTER to draw on the raster
	 */
	private void drawColour(int x, int y)
	{
		this.isFrameUsingColour = true;
		int next = x + 1;
		colourRaster[y][x] = drawColour.toString();
		if (next < colourRaster[0].length && colourRaster[y][next] == null)
		{
			colourRaster[y][next] = Colour.RESET.toString();
			if (this.bgColour != null)
				colourRaster[y][next] += bgColour.toString();
		}
		else if (y + 1 < colourRaster.length && colourRaster[y + 1][0] == null)
		{
			colourRaster[y + 1][0] = Colour.RESET.toString();
			if (this.bgColour != null)
				colourRaster[y + 1][0] += bgColour.toString();
		}
	}

	/**
	 * Prints the current raster to the screen
	 * Prints in colour in case any colour has been set
	 */
	private void print()
	{
		if (drawColour != null)
		{
			printColour();
			return;
		}

		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < raster.length; i++) // iterate over y
		{
			if (bgColour != null)
				builder.append(this.bgColour);
			builder.append(new String(raster[i]));
			builder.append('\n');
		}
		if (bgColour != null)
			builder.append(Colour.RESET);
		System.out.print(builder.toString());
	}

	/**
	 * Prints the current raster to the screen with colour
	 */
	private void printColour()
	{
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < raster.length; i++) // iterate over y
		{
			if (bgColour != null)
				builder.append(this.bgColour);
			for (int j = 0; j < raster[0].length; j++) // iterate over x
			{
				if (colourRaster[i][j] != null)
					builder.append(colourRaster[i][j]);
				builder.append(raster[i][j]);
			}
			builder.append('\n');
		}
		if (bgColour != null)
			builder.append(Colour.RESET);
		System.out.print(builder.toString());
	}

	/**
	 * Renders a scene to the screen. (doesn't need initialization)
	 * @param scene the scene to render
	 * @param camera the location in space to render
	 * @param backgroundChar the background character
	 * @throws RuntimeException if the renderer was not initialized correctly
	 */
	public void render(Scene scene, Rectangle camera, char backgroundChar)
	{
		this.init(camera, backgroundChar);
		try
		{
			render(scene);
		}
		catch (GameEngineException e)
		{
			throw new RuntimeException("Should never happen. Renderer was not initialized correctly by this function.");
		}
	}

	/**
	 * Renders the outline of a circle to the screen. (doesn't need initialization)
	 * @param circle the circle to render
	 * @param camera the location in space to render
	 * @param backgroundChar the background character
	 * @param drawChar the character the circle should be rendered with
	 */
	public void renderSides(Circle circle, Rectangle camera, char backgroundChar, char drawChar)
	{
		this.init(camera, backgroundChar);
		try
		{
			renderSides(circle, drawChar);
		}
		catch (GameEngineException e)
		{
			throw new RuntimeException("Should never happen. Renderer was not initialized correctly by this function.");
		}
	}

	/**
	 * Renders a circle to the screen. (doesn't need initialization)
	 * @param circle the circle to render
	 * @param camera the location in space to render
	 * @param backgroundChar the background character
	 * @param drawChar the character the circle should be rendered with
	 */
	public void render(Circle circle, Rectangle camera, char backgroundChar, char drawChar)
	{
		this.init(camera, backgroundChar);
		try
		{
			render(circle, drawChar);
		}
		catch (GameEngineException e)
		{
			throw new RuntimeException("Should never happen. Renderer was not initialized correctly by this function.");
		}
	}

	/**
	 * Renders a polygon to the screen. (doesn't need initialization)
	 * @param poly the polygon to render
	 * @param camera the location in space to render
	 * @param backgroundChar the background character
	 * @param drawChar the character to render the polygon with
	 */
	public void render(Polygon poly, Rectangle camera, char backgroundChar, char drawChar)
	{
		this.init(camera, backgroundChar);
		try
		{
			render(poly, drawChar);
		}
		catch (GameEngineException e)
		{
			throw new RuntimeException("Should never happen. Renderer was not initialized correctly by this function.");
		}
	}

	/**
	 * Renders the outline of a polygon to the screen. (doesn't need initialization)
	 * @param poly the polygon to render
	 * @param camera the location in space to render
	 * @param backgroundChar the background character
	 * @param drawChar the character to render the polygon with
	 */
	public void renderSides(Polygon poly, Rectangle camera, char backgroundChar, char drawChar)
	{
		this.init(camera, backgroundChar);
		try
		{
			renderSides(poly, drawChar);
		}
		catch (GameEngineException e)
		{
			throw new RuntimeException("Should never happen. Renderer was not initialized correctly by this function.");
		}
	}

	/**
	 * Renders a segment to the screen. (doesn't need initialization)
	 * @param segment the segment to render
	 * @param camera the location in space to render
	 * @param backgroundChar the background character
	 * @param drawChar if character to render the segment with
	 */
	public void render(LineSegment segment, Rectangle camera, char backgroundChar, char drawChar)
	{
		this.init(camera, backgroundChar);
		try
		{
			render(segment, drawChar);
		}
		catch (GameEngineException e)
		{
			throw new RuntimeException("Should never happen. Renderer was not initialized correctly by this function.");
		}
	}

	/**
	 * Renders a scene to the screen.
	 * @param scene the scene to render
	 * @pre must initialize renderer first
	 * @throws GameEngineException if the renderer was not initialized properly
	 */
	public void render(Scene scene) throws GameEngineException
	{
		validateRender();
		rasterize(scene.renderablesArr());
		if (scene.getOverlay() != null)
			rasterize(scene.getOverlay());
		print();
	}

	/**
	 * Renders a circle to the screen.
	 * @param circle the circle to render
	 * @param drawChar the character to render the circle with
	 * @pre must initialize renderer first
	 * @throws GameEngineException if the renderer was not initialized properly
	 */
	public void render(Circle circle, char drawChar) throws GameEngineException
	{
		render(circle, drawChar, null);
	}

	/**
	 * Renders a circle to the screen with colour.
	 * @param circle the circle to render
	 * @param drawChar the character to render the circle with
	 * @param colour the colour to render the circle with (can be null)
	 * @pre must initialize renderer first
	 * @throws GameEngineException if the renderer was not initialized properly
	 */
	public void render(Circle circle, char drawChar, Colour.Foreground colour) throws GameEngineException
	{
		validateRender();
		this.drawChar = drawChar;
		this.drawColour = colour;
		rasterize(circle);
		print();
	}

	/**
	 * Renders the outline of a circle to the screen.
	 * @param circle the circle to render
	 * @param drawChar the character to render the circle with
	 * @pre must initialize renderer first
	 * @throws GameEngineException if the renderer was not initialized properly
	 */
	public void renderSides(Circle circle, char drawChar) throws GameEngineException
	{
		renderSides(circle, drawChar, null);
	}

	/**
	 * Renders the outline of a circle to the screen with colour.
	 * @param circle the circle to render
	 * @param drawChar the character to render the circle with
	 * @param colour the colour to draw the circle with (can be null)
	 * @pre must initialize renderer first
	 * @throws GameEngineException if the renderer was not initialized properly
	 */
	public void renderSides(Circle circle, char drawChar, Colour.Foreground colour) throws GameEngineException
	{
		validateRender();
		this.drawChar = drawChar;
		this.drawColour = colour;
		rasterizeSides(circle);
		print();
	}

	/**
	 * Renders a polygon to the screen.
	 * @param poly the polygon to render
	 * @param drawChar the character to render the polygon with
	 * @pre must initialize renderer first
	 * @throws GameEngineException if the renderer was not initialized properly
	 */
	public void render(Polygon poly, char drawChar) throws GameEngineException
	{
		render(poly, drawChar, null);
	}

	/**
	 * Renders a polygon to the screen with colour.
	 * @param poly the polygon to render
	 * @param drawChar the character to render the polygon with
	 * @param colour the colour to draw the polygon with (can be null)
	 * @pre must initialize renderer first
	 * @throws GameEngineException if the renderer was not initialized properly
	 */
	public void render(Polygon poly, char drawChar, Colour.Foreground colour) throws GameEngineException
	{
		validateRender();
		this.drawChar = drawChar;
		this.drawColour = colour;
		rasterize(poly);
		print();
	}

	/**
	 * Renders the outline of a polygon to the screen.
	 * @param poly the polygon to render
	 * @param drawChar the character to render the polygon with
	 * @pre must initialize renderer first
	 * @throws GameEngineException if the renderer was not initialized properly
	 */
	public void renderSides(Polygon poly, char drawChar) throws GameEngineException
	{
		renderSides(poly, drawChar, null);
	}

	/**
	 * Renders the outline of a polygon to the screen with colour.
	 * @param poly the polygon to render
	 * @param drawChar the character to render the polygon with
	 * @param colour the colour to render the polygon with (can be null)
	 * @pre must initialize renderer first
	 * @throws GameEngineException if the renderer was not initialized properly
	 */
	public void renderSides(Polygon poly, char drawChar, Colour.Foreground colour) throws GameEngineException
	{
		validateRender();
		this.drawChar = drawChar;
		this.drawColour = colour;
		rasterizeSides(poly);
		print();
	}

	/**
	 * Renderers a segment to the screen.
	 * @param segment the segment to render
	 * @param drawChar the character to render the segment with
	 * @pre must initialize renderer first
	 * @throws GameEngineException if the renderer was not initialized properly
	 */
	public void render(LineSegment segment, char drawChar) throws GameEngineException
	{
		render(segment, drawChar, null);
	}

	/**
	 * Renders a segment to the screen with colour.
	 * @param segment the segment to render
	 * @param drawChar the character to render the segment with
	 * @param colour the colour to render the segment with
	 * @pre must initialize renderer first
	 * @throws GameEngineException if the renderer was not initialized properly
	 */
	public void render(LineSegment segment, char drawChar, Colour.Foreground colour) throws GameEngineException
	{
		validateRender();
		this.drawChar = drawChar;
		this.drawColour = colour;
		rasterize(segment);
		print();
	}

	/**
	 * Checks if the renderer was properly initialized
	 * @throws GameEngineException if the renderer was not initialized properly
	 */
	private void validateRender() throws GameEngineException
	{
		if (this.camera == null)
			throw new GameEngineException("Must initialize renderer or pass all arguments.");
	}

	/**
	 * Rasterizes an overlay, being overlayed on top of the raster
	 * @param overlay the overlay to rasterize
	 */
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
				if (isFrameUsingColour)
				{
					colourRaster[i][j] = Colour.RESET.toString();
					if (this.bgColour != null)
						colourRaster[i][j] += this.bgColour.toString();
				}
			}
		}
	}

	/**
	 * Rasterizes an array of RenderData to the raster
	 * @param renderDataArr the array of RenderData to rasterize
	 */
	private void rasterize(RenderData<?>[] renderDataArr)
	{
		Arrays.sort(renderDataArr);

		for (RenderData<?> rData : renderDataArr)
		{
			IGeometricShape<?> shape = rData.getShape();
			this.drawColour = rData.getColour();
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

	/**
	 * Rasterizes a circle to the raster.
	 * @param circle the circle to rasterize
	 */
	private void rasterize(Circle circle)
	{
		rasterize(circle, true);
	}

	/**
	 * Rasterizes the outline of a circle to the raster.
	 * @param circle the circle to rasterize
	 */
	private void rasterizeSides(Circle circle)
	{
		rasterize(circle, false);
	}

	/**
	 * Rasterizes a circle to the raster.
	 * Uses Bresenham's circle algorithm
	 * @param circle the circle to rasterize
	 * @param fill if the circle should be filled
	 */
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

	/**
	 * Draws a coordinate of a circle to the raster across all 8 octants
	 * Uses Bresenham's circle algorithm
	 * @param xCircle the x of the center of the circle
	 * @param yCircle the y of the center of the circle
	 * @param x the x to draw in the first octant
	 * @param y the y to draw in the first octant
	 * @param fill if the circle should be filled
	 * @pre x should be in the first octant of the circle
	 * @pre y should be in the first octant of the circle
	 */
	private void drawCircle(int xCircle, int yCircle, int x, int y, boolean fill)
	{
		if (fill)
			drawCircleFill(xCircle, yCircle, x, y);
		else
			drawCircle(xCircle, yCircle, x, y);
	}

	/**
	 * Draws a line of a circle to the raster across all 8 octants
	 * Uses Bresenham's circle algorithm
	 * @param xCircle the x of the center of the circle
	 * @param yCircle the y of the center of the circle
	 * @param x the x to draw in the first octant
	 * @param y the y to draw in the first octant
	 * @pre x should be in the first octant of the circle
	 * @pre y should be in the first octant of the circle
	 */
	private void drawCircleFill(int xCircle, int yCircle, int x, int y)
	{
		drawHorizontalLine(xCircle - x, xCircle + x, yCircle + y);
		drawHorizontalLine(xCircle - x, xCircle + x, yCircle - y);
		drawHorizontalLine(xCircle - y, xCircle + y, yCircle + x);
		drawHorizontalLine(xCircle - y, xCircle + y, yCircle - x);
	}

	/**
	 * Draws a coordinate of a circle to the raster across all 8 octants
	 * Uses Bresenham's circle algorithm
	 * @param xCircle the x of the center of the circle
	 * @param yCircle the y of the center of the circle
	 * @param x the x to draw in the first octant
	 * @param y the y to draw in the first octant
	 * @pre x should be in the first octant of the circle
	 * @pre y should be in the first octant of the circle
	 */
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

	/**
	 * Rasterizes a polygon to the raster
	 * Uses the scanline algorithm
	 * @param poly the polygon to raster
	 */
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

	/**
	 * Executes the scanline algorithm for one scanline
	 * Uses the scanline algorithm
	 * @param poly the polygon to rasterize
	 * @param currY the current y that should be rasterized
	 */
	private void scanLine(Polygon poly, int currY)
	{
		// generate current scanline
		Line scanLine = getScanLine(currY);

		// calculate all intersections
		ArrayList<ScanlinePoint> intersections = getAllScanIntersections(poly, scanLine, currY);

		// sort intersections
		Collections.sort(intersections);

		// add all raster points to list
		drawScanlinePoints(intersections, scanLine, currY);
	}

	/**
	 * Generates a line for the scanline algorithm
	 * Uses the scanline algorithm
	 * @param currY the y value the scanline should be generated in
	 * @return a scan line for the scanline algorithm in the current y
	 */
	private Line getScanLine(int currY)
	{
		try
		{
			return new Line(new Point(0.0, currY), new Point(1.0, currY));
		}
		catch (GeometricException e)
		{
			throw new RuntimeException("Should not happen, the scanline should always be valid.");
		}
	}

	/**
	 * Gets all the intersections of a scanline with a polygon
	 * Uses the scanline algorithm
	 * @param poly the polygon to get the intersections from
	 * @param scanLine the current scanline
	 * @param currY the current y
	 * @return the scanlinePoint intersections found
	 */
	private ArrayList<ScanlinePoint> getAllScanIntersections(Polygon poly, Line scanLine, int currY)
	{
		ArrayList<ScanlinePoint> intersections = new ArrayList<ScanlinePoint>();
		for (LineSegment segment : poly.sides())
		{
			if (segment.line().isParalel(scanLine) && MathUtil.areEqual(segment.firstPoint().Y(), currY))
			{
				intersections.add(new ScanlinePoint(segment.firstPoint().X(), segment));
				intersections.add(new ScanlinePoint(segment.secondPoint().X(), segment));
			}
			else if (segment.intersectsInclusive(scanLine))
			{
				double x = segment.line().intersection(scanLine).X();
				intersections.add(new ScanlinePoint(x, segment));
			}
		}
		return intersections;
	}

	/**
	 * Draws to the raster the polygon at a given Y value
	 * Uses the scanline algorithm
	 * @param intersections the intersections between the polygon and the scanline
	 * @param scanLine the current scanline
	 * @param currY the current Y value to rasterize
	 */
	private void drawScanlinePoints(ArrayList<ScanlinePoint> intersections, Line scanLine, int currY)
	{
		if (intersections.size() == 0)
			return;

		ScanlinePoint last = intersections.getFirst();
		boolean isPrinting = true;
		boolean isHorizontal = false;
		boolean lastHorizontalMin = false;
		for (int i = 1; i < intersections.size(); i++)
		{
			ScanlinePoint curr = intersections.get(i);

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

	/**
	 * Gets the minimum point of a segment on a ScanlinePoint
	 * @param minPoint the ScanlinePoint to get the minimum point from
	 * @return the minimum point of a segment on a ScanlinePoint
	 */
	private VirtualPoint getMinPoint(ScanlinePoint minPoint)
	{
		return minPoint.segment().firstPoint().Y() < minPoint.segment().secondPoint().Y() ? minPoint.segment().firstPoint() : minPoint.segment().secondPoint();

	}

	/**
	 * Draws a horizontal line to the raster from a x coordinate up to
	 * another x coordinate, on the given y value
	 * @param xFrom the x coordinate to draw the line from
	 * @param xTo the x coordinate to draw the line to
	 * @param y the y coordinate the line should be drawn on
	 */
	private void drawHorizontalLine(int xFrom, int xTo, int y)
	{
		for (int i = xFrom; i <= xTo; i++)
			draw(i, y);
	}

	/**
	 * Rasterizes the sides of a polygon to the raster
	 * Uses Bresenham's line algorithm
	 * @param poly the polygon to rasterize
	 */
	private void rasterizeSides(Polygon poly)
	{
		for (LineSegment side : poly.sides())
			rasterize(side);
	}

	/**
	 * Rasterizes a segment to the raster
	 * Uses Bresenham's line algorithm
	 * @param segment the segment to rasterize
	 */
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

		if (isRight && isUp && !isCloserToVerticalCenter) // first octant
			rasterizeLine(x, y, dx, dy, len, false, false);
		else if (isRight && isUp && isCloserToVerticalCenter) // second octant
			rasterizeLine(y, x, dy, dx, len, true, false);
		else if (!isRight && isUp && isCloserToVerticalCenter) // third octant
			rasterizeLine(y, x, dy, dx, len, true, true);
		else if (!isRight && isUp && !isCloserToVerticalCenter) // fourth octant
			rasterizeLine(x1, y1, dx, dy, len, false, true);
		else if (!isRight && !isUp && !isCloserToVerticalCenter) // fifth octant
			rasterizeLine(x1, y1, dx, dy, len, false, false);
		else if (!isRight && !isUp && isCloserToVerticalCenter) // sixth octant
			rasterizeLine(y1, x1, dy, dx, len, true, false);
		else if (isRight && !isUp && isCloserToVerticalCenter) // seventh octant
			rasterizeLine(y1, x1, dy, dx, len, true, true);
		else // eith octant
			rasterizeLine(x, y, dx, dy, len, false, true);
	}

	/**
	 * Rasterizes a line segment to the raster in the first octant
	 * Bresenham's algorithm
	 * @param x the starting x coordinate
	 * @param y the starting y coordinate
	 * @param dx the difference in x coordinates to destiny x coordinate
	 * @param dy the difference in y coordinates to destiny y coordinate
	 * @param len the projected length of the line
	 * @param swapAxis if the x and y axis should be swapped (flag for other octants)
	 * @param decrementY if the y value should be decremented instead of incremented (flag for other octants)
	 */
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
