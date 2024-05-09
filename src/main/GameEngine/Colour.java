package GameEngine;

public enum Colour
{
	RESET
	{
		@Override
		public String toString() { return "\u001B[0m"; }
	};

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
