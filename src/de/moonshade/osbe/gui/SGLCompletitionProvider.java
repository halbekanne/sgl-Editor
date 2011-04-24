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

package de.moonshade.osbe.gui;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.fife.ui.autocomplete.AbstractCompletion;
import org.fife.ui.autocomplete.CompletionProvider;
import org.fife.ui.autocomplete.DefaultCompletionProvider;
import org.fife.ui.autocomplete.LanguageAwareCompletionProvider;

public class SGLCompletitionProvider {

	static List<AbstractCompletion> completitions = new ArrayList<AbstractCompletion>();

	public CompletionProvider getProvider() {

		DefaultCompletionProvider provider = new DefaultCompletionProvider();
		provider.setAutoActivationRules(false, ".");

		LanguageAwareCompletionProvider languageProvider = new LanguageAwareCompletionProvider(
				provider);

		/*
		 * FunctionCompletion function = new FunctionCompletion(provider,
		 * "rand", "int");
		 * 
		 * List<Parameter> params = new ArrayList<Parameter>(); params.add(new
		 * Parameter("int","start")); params.add(new Parameter("int","end"));
		 * function.setParams(params);
		 * 
		 * provider.addCompletion(function); provider.addCompletion(new
		 * BasicCompletion(provider, "abstract")); provider.addCompletion(new
		 * BasicCompletion(provider, "area"));
		 */

		
		
		
		ClassLoader cl = getClass().getClassLoader();
		InputStream in = cl.getResourceAsStream("sgl.xml");
		try {
			if (in!=null) {
				provider.loadFromXML(in);
				in.close();
			}
			else {
				JOptionPane.showMessageDialog(
					null,
					"Nicht gefunden",
					"blaa", JOptionPane.ERROR_MESSAGE);
				provider.loadFromXML(new File("sgl.xml"));
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
			JOptionPane.showMessageDialog(
					null,
					"oh noes",
					"blaa", JOptionPane.ERROR_MESSAGE);
			ioe.printStackTrace();
		}
		
		
		
		try {
			provider.loadFromXML(new File("lang/sgl.xml"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			StackTraceElement[] errStack = e.getStackTrace();
			String stackTrace = "";
			for (StackTraceElement elem : errStack) {
				stackTrace += "at " + elem.toString() + "\n";
			}
			
			
		}

		// completitions.add( function);

		return languageProvider;
	}

}
