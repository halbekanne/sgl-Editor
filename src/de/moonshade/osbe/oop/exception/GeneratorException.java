package de.moonshade.osbe.oop.exception;

public class GeneratorException extends Exception {

	private static final long serialVersionUID = 1L;
	private int line;
	private String context;

	public GeneratorException(String context, int line, String msg) {
		super(msg);
		setContext(context);
		setLine(line);
	}

	public void setLine(int line) {
		this.line = line;
	}

	public int getLine() {
		return line;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getContext() {
		return context;
	}

}
