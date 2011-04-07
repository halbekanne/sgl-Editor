package de.moonshade.osbe.oop;

public interface Context {

	public void createVariable(String name, int value);
	public void createVariable(String name, String value);
	public void createVariable(String name, boolean value);
	public void createVariable(String name, Object value);
	public Variable searchVariable(String variableName);
	
	
}
