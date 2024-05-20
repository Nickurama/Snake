package GameEngine;

import java.awt.event.KeyEvent;
import java.io.File;
import Geometry.Rectangle;

/**
 * Responsible for taking a scene and running the game contained within it.
 *
 * Upon calling the start method, this class will make sure all the GameObjects
 * contained within the scene fed into the class through the init method will
 * be rendered (if so desired), updated, collisions will be checked, and input
 * will be read. The configuration as to how the game engine will run is defined
 * in the GameEngineFlags class.
 *
 * Before the first update, the start method is called for every game object. (only once)
 *
 * The order of operations upon each update are as follow:
 * 0. input (if in stepped mode)
 * 1. early update
 * 2. update
 * 3. collision checking
 * 4. late update
 * 5. rendering (if provided a camera)
 *
 * When the cycle stops, the stop method is called for every game object. (only once)
 * 
 * @author Diogo Fonseca a79858
 * @version 08/05/2024
 *
 * @inv singleton
 * @inv isRunning is true when the engine is running and false otherwise
 * @see Scene
 * @see GameObject
 * @see Renderer
 * @see CollisionManager
 */
public class GameEngine implements IInputListener, IClockListener
{
	private final String STOP_CMD_STR = "stop";
	private final String DEBUG_CMD_STR = "debug";
	private final char BG_CHAR = ' ';
	private static GameEngine instance = null;
	private GameEngineFlags flags;
	private Scene currScene;
	private boolean isRunning;
	private Rectangle camera;
	private Clock clock;

	/**
	 * private constructor for singleton
	 */
	private GameEngine()
	{
	}

	/**
	 * Gets the game engine instance
	 * @return the game engine instance
	 */
	public static GameEngine getInstance()
	{
		if (instance == null)
			instance = new GameEngine();

		return instance;
	}

	/**
	 * Initializes the game engine. 
	 *
	 * if the engine was still running prior to the calling of this method,
	 * it will be ungracefully stopped, overwritting everything. The stop method
	 * on the game objects of the scene being overwritten will NOT be called
	 *
	 * @param flags the flags defining the behaviour of the game engine
	 * @param scene the scene to be ran by the game engine
	 * @param camera the section of space to be rendered (can be null)
	 */
	public void init(GameEngineFlags flags, Scene scene, Rectangle camera)
	{
		this.flags = new GameEngineFlags(flags);
		this.currScene = scene;
		this.isRunning = false;
		this.clock = new Clock(flags.maxUpdatesPerSecond());
		this.clock.addListener(this);
		this.camera = camera;
		Renderer.getInstance().setTextual(flags.isTextual());
		if (this.camera != null)
			Renderer.getInstance().init(this.camera, BG_CHAR);
	}
	
	/**
	 * Initializes the game engine without renderization.
	 * same as {@link GameEngine#init(GameEngineFlags,Scene,Rectangle)}
	 *
	 * @see GameEngine#init(GameEngineFlags,Scene,Rectangle)
	 */
	public void init(GameEngineFlags flags, Scene scene)
	{
		this.init(flags, scene, null);
	}

	/**
	 * Starts the game engine, starting the game engine lifecycle.
	 * Calls the start method of all the game objects in the scene and
	 * sets the current scene as active.
	 *
	 * @pre engine must have been initialized
	 * @throws RuntimeException if the engine is already running or
	 * if the engine has not been initialized yet
	 */
	public void start()
	{
		if (this.isRunning)
		{
			Logger.log(Logger.Level.FATAL, "Engine attempted to start while running.");
			throw new RuntimeException("Engine attempted to start while running.");
		}

		if (this.currScene == null)
		{
			Logger.log(Logger.Level.FATAL, "Engine was started with null scene. (not initialized)");
			throw new RuntimeException("Engine must be initialized before starting.");
		}

		this.isRunning = true;
		currScene.setActive(true);

		for (GameObject obj : currScene)
			obj.start();

		CollisionManager.detectCollisions(this.currScene);
		if (this.camera != null)
			Renderer.getInstance().render(this.currScene, this.camera, this.BG_CHAR);

		InputManager.getInstance().init(this.currScene);
		if (Renderer.getInstance().getGraphicWindow() != null)
		Renderer.getInstance().getGraphicWindow().addKeyListener(InputManager.getInstance());

		if (this.flags.updateMethod() == GameEngineFlags.UpdateMethod.STEP)
			InputManager.getInstance().captureInput();
		else if (this.flags.updateMethod() == GameEngineFlags.UpdateMethod.AUTO)
			this.clock.start();
	}

	/**
	 * Stops the game engine, stopping the engine lifecycle and the core loop.
	 * the current scene is set to inactive and calls the stop method of all
	 * the game objects within the current scene
	 *
	 * Does not throw exception if the engine is not running.
	 *
	 * @pre engine must be running
	 */
	public void stop()
	{
		if (!this.isRunning)
			return;

		System.out.println("Stopping...");

		this.clock.stop();

		this.isRunning = false;
		currScene.setActive(false);

		for (GameObject obj : currScene)
			obj.stop();

		Renderer.getInstance().closeGraphicWindow();
	}

	@Override
	public void onInputReceived(String input)
	{
		if (this.flags.updateMethod() == GameEngineFlags.UpdateMethod.STEP)
			update(1);
	}

	/**
	 * Called when received input. Runs before the other InputListeners.
	 * @param input the input received
	 */
	public void priorityInput(String input)
	{
		executeCommand(input);
	}

	@Override
	public void onKeyPressed(KeyEvent event)
	{
		if (event.getKeyCode() == KeyEvent.VK_C)
			stop();

		if (isNotModifierKey(event) && this.flags.updateMethod() == GameEngineFlags.UpdateMethod.STEP)
			update(1);
	}

	/**
	 * If the event doesn't describe a modifier key,
	 * such as ALT, CTRL, SHIFT...
	 * @param event the event to test for modifier keys
	 * @return true if the event doesn't describe a modifier key
	 */
	private boolean isNotModifierKey(KeyEvent event)
	{
		return event.getKeyCode() != KeyEvent.VK_ALT &&
			event.getKeyCode() != KeyEvent.VK_CONTROL &&
			event.getKeyCode() != KeyEvent.VK_SHIFT &&
			event.getKeyCode() != KeyEvent.VK_CAPS_LOCK &&
			event.getKeyCode() != KeyEvent.VK_ALT &&
			event.getKeyCode() != KeyEvent.VK_ALT_GRAPH;
	}

	@Override
	public void onKeyReleased(KeyEvent event) { }

	@Override
	public void onKeyTyped(KeyEvent event) { }

	/**
	 * Executes a command, distributing it through all the IInputListeners in the
	 * current scene.
	 *
	 * If the command is "stop", it will not be distributed, as that is the command
	 * used for stopping the game engine.
	 *
	 * @param command the command to execute across all IInputListeners
	 */
	private void executeCommand(String command)
	{
		switch (command)
		{
			case STOP_CMD_STR:
				stop();
				break;
			case DEBUG_CMD_STR:
				Logger.startLogging(Logger.Level.DEBUG);
				Logger.log(Logger.Level.INFO, "Started debugging.");
				break;
		}
	}

	/**
	 * Runs a game engine cycle.
	 * Should only be used when the update method is through code.
	 * @param deltaT the delta time in ms since last update
	 */
	public void step(long deltaT)
	{
		// if (flags.updateMethod() != GameEngineFlags.UpdateMethod.CODE)
		// 	throw new GameEngineException("Called GameEngine.update() when update method isn't through code.");
		if (!isRunning)
			return;

		update(deltaT);
	}

	/**
	 * Runs a game engine cycle.
	 * same as {@link GameEngine#step(long)} but gets the time elapsed since last update
	 *
	 * @see GameEngine#step(long)
	 */
	public void step()
	{
		step(this.clock.getDeltaT());
	}

	/**
	 * Sets a new scene. can be used with engine still running, replacing the current
	 * scene.
	 * From the GameObject's perspective it's as if the game engine has just started.
	 * @param newScene the scene to be set in the game engine
	 */
	public void setScene(Scene newScene)
	{
		// if (isRunning)
		// 	throw new GameEngineException("Can't set scene while engine is still running!");
		this.currScene.setActive(false);

		this.currScene = newScene;

		currScene.setActive(true);

		for (GameObject obj : newScene)
			obj.start();

		InputManager.getInstance().init(newScene);
	}

	@Override
	public void tick(long deltaTimeMs)
	{
		update(deltaTimeMs);
	}

	/**
	 * Runs an engine cycle. calling the update methods, checking for collisions
	 * and rendering a new frame.
	 *
	 * A typical update has the following structure:
	 * 1. early update
	 * 2. update
	 * 3. collision checking
	 * 4. late update
	 * 5. rendering (if provided a camera)
	 *
	 * @param deltaT the elapsed time since the last update in ms.
	 */
	private void update(long deltaT)
	{
		for (GameObject obj : currScene)
			obj.earlyUpdate();

		for (GameObject obj : currScene)
			obj.update((int)deltaT);

		CollisionManager.detectCollisions(this.currScene);

		for (GameObject obj : currScene)
			obj.lateUpdate();


		if (this.camera != null)
			Renderer.getInstance().render(this.currScene, this.camera, this.BG_CHAR);
	}

	/**
	 * Checks if the engine is running
	 * @return if the engine is running
	 */
	public boolean isRunning() { return this.isRunning; }

	/**
	 * The path to the project
	 * @return the path to the project
	 */
	public static String getProjectPath()
	{
		String starting = System.getProperty("user.dir");
        String platformIndependantSplitter = File.separator.replace("\\", "\\\\");
		String[] tokens = starting.split(platformIndependantSplitter);

		String currPath;
		for (int i = tokens.length - 1; i >= 0; i--)
		{
			StringBuilder builder = new StringBuilder();
			for (int j = 0; j <= i; j++)
			{
				builder.append(tokens[j]);
				builder.append(File.separator);
			}

			String result = builder.toString();
			builder.append("src");
			builder.append(File.separator);
			currPath = builder.toString();

			File f = new File(currPath);
			if (f.exists())
				return result;
		}
		return null;
	}
}
