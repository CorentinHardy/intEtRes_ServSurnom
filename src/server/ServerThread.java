package server;

import java.net.Socket;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

//import ServSurnom_classes.*;

public class ServerThread extends Thread {
	private Socket socket;
	
	
	public ServerThread(Socket socket) {
		super("ServerThread");
		this.socket = socket;
	}
	
	public void run(){
		ObjectOutputStream os;
		ObjectInputStream is;
		try {
			os = new ObjectOutputStream(socket.getOutputStream());
			is = new ObjectInputStream(socket.getInputStream());
			System.out.println("Je te vois !!!!");
			
//			Request request;
//
//			while(request == null)
//				request = (Request) is.readObject();
//
//			Answer answer;
			//answer = action(); // en gros
			
//			os.writeObject(answer);
			
			socket.close();
			os.close();
			is.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
