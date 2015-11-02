package client;

import java.io.Serializable;

import common.Action;

public class Request implements Serializable{
	private Action action;
	private String name;
	private String nickname;
	
	public Request(Action action, String name){
		this.action = action;
		this.name = name;
	}
	
	public Request(Action action, String name, String nickname){
		this.action = action;
		this.name = name;
		this.nickname = nickname;
	}

	public Action getAction() {
		return action;
	}
	
	public String getName() {
		return name;
	}
	
	public String getNickname() {
		return nickname;
	}
}
