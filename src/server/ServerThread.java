package server;

import java.net.Socket;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

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
			
/*/
			Request request;
			Answer answer;
			
			while(request == null)
				request = (Request) is.readObject();
			
			switch(request.getAction()){
			case Action.ADD_NAME:
				System.out.println("on ajoute un nom: " + request.getFirstValue());
				if(toutVasBien)
					answer = new Answer(Result.OK, Action.ADD_NAME, request.getFirstValue());
				else
					answer = new Answer(Result.EXCEPTION, Action.ADD_NAME, 
							new NameAlreadyExistException(request.getFirstValue()).toSring());
				break;
			case Action.ADD_NICKNAME:
				System.out.println("on ajoute au nom " 
						+ request.getSecondValue() +" le surnom: " 
						+ request.getFirstValue());
				if(toutVasBien)
					answer = new Answer(Result.OK, Action.ADD_NICKNAME, 
							request.getFirstValue(), request.getSecondValue());
				else
					answer = new Answer(Result.EXCEPTION, Action.ADD_NICKNAME, 
							new NicknameAlreadyExistException(request.getSecondValue()).toString());
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
