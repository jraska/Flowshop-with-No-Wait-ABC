package com.jraska.vsb.or1.schedule.summary;

import com.jraska.common.ArgumentCheck;

import java.util.Collections;
import java.util.List;

public class SummaryItem {
  //region Fields

  private final String _name;
  private final int _simpleMakespan;
  private final List<SingleRunSummary> _singleRuns;

  //endregion

  //region Constructors

  public SummaryItem(String name, int simpleMakespan, List<SingleRunSummary> singleRuns) {
    ArgumentCheck.notNull(name);

    if (simpleMakespan < 0) {
      throw new IllegalArgumentException("simpleMakespan is negative: " + simpleMakespan);
    }

    ArgumentCheck.notNull(singleRuns);

    _name = name;
    _simpleMakespan = simpleMakespan;
    _singleRuns = Collections.unmodifiableList(singleRuns);
  }

  //endregion

  //region Properties

  public String getName() {
    return _name;
  }

  public int getSimpleMakespan() {
    return _simpleMakespan;
  }

  public List<SingleRunSummary> getSingleRuns() {
    return _singleRuns;
  }

  public int getRunsCount() {
    return _singleRuns.size();
  }

  public int getBestMakespan() {
    int min = Integer.MAX_VALUE;

    for (SingleRunSummary run : _singleRuns) {
      if (run.getResult() < min) {
        min = run.getResult();
      }
    }

    return min;
  }

  public int getWorstMakespan() {
    int max = Integer.MIN_VALUE;

    for (SingleRunSummary run : _singleRuns) {
      if (run.getResult() > max) {
        max = run.getResult();
      }
    }

    return max;
  }

  public long getTotalTime() {
    long sum = 0;

    for (SingleRunSummary run : _singleRuns) {
      sum += run.getTime();
    }

    return sum;
  }

  public double getMakespanMean() {
    int sum = 0;

    for (SingleRunSummary run : _singleRuns) {
      sum += run.getResult();
    }

    return sum / (double) getRunsCount();
  }

  public double getStandardDeviation() {
    double deviationsSum = 0;

    final double average = getMakespanMean();

    for (SingleRunSummary run : _singleRuns) {
      double diff = average - run.getResult();

      deviationsSum += diff * diff;
    }

    deviationsSum /= getRunsCount();

    double deviation = Math.sqrt(deviationsSum);

    return deviation;
  }

  public double getAverageTime() {
    long sum = 0;

    for (SingleRunSummary run : _singleRuns) {
      sum += run.getTime();
    }

    return sum / (double) getRunsCount();
  }

  //endregion
}
