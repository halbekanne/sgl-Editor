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

package de.moonshade.osbe.menuhandler;

import de.moonshade.osbe.oop.Generator;

public class ActionGenerateStoryboard extends MenuAction {

	@Override
	public void start() {
		// TODO Auto-generated method stub
		try {

			Generator.abortGeneration = false;

			// synchron generation
			/*
			 * Generator generator = new Generator();
			 * generator.startCompiler(gui);
			 */
			// parallel generation
			Thread thread = new Thread(new SGLGenerationThread(gui));
			thread.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
