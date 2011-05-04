package de.moonshade.osbe.menuhandler;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class ActionOnlineDocs extends MenuAction {

	@Override
	public void start() {
		// TODO Auto-generated method stub
		
		Desktop desktop = Desktop.getDesktop();
		  URI uri;
		try {
			uri = new URI("https://github.com/MoonShade/sgl-Editor/wiki");
			desktop.browse(uri);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}

}
