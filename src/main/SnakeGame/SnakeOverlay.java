package SnakeGame;

import Geometry.*;
import GameEngine.*;

public class SnakeOverlay extends GameObject implements IOverlay
{
	public static enum OverlayType
	{
		GAMEPLAY,
		GAMEOVER,
		HIGHSCORES,
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
	private int maxHighscoreEntries;

	public SnakeOverlay(Rectangle camera, OverlayType overlay)
	{
		this.bounds = new BoundingBox(camera);
		this.activeOverlayType = overlay;
		this.maxHighscoreEntries = -1;
		updateOverlay();
	}

	public void setOverlayType(OverlayType newOverlay)
	{
		this.activeOverlayType = newOverlay;
	}

	public void setMaxHighscoreEntries(int maxEntries)
	{
		this.maxHighscoreEntries = maxEntries;
	}

	private void updateOverlay()
	{
		switch (this.activeOverlayType)
		{
			case OverlayType.GAMEPLAY:
				updateGameplayOverlay(0, 0);
				break;
			case OverlayType.GAMEOVER:
				updateGameoverOverlay(60);
				break;
			case OverlayType.HIGHSCORES:
				updateHighscoresOverlay(5);
				break;
		}
	}

	private void updateGameoverOverlay(int score)
	{
		resetOverlay();
		setOutline();
		fillEmpty();
		writeCentered("Game Over!", (overlay.length - 2) / 3);
		writeCentered("Score: " + score, (overlay.length) / 2);
		writeCentered("What is your name?", overlay.length - (overlay.length - 2) / 3 - 1);
	}

	private void updateHighscoresOverlay(int maxScores)
	{
		resetOverlay();
		setOutline();
		fillEmpty();
		if (this.maxHighscoreEntries < 0)
			writeCentered("Highscores", 2);
		else
			writeCentered("Highscores (Top " + this.maxHighscoreEntries + ")", 2);
		writeScores();
	}

	private void writeScores()
	{
		Score[] scores = null;
		boolean errorOccurred = false;
		int writeIndex = 4;
		try
		{
			scores = Scoreboard.getInstance().getScores();
		}
		catch (SnakeGameException e)
		{
			errorOccurred = true;
		}

		if (errorOccurred)
		{
			writeParagraph(" An error ocurred while reading scores...", writeIndex);
			return;
		}

		if (scores == null)
		{
			writeParagraph(" No scores found...", writeIndex);
			return;
		}

		writeScores(scores, writeIndex);
	}

	private void writeScores(Score[] scores, int writeIndex)
	{
		int maxNameLen = scores[0].name().length();
		int maxScoreLen = Integer.toString(scores[0].score()).length();

		int maxScoresCanBeDisplayed = overlay.length - 2 - writeIndex;
		int numScoresDisplayed = this.maxHighscoreEntries < 0 ? maxScoresCanBeDisplayed : Math.min(maxScoresCanBeDisplayed, this.maxHighscoreEntries);

		for (int i = 0; i < numScoresDisplayed; i++)
			maxNameLen = Math.max(maxNameLen, scores[i].name().length());

		for (int i = 0; i < numScoresDisplayed; i++)
			writeLeft(formatScore(scores[i], i + 1, maxNameLen, maxScoreLen), writeIndex + i);
		// int n = 1;
		// for (Score s : scores)
		// {
		// 	if (writeIndex + n > overlay.length - 2 || (this.maxHighscoreEntries > 0 && n > this.maxHighscoreEntries))
		// 		break;
		//
		// 	writeLeft(formatScore(s, n, maxNameLen, maxScoreLen), writeIndex + n - 1);
		// 	n++;
		// }
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

	private void updateGameplayOverlay(int dir, int score)
	{
		resetOverlay();
		setOutline();
		writeLeft(DIR_STR + dir, overlay.length - 2);
		writeRight(SCR_STR + score, overlay.length - 2);
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

	private void fillEmpty()
	{
		for (int i = 1; i < overlay.length - 1; i++)
			for (int j = 1; j < overlay[0].length - 1; j++)
				overlay[i][j] = ' ';
	}

	private void writeCentered(String str, int index)
	{
		int size = overlay[0].length - 2;
		size -= str.length();
		if (size < 0)
			return;
		int leftSize = size / 2;

		for (int i = 0; i < str.length(); i++)
			overlay[index][1 + leftSize + i] = str.charAt(i);
	}

	private void writeLeft(String str, int index)
	{
		int n = 0;
		for (int i = 1; i < overlay[0].length - 1 && n < str.length(); i++)
			overlay[index][i] = str.charAt(n++);
	}

	private void writeRight(String str, int index)
	{
		int n = 0;
		int startI = overlay[0].length - 1 - str.length();
		if (startI < 1)
			return;
		for (int i = startI; n < str.length(); i++)
			overlay[index][i] = str.charAt(n++);
	}

	private void writeParagraph(String str, int index)
	{
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

	@Override
	public void update(int deltaT)
	{
		updateOverlay();
	}

	public char[][] getOverlay()
	{
		return this.overlay;
	}
}
