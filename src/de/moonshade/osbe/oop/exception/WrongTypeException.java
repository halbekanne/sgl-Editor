package de.moonshade.osbe.oop.exception;

public class WrongTypeException extends GeneratorException {

	public WrongTypeException(String context, int line, String msg) {
		super(context, line, msg);
	}

}
