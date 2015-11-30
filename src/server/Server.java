package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Server {

	public static final int PORT = 1313;

	public Server() {
		System.out.println("[SERVEUR] " + "Server Start:");

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

		int nb = 0;
		while(enEcoute){
			try{
				System.out.println("[SERVEUR] " + "prêt a recevoir");
				serverSocket.receive(paquet);
				// accepter une connection et faire un nouveau thread
				System.out.println("[SERVEUR] " + "     reçu");
				(new ServerThread(paquet, gns, nb++)).start();
				paquet = new DatagramPacket(new byte[1024], 1024);
			}catch(Exception e){
				System.err.println("There was a big unknown problem: ");
				e.printStackTrace();
				enEcoute = false;
			}
		}
		System.out.println("[SERVEUR] " + "We close the server.");
		serverSocket.close();
	}

	public static void main(String[] args){
		new Server();
	}
}
