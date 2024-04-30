package SnakeGame;

import GameEngine.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class Scoreboard
{
	private static Scoreboard instance = null;
	private String filename;

	private Scoreboard()
	{
		// Singleton
		this.filename = "scores.csv";
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
		ArrayList<Score> scores = null;
		try (FileInputStream fileIn = new FileInputStream(filename);
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
		try (FileOutputStream fileOut = new FileOutputStream(filename);
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
