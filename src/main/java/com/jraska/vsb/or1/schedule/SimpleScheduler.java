package com.jraska.vsb.or1.schedule;

import com.jraska.common.ArgumentCheck;
import com.jraska.vsb.or1.data.Input;
import com.jraska.vsb.or1.data.Job;
import com.jraska.vsb.or1.data.JobSchedule;
import com.jraska.vsb.or1.data.Output;

import java.util.List;

/**
 * Dummy implementation which just put jobs to line and verify validators of output.
 */
public final class SimpleScheduler implements IScheduler {
  //region IScheduler impl

  @Override
  public Output schedule(Input input) {
    ArgumentCheck.notNull(input);

    Job[] jobs = input.getJobs();

    List<JobSchedule> schedules = createJobSchedules(jobs);

    return new Output(schedules, input);
  }

  protected List<JobSchedule> createJobSchedules(Job[] jobs) {
    return JobSchedule.createJobSchedules(jobs);
  }

  //endregion

}
