package de.moonshade.osbe.oop;

import de.moonshade.osbe.oop.exception.GeneratorException;

public class StringVariable implements Variable {

	private String name;
	private String value;
	
	public StringVariable(String name, String value) {
		// TODO Auto-generated constructor stub
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


	public String getValue() {
		// TODO Auto-generated method stub
		return value;
	}


	public void setValue(String value) {
		// TODO Auto-generated method stub
		this.value = value;
	}

	@Override
	public String getStringValue() throws GeneratorException {
		// TODO Auto-generated method stub
		return getValue();
	}

}
