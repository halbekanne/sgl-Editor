package de.moonshade.osbe.oop;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import mathExpr.ExpressionConfiguration;
import mathExpr.evaluator.Type;
import mathExpr.evaluator.realEvaluator.Real;
import mathExpr.evaluator.realEvaluator.RealType;

import de.moonshade.osbe.oop.exception.GeneratorException;
import de.moonshade.osbe.oop.exception.ParserException;
import de.moonshade.osbe.oop.line.NewVariableDefinition;

public class Generator {

	Root root = new Root();

	public void compile(String input) throws GeneratorException {
		// Wir besorgen uns ein Root Element
		root = new Root();

		// Wir gehen systematisch durch den Code und analysieren ihn grob
		String[] lines = input.split("\\n");
		int lineCounter = 1;
		for (String line : lines) {
			line = line.trim();
			if (line == null || line.length() == 0) {
				lineCounter++;
				continue;
			}

			CodeItem codeItem = null;
			MainClass context = root.getMain();

			if (line.matches("\\S+\\s+\\S+\\s*=\\s*\\S+.*")) {
				System.out.println("This is a Variable definition");
				codeItem = new NewVariableDefinition(context, line);
			} else {
				System.out.println("This is not a Variable definition :(");
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

	public static int encodeIntegerExpression(Context context, String expression)
			throws GeneratorException {

		// 1.) default type will be real
		Type defaultType = RealType.TYPE;

		// 2.) create ExpressionConfiguration
		ExpressionConfiguration config = new ExpressionConfiguration(
				defaultType);

		// 3.) parse an expression
		Real real;
		try {
			config.setExpression(expression);

			// 4.) evaluate the parsed expression
			real = (Real) config.evaluateExpression();
		} catch (Throwable e) {
			throw new ParserException(null, -1, "Unable to parse expression \""
					+ expression + "\" to an integer value.");
		}
		double result = real.getValue();
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

	public static boolean encodBooleanExpression(Context context,
			String expression) throws GeneratorException {
		try {
			return Boolean.parseBoolean(new ScriptEngineManager().getEngineByName("javascript").eval(expression).toString());
		} catch (ScriptException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
}
