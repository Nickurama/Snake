package SnakeGame;

import Geometry.*;
import GameEngine.*;

public class HighscoresOverlay extends GameObject implements IOverlay
{
	public static final String HIGHSCORE_TITLE_STR = "Highscores";
	public static final String ERR_NO_SCORES_STR = " No scores found...";
	public static final String ERR_READING_STR = " An error ocurred while reading scores...";

	private IHighscoresReader highscoresReader;
	private TextOverlay overlay;
	private int maxHighscoreEntries;

	public HighscoresOverlay(IHighscoresReader highscoresReader, Rectangle camera, int maxHighscoreEntries, TextOverlayOutline outline)
	{
		this.highscoresReader = highscoresReader;
		this.overlay = new TextOverlay(camera);
		this.overlay.setOutline(outline);
		this.maxHighscoreEntries = maxHighscoreEntries;
	}

	public HighscoresOverlay(IHighscoresReader highscoresReader, Rectangle camera, TextOverlayOutline overlay)
	{
		this(highscoresReader, camera, -1, overlay);
	}

	public HighscoresOverlay(IHighscoresReader highscoresReader, Rectangle camera, int maxHighscoreEntries, char cornerTL, char cornerTR, char cornerDL, char cornerDR, char sideLR, char sideTD)
	{
		this(highscoresReader, camera, maxHighscoreEntries, new TextOverlayOutline(cornerTL, cornerTR, cornerDL, cornerDR, sideLR, sideTD));
	}

	public HighscoresOverlay(IHighscoresReader highscoresReader, Rectangle camera, char cornerTL, char cornerTR, char cornerDL, char cornerDR, char sideLR, char sideTD)
	{
		this(highscoresReader, camera, new TextOverlayOutline(cornerTL, cornerTR, cornerDL, cornerDR, sideLR, sideTD));
	}

	private void drawOverlay(int maxScores)
	{
		overlay.fillEmpty();
		if (this.maxHighscoreEntries < 0)
			overlay.writeCentered(HIGHSCORE_TITLE_STR, 2);
		else
			overlay.writeCentered(HIGHSCORE_TITLE_STR + " (Top " + this.maxHighscoreEntries + ")", 2);
		writeScores();
	}

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
			overlay.writeLeft(formatScore(scores[i], i + 1, maxNameLen, maxScoreLen), writeIndex + i);
	}

	private String formatScore(Score s, int place, int maxNameLen, int maxScoreLen)
	{
		StringBuilder builder = new StringBuilder();
		builder.append(" ");
		builder.append(place);
		builder.append(". ");
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

	public char[][] getOverlay()
	{
		return this.overlay.getOverlay();
	}
}
