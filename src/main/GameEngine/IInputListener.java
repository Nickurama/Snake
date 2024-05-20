package GameEngine;

import java.awt.event.KeyEvent;

/**
 * Represents a {@link GameObject game object} that listens for input
 * Also acts as a wrapper interface for KeyListener
 * 
 * @author Diogo Fonseca a79858
 * @version 10/04/2024
 *
 * @inv onInputReceived is called when input is received from stdin
 * @see GameObject
 * @see CollisionManager
 */
public interface IInputListener
{
	/**
	 * Event called when input is received from stdin.
	 * Should only be called by the {@link GameEngine}
	 * @param input the input received
	 */
	public void onInputReceived(String input);

	/**
	 * Event called when a key is pressed
	 * Should only be called by the {@link InputManager}
	 * @param event information in regards to the key pressed.
	 */
	public void onKeyPressed(KeyEvent event);

	/**
	 * Event called when a key is released
	 * Should only be called by the {@link InputManager}
	 * @param event information in regards to the key released.
	 */
	public void onKeyReleased(KeyEvent event);

	/**
	 * Event called when a key is typed
	 * Should only be called by the {@link InputManager}
	 * @param event information in regards to the key typed.
	 */
	public void onKeyTyped(KeyEvent event);
}
