package GameEngine;

/**
 * Represents an object of a game.
 * This class is made to be inherited.
 * 
 * @author Diogo Fonseca a79858
 * @version 06/05/2024
 * 
 * @inv id is negative when the the object is not in a scene
 * @see Scene
 */
public class GameObject
{
	private int id = -1; // set to -1 here since constructor wouldn't be inherited
	private Scene sceneHandle = null;

	/**
	 * Sets the game object's to the scene it's contained in
	 * Should only be called by a {@link Scene scene}
	 *
	 * @param scene the scene the game object is in
	 * @param id the unique (within the scene) id for the object
	 * @throws GameEngineException if the game object is already in a scene
	 */
	protected final void setScene(Scene scene, int id) throws GameEngineException
	{
		if (hasSceneBeenSet())
			throw new GameEngineException("Tried to set GameObject scene after it has already been set.");

		this.sceneHandle = scene;
		this.id = id;
	}

	/**
	 * If the game object is already linked to a scene
	 * @return if the game object is already linked to a scene
	 */
	private final boolean hasSceneBeenSet()
	{
		return id != -1;
	}

	/**
	 * Detaches the game object of it's scene.
	 * Doesn't do anything if the game object isn't in a scene.
	 *
	 * Should only be called by {@link Scene Scene}.
	 */
	protected final void detachScene()
	{
		id = -1;
		this.sceneHandle = null;
	}

	/**
	 * The scene the game object is in. (can be null)
	 * @return the scene the game object is in. (can be null)
	 */
	public final Scene sceneHandle()
	{
		return this.sceneHandle;
	}

	/**
	 * The object's id. Set by the scene it's in
	 * @return the object's id
	 */
	public final Integer id()
	{
		return this.id;
	}

	/**
	 * Called once every {@link GameEngine game engine} cycle
	 *
	 * @param deltaTms the time since the last update
	 */
	public void update(int deltaTms) {}

	/**
	 * Called when the {@link GameEngine game engine} starts
	 * or whenever the object is added to a {@link Scene scene},
	 * in case the {@link GameEngine game engine} is already running
	 */
	public void start() {}

	/**
	 * Called when the {@link GameEngine game engine} stops.
	 * or whenever the object is removed from a {@link Scene scene},
	 * in the case the {@link GameEngine game engine} is running
	 */
	public void stop() {}

	/**
	 * Called once the moment the game object is added to a {@link Scene scene}, after start.
	 */
	public void initialize() {}

	/**
	 * Called after every {@link GameObject#update() update}.
	 * Happens after collision checking.
	 */
	public void lateUpdate() {}

	/**
	 * Called before every {@link GameObject#update() update}.
	 */
	public void earlyUpdate() {}
}
