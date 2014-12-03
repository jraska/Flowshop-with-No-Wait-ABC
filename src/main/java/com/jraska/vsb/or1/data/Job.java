package com.jraska.vsb.or1.data;

import com.jraska.common.ArgumentCheck;

import java.util.Arrays;

public final class Job
{
	//region Fields

	private final String mName;
	private final int[] mDurations;

	//endregion

	//region Constructors

	public Job(String name, int[] durations)
	{
		ArgumentCheck.notNull(name);
		ArgumentCheck.notNull(durations);

		mName = name;
		mDurations = Arrays.copyOf(durations, durations.length);
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

	//endregion

	//region Object impl

	@Override
	public String toString()
	{
		return "Job" + mName + ", Durations=" + Arrays.toString(mDurations);
	}

	//endregion
}
