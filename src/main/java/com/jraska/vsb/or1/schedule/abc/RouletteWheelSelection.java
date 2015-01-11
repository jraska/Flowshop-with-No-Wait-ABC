package com.jraska.vsb.or1.schedule.abc;

import com.jraska.common.ArgumentCheck;

import java.util.Random;

public final class RouletteWheelSelection implements IOnlookerChooser {
  //region Fields

  private final Random mRandom;

  //endregion

  //region Constructors


  public RouletteWheelSelection() {
    this(new Random());
  }

  public RouletteWheelSelection(Random random) {
    ArgumentCheck.notNull(random);

    mRandom = random;
  }

  //endregion

  //region IOnlookerChooser impl

  @Override
  public Bee selectBee(Bee[] bees, double fitnessSum) {
    double value = fitnessSum * mRandom.nextDouble();

    double sum = 0;
    for (Bee bee : bees) {
      sum += bee.getFitnessValue();
      if (sum >= value) {
        return bee;
      }
    }

    throw new IllegalStateException("Should not come here.");
  }

  //endregion
}
