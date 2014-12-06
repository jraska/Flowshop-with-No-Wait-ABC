package com.jraska.vsb.or1.schedule.abc;

public interface IOnlookerChooser
{
	//region Methods

	Bee selectBee(Bee[] bees, double fitnessSum);

	//endregion
}
