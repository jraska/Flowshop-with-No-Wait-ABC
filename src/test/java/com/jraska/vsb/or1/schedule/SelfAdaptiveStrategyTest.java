package com.jraska.vsb.or1.schedule;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Random;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class SelfAdaptiveStrategyTest {
  //region Test Methods

  @Test
  public void testFillListAtStart() throws Exception {
    IObjectiveFunction objectiveFunction = mock(IObjectiveFunction.class);

    int size = 111;
    SelfAdaptiveSearchStrategy strategy = new SelfAdaptiveSearchStrategy(new Random(), objectiveFunction, size);

    strategy.getNext(new int[2]);

    assertThat(strategy.getNeighboursStrategies().size(), equalTo(size - 1)); //-1 because one is used
  }

  @Test
  public void testFillsWinningList() throws Exception {
    SelfAdaptiveSearchStrategy strategy = new SelfAdaptiveSearchStrategy(new GettingBetterFunction());

    for (int i = 0, size = strategy.getSize(); i < size; i++) {
      strategy.getNext(new int[2]);
    }

    assertThat(strategy.getWinningNeighbourStrategies().size(), equalTo(strategy.getSize())); //all should succeed
  }

  @Test
  public void testUsesWinningList() throws Exception {
    SelfAdaptiveSearchStrategy strategy = new SelfAdaptiveSearchStrategy(new GettingBetterFunction());

    for (int i = 0, size = strategy.getSize(); i < size; i++) {
      strategy.getNext(new int[2]);
    }

    ArrayList<ILocalSearchStrategy> winningStrategies = new ArrayList<ILocalSearchStrategy>(strategy.getWinningNeighbourStrategies());

    strategy.fillNeighboursList();

    assertThat(strategy.getWinningNeighbourStrategies().size(), equalTo(0));

    ArrayList<ILocalSearchStrategy> refilledStrategies = new ArrayList<ILocalSearchStrategy>(strategy.getNeighboursStrategies());
    for (int i = 0, size = strategy.getSize() / 2; i < size; i++) { // / 2 because only first part should fit
      assertThat(refilledStrategies.get(i), equalTo(winningStrategies.get(i)));
    }
  }

  //endregion

  //region Nested classes

  static class GettingBetterFunction implements IObjectiveFunction {
    private int _counter = Integer.MAX_VALUE;

    @Override
    public int evaluate(int[] solution) {
      return --_counter;
    }
  }

  //endregion
}
