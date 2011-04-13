package de.moonshade.osbe.oop.line;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.moonshade.osbe.oop.BooleanVariable;
import de.moonshade.osbe.oop.Context;
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
	
	
	public void analyse() throws GeneratorException {
		Pattern pattern; Matcher matcher;
		pattern = Pattern.compile("=");
		matcher = pattern.matcher(content);
		if (matcher.find()) {
			String name = content.substring(0, matcher.start()).trim();
			String expression = content.substring(matcher.end()).trim();
			Variable variable = Generator.getVariable(context, name);
			if (variable instanceof IntVariable) {
				// Parst einen arithmetischen Ausdruck in einen Integer-Wert
				int value = Generator.encodeIntegerExpression(context, expression);
				System.out.println("And the int value is: " + value);
				context.setVariable(variable, value);
				
				
			} else if (variable instanceof BooleanVariable) {
				// Parst einen boolschen Ausdruck in einen boolean-Wert
				boolean value = Generator.encodeBooleanExpression(context, expression);
				System.out.println("And the boolean value is: " + value);
				context.createVariable(name, value);
				
			} else if (variable instanceof StringVariable) {
				// Parst einen boolschen Ausdruck in einen boolean-Wert
				String value = Generator.encodeStringExpression(context, expression);
				System.out.println("And the string is: " + value);
				context.createVariable(name, value);
			}
			
			
		}
		
		
	}	
	
	
	
	
}
