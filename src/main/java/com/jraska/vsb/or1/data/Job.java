package com.jraska.vsb.or1.data;

import com.jraska.common.ArgumentCheck;

import java.util.Arrays;

public final class Job {
  //region Fields

  private final String _name;
  private final int[] _durations;
  private final int _totalDuration;

  //endregion

  //region Constructors

  public Job(String name, int[] durations) {
    ArgumentCheck.notNull(name);
    ArgumentCheck.notNull(durations);

    if (durations.length == 0) {
      throw new IllegalArgumentException("Job must have at least one operation.");
    }

    _name = name;
    _durations = Arrays.copyOf(durations, durations.length);

    _totalDuration = countTotalDuration(_durations);
  }

  //endregion

  //region Properties

  public String getName() {
    return _name;
  }

  public int[] getDurations() {
    return Arrays.copyOf(_durations, _durations.length);
  }

  public int getDurationsCount() {
    return _durations.length;
  }

  public int getTotalDuration() {
    return _totalDuration;
  }

  //endregion

  //region Methods

  protected static int countTotalDuration(int[] durations) {
    int sum = 0;

    for (int duration : durations) {
      sum += duration;
    }

    return sum;
  }

  /**
   * Calculates departing delay of provided job after this job
   *
   * @param next Job to continue after me
   * @return Delay of next job in which can start
   */
  public int getDepartureDelay(Job next) {
    int[] nextDurations = next._durations;
    final int length = nextDurations.length;
    int delay = _durations[0]; //need to wait at start

    int prevLeaveTime = 0;
    int nextComeTime = 0;
    for (int i = 0, end = length - 1; i < end; i++) {
      prevLeaveTime += _durations[i + 1];
      nextComeTime += nextDurations[i];

      if (nextComeTime < prevLeaveTime) {
        int diff = prevLeaveTime - nextComeTime;
        delay += diff;
        nextComeTime += diff;
      }
    }

    return delay;
  }

  //endregion

  //region Object impl

  @Override
  public String toString() {
    return "Job" + _name + ", Durations=" + Arrays.toString(_durations);
  }

  //endregion
}
