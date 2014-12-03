package com.jraska.vsb.or1.data;

import com.jraska.common.ArgumentCheck;

import java.util.Collections;
import java.util.List;

public final class Input
{
	//region Fields

	private final int mMachinesCount;
	private final List<Job> mJobs;

	//endregion

	//region Constructors


	public Input(int machinesCount, List<Job> jobs)
	{
		if (machinesCount < 1)
		{
			throw new IllegalArgumentException("There must be at least one machine");
		}
		ArgumentCheck.notNull(jobs);

		validateJobs(machinesCount, jobs);

		mMachinesCount = machinesCount;
		mJobs = Collections.unmodifiableList(jobs);
	}

	//endregion

	//region Properties

	public static void validateJobs(int machineSize, Iterable<Job> jobs)
	{
		for (Job job : jobs)
		{
			if (job.getDurationsCount() != machineSize)
			{
				String messageBase = "Job %s does not have correct number of durations for %d machines";
				throw new IllegalArgumentException(String.format(messageBase, job, machineSize));
			}
		}
	}

	public List<Job> getJobs()
	{
		return mJobs;
	}

	//endregion

	//region Methods

	public int getMachinesCount()
	{
		return mMachinesCount;
	}

	//endregion
}
