package org.openjfx.chatServer.server;

import org.openjfx.chatServer.socket.SimpleTextSocket;
import org.openjfx.chatServer.socket.SocketStatusObserver;
import org.openjfx.chatServer.utilities.RMIManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


public class ConnectionControler implements SocketStatusObserver {

	SimpleTextSocket simpleTextSocket;

	@FXML
	private Label status;
	@FXML
	private TextField portField;
	@FXML
	private Button connect;
	@FXML
	private Button disconnect;


	@FXML
	public void initialize() {
		updateSocketStatus(0);
		RMIManager.createRMIManager(1099);
	}

	/**
	 * On connect button clicked, launch the server.
	 */
	@FXML
	public void connect() {
		simpleTextSocket = new SimpleTextSocket(portField.getText());
		simpleTextSocket.subscribe(this);
		simpleTextSocket.connect();
	}
	
	/**
	 * On disconnect button clicked, close the server
	 */
	@FXML
	public void close() {
		if(simpleTextSocket != null) simpleTextSocket.close();
	}

	/**
	 * Get information on server status
	 * 
	 * null socket = 0
	 * connected = 1
	 * disconnected = 2
	 * inexpected error = 3
	 */
	@Override
	public void updateSocketStatus(int sigValue) {
		if(sigValue == 0) {
			connect.setDisable(false);
			disconnect.setDisable(true);
		}else if(sigValue == 1) {
			connect.setDisable(true);
			disconnect.setDisable(false);
			status.setText("Active server on port "+simpleTextSocket.getPort()+"."); // set the view
		}else if (sigValue == 2) {
			connect.setDisable(false);
			disconnect.setDisable(true);
			status.setText("Waiting to start your server");
		}else if (sigValue == 3) {
			connect.setDisable(false);
			disconnect.setDisable(true);
			status.setText("An error has occured. Server can't start."); // set the view
		}
	}

}
