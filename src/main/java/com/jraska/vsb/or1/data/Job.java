package com.jraska.vsb.or1.data;

import com.jraska.common.ArgumentCheck;

import java.util.Arrays;

public final class Job
{
	//region Fields

	private final String mName;
	private final int[] mDurations;
	private final int mTotalDuration;

	//endregion

	//region Constructors

	public Job(String name, int[] durations)
	{
		ArgumentCheck.notNull(name);
		ArgumentCheck.notNull(durations);

		if (durations.length == 0)
		{
			throw new IllegalArgumentException("Job must have at least one operation.");
		}

		mName = name;
		mDurations = Arrays.copyOf(durations, durations.length);

		mTotalDuration = countTotalDuration(mDurations);
	}

	//endregion

	//region Properties

	public String getName()
	{
		return mName;
	}

	public int[] getDurations()
	{
		return Arrays.copyOf(mDurations, mDurations.length);
	}

	public int getDurationsCount()
	{
		return mDurations.length;
	}

	public int getTotalDuration()
	{
		return mTotalDuration;
	}

	//endregion

	//region Methods

	protected static int countTotalDuration(int[] durations)
	{
		int sum = 0;

		for (int duration : durations)
		{
			sum += duration;
		}

		return sum;
	}

	/**
	 * Calculates departing delay of provided job after this job
	 *
	 * @param next Job to continue after me
	 * @return Delay of next job in which can start
	 */
	public int getDepartureDelay(Job next)
	{
		int[] nextDurations = next.mDurations;
		final int length = nextDurations.length;
		int delay = mDurations[0]; //need to wait at start

		int prevLeaveTime = 0;
		int nextComeTime = 0;
		for (int i = 0, end = length - 1; i < end; i++)
		{
			prevLeaveTime += mDurations[i + 1];
			nextComeTime += nextDurations[i];

			if (nextComeTime < prevLeaveTime)
			{
				int diff = prevLeaveTime - nextComeTime;
				delay += diff;
				nextComeTime += diff;
			}
		}

		return delay;
	}

	//endregion

	//region Object impl

	@Override
	public String toString()
	{
		return "Job" + mName + ", Durations=" + Arrays.toString(mDurations);
	}

	//endregion
}
