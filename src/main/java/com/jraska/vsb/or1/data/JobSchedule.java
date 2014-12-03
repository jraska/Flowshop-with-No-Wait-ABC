package com.jraska.vsb.or1.data;

import com.jraska.common.ArgumentCheck;

import java.util.Arrays;

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

	public static int[] countEndingTimes(int[] durations, int startingTime)
	{
		int[] times = countStartingTimes(durations, startingTime);

		for (int i = 0; i < durations.length; i++)
		{
			times[i] += durations[i];
		}

		return times;
	}

	public static int[] countStartingTimes(int[] durations, final int start)
	{
		int[] startingTimes = new int[durations.length];

		int time = start;
		startingTimes[0] = start;
		for (int i = 1; i < durations.length; i++)
		{
			time += durations[i - 1];
			startingTimes[i] = time;
		}

		return startingTimes;
	}

	public int getStartingTime()
	{
		return mStartingTime;
	}

	public int[] getStartingTimes()
	{
		return countStartingTimes(mJob.getDurations(), mStartingTime);
	}

	//endregion

	//region Methods

	public int[] getEndingTimes()
	{
		return countEndingTimes(mJob.getDurations(), mStartingTime);
	}

	public Interval[] getJobIntervals()
	{
		int[] startingTimes = getStartingTimes();
		int[] endingTimes = getEndingTimes();

		Interval[] intervals = new Interval[startingTimes.length];
		for (int i = 0; i < startingTimes.length; i++)
		{
			intervals[i] = new Interval(startingTimes[i], endingTimes[i]);
		}

		return intervals;
	}

	//endregion

	//region Object impl

	@Override
	public String toString()
	{
		return "Schedule for job " + mJob.getName() + "Running intervals: " + Arrays.toString(getJobIntervals());
	}

	//endregion
}
