package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Server {

	public static final int PORT = 1313;

	public Server() {
		System.out.println("Server Start:");

		DatagramSocket serverSocket = null;
		DatagramPacket paquet = null;
		Boolean enEcoute = true;

		try {
			serverSocket = new DatagramSocket(PORT);
			paquet = new DatagramPacket(new byte[1024], 1024);
		} catch (IOException e) {
			System.err.println("IOException on port: " + PORT);
			e.printStackTrace();
			System.exit(-1);
		}
		
		GestionNomSurnom gns = new GestionNomSurnom();

		while(enEcoute){
			try{
				serverSocket.receive(paquet);
				// accepter une connection et faire un nouveau thread
				(new ServerThread(paquet, gns)).start();
			}catch(Exception e){
				System.err.println("There was a big unknown problem: ");
				e.printStackTrace();
				enEcoute = false;
			}
		}
		System.out.println("We close the server.");
		serverSocket.close();
	}

	public static void main(String[] args){
		new Server();
	}
}
