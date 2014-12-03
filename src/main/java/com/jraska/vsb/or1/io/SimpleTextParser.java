package com.jraska.vsb.or1.io;

import com.jraska.common.ArgumentCheck;
import com.jraska.vsb.or1.data.Input;
import com.jraska.vsb.or1.data.Job;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public final class SimpleTextParser implements IInputParser
{
	//region IInputParser impl

	@Override
	public Input parse(InputStream inputStream)
	{
		ArgumentCheck.notNull(inputStream);

		Scanner scanner = new Scanner(inputStream);

		int machinesCount = scanner.nextInt();
		int jobsCount = scanner.nextInt();

		int[][] allData = new int[jobsCount][machinesCount];

		for (int i = 0; i < machinesCount; i++)
		{
			for (int j = 0; j < jobsCount; j++)
			{
				allData[j][i] = scanner.nextInt();
			}
		}

		List<Job> jobs = new ArrayList<Job>();
		for (int i = 0; i < jobsCount; i++)
		{
			jobs.add(new Job(allData[i]));
		}

		Input input = new Input(machinesCount, jobs);

		return input;
	}

	//endregion
}
