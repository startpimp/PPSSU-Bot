package fr.startkingz.ppssu.systems;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Base64;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;

import fr.startkingz.ppssu.systems.sub.cache.CacheCollector;
import fr.startkingz.ppssu.systems.sub.cache.CacheHandler;

public final class Database extends CacheHandler<ConcurrentHashMap<String, String>>
{
	private final Pattern	PATTERN;
	private final File		FILE;
	private final Object[]	FIELDS;

	public Database(final File file, final Object... fieldsName)
	{
		try
		{
			FileUtils.touch(file);
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		final StringBuilder FINAL_REGEX = new StringBuilder("(?<key>.+)=\\{");

		for (final Object FIELD_NAME : fieldsName)
		{
			FINAL_REGEX.append("(?<").append(FIELD_NAME.toString()).append(">.+),");
		}
		FINAL_REGEX.append("\\}");

		PATTERN	= Pattern.compile(FINAL_REGEX.toString(), Pattern.MULTILINE);
		FILE	= file;
		FIELDS	= fieldsName;

		System.out.printf("[Database] New database loaded! (%s)\n", file.getAbsolutePath());
	}

	public void addToCache(final String cacheName)
	{
		CacheCollector.add(cacheName, this);
	}

	public ConcurrentHashMap<String, String> getFromFile(final String key)
	{
		final ConcurrentHashMap<String, String>	RESULT	= new ConcurrentHashMap<>();
		boolean									found	= false;
		try (final Scanner SCANNER = new Scanner(new FileInputStream(FILE)))
		{
			first: while (SCANNER.hasNext())
			{
				final String	LINE			= SCANNER.nextLine();
				final String	DECODED_LINE	= new String(Base64.getDecoder().decode(LINE));
				final Matcher	MATCHER			= PATTERN.matcher(DECODED_LINE);

				while (MATCHER.find())
				{
					if (MATCHER.group("key").equals(key))
					{
						for (final Object FIELD : FIELDS)
						{
							RESULT.put(FIELD.toString(), MATCHER.group(FIELD.toString()));
						}
						MAP.put(key, RESULT);
						found = true;
						break first;
					}
				}
			}
			if (!found)
			{
				for (final Object FIELD : FIELDS)
				{
					RESULT.put(FIELD.toString(), "null");
				}
				MAP.put(key, RESULT);
				System.out.println("[Database] " + key + " created in \"" + FILE.getAbsolutePath() + "\".");
			}
		} catch (final Exception e)
		{
			e.printStackTrace();
		}
		return RESULT;
	}

	public ConcurrentHashMap<String, String> getValue(final String key)
	{
		if (MAP.containsKey(key)) return this.get(key);
		else return getFromFile(key);
	}

	@Override
	public void save()
	{
		final ConcurrentHashMap<String, String> LINES = new ConcurrentHashMap<>();

		try (final Scanner SCANNER = new Scanner(new FileInputStream(FILE)))
		{
			while (SCANNER.hasNext())
			{
				final String	LINE			= SCANNER.nextLine();
				final String	DECODED_LINE	= new String(Base64.getDecoder().decode(LINE));
				final Matcher	MATCHER			= PATTERN.matcher(DECODED_LINE);

				while (MATCHER.find())
				{
					LINES.put(MATCHER.group("key"), LINE);
				}
			}
		} catch (final Exception e)
		{
			e.printStackTrace();
		}

		final StringBuilder TO_WRITE = new StringBuilder();

		LINES.forEach((lineKey, lineValue) ->
		{
			if (!MAP.containsKey(lineKey)) TO_WRITE.append(lineValue).append('\n');
		});

		MAP.forEach((mapKey, mapValue) ->
		{
			final StringBuilder LINE = new StringBuilder(mapKey).append("={");
			mapValue.forEach((fieldKey, fieldValue) -> LINE.append(fieldValue).append(','));
			LINE.append('}');
			TO_WRITE.append(Base64.getEncoder().encodeToString(LINE.toString().getBytes())).append('\n');
		});

		try (final PrintWriter PW = new PrintWriter(new FileOutputStream(FILE)))
		{
			PW.print(TO_WRITE.toString());
		} catch (final Exception e)
		{
			e.printStackTrace();
		}
	}

}
