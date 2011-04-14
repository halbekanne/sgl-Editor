package de.moonshade.osbe.oop.block;

import de.moonshade.osbe.oop.Block;
import de.moonshade.osbe.oop.Context;
import de.moonshade.osbe.oop.Generator;
import de.moonshade.osbe.oop.exception.GeneratorException;

public class IfCondition extends Block {

	Context context;
	boolean condition = false;
	boolean lastCondition;

	public IfCondition(Context context, String line, boolean lastCondition, int absoluteTime) {
		this.context = context;
		this.content = line;
		this.lastCondition = lastCondition;
		this.absoluteTime = absoluteTime;
	}
	
	public void analyse() throws GeneratorException {
		String expression = content.substring(content.indexOf("(") + 1, content.lastIndexOf(")"));
		//System.out.println("expression: " + expression);
		//System.out.println("content string: " + contentString);
		
		// ist die behauptung wahr? Ist der letzte if-block wahr gewesen? (für else und else if)
		if (!lastCondition && Generator.encodeBooleanExpression(context, expression)) {
			System.out.println("Die Bedingung ist wahr, Anweisungen werden ausgeführt.");
			condition = true;
			// wenn die behauptung wahr ist, dann sollten wir auch den contextString ausführen
			// übrigens wow neuer kontext, der Block hier ist jetzt der neue Kontext damit wir lokale variablen und son kram finden
			Generator.compile(this, contentString, absoluteTime);
		} else {
			System.out.println("Die Bedingung ist falsch oder else-if Block, die Anweisungen werden nicht ausgeführt!");
		}
		
	}
	
	public boolean isTrue() {
		return condition;
	}
	
}
