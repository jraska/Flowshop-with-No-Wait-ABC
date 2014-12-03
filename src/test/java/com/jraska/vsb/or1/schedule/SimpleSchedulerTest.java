package com.jraska.vsb.or1.schedule;

import com.jraska.vsb.or1.data.Input;
import com.jraska.vsb.or1.data.Job;
import com.jraska.vsb.or1.data.JobSchedule;
import com.jraska.vsb.or1.data.Output;
import com.jraska.vsb.or1.io.SimpleTextParserTest;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class SimpleSchedulerTest
{
	//region Constants

	public static final String LAB_INPUT = "4 5\n" +
			"5 5 3 6 3\n" +
			"4 4 2 4 4\n" +
			"4 4 3 4 1\n" +
			"3 6 3 2 5";

	//endregion

	//region Test Methods

	@Test
	public void testCalculateNextStartTime() throws Exception
	{
		int[] durations = {5, 4, 4, 3};

		JobSchedule jobSchedule = new JobSchedule(new Job("TestJob", durations), 0);

		int[] nextDurations = {5, 4, 4, 6};
		Job nextJob = new Job("TestJob2", nextDurations);

		SimpleScheduler simpleScheduler = new SimpleScheduler();
		int start = simpleScheduler.calculateNextStart(jobSchedule, nextJob);

		assertThat("Bad start found.", start, equalTo(5));
	}

	@Test
	public void testProducesValidSolution() throws Exception
	{
		Input input = SimpleTextParserTest.parseInput(LAB_INPUT);

		SimpleScheduler scheduler = new SimpleScheduler();
		Output output = scheduler.schedule(input);

		//start times from labs
		int[] expectedStarts = {0, 5, 16, 19, 27};
		int[] expectedEnds = {16, 24, 27, 35, 40};

		List<JobSchedule> jobSchedules = output.getJobSchedules();
		assertThat(jobSchedules.size(), equalTo(5));

		for (int i = 0; i < jobSchedules.size(); i++)
		{
			JobSchedule jobSchedule = jobSchedules.get(i);
			assertThat("Not correct start counted", jobSchedule.getStartTime(), equalTo(expectedStarts[i]));
			assertThat("Not correct finish counted", jobSchedule.getFinishTime(), equalTo(expectedEnds[i]));
		}

		assertThat("TFT does not match", output.getTFT(), equalTo(142));
	}

	//endregion
}
