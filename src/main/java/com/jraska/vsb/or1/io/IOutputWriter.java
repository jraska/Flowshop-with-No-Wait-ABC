package com.jraska.vsb.or1.io;

import com.jraska.vsb.or1.data.Output;

import java.io.OutputStream;

public interface IOutputWriter
{
	//region Methods

	void write(Output output, OutputStream outputStream);

	//endregion
}
