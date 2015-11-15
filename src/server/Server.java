package server;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {
	
	public static void main(String[] args) {
		System.out.println("Server Start:");

		ServerSocket serverSocket = null;
		Boolean enEcoute = true;
		int port = 1313;
		
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			System.err.println("IOException on port: " + port);
			e.printStackTrace();
			System.exit(-1);
		}
		
		GestionNomSurnom gns = new GestionNomSurnom();

		while(enEcoute){
			try{
				// accepter une connection et faire un nouveau thread
				(new ServerThread(serverSocket.accept(), gns)).start();
			}catch(Exception e){
				System.err.println("There was a big unknown problem: ");
				e.printStackTrace();
				enEcoute = false;
			}
		}
		try {
			System.out.println("We close the server.");
			serverSocket.close();
		} catch (IOException e) {
			System.err.println("An IOException have appeared when we close the server:");
			e.printStackTrace();
		}
	}
}
