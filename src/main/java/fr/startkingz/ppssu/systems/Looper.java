package fr.startkingz.ppssu.systems;

import java.util.concurrent.atomic.AtomicBoolean;

import fr.startkingz.ppssu.managers.ThreadsManager;

public abstract class Looper
{
	private final String		NAME;
	private Lambda				lambda;
	private final AtomicBoolean	RUNNING	= new AtomicBoolean(false);

	public Looper(final String name, final Lambda lambda)
	{
		NAME		= name;
		this.lambda	= lambda;
		ThreadsManager.THREADS.add(this);
	}

	public void start()
	{
		start(null);
	}

	public void start(final Lambda lambda)
	{
		if (this.lambda == null)
		{
			if (lambda == null) System.err.printf("[Looper-%s] Looper must have a lambda function\n", NAME);
			else this.lambda = lambda;
		}

		RUNNING.set(true);

		new Thread(() ->
		{
			while (RUNNING.get())
			{
				this.lambda.execute();
			}
		}, NAME).start();

		System.out.printf("[Looper-%s] Loop started\n", NAME);
	}

	public void stop()
	{
		RUNNING.set(false);
		onStop();
		System.out.printf("[Looper-%s] Loop ended correctly\n", NAME);
	}

	public void onStop()
	{}

	@FunctionalInterface
	public interface Lambda
	{
		void execute();
	}
}
