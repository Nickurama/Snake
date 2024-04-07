package GameEngine;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Represents a collection of game objects to be instanciated
 */
public class Scene implements Iterable<IGameObject>
{
	private ArrayList<IGameObject> objects;

	/**
	 * Initializes a scene
	 */
	public Scene()
	{
		this.objects = new ArrayList<IGameObject>();
	}

	/**
	 * Adds a game object to the scene
	 * @param object
	 */
	public void add(IGameObject object)
	{
		this.objects.add(object);
	}

	/**
	 * Returns a game object in the scene at an index
	 * @param i The index to return
	 * @return the game object at index i in the scene
	 * @pre i < scene.size() - 1
	 */
	public IGameObject get(int i)
	{
		return objects.get(i);
	}

	/**
	 * Returns how many game objects are in the scene
	 * @return the ammount of game objects in the scene
	 */
	public int size()
	{
		return objects.size();
	}

	public Iterator<IGameObject> iterator()
	{
		return objects.iterator();
	}
}
