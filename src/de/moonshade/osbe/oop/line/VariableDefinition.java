package de.moonshade.osbe.oop.line;

import de.moonshade.osbe.oop.Context;
import de.moonshade.osbe.oop.Generator;
import de.moonshade.osbe.oop.Line;
import de.moonshade.osbe.oop.Variable;
import de.moonshade.osbe.oop.exception.GeneratorException;

public class VariableDefinition extends Line {

	private Context context;
	
	public VariableDefinition(Context context, String line) {
		this.context = context;
		this.content = line;
	}
	
	
	public void analyse() throws GeneratorException {
		String[] parts = content.split(" ");
		Variable variable = Generator.getVariable(context, parts[0]);
		
		
	}	
	
	
	
	
}
