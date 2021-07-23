package fr.startkingz.ppssu.commands.console;

import fr.startkingz.ppssu.events.OnMessageInConsoleEvent;
import fr.startkingz.ppssu.systems.CommandScheme;

public abstract class ConsoleCommand extends CommandScheme<OnMessageInConsoleEvent>
{
	public ConsoleCommand()
	{
		super(CommandType.CONSOLE);
	}
}
