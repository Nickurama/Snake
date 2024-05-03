package GameEngine;

public class TextOverlayOutline
{
	private char topLeft;
	private char topRight;
	private char bottomLeft;
	private char bottomRight;
	private char leftRight;
	private char topBottom;

	public TextOverlayOutline(char topLeft, char topRight, char bottomLeft, char bottomRight, char leftRight, char topBottom)
	{
		this.topLeft = topLeft;
		this.topRight = topRight;
		this.bottomLeft = bottomLeft;
		this.bottomRight = bottomRight;
		this.leftRight = leftRight;
		this.topBottom = topBottom;
	}

	public TextOverlayOutline()
	{
		this('╔', '╗', '╚', '╝', '║', '═');
	}

	public void setTopLeft(char c) { this.topLeft = c; }
	public void setTopRight(char c) { this.topRight = c; }
	public void setBottomLeft(char c) { this.bottomLeft = c; }
	public void setBottomRight(char c) { this.bottomRight = c; }
	public void setLeftRight(char c) { this.leftRight = c; }
	public void setTopBottom(char c) { this.topBottom = c; }
	public char getTopLeft() { return this.topLeft; }
	public char getTopRight() { return this.topRight; }
	public char getBottomLeft() { return this.bottomLeft; }
	public char getBottomRight() { return this.bottomRight; }
	public char getLeftRight() { return this.leftRight; }
	public char getTopBottom() { return this.topBottom; }
}
