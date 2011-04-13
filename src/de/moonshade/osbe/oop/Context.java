package de.moonshade.osbe.oop;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public abstract class Context {

	private List<Variable> variables = new ArrayList<Variable>();
	private int time = 0;
	
	

	public void createVariable(String name, int value) {
		// TODO Auto-generated method stub
		variables.add(new IntVariable(name,value));
		System.out.println("Int Variable angelegt: " + name + " = " + value);
	}


	public void createVariable(String name, String value) {
		variables.add(new StringVariable(name,value));
		System.out.println("String Variable angelegt: " + name + " = " + value);
	}


	public void createVariable(String name, Object value) {
		// TODO Auto-generated method stub
		
	}


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


	public void createVariable(String name, boolean value) {
		// TODO Auto-generated method stub
		variables.add(new BooleanVariable(name,value));
		System.out.println("Boolean Variable angelegt: " + name + " = " + value);
	}

	public void createVariable(SpriteVariable sprite) {
		// TODO Auto-generated method stub
		variables.add(sprite);
		System.out.println("Sprite Variable angelegt: " + sprite.getName() + " = " + sprite.getPath());
	}

	
	public void setVariable(Variable variable, int value) {
		((IntVariable) variables.get(variables.indexOf(variable))).setValue(value);
		System.out.println("Int Variable verändert: " + variable.getName() + " = " + value);
	}
	
	public void setVariable(Variable variable, String value) {
		((StringVariable) variables.get(variables.indexOf(variable))).setValue(value);
		System.out.println("String Variable verändert: " + variable.getName() + " = " + value);
	}
	
	public void setVariable(Variable variable, boolean value) {
		((BooleanVariable) variables.get(variables.indexOf(variable))).setValue(value);
		System.out.println("Boolean Variable verändert: " + variable.getName() + " = " + value);
	}
	
	public List<Variable> getAllVariables() {
		return variables;
	}
	
}
