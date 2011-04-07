package de.moonshade.osbe.oop;

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
		return value;
	}

	@Override
	public void setValue(boolean value) {
		// TODO Auto-generated method stub
		this.value = value;
	}
	
	

	
}
