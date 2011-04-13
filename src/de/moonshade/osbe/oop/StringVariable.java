package de.moonshade.osbe.oop;

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

	@Override
	public int getIntValue() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setValue(int value) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean getBooleanValue() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setValue(boolean value) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getStringValue() {
		// TODO Auto-generated method stub
		return value;
	}

	@Override
	public void setValue(String value) {
		// TODO Auto-generated method stub
		this.value = value;
	}

}
