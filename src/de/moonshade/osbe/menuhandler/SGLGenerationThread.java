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

import java.io.FileNotFoundException;
import java.io.PrintStream;

import javax.swing.JOptionPane;

import de.moonshade.osbe.gui.GUI;
import de.moonshade.osbe.oop.Generator;
import de.moonshade.osbe.oop.exception.GeneratorException;

public class SGLGenerationThread implements Runnable {

	GUI gui;

	public SGLGenerationThread(GUI gui) {
		this.gui = gui;
	}

	@Override
	public void run() {
		Generator generator = new Generator();
		ProgressDialogThread pdThread = new ProgressDialogThread(gui, generator);
		Thread thread = new Thread(pdThread);
		thread.start();
		try {

			generator.startCompiler(gui);
		} catch (GeneratorException e) {
			// TODO Auto-generated catch block
			pdThread.generator.finished = true;
			JOptionPane.showMessageDialog(
					null,
					e.getClass().getSimpleName() + " in " + e.getContext() + ", line "
							+ e.getLine() + ":\n" + e.getMessage() + ".", "Generator Error",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		} catch (Exception e) {
			pdThread.generator.finished = true;
			StackTraceElement[] errStack = e.getStackTrace();
			String stackTrace = "";
			for (StackTraceElement elem : errStack) {
				stackTrace += "at " + elem.toString() + "\n";
			}
			JOptionPane
					.showMessageDialog(
							null,
							"An unhandled Exception occured. Please inform the author about this problem by opening a new issue at GitHub (https://github.com/MoonShade/osbe/issues) or by sending an E-Mail to osbe.issues@googlemail.com\nPlease also send the following information:\n\n"
									+ e.toString()
									+ "\n"
									+ stackTrace
									+ "\n\nYou can also find this stacktrace in the file error.txt",
							"Unhandled Exception", JOptionPane.ERROR_MESSAGE);

			java.io.FileOutputStream outstream = null;
			try {
				outstream = new java.io.FileOutputStream("error.txt");
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			System.setErr(new PrintStream(outstream));
			System.setOut(new PrintStream(outstream));

			e.printStackTrace();
		}

	}

}
