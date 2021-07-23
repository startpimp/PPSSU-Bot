package fr.startkingz.ppssu.managers;

import java.util.concurrent.CopyOnWriteArrayList;

import fr.startkingz.ppssu.events.OnMessageInConsoleEvent;
import fr.startkingz.ppssu.systems.CommandScheme;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class CommandsManager
{
	public static final CopyOnWriteArrayList<CommandScheme<MessageReceivedEvent>>		INTERFACE_COMMANDS	= new CopyOnWriteArrayList<>();
	public static final CopyOnWriteArrayList<CommandScheme<OnMessageInConsoleEvent>>	CONSOLE_COMMANDS	= new CopyOnWriteArrayList<>();
}
