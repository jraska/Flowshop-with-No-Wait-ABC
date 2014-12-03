package com.jraska.vsb.or1.data;

import com.jraska.common.ArgumentCheck;

import java.util.Collections;
import java.util.List;

public final class Output
{
	//region Fields

	private final List<JobSchedule> mJobSchedules;
	private final Input mRelatedInput;

	//endregion

	//region Constructors

	public Output(List<JobSchedule> jobSchedules, Input relatedInput)
	{
		ArgumentCheck.notNull(jobSchedules);
		ArgumentCheck.notNull(relatedInput);

		mJobSchedules = Collections.unmodifiableList(jobSchedules);
		mRelatedInput = relatedInput;
	}

	//endregion

	//region Properties

	public List<JobSchedule> getJobSchedules()
	{
		return mJobSchedules;
	}

	public Input getRelatedInput()
	{
		return mRelatedInput;
	}

	public int getMachinesCount()
	{
		return mRelatedInput.getMachinesCount();
	}

	public int getTFT()
	{
		int sum = 0;

		for (JobSchedule schedule : mJobSchedules)
		{
			sum += schedule.getFinishTime();
		}

		return sum;
	}

	//endregion

	//region Object impl

	@Override
	public String toString()
	{
		StringBuilder b = new StringBuilder(1000);
		b.append("Schedules:\n");

		for (JobSchedule schedule : mJobSchedules)
		{
			b.append(schedule).append(",\n");
		}

		//remove last comma
		b.setLength(b.length() - 2);

		return b.toString();
	}


	//endregion
}
