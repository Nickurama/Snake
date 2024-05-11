package SnakeGame;

import GameEngine.*;
import Geometry.*;

/**
 * The overlay displayed when the current game terminates.
 * 
 * @author Diogo Fonseca a79858
 * @version 06/05/2024
 *
 * @see IOverlay
 * @see ISnakeStats
 */
public class GameoverOverlay extends GameObject implements IOverlay
{
	private static final String GAMEOVER_STR = "Game Over!";
	private static final String SCORE_STR = "Score: ";
	private static final String NAME_STR = "What is your name?";

	private TextOverlay overlay;
	private ISnakeStats snakeStats;

	/**
	 * Instantiates a GameoverOverlay
	 * @param snakeStats the handle to get the snake's info
	 * @param camera the camera to draw the overlay on
	 * @param outline the outline of the overlay
	 */
	public GameoverOverlay(ISnakeStats snakeStats, Rectangle camera, TextOverlayOutline outline)
	{
		this.snakeStats = snakeStats;
		this.overlay = new TextOverlay(camera);
		this.overlay.setOutline(outline);
	}

	/**
	 * Initializes a GameoverOverlay 
	 * @param snakeStats the handle to get the snake's info
	 * @param camera the camera to draw the overlay in
	 * @param cornerTL the top left corner character
	 * @param cornerTR the top right corner character
	 * @param cornerDL the bottom left corner character
	 * @param cornerDR the bottom right corner character
	 * @param sideLR the left and right sides character
	 * @param sideTD the top and bottom character
	 */
	public GameoverOverlay(ISnakeStats snakeStats, Rectangle camera, char cornerTL, char cornerTR, char cornerDL, char cornerDR, char sideLR, char sideTD)
	{
		this(snakeStats, camera, new TextOverlayOutline(cornerTL, cornerTR, cornerDL, cornerDR, sideLR, sideTD));
	}

	/**
	 * Draws the gameover overlay
	 * @param score
	 */
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

	@Override
	public char[][] getOverlay()
	{
		return this.overlay.getOverlay();
	}
}
