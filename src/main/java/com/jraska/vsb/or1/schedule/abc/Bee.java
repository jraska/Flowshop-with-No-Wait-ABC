package com.jraska.vsb.or1.schedule.abc;

import com.jraska.common.ArgumentCheck;
import com.jraska.vsb.or1.schedule.ILocalSearchStrategy;
import com.jraska.vsb.or1.schedule.IObjectiveFunction;
import com.jraska.vsb.or1.schedule.IPositionGenerator;

/**
 * Bzz...
 */
public class Bee {
  //region Fields

  int[] _position;
  int _positionValue;
  private double _fitnessValue;

  private int _countOfMisses;

  private final ILocalSearchStrategy _localSearchStrategy;
  private final IObjectiveFunction _objectiveFunction;

  //endregion

  //region Constructors

  public Bee(ILocalSearchStrategy localSearchStrategy, IObjectiveFunction objectiveFunction) {
    ArgumentCheck.notNull(localSearchStrategy);
    ArgumentCheck.notNull(objectiveFunction);

    _localSearchStrategy = localSearchStrategy;
    _objectiveFunction = objectiveFunction;
  }

  //endregion

  //region Properties

  public int[] getPosition() {
    return _position;
  }

  public int getPositionValue() {
    return _positionValue;
  }

  public int getCountOfMisses() {
    return _countOfMisses;
  }

  public double getFitnessValue() {
    return _fitnessValue;
  }

  //endregion

  //region Methods

  public int sendScouting(IPositionGenerator generator) {
    _position = generator.generate();
    _positionValue = _objectiveFunction.evaluate(_position);

    onNewFound();

    return _positionValue;
  }

  public boolean searchForNewPosition() {
    int[] next = _localSearchStrategy.getNext(_position);

    int value = _objectiveFunction.evaluate(next);
    if (value < _positionValue) {
      _positionValue = value;
      _position = next;

      onBetterFound();

      return true;
    } else {
      _countOfMisses++;

      return false;
    }
  }

  protected void onBetterFound() {
    onNewFound();
  }

  private void onNewFound() {
    _countOfMisses = 0;

    _fitnessValue = 1.0 / (1 + _positionValue);
  }

  //endregion
}
