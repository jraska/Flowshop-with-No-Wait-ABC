package com.jraska.vsb.or1.schedule.abc;

import com.jraska.common.ArgumentCheck;
import com.jraska.vsb.or1.schedule.IObjectiveFunction;
import com.jraska.vsb.or1.schedule.IPositionGenerator;

import java.io.PrintStream;

public class DebugBee extends Bee
{
	//region Fields

	private final String mName;
	private final PrintStream mOut;

	//endregion

	//region Constructors

	public DebugBee(String name, PrintStream out, ILocalSearchStrategy localSearchStrategy, IObjectiveFunction objectiveFunction)
	{
		super(localSearchStrategy, objectiveFunction);

		ArgumentCheck.notNull(name);
		ArgumentCheck.notNull(out);

		mName = name;
		mOut = out;
	}

	//endregion

	//region Properties

	public String getName()
	{
		return mName;
	}

	//endregion

	//region Bee overrides

	@Override
	public int sendScouting(IPositionGenerator generator)
	{
		int value = super.sendScouting(generator);

		log("Scouting");

		return value;
	}

	@Override
	public boolean searchForNewPosition()
	{
		boolean result = super.searchForNewPosition();

		log("Searching: Found better solution = " + result);

		return result;
	}

	@Override
	protected void onBetterFound()
	{
		super.onBetterFound();

		log("On Found better solution");
	}

	//endregion

	//region Methods

	protected void log(String message)
	{
		mOut.println("Bee " + mName + ": " + message);
	}

	//endregion
}
