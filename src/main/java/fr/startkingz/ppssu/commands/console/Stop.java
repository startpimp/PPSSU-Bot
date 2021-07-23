package fr.startkingz.ppssu.commands.console;

import fr.startkingz.ppssu.events.OnMessageInConsoleEvent;
import fr.startkingz.ppssu.managers.ThreadsManager;

public class Stop extends ConsoleCommand
{
	@Override
	public void call(OnMessageInConsoleEvent event)
	{
		System.out.println("[Stop] Stopping all systems...");
		ThreadsManager.stopAll();
		System.out.println("[Stop] All systems has stopped!");
	}
}
