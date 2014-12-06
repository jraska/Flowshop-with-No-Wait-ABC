package com.jraska.vsb.or1.schedule;

import com.jraska.common.ArgumentCheck;
import com.jraska.vsb.or1.data.Input;
import com.jraska.vsb.or1.data.Job;
import com.jraska.vsb.or1.data.JobSchedule;
import com.jraska.vsb.or1.data.Output;

import java.util.ArrayList;
import java.util.List;

/**
 * Dummy implementation which just put jobs to line and verify validators of output.
 */
public final class SimpleScheduler implements IScheduler
{
	//region IScheduler impl

	@Override
	public Output schedule(Input input)
	{
		ArgumentCheck.notNull(input);

		Job[] jobs = input.getJobs();

		List<JobSchedule> schedules = new ArrayList<JobSchedule>(jobs.length);

		JobSchedule previous = new JobSchedule(jobs[0], 0);//first starts immediately
		schedules.add(previous);

		for (int i = 1; i < jobs.length; i++)
		{
			Job next = jobs[i];
			int nextStart = calculateNextStart(previous, next);

			previous = new JobSchedule(next, nextStart);
			schedules.add(previous);
		}

		return new Output(schedules, input);
	}

	//endregion

	//region Methods

	protected int calculateNextStart(JobSchedule previous, Job next)
	{
		return previous.getStartTime() + previous.getDelay(next);
	}


	//endregion
}
