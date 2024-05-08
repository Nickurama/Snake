package GameEngine;

import java.util.Scanner;
import Geometry.Rectangle;

public class GameEngine
{
	private static GameEngine instance = null;
	private GameEngineFlags flags;
	private Scene currScene;
	private boolean isRunning;
	private long lastFrameMillis;
	private long currentFrameMillis;
	private Rectangle camera;
	private final char backgroundChar = ' ';

	private GameEngine()
	{
		// Singleton
	}

	public static GameEngine getInstance()
	{
		if (instance == null)
			instance = new GameEngine();

		return instance;
	}

	public void init(GameEngineFlags flags, Scene scene)
	{
		this.init(flags, scene, null);
	}

	public void init(GameEngineFlags flags, Scene scene, Rectangle camera)
	{
		this.flags = new GameEngineFlags(flags);
		this.currScene = scene;
		this.isRunning = false;
		this.lastFrameMillis = System.currentTimeMillis();
		this.currentFrameMillis = System.currentTimeMillis();
		this.camera = camera;
	}

	// must initialize engine first before using any method
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
			Renderer.getInstance().render(this.currScene, this.camera, this.backgroundChar);

		if (this.flags.updateMethod() == GameEngineFlags.UpdateMethod.STEP)
			updateStepped();
		else if (this.flags.updateMethod() == GameEngineFlags.UpdateMethod.AUTO)
		{
			// TODO: implement
		}
	}

	public void stop()
	{
		if (!this.isRunning)
			return;

		this.isRunning = false;
		currScene.setActive(false);

		for (GameObject obj : currScene)
			obj.stop();
	}

	private void updateStepped()
	{
		Scanner reader = new Scanner(System.in);
		while(this.isRunning)
		{
			String command = reader.nextLine();
			executeCommand(command);
		}
		reader.close();
	}

	private void executeCommand(String command)
	{
		// if (command == null || command.isEmpty())
		// {
		// 	System.out.println("Command cannot be null.");
		// 	return;
		// }

		switch (command)
		{
			case "":
			case "step":
				System.out.println("Stepping...");
				update(1);
				break;
			case "stop":
				System.out.println("Stopping...");
				stop();
				break;
			case "debug":
				Logger.startLogging(Logger.Level.DEBUG);
				Logger.log(Logger.Level.INFO, "Started debugging.");
				break;
			default:
				for (IInputListener listener : currScene.inputListeners())
					listener.onInputReceived(command);
				break;
		}
	}

	public void step() throws GameEngineException
	{
		step(getDeltaT());
	}

	public void step(long deltaT) throws GameEngineException
	{
		if (flags.updateMethod() != GameEngineFlags.UpdateMethod.CODE)
			throw new GameEngineException("Called GameEngine.update() when update method isn't through code.");
		if (!isRunning)
			return;

		update(deltaT);
	}

	public void setScene(Scene newScene) throws GameEngineException
	{
		// if (isRunning)
		// 	throw new GameEngineException("Can't set scene while engine is still running!");

		this.currScene = newScene;

		currScene.setActive(true);

		for (GameObject obj : newScene)
			obj.start();

	}

	private long getDeltaT()
	{
		lastFrameMillis = currentFrameMillis;
		currentFrameMillis = System.currentTimeMillis();
		return currentFrameMillis - lastFrameMillis;
	}

	private void update()
	{
		update(getDeltaT());
	}

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
			Renderer.getInstance().render(this.currScene, this.camera, this.backgroundChar);
	}

	public boolean isRunning() { return this.isRunning; }
}
