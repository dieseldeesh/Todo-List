package implementation;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import constants.RMIConstants;

public class TaskServer {
	public static void main(String[] args) throws RemoteException,
			AlreadyBoundException {
		LocateRegistry.createRegistry(RMIConstants.PORT).bind(RMIConstants.ID,
				new TaskListManager());
		System.out.println("Server has started!");
	}
}
