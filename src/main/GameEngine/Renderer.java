package GameEngine;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import javax.swing.JPanel;

import Geometry.*;

/**
 * Singleton class responsible for rendering a shape to the screen.
 * The class also renders {@link RenderData RenderData}, which provides the
 * renderer with information as to how to render a shape, such as colour, or
 * if the shape should be filled or not.
 *
 * TerminalColour is the colour for when rendering with the terminal, in a textual manner.
 * It's completely optional, since some terminals don't interpret the ANSI color codes.
 *
 * Color is the color for when rendering graphically. It's also optional, having a default color
 * for when a color is null.
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
 * @see GraphicWindow
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

	private static final String DEFAULT_WINDOW_TITLE = "GameEngine Graphical Window";
	private static final TerminalColour.Background DEFAULT_TEXTUAL_BACKGROUND_COLOUR = null;
	private static final Color DEFAULT_GRAPHICAL_COLOR = Color.black;
	private static final boolean DEFAULT_IS_TEXTUAL = true;

	private static Renderer instance = null;
	private BoundingBox camera;
	private TerminalColour.Background terminalBgColour;
	private char backgroundChar;
	private Color graphicalBgColor;
	private char drawChar;
	private Color graphicalDrawColor;
	private TerminalColour.Foreground terminalDrawColour;
	private char[][] raster;
	private boolean isFrameUsingColour;
	private String[][] colourRaster;
	private boolean isTextual;
	private GraphicWindow graphicalRaster;
	private String graphicalWindowTitle;

	private int width;
	private int height;

	/**
	 * Singleton Renderer initialization
	 */
	private Renderer()
	{
		this.graphicalWindowTitle = DEFAULT_WINDOW_TITLE;
		this.terminalBgColour = DEFAULT_TEXTUAL_BACKGROUND_COLOUR;
		this.graphicalBgColor = DEFAULT_GRAPHICAL_COLOR;
		this.isTextual = DEFAULT_IS_TEXTUAL;
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
		if (instance == null)
			return;

		instance.closeGraphicWindow();
		instance = new Renderer();
	}

	/**
	 * Sets if the renderer should render as text to
	 * the terminal or to a GUI
	 * @param value if the renderer should render as text
	 */
	public void setTextual(boolean value)
	{
		this.isTextual = value;
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
		this.height = (int)Math.floor(this.camera.maxPoint().Y()) - (int)Math.ceil(this.camera.minPoint().Y()) + 1;
		this.width = (int)Math.floor(this.camera.maxPoint().X()) - (int)Math.ceil(this.camera.minPoint().X()) + 1;

		if (this.isTextual)
			initTextual(backgroundChar);
		else
			initGraphical(this.graphicalBgColor);
	}

	/**
	 * Initializes the renderer for renderization in the terminal.
	 * @param backgroundChar the character that represents empty space
	 */
	private void initTextual(char backgroundChar)
	{
		this.backgroundChar = backgroundChar;
		generateRaster();
		generateColourRaster();
	}

	/**
	 * Initializes the renderer for graphical renderization
	 * @param bgColor the color that represents empty space
	 */
	private void initGraphical(Color bgColor)
	{
		if (this.graphicalRaster == null)
		{
			this.graphicalRaster = new GraphicWindow(this.width, this.height, this.graphicalWindowTitle, bgColor);
			return;
		}

		this.graphicalRaster.reset();
	}

	/**
	 * Sets the background colour
	 * Can be null (disabling background colour)
	 * @param colour the background colour (can be null)
	 */
	public void setTerminalBackgroundColour(TerminalColour.Background colour)
	{
		this.terminalBgColour = colour;
	}

	/**
	 * Sets the graphical background color.
	 * @param color the color to set the graphical background to.
	 */
	public void setGraphicalBackgroundColor(Color color)
	{
		this.graphicalBgColor = color;
	}

	/**
	 * Sets the graphical window title.
	 * @param title the tile of the graphical window.
	 */
	public void setGraphicalWindowTitle(String title)
	{
		this.graphicalWindowTitle = title;
	}

	/**
	 * Generates the empty rendering raster (with the background character)
	 * @param cam the location to render
	 */
	private void generateRaster()
	{
		raster = new char[this.height][this.width];
		for (int i = 0; i < this.height; i++) // iterate over y
		{
			raster[i] = new char[this.width];
			for (int j = 0; j < this.width; j++) // iterate over x
				raster[i][j] = backgroundChar;
		}
	}

	/**
	 * Generates the empty colour raster (with the background colour, in case there is one)
	 * @param cam the location to render
	 */
	private void generateColourRaster()
	{
		colourRaster = new String[this.height][this.width];
		for (int i = 0; i < this.height; i++) // iterate over y
			colourRaster[i] = new String[this.width];
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
		// y = (raster.length - 1) - (y - (int)Math.ceil(camera.minPoint().Y()));
		y = (this.height - 1) - (y - (int)Math.ceil(camera.minPoint().Y()));

		if (this.isTextual)
			drawTextual(x, y);
		else
			drawGraphical(x, y);
	}

	/**
	 * Draws on the graphical raster
	 * @param x the x coordinate to draw on
	 * @param y the y coordinate to draw on
	 */
	private void drawGraphical(int x, int y)
	{
		if (this.graphicalDrawColor == null)
			this.graphicalDrawColor = DEFAULT_GRAPHICAL_COLOR;
		this.graphicalRaster.draw(x, y, this.graphicalDrawColor);
	}

	/**
	 * Draws on the textual raster
	 * @param x the x coordinate to draw on
	 * @param y the y coordinate to draw on
	 */
	private void drawTextual(int x, int y)
	{
		raster[y][x] = drawChar;

		if (terminalDrawColour != null)
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
		colourRaster[y][x] = terminalDrawColour.toString();
		if (next < colourRaster[0].length && colourRaster[y][next] == null)
		{
			colourRaster[y][next] = TerminalColour.RESET.toString();
			if (this.terminalBgColour != null)
				colourRaster[y][next] += terminalBgColour.toString();
		}
		else if (y + 1 < colourRaster.length && colourRaster[y + 1][0] == null)
		{
			colourRaster[y + 1][0] = TerminalColour.RESET.toString();
			if (this.terminalBgColour != null)
				colourRaster[y + 1][0] += terminalBgColour.toString();
		}
	}

	/**
	 * Prints the current raster to the screen
	 * Prints in colour in case any colour has been set
	 */
	private void print()
	{
		if (isTextual)
			printTextual();
		else
			printGraphical();
	}

	/**
	 * Prints the raster to the graphical window
	 */
	private void printGraphical()
	{
		this.graphicalRaster.repaint();
	}

	/**
	 * Prints the raster to the terminal
	 */
	private void printTextual()
	{
		if (this.isFrameUsingColour)
			printColour();
		else
			printNoColor();
	}

	/**
	 * Prints the raster to the terminal without color
	 */
	private void printNoColor()
	{
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < raster.length; i++) // iterate over y
		{
			if (this.terminalBgColour != null)
				builder.append(this.terminalBgColour);
			builder.append(new String(raster[i]));
			builder.append('\n');
		}
		if (this.terminalBgColour != null)
			builder.append(TerminalColour.RESET);
		System.out.print(builder.toString());
	}

	/**
	 * Prints the current raster to the terminal with colour
	 */
	private void printColour()
	{
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < raster.length; i++) // iterate over y
		{
			if (this.terminalBgColour != null)
				builder.append(this.terminalBgColour);
			for (int j = 0; j < raster[0].length; j++) // iterate over x
			{
				if (colourRaster[i][j] != null)
					builder.append(colourRaster[i][j]);
				builder.append(raster[i][j]);
			}
			builder.append('\n');
		}
		if (this.terminalBgColour != null)
			builder.append(TerminalColour.RESET);
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
		render(circle, drawChar, null, null);
	}

	/**
	 * Renders a circle to the screen with colour.
	 * @param circle the circle to render
	 * @param drawChar the character to render the circle with
	 * @param terminalColour the colour to render the circle with in the terminal (can be null)
	 * @param graphicalColor the colour to render the circle with in the graphical interface (can be null)
	 * @pre must initialize renderer first
	 * @throws GameEngineException if the renderer was not initialized properly
	 */
	public void render(Circle circle, char drawChar, TerminalColour.Foreground terminalColour, Color graphicalColor) throws GameEngineException
	{
		validateRender();
		this.drawChar = drawChar;
		this.terminalDrawColour = terminalColour;
		this.graphicalDrawColor = graphicalColor;
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
		renderSides(circle, drawChar, null, null);
	}

	/**
	 * Renders the outline of a circle to the screen with colour.
	 * @param circle the circle to render
	 * @param drawChar the character to render the circle with
	 * @param terminalColour the colour to draw the circle with in the terminal (can be null)
	 * @param graphicalColor the colour to draw the circle with in the graphical interface (can be null)
	 * @pre must initialize renderer first
	 * @throws GameEngineException if the renderer was not initialized properly
	 */
	public void renderSides(Circle circle, char drawChar, TerminalColour.Foreground terminalColour, Color graphicalColor) throws GameEngineException
	{
		validateRender();
		this.drawChar = drawChar;
		this.terminalDrawColour = terminalColour;
		this.graphicalDrawColor = graphicalColor;
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
		render(poly, drawChar, null, null);
	}

	/**
	 * Renders a polygon to the screen with colour.
	 * @param poly the polygon to render
	 * @param drawChar the character to render the polygon with
	 * @param terminalColour the colour to draw the polygon with in the terminal (can be null)
	 * @param graphicalColor the colour to draw the polygon with in the graphical interface (can be null)
	 * @pre must initialize renderer first
	 * @throws GameEngineException if the renderer was not initialized properly
	 */
	public void render(Polygon poly, char drawChar, TerminalColour.Foreground terminalColour, Color graphicalColor) throws GameEngineException
	{
		validateRender();
		this.drawChar = drawChar;
		this.terminalDrawColour = terminalColour;
		this.graphicalDrawColor = graphicalColor;
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
		renderSides(poly, drawChar, null, null);
	}

	/**
	 * Renders the outline of a polygon to the screen with colour.
	 * @param poly the polygon to render
	 * @param drawChar the character to render the polygon with
	 * @param terminalColour the colour to render the polygon with in the terminal (can be null)
	 * @param graphicalColor the colour to render the polygon with in the graphical interface (can be null)
	 * @pre must initialize renderer first
	 * @throws GameEngineException if the renderer was not initialized properly
	 */
	public void renderSides(Polygon poly, char drawChar, TerminalColour.Foreground terminalColour, Color graphicalColor) throws GameEngineException
	{
		validateRender();
		this.drawChar = drawChar;
		this.terminalDrawColour = terminalColour;
		this.graphicalDrawColor = graphicalColor;
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
		render(segment, drawChar, null, null);
	}

	/**
	 * Renders a segment to the screen with colour.
	 * @param segment the segment to render
	 * @param drawChar the character to render the segment with
	 * @param terminalColour the colour to render the segment with in the terminal
	 * @param graphicalColor the colour to render the segment with in the graphical interface
	 * @pre must initialize renderer first
	 * @throws GameEngineException if the renderer was not initialized properly
	 */
	public void render(LineSegment segment, char drawChar, TerminalColour.Foreground terminalColour, Color graphicalColor) throws GameEngineException
	{
		validateRender();
		this.drawChar = drawChar;
		this.terminalDrawColour = terminalColour;
		this.graphicalDrawColor = graphicalColor;
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
		if (this.isTextual)
			rasterizeTextualOverlay(overlay);
		else
			rasterizeGraphicalOverlay(overlay);
	}

	/**
	 * Rasterizes a textual overlay
	 * @param overlay the overlay to rasterize
	 */
	private void rasterizeTextualOverlay(IOverlay overlay)
	{
		char[][] overlayRaster = overlay.getOverlay();
		if (overlayRaster == null)
		{
			Logger.log(Logger.Level.FATAL, "Tried to rasterize a graphic-only overlay as textual! raster was null.");
			throw new RuntimeException("Tried to rasterize a graphic-only overlay as textual! raster was null.");
		}

		for (int i = 0; i < raster.length; i++)
		{
			for (int j = 0; j < raster[0].length; j++)
			{
				if (overlayRaster[i][j] == '\0')
					continue;
				raster[i][j] = overlayRaster[i][j];
				if (isFrameUsingColour)
				{
					colourRaster[i][j] = TerminalColour.RESET.toString();
					if (this.terminalBgColour != null)
						colourRaster[i][j] += this.terminalBgColour.toString();
				}
			}
		}
	}

	/**
	 * Rasterizes a graphical overlay
	 * @param overlay the overlay to rasterize
	 * @throws RuntimeException if the overlay was null
	 */
	private void rasterizeGraphicalOverlay(IOverlay overlay)
	{
		JPanel overlayPanel = overlay.getPanel();
		if (overlayPanel == null)
		{
			Logger.log(Logger.Level.FATAL, "Tried to rasterize a text-only overlay as graphical! panel was null.");
			throw new RuntimeException("Tried to rasterize a text-only overlay as graphical! panel was null.");
		}

		this.graphicalRaster.setOverlay(overlayPanel);
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
			this.terminalDrawColour = rData.getTerminalColour();
			this.graphicalDrawColor = rData.getGraphicalColor();
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

	/**
	 * closes the graphical window
	 */
	protected void closeGraphicWindow()
	{
		if (this.graphicalRaster == null)
			return;
		this.graphicalRaster.close();
	}

	/**
	 * Gets the graphical window.
	 * Returns null when there is no window (if it's in textual mode).
	 * @return the graphical window (may be null)
	 */
	public GraphicWindow getGraphicWindow() { return this.graphicalRaster; }
}
