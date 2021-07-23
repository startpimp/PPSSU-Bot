package fr.startkingz.ppssu.systems;

import java.io.File;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.startkingz.ppssu.utils.Config;
import fr.startkingz.ppssu.utils.StringUtil;
import fr.startkingz.ppssu.utils.json.parser.JSONParser;

public class Language
{
	final HashMap<String, String> VARS = new HashMap<>();

	@SuppressWarnings("unchecked")
	public Language(final String code)
	{
		VARS.putAll(new JSONParser().parse(new File(Config.get("languagesPath").toString() + code + ".json")));
	}

	public String get(final String key, final String... replaces)
	{
		String result;

		if (!VARS.containsKey(key)) return "null";

		result = VARS.get(key);

		for (final String REPLACER : replaces)
		{
			final String	VAR_NAME	= REPLACER.replaceFirst("=.+", "");
			final String	VAR_CONTENT	= REPLACER.replaceFirst(".+=", "");
			String			varType		= REPLACER.replaceFirst(".+\\?", "");
			try
			{
				final double NUMBER = Integer.parseInt(VAR_CONTENT);
				if (NUMBER < 2.0) varType = "sg";
				else if (NUMBER >= 2.0) varType = "pl";
			} catch (final Exception ignored)
			{}


			result = result.replaceAll("\\$\\[" + VAR_NAME + "]", VAR_CONTENT);

			final String	VAR_REGEX	= "\\$\\{" + VAR_NAME + "}\\((.+?)\\)";
			final Pattern	PATTERN		= Pattern.compile(VAR_REGEX, Pattern.DOTALL);
			final Matcher	MATCHER		= PATTERN.matcher(result);

			while (MATCHER.find())
			{
				final String[] TYPE_REPLACES = MATCHER.group(1).split("\\|");
				for (final String TYPE_REPLACER : TYPE_REPLACES)
				{
					final String[] DECLARATIONS = TYPE_REPLACER.split("=");
					if (DECLARATIONS[0].equalsIgnoreCase(varType)) result = result.replaceFirst(StringUtil.regexEscapeChars(MATCHER.group(0), "${}()|"), DECLARATIONS[1]);
				}
			}

		}

		return result;
	}
}
