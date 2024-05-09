package GameEngine;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class TextOverlayOutlineTests
{
	@Test
	public void ShouldGetTopLeftChar()
	{
		// Arrange
		TextOverlayOutline outline = new TextOverlayOutline();
		char expected = 'a';
		outline.setTopLeft(expected);

		// Act
		char gotten = outline.getTopLeft();

		// Assert
		assertEquals(expected, gotten);
	}
	@Test
	public void ShouldGetTopRightChar()
	{
		// Arrange
		TextOverlayOutline outline = new TextOverlayOutline();
		char expected = 'a';
		outline.setTopRight(expected);

		// Act
		char gotten = outline.getTopRight();

		// Assert
		assertEquals(expected, gotten);
	}
	@Test
	public void ShouldGetBottomLeftChar()
	{
		// Arrange
		TextOverlayOutline outline = new TextOverlayOutline();
		char expected = 'a';
		outline.setBottomLeft(expected);

		// Act
		char gotten = outline.getBottomLeft();

		// Assert
		assertEquals(expected, gotten);
	}
	@Test
	public void ShouldGetBottomRightChar()
	{
		// Arrange
		TextOverlayOutline outline = new TextOverlayOutline();
		char expected = 'a';
		outline.setBottomRight(expected);

		// Act
		char gotten = outline.getBottomRight();

		// Assert
		assertEquals(expected, gotten);
	}
	@Test
	public void ShouldGetLeftRightChar()
	{
		// Arrange
		TextOverlayOutline outline = new TextOverlayOutline();
		char expected = 'a';
		outline.setLeftRight(expected);

		// Act
		char gotten = outline.getLeftRight();

		// Assert
		assertEquals(expected, gotten);
	}
	@Test
	public void ShouldGetTopBottomChar()
	{
		// Arrange
		TextOverlayOutline outline = new TextOverlayOutline();
		char expected = 'a';
		outline.setTopBottom(expected);

		// Act
		char gotten = outline.getTopBottom();

		// Assert
		assertEquals(expected, gotten);
	}

	@Test
	public void ShouldSetDefaults()
	{
		// Arrange
		TextOverlayOutline outline = new TextOverlayOutline();
		char expTL = '╔';
		char expTR = '╗';
		char expDL = '╚';
		char expDR = '╝';
		char expLR = '║';
		char expTD = '═';

		// Act
		char gotTL = outline.getTopLeft();
		char gotTR = outline.getTopRight();
		char gotDL = outline.getBottomLeft();
		char gotDR = outline.getBottomRight();
		char gotLR = outline.getLeftRight();
		char gotTD = outline.getTopBottom();

		// Assert
		assertEquals(expTL, gotTL);
		assertEquals(expTR, gotTR);
		assertEquals(expDL, gotDL);
		assertEquals(expDR, gotDR);
		assertEquals(expLR, gotLR);
		assertEquals(expTD, gotTD);
	}

	@Test
	public void ShouldGenerateFromConstructor()
	{
		// Arrange
		char expTL = 'a';
		char expTR = 'b';
		char expDL = 'c';
		char expDR = 'd';
		char expLR = 'e';
		char expTD = 'f';
		TextOverlayOutline outline = new TextOverlayOutline(expTL, expTR, expDL, expDR, expLR, expTD);

		// Act
		char gotTL = outline.getTopLeft();
		char gotTR = outline.getTopRight();
		char gotDL = outline.getBottomLeft();
		char gotDR = outline.getBottomRight();
		char gotLR = outline.getLeftRight();
		char gotTD = outline.getTopBottom();

		// Assert
		assertEquals(expTL, gotTL);
		assertEquals(expTR, gotTR);
		assertEquals(expDL, gotDL);
		assertEquals(expDR, gotDR);
		assertEquals(expLR, gotLR);
		assertEquals(expTD, gotTD);
	}
}
