package server;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

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
		} catch (SocketException e) {
			System.err.println("[SERVEUR] " + "SocketException on port: " + PORT);
			e.printStackTrace();
			System.exit(-1);
		}
		
		GestionNomSurnom gns = new GestionNomSurnom();
		// pour explicité le message des Threads
		int nbThread = 0;

		while(enEcoute){
			try {
				System.out.println("[SERVEUR] " + "prêt a recevoir");
				serverSocket.receive(paquet);
				// accepter une connection et faire un nouveau thread
				System.out.println("[SERVEUR] " + "     reçu");
				(new ServerThread(paquet, gns, nbThread++)).start();
				paquet = new DatagramPacket(new byte[1024], 1024);
			}catch(Exception e){
				// le plus normale serait une IOException de receive
				System.err.println("[SERVEUR] " + "There was a big unknown problem: ");
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
