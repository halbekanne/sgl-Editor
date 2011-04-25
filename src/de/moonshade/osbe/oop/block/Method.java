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

package de.moonshade.osbe.oop.block;

import java.util.ArrayList;
import java.util.List;

import de.moonshade.osbe.oop.Block;
import de.moonshade.osbe.oop.CodeItem;
import de.moonshade.osbe.oop.Context;
import de.moonshade.osbe.oop.Generator;
import de.moonshade.osbe.oop.SpriteVariable;
import de.moonshade.osbe.oop.Variable;
import de.moonshade.osbe.oop.exception.GeneratorException;
import de.moonshade.osbe.oop.exception.UnknownTypeException;

public class Method extends Block {

	String name = "";
	List<String> paramType = new ArrayList<String>();
	List<String> paramName = new ArrayList<String>();
	private String[] currentParameters = null;
	private SpriteVariable currentObject = null;

	@Override
	public void analyse() throws GeneratorException {

		// Setze Variablen im Methodenkontext mit den übergebenen Werten. (call
		// by value)
		if (this.currentParameters != null) {
			for (int a = 0; a < currentParameters.length; a++) {
				if (paramType.get(a).equals("int")) {
					this.createVariable(paramName.get(a), Integer.parseInt(currentParameters[a]));
				} else if (paramType.get(a).equals("float")) {
					this.createVariable(paramName.get(a), Float.parseFloat(currentParameters[a]));
				} else if (paramType.get(a).equals("boolean")) {
					this.createVariable(paramName.get(a), Boolean.parseBoolean(currentParameters[a]));
				} else {
					throw new UnknownTypeException(null, -1, "Parameter type " + paramType.get(a)
							+ " is unknown or not supported");
				}

			}
		}

		// Setze Variablenreferenz für ein mit der Methode verbundenes Object
		// (call by reference)
		String originalName = "";
		if (this.currentObject != null) {
			originalName = currentObject.getName();
			currentObject.setName("object");
			this.createVariable(currentObject);
		}
		
		// So, jetzt ist alles bereit, damit wir unsere Methode ausführen können
		Generator generator = new Generator();
		generator.compile(this, contentString, absoluteTime);
		
		// Danach
		if (this.currentObject != null) {
			currentObject.setName(originalName);
		}
		currentParameters = null;
		
	}
	
	

	public String getName() {
		return name;
	}

	public List<String> getParamType() {
		return paramType;
	}

	public List<String> getParamName() {
		return paramName;
	}

	public Method(String line) throws GeneratorException {

		this.content = line;
		preCompile();
	}

	private void preCompile() throws GeneratorException {
		System.out.println("line: " + content);
		// wir suchen das Ende der Methoden-Parametersklammer
		char[] chars = content.toCharArray();
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

		String methodParams = content.substring(firstBracket + 1, lastBracket);
		this.name = content.substring(0, firstBracket).replace("method", "").trim();

		System.out.println("methodParams: " + methodParams);
		System.out.println("methodName: " + name);

		String[] parameterDefinitions = Generator.splitParameters(methodParams);
		for (String paramDef : parameterDefinitions) {
			System.out.println("Parameter: " + paramDef);
			String[] paramDefSplitted = paramDef.split(" ");
			if (paramDefSplitted.length == 2) {
				paramType.add(paramDefSplitted[0]);
				paramName.add(paramDefSplitted[1]);
			} else {
				throw new GeneratorException(null, -1, "Error in parameter definition list.");
			}
			System.out.println();
		}

	}

	public void setCurrentParameters(String[] currentParameters) {
		// TODO Auto-generated method stub
		this.currentParameters = currentParameters;
	}

	public void setCurrentObject(SpriteVariable currentObject) {
		// TODO Auto-generated method stub
		this.currentObject = currentObject;
	}

}
