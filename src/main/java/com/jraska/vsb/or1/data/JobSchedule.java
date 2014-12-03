package com.jraska.vsb.or1.data;

import com.jraska.common.ArgumentCheck;

public final class JobSchedule
{
	//region Fields

	private final Job mJob;
	private final int mStartingTime;

	//endregion

	//region Constructors

	public JobSchedule(Job job, int startingTime)
	{
		ArgumentCheck.notNull(job, "job");
		if (startingTime < 0)
		{
			throw new IllegalArgumentException(String.format("Starting time must be at least zero, not %d.", startingTime));
		}

		mJob = job;
		mStartingTime = startingTime;
	}

	//endregion

	//region Properties

	public Job getJob()
	{
		return mJob;
	}

	public int getStartingTime()
	{
		return mStartingTime;
	}

	//endregion
}
