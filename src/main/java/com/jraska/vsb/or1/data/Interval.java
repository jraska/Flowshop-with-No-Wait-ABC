package com.jraska.vsb.or1.data;

public final class Interval {
  //region Fields

  private final int _start;
  private final int _end;

  private final int _length;

  //endregion

  //region Constructors

  public Interval(int start, int end) {
    if (end < start) {
      String message = "Cannot create interval <%d, %d>. Start is higher than end.";
      throw new IllegalArgumentException(String.format(message, start, end));
    }

    _start = start;
    _end = end;

    _length = end - start;
  }

  //endregion

  //region Properties

  public int getStart() {
    return _start;
  }

  public int getEnd() {
    return _end;
  }

  public int getLength() {
    return _length;
  }

  //endregion

  //region Methods

  public boolean intersects(Interval other) {
    return _start < other._end && _end > other._start;
  }

  //endregion

  //region Object impl

  @Override
  public String toString() {
    return "<" + _start + "; " + _end + ">";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Interval interval = (Interval) o;

    return _end == interval._end && _start == interval._start;
  }

  @Override
  public int hashCode() {
    int result = _start;
    result = 31 * result + _end;
    return result;
  }

  //endregion
}
