package de.moonshade.osbe.oop.block;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.moonshade.osbe.oop.Block;
import de.moonshade.osbe.oop.Context;
import de.moonshade.osbe.oop.Generator;
import de.moonshade.osbe.oop.exception.GeneratorException;

public class TimeBlock extends Block {

	Context context;
	boolean singleLine;
	
	public TimeBlock(Context context, String line, int absoluteTime, boolean singleLine) {
		this.parentContext = context;
		this.content = line;
		this.absoluteTime = absoluteTime;
		this.singleLine = singleLine;
	}
	
	public void analyse() throws GeneratorException {
		
		// wir suchen das Ende der AT-Zeitangabenklammer
		char[] chars = content.toCharArray();
		int firstBracket = 0;
		int lastBracket = 0;
		int counter = 0;
		for(int a = 0; a < chars.length; a++) {
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
		
		String expression = content.substring(firstBracket + 1, lastBracket);
		int time = absoluteTime + Generator.encodeIntegerExpression(context, expression);
		
		if (singleLine) {
			// Befehl kommt direkt nach den runden Klammern
			String command =  content.substring(lastBracket + 1).trim();
			Generator generator = new Generator();
			generator.compile(this, command, time);
			// Befehl wird mit der erforderlichen zeit ausgeführt
			
		}
		
	}
	

}
