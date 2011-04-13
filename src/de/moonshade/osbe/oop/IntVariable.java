package de.moonshade.osbe.oop;

import de.moonshade.osbe.oop.exception.GeneratorException;

public class IntVariable implements Variable {

	private String name;
	private int value;

	public IntVariable(String name, int value) {
		this.name = name;
		this.value = value;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return this.name;
	}

	@Override
	public void setName(String name) {
		// TODO Auto-generated method stub
		this.name = name;
	}

	public int getValue() {
		// TODO Auto-generated method stub
		return value;
	}

	public void setValue(int value) {
		// TODO Auto-generated method stub
		this.value = value;
	}

	@Override
	public String getStringValue() throws GeneratorException {
		// TODO Auto-generated method stub
		return String.valueOf(getValue());
	}

}
