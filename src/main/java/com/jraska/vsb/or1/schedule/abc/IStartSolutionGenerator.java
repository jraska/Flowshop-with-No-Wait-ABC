package com.jraska.vsb.or1.schedule.abc;

import com.jraska.vsb.or1.data.Job;

public interface IStartSolutionGenerator
{
	int[] generate(Job job);
}
