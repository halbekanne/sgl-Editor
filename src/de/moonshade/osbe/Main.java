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

package de.moonshade.osbe;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import de.moonshade.osbe.analyse.AnalyseStructureThread;
import de.moonshade.osbe.gui.DefaultGUI;
import de.moonshade.osbe.gui.GUI;
import de.moonshade.osbe.menuhandler.Action;
import de.moonshade.osbe.menuhandler.MenuHandler;
import de.moonshade.osbe.serializable.Options;

public class Main {

	private GUI gui = null;
	private MenuHandler handler = null;
	private Options options = null;
	private ArrayList<String> bufferedLines;

	/**
	 * @param args
	 */
	public void start(String[] args) {
		// TODO Auto-generated method stub

		// First of all, we have to get the Options which we maybe saved the
		// last time using this application
		options = this.getOptions();

		// Get the MenuHandler instance for menu actions
		handler = new MenuHandler();

		// We should make a GUI for the application
		gui = new DefaultGUI(this, options);
		// Initialize the GUI
		// gui.init("", new Point(20,20), 800, 600);
		gui.init("osu! Storyboard Script Editor");
		gui.onClose(this);
		gui.setMenuHandler(handler);

		gui.createMenu("File");
		gui.createMenuItem("New", 0, Action.New);
		gui.createMenuItem("Open", 0, Action.Open);
		gui.createMenuItem("Save", 0, Action.Save);
		
		gui.createMenu("Edit");
		gui.createMenuItem("Undo", 1, Action.New);
		gui.createMenuItem("Redo", 1, Action.Open);
		gui.createMenuItem("Cut", 1, Action.Save);
		gui.createMenuItem("Copy", 1, Action.Save);
		gui.createMenuItem("Paste", 1, Action.Save);
		
		gui.createMenu("View");
		gui.createMenuItem("Outline", 2, Action.New);
		gui.createMenuItem("Files", 2, Action.Open);
		gui.createMenuItem("Cut", 2, Action.Save);
		gui.createMenuItem("Copy", 2, Action.Save);
		gui.createMenuItem("Paste", 2, Action.Save);
		
		gui.start();
		
		Thread analyzeStructure = new AnalyseStructureThread(gui);
		analyzeStructure.start();

	}

	private Options getOptions() {
		Options newOptions = new Options();
		try {
			ObjectInputStream objIn = new ObjectInputStream(
					new BufferedInputStream(new FileInputStream("options.ser")));
			newOptions = (Options) objIn.readObject();
			objIn.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			saveOptions(newOptions);
			// e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return newOptions;
	}

	private void saveOptions(Options options) {
		ObjectOutputStream objOut;
		try {
			objOut = new ObjectOutputStream(new BufferedOutputStream(
					new FileOutputStream("options.ser")));
			objOut.writeObject(options);
			objOut.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * If the main window is about to close, this method will be called
	 */
	public void onGUIClosed() {
		System.out.println("blaa");
		getOptionsCloseUpdate();
		saveOptions(options);
	}

	/**
	 * Updates the options when the application is about to close
	 */
	private void getOptionsCloseUpdate() {
		options.setLastHeight(gui.getHeight());
		options.setLastWidth(gui.getWidth());
		options.setLastLocation(gui.getLocation());
	}

}
