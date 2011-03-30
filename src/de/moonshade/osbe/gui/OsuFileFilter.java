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

package de.moonshade.osbe.gui;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class OsuFileFilter extends FileFilter {

	private final String[] extensions = { ".osu", ".osb" };

	@Override
	public boolean accept(File f) {
		if (f == null)
			return false;

		// show directories
		if (f.isDirectory())
			return true;

		// true if the file has the correct extension
		for (String extension : extensions) {
			return f.getName().toLowerCase().endsWith(extension);
		}

		return false;
	}

	@Override
	public String getDescription() {
		return "OSU-Datei (*.osu, *.osb)";
	}

}
