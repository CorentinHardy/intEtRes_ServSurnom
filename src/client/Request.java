package client;

import common.Protocol;

public class Request {
	private Protocol protocol;
	private String name;
	
	public Request(Protocol protocol, String name){
		this.protocol = protocol;
		this.name = name;
	}
}
