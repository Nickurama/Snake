package GameEngine;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Scanner;

/**
 * Manages input from stdin and from a window
 * Calls the appropriate events on {@link IInputListener IInputListener}
 * Also acts as a wrapper for KeyListener
 * 
 * @author Diogo Fonseca a79858
 * @version 18/05/2024
 *
 * @inv Singleton
 * @inv {@link IInputListener#onInputReceived(String) onInputReceived} is called when input is received from stdin
 * @inv {@link IInputListener#onKeyPressed(KeyEvent) onKeyPressed} is called when key is pressed in graphic mode
 * @inv {@link IInputListener#onKeyReleased(KeyEvent) onKeyReleased} is called when key is released in graphic mode
 * @inv {@link IInputListener#onKeyTyped(KeyEvent) onKeyTyped} is called when key is typed in graphic mode
 * @see IInputListener
 */
public class InputManager implements KeyListener
{
	private static InputManager instance;
	private Scene scene;

	/**
	 * Singleton constructor
	 */
	private InputManager() { }

	/**
	 * Gets the input manager instance
	 * @return the input manager instance
	 */
	public static InputManager getInstance()
	{
		if (instance == null)
			instance = new InputManager();
		return instance;
	}

	/**
	 * Initializes the InputManager
	 * @param scene the scene to call the input events on
	 */
	public void init(Scene scene)
	{
		this.scene = scene;
	}

	/**
	 * Captures input from stdin
	 */
	public void captureInput()
	{
		Scanner reader = new Scanner(System.in);
		while(GameEngine.getInstance().isRunning())
		{
			String input = reader.nextLine();
			// System.out.println("input received! " + input);

			GameEngine.getInstance().priorityInput(input);
			if (GameEngine.getInstance().isRunning())
			{
				for (IInputListener listener : this.scene.inputListeners())
					listener.onInputReceived(input);
				GameEngine.getInstance().onInputReceived(input);
			}
		}
		reader.close();
	}

	@Override
	public void keyPressed(KeyEvent event)
	{
		System.out.println("key event! " + event.getKeyChar() + " pressed!");
		if (event.getKeyChar() == 'n')
		{
			System.out.println("N key pressed.");
			// update(1);
		}
		for (IInputListener listener : scene.inputListeners())
			listener.onKeyPressed(event);
		GameEngine.getInstance().onKeyPressed(event);
	}

	@Override
	public void keyReleased(KeyEvent event)
	{
		for (IInputListener listener : scene.inputListeners())
			listener.onKeyReleased(event);
		GameEngine.getInstance().onKeyReleased(event);
	}

	@Override
	public void keyTyped(KeyEvent event)
	{
		for (IInputListener listener : scene.inputListeners())
			listener.onKeyTyped(event);
		GameEngine.getInstance().onKeyTyped(event);
	}
}
