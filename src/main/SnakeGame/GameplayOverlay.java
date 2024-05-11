package SnakeGame;

import Geometry.*;
import GameEngine.*;

/**
 * The overlay displayed while the game is being played
 * 
 * @author Diogo Fonseca a79858
 * @version 08/05/2024
 *
 * @see IOverlay
 * @see ISnakeStats
 */
public class GameplayOverlay extends GameObject implements IOverlay
{
	private static final String DIR_STR = "Dir: ";
	private static final String SCR_STR = "Score: ";

	private TextOverlay overlay;
	private TextOverlayOutline outline;
	private ISnakeStats snakeStats;

	/**
	 * Initializes a GameplayOverlay
	 * @param snakeStats the handle to get the snake's info
	 * @param camera the camera to draw the overlay in
	 * @param outline the outline of the overlay
	 */
	public GameplayOverlay(ISnakeStats snakeStats, Rectangle camera, TextOverlayOutline outline)
	{
		this.snakeStats = snakeStats;
		this.outline = outline;
		this.overlay = new TextOverlay(camera);
		this.overlay.setOutline(outline);
	}

	/**
	 * Initializes a GameplayOverlay
	 * @param snakeStats the handle to get the snake's info
	 * @param camera the camera to draw the overlay in
	 * @param cornerTL the top left corner character
	 * @param cornerTR the top right corner character
	 * @param cornerDL the bottom left corner character
	 * @param cornerDR the bottom right corner character
	 * @param sideLR the left and right sides character
	 * @param sideTD the top and bottom character
	 */
	public GameplayOverlay(ISnakeStats snakeStats, Rectangle camera, char cornerTL, char cornerTR, char cornerDL, char cornerDR, char sideLR, char sideTD)
	{
		this(snakeStats, camera, new TextOverlayOutline(cornerTL, cornerTR, cornerDL, cornerDR, sideLR, sideTD));
	}

	/**
	 * Updates the overlay with the snake's current information.
	 */
	private void updateOverlay()
	{
		int direction = 0;
		switch (snakeStats.direction())
		{
			case Direction.UP:
				direction = 90;
				break;
			case Direction.DOWN:
				direction = 270;
				break;
			case Direction.LEFT:
				direction = 180;
				break;
			case Direction.RIGHT:
				direction = 0;
				break;
		}
		updateOverlay(direction, snakeStats.score());
	}

	/**
	 * Updates the overlay with a given information
	 * @param dir the direction to display
	 * @param score the score to display
	 */
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

	@Override
	public char[][] getOverlay()
	{
		return this.overlay.getOverlay();
	}
}
