package de.moonshade.osbe.oop.line;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.moonshade.osbe.oop.Context;
import de.moonshade.osbe.oop.Generator;
import de.moonshade.osbe.oop.Line;
import de.moonshade.osbe.oop.SpriteVariable;
import de.moonshade.osbe.oop.Variable;
import de.moonshade.osbe.oop.exception.GeneratorException;

public class StaticMethod extends Line {

	//private Context context;
	private int absoluteTime = 0;
	
	public StaticMethod(Context context, String line, int absoluteTime) {
		this.parentContext = context;
		this.content = line;
		this.absoluteTime = absoluteTime;
	}
	
	public void analyse() throws GeneratorException {
		
		
		// Suche "." und trenne
		Pattern patternPoint; Matcher matcherPoint;
		patternPoint = Pattern.compile("\\.");
		matcherPoint = patternPoint.matcher(content);
		if (matcherPoint.find()) {

			String variableName = content.substring(0, matcherPoint.start()).trim();
			String method = content.substring(matcherPoint.end()).trim();
			
			// Suche "(...)" und trenne
			Pattern patternBracket; Matcher matcherBracket;
			patternBracket = Pattern.compile("\\(.*\\)");
			matcherBracket = patternBracket.matcher(method);
			
			if (matcherBracket.find()) {

				String methodName = method.substring(0, matcherBracket.start());
				String parameters = method.substring(matcherBracket.start() + 1, matcherBracket.end() - 1);
				//String[] parameter = parameters.split(",");
				String[] parameter = Generator.splitParameters(parameters);
				executeMethod(variableName, methodName, parameter);
				
				
			}
		}
		
		
		
			
		
	}
	
	private void executeMethod(String variableName, String name, String[] parameter) throws GeneratorException {
		
		// Jetzt suchen wir doch erstmal die Variable ^^
		Variable variable = parentContext.searchVariable(variableName);

		if (variable instanceof SpriteVariable) {
			// Hier kommen alle statischen Methoden rein, die bei Sprites etwas tun
			
			// Wir parsen uns aus der Variable einen Sprite
			SpriteVariable sprite = (SpriteVariable) variable;
			
			int startTime = absoluteTime;
			int endTime = absoluteTime;
			int easing = 0;
			
			if (name.equals("move")) {
				System.out.println(6);
				int startX = 0, startY = 0, endX = 0, endY = 0;
				if (parameter.length == 2) {
					startX = Generator.encodeIntegerExpression(parentContext, parameter[0]);
					startY = Generator.encodeIntegerExpression(parentContext, parameter[1]);
					endX = startX;
					endY = startY;
				} else if (parameter.length == 3) {
					startTime += Generator.encodeIntegerExpression(parentContext, parameter[0]);
					endTime = startTime;
					startX = Generator.encodeIntegerExpression(parentContext, parameter[1]);
					startY = Generator.encodeIntegerExpression(parentContext, parameter[2]);
					endX = startX;
					endY = startY;
				} else if (parameter.length == 6) {
					startTime += Generator.encodeIntegerExpression(parentContext, parameter[0]);
					endTime = Generator.encodeIntegerExpression(parentContext, parameter[1]);
					startX = Generator.encodeIntegerExpression(parentContext, parameter[2]);
					startY = Generator.encodeIntegerExpression(parentContext, parameter[3]);
					endX = Generator.encodeIntegerExpression(parentContext, parameter[4]);
					endY = Generator.encodeIntegerExpression(parentContext, parameter[5]);
				} 
				
				System.out.println(1);
				
				// Variablen definiert, jetzt geht es zur Codegenerierung ^^
				sprite.addStoryboard(" M," + easing + "," + startTime + "," + endTime + "," + startX + ","+ startY + "," + endX + "," +endY);
				
				
			}
			
			
		}
		
	}

}
