package server;

import Exception.MarshallingException;
import Exception.StringException;
import protocol.Action;
import protocol.Answer;
import protocol.Request;
import protocol.Result;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerThread extends Thread {
	private Socket socket;
	private GestionNomSurnom gns;


	public ServerThread(Socket socket, GestionNomSurnom gns) {
		super("ServerThread");
		this.socket = socket;
		this.gns = gns;
	}

	public void run() {
		ObjectOutputStream os;
		ObjectInputStream is;
		try {
			os = new ObjectOutputStream(socket.getOutputStream());
			is = new ObjectInputStream(socket.getInputStream());
			System.out.println("Je te vois !!!!");

			Request request;
			Answer answer;

			try {
				do
					request = (Request) is.readObject();
				while (request == null);
				answer = findAnswer(request);
			} catch (ClassNotFoundException e) {
				System.err.println("a ClassNotFoundException has appeared: " + e.getStackTrace());
				answer = new Answer(Result.EXCEPTION, null, new MarshallingException().toString());
			}

			os.writeObject(answer);

			socket.close();
			os.close();
			is.close();
		} catch (IOException e) {
			System.err.println("a IOException has appeared: " + e.getStackTrace());
		}
	}

	public Answer findAnswer(Request request){
		Answer answer;
		try {
			if(Action.ADD_NAME.equals(request.getAction())){
				System.out.println("on ajoute un nom: " + request.getFirstValue());
				gns.addName(request.getFirstValue());
				answer = new Answer(Result.OK, Action.ADD_NAME, request.getFirstValue());
			}else if(Action.ADD_NICKNAME.equals(request.getAction())) {
				System.out.println("on ajoute au nom "
						+ request.getSecondValue() + " le surnom: "
						+ request.getFirstValue());
				gns.addSurname(request.getFirstValue(), request.getFirstValue());
				List<String> al = new ArrayList<String>();
				al.add(request.getSecondValue());
				answer = new Answer(Result.OK, Action.ADD_NICKNAME,
						request.getFirstValue(), al);
		/*/
			}else if(Action.GET_NICKNAMES.equals(request.getAction())){
				List<String> al = gns.getSurnames(request.getFirstValue());
		//*/
			}else {
				System.out.println("on ne fait rien.");
				answer = new Answer(Result.EXCEPTION, null, new MarshallingException().toString());
			}
		} catch (StringException e) {
			answer = new Answer(Result.EXCEPTION, request.getAction(), e.toString());
		}
		return answer;
	}
}
