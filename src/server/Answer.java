package server;


import java.io.Serializable;

import common.Result;
import common.Action;

public class Answer implements Serializable{
	Result answer;
	Action request;
	String field;
	String[] surnames;
	
	private Answer(Result answer, Action request){
		this.answer = answer;
		this.request = request;
	}
	public Answer(Result answer, Action request, String e){
		this(answer, request);
		field = e;
	}
	
	//	public Answer(reponse , aquoi, String)
	//	public Answer(reponse , aquoi, String, String)
}
