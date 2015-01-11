package com.jraska.vsb.or1.schedule;

import com.jraska.common.ArgumentCheck;

import java.util.Arrays;
import java.util.Random;

public class Insert implements ILocalSearchStrategy {

  //region Fields

  private final Random _random;

  //endregion

  //region Constructors

  public Insert() {
    this(new Random());
  }

  public Insert(Random random) {
    ArgumentCheck.notNull(random);

    _random = random;
  }

  //endregion

  //region ILocalSearchStrategy impl

  @SuppressWarnings("ManualArrayCopy") //more readable
  @Override
  public int[] getNext(int[] currentSolution) {
    int length = currentSolution.length;

    int[] copyOf = Arrays.copyOf(currentSolution, currentSolution.length);
    int fromIndex = _random.nextInt(length);

    int targetIndex = _random.nextInt(length - 1);
    if (targetIndex >= fromIndex) {
      targetIndex++;
    }

    int temp = currentSolution[fromIndex];
    if (fromIndex < targetIndex) {
      for (int i = fromIndex; i < targetIndex; i++) {
        copyOf[i] = currentSolution[i + 1];
      }
    } else {
      for (int i = fromIndex; i > targetIndex; i--) {
        copyOf[i] = currentSolution[i - 1];
      }
    }

    copyOf[targetIndex] = temp;

    return copyOf;
  }

  //endregion
}
