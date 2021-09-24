package org.openjfx.chatServer.socket;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.Socket;

public class clientData implements Serializable {

	private static final long serialVersionUID = 1L;
	Socket socket;
	PrintWriter out;
	BufferedReader in;
	String pseudo;
	
	public clientData(Socket socket, PrintWriter out, BufferedReader in) {
		this.socket = socket;
		this.out = out;
		this.in = in;
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudoString) {
		this.pseudo = pseudoString;
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}


	public PrintWriter getOut() {
		return out;
	}

	public void setOut(PrintWriter out) {
		this.out = out;
	}

	public BufferedReader getIn() {
		return in;
	}

	public void setIn(BufferedReader in) {
		this.in = in;
	}
}