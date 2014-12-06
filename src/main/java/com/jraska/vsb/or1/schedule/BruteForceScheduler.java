package com.jraska.vsb.or1.schedule;

import com.jraska.common.ArgumentCheck;
import com.jraska.vsb.or1.data.Input;
import com.jraska.vsb.or1.data.Job;
import com.jraska.vsb.or1.data.JobSchedule;
import com.jraska.vsb.or1.data.Output;

import java.util.ArrayList;
import java.util.List;


public class BruteForceScheduler implements IScheduler
{
	//region Fields

	private final IObjectiveFunction mObjectiveFunction;

	//endregion

	//region Constructors

	public BruteForceScheduler(IObjectiveFunction objectiveFunction)
	{
		ArgumentCheck.notNull(objectiveFunction);

		mObjectiveFunction = objectiveFunction;
	}

	//endregion

	//region IScheduler impl

	@Override
	public Output schedule(Input input)
	{
		ArgumentCheck.notNull(input);

		final int length = input.getJobs().length;

		List<int[]> permutations = permutations(length);

		int[] bestSoFar = null;
		int minSoFar = Integer.MAX_VALUE;
		for (int[] permutation : permutations)
		{
			int value = mObjectiveFunction.evaluate(permutation);
			if (value < minSoFar)
			{
				minSoFar = value;
				bestSoFar = permutation;
			}
		}

		if (bestSoFar == null)
		{
			throw new IllegalStateException("Ensures something is there.");
		}

		Job[] startJobs = input.getJobs();
		Job[] order = new Job[length];
		for (int i = 0; i < length; i++)
		{
			order[i] = startJobs[bestSoFar[i]];
		}

		List<JobSchedule> jobSchedules = JobSchedule.createJobSchedules(order);
		return new Output(jobSchedules, input);
	}

	//endregion

	//region Methods

	static List<int[]> permutations(int n)
	{
		int[] a = new int[n];
		for (int i = 0; i < n; i++)
		{
			a[i] = i;
		}

		List<int[]> ret = new ArrayList<int[]>();
		permutation(a, 0, ret);
		return ret;
	}

	public static void permutation(int[] arr, int pos, List<int[]> list)
	{
		if (arr.length - pos == 1)
		{
			list.add(arr.clone());
		}
		else
		{
			for (int i = pos; i < arr.length; i++)
			{
				swap(arr, pos, i);
				permutation(arr, pos + 1, list);
				swap(arr, pos, i);
			}
		}
	}

	public static void swap(int[] arr, int pos1, int pos2)
	{
		int h = arr[pos1];
		arr[pos1] = arr[pos2];
		arr[pos2] = h;
	}

	//endregion
}
