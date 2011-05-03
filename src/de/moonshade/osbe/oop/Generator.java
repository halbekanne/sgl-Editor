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

package de.moonshade.osbe.oop;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.swing.JOptionPane;

import de.moonshade.osbe.Main;
import de.moonshade.osbe.gui.GUI;
import de.moonshade.osbe.oop.block.IfCondition;
import de.moonshade.osbe.oop.block.Loop;
import de.moonshade.osbe.oop.block.Method;
import de.moonshade.osbe.oop.block.NoopBlock;
import de.moonshade.osbe.oop.block.TimeBlock;
import de.moonshade.osbe.oop.exception.GeneratorException;
import de.moonshade.osbe.oop.exception.ParserException;
import de.moonshade.osbe.oop.line.CustomMethod;
import de.moonshade.osbe.oop.line.NewVariableDefinition;
import de.moonshade.osbe.oop.line.StaticMethod;
import de.moonshade.osbe.oop.line.VariableDefinition;

public class Generator {

	public static List<Variable> variables = new ArrayList<Variable>();

	public static boolean encodeBooleanExpression(Context context, String expression)
			throws GeneratorException {

		if (Main.debug)
			System.out.println(expression);

		expression = encodeValueMethods(context, expression);
		expression = encodeVariables(context, expression);

		// In case of an arithmetic expression, we let the JavaScript Evaluator
		// encode this expression
		try {
			return Boolean.parseBoolean(Main.javaScriptEvaluator.eval(expression).toString());
		} catch (ScriptException e) {
			e.printStackTrace();
			throw new ParserException(null, -1, "Unable to parse \"" + expression
					+ "\" to a boolean value");
		}
	}

	public static float encodeFloatExpression(Context context, String expression)
			throws GeneratorException {

		// Performance Inhancement
		try {
			return Float.parseFloat(expression);
		} catch (Exception ex) {
		}

		expression = encodeValueMethods(context, expression);
		expression = encodeVariables(context, expression);

		// Performance Inhancement
		try {
			return Float.parseFloat(expression);
		} catch (Exception ex) {
		}

		// In case of an arithmetic expression, we let the JavaScript Evaluator
		// encode this expression
		float result;
		try {
			result = Float.parseFloat(Main.javaScriptEvaluator.eval(expression).toString());
		} catch (Exception ex) {
			throw new ParserException(null, -1, "Unable to parse \"" + expression
					+ "\" to a float value");
		}
		return result;
	}

	public static int encodeIntegerExpression(Context context, String expression)
			throws GeneratorException {

		// Performance Inhancement
		try {
			return Integer.parseInt(expression);
		} catch (Exception ex) {
		}

		expression = encodeValueMethods(context, expression);
		expression = encodeVariables(context, expression);

		// Performance Inhancement
		try {
			return Integer.parseInt(expression);
		} catch (Exception ex) {
		}

		// In case of an arithmetic expression, we let the JavaScript Evaluator
		// encode this expression
		float result;
		try {
			result = Float.parseFloat(Main.javaScriptEvaluator.eval(expression).toString());
		} catch (Exception ex2) {
			throw new ParserException(null, -1, "Unable to parse \"" + expression
					+ "\" to an integer value");
		}
		return (int) result;

		// if (Main.debug) System.out.print(result);

		/*
		 * Pattern pattern; Matcher matcher;
		 * 
		 * 
		 * pattern = Pattern.compile("^[0-9]+$"); matcher =
		 * pattern.matcher(expression); if (matcher.find()) { // Nur eine Zahl
		 * return Integer.parseInt(expression); }
		 * 
		 * pattern = Pattern.compile("^[a-zA-Z0-9]+$"); matcher =
		 * pattern.matcher(expression); if (matcher.find()) { // Nur eine
		 * Variable return getVariable(context,expression).getValue(); }
		 * 
		 * // Wir suchen nach den innersten Klammern pattern =
		 * Pattern.compile("\\([^\\(^\\)]*\\)"); matcher =
		 * pattern.matcher(expression); if (matcher.find()) { int start =
		 * matcher.start(); int end = matcher.end(); int subencode =
		 * encodeIntegerExpression(context,expression.substring(start,end));
		 * return encodeIntegerExpression(context,expression.substring(0, start)
		 * + subencode + expression.substring(end)); }
		 * 
		 * // Für hochrechnungen (z.B. 3^5) pattern =
		 * Pattern.compile("[^\\+^\\-^\\*^/^\\^^%]*[\\^][^\\+^\\-^\\*^/^\\^^%]*"
		 * ); matcher = pattern.matcher(expression); if (matcher.find()) { int
		 * start = matcher.start(); int end = matcher.end(); String[] subexp =
		 * expression.substring(start,end).split("[\\^]"); int subencode =
		 * encodeIntegerExpression(context,subexp[0]) +
		 * encodeIntegerExpression(context,subexp[1]); return
		 * encodeIntegerExpression(context,expression.substring(0, start) +
		 * subencode + expression.substring(end)); }
		 * 
		 * // Multiplikation und Division pattern =
		 * Pattern.compile("[^\\+^\\-^\\*^/^\\^^%]*[\\* /][^\\+^\\-^\\*^/^\\^^%]*"
		 * ); matcher = pattern.matcher(expression); if (matcher.find()) { int
		 * start = matcher.start(); int end = matcher.end(); String[] subexp =
		 * expression.substring(start,end).split("[\\^]"); int subencode =
		 * encodeIntegerExpression(context,subexp[0]) +
		 * encodeIntegerExpression(context,subexp[1]); return
		 * encodeIntegerExpression(context,expression.substring(0, start) +
		 * subencode + expression.substring(end)); }
		 * 
		 * /* pattern = Pattern.compile(".*[\\+\\-].*"); matcher =
		 * pattern.matcher(expression); if (matcher.find()) { //
		 * Additionsausdrücke
		 * 
		 * }
		 * 
		 * if (expression.contains("^")) { String[] exposeParts =
		 * trimArray(expression.split("^"));
		 * 
		 * }
		 */
	}

	private static String encodeValueMethods(Context context, String expression)
			throws GeneratorException {

		boolean found;
		do {
			found = false;

			Pattern pattern;
			Matcher matcher;
			pattern = Pattern.compile("[a-zA-Z]\\w*\\(.*");
			matcher = pattern.matcher(expression);
			if (matcher.find()) {
				String methodString = expression.substring(matcher.start());

				// wir suchen das Ende und den Anfang der Methodenklammern
				char[] chars = methodString.toCharArray();
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

				String methodName = methodString.substring(0, firstBracket);
				String methodParameters = methodString.substring(firstBracket + 1, lastBracket);

				if (Main.debug)
					System.out.println(methodName + " ist eine Methode. Parameter: "
							+ methodParameters);

				expression = expression.substring(0, matcher.start())
						+ valueMethods(context, methodName, splitParameters(methodParameters))
						+ expression.substring(matcher.start() + lastBracket + 1);
				if (Main.debug)
					System.out.println("Neue Expression: " + expression);

				found = true;
			}

		} while (found);
		return expression;
	}

	public static String encodeStringExpression(Context context, String expression)
			throws GeneratorException {

		if (expression.matches("^\"[^\"]*\"$")) {
			return expression;
		}

		expression = encodeStringVariables(context, expression);

		return expression;
	}

	private static String encodeVariables(Context context, String expression)
			throws GeneratorException {

		boolean found;
		do {
			found = false;

			Pattern pattern;
			Matcher matcher;
			pattern = Pattern.compile("[a-zA-Z]\\w*(\\W|$)");
			matcher = pattern.matcher(expression);
			if (matcher.find()) {
				String variableName = expression.substring(matcher.start(), matcher.end()).trim();

				if (Main.debug)
					System.out.println("1: " + variableName);

				int ende = matcher.end();
				if (variableName.endsWith("\\W")) {
					variableName = expression.substring(matcher.start(), matcher.end() - 1).trim();
					ende -= 1;
				}
				if (Main.debug)
					System.out.println("2: " + variableName);

				if (Main.debug)
					System.out.println(variableName + " ist eine Variable. Suche Wert...");
				Variable variable = context.searchVariable(variableName);
				if (variable != null) {
					expression = expression.substring(0, matcher.start())
							+ variable.getStringValue() + expression.substring(ende);
					if (Main.debug)
						System.out.println("Neue Expression: " + expression);
					found = true;
				}
			}

		} while (found);
		return expression;
	}

	public static Variable getVariable(Context context, String variableName) {
		return context.searchVariable(variableName);

	}

	private static String valueMethods(Context context, String name, String[] parameter)
			throws GeneratorException {

		// if (Main.debug) System.out.println(1);
		if (name.equals("rand")) {
			if (parameter.length == 0) {
				if (Main.debug)
					System.out.println(3);
				Random generator = new Random();
				return String.valueOf(generator.nextFloat());
			} else if (parameter.length == 2) {
				Random generator = new Random();
				int start = Generator.encodeIntegerExpression(context, parameter[0]);
				int end = Generator.encodeIntegerExpression(context, parameter[1]);
				return String.valueOf(start + generator.nextInt(end - start + 1));
			} else {
				throw new GeneratorException(null, -1, "Method \"" + name
						+ "\" - too few or too much parameters.");
			}
		} else if (name.equals("max")) {
			if (parameter.length == 2) {
				float value1 = Generator.encodeFloatExpression(context, parameter[0]);
				float value2 = Generator.encodeFloatExpression(context, parameter[1]);
				return String.valueOf(value1 >= value2 ? value1 : value2);
			} else {
				throw new GeneratorException(null, -1, "Method \"" + name
						+ "\" - too few or too much parameters.");
			}
		} else if (name.equals("min")) {
			if (parameter.length == 2) {
				float value1 = Generator.encodeFloatExpression(context, parameter[0]);
				float value2 = Generator.encodeFloatExpression(context, parameter[1]);
				return String.valueOf(value1 <= value2 ? value1 : value2);
			} else {
				throw new GeneratorException(null, -1, "Method \"" + name
						+ "\" - too few or too much parameters.");
			}
		}

		throw new GeneratorException(null, -1, "Cannot find integer method \"" + name + "\"");
	}

	public static String[] splitParameters(String parameters) {

		char[] chars = parameters.toCharArray();

		int counter = 0;
		List<String> parameterList = new ArrayList<String>();
		String buffer = "";
		for (int a = 0; a < chars.length; a++) {

			if (chars[a] == ',' && counter == 0) {
				parameterList.add(buffer);
				buffer = "";
			} else {
				// Wir müssen immer wissen, ob wir grad in einer Klammer sind
				// oder nicht
				if (chars[a] == '(') {
					counter++;
				} else if (chars[a] == ')') {
					counter--;
				}
				buffer += chars[a];
			}

		}
		if (!buffer.equals("")) {
			parameterList.add(buffer.trim());
		}
		return parameterList.toArray(new String[0]);
	}

	private static String encodeStringVariables(Context context, String expression)
			throws GeneratorException {
		// and Methods

		boolean isString = false;
		String newExpression = "";
		String buffer = "";

		char[] chars = expression.toCharArray();
		for (int a = 0; a < chars.length; a++) {
			if (chars[a] == '"') {
				if (!isString) {
					isString = true;

					if (!buffer.equals("")) {
						// Die "+" am Anfang und am Ende rausschneiden
						buffer = buffer.trim();
						if (buffer.startsWith("+")) {
							buffer = buffer.substring(1);
						}
						if (buffer.endsWith("+")) {
							buffer = buffer.substring(0, buffer.length() - 1);
						}
						buffer = buffer.trim();
						newExpression += Generator.encodeIntegerExpression(context, buffer);
						buffer = "";
					}

				} else {
					isString = false;
				}
				a++;
			}
			if (a < chars.length) {
				if (!isString) {
					buffer += chars[a];
				} else {
					newExpression += chars[a];
				}
			}
		}

		if (!isString && !buffer.equals("")) {
			buffer = buffer.trim();
			if (buffer.startsWith("+")) {
				buffer = buffer.substring(1);
			}
			if (buffer.endsWith("+")) {
				buffer = buffer.substring(0, buffer.length() - 1);
			}
			buffer = buffer.trim();
			newExpression += Generator.encodeIntegerExpression(context, buffer);
			buffer = "";
		}

		System.out.println("Neue Expression: " + newExpression);

		return "\"" + newExpression + "\"";
	}

	private static String[] trimArray(String[] array) {
		for (int a = 0; a < array.length; a++) {
			array[0] = array[0].trim();
		}
		return array;
	}

	public static Root root = new Root();

	// Wir benutzen einen StringBuilder, da wir evt. viele Code-Zeilen in ihm
	// speichern wollen, und das Programm so schneller wird
	public static StringBuilder output = new StringBuilder();

	private GUI gui;

	// für progressbar
	public int totalLines = 1;

	public int currentLine = 0;

	public boolean finished = false;

	public static boolean loop = false;

	public static int totalLoopCount = 1;

	public static int currentLoopCount = 0;

	public static boolean abortGeneration = false;

	public void compile(Context context, String input, int absoluteTime) throws GeneratorException {

		String[] lines = input.split("\\n");
		totalLines = lines.length;

		int lineCounter = 1;
		boolean isBlock = false;
		Block block = null;
		boolean lastCondition = false;
		int bracketCounter = 0;

		eachline: for (String line : lines) {
			line = line.trim();
			if (line == null || line.length() == 0 || line.startsWith("//")) {
				lineCounter++;
				continue;
			}

			currentLine = lineCounter;

			boolean analyseTwice = true;

			while (analyseTwice && !abortGeneration) {
				if (isBlock) {
					if (Main.debug)
						System.out.println("analysiere Zeile " + lineCounter + ": " + line);
					if (line.startsWith("}"))
						bracketCounter--;

					if (bracketCounter <= -1) {
						if (Main.debug)
							System.out.println("Block end reached, block will now be analysed...");
						// Block ist zu Ende, Block wird analysiert
						isBlock = false;
						CodeItem codeItem = block;
						try {
							codeItem.analyse();
						} catch (GeneratorException e) {
							e.setContext("Main");
							e.setLine(lineCounter);
							throw e;
						}

						if (block instanceof IfCondition) {
							IfCondition ifcondition = (IfCondition) (block);
							lastCondition = ifcondition.isTrue();
						}
						bracketCounter = 0;
						analyseTwice = true;
						continue;

					} else {
						// if (Main.debug) System.out.println("4: " +
						// bracketCounter);
						block.contentAdd(line);
						analyseTwice = false;
					}

					if (line.endsWith("{"))
						bracketCounter++;

				} else {

					CodeItem codeItem = null;
					if (Main.debug)
						System.out.println();
					if (Main.debug)
						System.out.println("Line " + lineCounter + ": \"" + line + "\"");

					if (line.matches("if\\s*\\(.*")) {
						// if (Main.debug)
						// System.out.println("This is an if-Block");

						if (line.endsWith("{")) {
							if (Main.debug)
								System.out.println("This is an if-Block with brackets");
							block = new IfCondition(context, line, false, absoluteTime, false);
							isBlock = true;
							lineCounter++;
							continue eachline;
						} else {
							if (Main.debug)
								System.out.println("This is a single-line if-Condition");
							codeItem = new IfCondition(context, line, false, absoluteTime, true);
						}
					} else if (line.matches("\\}?\\s*else if\\s*\\(.*")) {
						// if (Main.debug)
						// System.out.println("This is an else-if-Block");

						if (line.endsWith("{")) {
							if (Main.debug)
								System.out.println("This is an else-if-Block with brackets");
							block = new IfCondition(context, line, lastCondition, absoluteTime,
									false);
							isBlock = true;
							lineCounter++;
							continue eachline;
						} else {
							if (Main.debug)
								System.out.println("This is a single-line if-else-Condition");
							codeItem = new IfCondition(context, line, lastCondition, absoluteTime,
									true);
						}
					} else if (line.matches("\\}?\\s*else\\s*.+")) {
						// if (Main.debug)
						// System.out.println("This is an else-Block");

						if (line.endsWith("{")) {
							if (Main.debug)
								System.out.println("This is an else-Block with brackets");
							block = new IfCondition(context, line.substring(line.indexOf("else")),
									lastCondition, absoluteTime, false, true);
							isBlock = true;
							lineCounter++;
							continue eachline;
						} else {
							if (Main.debug)
								System.out.println("This is a single-line else-Condition");
							codeItem = new IfCondition(context,
									line.substring(line.indexOf("else")), lastCondition,
									absoluteTime, true, true);
						}
					} else if (line.matches("at\\s*\\(.*")) {
						// at-block
						if (line.endsWith("{")) {
							if (Main.debug)
								System.out.println("This is a Time-Block with brackets");
							block = new TimeBlock(context, line, absoluteTime, false);
							isBlock = true;
							lineCounter++;
							continue eachline;
						} else {
							if (Main.debug)
								System.out.println("This is a single-line Time-Block");
							codeItem = new TimeBlock(context, line, absoluteTime, true);
						}

					} else if (line.matches("loop\\s*\\(.*")) {
						// at-block
						if (line.endsWith("{")) {
							if (Main.debug)
								System.out.println("This is a Time-Block with brackets");
							block = new Loop(context, line, absoluteTime, false);
							isBlock = true;
							lineCounter++;
							continue eachline;
						} else {
							if (Main.debug)
								System.out.println("This is a single-line Time-Block");
							codeItem = new TimeBlock(context, line, absoluteTime, true);
						}

					} else if (line.matches("method\\s+.*")) {
						// method-block
						if (Main.debug)
							System.out.println("This is a Method-Block");
						block = new NoopBlock();
						isBlock = true;
						lineCounter++;
						continue eachline;
					} else if (line.matches("global\\s+\\S+\\s+\\S+\\s*=\\s*\\S+.*")) {
						if (Main.debug)
							System.out.println("This is a GLOBAL NEW Variable + definition");
						codeItem = new NewVariableDefinition(context, line.replace("global", "")
								.trim(), true);
					} else if (line.matches("\\S+\\s+\\S+\\s*=\\s*\\S+.*")) {
						if (Main.debug)
							System.out.println("This is a NEW Variable + definition");
						codeItem = new NewVariableDefinition(context, line, false);
					} else if (line.matches("\\S+\\s*=\\s*\\S*.*")) {
						if (Main.debug)
							System.out.println("This is a Variable definition");
						codeItem = new VariableDefinition(context, line);

					} else if (line.matches("\\S+\\.\\S+\\(.*\\)")) {
						if (Main.debug)
							System.out.println("This is a static Method");
						codeItem = new StaticMethod(context, line, absoluteTime);

					} else if (line.matches("\\S+\\(.*\\)")) {
						if (Main.debug)
							System.out.println("This is an own Method");
						codeItem = new CustomMethod(context, line, absoluteTime);

					} else if (line.equals("}")) {
						lineCounter++;
						continue eachline;
					} else {
						if (Main.debug)
							System.out.println("WTF is this? D:");
					}

					// Jetzt soll die entsprechende Zeile analysiert werden
					if (codeItem == null) {
						this.setOutput(new String());
						throw new GeneratorException("Main", lineCounter,
								"Unable to understand this line in Main");
					}
					try {
						codeItem.analyse();
					} catch (GeneratorException e) {
						e.setContext("Main");
						e.setLine(lineCounter);
						throw e;
					}

					analyseTwice = false;

				}

			}

			lineCounter++;
		}

		// Analyse ageschlossen

		// Jetzt wollen wir doch mal die Sprite Variablen ins Storyboard
		// umsetzen ^^
		if (!abortGeneration) {
			List<Variable> variables = context.getAllVariables();

			ListIterator<Variable> i = variables.listIterator();
			while (i.hasNext()) {
				Variable var = variables.get(i.nextIndex());
				if (var instanceof SpriteVariable
						&& !(context instanceof Method && var.getName().equals("object"))) {
					SpriteVariable sprite = (SpriteVariable) var;
					Generator.output.append("Sprite," + sprite.getLayer() + ","
							+ sprite.getOrigin() + "," + sprite.getPath() + ",320,240\n");
					Generator.output.append(sprite.getStoryboard());
				}

				i.next();
			}
		}

		finished = true;

	}

	public void setOutput(String str) {
		this.gui.getContentArea().setText(str);
	}

	public void startCompiler(GUI gui) throws GeneratorException {

		// Löschen eines evt. alten Root Elements
		root = new Root();
		this.gui = gui;

		// Löschen alter, globaler Variablen
		variables = new ArrayList<Variable>();

		// Löschen des outputs
		output = new StringBuilder();

		// Compilieren der Main-Klasse
		compileMethods(gui.getMainClassContent());
		compile(root.getMain(), gui.getMainClassContent(), 0);

		// In output sollte jetzt schöner Storyboard-Code zu finden sein, tragen
		// wir es doch in unser Textfenster ein

		this.setOutput(output.toString());

		JOptionPane.showMessageDialog(gui.getContentPanel(),
				"Storyboard-Generation completed without errors.", "Generation Completed",
				JOptionPane.INFORMATION_MESSAGE);

	}

	private void compileMethods(String input) throws GeneratorException {
		// TODO Auto-generated method stub
		// Pre-Compilation für Methoden

		// Wir gehen systematisch durch den Code und analysieren ihn grob
		String[] lines = input.split("\\n");
		totalLines = lines.length;

		// Pre-Compilation für Methoden
		boolean isMethod = false;
		Method method = null;
		int lineCounter = 1;
		int bracketCounter = 0;

		for (String line : lines) {
			if (Main.debug)
				System.out.println("0");
			line = line.trim();
			if (line == null || line.length() == 0 || line.startsWith("//")) {
				lineCounter++;
				continue;
			}

			// In einer Methode drinn
			if (isMethod) {
				if (Main.debug)
					System.out.println("füge Zeile " + lineCounter + " hinzu: " + line);
				if (line.startsWith("}"))
					bracketCounter--;

				if (bracketCounter <= -1) {
					if (Main.debug)
						System.out.println("Method end reached, method will now be added to Root");

					root.addMethod(method);

					isMethod = false;
					method = null;
					bracketCounter = 0;
					lineCounter++;

					continue;

				} else {
					// if (Main.debug) System.out.println("4: " +
					// bracketCounter);
					method.contentAdd(line);
				}

				if (line.endsWith("{"))
					bracketCounter++;

			} else {

				// Finde Methoden und füge sie hinzu
				if (line.matches("method\\s+.*")) {

					if (Main.debug)
						System.out.println("Methode gefunden");
					try {
						method = new Method(line);
						isMethod = true;
					} catch (GeneratorException e) {
						e.setContext("Main");
						e.setLine(lineCounter);
						throw e;
					}

				}

			}
			lineCounter++;
		}
	}

	public static void createGlobalVariable(SpriteVariable sprite) throws GeneratorException {
		// TODO Auto-generated method stub
		Variable sameVariable = searchGlobalVariable(sprite.getName());
		if (sameVariable == null) {
			variables.add(sprite);
			if (Main.debug)
				System.out.println("Sprite Variable angelegt: " + sprite.getName() + " = "
						+ sprite.getPath());
		} else {
			throw new GeneratorException(null, -1, "The variable \"" + sprite.getName()
					+ "\" has already been created");
		}
	}

	public static void createGlobalVariable(String name, boolean value) throws GeneratorException {
		// TODO Auto-generated method stub
		Variable sameVariable = searchGlobalVariable(name);
		if (sameVariable == null) {
			variables.add(new BooleanVariable(name, value));
			if (Main.debug)
				System.out.println("Boolean Variable angelegt: " + name + " = " + value);
		} else {
			throw new GeneratorException(null, -1, "The variable \"" + name
					+ "\" has already been created");
		}
	}

	public static void createGlobalVariable(String name, float value) throws GeneratorException {
		Variable sameVariable = searchGlobalVariable(name);
		if (sameVariable == null) {
			variables.add(new FloatVariable(name, value));
			if (Main.debug)
				System.out.println("Float Variable angelegt: " + name + " = " + value);
		} else {
			throw new GeneratorException(null, -1, "The variable \"" + name
					+ "\" has already been created");
		}
	}

	public static void createGlobalVariable(String name, int value) throws GeneratorException {
		// TODO Auto-generated method stub
		Variable sameVariable = searchGlobalVariable(name);
		if (sameVariable == null) {
			variables.add(new IntVariable(name, value));
			if (Main.debug)
				System.out.println("Int Variable angelegt: " + name + " = " + value);
		} else {
			throw new GeneratorException(null, -1, "The variable \"" + name
					+ "\" has already been created");
		}
	}

	/*
	 * public void createVariable(String name, Object value) { // TODO
	 * Auto-generated method stub
	 * 
	 * }
	 */

	public static void createGlobalVariable(String name, String value) throws GeneratorException {
		Variable sameVariable = searchGlobalVariable(name);
		if (sameVariable == null) {
			variables.add(new StringVariable(name, value));
			if (Main.debug)
				System.out.println("String Variable angelegt: " + name + " = " + value);
		} else {
			throw new GeneratorException(null, -1, "The variable \"" + name
					+ "\" has already been created");
		}
	}

	public static Variable searchGlobalVariable(String variableName) {
		// TODO Auto-generated method stub

		if (Main.debug)
			System.out.println("suche variable global " + variableName);

		if (Main.debug)
			System.out.println("waaaaaa 0");

		// Suche Variable hier
		ListIterator<Variable> i = variables.listIterator();
		while (i.hasNext()) {
			if (Main.debug)
				System.out.println("waaaaaa 0.1");
			if (Main.debug)
				System.out.println(variables.get(i.nextIndex()).getName());
			if (Main.debug)
				System.out.println(variableName);
			if (variables.get(i.nextIndex()).getName().equals(variableName)) {
				if (Main.debug)
					System.out.println("waaaaaa 1");
				return variables.get(i.nextIndex());
			}
			i.next();
		}

		if (Main.debug)
			System.out.println("Variable " + variableName + " wurde nicht gefunden :(");
		return null;
	}

}
