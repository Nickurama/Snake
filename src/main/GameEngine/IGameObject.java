package GameEngine;

/**
 * Interface for objects in the game engine
 * 
 * @author Diogo Fonseca a79858
 * @version 07/05/2024
 */
public interface IGameObject
{
	/**
	 * Is called every frame
	 * @param deltaTms the time since the last update
	 */
	public void onUpdate(int deltaTms);

	/**
	 * Is called when the game engine starts
	 */
	public void onStart();

	/**
	 * Is called when the game engine stops
	 */
	public void onStop();
}
