package com.jraska.vsb.or1.schedule;

/**
 * All algorithms will try to minimise the value of this function
 */
public interface IObjectiveFunction
{
	//region Methods

	int evaluate(int[] solution);

	//endregion
}
