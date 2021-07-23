package fr.startkingz.ppssu.utils;

public final class StringUtil
{

	public static String regexEscapeChars(final String original, final String charsToEscape)
	{
		final StringBuilder RESULT = new StringBuilder();

		for (final char O_CHAR : original.toCharArray())
		{
			boolean founded = false;
			for (final char E_CHAR : charsToEscape.toCharArray())
			{
				if (O_CHAR == E_CHAR)
				{
					founded = true;
					break;
				}
			}
			if (founded) RESULT.append("\\").append(O_CHAR);
			else RESULT.append(O_CHAR);
		}

		return RESULT.toString();
	}
}
