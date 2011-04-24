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

public class Root {

	private MainClass main = new MainClass();
	private List<Class> classes = new ArrayList<Class>();

	/**
	 * @return the classes
	 */
	public List<Class> getClasses() {
		return classes;
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
	public void setClasses(List<Class> classes) {
		this.classes = classes;
	}

	/**
	 * @param main
	 *            the main to set
	 */
	public void setMain(MainClass main) {
		this.main = main;
	}

}
