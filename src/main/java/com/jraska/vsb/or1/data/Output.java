package com.jraska.vsb.or1.data;

import com.jraska.common.ArgumentCheck;

import java.util.Collections;
import java.util.List;

public final class Output
{
	//region Fields

	private final List<JobSchedule> mJobSchedules;

	//endregion

	//region Constructors

	public Output(List<JobSchedule> jobSchedules)
	{
		ArgumentCheck.notNull(jobSchedules);

		mJobSchedules = Collections.unmodifiableList(jobSchedules);
	}

	//endregion

	//region Properties

	public List<JobSchedule> getJobSchedules()
	{
		return mJobSchedules;
	}

	//endregion
}
