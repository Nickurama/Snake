package GameEngine;

import Geometry.*;

/**
 * Utility class for creating text overlays.
 * Should be inherited. (or contained)
 * 
 * @author Diogo Fonseca a79858
 * @version 03/05/2024
 * @see TextOverlayOutline
 */
public class TextOverlay extends GameObject implements IOverlay
{
	private BoundingBox bounds;
	private char[][] overlay;

	/**
	 * Initializes a TextOverlay overlay
	 * @param camera the camera to generate the overlay in
	 */
	public TextOverlay(Rectangle camera)
	{
		this.bounds = new BoundingBox(camera);
		reset();
	}

	/**
	 * Resets the overlay, making it a blank canvas
	 */
	public void reset()
	{
		int dx = (int)Math.floor(bounds.maxPoint().X()) - (int)Math.ceil(bounds.minPoint().X()) + 1;
		int dy = (int)Math.floor(bounds.maxPoint().Y()) - (int)Math.ceil(bounds.minPoint().Y()) + 1;
		overlay = new char[dy][dx];
	}

	/**
	 * Sets an outline for the overlay
	 * @param outline the outline for the overlay
	 */
	public void setOutline(TextOverlayOutline outline)
	{
		setOutline(outline.getTopLeft(), outline.getTopRight(), outline.getBottomLeft(), outline.getBottomRight(), outline.getLeftRight(), outline.getTopBottom());
	}

	/**
	 * Sets an outline for the overlay
	 * @param cornerTL the top left corner character
	 * @param cornerTR the top right corner character
	 * @param cornerDL the bottom left corner character
	 * @param cornerDR the bottom right corner character
	 * @param sideLR the left and right sides character
	 * @param sideTD the top and bottom character
	 */
	public void setOutline(char cornerTL, char cornerTR, char cornerDL, char cornerDR, char sideLR, char sideTD)
	{
		setCorners(cornerTL, cornerTR, cornerDL, cornerDR);

		// left side
		for (int i = 1; i < overlay.length - 1; i++)
			overlay[i][0] = sideLR;

		// right side
		for (int i = 1; i < overlay.length - 1; i++)
			overlay[i][overlay[0].length - 1] = sideLR;

		// up
		for (int i = 1; i < overlay[0].length - 1; i++)
			overlay[0][i] = sideTD;

		// down
		for (int i = 1; i < overlay[0].length - 1; i++)
			overlay[overlay.length - 1][i] = sideTD;
	}

	/**
	 * Sets the corners for the outline
	 * @param cornerTL the top left corner character
	 * @param cornerTR the top right corner character
	 * @param cornerDL the bottom left corner character
	 * @param cornerDR the bottom right corner character
	 */
	private void setCorners(char cornerTL, char cornerTR, char cornerDL, char cornerDR)
	{
		overlay[0][0] = cornerTL;
		overlay[0][overlay[0].length - 1] = cornerTR;
		overlay[overlay.length - 1][0]= cornerDL;
		overlay[overlay.length - 1][overlay[0].length - 1] = cornerDR;
	}

	/**
	 * Fills the entire inside of the overlay with a whitespace
	 */
	public void fillEmpty()
	{
		fill(' ');
	}

	/**
	 * Fills the entire inside of the overlay
	 * @param c the character to fill the inside of the overlay with
	 */
	public void fill(char c)
	{
		for (int i = 1; i < overlay.length - 1; i++)
			for (int j = 1; j < overlay[0].length - 1; j++)
				overlay[i][j] = c;
	}

	/**
	 * Checks if an index is valid on the overlay array
	 * @param index the index to check if is valid
	 * @return if the index is valid for the overlay array
	 */
	private boolean isValidIndex(int index)
	{
		return !(index < 0 || index >= overlay.length);
	}

	/**
	 * Writes a text centered on the overlay on the provided index
	 * the 0 index is the top of the overlay
	 * if the index is invalid or if the text is too big, nothing will be written
	 * @param str the text to write
	 * @param index the index to write on (starts on top)
	 */
	public void writeCentered(String str, int index)
	{
		if (!isValidIndex(index))
			return;

		int size = overlay[0].length - 2;
		size -= str.length();
		if (size < 0)
			return;
		int leftSize = size / 2;

		for (int i = 0; i < str.length(); i++)
			overlay[index][1 + leftSize + i] = str.charAt(i);
	}

	/**
	 * Writes a text aligned to the left of the overlay
	 * the 0 index is the top of the overlay
	 * if the index is invalid or if the text is too big, nothing will be written
	 * @param str the text to write
	 * @param index the index to write on (starts on top)
	 */
	public void writeLeft(String str, int index)
	{
		if (!isValidIndex(index))
			return;

		if (str.length() > overlay[0].length - 2)
			return;

		int n = 0;
		for (int i = 1; i < overlay[0].length - 1 && n < str.length(); i++)
			overlay[index][i] = str.charAt(n++);
	}

	/**
	 * Writes a text aligned to the right of the overlay
	 * the 0 index is the top of the overlay
	 * if the index is invalid or if the text is too big, nothing will be written
	 * @param str the text to write
	 * @param index the index to write on (starts on top)
	 */
	public void writeRight(String str, int index)
	{
		if (!isValidIndex(index))
			return;

		int n = 0;
		int startI = overlay[0].length - 1 - str.length();
		if (startI < 1)
			return;
		for (int i = startI; n < str.length(); i++)
			overlay[index][i] = str.charAt(n++);
	}

	/**
	 * Writes a paragraph aligned to the left of the overlay,
	 * possibly spanning multiple lines
	 * the 0 index is the top of the overlay
	 * if the index is invalid nothing will be written
	 * @param str the text to write
	 * @param index the index to start writing on
	 */
	public void writeParagraph(String str, int index)
	{
		if (!isValidIndex(index))
			return;

		int lineSize = overlay[0].length - 2;
		int extra = str.length() % lineSize == 0 ? 0 : 1;
		String[] lines = new String[str.length() / lineSize + extra];
		for (int i = 0; i < lines.length; i++)
			lines[i] = str.substring(i * lineSize, Math.min((i + 1) * lineSize, str.length()));

		for (String line : lines)
		{
			if (index > overlay.length - 2)
				return;
			writeLeft(line, index++);
		}
	}

	@Override
	public char[][] getOverlay()
	{
		return this.overlay;
	}

	/**
	 * The width of the overlay
	 * @return the width of the overlay
	 */
	public int width()
	{
		return this.overlay[0].length;
	}

	/**
	 * The height of the overlay
	 * @return the height of the overlay
	 */
	public int height()
	{
		return this.overlay.length;
	}

	/**
	 * The inner widht of the overlay (excluding the outline)
	 * @return the inner width of the overlay (excluding the outline)
	 */
	public int innerWidth()
	{
		return width() - 2;
	}

	/**
	 * The inner height of the overlay (excluding the outline)
	 * @return the inner height of the overlay (excluding the outline)
	 */
	public int innerHeight()
	{
		return height() - 2;
	}
}
