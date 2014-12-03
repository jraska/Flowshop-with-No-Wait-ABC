package com.jraska.vsb.or1.schedule.validation;

import com.jraska.vsb.or1.data.Input;
import com.jraska.vsb.or1.data.Job;
import com.jraska.vsb.or1.data.JobSchedule;
import com.jraska.vsb.or1.data.Output;
import com.jraska.vsb.or1.io.SimpleTextParserTest;
import com.jraska.vsb.or1.schedule.SimpleScheduler;
import com.jraska.vsb.or1.schedule.SimpleSchedulerTest;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class NoWaitFlowShopValidatorTest
{
	//region Test Methods

	@Test
	public void testFindConflict() throws Exception
	{
		String inputText = "2 2\n" +
				"3 2\n" +
				"2 3";

		Input input = SimpleTextParserTest.parseInput(inputText);

		List<JobSchedule> schedules = new ArrayList<JobSchedule>();
		for (Job job : input.getJobs())
		{
			schedules.add(new JobSchedule(job, 1));//all starts at once
		}

		Output output = new Output(schedules, input);

		NoWaitFlowShopValidator validator = new NoWaitFlowShopValidator();
		List<String> validationResults = validator.validate(output);

		assertThat(validationResults.size(), equalTo(2)); //all starts at once so there will be conflict on both machines
	}

	@Test
	public void testValidSolution() throws Exception
	{
		Input input = SimpleTextParserTest.parseInput(SimpleSchedulerTest.LAB_INPUT);

		SimpleScheduler simpleScheduler = new SimpleScheduler();
		Output schedule = simpleScheduler.schedule(input);

		NoWaitFlowShopValidator validator = new NoWaitFlowShopValidator();
		List<String> validationResults = validator.validate(schedule);

		assertThat("There are no actual errors", validationResults.size(), equalTo(0));
	}

	//endregion
}
