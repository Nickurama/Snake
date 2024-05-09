package GameEngine;

/**
 * Utility class for creating outlines for a textual overlay
 * 
 * @author Diogo Fonseca a79858
 * @version 06/05/2024
 * @see TextOverlay
 */
public class TextOverlayOutline
{
	private static final char DEFAULT_TOP_LEFT = '╔';
	private static final char DEFAULT_TOP_RIGHT = '╗';
	private static final char DEFAULT_BOTTOM_LEFT = '╚';
	private static final char DEFAULT_BOTTOM_RIGHT = '╝';
	private static final char DEFAULT_LEFT_RIGHT = '║';
	private static final char DEFAULT_TOP_BOTTOM = '═';
	private char topLeft;
	private char topRight;
	private char bottomLeft;
	private char bottomRight;
	private char leftRight;
	private char topBottom;

	/**
	 * Initializes a TextOverlayOutline
	 * @param topLeft the top left corner character
	 * @param topRight thge top right corner character
	 * @param bottomLeft the bottom left corner character
	 * @param bottomRight the bottom right corner character
	 * @param leftRight the left and right sides character
	 * @param topBottom the top and bottom character
	 */
	public TextOverlayOutline(char topLeft, char topRight, char bottomLeft, char bottomRight, char leftRight, char topBottom)
	{
		this.topLeft = topLeft;
		this.topRight = topRight;
		this.bottomLeft = bottomLeft;
		this.bottomRight = bottomRight;
		this.leftRight = leftRight;
		this.topBottom = topBottom;
	}

	/**
	 * Generates a TExtOverlayOutline with default values
	 */
	public TextOverlayOutline()
	{
		this(DEFAULT_TOP_LEFT, DEFAULT_TOP_RIGHT, DEFAULT_BOTTOM_LEFT, DEFAULT_BOTTOM_RIGHT, DEFAULT_LEFT_RIGHT, DEFAULT_TOP_BOTTOM);
	}

	/**
	 * Sets the top left corner character
	 * @param c the top left corner character
	 */
	public void setTopLeft(char c) { this.topLeft = c; }

	/**
	 * Sets the top right corner character
	 * @param c the top right corner character
	 */
	public void setTopRight(char c) { this.topRight = c; }

	/**
	 * Sets the bottom left corner character
	 * @param c the bottom left corner character
	 */
	public void setBottomLeft(char c) { this.bottomLeft = c; }

	/**
	 * Sets the bottom right corner character
	 * @param c the bottom right character
	 */
	public void setBottomRight(char c) { this.bottomRight = c; }

	/**
	 * Sets the left and right sides character
	 * @param c the left and right sides character
	 */
	public void setLeftRight(char c) { this.leftRight = c; }

	/**
	 * Sets the top and bottom character
	 * @param c the top and bottom character
	 */
	public void setTopBottom(char c) { this.topBottom = c; }

	/**
	 * Gets the top left corner character
	 * @return the top left corner character
	 */
	public char getTopLeft() { return this.topLeft; }

	/**
	 * Gets the top right corner character
	 * @return the top right corner character
	 */
	public char getTopRight() { return this.topRight; }

	/**
	 * Gets the bottom left corner character
	 * @return the top bottom left character
	 */
	public char getBottomLeft() { return this.bottomLeft; }

	/**
	 * Gets the bottom right corner character
	 * @return the top bottom right character
	 */
	public char getBottomRight() { return this.bottomRight; }

	/**
	 * Gets the left and right sides character
	 * @return the left and right sides character
	 */
	public char getLeftRight() { return this.leftRight; }

	/**
	 * Gets the top and bottom character
	 * @return the top and bottom character
	 */
	public char getTopBottom() { return this.topBottom; }
}
