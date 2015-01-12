package com.jraska.vsb.or1.schedule.summary;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class SummaryItemTest {
  //region Constants

  public static final double DELTA = 0.00001;

  //endregion

  //region Test methods

  @Test
  public void testMinValue() throws Exception {
    SummaryItem summaryItem = getTestSummaryItem();

    assertThat(summaryItem.getBestMakespan(), equalTo(2));
  }

  @Test
  public void testMaxValue() throws Exception {
    SummaryItem summaryItem = getTestSummaryItem();

    assertThat(summaryItem.getWorstMakespan(), equalTo(9));
  }

  @Test
  public void testMeanValue() throws Exception {
    SummaryItem summaryItem = getTestSummaryItem();

    assertEquals(summaryItem.getMakespanMean(), 5.33333, DELTA);
  }

  @Test
  public void testStandardDeviation() throws Exception {
    SummaryItem summaryItem = getTestSummaryItem();

    assertEquals(summaryItem.getStandardDeviation(), 2.56038, DELTA);
  }

  //endregion

  //region Methods

  static SummaryItem getTestSummaryItem() {
    int[] makespans = {3, 4, 6, 8, 9, 2};
    long[] durations = {45, 167, 128, 121, 89, 25};

    List<SingleRunSummary> singleRunSummaries = new ArrayList<SingleRunSummary>();
    for (int i = 0; i < makespans.length; i++) {
      singleRunSummaries.add(new SingleRunSummary(makespans[i], durations[i]));
    }

    return new SummaryItem("Test", 10, singleRunSummaries);
  }

  //endregion
}
