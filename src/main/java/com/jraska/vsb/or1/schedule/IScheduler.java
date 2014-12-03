package com.jraska.vsb.or1.schedule;

import com.jraska.vsb.or1.data.Input;
import com.jraska.vsb.or1.data.Output;

public interface IScheduler
{
	//region Methods

	Output schedule(Input input);

	//endregion
}
