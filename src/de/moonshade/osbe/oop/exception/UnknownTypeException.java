package de.moonshade.osbe.oop.exception;

public class UnknownTypeException extends GeneratorException {

	public UnknownTypeException(String context, int line, String msg) {
		super(context, line, msg);
	}
	
}
