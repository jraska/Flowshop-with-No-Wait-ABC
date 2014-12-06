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

	int mCountOfMisses;

	private final ILocalSearchStrategy mLocalSearchStrategy;

	//endregion

	//region Constructors

	public Bee(ILocalSearchStrategy localSearchStrategy)
	{
		ArgumentCheck.notNull(localSearchStrategy);

		mLocalSearchStrategy = localSearchStrategy;
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

	public int sendScounting(IPositionGenerator generator, IObjectiveFunction objectiveFunction)
	{
		mPosition = generator.generate();
		mPositionValue = objectiveFunction.evaluate(mPosition);

		onBetterFound();

		return mPositionValue;
	}

	public boolean searchForNewPosition(IObjectiveFunction function)
	{
		int[] next = mLocalSearchStrategy.getNext(mPosition);

		int value = function.evaluate(next);
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
		mCountOfMisses = 0;

		mFitnessValue = 1.0 / (1 + mPositionValue);
	}

	//endregion
}
