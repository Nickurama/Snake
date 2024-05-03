package SnakeGame;

import GameEngine.*;
import Geometry.*;

public class GameoverOverlay extends GameObject implements IOverlay
{
	private static final String GAMEOVER_STR = "Game Over!";
	private static final String SCORE_STR = "Score: ";
	private static final String NAME_STR = "What is your name?";

	private TextOverlay overlay;
	private Snake snakeHandle;

	public GameoverOverlay(Snake snake, Rectangle camera, TextOverlayOutline outline)
	{
		this.snakeHandle = snake;
		this.overlay = new TextOverlay(camera);
		this.overlay.setOutline(outline);
	}

	public GameoverOverlay(Snake snake, Rectangle camera, char cornerTL, char cornerTR, char cornerDL, char cornerDR, char sideLR, char sideTD)
	{
		this(snake, camera, new TextOverlayOutline(cornerTL, cornerTR, cornerDL, cornerDR, sideLR, sideTD));
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
		drawOverlay(snakeHandle.length());
	}

	public char[][] getOverlay()
	{
		return this.overlay.getOverlay();
	}
}
