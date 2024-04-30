package SnakeGameTests;

import SnakeGame.*;
import TestUtil.*;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;

import GameEngine.Logger;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

public class ScoreboardTests
{
	private final String TEST_FILE = TestUtil.TEST_FILES_PATH + "testscores.ser";

	@Test
	public void ShouldSaveScores() throws SnakeGameException
	{
		Logger.startLogging(Logger.Level.INFO);
		// Arrange
		String name = "sample";
		LocalDate date = LocalDate.of(2003, 5, 1);
		int scoreValue = 21;
		Score score = new Score(name, date, scoreValue);

		Scoreboard scoreboard = Scoreboard.getInstance();
		scoreboard.setFile(TEST_FILE);
		scoreboard.purgeScores();

		// Act
		scoreboard.addEntry(score);
		Score[] scores = scoreboard.getScores();

		// Arrange
		assertEquals(1, scores.length);
		assertEquals(name, scores[0].name());
		assertEquals(date, scores[0].date());
		assertEquals(scoreValue, scores[0].score());
	}

	@Test
	public void ShouldSortScores() throws SnakeGameException
	{
		// Arrange
		int lowestScore = 1;
		int middleScore = 5;
		int highestScore = 15;
		Score score0 = new Score("sample0", LocalDate.of(2003, 5, 1), middleScore);
		Score score1 = new Score("sample1", LocalDate.of(2003, 5, 1), highestScore);
		Score score2 = new Score("sample2", LocalDate.of(2003, 5, 1), lowestScore);

		Scoreboard scoreboard = Scoreboard.getInstance();
		scoreboard.setFile(TEST_FILE);
		scoreboard.purgeScores();
		scoreboard.addEntry(score0);
		scoreboard.addEntry(score1);
		scoreboard.addEntry(score2);

		// Act
		Score[] scores = scoreboard.getScores();

		// Arrange
		assertEquals(3, scores.length);
		assertEquals(highestScore, scores[0].score());
		assertEquals(middleScore, scores[1].score());
		assertEquals(lowestScore, scores[2].score());
	}
}
