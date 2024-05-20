package GameEngine;

/**
 * Represents a colour as an ANSI code string
 * 
 * @author Diogo Fonseca a79858
 * @version 08/05/2024
 */
public enum TerminalColour
{
	/**
	 * Represents a reset of background/foreground colour
	 */
	RESET
	{
		@Override
		public String toString() { return "\u001B[0m"; }
	};

	/**
	 * Represents a foreground colour as an ANSI code string
	 */
	public enum Foreground
	{
		BLACK
		{
			@Override
			public String toString() { return "\u001B[30m"; }
		},
		RED
		{
			@Override
			public String toString() { return "\u001B[31m"; }
		},
		GREEN
		{
			@Override
			public String toString() { return "\u001B[32m"; }
		},
		YELLOW
		{
			@Override
			public String toString() { return "\u001B[33m"; }
		},
		BLUE
		{
			@Override
			public String toString() { return "\u001B[34m"; }
		},
		MAGENTA
		{
			@Override
			public String toString() { return "\u001B[35m"; }
		},
		CYAN
		{
			@Override
			public String toString() { return "\u001B[36m"; }
		},
		WHITE
		{
			@Override
			public String toString() { return "\u001B[37m"; }
		},
	}

	/**
	 * Represents a background colour as an ANSI code string
	 */
	public enum Background
	{
		BLACK
		{
			@Override
			public String toString() { return "\u001B[40m"; }
		},
		RED
		{
			@Override
			public String toString() { return "\u001B[41m"; }
		},
		GREEN
		{
			@Override
			public String toString() { return "\u001B[42m"; }
		},
		YELLOW
		{
			@Override
			public String toString() { return "\u001B[43m"; }
		},
		BLUE
		{
			@Override
			public String toString() { return "\u001B[44m"; }
		},
		MAGENTA
		{
			@Override
			public String toString() { return "\u001B[45m"; }
		},
		CYAN
		{
			@Override
			public String toString() { return "\u001B[46m"; }
		},
		WHITE
		{
			@Override
			public String toString() { return "\u001B[47m"; }
		},
	}
}
