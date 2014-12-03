package com.jraska.vsb.or1.data;

public final class Interval
{
	//region Fields

	private final int mStart;
	private final int mEnd;

	private final int mLength;

	//endregion

	//region Constructors

	public Interval(int start, int end)
	{
		if (end < start)
		{
			String message = "Cannot create interval <%d, %d>. Start is higher than end.";
			throw new IllegalArgumentException(String.format(message, start, end));
		}

		mStart = start;
		mEnd = end;

		mLength = end - start;
	}

	//endregion

	//region Properties

	public int getStart()
	{
		return mStart;
	}

	public int getEnd()
	{
		return mEnd;
	}

	public int getLength()
	{
		return mLength;
	}

	//endregion

	//region Methods

	public boolean intersects(Interval other)
	{
		return mStart < other.mEnd && mEnd > other.mStart;
	}

	//endregion

	//region Object impl

	@Override
	public String toString()
	{
		return "<" + mStart + "; " + mEnd + ">";
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Interval interval = (Interval) o;

		return mEnd == interval.mEnd && mStart == interval.mStart;
	}

	@Override
	public int hashCode()
	{
		int result = mStart;
		result = 31 * result + mEnd;
		return result;
	}

	//endregion
}
