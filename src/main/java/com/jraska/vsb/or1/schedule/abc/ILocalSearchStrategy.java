package com.jraska.vsb.or1.schedule.abc;

public interface ILocalSearchStrategy
{
	//region Methods

	int[] getNext(int[] currentSolution);

	//endregion
}
