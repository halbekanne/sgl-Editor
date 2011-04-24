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

/**
 * 
 */
package de.moonshade.osbe;

import java.io.FileNotFoundException;
import java.io.PrintStream;

/**
 * @author Dominik Halfkann
 * 
 */
public class Run {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		java.io.FileOutputStream outstream = null;
		try {
			outstream = new java.io.FileOutputStream("error.txt");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		System.setErr(new PrintStream(outstream));
		System.setOut(new PrintStream(outstream));
		
		Main main = new Main();
		main.start(args);
	}

}
