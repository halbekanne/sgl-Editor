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

public interface Variable {

	public String getName();

	public String getStringValue() throws GeneratorException;
	/*
	 * public int getIntValue(); public void setValue(int value); public boolean
	 * getBooleanValue(); public void setValue(boolean value); public String
	 * getStringValue(); public void setValue(String value);
	 */

	public void setName(String name);

}
