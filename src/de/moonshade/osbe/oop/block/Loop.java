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

package de.moonshade.osbe.oop.block;

import de.moonshade.osbe.Main;
import de.moonshade.osbe.oop.Block;
import de.moonshade.osbe.oop.Context;
import de.moonshade.osbe.oop.Generator;
import de.moonshade.osbe.oop.MainClass;
import de.moonshade.osbe.oop.NoopContext;
import de.moonshade.osbe.oop.exception.GeneratorException;

public class Loop extends Block {

	boolean singleLine;

	public Loop(Context context, String line, int absoluteTime, boolean singleLine) {
		this.parentContext = context;
		this.content = line;
		this.absoluteTime = absoluteTime;
		this.singleLine = singleLine;
	}

	@Override
	public void analyse() throws GeneratorException {
		// wir suchen die Loop-Parameterklammern
		char[] chars = content.toCharArray();
		int firstBracket = 0;
		int lastBracket = 0;
		int counter = 0;
		for (int a = 0; a < chars.length; a++) {
			if (chars[a] == '(') {
				counter++;
				if (firstBracket == 0) {
					firstBracket = a;
				}
			} else if (chars[a] == ')') {
				counter--;
				if (counter == 0) {
					lastBracket = a;
					break;
				}
			}
		}

		String expression = content.substring(firstBracket + 1, lastBracket);
		String parameter[] = Generator.splitParameters(expression);

		if (Main.debug)
			System.out.println("expression: " + expression);
		if (Main.debug)
			System.out.println("content string: " + contentString);

		if (parameter.length != 3) {
			throw new GeneratorException(null, -1, "Too few or too much parameters");
		}

		int time = absoluteTime + Generator.encodeIntegerExpression(this, parameter[0]);
		int repeats = Generator.encodeIntegerExpression(this, parameter[1]);
		int timeAdd = Generator.encodeIntegerExpression(this, parameter[2]);

		// Aktualisierung für die Progressbar
		boolean progressAkt = false;
		if (parentContext instanceof MainClass) {
			progressAkt = true;
		}
		if (progressAkt) {
			Generator.loop = true;
			Generator.totalLoopCount = repeats;
		}

		if (Main.debug)
			System.out.println("time: " + time);
		if (Main.debug)
			System.out.println("repeats: " + repeats);
		if (Main.debug)
			System.out.println("timeAdd: " + timeAdd);

		for (int a = 1; a <= repeats; a++) {
			if (progressAkt) {
				// Aktualisierung für die Progressbar
				Generator.currentLoopCount = a;
			}
			if (Main.debug)
				System.out.println("Wiederholung: " + a + ", Zeit: " + time);
			Generator generator = new Generator();
			generator.compile(new NoopContext(this), contentString, time);
			time += timeAdd;
		}

		if (progressAkt) {
			Generator.loop = false;
			Generator.totalLoopCount = 1;
			Generator.currentLoopCount = 0;
		}
	}

}
