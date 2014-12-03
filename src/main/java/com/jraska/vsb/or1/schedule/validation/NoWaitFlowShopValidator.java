package com.jraska.vsb.or1.schedule.validation;

import com.jraska.common.ArgumentCheck;
import com.jraska.vsb.or1.data.Interval;
import com.jraska.vsb.or1.data.JobSchedule;
import com.jraska.vsb.or1.data.Output;

import java.util.ArrayList;
import java.util.List;

/**
 * Validates that there is only one job at time on the machine
 */
public class NoWaitFlowShopValidator implements IOutputValidator
{
	//region IOutputValidator impl

	@Override
	public List<String> validate(Output output)
	{
		ArgumentCheck.notNull(output);

		return validateNoIntersectingInterval(output);
	}

	//endregion

	//region Methods

	protected List<String> validateNoIntersectingInterval(Output output)
	{
		List<IntervalWrapper>[] machineIntervals = getIntervalsForMachines(output);

		List<String> validationErrors = new ArrayList<String>();

		for (int machineIndex = 0, count = output.getMachinesCount(); machineIndex < count; machineIndex++)
		{
			List<IntervalWrapper> intervals = machineIntervals[machineIndex];

			validationErrors.addAll(checkForIntersectionErrors(intervals));
		}

		return validationErrors;
	}

	protected List<String> checkForIntersectionErrors(List<IntervalWrapper> intervals)
	{
		List<String> errors = new ArrayList<String>();

		//Check all possible pairs
		for (int i = 0, size = intervals.size(); i < size; i++)
		{
			for (int j = i + 1; j < size; j++)
			{
				IntervalWrapper first = intervals.get(i);
				IntervalWrapper second = intervals.get(j);

				if (first.mInterval.intersects(second.mInterval))
				{
					errors.add(createIntersectionReport(first, second));
				}
			}
		}

		return errors;
	}

	@SuppressWarnings("StringBufferReplaceableByString") //More readable
	protected String createIntersectionReport(IntervalWrapper first, IntervalWrapper second)
	{
		int problemMachineIndex = first.mJob.indexForInterval(first.mInterval);

		StringBuilder b = new StringBuilder();

		b.append("Error on machine ").append(problemMachineIndex + 1).append("\n");

		b.append("Jobs ").append(first.mJob.getJobName()).append(" and ").append(second.mJob.getJobName());
		b.append(" intersects with working intervals ");
		b.append(first.mInterval).append(" and ").append(second.mInterval);

		return b.toString();
	}

	@SuppressWarnings("unchecked")
	protected List<IntervalWrapper>[] getIntervalsForMachines(Output output)
	{
		int machineCount = output.getMachinesCount();
		List<IntervalWrapper>[] machineIntervals = new List[machineCount];
		for (int i = 0; i < machineCount; i++)
		{
			machineIntervals[i] = new ArrayList<IntervalWrapper>();
		}

		//fill the intervals
		for (JobSchedule jobSchedule : output.getJobSchedules())
		{
			Interval[] jobIntervals = jobSchedule.getJobIntervals();
			for (int i = 0; i < machineCount; i++)
			{
				machineIntervals[i].add(new IntervalWrapper(jobSchedule, jobIntervals[i]));
			}
		}
		return machineIntervals;
	}

	//endregion

	//region Nested classes

	/**
	 * Adds info about Job to interval
	 */
	static class IntervalWrapper
	{
		private final JobSchedule mJob;
		private final Interval mInterval;

		public IntervalWrapper(JobSchedule job, Interval interval)
		{
			mJob = job;
			mInterval = interval;
		}
	}

	//endregion
}
