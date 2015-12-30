package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

import exceptions.UserAlreadyExistsException;
import exceptions.UserAuthenticationFailedException;
import exceptions.UserDoesNotExistException;

public interface AuthenticationInterface extends Remote {
	public String signUp(String username, String password)
			throws UserAlreadyExistsException, RemoteException;

	public String login(String username, String password)
			throws UserDoesNotExistException,
			UserAuthenticationFailedException, RemoteException;

	public boolean logout(String username, String token) throws RemoteException;
}
