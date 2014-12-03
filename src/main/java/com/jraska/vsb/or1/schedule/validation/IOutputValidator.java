package com.jraska.vsb.or1.schedule.validation;

import com.jraska.vsb.or1.data.Output;

import java.util.List;

public interface IOutputValidator
{
	List<String> validate(Output output);
}
