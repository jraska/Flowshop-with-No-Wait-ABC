package com.jraska.vsb.or1.schedule;

import com.jraska.common.ArgumentCheck;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class RandomPositionGenerator implements IPositionGenerator
{
	//region Fields

	private final int mLength;
	private final Random mRandom;

	//endregion

	//region Constructors


	public RandomPositionGenerator(int length)
	{
		this(length, new Random());
	}

	public RandomPositionGenerator(int length, Random random)
	{
		if (length < 1)
		{
			throw new IllegalArgumentException("Length must be positive");
		}

		ArgumentCheck.notNull(random);

		mLength = length;
		mRandom = random;
	}

	//endregion

	//region IStartSolutionGenerator impl

	@Override
	public int[] generate()
	{
		int length = mLength;

		List<Integer> integers = new ArrayList<Integer>(length);
		for (int i = 0; i < length; i++)
		{
			integers.add(i);
		}

		int[] startSolution = new int[length];

		for (int i = 0; i < length; i++)
		{
			int size = integers.size();
			int position = mRandom.nextInt(size);

			int index = integers.get(position);

			startSolution[index] = i;
			integers.remove(position);
		}

		return startSolution;
	}

	//endregion
}
