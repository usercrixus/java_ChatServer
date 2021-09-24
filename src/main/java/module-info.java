module org.openjfx.chatServer {
	
	requires javafx.base;
	requires transitive javafx.graphics;
	requires transitive javafx.fxml;
	requires javafx.controls;
	requires java.xml;
	requires transitive java.rmi;

    opens org.openjfx.chatServer to javafx.fxml;
    opens org.openjfx.chatServer.server;
    
    exports org.openjfx.chatServer;
    exports org.openjfx.chatServer.rmi;
    exports org.openjfx.chatServer.socket;
    exports org.openjfx.chat.remote;
    exports org.openjfx.chatServer.server;
    exports org.openjfx.chatServer.utilities;
}
