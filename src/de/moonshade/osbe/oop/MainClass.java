package de.moonshade.osbe.oop;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class MainClass extends Context {

	private List<CodeItem> codeItems = new ArrayList<CodeItem>();

	public void setCodeItems(List<CodeItem> codeItems) {
		this.codeItems = codeItems;
	}

	public List<CodeItem> getCodeItems() {
		return codeItems;
	}

	
}
