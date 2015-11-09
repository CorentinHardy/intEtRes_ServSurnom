package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import server.Answer;
import common.Action;

public class Client {

	public static void main(String[] args) {
		Socket socket = null;

		ObjectOutputStream os = null; // output stream
		ObjectInputStream is = null; // input stream
		
		try {
			socket = new Socket("Jean Jacques", 1234);
			Request r = new Request(Action.ADD_NAME, "Tortilla");
			
			os = new ObjectOutputStream(socket.getOutputStream());
			is = new ObjectInputStream(socket.getInputStream());
			
			Answer a = null;
			
			os.writeObject(r);
			while(a == null){
				a = (Answer) is.readObject();
			}
			
			System.out.println("tavu, on a l'answer !" /* + a.getResult()*/ );
			
			os.close();
			is.close();
			socket.close();
		} catch (Exception e) {
			System.err.println("Tu la sens mon Exception, gros !");
		}
	}
}
