package SnakeGame;

import GameEngine.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

/**
 * This class represents all of the scores set.
 * This class is responsible for handling scores,
 * having utility functions for writing and reading to and from files respectively.
 * 
 * @author Diogo Fonseca a79858
 * @version 06/05/2024
 * 
 * @see Score
 * @see IHighscoresReader
 */
public class Scoreboard implements IHighscoresReader
{
	private static final String DEFAULT_FILENAME = "scores.ser";
	private static Scoreboard instance = null;
	private String filename;

	/**
	 * Scoreboard singleton constructor
	 */
	private Scoreboard()
	{
		// Singleton
		this.filename = DEFAULT_FILENAME;
	}

	/**
	 * Gets the scoreboard instance.
	 * @return the scoreboard instance
	 */
	public static Scoreboard getInstance()
	{
		if (instance == null)
			instance = new Scoreboard();

		return instance;
	}

	/**
	 * Sets a file for reading/writing scores.
	 * @param filename the name of the file to set for reading/writing scores
	 * @post the class will now use the file set
	 */
	public void setFile(String filename)
	{
		this.filename = filename;
	}
	
	/**
	 * Adds a new score. (writing it to the file)
	 * @param score the score to add to the scoreboard
	 * @throws SnakeGameException if an error occurred while writing to the file
	 */
	public void addEntry(Score score) throws SnakeGameException
	{
		File file = new File(this.filename);
		if (!file.exists())
			createScoreFile();

		ArrayList<Score> scores = readScores();
		scores.add(score);
		Collections.sort(scores, Collections.reverseOrder());
		writeScores(scores);
	}

	/**
	 * Creates the file to hold the scores.
	 * If a file with the same name already exists, it is overwritten.
	 * @throws SnakeGameException if an error occurred creating the file
	 */
	private void createScoreFile() throws SnakeGameException
	{
		try (FileOutputStream fileOut = new FileOutputStream(filename);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);)
		{
			out.writeObject(new ArrayList<Score>());
		}
		catch (Exception e)
		{
			Logger.log(Logger.Level.ERROR, "Could not create score file.\n" + e);
			throw new SnakeGameException("Could not create score file.");
		}
	}

	/**
	 * Reads all the scores from the file
	 * @return all the scores read
	 * @throws SnakeGameException if an error occurred reading from the file
	 */
	@SuppressWarnings("unchecked")
	private ArrayList<Score> readScores() throws SnakeGameException
	{
		File file = new File(filename);
		ArrayList<Score> scores = null;

		try (FileInputStream fileIn = new FileInputStream(file);
			ObjectInputStream in = new ObjectInputStream(fileIn);)
		{
			scores = (ArrayList<Score>) in.readObject();
		}
		catch (Exception e)
		{
			Logger.log(Logger.Level.ERROR, "Could not read scores from file.\n" + e);
			throw new SnakeGameException("Could not read scores from file.");
		}
		return scores;
	}

	/**
	 * scores to the file
	 * @param scores the scores to write to the file
	 * @throws SnakeGameException if an error occurred writing to the file
	 */
	private void writeScores(ArrayList<Score> scores) throws SnakeGameException
	{
		File file = new File(filename);

		try (FileOutputStream fileOut = new FileOutputStream(file);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);)
		{
			out.writeObject(scores);
		}
		catch (Exception e)
		{
			Logger.log(Logger.Level.ERROR, "Could not write to score file.\n" + e);
			throw new SnakeGameException("Could not write to score file.");
		}
	}

	@Override
	public Score[] getScores() throws SnakeGameException
	{
		ArrayList<Score> scores = readScores();
		if (scores == null)
			return null;
		Collections.sort(scores, Collections.reverseOrder());
		Score[] result = new Score[scores.size()];
		scores.toArray(result);
		return result;
	}

	/**
	 * Purges the scoreboard, clearing all the scores.
	 */
	public void purgeScores()
	{
		try
		{
			createScoreFile();
		}
		catch (SnakeGameException e)
		{
			Logger.log(Logger.Level.ERROR, "Could not purge scores.");
		}
	}
}
