package com.jraska.vsb.or1.io;

import com.jraska.common.ArgumentCheck;
import com.jraska.vsb.or1.data.Input;
import com.jraska.vsb.or1.data.Job;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SimpleTextParser implements IInputParser
{
	//region IInputParser impl

	@Override
	public Input parse(InputStream inputStream)
	{
		ArgumentCheck.notNull(inputStream);

		Scanner scanner = new Scanner(inputStream);

		int machinesCount = scanner.nextInt();
		int jobsCount = scanner.nextInt();

		int[][] allData = new int[machinesCount][jobsCount];

		for (int i = 0; i < machinesCount; i++)
		{
			for (int j = 0; j < jobsCount; j++)
			{
				allData[i][j] = scanner.nextInt();
			}
		}

		List<Job> jobs = new ArrayList<Job>();
		for (int i = 0; i < jobsCount; i++)
		{
			int[] durations = new int[machinesCount];

			for (int j = 0; j < machinesCount; j++)
			{
				durations[j] = allData[j][i];
			}

			jobs.add(new Job(durations));
		}

		Input input = new Input(machinesCount, jobs);

		return input;
	}

	//endregion
}
