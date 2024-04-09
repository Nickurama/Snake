package GameEngine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.naming.OperationNotSupportedException;

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

		if (this.flags.updateMethod() == GameEngineFlags.UpdateMethod.STEP)
			updateStepped();
		else if (this.flags.updateMethod() == GameEngineFlags.UpdateMethod.AUTO)
		{
			// TODO: implement
		}

		for (IGameObject obj : currScene)
			obj.onStart();
	}

	private void updateStepped()
	{
		long deltaT;
		while(this.isRunning)
		{
			readStepped();
			deltaT = updateTime();
			update(deltaT);
		}
	}

	private long updateTime()
	{
		lastFrameMillis = currentFrameMillis;
		currentFrameMillis = System.currentTimeMillis();
		return currentFrameMillis - lastFrameMillis;
	}

	public void step()
	{
		if (flags.updateMethod() != GameEngineFlags.UpdateMethod.CODE)
			return;
		if (!isRunning)
			return;

		long deltaT = updateTime();
		update(deltaT);
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

	private void update(long deltaT)
	{
		for (IGameObject obj : currScene)
			obj.onUpdate((int)deltaT);
	}
	
}
