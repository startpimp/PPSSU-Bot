package fr.startkingz.ppssu.commands.ui;

import fr.startkingz.ppssu.systems.Sender;
import fr.startkingz.ppssu.systems.listeners.MessageEventListener;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Ping extends InterfaceCommand
{

	@Override
	public void call(MessageReceivedEvent event)
	{
		final long FIRST_TIME = System.currentTimeMillis();
		Sender.Normal.send(event.getTextChannel(), "Pong!").queue(message ->
		{
			final long PASSED_TIME = System.currentTimeMillis() - FIRST_TIME;
			Sender.Normal.edit(message, MessageEventListener.properUserLanguage.get("command.interface.ping.result", new String[]
			{ "NUMBER=" + PASSED_TIME })).queue();
		});
	}

}
