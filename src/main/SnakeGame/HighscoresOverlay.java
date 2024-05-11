package SnakeGame;

import Geometry.*;
import GameEngine.*;

/**
 * The overlay to display the highscores
 * 
 * @author Diogo Fonseca a79858
 * @version 08/05/2024
 *
 * @see score
 * @see IHighscoresReader
 * @see IOverlay
 */
public class HighscoresOverlay extends GameObject implements IOverlay
{
	public static final String HIGHSCORE_TITLE_STR = "Highscores";
	public static final String ERR_NO_SCORES_STR = " No scores found...";
	public static final String ERR_READING_STR = " An error ocurred while reading scores...";

	private IHighscoresReader highscoresReader;
	private TextOverlay overlay;
	private int maxHighscoreEntries;

	/**
	 * Instantiates a HighscoresOverlay
	 * @param highscoresReader where to get the highscores from
	 * @param camera the camera to draw the overlay on
	 * @param maxHighscoreEntries the max entries to display
	 * @param outline the overlay's outline
	 */
	public HighscoresOverlay(IHighscoresReader highscoresReader, Rectangle camera, int maxHighscoreEntries, TextOverlayOutline outline)
	{
		this.highscoresReader = highscoresReader;
		this.overlay = new TextOverlay(camera);
		this.overlay.setOutline(outline);
		this.maxHighscoreEntries = maxHighscoreEntries;
	}

	/**
	 * Instantiates a HighscoresOverlay without limiting the number of entries
	 * @param highscoresReader where to get the highscores from
	 * @param camera the camera to draw the overlay on
	 * @param outline the overlay's outline
	 */
	public HighscoresOverlay(IHighscoresReader highscoresReader, Rectangle camera, TextOverlayOutline outline)
	{
		this(highscoresReader, camera, -1, outline);
	}

	/**
	 * Initializes a HighscoresOverlay. 
	 * @param camera the camera to draw the overlay in
	 * @param maxHighscoreEntries the max entries to display
	 * @param cornerTL the top left corner character
	 * @param cornerTR the top right corner character
	 * @param cornerDL the bottom left corner character
	 * @param cornerDR the bottom right corner character
	 * @param sideLR the left and right sides character
	 * @param sideTD the top and bottom character
	 */
	public HighscoresOverlay(IHighscoresReader highscoresReader, Rectangle camera, int maxHighscoreEntries, char cornerTL, char cornerTR, char cornerDL, char cornerDR, char sideLR, char sideTD)
	{
		this(highscoresReader, camera, maxHighscoreEntries, new TextOverlayOutline(cornerTL, cornerTR, cornerDL, cornerDR, sideLR, sideTD));
	}

	/**
	 * Initializes a HighscoresOverlay without limiting the number of entries. 
	 * @param camera the camera to draw the overlay in
	 * @param cornerTL the top left corner character
	 * @param cornerTR the top right corner character
	 * @param cornerDL the bottom left corner character
	 * @param cornerDR the bottom right corner character
	 * @param sideLR the left and right sides character
	 * @param sideTD the top and bottom character
	 */
	public HighscoresOverlay(IHighscoresReader highscoresReader, Rectangle camera, char cornerTL, char cornerTR, char cornerDL, char cornerDR, char sideLR, char sideTD)
	{
		this(highscoresReader, camera, new TextOverlayOutline(cornerTL, cornerTR, cornerDL, cornerDR, sideLR, sideTD));
	}

	/**
	 * Draws the highscores overlay
	 * @param maxScores the maximum number of scores to display
	 */
	private void drawOverlay(int maxScores)
	{
		overlay.fillEmpty();
		if (this.maxHighscoreEntries < 0)
			overlay.writeCentered(HIGHSCORE_TITLE_STR, 2);
		else
			overlay.writeCentered(HIGHSCORE_TITLE_STR + " (Top " + this.maxHighscoreEntries + ")", 2);
		writeScores();
	}

	/**
	 * Writes the scores from the highscores reader to the screen
	 */
	private void writeScores()
	{
		Score[] scores = null;
		boolean errorOccurred = false;
		int writeIndex = 4;
		try
		{
			scores = highscoresReader.getScores();
		}
		catch (SnakeGameException e)
		{
			errorOccurred = true;
		}

		if (errorOccurred)
		{
			overlay.writeParagraph(ERR_READING_STR, writeIndex);
			return;
		}

		if (scores == null)
		{
			overlay.writeParagraph(ERR_NO_SCORES_STR, writeIndex);
			return;
		}

		writeScores(scores, writeIndex);
	}

	/**
	 * Writes scores to the screen
	 * @param scores the scores to write to display
	 * @param writeIndex the index where the scores should start being displayed in
	 */
	private void writeScores(Score[] scores, int writeIndex)
	{
		int maxNameLen = scores[0].name().length();
		int maxScoreLen = Integer.toString(scores[0].score()).length();

		int maxScoresCanBeDisplayed = overlay.innerHeight() - writeIndex;
		int numScoresDisplayed = this.maxHighscoreEntries < 0 ? maxScoresCanBeDisplayed : Math.min(maxScoresCanBeDisplayed, this.maxHighscoreEntries);
		numScoresDisplayed = Math.min(numScoresDisplayed, scores.length);

		for (int i = 0; i < numScoresDisplayed; i++)
			maxNameLen = Math.max(maxNameLen, scores[i].name().length());

		for (int i = 0; i < numScoresDisplayed; i++)
			overlay.writeLeft(formatScore(scores[i], i + 1, numScoresDisplayed, maxNameLen, maxScoreLen), writeIndex + i);
	}

	/**
	 * Formats a score line
	 * @param s the score to format
	 * @param place the place in the leaderboard (top n)
	 * @param maxPlace the maximum place to be displayed
	 * @param maxNameLen the maximum name length of the scores
	 * @param maxScoreLen the maximum score digit length of the scores
	 * @return A string with the score formatted
	 */
	private String formatScore(Score s, int place, int maxPlace, int maxNameLen, int maxScoreLen)
	{
		int maxNumLen = Integer.toString(maxPlace).length();

		StringBuilder builder = new StringBuilder();
		builder.append(" ");
		builder.append(place);
		builder.append(". ");
		for (int i = 0; i < maxNumLen - Integer.toString(place).length(); i++)
			builder.append(" ");
		builder.append(s.name());
		for (int i = 0; i < maxNameLen - s.name().length() + 2; i++)
			builder.append(" ");
		builder.append(s.score());
		for (int i = 0; i < maxScoreLen - Integer.toString(s.score()).length() + 2; i++)
			builder.append(" ");
		builder.append(String.format("%02d", s.date().getDayOfMonth()));
		builder.append("/");
		builder.append(String.format("%02d", s.date().getMonthValue()));
		builder.append("/");
		builder.append(s.date().getYear());
		return builder.toString();
	}

	@Override
	public void start()
	{
		drawOverlay(this.maxHighscoreEntries);
	}

	@Override
	public char[][] getOverlay()
	{
		return this.overlay.getOverlay();
	}
}
