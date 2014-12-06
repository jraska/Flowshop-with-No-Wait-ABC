package com.jraska.vsb.or1;

import com.jraska.vsb.or1.data.Input;
import com.jraska.vsb.or1.data.Output;
import com.jraska.vsb.or1.schedule.BruteForceScheduler;
import com.jraska.vsb.or1.schedule.SimpleScheduler;
import com.jraska.vsb.or1.schedule.abc.MakespanCounter;

public class BruteForceComparison extends Program
{
	//region Main method

	public static void main(String[] args) throws Exception
	{
		BruteForceComparison bruteForceComparison = new BruteForceComparison();
		bruteForceComparison.run(args);
	}

	//endregion

	//region Program overrides

	@Override
	protected void referenceSchedule(Input input)
	{
		BruteForceScheduler bruteForceScheduler = new BruteForceScheduler(new MakespanCounter(input.getJobs()));
		Output schedule = bruteForceScheduler.schedule(input);

		_out.println("Brute force makespan: " + schedule.getMakespan());

		SimpleScheduler simpleScheduler = new SimpleScheduler();
		Output output = simpleScheduler.schedule(input);

		_out.println("SimpleScheduler makespan: " + output.getMakespan());
	}


	//endregion
}
