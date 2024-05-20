package GameEngine;

/**
 * Logger class for the game engine
 * 
 * @author Diogo Fonseca a79858
 * @version 06/05/2024
 */
public class Logger
{
	/**
	 * The level of logging.
	*/
	public static enum Level
	{
		DEBUG,
		INFO,
		WARN,
		ERROR,
		FATAL,
	};

	private static Level logLevel;

	/**
	 * Starts logging any logs above the specified level
	 * @param level the floor level to print logs
	 */
	public static void startLogging(Level level)
	{
		logLevel = level;
	}

	/**
	 * Stops logging
	 */
	public static void stopLogging()
	{
		logLevel = null;
	}

	/**
	 * Logs a message if the current level is lower or equal to the
	 * log's level
	 * @param level the level of the log
	 * @param message the message to log
	 */
	public static void log(Level level, String message)
	{
		if (logLevel == null)
			return;
		if (level.ordinal() < logLevel.ordinal())
			return;

		StringBuilder builder = new StringBuilder();
		builder.append("[");
		builder.append(level.toString());
		builder.append("] ");
		builder.append(message);

		System.out.println(builder.toString());
	}
}
