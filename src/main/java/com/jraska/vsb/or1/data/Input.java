package com.jraska.vsb.or1.data;

import com.jraska.common.ArgumentCheck;

import java.util.Arrays;

public final class Input {
  //region Fields

  private final int _machinesCount;
  private final Job[] _jobs;

  //TODO: remove mutable field
  private String _name;

  //endregion

  //region Constructors


  public Input(int machinesCount, Job[] jobs) {
    if (machinesCount < 1) {
      throw new IllegalArgumentException("There must be at least one machine");
    }

    ArgumentCheck.notNull(jobs, "jobs");

    validateJobsSize(machinesCount, jobs);

    _machinesCount = machinesCount;
    _jobs = Arrays.copyOf(jobs, jobs.length);
  }

  //endregion

  //region Properties

  public String getName() {
    return _name;
  }

  public void setName(String name) {
    _name = name;
  }

  public static void validateJobsSize(int machineSize, Job[] jobs) {
    for (Job job : jobs) {
      if (job.getDurationsCount() != machineSize) {
        String messageBase = "Job %s does not have correct number of durations for %d machines";
        throw new IllegalArgumentException(String.format(messageBase, job, machineSize));
      }
    }
  }

  public Job[] getJobs() {
    return _jobs;
  }

  public int getMachinesCount() {
    return _machinesCount;
  }

  public int getJobsCount() {
    return _jobs.length;
  }

  //endregion

  //region Methods

  public Job[] getWithOrder(int[] order) {
    ArgumentCheck.notNull(order);

    Job[] jobs = new Job[_jobs.length];
    for (int i = 0; i < _jobs.length; i++) {
      jobs[i] = _jobs[order[i]];
    }

    return jobs;
  }

  //endregion

  //region Object impl

  @Override
  public String toString() {
    return "MachinesCount=" + _machinesCount + ", Jobs" + Arrays.toString(_jobs);
  }

  //endregion
}
