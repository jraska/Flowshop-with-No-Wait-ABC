package com.jraska.vsb.or1.schedule.abc;

import com.jraska.vsb.or1.data.Input;
import com.jraska.vsb.or1.data.Output;
import com.jraska.vsb.or1.schedule.RandomPositionGenerator;
import com.jraska.vsb.or1.schedule.SimpleSchedulerTest;
import com.jraska.vsb.or1.schedule.Swap;
import com.jraska.vsb.or1.schedule.validation.NoWaitFlowShopValidatorTest;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

public class ABCSchedulerTest {
  //region Test Methods

  @Test
  public void testSingleRun() throws Exception {
    Input input = SimpleSchedulerTest.newLabInput();

    MakespanCounter makespanCounter = new MakespanCounter(input.getJobs());
    RandomPositionGenerator randomPositionGenerator = new RandomPositionGenerator(input.getJobsCount());
    RouletteWheelSelection wheelSelection = new RouletteWheelSelection();

    Swap swap = new Swap();
    Bee[] bees = new Bee[20];
    for (int i = 0; i < 20; i++) {
      bees[i] = new Bee(swap, makespanCounter);
    }

    ABCScheduler abcScheduler = new ABCScheduler(bees, randomPositionGenerator, wheelSelection, 5, 1000);
    Output schedule = abcScheduler.schedule(input);

    assertThat(schedule, not(nullValue()));

    NoWaitFlowShopValidatorTest.validate(schedule);

    System.out.println(schedule);
  }

  //endregion
}
