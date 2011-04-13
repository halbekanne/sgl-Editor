package de.moonshade.osbe.oop;

import de.moonshade.osbe.oop.exception.GeneratorException;

public class BooleanVariable implements Variable {

	private String name;
	private boolean value;
	
	public BooleanVariable(String name, boolean value) {
		this.name = name;
		this.value = value;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setName(String name) {
		// TODO Auto-generated method stub
		this.name = name;
	}


	public boolean getValue() {
		// TODO Auto-generated method stub
		return value;
	}


	public void setValue(boolean value) {
		// TODO Auto-generated method stub
		this.value = value;
	}

	@Override
	public String getStringValue() throws GeneratorException {
		// TODO Auto-generated method stub
		return String.valueOf(getValue());
	}

	
	

	
}
