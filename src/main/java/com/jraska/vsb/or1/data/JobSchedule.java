package com.jraska.vsb.or1.data;

import com.jraska.common.ArgumentCheck;

import java.util.Arrays;

public final class JobSchedule
{
	//region Fields

	private final Job mJob;
	private final int mStartingTime;

	private final Interval[] mJobIntervals;

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

		mJobIntervals = countIntervals(mJob.getDurations(), startingTime);
	}

	//endregion

	//region Properties

	public int getStartingTime()
	{
		return mStartingTime;
	}

	public int[] getEndingTimes()
	{
		return countEndingTimes(mJob.getDurations(), mStartingTime);
	}

	public int[] getStartingTimes()
	{
		return countStartingTimes(mJob.getDurations(), mStartingTime);
	}

	public int getOperationsCount()
	{
		return mJob.getDurationsCount();
	}

	public Interval[] getJobIntervals()
	{
		return mJobIntervals;
	}

	public Interval intervalAt(int index)
	{
		return mJobIntervals[index];
	}

	//endregion

	//region Methods

	static Interval[] countIntervals(int[] durations, int start)
	{
		int[] startingTimes = countStartingTimes(durations, start);
		int[] endingTimes = countEndingTimes(durations, start);

		return countIntervals(startingTimes, endingTimes);
	}

	static Interval[] countIntervals(int[] startingTimes, int[] endingTimes)
	{
		Interval[] intervals = new Interval[startingTimes.length];
		for (int i = 0; i < startingTimes.length; i++)
		{
			intervals[i] = new Interval(startingTimes[i], endingTimes[i]);
		}

		return intervals;
	}

	static int[] countEndingTimes(int[] durations, int start)
	{
		int[] times = countStartingTimes(durations, start);

		for (int i = 0; i < durations.length; i++)
		{
			times[i] += durations[i];
		}

		return times;
	}

	static int[] countStartingTimes(int[] durations, final int start)
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

	//endregion

	//region Object impl

	@Override
	public String toString()
	{
		return "Schedule for job " + mJob.getName() + "Running intervals: " + Arrays.toString(getJobIntervals());
	}

	//endregion
}
