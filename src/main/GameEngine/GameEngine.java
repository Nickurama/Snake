package GameEngine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class GameEngine
{
	private GameEngineFlags flags;
	private Scene currScene;
	private boolean isRunning;
	private long lastFrameMillis;
	private long currentFrameMillis;

	public GameEngine(GameEngineFlags flags, Scene scene)
	{
		this.flags = flags;
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

		switch (this.flags.updateMethod())
		{
			case GameEngineFlags.UpdateMethod.STEP:
				updateStepped();
				break;
			case GameEngineFlags.UpdateMethod.CODE:
				// TODO: remove
				break;
			case GameEngineFlags.UpdateMethod.AUTO:
				// TODO: implement
				break;
		}

		for (GameObject obj : currScene)
			obj.start();
	}

	private void updateStepped()
	{
		long deltaT;
		while(this.isRunning)
		{
			readStepped();
			deltaT = getDeltaT();
			update(deltaT);
		}
	}

	private long getDeltaT()
	{
		lastFrameMillis = currentFrameMillis;
		currentFrameMillis = System.currentTimeMillis();
		return currentFrameMillis - lastFrameMillis;
	}

	private void readStepped()
	{
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		try
		{
			reader.readLine();
		}
		catch (IOException e)
		{
			//! LOG
		}
	}

	public void update() throws GameEngineException
	{
		if (flags.updateMethod() != GameEngineFlags.UpdateMethod.CODE)
			throw new GameEngineException("Called GameEngine.update() when update method isn't through code.");
		if (!isRunning)
			return;

		long deltaT = getDeltaT();
		update(deltaT);
	}

	private void update(long deltaT)
	{
		for (GameObject obj : currScene)
			obj.update((int)deltaT);
	}
}
