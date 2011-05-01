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
import de.moonshade.osbe.oop.exception.GeneratorException;

public class IfCondition extends Block {

	boolean condition = false;
	boolean lastCondition;
	boolean singleLine;
	boolean elseBlock = false;

	public IfCondition(Context context, String line, boolean lastCondition, int absoluteTime,
			boolean singleLine) {
		this.parentContext = context;
		this.content = line;
		this.lastCondition = lastCondition;
		this.absoluteTime = absoluteTime;
		this.singleLine = singleLine;
	}

	public IfCondition(Context context, String line, boolean lastCondition, int absoluteTime,
			boolean singleLine, boolean elseBlock) {
		this.parentContext = context;
		this.content = line;
		this.lastCondition = lastCondition;
		this.absoluteTime = absoluteTime;
		this.singleLine = singleLine;
		this.elseBlock = elseBlock;
	}

	@Override
	public void analyse() throws GeneratorException {
		if (elseBlock) {
			Generator generator = new Generator();
			if (!lastCondition) {
				condition = true;
				if (singleLine) {
					String command = content.replace("else", "").trim();
					generator.compile(this, command, absoluteTime);
				} else {
					// wenn die behauptung wahr ist, dann sollten wir auch den
					// contextString ausführen
					// übrigens wow neuer kontext, der Block hier ist jetzt der
					// neue
					// Kontext damit wir lokale variablen und son kram finden
					generator.compile(this, contentString, absoluteTime);
				}
			}
		} else {
			// wir suchen das Ende der If-Bedingungsklammer
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
			// if (Main.debug) System.out.println("expression: " + expression);
			// if (Main.debug) System.out.println("content string: " + contentString);

			Generator generator = new Generator();

			// ist die behauptung wahr? Ist der letzte if-block wahr gewesen?
			// (für
			// else und else if)
			if (!lastCondition && Generator.encodeBooleanExpression(parentContext, expression)) {
				if (Main.debug) System.out.println("Die Bedingung ist wahr, Anweisungen werden ausgeführt.");
				condition = true;
				if (singleLine) {
					String command = content.substring(lastBracket + 1).trim();
					generator.compile(this, command, absoluteTime);
				} else {
					// wenn die behauptung wahr ist, dann sollten wir auch den
					// contextString ausführen
					// übrigens wow neuer kontext, der Block hier ist jetzt der
					// neue
					// Kontext damit wir lokale variablen und son kram finden
					generator.compile(this, contentString, absoluteTime);
				}
			} else {
				if (Main.debug) System.out
						.println("Die Bedingung ist falsch oder else-if Block, die Anweisungen werden nicht ausgeführt!");
			}
		}
		
		if (lastCondition) {
			condition = true;
		}
		
	}


	public boolean isTrue() {
		return condition;
	}

}
