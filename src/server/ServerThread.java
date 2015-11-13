package server;

import java.net.Socket;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ServerThread extends Thread {
	private Socket socket;
	private GestionNomSurnom gns;
	
	
	public ServerThread(Socket socket, GestionNomSurnom gns) {
		super("ServerThread");
		this.socket = socket;
		this.gns = gns;
	}
	
	public void run(){
		ObjectOutputStream os;
		ObjectInputStream is;
		try {
			os = new ObjectOutputStream(socket.getOutputStream());
			is = new ObjectInputStream(socket.getInputStream());
			System.out.println("Je te vois !!!!");
			
/*/
			Request request;
			Answer answer;
			
			while(request == null)
				request = (Request) is.readObject();
			
			switch(request.getAction()){
			case Action.ADD_NAME:
				System.out.println("on ajoute un nom: " + request.getFirstValue());
				try{
					gns.addName(request.getFirstValue());
					answer = new Answer(Result.OK, Action.ADD_NAME, request.getFirstValue());
				}catch(StringException e){
					answer = new Answer(Result.EXCEPTION, Action.ADD_NAME, e.toSring());
				}
				break;
			case Action.ADD_NICKNAME:
				System.out.println("on ajoute au nom " 
						+ request.getSecondValue() +" le surnom: " 
						+ request.getFirstValue());
				
				try{
					gns.addSurname(request.getFirstValue(), request.getFirstValue());
					answer = new Answer(Result.OK, Action.ADD_NICKNAME, 
							request.getFirstValue(), request.getSecondValue());
				}catch(StringException e){
					answer = new Answer(Result.EXCEPTION, Action.ADD_NICKNAME, 
							e.toString());
				}
				break;
			default:
				System.out.println("on ne fait rien.");
				answer = new Answer(Result.EXCEPTION, null, new MarshallingException().toString());
			}
			
			os.writeObject(answer);
//*/			
			socket.close();
			os.close();
			is.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
