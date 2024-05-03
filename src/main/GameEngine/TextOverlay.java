package GameEngine;

import Geometry.*;

public class TextOverlay extends GameObject implements IOverlay
{
	private BoundingBox bounds;
	private char[][] overlay;

	public TextOverlay(Rectangle camera)
	{
		this.bounds = new BoundingBox(camera);
		reset();
	}

	public void reset()
	{
		int dx = (int)Math.floor(bounds.maxPoint().X()) - (int)Math.ceil(bounds.minPoint().X()) + 1;
		int dy = (int)Math.floor(bounds.maxPoint().Y()) - (int)Math.ceil(bounds.minPoint().Y()) + 1;
		overlay = new char[dy][dx];
	}

	public void setOutline(TextOverlayOutline outline)
	{
		setOutline(outline.getTopLeft(), outline.getTopRight(), outline.getBottomLeft(), outline.getBottomRight(), outline.getLeftRight(), outline.getTopBottom());
	}

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

	private void setCorners(char cornerTL, char cornerTR, char cornerDL, char cornerDR)
	{
		overlay[0][0] = cornerTL;
		overlay[0][overlay[0].length - 1] = cornerTR;
		overlay[overlay.length - 1][0]= cornerDL;
		overlay[overlay.length - 1][overlay[0].length - 1] = cornerDR;
	}

	public void fillEmpty()
	{
		fill(' ');
	}

	public void fill(char c)
	{
		for (int i = 1; i < overlay.length - 1; i++)
			for (int j = 1; j < overlay[0].length - 1; j++)
				overlay[i][j] = c;
	}

	private boolean isValidIndex(int index)
	{
		return !(index < 0 || index >= overlay.length);
	}

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

	public char[][] getOverlay()
	{
		return this.overlay;
	}

	public int width()
	{
		return this.overlay[0].length;
	}

	public int height()
	{
		return this.overlay.length;
	}

	public int innerWidth()
	{
		return width() - 2;
	}

	public int innerHeight()
	{
		return height() - 2;
	}
}
