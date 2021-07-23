package fr.startkingz.ppssu.utils;

import java.io.File;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

import fr.startkingz.ppssu.systems.Database;
import fr.startkingz.ppssu.utils.json.JSONArray;
import fr.startkingz.ppssu.utils.json.JSONObject;
import fr.startkingz.ppssu.utils.json.parser.JSONParser;
import net.dv8tion.jda.api.JDA;

public final class Config
{
	public static JDA										jda			= null;
	public static final ConcurrentHashMap<Object, Object>	CONFIGS		= new ConcurrentHashMap<>();
	public static final ConcurrentHashMap<String, Database>	DATABASES	= new ConcurrentHashMap<>();

	public static Object get(final Object o)
	{
		final AtomicReference<Object> RESULT = new AtomicReference<>();
		RESULT.set(null);
		CONFIGS.forEach((in, out) ->
		{
			if (in.toString().equals(o.toString())) RESULT.set(out);
		});
		if (RESULT.get() == null) System.err.println("[Config] The input \"" + o.toString() + "\" does not exist.");
		return RESULT.get();
	}

	@SuppressWarnings("unchecked")
	public static void load()
	{
		Config.CONFIGS.putAll(new JSONParser().parse(new File("./res/config.json")));

		final JSONObject DATABASES_JSON = (JSONObject) get("database");
		DATABASES_JSON.forEach((name, content) ->
		{
			DATABASES.put(name.toString(), new Database(new File(((JSONObject) content).get("path").toString()), ((JSONArray) ((JSONObject) content).get("args")).toArray()));

			final String DATABASE_NAME = String.valueOf(name.toString().charAt(0));

			DATABASES.get(name.toString()).addToCache(name.toString().replaceFirst(DATABASE_NAME, DATABASE_NAME.toUpperCase()));
		});

		System.out.println("[Config] config.json file loaded properly!");
	}
}
