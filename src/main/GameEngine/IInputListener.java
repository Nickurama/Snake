package GameEngine;

/**
 * Represents a {@link GameObject game object} that listens for input
 * 
 * @author Diogo Fonseca a79858
 * @version 10/04/2024
 *
 * @inv onInputReceived is called when input is received
 * @see GameObject
 * @see CollisionManager
 */
public interface IInputListener
{
	/**
	 * Event called when input is received.
	 * Should only be called by the {@link GameEngine}
	 * @param input the input received
	 */
	public void onInputReceived(String input);
}
