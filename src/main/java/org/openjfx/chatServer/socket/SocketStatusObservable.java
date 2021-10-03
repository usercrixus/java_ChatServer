package org.openjfx.chatServer.socket;

public interface SocketStatusObservable {
	public void subscribe(SocketStatusObserver observer);
	public void unsubscribe(SocketStatusObserver observer);
	public void notifyStatusObservers(int sigValue);
}
