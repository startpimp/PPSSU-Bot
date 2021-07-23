package fr.startkingz.ppssu.systems.listeners;

import java.util.concurrent.ConcurrentHashMap;

import fr.startkingz.ppssu.entities_enum.UserEntity;
import fr.startkingz.ppssu.managers.CommandsManager;
import fr.startkingz.ppssu.systems.CommandScheme;
import fr.startkingz.ppssu.systems.Language;
import fr.startkingz.ppssu.systems.Sender;
import fr.startkingz.ppssu.utils.Config;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import org.jetbrains.annotations.NotNull;

public class MessageEventListener implements EventListener
{

	public static ConcurrentHashMap<String, String>	properUser			= null;
	public static Language							properUserLanguage	= null;

	@Override
	public void onEvent(@NotNull final GenericEvent event)
	{
		if (event instanceof MessageReceivedEvent) onMessage((MessageReceivedEvent) event);
	}

	private void onMessage(MessageReceivedEvent event)
	{
		final User		AUTHOR	= event.getAuthor();
		final Message	MESSAGE	= event.getMessage();

		if (isSelf(AUTHOR))
		{
			if (MESSAGE.getEmbeds().isEmpty()) Sender.Normal.verify(MESSAGE);
		}

		if (!isSelf(AUTHOR)) checkUser(AUTHOR);

		final String CONTENT = MESSAGE.getContentRaw();

		if (CONTENT.startsWith(Config.get("prefix").toString()))
		{
			final String COMMAND_FROM_LINE = CONTENT.split(" ")[0].replaceFirst(Config.get("prefix").toString(), "");

			for (final CommandScheme<MessageReceivedEvent> COMMAND : CommandsManager.INTERFACE_COMMANDS)
			{
				final String	CANONICAL_NAME	= COMMAND.getClass().getCanonicalName();
				final String	COMMAND_NAME	= CANONICAL_NAME.split("\\.")[CANONICAL_NAME.split("\\.").length - 1];

				if (COMMAND_NAME.equalsIgnoreCase(COMMAND_FROM_LINE))
				{
					COMMAND.call(event);
					break;
				}
			}
		}
	}

	private void checkUser(final User user)
	{
		final ConcurrentHashMap<String, String> USER = Config.DATABASES.get("users").getValue(user.getId());
		if (USER.get("language").equals("null")) USER.put("language", "en");
		if (USER.get("type").equals("null")) USER.put("type", user.isBot() ? UserEntity.BOT.name() : UserEntity.BASIC.name());
		properUser			= USER;
		properUserLanguage	= new Language(properUser.get("language"));
	}

	private boolean isSelf(final User author)
	{
		return author.getId().equals(author.getJDA().getSelfUser().getId());
	}
}
