package com.jraska.vsb.or1.schedule.abc;

import com.jraska.common.ArgumentCheck;
import com.jraska.vsb.or1.data.Input;
import com.jraska.vsb.or1.data.Job;
import com.jraska.vsb.or1.data.JobSchedule;
import com.jraska.vsb.or1.data.Output;
import com.jraska.vsb.or1.schedule.IPositionGenerator;
import com.jraska.vsb.or1.schedule.IScheduler;

import java.util.Arrays;
import java.util.List;

/**
 * Scheduler using pure Artificial Bee Colony algorithm to find best vector of job order
 */
public class ABCScheduler implements IScheduler
{
	//region Fields

	private final Bee[] mBees;
	private final int mAttemptsThreshold;
	private final IPositionGenerator mPositionGenerator;
	private final int mOnlookersCount;
	private final IOnlookerChooser mOnlookerChooser;


	private int[] mBestSolution;
	private int mBestValue;

	//endregion

	//region Constructors

	public ABCScheduler(Bee[] bees, IPositionGenerator generator, IOnlookerChooser onlookerChooser, int attemptsThreshold)
	{
		ArgumentCheck.notNull(bees);
		ArgumentCheck.notNull(generator);
		ArgumentCheck.notNull(onlookerChooser);

		if (attemptsThreshold < 1)
		{
			throw new IllegalArgumentException("Attempts threshold must be positive");
		}

		mBees = Arrays.copyOf(bees, bees.length);
		mPositionGenerator = generator;
		mOnlookerChooser = onlookerChooser;
		mAttemptsThreshold = attemptsThreshold;

		//same size of onlookers as bees
		mOnlookersCount = bees.length;
	}

	//endregion

	//region IScheduler impl

	@Override
	public Output schedule(Input input)
	{
		ArgumentCheck.notNull(input, "input");

		mBestSolution = null;
		mBestValue = Integer.MAX_VALUE;

		//setup Bees
		for (Bee bee : mBees)
		{
			scout(bee);
		}

		for (int i = 0; i < 1000; i++)
		{
			//search near bees
			double fitnessSum = 0;
			for (Bee bee : mBees)
			{
				localSearch(bee);

				fitnessSum += bee.getFitnessValue();
			}

			//send onlookers
			for (int j = 0; j < mOnlookersCount; j++)
			{
				Bee bee = mOnlookerChooser.selectBee(mBees, fitnessSum);
				localSearch(bee);
			}

			//make scout and send them back
			for (Bee bee : mBees)
			{
				if (bee.mCountOfMisses >= mAttemptsThreshold)
				{
					scout(bee);
				}
			}
		}

		Job[] bestSequence = input.getWithOrder(mBestSolution);
		List<JobSchedule> jobSchedules = JobSchedule.createJobSchedules(bestSequence);

		return new Output(jobSchedules, input);
	}

	protected void localSearch(Bee bee)
	{
		boolean foundBetter = bee.searchForNewPosition();
		if (foundBetter)
		{
			if (bee.mPositionValue < mBestValue)
			{
				mBestValue = bee.mPositionValue;
				mBestSolution = bee.mPosition;
			}
		}
	}

	protected void scout(Bee bee)
	{
		int value = bee.sendScouting(mPositionGenerator);
		if (value < mBestValue)
		{
			mBestSolution = bee.getPosition();
			mBestValue = value;
		}
	}

	//endregion
}
