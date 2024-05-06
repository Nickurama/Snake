package SnakeGame;

import GameEngine.*;
import Geometry.*;

public class GameoverOverlay extends GameObject implements IOverlay
{
	private static final String GAMEOVER_STR = "Game Over!";
	private static final String SCORE_STR = "Score: ";
	private static final String NAME_STR = "What is your name?";

	private TextOverlay overlay;
	private ISnakeStats snakeStats;

	public GameoverOverlay(ISnakeStats snakeStats, Rectangle camera, TextOverlayOutline outline)
	{
		this.snakeStats = snakeStats;
		this.overlay = new TextOverlay(camera);
		this.overlay.setOutline(outline);
	}

	public GameoverOverlay(ISnakeStats snakeStats, Rectangle camera, char cornerTL, char cornerTR, char cornerDL, char cornerDR, char sideLR, char sideTD)
	{
		this(snakeStats, camera, new TextOverlayOutline(cornerTL, cornerTR, cornerDL, cornerDR, sideLR, sideTD));
	}

	private void drawOverlay(int score)
	{
		overlay.fillEmpty();
		overlay.writeCentered(GAMEOVER_STR, overlay.innerHeight() / 3);
		overlay.writeCentered(SCORE_STR + score, (overlay.height()) / 2);
		overlay.writeCentered(NAME_STR, overlay.height() - (overlay.innerHeight() / 3) - 1);
	}

	@Override
	public void start()
	{
		drawOverlay(snakeStats.score());
	}

	public char[][] getOverlay()
	{
		return this.overlay.getOverlay();
	}
}
