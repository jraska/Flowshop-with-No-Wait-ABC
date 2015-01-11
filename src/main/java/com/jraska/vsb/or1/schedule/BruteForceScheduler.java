package com.jraska.vsb.or1.schedule;

import com.jraska.common.ArgumentCheck;
import com.jraska.vsb.or1.data.Input;
import com.jraska.vsb.or1.data.Job;
import com.jraska.vsb.or1.data.JobSchedule;
import com.jraska.vsb.or1.data.Output;

import java.util.List;


public class BruteForceScheduler implements IScheduler {
  //region Fields

  private final IObjectiveFunction _objectiveFunction;

  private int[] _bestSoFar;
  private int _minSoFar;

  //endregion

  //region Constructors

  public BruteForceScheduler(IObjectiveFunction objectiveFunction) {
    ArgumentCheck.notNull(objectiveFunction);

    _objectiveFunction = objectiveFunction;
  }

  //endregion

  //region IScheduler impl

  @Override
  public Output schedule(Input input) {
    ArgumentCheck.notNull(input);

    _bestSoFar = null;
    _minSoFar = Integer.MAX_VALUE;

    final int length = input.getJobs().length;

    permutations(length);

    if (_bestSoFar == null) {
      throw new IllegalStateException("Ensures something is there.");
    }

    Job[] order = input.getWithOrder(_bestSoFar);

    List<JobSchedule> jobSchedules = JobSchedule.createJobSchedules(order);
    return new Output(jobSchedules, input);
  }

  //endregion

  //region Methods

  private void permutations(int n) {
    int[] a = new int[n];
    for (int i = 0; i < n; i++) {
      a[i] = i;
    }

    permutation(a, 0);
  }

  private void permutation(int[] arr, int pos) {
    if (arr.length - pos == 1) {
      int value = _objectiveFunction.evaluate(arr);
      if (value < _minSoFar) {
        _minSoFar = value;
        _bestSoFar = arr.clone();
      }
    } else {
      for (int i = pos; i < arr.length; i++) {
        swap(arr, pos, i);
        permutation(arr, pos + 1);
        swap(arr, pos, i);
      }
    }
  }

  public static void swap(int[] arr, int pos1, int pos2) {
    int h = arr[pos1];
    arr[pos1] = arr[pos2];
    arr[pos2] = h;
  }

  //endregion
}
