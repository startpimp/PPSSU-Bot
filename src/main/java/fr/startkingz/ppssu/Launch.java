package fr.startkingz.ppssu;

import javax.security.auth.login.LoginException;

import fr.startkingz.ppssu.commands.Initialize;
import fr.startkingz.ppssu.systems.listeners.BasicEventListener;
import fr.startkingz.ppssu.systems.listeners.MessageEventListener;
import fr.startkingz.ppssu.systems.sub.Console;
import fr.startkingz.ppssu.systems.sub.cache.CacheCollector;
import fr.startkingz.ppssu.utils.Config;
import net.dv8tion.jda.api.JDABuilder;

public class Launch
{
	public static void main(String[] args) throws LoginException
	{
		// Pre-operations
		Config.load();
		System.out.println("[Launch] Commands are loading...");
		new Initialize();
		System.out.println("[Launch] Commands have been loaded!");
		new Console();
		new CacheCollector(300000);

		// Bot
		System.out.println("[Launch] Bot is logging in...");
		final JDABuilder BUILDER = JDABuilder.createLight((String) Config.get("token"));
		BUILDER.setMaxReconnectDelay(32);
		BUILDER.addEventListeners(new BasicEventListener());
		BUILDER.addEventListeners(new MessageEventListener());
		Config.jda = BUILDER.build();

		System.out.println("[Launch] Bot is logged!");
	}
}
