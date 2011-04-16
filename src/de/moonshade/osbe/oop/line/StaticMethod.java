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

	// private Context context;
	private int absoluteTime = 0;

	public StaticMethod(Context context, String line, int absoluteTime) {
		this.parentContext = context;
		this.content = line;
		this.absoluteTime = absoluteTime;
	}

	public void analyse() throws GeneratorException {

		// Suche "." und trenne
		Pattern patternPoint;
		Matcher matcherPoint;
		patternPoint = Pattern.compile("\\.");
		matcherPoint = patternPoint.matcher(content);
		if (matcherPoint.find()) {

			String variableName = content.substring(0, matcherPoint.start()).trim();
			String method = content.substring(matcherPoint.end()).trim();

			// Suche "(...)" und trenne
			Pattern patternBracket;
			Matcher matcherBracket;
			patternBracket = Pattern.compile("\\(.*\\)");
			matcherBracket = patternBracket.matcher(method);

			if (matcherBracket.find()) {

				String methodName = method.substring(0, matcherBracket.start());
				String parameters = method.substring(matcherBracket.start() + 1, matcherBracket.end() - 1);
				// String[] parameter = parameters.split(",");
				String[] parameter = Generator.splitParameters(parameters);
				executeMethod(variableName, methodName, parameter);

			}
		}

	}

	private void executeMethod(String variableName, String name, String[] parameter) throws GeneratorException {

		// Jetzt suchen wir doch erstmal die Variable ^^
		Variable variable = parentContext.searchVariable(variableName);

		if (variable instanceof SpriteVariable) {
			// Hier kommen alle statischen Methoden rein, die bei Sprites etwas
			// tun

			// Wir parsen uns aus der Variable einen Sprite
			SpriteVariable sprite = (SpriteVariable) variable;

			int startTime = absoluteTime;
			int endTime = absoluteTime;
			int easing = 0;

			if (name.endsWith("SlowDown")) {
				easing = 1;
			} else if (name.endsWith("SpeedUp")) {
				easing = 2;
			}

			if (name.equals("move") || name.equals("moveSpeedUp") || name.equals("moveSlowDown")) {
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

				// Variablen definiert, jetzt geht es zur Codegenerierung ^^
				sprite.addStoryboard(" M," + easing + "," + startTime + "," + endTime + "," + startX + "," + startY
						+ "," + endX + "," + endY);
			}

			if (name.equals("fade") || name.equals("fadeSpeedUp") || name.equals("fadeSlowDown")) {
				float startOp = 0, endOp = 0;
				if (parameter.length == 1) {
					startOp = Generator.encodeFloatExpression(parentContext, parameter[0]);
					endOp = startOp;
				} else if (parameter.length == 2) {
					startTime += Generator.encodeIntegerExpression(parentContext, parameter[0]);
					endTime = startTime;
					startOp = Generator.encodeFloatExpression(parentContext, parameter[1]);
					endOp = startOp;
				} else if (parameter.length == 4) {
					startTime += Generator.encodeIntegerExpression(parentContext, parameter[0]);
					endTime = Generator.encodeIntegerExpression(parentContext, parameter[1]);
					startOp = Generator.encodeFloatExpression(parentContext, parameter[2]);
					endOp = Generator.encodeFloatExpression(parentContext, parameter[3]);
				}

				// Variablen definiert, jetzt geht es zur Codegenerierung ^^
				sprite.addStoryboard(" F," + easing + "," + startTime + "," + endTime + "," + startOp + "," + endOp);
			}
			
			if (name.equals("scale") || name.equals("scaleSpeedUp") || name.equals("scaleSlowDown")) {
				float startScale = 0, endScale = 0;
				if (parameter.length == 1) {
					startScale = Generator.encodeFloatExpression(parentContext, parameter[0]);
					endScale = startScale;
				} else if (parameter.length == 2) {
					startTime += Generator.encodeIntegerExpression(parentContext, parameter[0]);
					endTime = startTime;
					startScale = Generator.encodeFloatExpression(parentContext, parameter[1]);
					endScale = startScale;
				} else if (parameter.length == 4) {
					startTime += Generator.encodeIntegerExpression(parentContext, parameter[0]);
					endTime = Generator.encodeIntegerExpression(parentContext, parameter[1]);
					startScale = Generator.encodeFloatExpression(parentContext, parameter[2]);
					endScale = Generator.encodeFloatExpression(parentContext, parameter[3]);
				}

				// Variablen definiert, jetzt geht es zur Codegenerierung ^^
				sprite.addStoryboard(" F," + easing + "," + startTime + "," + endTime + "," + startScale + "," + endScale);
			}
			
			if (name.equals("rotate") || name.equals("rotateSpeedUp") || name.equals("rotateSlowDown")) {
				float startAngle = 0, endAngle = 0;
				if (parameter.length == 1) {
					startAngle = Generator.encodeFloatExpression(parentContext, parameter[0]);
					endAngle = startAngle;
				} else if (parameter.length == 2) {
					startTime += Generator.encodeIntegerExpression(parentContext, parameter[0]);
					endTime = startTime;
					startAngle = Generator.encodeFloatExpression(parentContext, parameter[1]);
					endAngle = startAngle;
				} else if (parameter.length == 4) {
					startTime += Generator.encodeIntegerExpression(parentContext, parameter[0]);
					endTime = Generator.encodeIntegerExpression(parentContext, parameter[1]);
					startAngle = Generator.encodeFloatExpression(parentContext, parameter[2]);
					endAngle = Generator.encodeFloatExpression(parentContext, parameter[3]);
				}

				// Variablen definiert, jetzt geht es zur Codegenerierung ^^
				sprite.addStoryboard(" F," + easing + "," + startTime + "," + endTime + "," + startAngle + "," + endAngle);
			}

		}

	}

}
