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
import de.moonshade.osbe.oop.block.Method;

public class Root {

	private MainClass main = new MainClass();
	private List<Method> methods = new ArrayList<Method>();

	/**
	 * @return the classes
	 */
	public List<Method> getMethods() {
		return methods;
	}

	/**
	 * @return the main
	 */
	public MainClass getMain() {
		return main;
	}

	/**
	 * @param classes
	 *            the classes to set
	 */
	public void setMethods(List<Method> methods) {
		this.methods = methods;
	}
	
	public void addMethod(Method method) {
		this.methods.add(method);
		System.out.println();
	}
	
	public Method searchMethod(String name) {
		
		if (Main.debug) System.out.println("suche methode " + name);

		// Suche Variable hier
		if (Main.debug) System.out.println(this.getClass().getName());
		ListIterator<Method> i = methods.listIterator();
		while (i.hasNext()) {
			if (Main.debug) System.out.println(methods.get(i.nextIndex()).getName());
			if (Main.debug) System.out.println(name);
			if (methods.get(i.nextIndex()).getName().equals(name)) {
				return methods.get(i.nextIndex());
			}
			i.next();
		}
		
		
		return null;
	}

	/**
	 * @param main
	 *            the main to set
	 */
	public void setMain(MainClass main) {
		this.main = main;
	}

}
