package fr.startkingz.ppssu.systems.sub.cache;

import java.util.concurrent.ConcurrentHashMap;

import fr.startkingz.ppssu.systems.Looper;

@SuppressWarnings("rawtypes")
public final class CacheCollector extends Looper
{
	private static long											lastTime	= System.currentTimeMillis();
	public static final ConcurrentHashMap<String, CacheHandler>	CACHES		= new ConcurrentHashMap<>();

	public CacheCollector(final long loopTime)
	{
		super("CacheCollector", () ->
		{
			final long CURRENT_TIME = System.currentTimeMillis();

			if (CURRENT_TIME - loopTime >= lastTime)
			{
				save();
				lastTime = CURRENT_TIME;
			}
		});
		
		start();

		System.out.println("[CacheCollector] Cache Collector started!");
	}

	public static void add(final String name, final CacheHandler cache)
	{
		CACHES.put(name, cache);
		System.out.println("[CacheCollector] " + name + " added!");
	}

	public static void save()
	{
		CACHES.forEach((name, cache) ->
		{
			cache.save();
			cache.MAP.clear();
		});
		System.out.println("[CacheCollector] Cache saved!");
	}

	@Override
	public void onStop()
	{
		save();
		System.out.println("[CacheCollector] Cache system stopped!");
	}

}
