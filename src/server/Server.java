package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Server {
	
	public static void main(String[] args) {
		System.out.println("Server Start:");

		DatagramSocket serverSocket = null;
		DatagramPacket paquet = null;
		Boolean enEcoute = true;
		int port = 1313;
		
		try {
			serverSocket = new DatagramSocket(port);
		} catch (IOException e) {
			System.err.println("IOException on port: " + port);
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
}
