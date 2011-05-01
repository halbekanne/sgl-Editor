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

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.moonshade.osbe.Main;
import de.moonshade.osbe.oop.Context;
import de.moonshade.osbe.oop.Generator;
import de.moonshade.osbe.oop.Line;
import de.moonshade.osbe.oop.SpriteVariable;
import de.moonshade.osbe.oop.Variable;
import de.moonshade.osbe.oop.block.Method;
import de.moonshade.osbe.oop.exception.GeneratorException;
import de.moonshade.osbe.oop.exception.UnknownTypeException;

public class StaticMethod extends Line {

	// private Context context;
	private int absoluteTime = 0;

	public StaticMethod(Context context, String line, int absoluteTime) {
		this.parentContext = context;
		this.content = line;
		this.absoluteTime = absoluteTime;
	}

	@Override
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

		name = name.trim();
		
		// Jetzt suchen wir doch erstmal die Variable ^^
		Variable variable = parentContext.searchVariable(variableName);

		if (variable instanceof SpriteVariable) {
			// Hier kommen alle statischen Methoden rein, die bei Sprites etwas
			// tun

			// Wir parsen uns aus der Variable einen Sprite
			SpriteVariable sprite = (SpriteVariable) variable;

			int startTime = absoluteTime;
			int endTime = absoluteTime;

			if (sprite.getLoop()) {
				startTime -= sprite.getLoopTime();
				endTime -= sprite.getLoopTime();
			}

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
					endTime += Generator.encodeIntegerExpression(parentContext, parameter[1]);
					startX = Generator.encodeIntegerExpression(parentContext, parameter[2]);
					startY = Generator.encodeIntegerExpression(parentContext, parameter[3]);
					endX = Generator.encodeIntegerExpression(parentContext, parameter[4]);
					endY = Generator.encodeIntegerExpression(parentContext, parameter[5]);
				} else {
					throw new GeneratorException(null, -1, name + ": Too much or too few parameters");
				}

				// Sprite aktualisieren
				sprite.setX(endX);
				sprite.setY(endY);

				// Variablen definiert, jetzt geht es zur Codegenerierung ^^
				sprite.addStoryboard(" M," + easing + "," + startTime + "," + endTime + "," + startX + "," + startY
						+ "," + endX + "," + endY);
			} else if (name.equals("moveTo") || name.equals("moveToSpeedUp") || name.equals("moveToSlowDown")) {
				int startX = 0, startY = 0, endX = 0, endY = 0;
				if (parameter.length == 4) {
					startTime += Generator.encodeIntegerExpression(parentContext, parameter[0]);
					endTime += Generator.encodeIntegerExpression(parentContext, parameter[1]);
					startX = sprite.getX();
					startY = sprite.getY();
					endX = Generator.encodeIntegerExpression(parentContext, parameter[2]);
					endY = Generator.encodeIntegerExpression(parentContext, parameter[3]);
				} else {
					throw new GeneratorException(null, -1, name + ": Too much or too few parameters");
				}

				// Sprite aktualisieren
				sprite.setX(endX);
				sprite.setY(endY);

				// Variablen definiert, jetzt geht es zur Codegenerierung ^^
				sprite.addStoryboard(" M," + easing + "," + startTime + "," + endTime + "," + startX + "," + startY
						+ "," + endX + "," + endY);
			}

			else if (name.equals("moveRel") || name.equals("moveRelSpeedUp") || name.equals("moveRelSlowDown")) {
				int startX = 0, startY = 0, endX = 0, endY = 0;
				if (parameter.length == 4) {
					startTime += Generator.encodeIntegerExpression(parentContext, parameter[0]);
					endTime += Generator.encodeIntegerExpression(parentContext, parameter[1]);
					startX = sprite.getX();
					startY = sprite.getY();
					if (Main.debug) System.out.println("---------------------------------------------------------");
					endX = startX + Generator.encodeIntegerExpression(parentContext, parameter[2]);
					endY = startY + Generator.encodeIntegerExpression(parentContext, parameter[3]);
				} else {
					throw new GeneratorException(null, -1, name + ": Too much or too few parameters");
				}

				// Sprite aktualisieren
				sprite.setX(endX);
				sprite.setY(endY);

				// Variablen definiert, jetzt geht es zur Codegenerierung ^^
				sprite.addStoryboard(" M," + easing + "," + startTime + "," + endTime + "," + startX + "," + startY
						+ "," + endX + "," + endY);
			}

			else if (name.equals("moveX") || name.equals("moveXSpeedUp") || name.equals("moveXSlowDown")) {
				int startX = 0, endX = 0;
				if (parameter.length == 1) {
					startX = Generator.encodeIntegerExpression(parentContext, parameter[0]);
					endX = startX;
				} else if (parameter.length == 2) {
					startTime += Generator.encodeIntegerExpression(parentContext, parameter[0]);
					endTime = startTime;
					startX = Generator.encodeIntegerExpression(parentContext, parameter[1]);
					endX = startX;
				} else if (parameter.length == 4) {
					startTime += Generator.encodeIntegerExpression(parentContext, parameter[0]);
					endTime += Generator.encodeIntegerExpression(parentContext, parameter[1]);
					startX = Generator.encodeIntegerExpression(parentContext, parameter[2]);
					endX = Generator.encodeIntegerExpression(parentContext, parameter[3]);
				} else {
					throw new GeneratorException(null, -1, name + ": Too much or too few parameters");
				}

				// Sprite aktualisieren
				sprite.setX(endX);

				// Variablen definiert, jetzt geht es zur Codegenerierung ^^
				sprite.addStoryboard(" MX," + easing + "," + startTime + "," + endTime + "," + startX + "," + endX);
			}

			else if (name.equals("moveXTo") || name.equals("moveXToSpeedUp") || name.equals("moveXToSlowDown")) {
				int startX = 0, endX = 0;
				if (parameter.length == 3) {
					startTime += Generator.encodeIntegerExpression(parentContext, parameter[0]);
					endTime += Generator.encodeIntegerExpression(parentContext, parameter[1]);
					startX = sprite.getX();
					endX = Generator.encodeIntegerExpression(parentContext, parameter[2]);
				} else {
					throw new GeneratorException(null, -1, name + ": Too much or too few parameters");
				}

				// Sprite aktualisieren
				sprite.setX(endX);

				// Variablen definiert, jetzt geht es zur Codegenerierung ^^
				sprite.addStoryboard(" MX," + easing + "," + startTime + "," + endTime + "," + startX + "," + endX);
			}

			else if (name.equals("moveXRel") || name.equals("moveXRelSpeedUp") || name.equals("moveXRelSlowDown")) {
				int startX = 0, endX = 0;
				if (parameter.length == 3) {
					startTime += Generator.encodeIntegerExpression(parentContext, parameter[0]);
					endTime += Generator.encodeIntegerExpression(parentContext, parameter[1]);
					startX = sprite.getX();
					endX = startX + Generator.encodeIntegerExpression(parentContext, parameter[2]);
				} else {
					throw new GeneratorException(null, -1, name + ": Too much or too few parameters");
				}

				// Sprite aktualisieren
				sprite.setX(endX);

				// Variablen definiert, jetzt geht es zur Codegenerierung ^^
				sprite.addStoryboard(" MX," + easing + "," + startTime + "," + endTime + "," + startX + "," + endX);
			}

			else if (name.equals("moveY") || name.equals("moveYSpeedUp") || name.equals("moveYSlowDown")) {
				int startY = 0, endY = 0;
				if (parameter.length == 1) {
					startY = Generator.encodeIntegerExpression(parentContext, parameter[0]);
					endY = startY;
				} else if (parameter.length == 2) {
					startTime += Generator.encodeIntegerExpression(parentContext, parameter[0]);
					endTime = startTime;
					startY = Generator.encodeIntegerExpression(parentContext, parameter[1]);
					endY = startY;
				} else if (parameter.length == 4) {
					startTime += Generator.encodeIntegerExpression(parentContext, parameter[0]);
					endTime += Generator.encodeIntegerExpression(parentContext, parameter[1]);
					startY = Generator.encodeIntegerExpression(parentContext, parameter[2]);
					endY = Generator.encodeIntegerExpression(parentContext, parameter[3]);
				} else {
					throw new GeneratorException(null, -1, name + ": Too much or too few parameters");
				}

				// Sprite aktualisieren
				sprite.setY(endY);

				// Variablen definiert, jetzt geht es zur Codegenerierung ^^
				sprite.addStoryboard(" MY," + easing + "," + startTime + "," + endTime + "," + startY + "," + endY);
			}

			else if (name.equals("moveYTo") || name.equals("moveYToSpeedUp") || name.equals("moveYToSlowDown")) {
				int startY = 0, endY = 0;
				if (parameter.length == 3) {
					startTime += Generator.encodeIntegerExpression(parentContext, parameter[0]);
					endTime += Generator.encodeIntegerExpression(parentContext, parameter[1]);
					startY = sprite.getY();
					endY = Generator.encodeIntegerExpression(parentContext, parameter[2]);
				} else {
					throw new GeneratorException(null, -1, name + ": Too much or too few parameters");
				}

				// Sprite aktualisieren
				sprite.setY(endY);

				// Variablen definiert, jetzt geht es zur Codegenerierung ^^
				sprite.addStoryboard(" MY," + easing + "," + startTime + "," + endTime + "," + startY + "," + endY);
			}

			else if (name.equals("moveYRel") || name.equals("moveYRelSpeedUp") || name.equals("moveYRelSlowDown")) {
				int startY = 0, endY = 0;
				if (parameter.length == 3) {
					startTime += Generator.encodeIntegerExpression(parentContext, parameter[0]);
					endTime += Generator.encodeIntegerExpression(parentContext, parameter[1]);
					startY = sprite.getY();
					endY = startY + Generator.encodeIntegerExpression(parentContext, parameter[2]);
				} else {
					throw new GeneratorException(null, -1, name + ": Too much or too few parameters");
				}

				// Sprite aktualisieren
				sprite.setY(endY);

				// Variablen definiert, jetzt geht es zur Codegenerierung ^^
				sprite.addStoryboard(" MY," + easing + "," + startTime + "," + endTime + "," + startY + "," + endY);
			}

			// Vector scale
			else if (name.equals("scaleVec") || name.equals("scaleVecSpeedUp") || name.equals("scaleVecSlowDown")) {
				float startX = 0, startY = 0, endX = 0, endY = 0;
				if (parameter.length == 2) {
					startX = Generator.encodeFloatExpression(parentContext, parameter[0]);
					startY = Generator.encodeFloatExpression(parentContext, parameter[1]);
					endX = startX;
					endY = startY;
				} else if (parameter.length == 3) {
					startTime += Generator.encodeIntegerExpression(parentContext, parameter[0]);
					endTime = startTime;
					startX = Generator.encodeFloatExpression(parentContext, parameter[1]);
					startY = Generator.encodeFloatExpression(parentContext, parameter[2]);
					endX = startX;
					endY = startY;
				} else if (parameter.length == 6) {
					startTime += Generator.encodeIntegerExpression(parentContext, parameter[0]);
					endTime += Generator.encodeIntegerExpression(parentContext, parameter[1]);
					startX = Generator.encodeFloatExpression(parentContext, parameter[2]);
					startY = Generator.encodeFloatExpression(parentContext, parameter[3]);
					endX = Generator.encodeFloatExpression(parentContext, parameter[4]);
					endY = Generator.encodeFloatExpression(parentContext, parameter[5]);
				} else {
					throw new GeneratorException(null, -1, name + ": Too much or too few parameters");
				}

				// Sprite aktualisieren
				sprite.setScaleX(endX);
				sprite.setScaleY(endY);

				// Variablen definiert, jetzt geht es zur Codegenerierung ^^
				sprite.addStoryboard(" V," + easing + "," + startTime + "," + endTime + "," + startX + "," + startY
						+ "," + endX + "," + endY);
			}

			else if (name.equals("scaleVecTo") || name.equals("scaleVecToSpeedUp") || name.equals("scaleVecToSlowDown")) {
				float startX = 0, startY = 0, endX = 0, endY = 0;
				if (parameter.length == 4) {
					startTime += Generator.encodeIntegerExpression(parentContext, parameter[0]);
					endTime += Generator.encodeIntegerExpression(parentContext, parameter[1]);
					startX = sprite.getScaleX();
					startY = sprite.getScaleY();
					endX = Generator.encodeFloatExpression(parentContext, parameter[2]);
					endY = Generator.encodeFloatExpression(parentContext, parameter[3]);
				} else {
					throw new GeneratorException(null, -1, name + ": Too much or too few parameters");
				}

				// Sprite aktualisieren
				sprite.setScaleX(endX);
				sprite.setScaleY(endY);

				// Variablen definiert, jetzt geht es zur Codegenerierung ^^
				sprite.addStoryboard(" V," + easing + "," + startTime + "," + endTime + "," + startX + "," + startY
						+ "," + endX + "," + endY);
			}

			else if (name.equals("scaleVecRel") || name.equals("scaleVecRelSpeedUp")
					|| name.equals("scaleVecRelSlowDown")) {
				float startX = 0, startY = 0, endX = 0, endY = 0;
				if (parameter.length == 4) {
					startTime += Generator.encodeIntegerExpression(parentContext, parameter[0]);
					endTime += Generator.encodeIntegerExpression(parentContext, parameter[1]);
					startX = sprite.getScaleX();
					startY = sprite.getScaleY();
					endX = startX + Generator.encodeFloatExpression(parentContext, parameter[2]);
					endY = startY + Generator.encodeFloatExpression(parentContext, parameter[3]);
				} else {
					throw new GeneratorException(null, -1, name + ": Too much or too few parameters");
				}

				// Sprite aktualisieren
				sprite.setScaleX(endX);
				sprite.setScaleY(endY);

				// Variablen definiert, jetzt geht es zur Codegenerierung ^^
				sprite.addStoryboard(" V," + easing + "," + startTime + "," + endTime + "," + startX + "," + startY
						+ "," + endX + "," + endY);
			}

			// Color
			else if (name.equals("color") || name.equals("colorSpeedUp") || name.equals("colorSlowDown")) {
				int startR = 0, startG = 0, startB = 0, endR = 0, endG = 0, endB = 0;
				if (parameter.length == 3) {
					startR = Generator.encodeIntegerExpression(parentContext, parameter[0]);
					startG = Generator.encodeIntegerExpression(parentContext, parameter[1]);
					startB = Generator.encodeIntegerExpression(parentContext, parameter[2]);
					endR = startR;
					endG = startG;
					endB = startB;
				} else if (parameter.length == 4) {
					startTime += Generator.encodeIntegerExpression(parentContext, parameter[0]);
					endTime = startTime;
					startR = Generator.encodeIntegerExpression(parentContext, parameter[1]);
					startG = Generator.encodeIntegerExpression(parentContext, parameter[2]);
					startB = Generator.encodeIntegerExpression(parentContext, parameter[3]);
					endR = startR;
					endG = startG;
					endB = startB;
				} else if (parameter.length == 8) {
					startTime += Generator.encodeIntegerExpression(parentContext, parameter[0]);
					endTime += Generator.encodeIntegerExpression(parentContext, parameter[1]);
					startR = Generator.encodeIntegerExpression(parentContext, parameter[2]);
					startG = Generator.encodeIntegerExpression(parentContext, parameter[3]);
					startB = Generator.encodeIntegerExpression(parentContext, parameter[4]);
					endR = Generator.encodeIntegerExpression(parentContext, parameter[5]);
					endG = Generator.encodeIntegerExpression(parentContext, parameter[6]);
					endB = Generator.encodeIntegerExpression(parentContext, parameter[7]);
				} else {
					throw new GeneratorException(null, -1, name + ": Too much or too few parameters");
				}

				// Sprite aktualisieren
				sprite.setRed(endR);
				sprite.setGreen(endG);
				sprite.setBlue(endB);

				// Variablen definiert, jetzt geht es zur Codegenerierung ^^
				sprite.addStoryboard(" C," + easing + "," + startTime + "," + endTime + "," + startR + "," + startG
						+ "," + startB + "," + endR + "," + endG + "," + endB);
			}

			else if (name.equals("colorTo") || name.equals("colorToSpeedUp") || name.equals("colorToSlowDown")) {
				int startR = 0, startG = 0, startB = 0, endR = 0, endG = 0, endB = 0;
				if (parameter.length == 5) {
					startTime += Generator.encodeIntegerExpression(parentContext, parameter[0]);
					endTime += Generator.encodeIntegerExpression(parentContext, parameter[1]);
					startR = sprite.getRed();
					startG = sprite.getGreen();
					startB = sprite.getBlue();
					endR = Generator.encodeIntegerExpression(parentContext, parameter[2]);
					endG = Generator.encodeIntegerExpression(parentContext, parameter[3]);
					endB = Generator.encodeIntegerExpression(parentContext, parameter[4]);
				} else {
					throw new GeneratorException(null, -1, name + ": Too much or too few parameters");
				}

				// Sprite aktualisieren
				sprite.setRed(endR);
				sprite.setGreen(endG);
				sprite.setBlue(endB);

				// Variablen definiert, jetzt geht es zur Codegenerierung ^^
				sprite.addStoryboard(" C," + easing + "," + startTime + "," + endTime + "," + startR + "," + startG
						+ "," + startB + "," + endR + "," + endG + "," + endB);
			}

			else if (name.equals("colorRel") || name.equals("colorRelSpeedUp") || name.equals("colorRelSlowDown")) {
				int startR = 0, startG = 0, startB = 0, endR = 0, endG = 0, endB = 0;
				if (parameter.length == 5) {
					startTime += Generator.encodeIntegerExpression(parentContext, parameter[0]);
					endTime += Generator.encodeIntegerExpression(parentContext, parameter[1]);
					startR = sprite.getRed();
					startG = sprite.getGreen();
					startB = sprite.getBlue();
					endR = startR + Generator.encodeIntegerExpression(parentContext, parameter[2]);
					endG = startG + Generator.encodeIntegerExpression(parentContext, parameter[3]);
					endB = startB + Generator.encodeIntegerExpression(parentContext, parameter[4]);
				} else {
					throw new GeneratorException(null, -1, name + ": Too much or too few parameters");
				}

				// Sprite aktualisieren
				sprite.setRed(endR);
				sprite.setGreen(endG);
				sprite.setBlue(endB);

				// Variablen definiert, jetzt geht es zur Codegenerierung ^^
				sprite.addStoryboard(" C," + easing + "," + startTime + "," + endTime + "," + startR + "," + startG
						+ "," + startB + "," + endR + "," + endG + "," + endB);
			}

			else if (name.equals("fade") || name.equals("fadeSpeedUp") || name.equals("fadeSlowDown")) {
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
					endTime += Generator.encodeIntegerExpression(parentContext, parameter[1]);
					startOp = Generator.encodeFloatExpression(parentContext, parameter[2]);
					endOp = Generator.encodeFloatExpression(parentContext, parameter[3]);
				} else {
					throw new GeneratorException(null, -1, name + ": Too much or too few parameters");
				}

				// Sprite aktualisieren
				sprite.setOpacity(endOp);

				// Variablen definiert, jetzt geht es zur Codegenerierung ^^
				sprite.addStoryboard(" F," + easing + "," + startTime + "," + endTime + "," + startOp + "," + endOp);
			}

			else if (name.equals("fadeTo") || name.equals("fadeToSpeedUp") || name.equals("fadeToSlowDown")) {
				float startOp = 0, endOp = 0;
				if (parameter.length == 3) {
					startTime += Generator.encodeIntegerExpression(parentContext, parameter[0]);
					endTime += Generator.encodeIntegerExpression(parentContext, parameter[1]);
					startOp = sprite.getOpacity();
					endOp = Generator.encodeFloatExpression(parentContext, parameter[2]);
				} else {
					throw new GeneratorException(null, -1, name + ": Too much or too few parameters");
				}

				// Sprite aktualisieren
				sprite.setOpacity(endOp);

				// Variablen definiert, jetzt geht es zur Codegenerierung ^^
				sprite.addStoryboard(" F," + easing + "," + startTime + "," + endTime + "," + startOp + "," + endOp);
			}

			else if (name.equals("fadeRel") || name.equals("fadeRelSpeedUp") || name.equals("fadeRelSlowDown")) {
				float startOp = 0, endOp = 0;
				if (parameter.length == 3) {
					startTime += Generator.encodeIntegerExpression(parentContext, parameter[0]);
					endTime += Generator.encodeIntegerExpression(parentContext, parameter[1]);
					startOp = sprite.getOpacity();
					endOp = startOp + Generator.encodeFloatExpression(parentContext, parameter[2]);
				} else {
					throw new GeneratorException(null, -1, name + ": Too much or too few parameters");
				}

				// Sprite aktualisieren
				sprite.setOpacity(endOp);

				// Variablen definiert, jetzt geht es zur Codegenerierung ^^
				sprite.addStoryboard(" F," + easing + "," + startTime + "," + endTime + "," + startOp + "," + endOp);
			}

			else if (name.equals("scale") || name.equals("scaleSpeedUp") || name.equals("scaleSlowDown")) {
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
					endTime += Generator.encodeIntegerExpression(parentContext, parameter[1]);
					startScale = Generator.encodeFloatExpression(parentContext, parameter[2]);
					endScale = Generator.encodeFloatExpression(parentContext, parameter[3]);
				} else {
					throw new GeneratorException(null, -1, name + ": Too much or too few parameters");
				}

				// Sprite aktualisieren
				sprite.setScaleX(endScale);
				sprite.setScaleY(endScale);

				// Variablen definiert, jetzt geht es zur Codegenerierung ^^
				sprite.addStoryboard(" S," + easing + "," + startTime + "," + endTime + "," + startScale + ","
						+ endScale);
			}

			else if (name.equals("scaleTo") || name.equals("scaleToSpeedUp") || name.equals("scaleToSlowDown")) {
				float startScale = 0, endScale = 0;
				if (parameter.length == 3) {
					startTime += Generator.encodeIntegerExpression(parentContext, parameter[0]);
					endTime += Generator.encodeIntegerExpression(parentContext, parameter[1]);
					startScale = sprite.getScaleX();
					endScale = Generator.encodeFloatExpression(parentContext, parameter[2]);
				} else {
					throw new GeneratorException(null, -1, name + ": Too much or too few parameters");
				}

				// Sprite aktualisieren
				sprite.setScaleX(endScale);
				sprite.setScaleY(endScale);

				// Variablen definiert, jetzt geht es zur Codegenerierung ^^
				sprite.addStoryboard(" S," + easing + "," + startTime + "," + endTime + "," + startScale + ","
						+ endScale);
			}

			else if (name.equals("scaleRel") || name.equals("scaleRelSpeedUp") || name.equals("scaleRelSlowDown")) {
				float startScale = 0, endScale = 0;
				if (parameter.length == 3) {
					startTime += Generator.encodeIntegerExpression(parentContext, parameter[0]);
					endTime += Generator.encodeIntegerExpression(parentContext, parameter[1]);
					startScale = sprite.getScaleX();
					endScale = startScale + Generator.encodeFloatExpression(parentContext, parameter[2]);
				} else {
					throw new GeneratorException(null, -1, name + ": Too much or too few parameters");
				}

				// Sprite aktualisieren
				sprite.setScaleX(endScale);
				sprite.setScaleY(endScale);

				// Variablen definiert, jetzt geht es zur Codegenerierung ^^
				sprite.addStoryboard(" S," + easing + "," + startTime + "," + endTime + "," + startScale + ","
						+ endScale);
			}

			else if (name.equals("rotate") || name.equals("rotateSpeedUp") || name.equals("rotateSlowDown")) {
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
					endTime += Generator.encodeIntegerExpression(parentContext, parameter[1]);
					startAngle = Generator.encodeFloatExpression(parentContext, parameter[2]);
					endAngle = Generator.encodeFloatExpression(parentContext, parameter[3]);
				} else {
					throw new GeneratorException(null, -1, name + ": Too much or too few parameters");
				}

				// Sprite aktualisieren
				sprite.setRotation(endAngle);

				// Variablen definiert, jetzt geht es zur Codegenerierung ^^
				sprite.addStoryboard(" R," + easing + "," + startTime + "," + endTime + "," + startAngle + ","
						+ endAngle);
			}

			else if (name.equals("rotateTo") || name.equals("rotateToSpeedUp") || name.equals("rotateToSlowDown")) {
				float startAngle = 0, endAngle = 0;
				if (parameter.length == 3) {
					startTime += Generator.encodeIntegerExpression(parentContext, parameter[0]);
					endTime += Generator.encodeIntegerExpression(parentContext, parameter[1]);
					startAngle = sprite.getRotation();
					endAngle = Generator.encodeFloatExpression(parentContext, parameter[2]);
				} else {
					throw new GeneratorException(null, -1, name + ": Too much or too few parameters");
				}

				// Sprite aktualisieren
				sprite.setRotation(endAngle);

				// Variablen definiert, jetzt geht es zur Codegenerierung ^^
				sprite.addStoryboard(" R," + easing + "," + startTime + "," + endTime + "," + startAngle + ","
						+ endAngle);
			}

			else if (name.equals("rotateRel") || name.equals("rotateRelSpeedUp") || name.equals("rotateRelSlowDown")) {
				float startAngle = 0, endAngle = 0;
				if (parameter.length == 3) {
					startTime += Generator.encodeIntegerExpression(parentContext, parameter[0]);
					endTime += Generator.encodeIntegerExpression(parentContext, parameter[1]);
					startAngle = sprite.getRotation();
					endAngle = startAngle + Generator.encodeFloatExpression(parentContext, parameter[2]);
				} else {
					throw new GeneratorException(null, -1, name + ": Too much or too few parameters");
				}

				// Sprite aktualisieren
				sprite.setRotation(endAngle);

				// Variablen definiert, jetzt geht es zur Codegenerierung ^^
				sprite.addStoryboard(" R," + easing + "," + startTime + "," + endTime + "," + startAngle + ","
						+ endAngle);

			} else if (name.equals("startLoop")) {
				int loopCount = 0;
				if (parameter.length == 2) {
					startTime += Generator.encodeIntegerExpression(parentContext, parameter[0]);
					loopCount = Generator.encodeIntegerExpression(parentContext, parameter[1]);
				} else {
					throw new GeneratorException(null, -1, name + ": Too much or too few parameters");
				}

				sprite.addStoryboard(" L," + startTime + "," + loopCount);
				sprite.setLoop(true);
				sprite.setLoopTime(startTime);

			} else if (name.equals("endLoop")) {
				if (parameter.length == 0) {
					// noop
				} else {
					throw new GeneratorException(null, -1, name + ": Too much or too few parameters");
				}

				sprite.setLoop(false);

			} else {
				if (Main.debug) System.out.println("suche eigene methode...");
				Method method = Generator.root.searchMethod(name);
				if (method == null) {
					throw new GeneratorException(null, -1, "Unable to find method " + name);
				}

				List<String> paramName = method.getParamName();
				List<String> paramType = method.getParamType();

				// Wir encoden jetzt die übergebenen Werte für die in der
				// Methode spezifizierten Typen von Variablen
				String[] methodParameters = new String[paramName.size()];

				for (int a = 0; a < methodParameters.length; a++) {
					if (paramType.get(a).equals("int")) {
						methodParameters[a] = String.valueOf(Generator.encodeIntegerExpression(parentContext,
								parameter[a]));
					} else if (paramType.get(a).equals("float")) {
						methodParameters[a] = String.valueOf(Generator.encodeFloatExpression(parentContext,
								parameter[a]));
					} else if (paramType.get(a).equals("boolean")) {
						methodParameters[a] = String.valueOf(Generator.encodeBooleanExpression(parentContext,
								parameter[a]));
					} else {
						throw new UnknownTypeException(null, -1, "Parameter type " + paramType.get(a)
								+ " is unknown or not supported");
					}
				}

				method.setCurrentParameters(methodParameters);

				// Da die Methode zusammen mit einem Objekt verwendet wird, wird
				// das Objekt übergeben
				method.setCurrentObject(sprite);

				// Jetzt noch die absoluteTime setzen
				method.setAbsoluteTime(absoluteTime);

				// Und ausführen
				method.analyse();

			}

		}

	}

}
