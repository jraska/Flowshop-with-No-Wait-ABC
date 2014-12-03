package com.jraska.vsb.or1.io;

import com.jraska.common.ArgumentCheck;
import com.jraska.vsb.or1.data.Input;
import com.jraska.vsb.or1.data.Machine;

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
		int size = scanner.nextInt();

		List<Machine> jobs = new ArrayList<Machine>();

		for (int i = 0; i < machinesCount; i++)
		{
			int[] data = new int[size];
			for (int j = 0; j < size; j++)
			{
				data[j] = scanner.nextInt();
			}

			jobs.add(new Machine(data));
		}

		Input input = new Input(jobs);

		return input;
	}

	//endregion
}
