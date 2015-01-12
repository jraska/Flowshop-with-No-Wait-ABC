package com.jraska.vsb.or1.schedule.summary;

public class SingleRunSummary {
  //region Fields

  private final int _result;
  private final long _time;

  //endregion

  //region Constructors

  public SingleRunSummary(int result, long time) {
    if (result < 0) {
      throw new IllegalArgumentException();
    }

    if (time < 0) {
      throw new IllegalArgumentException();
    }

    _result = result;
    _time = time;
  }

  //endregion


  //region properties

  public int getResult() {
    return _result;
  }

  public long getTime() {
    return _time;
  }


  //endregion
}
