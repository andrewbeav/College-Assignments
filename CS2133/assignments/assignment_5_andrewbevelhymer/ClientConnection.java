import java.io.*;
import java.net.*;

public class ClientConnection implements Runnable {
	private Socket socket;

	public ClientConnection(Socket socket) {
		this.socket = socket;
	}

	public void run() {
		try {
			BufferedReader in =
				new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String request = in.readLine();
			String fileName = getFileNameFromRequest(request);
			sendFile(fileName);
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}

	private void sendFile(String fileName) {
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		}
		catch(IOException e) {
			e.printStackTrace();
		}

		File file;
		FileInputStream fileStream;
		try {
			file = new File(fileName);
			fileStream = new FileInputStream(file);
			sendFile(fileName, out, file, fileStream);
		}
		catch(FileNotFoundException e) {
			send404(out);
		}

	}

	private void sendFile(String fileName, BufferedWriter out, File file, FileInputStream fileStream) {
		try {
			byte[] fileArray = new byte[(int)file.length()];
			fileStream.read(fileArray);
			String output = new String(fileArray, "UTF-8");
			out.write("HTTP/1.1 200 OK\r\nContent-type: text/html\r\n\r\n");
			out.write(output);
			out.flush();
			out.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}

	private void send404(BufferedWriter out) {
		try {
			out.write("HTTP/1.1 404 Not Found\r\n\r\n");
			out.flush();
			out.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}

	private String getFileNameFromRequest(String request) {
		String fileName = request.substring(4 , request.length()-1);
		fileName =  fileName.substring(1, fileName.indexOf(" "));
		if (fileName.equals("") || fileName.startsWith("..")) {
			fileName = "index.html";
		}
		return fileName;
	}
}
