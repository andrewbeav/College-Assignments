import java.io.*;
import java.net.*;

public class NetworkReader {
	String urlString;
	String fileLocation = "/";
	Webpage webpage;

	public NetworkReader(String urlString) {
		if (urlString.contains("/")) {
			String tmp = urlString.substring(0, urlString.indexOf("/"));
			fileLocation = urlString.substring(urlString.indexOf("/"), urlString.length());
			urlString = tmp;
		}

		this.urlString = urlString;
		this.webpage = new Webpage(getPage());
	}

	public Webpage getWebpage() {
		return this.webpage;
	}

	public String getPage() {
		String result = "";
		String line;

		try {
			Socket socket = new Socket(urlString, 80);
			PrintWriter out = new PrintWriter(socket.getOutputStream());
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out.print("GET " + fileLocation + " HTTP/1.1\r\n");
			out.print("host: " + urlString + "\r\n\r\n");
			out.flush();

			while ((line = in.readLine()) != null) {
				result += line + "\n";
			}

		} catch(Exception e) {
			e.printStackTrace();
		}

		return result;
	}
}
