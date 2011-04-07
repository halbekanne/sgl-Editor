package de.moonshade.osbe.oop;

import java.util.ArrayList;
import java.util.List;

public class Root {

	private MainClass main = new MainClass();
	private List<Class> classes = new ArrayList<Class>();
	
	/**
	 * @return the main
	 */
	public MainClass getMain() {
		return main;
	}
	/**
	 * @param main the main to set
	 */
	public void setMain(MainClass main) {
		this.main = main;
	}
	/**
	 * @return the classes
	 */
	public List<Class> getClasses() {
		return classes;
	}
	/**
	 * @param classes the classes to set
	 */
	public void setClasses(List<Class> classes) {
		this.classes = classes;
	}
	
	
}
