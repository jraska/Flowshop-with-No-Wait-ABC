package com.jraska.vsb.or1.schedule;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RandomPositionGeneratorTest
{
	//region Test Methods

	@Test
	public void testUniqueNumbers() throws Exception
	{
		RandomPositionGenerator generator = new RandomPositionGenerator(10);

		for (int i = 0; i < 1000; i++)
		{
			int[] solution = generator.generate();

			assertDistinct(solution);
		}
	}

	//endregion

	//region Methods

	private void assertDistinct(int[] solution)
	{
		Set<Integer> set = new HashSet<Integer>();

		for (int value : solution)
		{
			assertFalse("Numbers in solutions are not distinct", set.contains(value));

			set.add(value);
		}

		for (int i = 0; i < solution.length; i++)
		{
			assertTrue("Generated solution does not contain sequantial number", set.contains(i));
		}
	}

	//endregion
}
