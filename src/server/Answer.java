package server;


import java.io.Serializable;

public class Answer implements Serializable{
	Protocol answer, request;
	String name;
	String[] surnames;
	
	private Answer(Protocal answer, Protocol request){
		this.answer = answer;
		this.request = request;
	}
	public Answer(Protocol answer, Protocol request, Exception e){
		Answer(answer, request);
	}
//	public Answer(reponse , aquoi, String)
//	public Answer(reponse , aquoi, String, String)
}
