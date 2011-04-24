package de.aqua.osbe.oosbl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import de.moonshade.osbe.Main;

public class HttpPost {

	private StringBuilder data;
	private String url;

	public HttpPost(String url) {
		this.data = new StringBuilder();
		this.url = url;
	}

	public void addData(String key, String value) {
		try {
			if (data.length() == 0) {
				this.data.append(URLEncoder.encode(key, "UTF-8")).append("=")
						.append(URLEncoder.encode(value, "UTF-8"));

			} else {
				this.data.append("&").append(URLEncoder.encode(key, "UTF-8")).append("=")
						.append(URLEncoder.encode(value, "UTF-8"));
			}

			if (Main.debug) System.out.println(this.data.toString());
		} catch (UnsupportedEncodingException unsupportedEncodingException) {
			System.out.print(unsupportedEncodingException.getStackTrace());
		}
	}

	public String postData() {

		try {
			URL url = new URL(this.url);
			URLConnection conn = url.openConnection();
			conn.setDoOutput(true);

			OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
			wr.write(this.data.toString());
			wr.flush();

			// Get the response
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			StringBuilder output = new StringBuilder();

			while ((line = rd.readLine()) != null) {
				output.append(line);
				output.append(System.getProperty("line.separator"));
			}
			wr.close();
			rd.close();

			return output.toString();

		} catch (IOException iOException) {
			System.out.print(iOException.getStackTrace());
			return "False";
		}

	}
}
