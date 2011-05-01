/*
 * Copyright (c) 2011 Dominik Halfkann.
 * 
 * This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 3 of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this program; if not, see <http://www.gnu.org/licenses/>.
 * 
 * Contributors:
 *     Dominik Halfkann
 */

package de.moonshade.osbe.oop;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import de.moonshade.osbe.Main;
import de.moonshade.osbe.oop.exception.GeneratorException;

public abstract class Context {

	private List<Variable> variables = new ArrayList<Variable>();
	private List<String> altVatiableNames = new ArrayList<String>();
	private int time = 0;
	protected Context parentContext = null;

	public List<String> getAltVatiableNames() {
		return altVatiableNames;
	}
	
	public void clearVariables() {
		// TODO Auto-generated method stub
		variables = new ArrayList<Variable>();
		altVatiableNames = new ArrayList<String>();
	}

	public void createVariable(SpriteVariable sprite) throws GeneratorException {
		// TODO Auto-generated method stub
		Variable sameVariable = this.searchVariable(sprite.getName());
		if (sameVariable == null) {
			variables.add(sprite);
			if (Main.debug)
				System.out.println("Sprite Variable angelegt: " + sprite.getName() + " = " + sprite.getPath());
		} else {
			throw new GeneratorException(null, -1, "The variable \"" + sprite.getName() + "\" has already been created");
		}
	}

	public void createVariable(String name, boolean value) throws GeneratorException {
		// TODO Auto-generated method stub
		Variable sameVariable = this.searchVariable(name);
		if (sameVariable == null) {
			variables.add(new BooleanVariable(name, value));
			if (Main.debug)
				System.out.println("Boolean Variable angelegt: " + name + " = " + value);
		} else {
			throw new GeneratorException(null, -1, "The variable \"" + name + "\" has already been created");
		}
	}

	public void createVariable(String name, float value) throws GeneratorException {
		Variable sameVariable = this.searchVariable(name);
		if (sameVariable == null) {
			variables.add(new FloatVariable(name, value));
			if (Main.debug)
				System.out.println("Float Variable angelegt: " + name + " = " + value);
		} else {
			throw new GeneratorException(null, -1, "The variable \"" + name + "\" has already been created");
		}
	}

	public void createVariable(String name, int value) throws GeneratorException {
		// TODO Auto-generated method stub
		Variable sameVariable = this.searchVariable(name);
		if (sameVariable == null) {
			variables.add(new IntVariable(name, value));
			if (Main.debug)
				System.out.println("Int Variable angelegt: " + name + " = " + value);
		} else {
			throw new GeneratorException(null, -1, "The variable \"" + name + "\" has already been created");
		}
	}

	/*
	 * public void createVariable(String name, Object value) { // TODO
	 * Auto-generated method stub
	 * 
	 * }
	 */

	public void createVariable(String name, String value) throws GeneratorException {
		Variable sameVariable = this.searchVariable(name);
		if (sameVariable == null) {
			variables.add(new StringVariable(name, value));
			if (Main.debug)
				System.out.println("String Variable angelegt: " + name + " = " + value);
		} else {
			throw new GeneratorException(null, -1, "The variable \"" + name + "\" has already been created");
		}
	}

	public List<Variable> getAllVariables() {
		return variables;
	}

	public Context getParentContext() {
		if (Main.debug)
			System.out.println("This context " + this.getClass().getName() + " has parent context " + parentContext);
		return parentContext;
	}

	public Variable searchVariable(String variableName) {
		// TODO Auto-generated method stub

		if (Main.debug)
			System.out.println("suche variable " + variableName);

		if (Main.debug) System.out.println("waaaaaa 0");
		
		// Suche Variable hier
		if (Main.debug)
			System.out.println(this.getClass().getName());
		ListIterator<Variable> i = variables.listIterator();
		while (i.hasNext()) {
			if (Main.debug) System.out.println("waaaaaa 0.1");
			if (Main.debug)
				System.out.println(variables.get(i.nextIndex()).getName());
			if (Main.debug)
				System.out.println(variableName);
			if (variables.get(i.nextIndex()).getName().equals(variableName)) {
				if (Main.debug) System.out.println("waaaaaa 1");
				return variables.get(i.nextIndex());
			}
			// Suche Variable hier, unter alternativen Namen
			else {
				if (altVatiableNames.size() > i.nextIndex() && altVatiableNames.get(i.nextIndex()) != null
						&& altVatiableNames.get(i.nextIndex()).equals("")) {
					if (altVatiableNames.get(i.nextIndex()).equals(variableName)) {
						if (Main.debug) System.out.println("waaaaaa 2");
						return variables.get(i.nextIndex());
					}
				}
			}
			i.next();
		}

		// Suche im übergeordneten context, rekursiver Aufruf
		Context currentContext = this.getParentContext();
		while (currentContext != null) {
			if (Main.debug) System.out.println("waaaaaa");
			if (Main.debug)
				System.out.println(currentContext.getClass().getName());

			ListIterator<Variable> iPar = currentContext.getAllVariables().listIterator();
			while (iPar.hasNext()) {
				if (currentContext.getAllVariables().get(iPar.nextIndex()).getName().equals(variableName)) {
					if (Main.debug) System.out.println("waaaaaa 3");
					return currentContext.getAllVariables().get(iPar.nextIndex());
				}
				// Suche Variable im Parent, unter alternativen Namen
				else {
					if (currentContext.getAltVatiableNames().size() > iPar.nextIndex()
							&& currentContext.getAltVatiableNames().get(iPar.nextIndex()) != null
							&& currentContext.getAltVatiableNames().get(iPar.nextIndex()).equals("")) {
						if (currentContext.getAltVatiableNames().get(iPar.nextIndex()).equals(variableName)) {
							if (Main.debug) System.out.println("waaaaaa 4");
							return currentContext.getAllVariables().get(iPar.nextIndex());
						}
					}
				}
				iPar.next();
			}

			currentContext = currentContext.getParentContext();
			if (Main.debug)
				System.out.println("context: " + currentContext);
		}
		if (Main.debug)
			System.out.println("Variable " + variableName + " wurde nicht gefunden :(");
		return null;
	}

	public void setVariable(Variable variable, boolean value) {
		((BooleanVariable) variable).setValue(value);
		if (Main.debug)
			System.out.println("Boolean Variable verändert: " + variable.getName() + " = " + value);
	}

	public void setVariable(Variable variable, float value) {
		((FloatVariable) variable).setValue(value);
		if (Main.debug)
			System.out.println("Float Variable verändert: " + variable.getName() + " = " + value);
	}

	public void setVariable(Variable variable, int value) {
		// System.out.println(variable.getClass().getDeclaringClass());
		// ((IntVariable)
		// variables.get(variables.indexOf(variable))).setValue(value);
		((IntVariable) variable).setValue(value);
		if (Main.debug)
			System.out.println("Int Variable verändert: " + variable.getName() + " = " + value);
	}

	public void setVariable(Variable variable, String value) {
		((StringVariable) variables.get(variables.indexOf(variable))).setValue(value);
		if (Main.debug)
			System.out.println("String Variable verändert: " + variable.getName() + " = " + value);
	}

}
