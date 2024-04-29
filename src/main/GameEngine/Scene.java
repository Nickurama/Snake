package GameEngine;

import java.util.*;

/**
 * Represents a collection of managed game objects
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
	private ArrayList<IInputListener> inputListeners;
	private ArrayList<IRenderable> renderables;
	private boolean isActive;
	private int idCounter;

	/**
	 * Initializes a scene
	 */
	public Scene()
	{
		this.objects = new HashMap<Integer, GameObject>();
		this.inputListeners = new ArrayList<IInputListener>();
		this.renderables = new ArrayList<IRenderable>();
		this.isActive = false;
	}

	/**
	 * Adds a game object to the scene
	 * and calls start() if the scene is already active
	 * @param object the object to add to the scene
	 */
	public void add(GameObject object) throws GameEngineException
	{
		object.setScene(this, nextId());
		this.objects.put(object.id(), object);

		categorize(object);

		if (this.isActive)
			object.start();
	}

	private int nextId()
	{
		return idCounter++;
	}

	private void categorize(GameObject object)
	{
		if (object instanceof IInputListener)
			this.inputListeners.add((IInputListener) object);
		if (object instanceof IRenderable)
			this.renderables.add((IRenderable) object);
	}

	public void remove(GameObject object)
	{
		this.objects.remove(object.id());
		if (this.isActive)
			object.stop();
	}

	public boolean contains(GameObject obj)
	{
		return objects.containsKey(obj.id());
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

	public Iterable<IInputListener> inputListeners()
	{
		return new Iterable<IInputListener>()
		{
			public Iterator<IInputListener> iterator() { return inputListeners.iterator(); }
		};
	}

	public Iterable<IRenderable> renderables()
	{
		return new Iterable<IRenderable>()
		{
			public Iterator<IRenderable> iterator() { return renderables.iterator(); }
		};
	}

	public RenderData<?>[] renderablesArr()
	{
		RenderData<?>[] result = new RenderData[this.renderables.size()];
		int i = 0;
		for (IRenderable r : renderables())
			result[i++] = r.getRenderData();
		return result;
	}

	public boolean isActive() { return this.isActive; }
	public void setActive(boolean value) { this.isActive = value; }
}
