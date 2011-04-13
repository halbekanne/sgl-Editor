package de.moonshade.osbe.oop;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import de.moonshade.osbe.gui.GUI;
import de.moonshade.osbe.oop.block.IfCondition;
import de.moonshade.osbe.oop.exception.GeneratorException;
import de.moonshade.osbe.oop.exception.ParserException;
import de.moonshade.osbe.oop.line.NewVariableDefinition;
import de.moonshade.osbe.oop.line.VariableDefinition;

public class Generator {

	Root root = new Root();

	public void startCompiler(GUI gui) throws GeneratorException {

		// Löschen eines evt. alten Root Elements
		root = new Root();

		// Compilieren der Main-Klasse
		Generator.compile(root.getMain(), gui.getMainClassContent());

	}

	public static void compile(Context context, String input)
			throws GeneratorException {

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
					System.out.println("analysiere Zeile " + lineCounter + ": "
							+ line);
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
						//System.out.println("4: " + bracketCounter);
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
						//System.out.println("This is an if-Block");

						if (line.endsWith("{")) {
							System.out.println("This is an if-Block with brackets");
							block = new IfCondition(context, line, false);
							isBlock = true;
							lineCounter++;
							continue eachline;
						} else {
							System.out.println("This is a single-line if-Condition");
							codeItem = new IfCondition(context, line, false);
						}
					} else if (line.matches("\\}?\\s*else if\\s*\\(.*")) {
						//System.out.println("This is an else-if-Block");

						if (line.endsWith("{")) {
							System.out.println("This is an else-if-Block with brackets");
							block = new IfCondition(context,
									line.substring(line.indexOf("if")),
									lastCondition);
							isBlock = true;
							lineCounter++;
							continue eachline;
						} else {
							System.out.println("This is a single-line if-else-Condition");
							codeItem = new IfCondition(context, line,
									lastCondition);
						}
					} else if (line.matches("\\S+\\s+\\S+\\s*=\\s*\\S+.*")) {
						System.out
								.println("This is a NEW Variable + definition");
						codeItem = new NewVariableDefinition(context, line);
					} else if (line.matches("\\S+\\s*=\\s*\\S*.*")) {
						System.out.println("This is a Variable definition");
						codeItem = new VariableDefinition(context, line);
					} else if (line.equals("}")) {
						lineCounter++;
						continue eachline;
					} else {
						System.out.println("WTF is this? D:");
					}

					// Jetzt soll die entsprechende Zeile analysiert werden
					if (codeItem == null) {
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

	private static String encodeVariables(Context context, String expression) {

		boolean found;
		do {
			found = false;

			Pattern pattern;
			Matcher matcher;
			pattern = Pattern.compile("[a-zA-Z]\\w*\\W");
			matcher = pattern.matcher(expression);
			if (matcher.find()) {
				String variableName = expression.substring(matcher.start(),
						matcher.end() - 1);
				System.out.println(variableName
						+ " ist eine Variable. Suche Wert...");
				Variable variable = context.searchVariable(variableName);
				expression = expression.substring(0, matcher.start())
						+ variable.getStringValue()
						+ expression.substring(matcher.end() - 1);
				System.out.println("Neue Expression: " + expression);
				found = true;
			}

		} while (found);
		return expression;
	}

	public static int encodeIntegerExpression(Context context, String expression)
			throws GeneratorException {

		expression = encodeVariables(context, expression);

		float result;
		try {
			result = Float.parseFloat(new ScriptEngineManager()
					.getEngineByName("javascript").eval(expression).toString());
		} catch (Exception ex) {
			throw new ParserException(null, -1, "Unable to parse \""
					+ expression + "\" to an integer value");
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

	public static boolean encodeBooleanExpression(Context context,
			String expression) throws GeneratorException {

		expression = encodeVariables(context, expression);

		try {
			return Boolean.parseBoolean(new ScriptEngineManager()
					.getEngineByName("javascript").eval(expression).toString());
		} catch (ScriptException e) {
			e.printStackTrace();
			throw new ParserException(null, -1, "Unable to parse \""
					+ expression + "\" to a boolean value");
		}
	}

	public static String encodeStringExpression(Context context,
			String expression) throws GeneratorException {

		expression = encodeVariables(context, expression);

		try {
			return new ScriptEngineManager().getEngineByName("javascript")
					.eval(expression).toString();
		} catch (ScriptException e) {
			e.printStackTrace();
			throw new ParserException(null, -1, "Unable to parse \""
					+ expression + "\" to a string");
		}
	}

}
