package GameEngine;

import java.util.*;

/**
 * Represents a collection of managed game objects
 */
public class Scene implements Iterable<GameObject>
{
	public class SceneIterator implements Iterator<GameObject>
	{
		private HashMap<Integer, GameObject> objectsMap;
		private Iterator<Map.Entry<Integer, GameObject>> entryIterator;

		public SceneIterator()
		{
			objectsMap = new HashMap<Integer, GameObject>(Scene.this.objects);
			entryIterator = objectsMap.entrySet().iterator();
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
	private HashMap<Integer, IInputListener> inputListeners;
	private HashMap<Integer, IRenderable> renderables;
	private HashMap<Integer, ICollider> colliders;
	private IOverlay overlay;
	private boolean isActive;
	private int idCounter;

	/**
	 * Initializes a scene
	 */
	public Scene()
	{
		this.objects = new HashMap<Integer, GameObject>();
		this.inputListeners = new HashMap<Integer, IInputListener>();
		this.renderables = new HashMap<Integer, IRenderable>();
		this.colliders= new HashMap<Integer, ICollider>();
		this.isActive = false;
	}

	/**
	 * Adds a game object to the scene
	 * and calls start() if the scene is already active
	 * @param object the object to add to the scene
	 */
	public void add(GameObject object)
	{
		try
		{
			object.setScene(this, nextId());
		}
		catch (GameEngineException e)
		{
			Logger.log(Logger.Level.FATAL, "Could not add GameObject to scene.\n" + e);
			throw new RuntimeException("Could not add GameObject to scene: " + e.getMessage());
		}
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
			this.inputListeners.put(object.id(), (IInputListener) object);
		if (object instanceof IRenderable)
			this.renderables.put(object.id(), (IRenderable) object);
		if (object instanceof IOverlay)
			this.overlay = (IOverlay) object;
		if (object instanceof ICollider)
			this.colliders.put(object.id(), (ICollider) object);
	}

	public void remove(GameObject object)
	{
		this.objects.remove(object.id());

		if (object instanceof IInputListener)
			this.inputListeners.remove(object.id());
		if (object instanceof IRenderable)
			this.renderables.remove(object.id());
		if (object instanceof IOverlay)
			this.overlay = null;
		if (object instanceof ICollider)
			this.colliders.remove(object.id());

		if (this.isActive)
			object.stop();

		object.detachScene(); // has to  be last operation
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
			class IInputListenerIterator implements Iterator<IInputListener>
			{
				private HashMap<Integer, IInputListener> map = new HashMap<Integer, IInputListener>(Scene.this.inputListeners);
				private Iterator<Map.Entry<Integer, IInputListener>> entryIterator = map.entrySet().iterator();
				public boolean hasNext() { return entryIterator.hasNext(); }
				public IInputListener next() { return entryIterator.next().getValue(); }
			}
			public Iterator<IInputListener> iterator() { return new IInputListenerIterator(); }
		};
	}

	public Iterable<ICollider> colliders()
	{
		return new Iterable<ICollider>()
		{
			class IColliderIterator implements Iterator<ICollider>
			{
				private HashMap<Integer, ICollider> map = new HashMap<Integer, ICollider>(Scene.this.colliders);
				private Iterator<Map.Entry<Integer, ICollider>> entryIterator = map.entrySet().iterator();
				public boolean hasNext() { return entryIterator.hasNext(); }
				public ICollider next() { return entryIterator.next().getValue(); }
			}
			public Iterator<ICollider> iterator() { return new IColliderIterator(); }
		};
	}

	public ICollider[] collidersArr()
	{
		return this.colliders.values().toArray(new ICollider[0]);
	}

	public Iterable<IRenderable> renderables()
	{
		return new Iterable<IRenderable>()
		{
			class IRenderableIterator implements Iterator<IRenderable>
			{
				private HashMap<Integer, IRenderable> map = new HashMap<Integer, IRenderable>(Scene.this.renderables);
				private Iterator<Map.Entry<Integer, IRenderable>> entryIterator = map.entrySet().iterator();
				public boolean hasNext() { return entryIterator.hasNext(); }
				public IRenderable next() { return entryIterator.next().getValue(); }
			}
			public Iterator<IRenderable> iterator() { return new IRenderableIterator(); }
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

	public IOverlay getOverlay()
	{
		return this.overlay;
	}

	public boolean isActive() { return this.isActive; }
	public void setActive(boolean value) { this.isActive = value; }
}
