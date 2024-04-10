package GameEngine;

import java.util.*;

/**
 * Represents a collection of game objects
 */
public class Scene implements Iterable<GameObject>
{
	public class SceneIterator implements Iterator<GameObject>
	{
		private Iterator<Map.Entry<Integer, GameObject>> entryIterator;

		public SceneIterator()
		{
			entryIterator = Scene.this.objects.entrySet().iterator();
		}

		@Override
		public boolean hasNext()
		{
			return entryIterator.hasNext();
		}

		@Override
		public GameObject next()
		{
			return entryIterator.next().getValue();
		}
	}

	private HashMap<Integer, GameObject> objects;
	// private ArrayList<GameObject> objects;
	boolean isActive;

	/**
	 * Initializes a scene
	 */
	public Scene()
	{
		this.objects = new HashMap<Integer, GameObject>();
		this.isActive = false;
	}

	/**
	 * Adds a game object to the scene
	 * and calls onStart() if the scene is already active
	 * @param object the object to add to the scene
	 */
	public void add(GameObject object)
	{
		this.objects.put(object.id(), object);
		if (this.isActive)
			object.start();
	}

	public void remove(GameObject object)
	{
		this.objects.remove(object.id());
		if (this.isActive)
			object.stop();
	}

	/**
	 * Returns a game object in the scene at an index
	 * @param i The index to return
	 * @return the game object at index i in the scene
	 * @pre i < scene.size() - 1
	 */
	public GameObject get(int i)
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

	public Iterator<GameObject> iterator()
	{
		return new SceneIterator();
	}

	public boolean isActive() { return this.isActive; }
	public void setActive(boolean value) { this.isActive = value; }
}
