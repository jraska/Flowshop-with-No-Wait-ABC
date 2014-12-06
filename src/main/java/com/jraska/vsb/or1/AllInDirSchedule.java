package com.jraska.vsb.or1;

import com.jraska.vsb.or1.data.Output;

import java.io.File;
import java.io.FileFilter;

public class AllInDirSchedule extends Program
{
	//region Main methods

	public static void main(String[] args) throws Exception
	{
		AllInDirSchedule allInDirSchedule = new AllInDirSchedule();
		allInDirSchedule.run(args);
	}

	//endregion

	//region Program overrides

	@Override
	protected File[] getFiles(String[] args)
	{
		File dir = new File(args[0]);

		if (!dir.exists())
		{
			throw new IllegalArgumentException(String.format("There is no dir on this path: %s", dir.getAbsolutePath()));
		}

		if (dir.isFile())
		{
			throw new IllegalArgumentException(String.format("%s is file, no dir", dir.getAbsolutePath()));
		}

		return dir.listFiles(getFileFilter());
	}

	@Override
	protected void writeFinalOutput(Output output)
	{
		_out.println("Found makespan: " + output.getMakespan());
	}

	//endregion

	//region Methods

	protected FileFilter getFileFilter()
	{
		return new FileFilter()
		{
			@Override
			public boolean accept(File pathname)
			{
				String name = pathname.getName().toLowerCase();

				if (!name.endsWith(".txt"))
				{
					return false;
				}

				String pureName = name.substring(0, name.length() - 4); //-4 is for txt extension

				//accept only digitally names files
				for (char c : pureName.toCharArray())
				{
					if (!Character.isDigit(c)) return false;
				}

				return true;
			}
		};
	}

	//endregion
}
