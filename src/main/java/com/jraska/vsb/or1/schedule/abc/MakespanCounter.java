package com.jraska.vsb.or1.schedule.abc;

import com.jraska.common.ArgumentCheck;
import com.jraska.vsb.or1.data.Job;
import com.jraska.vsb.or1.schedule.IObjectiveFunction;

import java.util.Arrays;

public final class MakespanCounter implements IObjectiveFunction {
  //region Fields

  private final int _jobsLength;
  private final Job[] _jobs;

  //endregion

  //region Constructors

  public MakespanCounter(Job[] jobs) {
    ArgumentCheck.notNull(jobs);

    _jobsLength = jobs.length;
    _jobs = Arrays.copyOf(jobs, _jobsLength);
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
    if (solution.length != _jobsLength) {
      String message = "Solution length %d and Jobs length %d does not match";
      throw new IllegalArgumentException(String.format(message, solution.length, _jobsLength));
    }

    int delaySum = 0;

    for (int i = 1; i < _jobsLength; i++) {
      Job previous = _jobs[solution[i - 1]];
      Job next = _jobs[solution[i]];

      delaySum += previous.getDepartureDelay(next);
    }

    int lastJobDuration = _jobs[solution[_jobsLength - 1]].getTotalDuration();

    return delaySum + lastJobDuration;
  }

  //endregion
}
