package fr.startkingz.ppssu.systems.sub.cache;

import java.util.concurrent.ConcurrentHashMap;

public abstract class CacheHandler<T>
{

	protected final ConcurrentHashMap<String, T> MAP = new ConcurrentHashMap<>();

	public final T get(final String key)
	{
		return MAP.get(key);
	}

	public final void add(final String key, final T value)
	{
		MAP.put(key, value);
	}
	
	public abstract void save();

}
