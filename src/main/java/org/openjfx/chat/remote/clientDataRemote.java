package org.openjfx.chat.remote;

import java.io.Serializable;

public class clientDataRemote implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String pseudo;
	private String ip;
	
	public clientDataRemote(String pseudo, String ip) {
		this.pseudo = pseudo;
		this.ip = ip;
	}
	
	public String getPseudo() {
		return pseudo;
	}
	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
}
