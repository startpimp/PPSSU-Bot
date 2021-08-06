package fr.startkingz.ppssu.systems;

import java.math.BigInteger;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.requests.restaction.MessageAction;

/**
 * COPYRIGHT - ALL RIGHTS RESERVED - PPSSU Discord Bot.<br>
 * This file, like the entire project, has been sold and may not be copied. Any
 * copy will be punishable under the present copyright.<br>
 * <br>
 * ––––––––––––––––––––––––––––––––––––––––––––––––––––––––––<br>
 * <br>
 * 
 * @author  StartKingz (スタートキングズ) (408271124825243659)
 *
 * @version 0.1
 *
 * @since   0.1
 */
public class Sender
{

	public static class Normal
	{
		public static MessageAction send(final TextChannel channel, final String content)
		{
			final String BVC = generateBVC(content, channel.getId(), false);
			final EmbedBuilder BUILDER = new EmbedBuilder();
			BUILDER.setAuthor(BVC);
			BUILDER.setDescription(content);
			return channel.sendMessageEmbeds(BUILDER.build());
		}

		public static MessageAction edit(final Message message, final String content)
		{
			final String BVC = generateBVC(content, message.getTextChannel().getId(), true);
			return message.editMessage(content + "\n||" + BVC + "||");
		}

		private static final String		REGEX_BVC	= "(.*)\n\\|\\|([A-Za-z0-9+/=]+)\\|\\|$";
		private static final Pattern	PATTERN		= Pattern.compile(REGEX_BVC, Pattern.DOTALL);

		public static void verify(final Message message)
		{
			final Matcher	MATCHER	= PATTERN.matcher(message.getContentRaw());
			final String	OLD_BVC	= MATCHER.replaceFirst("$2");
			final String	CONTENT	= MATCHER.replaceFirst("$1");
			final String	NEW_BVC	= generateBVC(CONTENT, message.getTextChannel().getId(), message.isEdited());

			if (!NEW_BVC.equals(OLD_BVC))
			{
				message.delete().complete();
				System.err.println("[Sender.Normal] BVC isn't correct (" + OLD_BVC + "=>" + NEW_BVC + ')');
			}
		}
	}

	public static String generateBVC(final String content, final String id, final boolean modified)
	{
		// invisible lines
	}
}
