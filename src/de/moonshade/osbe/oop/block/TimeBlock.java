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

import de.moonshade.osbe.oop.Block;
import de.moonshade.osbe.oop.Context;
import de.moonshade.osbe.oop.Generator;
import de.moonshade.osbe.oop.exception.GeneratorException;

public class TimeBlock extends Block {

	boolean singleLine;

	public TimeBlock(Context context, String line, int absoluteTime, boolean singleLine) {
		this.parentContext = context;
		this.content = line;
		this.absoluteTime = absoluteTime;
		this.singleLine = singleLine;
	}

	@Override
	public void analyse() throws GeneratorException {

		// wir suchen das Ende der AT-Zeitangabenklammer
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
		int time = absoluteTime + Generator.encodeIntegerExpression(parentContext, expression);

		Generator generator = new Generator();

		if (singleLine) {
			// Befehl kommt direkt nach den runden Klammern
			String command = content.substring(lastBracket + 1).trim();

			generator.compile(this, command, time);
			// Befehl wird mit der erforderlichen zeit ausgeführt

		} else {
			generator.compile(this, contentString, time);
		}

	}

}
