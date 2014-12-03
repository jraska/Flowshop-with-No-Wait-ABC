package com.jraska.vsb.or1.data;

import com.jraska.common.ArgumentCheck;

public class Job
{
	//region Fields

	private final int[] mDurations;

	//endregion

	//region Constructors

	public Job(int[] durations)
	{
		ArgumentCheck.notNull(durations);

		mDurations = durations;
	}

	//endregion

	//region Properties

	public int[] getDurations()
	{
		return mDurations;
	}

	public int getDurationsCount()
	{
		return mDurations.length;
	}

	//endregion
}
