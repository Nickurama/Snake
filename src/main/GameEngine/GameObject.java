package GameEngine;

/**
 * Interface for objects in the game engine
 * 
 * @author Diogo Fonseca a79858
 * @version 07/05/2024
 */
public class GameObject
{
	private static int counter = 0;
	private final int id = counter++;

	@Override
	public final int hashCode()
	{
		return this.id();
	}

	@Override
	public final boolean equals(Object other)
	{
		if (this == other) return true;
        if (other == null) return false;
        if (this.getClass() != other.getClass()) return false;
		GameObject that = (GameObject)other;
		return this.id() == that.id();
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
}
