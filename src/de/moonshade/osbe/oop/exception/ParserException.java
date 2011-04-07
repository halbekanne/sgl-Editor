package de.moonshade.osbe.oop.exception;

public class ParserException extends GeneratorException {
	
	public ParserException(String context, int line, String msg) {
		super(context, line, msg);
	}

}
