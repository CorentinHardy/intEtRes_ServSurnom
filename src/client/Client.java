package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

	public static void main(String[] args) {
		Socket socket = null;

		ObjectOutputStream os = null; // output stream
		ObjectInputStream is = null; // input stream
		try {
			socket = new Socket("hostname", 1234);
			os = new ObjectOutputStream(socket.getOutputStream());
			is = new ObjectInputStream(socket.getInputStream());
		} catch (UnknownHostException e) {
			System.err.println("on ne connais pas l'hôte: hostname");
		} catch (IOException e) {
			System.err.println("J'ai pas pus recevoir I/O pour la connection à: hostname");
		}
		
		if (socket != null && os != null && is != null) {
			try{
				os.writeBytes("Holla");
				// attente de "Ok" du serveur SMTP,
				String responseLine;
				while ((responseLine = is.readLine()) != null) {
					System.out.println("Server: " + responseLine);
					if (responseLine.indexOf("Ok") != -1) {break;}
				}
				os.close();
				is.close();
				socket.close(); 
			} catch (UnknownHostException e) {
				System.err.println("t'essaye de te connecter à l'hôte pas connu: " + e);
			} catch (IOException e){
				System.err.println("IOException: " + e);
			}
		}
	}
}
