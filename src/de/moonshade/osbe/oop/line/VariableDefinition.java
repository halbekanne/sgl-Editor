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
import de.moonshade.osbe.oop.BooleanVariable;
import de.moonshade.osbe.oop.Context;
import de.moonshade.osbe.oop.FloatVariable;
import de.moonshade.osbe.oop.Generator;
import de.moonshade.osbe.oop.IntVariable;
import de.moonshade.osbe.oop.Line;
import de.moonshade.osbe.oop.StringVariable;
import de.moonshade.osbe.oop.Variable;
import de.moonshade.osbe.oop.exception.GeneratorException;

public class VariableDefinition extends Line {

	private Context context;

	public VariableDefinition(Context context, String line) {
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
			String name = content.substring(0, matcher.start()).trim();
			String expression = content.substring(matcher.end()).trim();
			Variable variable = context.searchVariable(name);
			if (variable instanceof IntVariable) {
				// Parst einen arithmetischen Ausdruck in einen Integer-Wert
				int value = Generator.encodeIntegerExpression(context, expression);
				if (Main.debug) System.out.println("And the int value is: " + value);
				context.setVariable(variable, value);

			} else if (variable instanceof BooleanVariable) {
				// Parst einen boolschen Ausdruck in einen boolean-Wert
				boolean value = Generator.encodeBooleanExpression(context, expression);
				if (Main.debug) System.out.println("And the boolean value is: " + value);
				context.setVariable(variable, value);

			} else if (variable instanceof FloatVariable) {
				// Parst einen boolschen Ausdruck in einen boolean-Wert
				float value = Generator.encodeFloatExpression(context, expression);
				if (Main.debug) System.out.println("And the float value is: " + value);
				context.setVariable(variable, value);

			} else if (variable instanceof StringVariable) {
				// Parst einen boolschen Ausdruck in einen String-Wert
				String value = Generator.encodeStringExpression(context, expression);
				if (Main.debug) System.out.println("And the string is: " + value);
				context.setVariable(variable, value);
			}

		}

	}

}
