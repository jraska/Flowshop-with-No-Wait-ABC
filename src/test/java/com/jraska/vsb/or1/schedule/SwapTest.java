package com.jraska.vsb.or1.schedule;

import org.junit.Test;

import java.util.Random;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

public class SwapTest {

  //region Constants

  public static final int TEST_DIFFERENT_ARRAY_ITERATIONS = 10;
  public static final int TEST_ARRAY_SIZE = 100;
  public static final int TEST_SWAP_ITERATIONS = 100;

  //endregion

  //region Test methods

  @Test
  public void testSwapsEverytime() throws Exception {
    Swap swap = new Swap();

    for (int i = 0; i < TEST_DIFFERENT_ARRAY_ITERATIONS; i++) {

      int[] arr = new int[TEST_ARRAY_SIZE];

      Random random = new Random();
      for (int j = 0; j < TEST_ARRAY_SIZE; j++) {
        arr[j] = random.nextInt();
      }

      for (int j = 0; j < TEST_SWAP_ITERATIONS; j++) {
        int[] newArr = swap.getNext(arr);

        assertThat(arr, not(equalTo(newArr)));
      }
    }
  }

  //endregion
}
