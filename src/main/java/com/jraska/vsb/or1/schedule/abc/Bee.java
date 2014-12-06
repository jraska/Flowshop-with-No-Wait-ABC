package com.jraska.vsb.or1.schedule.abc;

import com.jraska.common.ArgumentCheck;
import com.jraska.vsb.or1.schedule.IObjectiveFunction;
import com.jraska.vsb.or1.schedule.IPositionGenerator;

/**
 * Bzz...
 */
public class Bee
{
	//region Fields

	int[] mPosition;
	int mPositionValue;
	private double mFitnessValue;

	private int mCountOfMisses;

	private final ILocalSearchStrategy mLocalSearchStrategy;
	private final IObjectiveFunction mObjectiveFunction;

	//endregion

	//region Constructors

	public Bee(ILocalSearchStrategy localSearchStrategy, IObjectiveFunction objectiveFunction)
	{
		ArgumentCheck.notNull(localSearchStrategy);
		ArgumentCheck.notNull(objectiveFunction);

		mLocalSearchStrategy = localSearchStrategy;
		mObjectiveFunction = objectiveFunction;
	}

	//endregion

	//region Properties

	public int[] getPosition()
	{
		return mPosition;
	}

	public int getPositionValue()
	{
		return mPositionValue;
	}

	public int getCountOfMisses()
	{
		return mCountOfMisses;
	}

	public double getFitnessValue()
	{
		return mFitnessValue;
	}

	//endregion

	//region Methods

	public int sendScouting(IPositionGenerator generator)
	{
		mPosition = generator.generate();
		mPositionValue = mObjectiveFunction.evaluate(mPosition);

		onNewFound();

		return mPositionValue;
	}

	public boolean searchForNewPosition()
	{
		int[] next = mLocalSearchStrategy.getNext(mPosition);

		int value = mObjectiveFunction.evaluate(next);
		if (value < mPositionValue)
		{
			mPositionValue = value;
			mPosition = next;

			onBetterFound();

			return true;
		}
		else
		{
			mCountOfMisses++;

			return false;
		}
	}

	protected void onBetterFound()
	{
		onNewFound();
	}

	private void onNewFound()
	{
		mCountOfMisses = 0;

		mFitnessValue = 1.0 / (1 + mPositionValue);
	}

	//endregion
}
