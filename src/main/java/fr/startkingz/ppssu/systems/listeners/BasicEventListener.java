package fr.startkingz.ppssu.systems.listeners;

import net.dv8tion.jda.api.events.DisconnectEvent;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.ShutdownEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import org.jetbrains.annotations.NotNull;

public class BasicEventListener implements EventListener
{

	@Override
	public void onEvent(@NotNull final GenericEvent event)
	{
		if (event instanceof DisconnectEvent) onDisconnect((DisconnectEvent) event);
		if (event instanceof ShutdownEvent) onShutdown((ShutdownEvent) event);
		if (event instanceof ReadyEvent) onReady();

	}

	private void onReady()
	{
		System.out.println("[BasicEventListener] The bot has been started!");
	}

	private void onShutdown(ShutdownEvent event)
	{
		onClosed(event.getCloseCode() != null ? event.getCloseCode().getMeaning() : "");
	}

	private void onDisconnect(DisconnectEvent event)
	{
		onClosed(event.getCloseCode() != null ? event.getCloseCode().getMeaning() : "");
	}

	private void onClosed(final String meaning)
	{
		System.out.printf("[BasicEventListener] The bot has been stopped (%s)\n", meaning);
	}

}
