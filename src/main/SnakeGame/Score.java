package SnakeGame;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Represents a score entry
 * Can be sorted by score
 * 
 * @author Diogo Fonseca a79858
 * @version 30/04/2024
 */
public class Score implements Comparable<Score>, Serializable
{
	private String name;
	private LocalDate date;
	private int score;

	/**
	 * Instantiates a score
	 * @param name the name of the user who set the score
	 * @param date the date the score was set in
	 * @param score the score value
	 */
	public Score(String name, LocalDate date, int score)
	{
		this.name = name;
		this.date = date;
		this.score = score;
	}

	/**
	 * The name of the user who set the score
	 * @return the name of the user whos set the score
	 */
	public String name() { return this.name; }

	/**
	 * The date the score was set in
	 * @return the date the score was set in
	 */
	public LocalDate date() { return this.date; }

	/**
	 * The score value
	 * @return the score value
	 */
	public int score() { return this.score; }

	@Override
	public int compareTo(Score other) { return this.score - other.score; }
}
