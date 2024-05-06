package SnakeGame;

import GameEngine.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Scoreboard implements IHighscoresReader
{
	private static final String DEFAULT_FILENAME = "scores.ser";
	private static Scoreboard instance = null;
	private String filename;

	private Scoreboard()
	{
		// Singleton
		this.filename = DEFAULT_FILENAME;
	}

	public static Scoreboard getInstance()
	{
		if (instance == null)
			instance = new Scoreboard();

		return instance;
	}

	public void setFile(String filename)
	{
		this.filename = filename;
	}
	
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
