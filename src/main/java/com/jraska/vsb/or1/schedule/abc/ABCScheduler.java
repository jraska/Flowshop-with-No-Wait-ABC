package com.jraska.vsb.or1.schedule.abc;

import com.jraska.common.ArgumentCheck;
import com.jraska.vsb.or1.data.Input;
import com.jraska.vsb.or1.data.Job;
import com.jraska.vsb.or1.data.JobSchedule;
import com.jraska.vsb.or1.data.Output;
import com.jraska.vsb.or1.schedule.IPositionGenerator;
import com.jraska.vsb.or1.schedule.IScheduler;

import java.util.Arrays;
import java.util.List;

/**
 * Scheduler using pure Artificial Bee Colony algorithm to find best vector of job order
 */
public class ABCScheduler implements IScheduler {
  //region Fields

  private final Bee[] _bees;
  private final int _attemptsThreshold;
  private final IPositionGenerator _positionGenerator;
  private final int _onlookersCount;
  private final IOnlookerChooser _onlookerChooser;


  private int[] _bestSolution;
  private int _bestValue;

  //endregion

  //region Constructors

  public ABCScheduler(Bee[] bees, IPositionGenerator generator, IOnlookerChooser onlookerChooser, int attemptsThreshold) {
    ArgumentCheck.notNull(bees);
    ArgumentCheck.notNull(generator);
    ArgumentCheck.notNull(onlookerChooser);

    if (attemptsThreshold < 1) {
      throw new IllegalArgumentException("Attempts threshold must be positive");
    }

    _bees = Arrays.copyOf(bees, bees.length);
    _positionGenerator = generator;
    _onlookerChooser = onlookerChooser;
    _attemptsThreshold = attemptsThreshold;

    //same size of onlookers as bees
    _onlookersCount = bees.length;
  }

  //endregion

  //region IScheduler impl

  @Override
  public Output schedule(Input input) {
    ArgumentCheck.notNull(input, "input");

    _bestSolution = null;
    _bestValue = Integer.MAX_VALUE;

    //setup Bees
    for (Bee bee : _bees) {
      scout(bee);
    }

    for (int i = 0; i < 1000; i++) {
      //search near bees
      double fitnessSum = 0;
      for (Bee bee : _bees) {
        localSearch(bee);

        fitnessSum += bee.getFitnessValue();
      }

      //send onlookers
      for (int j = 0; j < _onlookersCount; j++) {
        Bee bee = _onlookerChooser.selectBee(_bees, fitnessSum);
        localSearch(bee);
      }

      //make scout and send them back
      for (Bee bee : _bees) {
        if (bee.getCountOfMisses() >= _attemptsThreshold) {
          scout(bee);
        }
      }
    }

    Job[] bestSequence = input.getWithOrder(_bestSolution);
    List<JobSchedule> jobSchedules = JobSchedule.createJobSchedules(bestSequence);

    return new Output(jobSchedules, input);
  }

  protected void localSearch(Bee bee) {
    boolean foundBetter = bee.searchForNewPosition();
    if (foundBetter) {
      if (bee._positionValue < _bestValue) {
        _bestValue = bee._positionValue;
        _bestSolution = bee._position;
      }
    }
  }

  protected void scout(Bee bee) {
    int value = bee.sendScouting(_positionGenerator);
    if (value < _bestValue) {
      _bestSolution = bee.getPosition();
      _bestValue = value;
    }
  }

  //endregion
}
