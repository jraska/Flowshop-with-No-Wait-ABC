package com.jraska.vsb.or1.io;

import com.jraska.vsb.or1.data.Input;

import java.io.InputStream;

public interface IInputParser
{
	//region Methods

	Input parse(InputStream inputStream);

	//endregion
}
