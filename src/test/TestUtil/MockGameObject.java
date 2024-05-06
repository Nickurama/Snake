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
	protected int earlyUpdateCount;
	protected int updateCount;
	protected int lateUpdateCount;
	private boolean hasInitialized;

	private String inputReceived;

	public MockGameObject(int startNumber)
	{
		this.number = startNumber;
		this.hasStarted = false;
		this.hasStopped = false;
		this.earlyUpdateCount = 0;
		this.updateCount = 0;
		this.lateUpdateCount = 0;
		this.lastOperation = Operation.NONE;
		this.inputReceived = null;
		this.hasInitialized = false;
	}

	public MockGameObject()
	{
		this(0);
	}

	@Override
	public void update(int deltaT)
	{
		lastDelta = deltaT;
		updateCount++;
		lastOperation = Operation.UPDATED;
	}

	@Override
	public void start()
	{
		hasStarted = true;
		lastOperation = Operation.STARTED;
	}

	@Override
	public void stop()
	{
		hasStopped = true;
		lastOperation = Operation.STOPPED;
	}

	public void onInputReceived(String input)
	{
		this.inputReceived = input;
	}

	@Override
	public void lateUpdate()
	{
		this.lateUpdateCount++;
	}

	@Override
	public void earlyUpdate()
	{
		this.earlyUpdateCount++;
	}

	@Override
	public void initialize()
	{
		this.hasInitialized = true;
	}

	public int getNumber() { return number; }

	public Operation lastOperation() { return lastOperation; }

	public int getLastDelta() { return lastDelta; }

	public boolean hasStarted() { return hasStarted; }
	
	public boolean hasStopped() { return hasStopped; }

	public int updateCount() { return updateCount; }

	public String inputReceived() { return inputReceived; }

	public int lateUpdateCount() { return lateUpdateCount; }

	public int earlyUpdateCount() { return earlyUpdateCount; }

	public boolean hasInitialized() { return hasInitialized; }
}
