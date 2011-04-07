package de.moonshade.osbe.oop;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class MainClass implements Context {

	private List<CodeItem> codeItems = new ArrayList<CodeItem>();
	private List<Variable> variables = new ArrayList<Variable>();

	public void setCodeItems(List<CodeItem> codeItems) {
		this.codeItems = codeItems;
	}

	public List<CodeItem> getCodeItems() {
		return codeItems;
	}

	@Override
	public void createVariable(String name, int value) {
		// TODO Auto-generated method stub
		variables.add(new IntVariable(name,value));
		System.out.println("IntVar angelegt");
	}

	@Override
	public void createVariable(String name, String value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void createVariable(String name, Object value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Variable searchVariable(String variableName) {
		// TODO Auto-generated method stub
		
		//Suche Variable hier
		
		ListIterator<Variable> i = variables.listIterator();
		while (i.hasNext()) {
			if (variables.get(i.nextIndex()).getName().equals(variableName)) {
				return variables.get(i.nextIndex());
			}
			i.next();
		}
		
		
		return null;
	}

	@Override
	public void createVariable(String name, boolean value) {
		// TODO Auto-generated method stub
		variables.add(new BooleanVariable(name,value));
		System.out.println("BooleanVariable angelegt");
	}
	
}
