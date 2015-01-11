package com.jraska.vsb.or1.schedule;

import com.jraska.vsb.or1.data.Input;
import com.jraska.vsb.or1.data.Output;
import com.jraska.vsb.or1.schedule.abc.MakespanCounter;
import com.jraska.vsb.or1.schedule.validation.NoWaitFlowShopValidator;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class BruteForceSchedulerTest {
  //region Test Methods

  @Test
  public void testBestLabSolution() throws Exception {
    Input input = SimpleSchedulerTest.newLabInput();

    BruteForceScheduler scheduler = new BruteForceScheduler(new MakespanCounter(input.getJobs()));
    Output output = scheduler.schedule(input);

    List<String> validate = new NoWaitFlowShopValidator().validate(output);
    assertThat("Solution is not valid", validate.size(), equalTo(0));

    assertThat(output.getMakespan(), equalTo(32));
  }

  //endregion
}
