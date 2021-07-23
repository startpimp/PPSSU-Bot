package fr.startkingz.ppssu.commands.ui;

import fr.startkingz.ppssu.systems.CommandScheme;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public abstract class InterfaceCommand extends CommandScheme<MessageReceivedEvent>
{
	public InterfaceCommand()
	{
		super(CommandType.INTERFACE);
	}
}
