package fr.startkingz.ppssu.managers;

import java.util.concurrent.CopyOnWriteArrayList;

import fr.startkingz.ppssu.systems.Looper;
import fr.startkingz.ppssu.utils.Config;

public class ThreadsManager
{
	public static final CopyOnWriteArrayList<Looper> THREADS = new CopyOnWriteArrayList<>();

	public static void stopAll()
	{
		if (Config.jda != null) Config.jda.shutdownNow();
		ThreadsManager.THREADS.forEach(Looper::stop);
	}
}
