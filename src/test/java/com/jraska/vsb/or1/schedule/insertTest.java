package com.jraska.vsb.or1.schedule;

import org.junit.Test;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static com.jraska.vsb.or1.schedule.SwapTest.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

public class InsertTest {

  //region Test methods

  @Test
  public void testInsertsEverytime() throws Exception {
    Insert insert = new Insert(new Random());

    for (int i = 0; i < TEST_DIFFERENT_ARRAY_ITERATIONS; i++) {

      int[] arr = new int[TEST_ARRAY_SIZE];

      Random random = new Random();
      for (int j = 0; j < TEST_ARRAY_SIZE; j++) {
        arr[j] = random.nextInt();
      }

      Set<Integer> numbers = toSet(arr);

      for (int j = 0; j < TEST_ITERATIONS; j++) {
        int[] newArr = insert.getNext(arr);

        assertThat(arr, not(equalTo(newArr)));

        Set<Integer> newNumbers = toSet(newArr);

        assertThat(newNumbers, equalTo(numbers));
      }
    }
  }

  //endregion

  //region Methods

  public static Set<Integer> toSet(int[] arr) {
    Set<Integer> numbers = new HashSet<Integer>();
    for (int i : arr) {
      numbers.add(i);
    }

    return numbers;
  }

  //endregion
}
