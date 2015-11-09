package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.UnknownHostException;

public class Server {
	
	public static void main(String[] args) {
		ServerSocket socket = null;
		
		Boolean enEcoute = true;
	
		// ObjectOutputStream os = null;  // output stream
		// ObjectInputStream is = null;   // input stream
		try {
			socket = new ServerSocket(1234);
			// os = new ObjectOutputStream(socket.getOutputStream());
			// is = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			System.err.println("J'ai pas reçu I/O");
		}
		
		while(enEcoute){
			// accepter une connection
			// faire un nouveau thread
		}
		// socket.close();
	}
}
