package org.openjfx.chatServer.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import org.openjfx.chat.remote.RemoteOptions;
import org.openjfx.chat.remote.clientDataRemote;
import org.openjfx.chatServer.socket.SimpleTextSocket;
import org.openjfx.chatServer.socket.ClientData;

public class RMIEndPoint extends UnicastRemoteObject implements RemoteOptions {

	private static final long serialVersionUID = 1L;

	SimpleTextSocket serverSocket;

	public RMIEndPoint(SimpleTextSocket serverSocket) throws RemoteException {
		super();
		this.serverSocket = serverSocket;
	}

	@Override
	public ArrayList<clientDataRemote> getClient() throws RemoteException {
		ArrayList<clientDataRemote> clientDataRemotes = new ArrayList<>();

		for(ClientData ClientData : serverSocket.getClientList()) {
			clientDataRemotes.add(new clientDataRemote(ClientData.getPseudo(), ClientData.getSocket().getInetAddress().toString()));
		}

		return clientDataRemotes;
	}
}
