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

import javax.swing.ProgressMonitor;

import de.moonshade.osbe.gui.GUI;
import de.moonshade.osbe.oop.Generator;

public class ProgressDialogThread implements Runnable {

	GUI gui;
	public Generator generator;
	public ProgressMonitor pm;

	public ProgressDialogThread(GUI gui, Generator generator) {
		this.gui = gui;
		this.generator = generator;
	}

	@Override
	public void run() {
		pm = new ProgressMonitor(gui.getContentPanel(), "Generating Storyboard...", "", 0, 100);
		pm.setMillisToPopup(100);
		// pm.setMillisToDecideToPopup(100);

		while (!generator.finished) {
			// if (Main.debug) System.out.println(generator.currentLine +
			// "-------------------------------------------" +
			// generator.currentLine / generator.totalLines * 100);
			int progress = (int) Math
					.round((generator.currentLine / (generator.totalLines * 1.0)) * 100.0);
			if (progress >= 100) {
				progress = 99;
			}
			pm.setProgress(progress);
			pm.setNote("Analysing line " + generator.currentLine + " / " + generator.totalLines);

			if (pm.isCanceled()) {
				Generator.abortGeneration = true;
				generator.finished = true;
			}

			try {
				Thread.sleep(100);
			} catch (Exception ex) {
			}
		}
		pm.setProgress(100);
		// if (Main.debug) System.out.println("fertig----------------");
	}

}
