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
package de.moonshade.osbe.gui;

import java.awt.Point;
import java.io.File;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

import de.moonshade.osbe.Main;
import de.moonshade.osbe.menuhandler.Action;
import de.moonshade.osbe.menuhandler.MenuHandler;

/**
 * @author Dominik Halfkann
 * 
 */
public interface GUI {
	
	/**
	 * Initializes the GUI, hopefully builds some basic GUI stuff so that one
	 * can add some objects.
	 * 
	 * @param title
	 *            The Title of the GUI
	 * @param pos
	 *            The Position of the Frame
	 */
	public void init(String title);

	/**
	 * Makes the GUI visible for the user
	 */
	public void start();

	/**
	 * Defines an Object which has an onGUIClosed() method in order to save
	 * certain things
	 * 
	 * @param handler
	 */
	public void onClose(Main handler);

	/**
	 * Defines a Handler for handling requests which are triggered by clicking
	 * on a Menu Item
	 * 
	 * @param handler
	 */
	public void setMenuHandler(MenuHandler handler);

	/**
	 * Creates a new menu in the menu bar.
	 * 
	 * @param title
	 *            The title of the new menu
	 */
	public void createMenu(String title);

	/**
	 *
	 */
	public void createMenuItem(String title, int id, final Action effect);

	/**
	 * Returns the current height of the main frame
	 * 
	 * @return
	 */
	public int getHeight();

	/**
	 * Returns the current width of the main frame
	 * 
	 * @return
	 */
	public int getWidth();

	/**
	 * Returns the current location of the main frame
	 * 
	 * @return
	 */
	public Point getLocation();

	public RSyntaxTextArea getContentArea();

	/**
	 * Shows a FileChooser dialog, returns the chosen file.
	 * 
	 * @param title
	 *            The title of the dialog
	 * @return The chosen file if the user pressed the approve button, otherwise
	 *         null
	 */
	public File showFileChooser(String title);

}
