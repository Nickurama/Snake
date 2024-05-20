package GameEngine;

import java.util.*;

/**
 * Represents a collection of {@link GameObject GameObjects} to be initialized and
 * updated by the {@link GameEngine GameEngine}.
 *
 * @author Diogo Fonseca a79858
 * @version 06/05/2024
 *
 * @inv Scene is active while the {@link GameEngine GameEngine} is running
 * @see GameEngine
 * @see GameObject
 */
public class Scene implements Iterable<GameObject>
{
	/**
	 * Iterator that iterates through a scene
	 */
	public class SceneIterator implements Iterator<GameObject>
	{
		private HashMap<Integer, GameObject> objectsMap;
		private Iterator<Map.Entry<Integer, GameObject>> entryIterator;

		/**
		 * Initializes the iterator
		 */
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
	 * Initializes an empty scene
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
	 * Adds a {@link GameObject GameObject} to the scene.
	 *
	 * The game object is properly categorized in the scene if it implements the
	 * interfaces listed in {@link Scene#categorize(GameObject)}.
	 * 
	 * If the scene is active, the object's {@link GameObject#start() Start} method is called.
	 * Calls the object's {@link GameObject#initialize() initialize} method.
	 *
	 * @param object the object to add to the scene
	 * @pre the object must not be in a different scene
	 * @throws RuntimeException if the object is null or if the object is already assigned to a scene
	 * @see Scene#categorize(GameObject)
	 */
	public void add(GameObject object)
	{
		if (object == null)
		{
			Logger.log(Logger.Level.FATAL, "Attempted to add a null object to the scene.");
			throw new RuntimeException("Attempted to add a null object to the scene.");
		}

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
		object.initialize();
	}

	/**
	 * The next valid id for the scene
	 * @return the next valid id for the scene
	 */
	private int nextId()
	{
		return idCounter++;
	}

	/**
	 * Categorizes a {@link GameObject GameObject} if it implements
	 * one (or more) of the following interfaces:
	 * {@link IInputListener IInputListener}
	 * {@link IRenderable IRenderable}
	 * {@link ICollider ICollider}
	 * {@link IOverlay IOverlay}
	 * being promptly placed in the corresponding collection.
	 *
	 * @param object the object to categorize
	 */
	private void categorize(GameObject object)
	{
		if (object instanceof IInputListener)
			this.inputListeners.put(object.id(), (IInputListener) object);
		if (object instanceof IRenderable)
			this.renderables.put(object.id(), (IRenderable) object);
		if (object instanceof IOverlay)
		{
			if (this.overlay != null)
				remove((GameObject)this.overlay);
			this.overlay = (IOverlay) object;
		}
		if (object instanceof ICollider)
			this.colliders.put(object.id(), (ICollider) object);
	}

	/**
	 * Removes a {@link GameObject GameObject} from the scene,
	 * detaching it from the scene
	 *
	 * If the scene is active, the object's {@link GameObject#stop() stop} method
	 * is called.
	 * @param object
	 */
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

	/**
	 * Checks if a {@link GameObject GameObject} is in the scene.
	 *
	 * @param obj the object to check if is contained in the scene
	 * @return true if the object is contained in the scene
	 */
	public boolean contains(GameObject obj)
	{
		return objects.containsKey(obj.id());
	}

	/**
	 * The ammount of {@link GameObject GameObjects} in the scene
	 * @return the ammount of {@link GameObject GameObjects} in the scene
	 */
	public int size()
	{
		return objects.size();
	}

	@Override
	public Iterator<GameObject> iterator()
	{
		return new SceneIterator();
	}

	/**
	 * An iterable of all the {@link IInputListener input listeners} in the scene
	 * @return an iterable with all the {@link IInputListener input listeners} in the scene
	 */
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

	/**
	 * An iterable of all the {@link ICollider colliders} in the scene
	 * @return an iterable with all the {@link ICollider colliders} in the scene
	 */
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

	/**
	 * An array with all of the {@link ICollider colliders} in the scene
	 * @return an array with all of the {@link ICollider colliders} in the scene
	 */
	public ICollider[] collidersArr()
	{
		return this.colliders.values().toArray(new ICollider[0]);
	}

	/**
	 * An iterable of all the {@link IRenderable renderables} in the scene
	 * @return an iterable with all the {@link IRenderable renderables} in the scene
	 */
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

	/**
	 * An array with all of the {@link RenderData render data} in the scene
	 * @return an array with all of the {@link RenderData render data} in the scene
	 */
	public RenderData<?>[] renderablesArr()
	{
		RenderData<?>[] result = new RenderData[this.renderables.size()];
		int i = 0;
		for (IRenderable r : renderables())
			result[i++] = r.getRenderData();
		return result;
	}

	/**
	 * The scene's overlay
	 * @return the scene's overlay
	 */
	public IOverlay getOverlay()
	{
		return this.overlay;
	}

	/**
	 * if the scene is active
	 * @return if the scene is active
	 */
	public boolean isActive() { return this.isActive; }

	/**
	 * sets the scene's active state
	 * this method should only be called by the {@link GameEngine GameEngine}
	 *
	 * @param value the value to set the scene's active state to
	 */
	protected void setActive(boolean value) { this.isActive = value; }
}
