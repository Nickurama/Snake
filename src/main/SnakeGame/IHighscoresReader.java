package SnakeGame;

public interface IHighscoresReader
{
	public Score[] getScores() throws SnakeGameException;
}
