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
package de.moonshade.osbe.serializable;

import java.awt.Point;
import java.io.Serializable;
import java.util.Iterator;

import ca.beq.util.win32.registry.KeyIterator;
import ca.beq.util.win32.registry.RegistryKey;
import ca.beq.util.win32.registry.RegistryValue;
import ca.beq.util.win32.registry.RootKey;

/**
 * @author Dominik Halfkann
 * 
 */
public class Options implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5574229043155334192L;
	private int lastWidth = 800;
	private int lastHeight = 600;
	private Point lastLocation = new Point(100, 100);
	private String lastPath = null; // System.getProperty("user.home");

	public Options() {
		// set initial lastPath
		/*
		if (System.getProperty("os.name").startsWith("Windows")) {
			RegistryKey r = new RegistryKey(RootKey.HKEY_CLASSES_ROOT,
					"osu!\\shell\\open\\command");
			if (r.hasValues()) {
				Iterator i = r.values();
				while (i.hasNext()) {
					RegistryValue v = (RegistryValue) i.next();
					lastPath = v.getStringValue().substring(1,v.getStringValue().indexOf("osu!") + 4) + "\\songs";
				}
			}
		} else {
		*/
			lastPath = System.getProperty("user.home");
			
		//}
	}

	/**
	 * @return the lastWidth
	 */
	public int getLastWidth() {
		return lastWidth;
	}

	/**
	 * @param lastWidth
	 *            the lastWidth to set
	 */
	public void setLastWidth(int lastWidth) {
		this.lastWidth = lastWidth;
	}

	/**
	 * @return the lastHeight
	 */
	public int getLastHeight() {
		return lastHeight;
	}

	/**
	 * @param lastHeight
	 *            the lastHeight to set
	 */
	public void setLastHeight(int lastHeight) {
		this.lastHeight = lastHeight;
	}

	/**
	 * @return the lastLocation
	 */
	public Point getLastLocation() {
		return lastLocation;
	}

	/**
	 * @param lastLocation
	 *            the lastLocation to set
	 */
	public void setLastLocation(Point lastLocation) {
		this.lastLocation = lastLocation;
	}

	public void setLastPath(String lastPath) {
		this.lastPath = lastPath;
	}

	public String getLastPath() {
		return lastPath;
	}

}
