package com.jraska.vsb.or1.schedule;

import com.jraska.common.ArgumentCheck;
import com.jraska.vsb.or1.data.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Dummy implementation which just put jobs to line and verify validators of output.
 */
public class SimpleScheduler implements IScheduler
{
	//region IScheduler impl

	@Override
	public Output schedule(Input input)
	{
		ArgumentCheck.notNull(input);

		List<Job> jobs = input.getJobs();

		List<JobSchedule> schedules = new ArrayList<JobSchedule>(jobs.size());

		JobSchedule previous = new JobSchedule(jobs.get(0), 0);//first starts immediately
		schedules.add(previous);

		for (int i = 1; i < jobs.size(); i++)
		{
			Job next = jobs.get(i);
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
		Interval[] intervals = previous.getJobIntervals();

		int time = intervals[0].getEnd();

		int[] durations = next.getDurations();
		for (int i = 0, end = intervals.length - 1; i < end; i++)
		{
			time += durations[i];
			int latestPossibleEnd = intervals[i + 1].getEnd();
			if (latestPossibleEnd > time)
			{
				int diff = latestPossibleEnd - time;
				time += diff;
			}
		}

		time += durations[durations.length - 1];

		//time is now finish time
		return time - next.getTotalDuration();
	}

	//endregion
}
