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
			System.out.println("Thread start");

			Request request;
			Answer answer;

			try {
				do
					request = (Request) is.readObject();
				while (request == null);
				answer = findAnswer(request);
			} catch (ClassNotFoundException e) {
				System.err.println("a ClassNotFoundException has appeared: ");
				e.printStackTrace();
				answer = new Answer(Result.EXCEPTION, null, new MarshallingException().toString());
			}

			System.out.println("we sent an answer");
			os.writeObject(answer);

			socket.close();
			os.close();
			is.close();
			System.out.println("Thread's end");
		} catch (IOException e) {
			System.err.println("a IOException has appeared: ");
			e.printStackTrace();
		}
	}

	public Answer findAnswer(Request request){
		Answer answer;
		try {
			if(Action.ADD_NAME.equals(request.getAction())){
				System.out.println("we add name: " + request.getFirstValue());
				gns.addName(request.getFirstValue());
				answer = new Answer(Result.OK, request.getAction(), request.getFirstValue());
			}else if(Action.ADD_NICKNAME.equals(request.getAction())) {
				System.out.println("we add to name "
						+ request.getFirstValue() + " surname: "
						+ request.getSecondValue());
				gns.addSurname(request.getFirstValue(), request.getSecondValue());
				List<String> al = new ArrayList<String>();
				al.add(request.getSecondValue());
				answer = new Answer(Result.OK, request.getAction(),
						request.getFirstValue(), al);
			}else if(Action.GET_NICKNAMES.equals(request.getAction())) {
				System.out.println("we search surname for name: " + request.getFirstValue());
				List<String> al = gns.getSurnames(request.getFirstValue());
				answer = new Answer(Result.OK, request.getAction(), request.getFirstValue(), al);
			}else if(Action.GET_NAME.equals(request.getAction())) {
				List<String> al = new ArrayList<String>();
				al.add(gns.getName(request.getFirstValue()));
				answer = new Answer(Result.OK, request.getAction(), request.getFirstValue(), al);
			}else {
				System.err.println("Request is unknown. we send a MarshallingException to the Client");
				answer = new Answer(Result.EXCEPTION, null, new MarshallingException().toString());
			}
		} catch (StringException e) {
			System.out.println("Answer Exception sent: " + e.toString());
			answer = new Answer(Result.EXCEPTION, request.getAction(), e.toString());
		}
		return answer;
	}
}
