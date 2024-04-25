package SnakeGameTests;

import SnakeGame.*;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

public class ScoreboardTests
{
	private final String TEST_FILE = "testscores";

	@Test
	public void ShouldSaveScores()
	{
		// Arrange
		String name = "sample";
		LocalDate date = LocalDate.of(2003, 5, 1);
		Integer scoreValue = 21;
		Score score = new Score(name, date, scoreValue);

		Scoreboard scoreboard = Scoreboard.getInstance();
		scoreboard.setFile(TEST_FILE);
		scoreboard.purgeScores();

		// Act
		scoreboard.addEntry(score);
		Score[] scores = scoreboard.getScores();

		// Arrange
		assertEqual(1, scores.length);
		assertEqual(name, scores[0].getName());
		assertEqual(date, scores[0].getDate());
		assertEqual(scoreValue, scores[0].getScore());
	}

	@Test
	public void ShouldSortScores()
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
		assertEqual(3, scores.length);
		assertEqual(lowestScore, scores[0].getScore());
		assertEqual(middleScore, scores[1].getScore());
		assertEqual(highestScore, scores[2].getScore());
	}
}
