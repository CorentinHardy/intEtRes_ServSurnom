package server;

import Exception.MarshallingException;
import Exception.StringException;
import protocol.Action;
import protocol.Answer;
import protocol.Request;
import protocol.Result;

import java.io.IOException;
import java.net.DatagramPacket;
import java.util.ArrayList;
import java.util.List;

public class ServerThread extends Thread {
	private DatagramPacket paquetRecu;
	private GestionNomSurnom gns;



	public ServerThread(DatagramPacket paquetRecu, GestionNomSurnom gns) {
		super("ServerThread");
		this.paquetRecu = paquetRecu;
		this.gns = gns;
	}

	public void run() {
		byte[] buf = new byte[paquetRecu.getLength()];
		DatagramPacket yo;

		try {
			yo = new DatagramPacket(buf, buf.length);
			System.out.println("Thread start");

			Request request;
			Answer answer;

			try {
				do
					request = (Request) yo.readObject();
				while (request == null);
				answer = findAnswer(request);
			} catch (ClassNotFoundException e) {
				System.err.println("a ClassNotFoundException has appeared: ");
				e.printStackTrace();
				answer = new Answer(Result.EXCEPTION, null, new MarshallingException().toString());
			}

			System.out.println("we sent an answer");
//			os.writeObject(answer);

			paquetRecu.close();
			yo.close();
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
						+ request.getFirstValue() + " nickname: "
						+ request.getSecondValue());
				gns.addNickname(request.getFirstValue(), request.getSecondValue());
				List<String> al = new ArrayList<String>();
				al.add(request.getSecondValue());
				answer = new Answer(Result.OK, request.getAction(),
						request.getFirstValue(), al);
			}else if(Action.GET_NICKNAMES.equals(request.getAction())) {
				System.out.println("we search nickname for name: " + request.getFirstValue());
				List<String> al = gns.getNicknames(request.getFirstValue());
				answer = new Answer(Result.OK, request.getAction(), request.getFirstValue(), al);
			}else if(Action.GET_NAME.equals(request.getAction())) {
				System.out.println("we search name which have nickname: " + request.getFirstValue());
				List<String> al = new ArrayList<String>();
				al.add(gns.getName(request.getFirstValue()));
				answer = new Answer(Result.OK, request.getAction(), request.getFirstValue(), al);
			}else if (Action.REMOVE_NICKNAME.equals(request.getAction())){
				System.out.println("we remove nickname: " + request.getFirstValue());
				gns.removeNickname(request.getFirstValue());
				answer = new Answer(Result.OK, request.getAction(), request.getFirstValue());
			}else if (Action.REMOVE_NAME.equals(request.getAction())){
				System.out.println("we remove name and all his nicknames: " + request.getFirstValue());
				gns.removeName(request.getFirstValue());
				answer = new Answer(Result.OK, request.getAction(), request.getFirstValue());
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
