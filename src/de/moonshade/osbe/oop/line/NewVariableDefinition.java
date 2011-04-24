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

package de.moonshade.osbe.oop.line;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.moonshade.osbe.Main;
import de.moonshade.osbe.oop.Context;
import de.moonshade.osbe.oop.Generator;
import de.moonshade.osbe.oop.Line;
import de.moonshade.osbe.oop.SpriteVariable;
import de.moonshade.osbe.oop.exception.GeneratorException;
import de.moonshade.osbe.oop.exception.UnknownTypeException;

public class NewVariableDefinition extends Line {

	public enum Type {
		INT, STRING
	}

	int intVar;
	String stringVar;
	Context context;

	// context

	public NewVariableDefinition(Context context, String line) {
		this.context = context;
		this.content = line;
	}

	@Override
	public void analyse() throws GeneratorException {

		Pattern pattern;
		Matcher matcher;
		pattern = Pattern.compile("=");
		matcher = pattern.matcher(content);
		if (matcher.find()) {
			String definition = content.substring(0, matcher.start()).trim();
			String expression = content.substring(matcher.end()).trim();
			String type = definition.split(" ")[0].trim();
			String name = definition.split(" ")[1].trim();

			if (type.equals("int")) {
				// Parst einen arithmetischen Ausdruck in einen Integer-Wert
				int value = Generator.encodeIntegerExpression(context, expression);
				if (Main.debug) System.out.println("And the int value is: " + value);
				context.createVariable(name, value);

			} else if (type.equals("float")) {
				// Parst einen boolschen Ausdruck in einen float-Wert
				float value = Generator.encodeFloatExpression(context, expression);
				if (Main.debug) System.out.println("And the float value is: " + value);
				context.createVariable(name, value);

			} else if (type.equals("boolean")) {
				// Parst einen boolschen Ausdruck in einen boolean-Wert
				boolean value = Generator.encodeBooleanExpression(context, expression);
				if (Main.debug) System.out.println("And the boolean value is: " + value);
				context.createVariable(name, value);

			} else if (type.equals("Sprite")) {
				// Parst ein Sprite-Objekt
				if (!expression.matches("new\\s+Sprite\\(\".*")) {
					throw new GeneratorException(null, -1, "Wrong syntax or missing parameters");
				}

				pattern = Pattern.compile("\\(.*\\)");
				matcher = pattern.matcher(expression);
				if (matcher.find()) {
					String rawParameters = expression.substring(matcher.start() + 1,
							matcher.end() - 1);
					if (Main.debug) System.out.println(rawParameters);
					// Könnte Probleme geben, wenn Dateipfad Kommas enthält
					// String[] parameter = rawParameters.split(",");
					String[] parameter = Generator.splitParameters(rawParameters);

					// String file = parameter[0].replace("\"", "");

					SpriteVariable sprite;

					if (parameter.length == 1) {
						sprite = new SpriteVariable(name, parameter[0]);
					} else if (parameter.length == 2) {
						sprite = new SpriteVariable(name, parameter[0], parameter[1]);
					} else if (parameter.length == 3) {
						sprite = new SpriteVariable(name, parameter[0], parameter[1], parameter[2]);
					} else {
						throw new GeneratorException(null, -1,
								"In order to create a Sprite, you need 1-3 parameters");
					}

					context.createVariable(sprite);
				}

			}
			// String Variables are not supported jet
			/*
			 * else if (type.equals("String")) { // Parst einen boolschen
			 * Ausdruck in einen String-Wert String value =
			 * Generator.encodeStringExpression(context, expression);
			 * if (Main.debug) System.out.println("And the string is: " + value);
			 * context.createVariable(name, value); }
			 */
			else {
				throw new UnknownTypeException(null, -1, "The variable type \"" + type
						+ "\" is unknown or not supported");
			}

		}

		// String[] parts = content.split("=");
		// String[] parts2 = parts[0].split(" ");

		/*
		 * String[] parts = content.split(" "); if (parts[0].equals("int")) {
		 * String name; String rawValue; int value; if (!parts[1].endsWith("=")
		 * && parts[1].contains("=")) { String[] raw = parts[1].split("="); name
		 * = raw[0]; rawValue = raw[1]; } else if (parts[1].endsWith("=")) {
		 * name = parts[1].replace("=", ""); rawValue = parts[2]; } else if
		 * (parts.length == 3 && parts[2].startsWith("=")) { name = parts[1];
		 * rawValue = parts[2].replace("=", ""); } else { name = parts[1];
		 * rawValue = parts[3]; } try { /* if (Main.debug) System.out.println(name);
		 * if (Main.debug) System.out.println(rawValue);
		 *//*
			 * value = Generator.encodeIntegerExpression(context, rawValue);
			 * if (Main.debug) System.out.println("And the value is: " + value); //value =
			 * Integer.parseInt(rawValue); } catch (Exception e) { throw new
			 * WrongTypeException(null, -1,
			 * "Int value was expected, but value contains non-digit characters"
			 * ); }
			 * 
			 * context.createVariable(name, value);
			 */

	}

}
