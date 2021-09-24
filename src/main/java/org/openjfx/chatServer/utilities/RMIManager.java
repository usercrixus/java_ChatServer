package org.openjfx.chatServer.utilities;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

public class RMIManager {
	
	public static RMIManager rmi;

	private RMIManager(int port) {
		try {
			LocateRegistry.createRegistry(port);
			if (System.getSecurityManager() == null) {
				System.setProperty("java.security.policy","file:./securityPolicy.policy");
				System.setSecurityManager(new SecurityManager());
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	public static void createRMIManager(int port) {
		if(rmi == null) rmi = new RMIManager(port);
	}
	
	public void add(String name, UnicastRemoteObject obj) {
		try {
		    String url = "rmi://" + InetAddress.getLocalHost().getHostAddress() + "/" + name;
		    Naming.rebind(url, obj);
		} catch (UnknownHostException | RemoteException | MalformedURLException e) {
			e.printStackTrace();
		}
	}
}
