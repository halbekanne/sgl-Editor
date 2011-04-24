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

package de.moonshade.osbe.oop.exception;

public class GeneratorException extends Exception {

	private static final long serialVersionUID = 1L;
	private int line;
	private String context;

	public GeneratorException(String context, int line, String msg) {
		super(msg);
		setContext(context);
		setLine(line);
	}

	public String getContext() {
		return context;
	}

	public int getLine() {
		return line;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public void setLine(int line) {
		this.line = line;
	}

}
