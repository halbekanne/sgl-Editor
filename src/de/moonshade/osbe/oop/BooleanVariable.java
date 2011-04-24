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
		return this.name;
	}

	@Override
	public String getStringValue() throws GeneratorException {
		// TODO Auto-generated method stub
		return String.valueOf(getValue());
	}

	public boolean getValue() {
		// TODO Auto-generated method stub
		return value;
	}

	@Override
	public void setName(String name) {
		// TODO Auto-generated method stub
		this.name = name;
	}

	public void setValue(boolean value) {
		// TODO Auto-generated method stub
		this.value = value;
	}

}
