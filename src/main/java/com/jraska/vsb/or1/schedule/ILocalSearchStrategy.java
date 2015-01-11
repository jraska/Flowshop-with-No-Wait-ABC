package com.jraska.vsb.or1.schedule;

public interface ILocalSearchStrategy {
  //region Methods

  int[] getNext(int[] currentSolution);

  //endregion
}
