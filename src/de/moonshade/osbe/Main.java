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

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import de.moonshade.osbe.gui.DefaultGUI;
import de.moonshade.osbe.gui.GUI;
import de.moonshade.osbe.menuhandler.Action;
import de.moonshade.osbe.menuhandler.MenuHandler;
import de.moonshade.osbe.oop.CodeItem;
import de.moonshade.osbe.oop.Generator;
import de.moonshade.osbe.oop.MainClass;
import de.moonshade.osbe.oop.Root;
import de.moonshade.osbe.oop.exception.GeneratorException;
import de.moonshade.osbe.oop.line.NewVariableDefinition;
import de.moonshade.osbe.oop.line.VariableDefinition;
import de.moonshade.osbe.serializable.Options;

public class Main {

	public static boolean debug = false;
	
	private GUI gui = null;
	private MenuHandler handler = null;
	private Options options = null;
	private ArrayList<String> bufferedLines;
	private Generator generator = new Generator();
	// Da es viel Zeit braucht, um sich eine ScriptEngine zu holen, wird dies nur einmal am Anfang gemacht
	public static ScriptEngine javaScriptEvaluator = new ScriptEngineManager().getEngineByName("javascript");

	public void generate() throws GeneratorException {
		// Aus GUI sollen die erforderlichen Informationen herausgenommen
		// werden, in Objekte gestopft werden und dann generiert werden
		String mainContent = gui.getMainClassContent();

		// Wir besorgen uns ein Root Element
		Root root = new Root();

		// Wir gehen systematisch durch den Code und analysieren ihn grob
		String[] lines = mainContent.split("\\n");
		int lineCounter = 1;
		for (String line : lines) {
			line = line.trim();
			if (line == null || line.length() == 0) {
				lineCounter++;
				continue;
			}

			CodeItem codeItem = null;
			MainClass context = root.getMain();

			if (line.matches("\\S+\\s+\\S+\\s*=\\s*\\S+.*")) {
				if (Main.debug) System.out.println("This is a Variable definition for a new Variable");
				codeItem = new NewVariableDefinition(context, line);
			} else if (line.matches("\\S+\\s*=\\s*\\S*.*")) {
				if (Main.debug) System.out.println("This is a Variable definition");
				codeItem = new VariableDefinition(context, line);
			}

			else {
				if (Main.debug) System.out.println("This is not a Variable definition :(");
			}

			// Jetzt soll die entsprechende Zeile analysiert werden+
			if (codeItem == null) {
				throw new GeneratorException("Main", lineCounter, "Unable to parse this line");
			}
			try {
				codeItem.analyse();
			} catch (GeneratorException e) {
				e.setContext("Main");
				e.setLine(lineCounter);
				throw e;
			}

			lineCounter++;
		}

	}

	private Options getOptions() {
		Options newOptions = new Options();
		try {
			ObjectInputStream objIn = new ObjectInputStream(new BufferedInputStream(
					new FileInputStream("options.ser")));
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

	/**
	 * Updates the options when the application is about to close
	 */
	private void getOptionsCloseUpdate() {
		options.setLastHeight(gui.getHeight());
		options.setLastWidth(gui.getWidth());
		options.setLastLocation(gui.getLocation());
	}

	/**
	 * If the main window is about to close, this method will be called
	 */
	public void onGUIClosed() {
		if (Main.debug) System.out.println("blaa");
		getOptionsCloseUpdate();
		saveOptions(options);
	}

	private void saveOptions(Options options) {
		ObjectOutputStream objOut;
		try {
			objOut = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(
					"options.ser")));
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
		/*
		gui.createMenuItem("New", 0, Action.New);
		gui.createMenuItem("Open", 0, Action.Open);
		gui.createMenuItem("Save", 0, Action.Save);
	*/
		gui.createMenu("Edit");
		/*
		gui.createMenuItem("Undo", 1, Action.New);
		gui.createMenuItem("Redo", 1, Action.Open);
		gui.createMenuItem("Cut", 1, Action.Save);
		gui.createMenuItem("Copy", 1, Action.Save);
		gui.createMenuItem("Paste", 1, Action.Save);
		*/

		/*
		 * gui.createMenu("View");
		 * 
		 * gui.createMenuItem("Outline", 2, Action.New);
		 * gui.createMenuItem("Files", 2, Action.Open);
		 * gui.createMenuItem("Cut", 2, Action.Save); gui.createMenuItem("Copy",
		 * 2, Action.Save); gui.createMenuItem("Paste", 2, Action.Save);
		 */

		gui.createMenu("Generate");

		gui.createMenuItem("Generate Storyboard", 2, Action.GenerateStoryboard);
		gui.createMenuItem("Generate Storyboard from OOSBL", 2, Action.ParseOosbl);

		gui.start();
		/*
		 * Thread analyzeStructure = new AnalyseStructureThread(gui);
		 * analyzeStructure.start();
		 */

		/*
		 * try { generator.startCompiler(gui); } catch (GeneratorException e) {
		 * // TODO Auto-generated catch block
		 * JOptionPane.showMessageDialog(null, e.getClass().getSimpleName() +
		 * " in " + e.getContext() + ", line " + e.getLine() + ":\n" +
		 * e.getMessage() + ".", "Generator Error", JOptionPane.ERROR_MESSAGE);
		 * e.printStackTrace(); }
		 */

	}

}
