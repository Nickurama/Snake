package GameEngineTests;

import GameEngine.*;

public class MockGameObject extends GameObject
{
	public enum Operation {
		NONE,
		STARTED,
		STOPPED,
		UPDATED,
	}
	private int number;
	private Operation lastOperation;
	private int lastDelta;

	private boolean hasStarted;
	private boolean hasStopped;
	private int updateCount;

	public MockGameObject(int startNumber)
	{
		number = startNumber;
		hasStarted = false;
		hasStopped = false;
		updateCount = 0;
		lastOperation = Operation.NONE;
	}

	public MockGameObject()
	{
		this(0);
	}

	public void update(int deltaT)
	{
		lastDelta = deltaT;
		updateCount++;
		lastOperation = Operation.UPDATED;
	}

	public void start()
	{
		hasStarted = true;
		lastOperation = Operation.STARTED;
	}

	public void stop()
	{
		hasStopped = true;
		lastOperation = Operation.STOPPED;
	}

	public int getNumber() { return number; }

	public Operation lastOperation() { return lastOperation; }

	public int getLastDelta() { return lastDelta; }

	public boolean hasStarted() { return hasStarted; }
	
	public boolean hasStopped() { return hasStopped; }

	public int updateCount() { return updateCount; }
}
