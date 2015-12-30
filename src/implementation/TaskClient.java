package implementation;

import interfaces.AuthenticationInterface;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import constants.RMIConstants;
import exceptions.UserAlreadyExistsException;

public class TaskClient {

	public static final String username = "adhish";
	public static final String password = "ramkumar";

	public static void main(String[] args) throws RemoteException,
			NotBoundException {
		Registry registry = LocateRegistry.getRegistry(RMIConstants.HOST,
				RMIConstants.PORT);
		AuthenticationInterface taskServer = (AuthenticationInterface) registry
				.lookup(RMIConstants.ID);
		try {
			System.out.println(taskServer.login(username, password));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		try {
			System.out.println(taskServer.signUp(username, password));
		} catch (UserAlreadyExistsException e) {
			System.out.println(e.getMessage());
		}
		try {
			System.out.println(taskServer.login(username, password));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
