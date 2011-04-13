package de.moonshade.osbe.oop;

import java.util.ArrayList;
import java.util.List;

import de.moonshade.osbe.oop.exception.GeneratorException;

public interface Variable {

	
	public String getName();
	public void setName(String name);
	public String getStringValue() throws GeneratorException;
	/*
	public int getIntValue();
	public void setValue(int value);
	public boolean getBooleanValue();
	public void setValue(boolean value);
	public String getStringValue();
	public void setValue(String value);
	*/
	
}
