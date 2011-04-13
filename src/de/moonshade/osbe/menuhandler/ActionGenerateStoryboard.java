package de.moonshade.osbe.menuhandler;

import javax.swing.JOptionPane;

import de.moonshade.osbe.oop.Generator;
import de.moonshade.osbe.oop.exception.GeneratorException;

public class ActionGenerateStoryboard extends MenuAction {

	@Override
	public void start() {
		// TODO Auto-generated method stub
		try {
			Generator generator = new Generator();
			generator.startCompiler(gui);
		} catch (GeneratorException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, e.getClass().getSimpleName() + " in " + e.getContext() + ", line " + e.getLine() + ":\n" + e.getMessage() + ".", "Generator Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

}
