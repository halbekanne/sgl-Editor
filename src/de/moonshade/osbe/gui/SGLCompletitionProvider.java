package de.moonshade.osbe.gui;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.fife.ui.autocomplete.BasicCompletion;
import org.fife.ui.autocomplete.CompletionProvider;
import org.fife.ui.autocomplete.AbstractCompletion;
import org.fife.ui.autocomplete.DefaultCompletionProvider;
import org.fife.ui.autocomplete.FunctionCompletion;
import org.fife.ui.autocomplete.LanguageAwareCompletionProvider;
import org.fife.ui.autocomplete.ParameterizedCompletion.Parameter;

public class SGLCompletitionProvider {
	
	static List<AbstractCompletion> completitions = new ArrayList<AbstractCompletion>();

	public static CompletionProvider getProvider() {
		
		DefaultCompletionProvider provider = new DefaultCompletionProvider();
		provider.setAutoActivationRules(false, ".");
		
		LanguageAwareCompletionProvider languageProvider = new LanguageAwareCompletionProvider(provider);
		
		/*
		FunctionCompletion function = new FunctionCompletion(provider, "rand", "int");
		
		List<Parameter> params = new ArrayList<Parameter>();
		params.add(new Parameter("int","start"));
		params.add(new Parameter("int","end"));
		function.setParams(params);	
		
		provider.addCompletion(function);
		provider.addCompletion(new BasicCompletion(provider, "abstract"));
		provider.addCompletion(new BasicCompletion(provider, "area"));
		*/
		
		try {
			provider.loadFromXML(new File("lang/sgl.xml"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// completitions.add( function);
		
		
		
		
		
		return languageProvider;
	}
	
}
