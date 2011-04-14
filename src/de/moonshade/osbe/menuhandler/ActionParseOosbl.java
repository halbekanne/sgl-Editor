package de.moonshade.osbe.menuhandler;


import de.aqua.osbe.oosbl.HttpPost;
public class ActionParseOosbl extends MenuAction {

	@Override
	public void start() {
		// TODO Auto-generated method stub
		try {
                        String oosbl = gui.getMainClassContent();

			HttpPost post = new HttpPost("http://aqua3.bplaced.net/osu/parser.php");
                        post.addData("songlength", "13337");
                        post.addData("noBr", "true");
                        post.addData("oosbl", oosbl);

                        gui.getContentArea().setText(post.postData());

		} catch (Exception e) {
                    System.out.print(e.getStackTrace());
		}
	}

}
