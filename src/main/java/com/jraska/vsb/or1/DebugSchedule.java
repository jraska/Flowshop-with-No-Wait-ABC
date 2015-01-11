package com.jraska.vsb.or1;

import com.jraska.vsb.or1.schedule.IObjectiveFunction;
import com.jraska.vsb.or1.schedule.abc.Bee;
import com.jraska.vsb.or1.schedule.abc.DebugBee;
import com.jraska.vsb.or1.schedule.abc.ILocalSearchStrategy;

public class DebugSchedule extends Program {
  //region Main method

  public static void main(String[] args) throws Exception {
    DebugSchedule debugSchedule = new DebugSchedule();
    debugSchedule.run(args);
  }

  //endregion

  //region Fields

  private int _beeIndex;

  //endregion

  //region Program overrides

  @Override
  protected Bee createBee(ILocalSearchStrategy strategy, IObjectiveFunction function) {
    return new DebugBee(String.valueOf(_beeIndex++), _out, strategy, function);
  }

  @Override
  protected int getMaxMissThreshold() {
    return 5;
  }

  //endregion
}
