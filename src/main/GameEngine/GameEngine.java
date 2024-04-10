package GameEngine;

import java.util.Scanner;

public class GameEngine
{
	private GameEngineFlags flags;
	private Scene currScene;
	private boolean isRunning;
	private long lastFrameMillis;
	private long currentFrameMillis;

	public GameEngine(GameEngineFlags flags, Scene scene)
	{
		this.flags = new GameEngineFlags(flags);
		this.currScene = scene;
		this.isRunning = false;
		this.lastFrameMillis = System.currentTimeMillis();
		this.currentFrameMillis = System.currentTimeMillis();
	}

	public void start()
	{
		if (this.isRunning)
			return;

		this.isRunning = true;
		currScene.setActive(true);

		for (GameObject obj : currScene)
			obj.start();

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
		if (command == null || command.isEmpty())
		{
			System.out.println("Command cannot be null.");
			return;
		}

		switch (command)
		{
			case "step":
				System.out.println("Stepping...");
				update();
				break;
			case "stop":
				System.out.println("Stopping...");
				stop();
				break;
			default:
				for (IInputListener listener : currScene.inputListeners())
					listener.onInputReceived(command);
				break;
		}
	}

	public void step() throws GameEngineException
	{
		if (flags.updateMethod() != GameEngineFlags.UpdateMethod.CODE)
			throw new GameEngineException("Called GameEngine.update() when update method isn't through code.");
		if (!isRunning)
			return;

		update();
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
			obj.update((int)deltaT);
	}
}
