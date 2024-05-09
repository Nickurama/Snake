package SnakeGame;

import TestUtil.*;

import java.io.*;
import java.time.LocalDate;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;


public class ScoreTests
{
	@Test
	public void ShouldGetData()
	{
		// Arrange
		String name = "sample name";
		LocalDate date = LocalDate.of(2003, 1, 5);
		int scoreValue = 100;

		// Act
		Score score = new Score(name, date, scoreValue);

		// Arrange
		assertEquals(name, score.name());
		assertEquals(date, score.date());
		assertEquals(scoreValue, score.score());
	}

	@Test
	public void ShouldBeComparableByScore()
	{
		// Arrange
		Score[] scores = new Score[] {
			new Score("sample0", LocalDate.of(2003, 5, 1), 5),
			new Score("sample1", LocalDate.of(2003, 5, 1), 15),
			new Score("sample2", LocalDate.of(2003, 5, 1), 1),
		};

		// Act
		Arrays.sort(scores);

		// Assert
		assertEquals(1, scores[0].score());
		assertEquals(5, scores[1].score());
		assertEquals(15, scores[2].score());
	}

	@Test
	public void ShouldBeSerializable() throws FileNotFoundException, IOException, ClassNotFoundException
	{
		// Arrange
		String filename = "scoreTestFile.ser";
		String path = TestUtil.TEST_FILES_PATH + filename;
		Score score = new Score("sample name", LocalDate.of(2024, 4, 30), 1414);

		FileOutputStream fileOut = new FileOutputStream(path);
		ObjectOutputStream out = new ObjectOutputStream(fileOut);

		FileInputStream fileIn = new FileInputStream(path);
		ObjectInputStream in = new ObjectInputStream(fileIn);

		// Act
		out.writeObject(score);
		out.close();
		fileOut.close();
		Score gotten = (Score) in.readObject();
		in.close();
		fileIn.close();

		// Assert
		assertEquals(score.name(), gotten.name());
		assertEquals(score.date(), gotten.date());
		assertEquals(score.score(), gotten.score());
	}
}
