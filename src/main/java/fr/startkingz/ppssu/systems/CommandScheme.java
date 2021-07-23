package fr.startkingz.ppssu.systems;

import fr.startkingz.ppssu.events.OnMessageInConsoleEvent;
import fr.startkingz.ppssu.managers.CommandsManager;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public abstract class CommandScheme<T>
{
	@SuppressWarnings("unchecked")
	public CommandScheme(final CommandType type)
	{
		final String	CANONICAL_NAME	= this.getClass().getCanonicalName();
		final String	CLASS_NAME		= CANONICAL_NAME.split("\\.")[CANONICAL_NAME.split("\\.").length - 1];

		if (type == CommandType.CONSOLE) CommandsManager.CONSOLE_COMMANDS.add((CommandScheme<OnMessageInConsoleEvent>) this);
		else if (type == CommandType.INTERFACE) CommandsManager.INTERFACE_COMMANDS.add((CommandScheme<MessageReceivedEvent>) this);
		else
		{
			System.err.printf("[Command-%s] Command class cannot have an unknown type!\n", CLASS_NAME);
			return;
		}

		System.out.printf("[Command-%s] Command loaded!\n", CLASS_NAME);
	}

	public abstract void call(final T event);

	public enum CommandType
	{
		CONSOLE, INTERFACE
	}
}
