package org.openjfx.chat.remote;

import java.rmi.*;
import java.util.ArrayList;

public interface RemoteOptions extends Remote {
	
	public ArrayList<clientDataRemote> getClient() throws RemoteException;
	
}
