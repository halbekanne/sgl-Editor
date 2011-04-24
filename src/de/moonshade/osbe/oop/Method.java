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

public class Method extends Context {

	List<CodeItem> codeItems = new ArrayList<CodeItem>();

	@Override
	public void createVariable(String name, boolean value) {
		// TODO Auto-generated method stub

	}

	@Override
	public void createVariable(String name, int value) {
		// TODO Auto-generated method stub

	}

	@Override
	public void createVariable(String name, String value) {
		// TODO Auto-generated method stub

	}

	@Override
	public Context getParentContext() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Variable searchVariable(String variableName) {
		// TODO Auto-generated method stub
		return null;
	}

}
