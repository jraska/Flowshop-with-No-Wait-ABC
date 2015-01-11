package com.jraska.vsb.or1.schedule;

import com.jraska.vsb.or1.data.Input;
import com.jraska.vsb.or1.data.JobSchedule;
import com.jraska.vsb.or1.data.Output;
import com.jraska.vsb.or1.io.SimpleTextParserTest;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class SimpleSchedulerTest {
  //region Constants

  public static final String LAB_INPUT = "4 5\n" +
          "5 5 3 6 3\n" +
          "4 4 2 4 4\n" +
          "4 4 3 4 1\n" +
          "3 6 3 2 5";

  //endregion

  //region Test Methods

  @Test
  public void testProducesValidSolution() throws Exception {
    Input input = SimpleSchedulerTest.newLabInput();

    SimpleScheduler scheduler = new SimpleScheduler();
    Output output = scheduler.schedule(input);

    //start times from labs
    int[] expectedStarts = {0, 5, 16, 19, 27};
    int[] expectedEnds = {16, 24, 27, 35, 40};

    List<JobSchedule> jobSchedules = output.getJobSchedules();
    assertThat(jobSchedules.size(), equalTo(5));

    for (int i = 0; i < jobSchedules.size(); i++) {
      JobSchedule jobSchedule = jobSchedules.get(i);
      assertThat("Not correct start counted", jobSchedule.getStartTime(), equalTo(expectedStarts[i]));
      assertThat("Not correct finish counted", jobSchedule.getFinishTime(), equalTo(expectedEnds[i]));
    }

    assertThat("TFT does not match", output.getTFT(), equalTo(142));
  }

  //endregion

  //region Methods

  public static Input newLabInput() {
    return SimpleTextParserTest.parseInput(LAB_INPUT);
  }

  //endregion
}
