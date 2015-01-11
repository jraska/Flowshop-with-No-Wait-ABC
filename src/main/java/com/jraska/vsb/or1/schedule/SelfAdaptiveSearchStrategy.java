package com.jraska.vsb.or1.schedule;

import com.jraska.common.ArgumentCheck;

import java.util.*;

public class SelfAdaptiveSearchStrategy implements ILocalSearchStrategy {
  //region Constants

  public static final int DEFAULT_SIZE = 200; //from the article

  //endregion

  //region Fields

  private final Random _random;
  private final IObjectiveFunction _objectiveFunction;

  private final Queue<ILocalSearchStrategy> _neighboursStrategies;
  private final Queue<ILocalSearchStrategy> _winningNeighbourStrategies;

  private final int _size;

  //endregion

  //region Constructors

  public SelfAdaptiveSearchStrategy(IObjectiveFunction objectiveFunction) {
    this(new Random(), objectiveFunction, DEFAULT_SIZE);
  }

  public SelfAdaptiveSearchStrategy(Random random, IObjectiveFunction objectiveFunction, int size) {
    ArgumentCheck.notNull(random);
    ArgumentCheck.notNull(objectiveFunction);

    if (size < 1) {
      throw new IllegalArgumentException("Size cannot be lower than zero, but was:" + size);
    }

    _random = random;
    _objectiveFunction = objectiveFunction;
    _size = size;

    _neighboursStrategies = new LinkedList<ILocalSearchStrategy>();
    _winningNeighbourStrategies = new LinkedList<ILocalSearchStrategy>();
  }

  //endregion

  //region Properties

  public int getSize() {
    return _size;
  }

  public IObjectiveFunction getObjectiveFunction() {
    return _objectiveFunction;
  }

  public Collection<ILocalSearchStrategy> getNeighboursStrategies() {
    return Collections.unmodifiableCollection(_neighboursStrategies);
  }

  public Collection<ILocalSearchStrategy> getWinningNeighbourStrategies() {
    return Collections.unmodifiableCollection(_winningNeighbourStrategies);
  }

  //endregion

  //region ILocalSearchStrategy impl

  @Override
  public int[] getNext(int[] currentSolution) {
    if (_neighboursStrategies.isEmpty()) {
      fillNeighboursList();
    }

    ILocalSearchStrategy strategy = _neighboursStrategies.remove();

    int previousValue = _objectiveFunction.evaluate(currentSolution);

    int[] next = strategy.getNext(currentSolution);

    int newValue = _objectiveFunction.evaluate(next);

    if (newValue < previousValue) {
      _winningNeighbourStrategies.add(strategy);
    }

    return next;
  }

  //endregion

  //region Methods

  protected void fillNeighboursList() {
    int threshold = (_size * 3) / 4; //fill at most 75%

    int added = 0;
    for (ILocalSearchStrategy localSearchStrategy : _winningNeighbourStrategies) {
      if (added > threshold) {
        break;
      }
      added++;

      _neighboursStrategies.add(localSearchStrategy);
    }

    _winningNeighbourStrategies.clear();

    //fill the list to the correct size;
    for (int i = _neighboursStrategies.size(); i < _size; i++) {
      _neighboursStrategies.add(getNextStrategy());
    }
  }

  protected ILocalSearchStrategy getNextStrategy() {
    final int random = _random.nextInt(4);

    switch (random) {
      case 0:
        return new Swap();
      case 1:
        return new Insert();
      case 2:
        return new TwoSwaps();
      case 3:
        return new TwoInserts();

      default:
        throw new IllegalArgumentException();
    }
  }

  //endregion

  //region Nested classes

  static class TwoInserts implements ILocalSearchStrategy {
    private final Insert _insert;

    public TwoInserts() {
      this(new Insert());
    }

    public TwoInserts(Insert insert) {
      ArgumentCheck.notNull(insert);

      _insert = insert;
    }

    @Override
    public int[] getNext(int[] currentSolution) {
      int[] firstInsert = _insert.getNext(currentSolution);

      return _insert.getNext(firstInsert);
    }
  }

  static class TwoSwaps implements ILocalSearchStrategy {
    private final Swap _swap;

    public TwoSwaps() {
      this(new Swap());
    }

    public TwoSwaps(Swap swap) {
      ArgumentCheck.notNull(swap);

      _swap = swap;
    }

    @Override
    public int[] getNext(int[] currentSolution) {
      int[] firstSwap = _swap.getNext(currentSolution);

      return _swap.getNext(firstSwap);
    }
  }

  //endregion
}
