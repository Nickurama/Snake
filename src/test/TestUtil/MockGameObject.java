package TestUtil;

import GameEngine.*;

public class MockGameObject extends GameObject implements IInputListener
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

	private String inputReceived;

	public MockGameObject(int startNumber)
	{
		this.number = startNumber;
		this.hasStarted = false;
		this.hasStopped = false;
		this.updateCount = 0;
		this.lastOperation = Operation.NONE;
		this.inputReceived = null;
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

	public void onInputReceived(String input)
	{
		this.inputReceived = input;
	}

	public int getNumber() { return number; }

	public Operation lastOperation() { return lastOperation; }

	public int getLastDelta() { return lastDelta; }

	public boolean hasStarted() { return hasStarted; }
	
	public boolean hasStopped() { return hasStopped; }

	public int updateCount() { return updateCount; }

	public String inputReceived() { return inputReceived; }
}
