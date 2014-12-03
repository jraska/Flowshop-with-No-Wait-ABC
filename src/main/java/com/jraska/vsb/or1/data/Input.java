package com.jraska.vsb.or1.data;

import com.jraska.common.ArgumentCheck;

import java.util.Collections;
import java.util.List;

public final class Input
{
	//region Fields

	private final List<Machine> mMachines;

	//endregion

	//region Constructors

	public Input(List<Machine> machines)
	{
		ArgumentCheck.notNull(machines);

		mMachines = Collections.unmodifiableList(machines);
	}

	//endregion

	//region Properties

	public List<Machine> getMachines()
	{
		return mMachines;
	}

	public int getSchedulesSize()
	{
		return mMachines.size();
	}

	//endregion
}
