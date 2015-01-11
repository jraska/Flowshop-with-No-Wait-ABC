package com.jraska.vsb.or1.data;

import com.jraska.common.ArgumentCheck;

import java.util.Collections;
import java.util.List;

public final class Output {
  //region Fields

  private final List<JobSchedule> _jobSchedules;
  private final Input _relatedInput;

  //endregion

  //region Constructors

  public Output(List<JobSchedule> jobSchedules, Input relatedInput) {
    ArgumentCheck.notNull(jobSchedules);
    ArgumentCheck.notNull(relatedInput);

    _jobSchedules = Collections.unmodifiableList(jobSchedules);
    _relatedInput = relatedInput;
  }

  //endregion

  //region Properties

  public List<JobSchedule> getJobSchedules() {
    return _jobSchedules;
  }

  public Input getRelatedInput() {
    return _relatedInput;
  }

  public int getMachinesCount() {
    return _relatedInput.getMachinesCount();
  }

  public int getTFT() {
    int sum = 0;

    for (JobSchedule schedule : _jobSchedules) {
      sum += schedule.getFinishTime();
    }

    return sum;
  }

  public int getMakespan() {
    return _jobSchedules.get(_jobSchedules.size() - 1).getFinishTime();
  }

  //endregion

  //region Object impl

  @Override
  public String toString() {
    StringBuilder b = new StringBuilder(1000);
    b.append("Makespan: ").append(getMakespan()).append("\n");
    b.append("Schedules:\n");

    for (JobSchedule schedule : _jobSchedules) {
      b.append(schedule).append(",\n");
    }

    //remove last comma
    b.setLength(b.length() - 2);

    return b.toString();
  }


  //endregion
}
