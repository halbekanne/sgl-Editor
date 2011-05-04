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


public abstract class Block extends CodeItem {

	protected String contentString = "";
	protected int absoluteTime = 0;
	protected int absoluteLine = 0;

	public void contentAdd(String line) {
		contentString = contentString + "\n" + line;
	}

	public int getAbsoluteTime() {
		return absoluteTime;
	}
	
	public int getAbsoluteLine() {
		return absoluteLine;
	}

	public void setAbsoluteLine(int absoluteLine) {
		this.absoluteLine = absoluteLine;
	}
	
	public void setAbsoluteTime(int absoluteTime) {
		this.absoluteTime = absoluteTime;
	}

}
