package GameEngine;

import java.util.ArrayList;

/**
 * A clock which may be started to periodically
 * call all it's listeners.
 * (Blocks the current thread, works synchronously)
 * 
 * @author Diogo Fonseca a79858
 * @version 18/05/2024
 */
public class Clock
{
	private ArrayList<IClockListener> listeners;
	private float ticksPerSecond;
	private boolean isRunning;
	private long currTickMs;
	private long lastDeltaMs;

	/**
	 * Instantiates a clock
	 * @param ticksPerSecond the maximum ammount of ticks per second
	 */
	public Clock(float ticksPerSecond)
	{
		this.ticksPerSecond = ticksPerSecond;
		this.isRunning = false;
		this.currTickMs = System.currentTimeMillis();
		this.listeners = new ArrayList<IClockListener>();
	}

	/**
	 * Starts the clock.
	 * The clock will call all listeners on each tick
	 */
	public void start()
	{
		this.currTickMs = System.currentTimeMillis();
		this.isRunning = true;
		startTicking();
	}

	/**
	 * Starts ticking.
	 */
	private void startTicking()
	{
		while(this.isRunning)
		{
			tick();
			sleep();
		}
	}

	/**
	 * Sleeps for the remaining time
	 */
	private void sleep()
	{
		long maxDeltaMs = Math.round((1f / this.ticksPerSecond) * 1000f);
		long sleepTime = maxDeltaMs - this.lastDeltaMs;
		if (sleepTime > 0)
			sleep(sleepTime);
	}

	/**
	 * Sleeps for an ammount of time
	 * @param sleepTime the ammount of time to sleep in milliseconds
	 */
	private void sleep(long sleepTime)
	{
		try
		{
			Thread.sleep(sleepTime);
			this.currTickMs += sleepTime;
		}
		catch (InterruptedException e)
		{
			Logger.log(Logger.Level.WARN, "Clock sleep was interrupted.");
		}
	}

	/**
	 * Calls the tick event with the calculated deltaT
	 */
	public void tick()
	{
		tick(getDeltaT());
	}

	/**
	 * Calls the tick event with a set deltaT
	 * @param timeElapsedMs the time elapsed since the last tick
	 */
	public void tick(long timeElapsedMs)
	{
		for (IClockListener listener : listeners)
			listener.tick(timeElapsedMs);
	}

	/**
	 * Time elapsed since the last call of this method.
	 * @return the time elapsed since the last call of this method.
	 * @pos updates currTickMs
	 * @pos updates lastDeltaMs
	 */
	public long getDeltaT()
	{
		long lastTickMs = this.currTickMs;
		this.currTickMs = System.currentTimeMillis();
		this.lastDeltaMs = this.currTickMs - lastTickMs;
		return this.lastDeltaMs;
	}

	/**
	 * Stops the clock.
	 */
	public void stop()
	{
		this.isRunning = false;
	}

	/**
	 * Adds a listener for the tick event
	 * @param listener the listener for the tick event
	 */
	public void addListener(IClockListener listener)
	{
		listeners.add(listener);
	}
}
