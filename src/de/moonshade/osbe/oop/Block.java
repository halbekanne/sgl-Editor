package de.moonshade.osbe.oop;

import java.util.ArrayList;
import java.util.List;

public class Block extends CodeItem {

	protected String contentString = "";
	
	public void contentAdd(String line) {
		contentString = contentString + "\n" + line;
	}

}
