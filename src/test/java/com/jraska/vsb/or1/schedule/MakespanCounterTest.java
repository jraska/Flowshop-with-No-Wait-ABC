package com.jraska.vsb.or1.schedule;

import com.jraska.vsb.or1.data.Input;
import com.jraska.vsb.or1.io.SimpleTextParserTest;
import com.jraska.vsb.or1.schedule.abc.MakespanCounter;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class MakespanCounterTest
{
	//region Test methods

	@Test
	public void testLabMakespan() throws Exception
	{
		Input input = SimpleTextParserTest.parseInput(SimpleSchedulerTest.LAB_INPUT);

		MakespanCounter makespanCounter = new MakespanCounter(input.getJobs());
		int[] solution = {0, 1, 2, 3, 4};

		int makespan = makespanCounter.evaluate(solution);

		assertThat("Bad makespan counted", makespan, equalTo(40));
	}


	//endregion
}
