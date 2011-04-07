package de.moonshade.osbe.oop.line;

import de.moonshade.osbe.oop.Context;
import de.moonshade.osbe.oop.Generator;
import de.moonshade.osbe.oop.Line;
import de.moonshade.osbe.oop.exception.GeneratorException;
import de.moonshade.osbe.oop.exception.WrongTypeException;

public class NewVariableDefinition extends Line {

	public enum Type {
		INT,STRING
	}
	
	int intVar;
	String stringVar;
	Context context;
	//context

	public NewVariableDefinition(Context context, String line) {
		this.context = context;
		this.content = line;
	}
	
	public void analyse() throws GeneratorException {
		
		String[] parts = content.split("=");
		String[] parts2 = parts[0].split(" ");
		
		if (parts2[0].equals("int")) {
			// Integer Variable erstellen
			String name = parts2[1];
			String expression = parts[1];
			// Parst einen arithmetischen Ausdruck in einen Integer-Wert
			int value = Generator.encodeIntegerExpression(context, expression);
			System.out.println("And the int value is: " + value);
			context.createVariable(name, value);
		} else if (parts2[0].equals("boolean")) {
			// boolean Variable erstellen
			String name = parts2[1];
			String expression = parts[1];
			// Parst einen boolschen Ausdruck in einen boolean-Wert
			boolean value = Generator.encodBooleanExpression(context, expression);
			System.out.println("And the boolean value is: " + value);
			context.createVariable(name, value);
		}
		/*
		String[] parts = content.split(" ");
		if (parts[0].equals("int")) {
			String name;
			String rawValue;
			int value;
			if (!parts[1].endsWith("=") && parts[1].contains("=")) {
				String[] raw = parts[1].split("=");
				name = raw[0];
				rawValue = raw[1];
		    } else if (parts[1].endsWith("=")) {
				name = parts[1].replace("=", "");
				rawValue = parts[2];
			} else if (parts.length == 3 && parts[2].startsWith("=")) {
				name = parts[1];
				rawValue = parts[2].replace("=", "");
			} else {
				name = parts[1];
				rawValue = parts[3];
			}
			try {
				/*
				System.out.println(name);
				System.out.println(rawValue);*//*
				value = Generator.encodeIntegerExpression(context, rawValue);
				System.out.println("And the value is: " + value);
				//value = Integer.parseInt(rawValue);
			} catch (Exception e) {
				throw new WrongTypeException(null, -1, "Int value was expected, but value contains non-digit characters");
			}
			
			context.createVariable(name, value);
			*/
		
	}
	
}
