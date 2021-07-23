package fr.startkingz.ppssu.commands.console;

import fr.startkingz.ppssu.events.OnMessageInConsoleEvent;
import fr.startkingz.ppssu.systems.sub.cache.CacheCollector;

public class Save extends ConsoleCommand
{
	@Override
	public void call(OnMessageInConsoleEvent event)
	{
		CacheCollector.save();
	}
}
