package SnakeGame;

import Geometry.*;
import GameEngine.*;

public class GameplayOverlay extends GameObject implements IOverlay
{
	private static final String DIR_STR = "Dir: ";
	private static final String SCR_STR = "Score: ";

	private TextOverlay overlay;
	private TextOverlayOutline outline;
	private ISnakeStats snakeStats;

	public GameplayOverlay(ISnakeStats snakeStats, Rectangle camera, TextOverlayOutline outline)
	{
		this.snakeStats = snakeStats;
		this.outline = outline;
		this.overlay = new TextOverlay(camera);
		this.overlay.setOutline(outline);
	}

	public GameplayOverlay(ISnakeStats snakeStats, Rectangle camera, char cornerTL, char cornerTR, char cornerDL, char cornerDR, char sideLR, char sideTD)
	{
		this(snakeStats, camera, new TextOverlayOutline(cornerTL, cornerTR, cornerDL, cornerDR, sideLR, sideTD));
	}


	private void updateOverlay()
	{
		int direction = 0;
		switch (snakeStats.snakeDir())
		{
			case Snake.Direction.UP:
				direction = 90;
				break;
			case Snake.Direction.DOWN:
				direction = 270;
				break;
			case Snake.Direction.LEFT:
				direction = 180;
				break;
			case Snake.Direction.RIGHT:
				direction = 0;
				break;
		}
		updateOverlay(direction, snakeStats.score());
	}

	private void updateOverlay(int dir, int score)
	{
		overlay.reset();
		overlay.setOutline(this.outline);
		overlay.writeLeft(DIR_STR + dir, overlay.innerHeight());
		overlay.writeRight(SCR_STR + score, overlay.innerHeight());
	}

	@Override
	public void start()
	{
		updateOverlay();
	}

	@Override
	public void lateUpdate()
	{
		updateOverlay();
	}

	public char[][] getOverlay()
	{
		return this.overlay.getOverlay();
	}
}
