package com.jraska.vsb.or1.schedule;

import com.jraska.vsb.or1.data.Input;
import com.jraska.vsb.or1.data.Output;
import com.jraska.vsb.or1.schedule.abc.ABCScheduler;
import com.jraska.vsb.or1.schedule.abc.Bee;
import com.jraska.vsb.or1.schedule.abc.MakespanCounter;
import com.jraska.vsb.or1.schedule.validation.NoWaitFlowShopValidatorTest;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ABCSchedulerTest
{
	//region Test Methods

	@Test
	public void testSingleRun() throws Exception
	{
		Input input = SimpleSchedulerTest.newLabInput();

		MakespanCounter makespanCounter = new MakespanCounter(input.getJobs());
		RandomPositionGenerator randomPositionGenerator = new RandomPositionGenerator(input.getJobsCount());

		List<Bee> bees = new ArrayList<Bee>(20);
		for (int i = 0; i < 20; i++)
		{
			bees.add(new Bee());
		}

		ABCScheduler abcScheduler = new ABCScheduler(bees, makespanCounter, randomPositionGenerator, 5);
		Output schedule = abcScheduler.schedule(input);

		NoWaitFlowShopValidatorTest.validate(schedule);
	}

	//endregion
}
