package de.moonshade.osbe.oop;

import java.util.ArrayList;
import java.util.List;

public class Block extends CodeItem {

	protected String contentString = "";
	protected int absoluteTime = 0;
	
	public void contentAdd(String line) {
		contentString = contentString + "\n" + line;
	}

	public void setAbsoluteTime(int absoluteTime) {
		this.absoluteTime = absoluteTime;
	}
	
	public int getAbsoluteTime() {
		return absoluteTime;
	}
	
}
