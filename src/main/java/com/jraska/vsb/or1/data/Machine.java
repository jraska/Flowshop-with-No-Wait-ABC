package com.jraska.vsb.or1.data;

import com.jraska.common.ArgumentCheck;

public class Machine
{
	//region Fields

	private final int[] mJobs;

	//endregion

	//region Constructors

	public Machine(int[] jobs)
	{
		ArgumentCheck.notNull(jobs);

		mJobs = jobs;
	}

	//endregion

	//region Properties

	public int[] getJobs()
	{
		return mJobs;
	}

	public int geJobsCount()
	{
		return mJobs.length;
	}

	//endregion
}
