package com.jraska.vsb.or1.data;

import com.jraska.common.ArgumentCheck;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class JobSchedule
{
	//region Fields

	private final Job mJob;
	private final int mStartTime;

	private final Interval[] mJobIntervals;

	//endregion

	//region Constructors

	public JobSchedule(Job job, int startTime)
	{
		ArgumentCheck.notNull(job, "job");
		if (startTime < 0)
		{
			throw new IllegalArgumentException(String.format("Starting time must be at least zero, not %d.", startTime));
		}

		mJob = job;
		mStartTime = startTime;

		mJobIntervals = countIntervals(mJob.getDurations(), startTime);
	}

	//endregion

	//region Properties

	public int getStartTime()
	{
		return mStartTime;
	}

	public String getJobName()
	{
		return mJob.getName();
	}

	public int[] getDepartureTimes()
	{
		return countDepartureTimes(mJob.getDurations(), mStartTime);
	}

	public int[] getStartingTimes()
	{
		return countStartingTimes(mJob.getDurations(), mStartTime);
	}

	public int getOperationsCount()
	{
		return mJob.getDurationsCount();
	}

	public Interval[] getJobIntervals()
	{
		return mJobIntervals;
	}

	public int[] getDurations()
	{
		return mJob.getDurations();
	}

	public int getDelay(Job next)
	{
		return mJob.getDepartureDelay(next);
	}

	public int getFinishTime()
	{
		return mJobIntervals[mJobIntervals.length - 1].getEnd();
	}

	//endregion

	//region Methods

	public Interval intervalAt(int index)
	{
		return mJobIntervals[index];
	}

	public int indexForInterval(Interval interval)
	{
		ArgumentCheck.notNull(interval);

		for (int i = 0; i < mJobIntervals.length; i++)
		{
			if (mJobIntervals[i].equals(interval))
			{
				return i;
			}
		}

		throw new IllegalArgumentException("There is no such interval in this job.");
	}

	static Interval[] countIntervals(int[] durations, int start)
	{
		int[] startingTimes = countStartingTimes(durations, start);
		int[] endingTimes = countDepartureTimes(durations, start);

		return countIntervals(startingTimes, endingTimes);
	}

	static Interval[] countIntervals(int[] startingTimes, int[] endingTimes)
	{
		Interval[] intervals = new Interval[startingTimes.length];
		for (int i = 0; i < startingTimes.length; i++)
		{
			intervals[i] = new Interval(startingTimes[i], endingTimes[i]);
		}

		return intervals;
	}

	static int[] countDepartureTimes(int[] durations, int start)
	{
		int[] times = countStartingTimes(durations, start);

		for (int i = 0; i < durations.length; i++)
		{
			times[i] += durations[i];
		}

		return times;
	}

	static int[] countStartingTimes(int[] durations, final int start)
	{
		int[] startingTimes = new int[durations.length];

		int time = start;
		startingTimes[0] = start;
		for (int i = 1; i < durations.length; i++)
		{
			time += durations[i - 1];
			startingTimes[i] = time;
		}

		return startingTimes;
	}

	public static List<JobSchedule> createJobSchedules(Job[] jobs)
	{
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
		return schedules;
	}

	protected static int calculateNextStart(JobSchedule previous, Job next)
	{
		return previous.getStartTime() + previous.getDelay(next);
	}

	//endregion

	//region Object impl

	@Override
	public String toString()
	{
		return "Schedule for job " + mJob.getName() + " Running intervals: " + Arrays.toString(getJobIntervals());
	}

	//endregion
}
