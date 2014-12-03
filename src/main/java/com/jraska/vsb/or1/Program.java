package com.jraska.vsb.or1;

import com.jraska.vsb.or1.data.Input;
import com.jraska.vsb.or1.data.Output;
import com.jraska.vsb.or1.io.SimpleTextParser;
import com.jraska.vsb.or1.io.ToStringOutputWriter;
import com.jraska.vsb.or1.schedule.SimpleScheduler;
import com.jraska.vsb.or1.schedule.validation.NoWaitFlowShopValidator;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintStream;
import java.util.List;

public final class Program
{
	//region Main method

	public static void main(String args[]) throws Exception
	{
		Program program = new Program();
		program.run(args);
	}

	//endregion

	//region Fields

	private final PrintStream out;

	//endregion

	//region Constructors

	public Program()
	{
		this(System.out);
	}

	public Program(PrintStream out)
	{
		this.out = out;
	}

	//endregion

	//region methods

	public void run(String args[]) throws Exception
	{
		if (args.length < 1)
		{
			throw new IllegalArgumentException("There must be arg with file path.");
		}

		String path = args[0];

		FileInputStream fileInputStream = new FileInputStream(new File(path));
		SimpleTextParser parser = new SimpleTextParser();
		Input input = parser.parse(fileInputStream);
		fileInputStream.close();

		SimpleScheduler scheduler = new SimpleScheduler();
		Output output = scheduler.schedule(input);

		ToStringOutputWriter toStringOutputWriter = new ToStringOutputWriter();
		toStringOutputWriter.write(output, System.out);

		NoWaitFlowShopValidator validator = new NoWaitFlowShopValidator();
		List<String> validationResult = validator.validate(output);

		if (!validationResult.isEmpty())
		{
			out.println();
			out.println("-------------------");
			out.println("RESULT IS NOT VALID");
			out.println("-------------------");

			for (String error : validationResult)
			{
				out.println(error);
			}
		}
	}

	//endregion
}
