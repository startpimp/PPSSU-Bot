package fr.startkingz.ppssu.systems.sub;

import java.util.Scanner;

import fr.startkingz.ppssu.events.OnMessageInConsoleEvent;
import fr.startkingz.ppssu.managers.CommandsManager;
import fr.startkingz.ppssu.systems.CommandScheme;
import fr.startkingz.ppssu.systems.Looper;

public class Console extends Looper
{
	final Scanner CONSOLE_SCANNER = new Scanner(System.in);

	public Console()
	{
		super("Console", null);

		start(() ->
		{
			final String	LINE				= CONSOLE_SCANNER.nextLine();
			final String	COMMAND_FROM_LINE	= LINE.split(" ")[0];

			for (final CommandScheme<OnMessageInConsoleEvent> COMMAND : CommandsManager.CONSOLE_COMMANDS)
			{
				final String	CANONICAL_NAME	= COMMAND.getClass().getCanonicalName();
				final String	COMMAND_NAME	= CANONICAL_NAME.split("\\.")[CANONICAL_NAME.split("\\.").length - 1];

				if (COMMAND_NAME.equalsIgnoreCase(COMMAND_FROM_LINE))
				{
					COMMAND.call(new OnMessageInConsoleEvent(LINE));
					break;
				}
			}
		});
	}

	@Override
	public void onStop()
	{
		CONSOLE_SCANNER.close();
	}

}
