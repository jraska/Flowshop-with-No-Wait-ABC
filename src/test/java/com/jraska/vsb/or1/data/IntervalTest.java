package com.jraska.vsb.or1.data;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class IntervalTest {
  //region Test Methods

  @Test
  public void testNotIntersecting() throws Exception {
    Interval a = new Interval(2, 5);
    Interval b = new Interval(8, 99);
    Interval c = new Interval(5, 7);

    assertFalse(a.intersects(b));
    assertFalse(a.intersects(c));
    assertFalse(b.intersects(a));
    assertFalse(b.intersects(c));
    assertFalse(c.intersects(a));
    assertFalse(c.intersects(b));
  }

  @Test
  public void testIntersecting() throws Exception {
    Interval a = new Interval(2, 9);
    Interval b = new Interval(8, 99);
    Interval c = new Interval(5, 10);

    assertTrue(a.intersects(b));
    assertTrue(a.intersects(c));
    assertTrue(b.intersects(a));
    assertTrue(b.intersects(c));
    assertTrue(c.intersects(a));
    assertTrue(c.intersects(b));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCannotCreateBadInterval() throws Exception {
    new Interval(5, 4); //will throw error
  }

  //endregion
}
