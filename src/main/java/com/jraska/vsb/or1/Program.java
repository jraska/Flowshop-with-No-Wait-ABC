package com.jraska.vsb.or1;

import java.util.Arrays;

public class Program
{
	//region Main method

	public static void main(String args[])
	{
		Program program = new Program();
		program.run(args);
	}

	//endregion

	//region methods

	public void run(String args[])
	{
		System.out.println(Arrays.toString(args)); //now only prints arguments
	}

	//endregion
}
