package com.jfinal.core;

public abstract class ControllerInstancer {
	public abstract JfinalxController instance(Action action) throws InstantiationException, IllegalAccessException;
}
 