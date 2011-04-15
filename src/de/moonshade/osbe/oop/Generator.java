package de.moonshade.osbe.oop;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import de.moonshade.osbe.gui.GUI;
import de.moonshade.osbe.oop.block.IfCondition;
import de.moonshade.osbe.oop.block.TimeBlock;
import de.moonshade.osbe.oop.exception.GeneratorException;
import de.moonshade.osbe.oop.exception.ParserException;
import de.moonshade.osbe.oop.line.NewVariableDefinition;
import de.moonshade.osbe.oop.line.StaticMethod;
import de.moonshade.osbe.oop.line.VariableDefinition;

public class Generator {

	Root root = new Root();
	public static String output = "";
	private GUI gui;

	public void startCompiler(GUI gui) throws GeneratorException {

		// Löschen eines evt. alten Root Elements
		root = new Root();
		this.gui = gui;
		
		// Löschen des outputs
		output = "";

		// Compilieren der Main-Klasse
		compile(root.getMain(), gui.getMainClassContent(), 0);

		// In output sollte jetzt schöner Storyboard-Code zu finden sein, tragen
		// wir es doch in unser Textfenster ein

		this.setOutput(output);

	}

	public void setOutput(String str) {
		this.gui.getContentArea().setText(str);
	}

	public void compile(Context context, String input, int absoluteTime) throws GeneratorException {

		// Wir gehen systematisch durch den Code und analysieren ihn grob
		String[] lines = input.split("\\n");
		int lineCounter = 1;
		boolean isBlock = false;
		Block block = null;
		boolean lastCondition = false;
		int bracketCounter = 0;

		eachline: for (String line : lines) {
			line = line.trim();
			if (line == null || line.length() == 0) {
				lineCounter++;
				continue;
			}

			boolean analyseTwice = true;

			while (analyseTwice) {
				if (isBlock) {
					System.out.println("analysiere Zeile " + lineCounter + ": " + line);
					if (line.startsWith("}"))
						bracketCounter--;

					if (bracketCounter <= -1) {
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
						// System.out.println("4: " + bracketCounter);
						block.contentAdd(line);
						analyseTwice = false;
					}

					if (line.endsWith("{"))
						bracketCounter++;

				} else {

					CodeItem codeItem = null;
					System.out.println();
					System.out.println("Line " + lineCounter + ": \"" + line + "\"");

					if (line.matches("if\\s*\\(.*")) {
						// System.out.println("This is an if-Block");

						if (line.endsWith("{")) {
							System.out.println("This is an if-Block with brackets");
							block = new IfCondition(context, line, false, absoluteTime);
							isBlock = true;
							lineCounter++;
							continue eachline;
						} else {
							System.out.println("This is a single-line if-Condition");
							codeItem = new IfCondition(context, line, false, absoluteTime);
						}
					} else if (line.matches("\\}?\\s*else if\\s*\\(.*")) {
						// System.out.println("This is an else-if-Block");

						if (line.endsWith("{")) {
							System.out.println("This is an else-if-Block with brackets");
							block = new IfCondition(context, line.substring(line.indexOf("if")),
									lastCondition, absoluteTime);
							isBlock = true;
							lineCounter++;
							continue eachline;
						} else {
							System.out.println("This is a single-line if-else-Condition");
							codeItem = new IfCondition(context, line, lastCondition, absoluteTime);
						}
					} else if (line.matches("at\\s*\\(.*")) {
						// at-block
						if (line.endsWith("{")) {
							System.out.println("This is a Time-Block with brackets");

						} else {
							System.out.println("This is a single-line Time-Block");
							codeItem = new TimeBlock(context, line, absoluteTime, true);
						}

					} else if (line.matches("\\S+\\s+\\S+\\s*=\\s*\\S+.*")) {
						System.out.println("This is a NEW Variable + definition");
						codeItem = new NewVariableDefinition(context, line);
					} else if (line.matches("\\S+\\s*=\\s*\\S*.*")) {
						System.out.println("This is a Variable definition");
						codeItem = new VariableDefinition(context, line);

					} else if (line.matches("\\S+\\.\\S+\\(.*\\)")) {
						System.out.println("This is a static Method");
						codeItem = new StaticMethod(context, line, absoluteTime);

					} else if (line.equals("}")) {
						lineCounter++;
						continue eachline;
					} else {
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
		List<Variable> variables = context.getAllVariables();

		ListIterator<Variable> i = variables.listIterator();
		while (i.hasNext()) {
			Variable var = variables.get(i.nextIndex());
			if (var instanceof SpriteVariable) {
				SpriteVariable sprite = (SpriteVariable) var;
				Generator.output += "Sprite," + sprite.getLayer() + "," + sprite.getOrigin() + ","
						+ sprite.getPath() + ",320,240\n";
				Generator.output += sprite.getStoryboard();
			}

			i.next();
		}

	}

	private static String[] trimArray(String[] array) {
		for (int a = 0; a < array.length; a++) {
			array[0] = array[0].trim();
		}
		return array;
	}

	public static Variable getVariable(Context context, String variableName) {

		return context.searchVariable(variableName);

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
				String variableName = expression.substring(matcher.start(), matcher.end());

				System.out.println("1: " + variableName);

				int ende = matcher.end();
				if (variableName.endsWith("\\W")) {
					variableName = expression.substring(matcher.start(), matcher.end() - 1);
					ende -= 1;
				}
				System.out.println("2: " + variableName);

				System.out.println(variableName + " ist eine Variable. Suche Wert...");
				Variable variable = context.searchVariable(variableName);
				expression = expression.substring(0, matcher.start()) + variable.getStringValue()
						+ expression.substring(ende);
				System.out.println("Neue Expression: " + expression);
				found = true;
			}

		} while (found);
		return expression;
	}

	private static String encodeIntMethods(Context context, String expression)
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

				System.out
						.println(methodName + " ist eine Methode. Parameter: " + methodParameters);

				expression = expression.substring(0, matcher.start())
						+ intMethods(context, methodName, splitParameters(methodParameters))
						+ expression.substring(matcher.start() + lastBracket + 1);
				System.out.println("Neue Expression: " + expression);

				found = true;
			}

		} while (found);
		return expression;
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
		parameterList.add(buffer);

		return parameterList.toArray(new String[0]);
	}

	private static int intMethods(Context context, String name, String[] parameter)
			throws GeneratorException {

		if (name.equals("rand")) {
			if (parameter.length == 2) {
				Random generator = new Random();
				int start = Generator.encodeIntegerExpression(context, parameter[0]);
				int end = Generator.encodeIntegerExpression(context, parameter[1]);
				return start + generator.nextInt(end - start + 1);
			}
		}

		throw new GeneratorException(null, -1, "Cannot find integer method \"" + name + "\"");
	}

	public static int encodeIntegerExpression(Context context, String expression)
			throws GeneratorException {

		expression = encodeIntMethods(context, expression);
		expression = encodeVariables(context, expression);

		float result;
		try {
			result = Float.parseFloat(new ScriptEngineManager().getEngineByName("javascript")
					.eval(expression).toString());
		} catch (Exception ex) {
			throw new ParserException(null, -1, "Unable to parse \"" + expression
					+ "\" to an integer value");
		}
		// System.out.print(result);

		return (int) Math.round(result);

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

	public static boolean encodeBooleanExpression(Context context, String expression)
			throws GeneratorException {

		expression = encodeIntMethods(context, expression);
		expression = encodeVariables(context, expression);

		try {
			return Boolean.parseBoolean(new ScriptEngineManager().getEngineByName("javascript")
					.eval(expression).toString());
		} catch (ScriptException e) {
			e.printStackTrace();
			throw new ParserException(null, -1, "Unable to parse \"" + expression
					+ "\" to a boolean value");
		}
	}

	public static String encodeStringExpression(Context context, String expression)
			throws GeneratorException {

		expression = encodeVariables(context, expression);

		try {
			return new ScriptEngineManager().getEngineByName("javascript").eval(expression)
					.toString();
		} catch (ScriptException e) {
			e.printStackTrace();
			throw new ParserException(null, -1, "Unable to parse \"" + expression
					+ "\" to a string");
		}
	}

}
