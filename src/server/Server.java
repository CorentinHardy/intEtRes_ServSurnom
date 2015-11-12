package server;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {
	
	public static void main(String[] args) {
		System.out.println("debut du Server!");

		ServerSocket serverSocket = null;
		Boolean enEcoute = true;
		int port = 1234;
		
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			System.out.println("J'ai pas recu I/O sur le port: " + port);
			System.exit(-1);
		}
		
		while(enEcoute){
			try{
				// accepter une connection et faire un nouveau thread
				(new ServerThread(serverSocket.accept())).start();
			}catch(Exception e){
				// TODO doit on afficher l'exception ?
				System.out.println("Il y a eu un probleme majeur et inatendu: " + e);
				enEcoute = false;
			}
		}
		try {
			serverSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
