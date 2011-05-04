package de.moonshade.osbe.oop.line;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.moonshade.osbe.oop.Context;
import de.moonshade.osbe.oop.Generator;
import de.moonshade.osbe.oop.Line;
import de.moonshade.osbe.oop.block.Method;
import de.moonshade.osbe.oop.exception.GeneratorException;
import de.moonshade.osbe.oop.exception.UnknownTypeException;

public class CustomMethod extends Line {

	private int absoluteTime = 0;
	
	public CustomMethod(Context context, String line, int absoluteTime) {
		this.parentContext = context;
		this.content = line;
		this.absoluteTime = absoluteTime;
	}
	
	@Override
	public void analyse() throws GeneratorException {
		
		// wir suchen die Parameterklammern
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

		String name = content.substring(0, firstBracket).trim();
		String paramRaw = content.substring(firstBracket + 1, lastBracket).trim();
		String[] parameter = Generator.splitParameters(paramRaw);
		
		Method method = Generator.root.searchMethod(name);
		if (method == null) {
			throw new GeneratorException(null, -1, "Unable to find method " + name);
		}
		
		List<String> paramName = method.getParamName();
		List<String> paramType = method.getParamType();
		
		
		// Wir encoden jetzt die übergebenen Werte für die in der Methode spezifizierten Typen von Variablen
		String[] methodParameters = new String[paramName.size()];
		
		if (methodParameters.length != parameter.length) {
			throw new GeneratorException(null, -1, "Too few or too much parameters in your method call");
		}
		
		for (int a = 0; a < methodParameters.length; a++) {
			if (paramType.get(a).equals("int")) {
				methodParameters[a] = String.valueOf(Generator.encodeIntegerExpression(parentContext, parameter[a]));
			} else if (paramType.get(a).equals("float")) {
				methodParameters[a] = String.valueOf(Generator.encodeFloatExpression(parentContext, parameter[a]));
			} else if (paramType.get(a).equals("boolean")) {
				methodParameters[a] = String.valueOf(Generator.encodeBooleanExpression(parentContext, parameter[a]));
			} else {
				throw new UnknownTypeException(null, -1, "Parameter type " + paramType.get(a) + " is unknown or not supported");
			}
		}
		
		method.setCurrentParameters(methodParameters);
		
		// Jetzt noch die absoluteTime setzen
		method.setAbsoluteTime(absoluteTime);
		
		// Und ausführen
		method.analyse();
	}
	
	
}
