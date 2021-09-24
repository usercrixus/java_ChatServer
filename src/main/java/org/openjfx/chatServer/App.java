package org.openjfx.chatServer;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

import org.openjfx.chatServer.server.ConnectionControler;
import org.openjfx.chatServer.utilities.Booter;


/**
 * JavaFX App
 */
public class App extends Application {
	
	public static BorderPane root;
	public static Booter booter;
	
    @Override
    public void start(Stage primaryStage) throws IOException {
		try {
			
			root = new BorderPane();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			booter = new Booter();
			ConnectionControler controller = booter.createConnection();

			primaryStage.setScene(scene);
			primaryStage.setOnHidden(e->{
				controller.close();
				Platform.exit();
			});
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
    }

    public static void main(String[] args) {
        launch();
    }

}