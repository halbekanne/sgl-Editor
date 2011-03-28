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

import de.moonshade.osbe.gui.GUI;

public class MenuHandler {

	private MenuAction menuAction = null;

	public void doAction(Action action, GUI gui) {
		menuAction = null;
		switch (action) {
		case New:
			menuAction = new ActionNew();
			System.out.println("New clicked");
			break;
		case Open:
			menuAction = new ActionOpen();
			System.out.println("Open clicked");
			break;
		case Save:
			System.out.println("Save clicked");
			break;
		}
		System.out.println("something clicked");

		if (menuAction != null) {
			menuAction.onClick(gui);
		}
	}

}
