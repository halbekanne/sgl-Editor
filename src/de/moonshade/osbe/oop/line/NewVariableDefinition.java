package de.moonshade.osbe.oop.line;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.script.ScriptException;

import de.moonshade.osbe.oop.Context;
import de.moonshade.osbe.oop.Generator;
import de.moonshade.osbe.oop.Line;
import de.moonshade.osbe.oop.SpriteVariable;
import de.moonshade.osbe.oop.exception.GeneratorException;
import de.moonshade.osbe.oop.exception.UnknownTypeException;
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
		
		Pattern pattern; Matcher matcher;
		pattern = Pattern.compile("=");
		matcher = pattern.matcher(content);
		if (matcher.find()) {
			String definition = content.substring(0, matcher.start()).trim();
			String expression = content.substring(matcher.end()).trim();
			String type = definition.split(" ")[0];
			String name = definition.split(" ")[1];
			
			if (type.equals("int")) {
				// Parst einen arithmetischen Ausdruck in einen Integer-Wert
				int value = Generator.encodeIntegerExpression(context, expression);
				System.out.println("And the int value is: " + value);
				context.createVariable(name, value);
				
			} else if (type.equals("boolean")) {
				// Parst einen boolschen Ausdruck in einen boolean-Wert
				boolean value = Generator.encodeBooleanExpression(context, expression);
				System.out.println("And the boolean value is: " + value);
				context.createVariable(name, value);
				
			} else if (type.equals("Sprite")) {
				// Parst ein Sprite-Objekt
				if (!expression.matches("new\\s+Sprite\\(\".*")) {
					throw new GeneratorException(null,-1,"Wrong syntax or missing parameters");
				}
				
				pattern = Pattern.compile("\\(.*\\)");
				matcher = pattern.matcher(expression);
				if (matcher.find()) {
					String rawParameters = expression.substring(matcher.start() + 1, matcher.end() - 1);
					System.out.println(rawParameters);
					// Könnte Probleme geben, wenn Dateipfad Kommas enthält
					//String[] parameter = rawParameters.split(",");
					String[] parameter = Generator.splitParameters(rawParameters);
					
					//String file = parameter[0].replace("\"", "");
					
					SpriteVariable sprite;
					
					if (parameter.length == 1) {
						sprite = new SpriteVariable(name, parameter[0]);
					} else if (parameter.length == 2) {
						sprite = new SpriteVariable(name, parameter[0], parameter[1]);
					} else if (parameter.length == 3) {
						sprite = new SpriteVariable(name, parameter[0], parameter[1], parameter[2]);
					} else {
						throw new GeneratorException(null,-1,"In order to create a Sprite, you need 1-3 parameters");
					}
					
					context.createVariable(sprite);
				}
				
			}
			// String Variables are not supported jet
			/*
			else if (type.equals("String")) {
				// Parst einen boolschen Ausdruck in einen String-Wert
				String value = Generator.encodeStringExpression(context, expression);
				System.out.println("And the string is: " + value);
				context.createVariable(name, value);
			}
			*/
			else {
				throw new UnknownTypeException(null,-1,"The variable type \"" + type + "\" is unknown or not supported");
			}
			
		}
		
		//String[] parts = content.split("=");
		//String[] parts2 = parts[0].split(" ");
		
		
		
		
		
		
		
		
		
		
		
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
