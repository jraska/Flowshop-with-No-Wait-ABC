package com.jraska.vsb.or1.schedule.abc;

import com.jraska.common.ArgumentCheck;
import com.jraska.vsb.or1.data.Input;
import com.jraska.vsb.or1.data.Job;
import com.jraska.vsb.or1.data.JobSchedule;
import com.jraska.vsb.or1.data.Output;
import com.jraska.vsb.or1.schedule.IObjectiveFunction;
import com.jraska.vsb.or1.schedule.IPositionGenerator;
import com.jraska.vsb.or1.schedule.IScheduler;

import java.util.Collections;
import java.util.List;

/**
 * Scheduler using pure Artificial Bee Colony algorithm to find best vector of job order
 */
public class ABCScheduler implements IScheduler
{
	//region Fields

	private final List<Bee> mBees;
	private final int mAttemptsThreshold;
	private final IObjectiveFunction mObjectiveFunction;
	private final IPositionGenerator mStartSolutionGenerator;


	private int[] mBestSolution;
	private int mBestValue;

	//endregion

	//region Constructors

	public ABCScheduler(List<Bee> bees, IObjectiveFunction objectiveFunction,
	                    IPositionGenerator generator, int attemptsThreshold)
	{
		ArgumentCheck.notNull(bees);
		ArgumentCheck.notNull(objectiveFunction);
		ArgumentCheck.notNull(generator);

		if (attemptsThreshold < 1)
		{
			throw new IllegalArgumentException("Attempts threshold must be positive");
		}


		mBees = Collections.unmodifiableList(bees);
		mObjectiveFunction = objectiveFunction;
		mStartSolutionGenerator = generator;
		mAttemptsThreshold = attemptsThreshold;
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
			bee.mPosition = mStartSolutionGenerator.generate();
			bee.mPositionValue = mObjectiveFunction.evaluate(bee.mPosition);

			if (bee.mPositionValue < mBestValue)
			{
				mBestValue = bee.mPositionValue;
				mBestSolution = bee.mPosition;
			}
		}


		for (int i = 0; i < 1000; i++)
		{
			//TODO: steps of ABC algorithm
			//search near bees

			//send onlookers

			//make scout and send them back
		}

		Job[] bestSequence = input.getWithOrder(mBestSolution);
		List<JobSchedule> jobSchedules = JobSchedule.createJobSchedules(bestSequence);

		return new Output(jobSchedules, input);
	}

	//endregion
}
