package GameEngine;

/**
 * Interface for objects in the game engine
 * 
 * @author Diogo Fonseca a79858
 * @version 07/05/2024
 */
public class GameObject
{
	// private static int counter = 0;
	// private final int id = counter++;
	private int id = -1; // set to -1 here since constructor wouldn't be inherited
	protected Scene sceneHandle;

	public final void setScene(Scene scene, int id) throws GameEngineException
	{
		if (hasSceneBeenSet())
			throw new GameEngineException("Tried to set GameObject scene after it has already been set.");

		this.sceneHandle = scene;
		this.id = id;
	}

	private final boolean hasSceneBeenSet()
	{
		return id != -1;
	}

	public final Integer id()
	{
		return this.id;
	}

	/**
	 * Is called every frame
	 * @param deltaTms the time since the last update
	 */
	public void update(int deltaTms) {};

	/**
	 * Is called when the game engine starts
	 * and whenever the object is instantiated
	 */
	public void start() {};

	/**
	 * Is called when the game engine stops
	 * and whenever the object is uninstantiated
	 */
	public void stop() {};

	public void lateUpdate() {};
}
