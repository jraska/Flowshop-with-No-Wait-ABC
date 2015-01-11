package com.jraska.vsb.or1.schedule.abc;

import com.jraska.common.ArgumentCheck;
import com.jraska.vsb.or1.data.Job;
import com.jraska.vsb.or1.schedule.IObjectiveFunction;

import java.util.Arrays;

public final class MakespanCounter implements IObjectiveFunction {
  //region Fields

  private final int mJobsLength;
  private final Job[] mJobs;

  //endregion

  //region Constructors

  public MakespanCounter(Job[] jobs) {
    ArgumentCheck.notNull(jobs);

    mJobsLength = jobs.length;
    mJobs = Arrays.copyOf(jobs, mJobsLength);
  }

  //endregion

  //region IObjectiveFunction impl

  @Override
  public int evaluate(int[] solution) {
    return countMakespan(solution);
  }

  //endregion

  //region Methods

  public int countMakespan(int[] solution) {
    if (solution.length != mJobsLength) {
      String message = "Solution length %d and Jobs length %d does not match";
      throw new IllegalArgumentException(String.format(message, solution.length, mJobsLength));
    }

    int delaySum = 0;

    for (int i = 1; i < mJobsLength; i++) {
      Job previous = mJobs[solution[i - 1]];
      Job next = mJobs[solution[i]];

      delaySum += previous.getDepartureDelay(next);
    }

    int lastJobDuration = mJobs[solution[mJobsLength - 1]].getTotalDuration();

    return delaySum + lastJobDuration;
  }

  //endregion
}
