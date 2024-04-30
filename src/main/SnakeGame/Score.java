package SnakeGame;

import java.io.Serializable;
import java.time.LocalDate;

public class Score implements Comparable<Score>, Serializable
{
	private String name;
	private LocalDate date;
	private int score;

	public Score(String name, LocalDate date, int score)
	{
		this.name = name;
		this.date = date;
		this.score = score;
	}

	public String name() { return this.name; }
	public LocalDate date() { return this.date; }
	public int score() { return this.score; }
	public int compareTo(Score other) { return this.score - other.score; }
}
