package org.openjfx.chatServer.utilities;

import java.io.IOException;
import org.openjfx.chatServer.App;
import org.openjfx.chatServer.server.ConnectionControler;

import javafx.fxml.FXMLLoader;

public class Booter {
	
	private FXMLLoader loader;
	public static Booter booter;
	
	public Booter() throws IOException { }
	
	public ConnectionControler createConnection() throws IOException {
		loader = loadFXML("Connection");
		App.root.setCenter(loader.load());
		
		return loader.getController();
	}
	
	private FXMLLoader loadFXML(String fxml) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
		return fxmlLoader;
	}
}
