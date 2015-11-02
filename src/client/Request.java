package client;

import java.io.Serializable;

import common.Protocol;

public class Request implements Serializable{
	private Protocol protocol;
	private String name;
	private String nickname;
	
	public Request(Protocol protocol, String name){
		this.protocol = protocol;
		this.name = name;
	}
	
	public Request(Protocol protocol, String name, String nickname){
		this.protocol = protocol;
		this.name = name;
		this.nickname = nickname;
	}

	public Protocol getProtocol() {
		return protocol;
	}
	
	public String getName() {
		return name;
	}
	
	public String getNickname() {
		return nickname;
	}
}
