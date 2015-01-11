package com.jraska.vsb.or1.schedule.abc;

import com.jraska.common.ArgumentCheck;

public class TournamentOnlookerChooser implements IOnlookerChooser {
  //region Fields

  private final RouletteWheelSelection _wheelSelection;

  //endregion

  //region Constructors

  public TournamentOnlookerChooser(RouletteWheelSelection wheelSelection) {
    ArgumentCheck.notNull(wheelSelection);

    _wheelSelection = wheelSelection;
  }

  //endregion

  //region IOnlookerChooser impl

  @Override
  public Bee selectBee(Bee[] bees, double fitnessSum) {
    Bee firstBee = _wheelSelection.selectBee(bees, fitnessSum);
    Bee secondBee = _wheelSelection.selectBee(bees, fitnessSum);

    if (firstBee == secondBee) {
      return firstBee;
    }

    //pick the better one
    if (firstBee.getPositionValue() < secondBee.getPositionValue()) {
      return firstBee;
    } else {
      return secondBee;
    }
  }

  //endregion
}
