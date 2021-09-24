package org.openjfx.chatServer.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

import org.openjfx.chatServer.rmi.RMIEndPoint;
import org.openjfx.chatServer.utilities.RMIManager;

import javafx.application.Platform;

public class SimpleTextSocket {
	
	ArrayList<clientData> clientList = new ArrayList<clientData>();
	ServerSocket socketserver;
	SocketObserver uiSocket;
	int port;
	
	
	public SimpleTextSocket(String port, SocketObserver uiSocket) {
		try {
			this.port = Integer.parseInt(port);
			if(this.port < 1024 || this.port > 65536 ) {
				throw new NumberFormatException();
			}
		} catch (NumberFormatException e) {
			this.port = 50000; // default
		}
		this.uiSocket = uiSocket;
	}
	/**
	 * On connect button clicked, launch the server. If selected port isnt a valid number port, port will start on port 50000
	 */
	public void connect() {

		// Start server on the defined port
		try {
			socketserver = new ServerSocket(port); //create the server
			uiSocket.updateSocketStatus(1);
			
			try {
				RMIEndPoint endPoint = new RMIEndPoint(this);
				RMIManager.rmi.add("RMIEndPoint", endPoint);
			} catch (Exception e) {
				e.printStackTrace();
			}



			Thread getClientConnection = new Thread(()->{ // launch a thread to get new connection
				try {
					getClientConnection(socketserver); // handles connection asks
				} catch (IOException e) {
					if(socketserver.isClosed()) {
						close();
						/*
						 *  This code will be called at the end of this thread
						 *  We can't use join, it freezes the javafx thread, all button, field etc, in fact, it would crash the app
						 *  You must never block top javafx thread, not with join, not even with futur task or similar stuff
						 */
						Platform.runLater(()-> {
							uiSocket.updateSocketStatus(2);	
						});
					}else {
						e.printStackTrace(); // unexpected error...			
					}
				}
			});
			getClientConnection.start();
		} catch (Exception e) {
			uiSocket.updateSocketStatus(3); // unexpected error during the creation of serverSocket object
		}
	}

	/**
	 * Thread loop to get client server connection
	 * @param socketserver
	 * @throws IOException 
	 */
	public void getClientConnection(ServerSocket socketserver) throws IOException {
		while (!socketserver.isClosed()) {
			Socket remoteClient = socketserver.accept(); // a new client asking for connection
			try {
				handleClientConnection(remoteClient); // handles the new client
			} catch (Exception e) {
				if(!remoteClient.isClosed()) remoteClient.close();
				System.err.println("Unexpected error an client socket");
			}
		}

	}

	/**
	 * Save client data and launch thread to read data from this client
	 * @param socket client
	 * @throws IOException
	 */
	public void handleClientConnection(Socket client) throws IOException{
		clientList.add( new clientData(client, new PrintWriter(client.getOutputStream()), new BufferedReader(new InputStreamReader (client.getInputStream())) ) );

		Thread receiveClientData = new Thread( ()->{
			receiveClientData(clientList.get(clientList.size()-1));
		});
		receiveClientData.start();
	}

	/**
	 * read client msg
	 * @param object representing client
	 * @throws IOException
	 */
	public void receiveClientData(clientData client) {
		try {
			String msg = client.getIn().readLine();
			client.setPseudo(msg);
			msg = client.getSocket().getInetAddress().toString().substring(1) + " has connected as " + client.getPseudo();
			sendToAll(msg);
			
			msg = client.getIn().readLine();
			while(msg!=null && !socketserver.isClosed() && !client.getSocket().isClosed()) {
				sendToAll(client.getPseudo() + " : " + msg);
				msg = client.getIn().readLine();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		disconnectClient(client);
	}

	/**
	 * close client socket and discard object representing client
	 * @param object representing client
	 * @throws IOException
	 */
	public void disconnectClient(clientData client) {
		for (Iterator<clientData> iterator = clientList.iterator(); iterator.hasNext();) {
			clientData clientData = (clientData) iterator.next();
			if (clientData == client) {
				if(!clientData.getSocket().isClosed())
					try {
						clientData.getSocket().close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				sendToAll(client.getSocket().getInetAddress().toString().substring(1) + " " + client.getPseudo() + " has disconnected");
				iterator.remove();
				break;
			}
		}
	}

	/**
	 * send msg to all clients
	 * @param msg
	 */
	public void sendToAll(String msg) {
		try {
			Iterator<clientData> iterator = clientList.iterator();
			while(iterator.hasNext()) {
				clientData clientData = (clientData) iterator.next();
				clientData.getOut().println(msg);
				clientData.getOut().flush();
			}			
		} catch (Exception e) {
			System.err.println("Msg : "+msg+" could not be send to everyone");
		}
	}

	/**
	 * close the server
	 */
	public void close() {
		try {
			Iterator<clientData> iterator = clientList.iterator();
			while(iterator.hasNext()) {
				clientData clientData = (clientData) iterator.next();
				clientData.getSocket().close();
			}	
			if(!socketserver.isClosed()) socketserver.close();
			clientList.clear();
			Platform.runLater(()-> {
				uiSocket.updateSocketStatus(2);		
			});

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//--------------Getter-Setter--------------
	
	public ArrayList<clientData> getClientList() {
		return clientList;
	}
	public void setClientList(ArrayList<clientData> clientList) {
		this.clientList = clientList;
	}
	
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	
	
}
