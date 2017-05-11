import java.io.*;
import java.net.*;

public class Webserver {
	public static void main(String[] args) {
		try {
			ServerSocket ss = new ServerSocket(8080);
			while(true) {
				Thread conn = 
					new Thread(new ClientConnection(ss.accept()));
				conn.start();
			}
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
}
