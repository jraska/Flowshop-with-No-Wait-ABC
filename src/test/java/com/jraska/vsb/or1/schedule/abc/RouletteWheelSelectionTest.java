package com.jraska.vsb.or1.schedule.abc;

import org.junit.Test;

import java.util.Random;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class RouletteWheelSelectionTest
{
	//region Test Methods

	@Test
	public void testSelection() throws Exception
	{
		double[] fits = {0.4, 0.2, 0.3, 0.1};
		int beesCount = 4;
		Bee[] bees = new Bee[beesCount];
		for (int i = 0; i < beesCount; i++)
		{
			bees[i] = mock(Bee.class);
			doReturn(fits[i]).when(bees[i]).getFitnessValue();
		}

		RouletteWheelSelection selection = new RouletteWheelSelection(new SingleDoublePseudoRandom(0.5));

		Bee bee = selection.selectBee(bees, 1);
		assertThat(bee, equalTo(bees[1]));
	}


	//endregion

	//region Nested classes

	public static class SingleDoublePseudoRandom extends Random
	{
		private final double mValue;

		public SingleDoublePseudoRandom(double value)
		{
			if (value < 0 || value > 1)
			{
				throw new IllegalArgumentException("value must be int <0;1>");
			}

			mValue = value;
		}

		@Override
		public double nextDouble()
		{
			return mValue;
		}
	}

	//endregion
}
