package fr.startkingz.ppssu.commands;

import fr.startkingz.ppssu.commands.console.Save;
import fr.startkingz.ppssu.commands.console.Stop;
import fr.startkingz.ppssu.commands.ui.Ping;

public class Initialize
{
	static
	{
		// Console
		new Stop();
		new Save();

		// Interface
		new Ping();
	}
}
