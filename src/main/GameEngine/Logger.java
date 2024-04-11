package GameEngine;

public class Logger
{
	public static enum Level
	{
		DEBUG,
		INFO,
		WARN,
		ERROR,
		FATAL,
	};
	private static Level logLevel;

	public static void startLogging(Level level)
	{
		logLevel = level;
	}

	public static void stopLogging()
	{
		logLevel = null;
	}

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
