package com.jraska.vsb.or1;

import org.junit.Test;

public class ProgramTest
{
	//region Test Methods

	@Test
	public void testSimpleRun() throws Exception
	{
		Program program = new Program();
		program.run(new String[]{});
	}

	//endregion
}
