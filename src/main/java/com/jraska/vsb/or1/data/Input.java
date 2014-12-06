package com.jraska.vsb.or1.data;

import com.jraska.common.ArgumentCheck;

import java.util.Arrays;

public final class Input
{
	//region Fields

	private final int mMachinesCount;
	private final Job[] mJobs;

	//TODO: remove mutable field
	private String mName;

	//endregion

	//region Constructors


	public Input(int machinesCount, Job[] jobs)
	{
		if (machinesCount < 1)
		{
			throw new IllegalArgumentException("There must be at least one machine");
		}

		ArgumentCheck.notNull(jobs, "jobs");

		validateJobsSize(machinesCount, jobs);

		mMachinesCount = machinesCount;
		mJobs = Arrays.copyOf(jobs, jobs.length);
	}

	//endregion

	//region Properties

	public String getName()
	{
		return mName;
	}

	public void setName(String name)
	{
		mName = name;
	}

	public static void validateJobsSize(int machineSize, Job[] jobs)
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

	public Job[] getJobs()
	{
		return mJobs;
	}

	public int getMachinesCount()
	{
		return mMachinesCount;
	}

	public int getJobsCount()
	{
		return mJobs.length;
	}

	//endregion

	//region Methods

	public Job[] getWithOrder(int[] order)
	{
		ArgumentCheck.notNull(order);

		Job[] jobs = new Job[mJobs.length];
		for (int i = 0; i < mJobs.length; i++)
		{
			jobs[i] = mJobs[order[i]];
		}

		return jobs;
	}

	//endregion

	//region Object impl

	@Override
	public String toString()
	{
		return "MachinesCount=" + mMachinesCount + ", Jobs" + Arrays.toString(mJobs);
	}

	//endregion
}
