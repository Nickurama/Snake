package SnakeGame;

/**
 * An object that will retrieve the game's highscores
 * 
 * @author Diogo Fonseca a79858
 * @version 06/05/2024
 *
 * @see Score
 */
public interface IHighscoresReader
{
	/**
	 * Gets the highscores.
	 * @return the highscores
	 * @throws SnakeGameException if there was an error reading the highscores
	 */
	public Score[] getScores() throws SnakeGameException;
}
