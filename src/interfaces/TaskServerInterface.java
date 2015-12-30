package interfaces;

import java.nio.file.AccessDeniedException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

import exceptions.TaskDoesNotExistException;
import exceptions.TaskListAlreadyExistsException;
import exceptions.TaskListDoesNotExistException;
import exceptions.UserNotAuthenticatedException;

public interface TaskServerInterface extends Remote {
	public boolean addTask(String userToken, String taskToken, String task)
			throws UserNotAuthenticatedException,
			TaskListDoesNotExistException, AccessDeniedException,
			RemoteException;

	public boolean removeTask(String userToken, String taskToken, String task)
			throws UserNotAuthenticatedException, TaskDoesNotExistException,
			TaskListDoesNotExistException, AccessDeniedException,
			RemoteException;

	public boolean checkOffTask(String userToken, String taskToken, String task)
			throws UserNotAuthenticatedException, TaskDoesNotExistException,
			TaskListDoesNotExistException, AccessDeniedException,
			RemoteException;

	public List<String> getTaskLists(String userToken)
			throws UserNotAuthenticatedException, RemoteException;

	public Map<String, Boolean> getTasks(String userToken, String taskToken)
			throws UserNotAuthenticatedException,
			TaskListDoesNotExistException, AccessDeniedException,
			RemoteException;

	public boolean createList(String userToken, String listName)
			throws UserNotAuthenticatedException,
			TaskListAlreadyExistsException, AccessDeniedException,
			RemoteException;
}