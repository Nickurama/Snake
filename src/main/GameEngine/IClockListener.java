package GameEngine;

/**
 * Listener to a clock tick
 * 
 * @author Diogo Fonseca a79858
 * @version 18/05/2024
 *
 * @inv {@link IClockListener#tick(long) tick} is called whenever the clock ticks
 * @see Clock
 */
public interface IClockListener
{
	/**
	 * Tick event, providing the time elapsed since the last tick in ms
	 * @param timeElapsedMs the time elapsed since the alst tick in ms
	 */
	public void tick(long timeElapsedMs);
}
