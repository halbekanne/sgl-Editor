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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ActionSaveAs extends MenuAction {

	@Override
	public void start() {

			File file = gui.showFileChooser("Save");
			if (file != null) {
				try {
					if (!file.getName().contains(".")) {
						file = new File(file.getAbsolutePath() + ".sgl");
					}
					gui.getMainClassContentArea().write(new FileWriter(file));
					gui.setFileName(file.getAbsolutePath());

					// Unlocks the file
					file.setReadable(true);
					file.setWritable(true);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		
		gui.setSaved(true);
	}

}
