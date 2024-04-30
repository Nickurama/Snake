package SnakeGame;

import Geometry.*;
import GameEngine.*;

public class SnakeOverlay extends GameObject implements IOverlay
{
	public static enum OverlayType
	{
		GAMEPLAY,
		LOSE,
	};

	public static final char CORNER_TL = '╔';
	public static final char CORNER_TR = '╗';
	public static final char CORNER_DL = '╚';
	public static final char CORNER_DR = '╝';
	public static final char SIDE_LR = '║';
	public static final char SIDE_TD = '═';
	public static final String DIR_STR = "Dir: ";
	public static final String SCR_STR = "Score: ";

	private OverlayType activeOverlayType;
	private BoundingBox bounds;
	private char[][] overlay;

	public SnakeOverlay(Rectangle camera, OverlayType overlay)
	{
		this.bounds = new BoundingBox(camera);
		this.activeOverlayType = overlay;
		updateGameplayOverlay(0, 0);
	}

	public void setOverlayType(OverlayType newOverlay)
	{
		this.activeOverlayType = newOverlay;
	}

	private void updateGameplayOverlay(int dir, int score)
	{
		resetOverlay();
		setOutline();
		setDirection(dir);
		setScore(score);
	}

	private void resetOverlay()
	{
		int dx = (int)Math.floor(bounds.maxPoint().X()) - (int)Math.ceil(bounds.minPoint().X()) + 1;
		int dy = (int)Math.floor(bounds.maxPoint().Y()) - (int)Math.ceil(bounds.minPoint().Y()) + 1;
		overlay = new char[dy][dx];
	}

	private void setOutline()
	{
		setCorners();

		// left side
		for (int i = 1; i < overlay.length - 1; i++)
			overlay[i][0] = SIDE_LR;

		// right side
		for (int i = 1; i < overlay.length - 1; i++)
			overlay[i][overlay[0].length - 1] = SIDE_LR;

		// up
		for (int i = 1; i < overlay[0].length - 1; i++)
			overlay[0][i] = SIDE_TD;

		// down
		for (int i = 1; i < overlay[0].length - 1; i++)
			overlay[overlay.length - 1][i] = SIDE_TD;
	}

	private void setCorners()
	{
		overlay[0][0] = CORNER_TL;
		overlay[0][overlay[0].length - 1] = CORNER_TR;
		overlay[overlay.length - 1][0]= CORNER_DL;
		overlay[overlay.length - 1][overlay[0].length - 1] = CORNER_DR;
	}

	private void setDirection(int dir)
	{
		String dirStr = DIR_STR + dir;
		char[] dirChars = dirStr.toCharArray();
		int n = 0;
		for (int i = 1; n < dirChars.length && i < overlay[0].length - 2; i++)
			overlay[overlay.length - 2][i] = dirChars[n++];
	}

	private void setScore(int score)
	{
		String scrStr = SCR_STR + score;
		char[] scrChars = scrStr.toCharArray();
		int n = 0;
		int startI = overlay[0].length - 2 - scrChars.length;
		if (startI < 1)
			return;
		for (int i = startI; n < scrChars.length; i++)
			overlay[overlay.length - 2][i] = scrChars[n++];
	}

	@Override
	public void update(int deltaT)
	{
		// TODO
	}

	public char[][] getOverlay()
	{
		return this.overlay;
	}
}
