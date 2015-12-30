package implementation;

import interfaces.AuthenticationInterface;
import interfaces.TaskServerInterface;

import java.nio.file.AccessDeniedException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import exceptions.TaskDoesNotExistException;
import exceptions.TaskListAlreadyExistsException;
import exceptions.TaskListDoesNotExistException;
import exceptions.UserAlreadyExistsException;
import exceptions.UserAuthenticationFailedException;
import exceptions.UserDoesNotExistException;
import exceptions.UserNotAuthenticatedException;

public class TaskListManager extends UnicastRemoteObject implements
		TaskServerInterface, AuthenticationInterface {

	private static final long serialVersionUID = -7422064019015434667L;
	private ConcurrentMap<String, Integer> users;
	private ConcurrentMap<String, String> cookies;
	private ConcurrentMap<String, TaskList> taskLists;

	protected TaskListManager() throws RemoteException {
		super();
		this.cookies = new ConcurrentHashMap<String, String>();
		this.users = new ConcurrentHashMap<String, Integer>();
		this.taskLists = new ConcurrentHashMap<String, TaskList>();
	}

	@Override
	public String signUp(String username, String password)
			throws UserAlreadyExistsException {
		if (this.users.containsKey(username)) {
			throw new UserAlreadyExistsException(username);
		} else {
			this.users.put(username, password.hashCode());
			String cookie = UUID.randomUUID().toString();
			this.cookies.put(cookie, username);
			return cookie;
		}
	}

	@Override
	public String login(String username, String password)
			throws RemoteException, UserDoesNotExistException,
			UserAuthenticationFailedException {
		if (this.users.containsKey(username)) {
			if (this.users.get(username).equals(password.hashCode())) {
				String cookie = UUID.randomUUID().toString();
				this.cookies.put(cookie, username);
				return cookie;
			} else {
				throw new UserAuthenticationFailedException();
			}
		} else {
			throw new UserDoesNotExistException(username);
		}
	}

	@Override
	public boolean logout(String username, String token) throws RemoteException {
		if (this.cookies.containsValue(username)) {
			String key = null;
			for (Entry<String, String> entry : this.cookies.entrySet()) {
				if (entry.getValue() == username) {
					key = entry.getKey();
					break;
				}
			}
			if (key == null) {
				return false;
			} else {
				this.cookies.remove(key);
				return true;
			}
		} else {
			return true;
		}
	}

	@Override
	public boolean addTask(String userToken, String taskToken, String task)
			throws UserNotAuthenticatedException,
			TaskListDoesNotExistException, AccessDeniedException,
			RemoteException {
		return validateTaskToken(userToken, taskToken).addTask(task);
	}

	@Override
	public boolean removeTask(String userToken, String taskToken, String task)
			throws UserNotAuthenticatedException, TaskDoesNotExistException,
			TaskListDoesNotExistException, AccessDeniedException,
			RemoteException {
		if (validateTaskToken(userToken, taskToken).removeTask(task)) {
			return true;
		}
		throw new TaskDoesNotExistException(task, taskToken);
	}

	@Override
	public boolean checkOffTask(String userToken, String taskToken, String task)
			throws UserNotAuthenticatedException, TaskDoesNotExistException,
			TaskListDoesNotExistException, AccessDeniedException,
			RemoteException {
		if (validateTaskToken(userToken, taskToken).checkTask(task)) {
			return true;
		}
		throw new TaskDoesNotExistException(task, taskToken);
	}

	@Override
	public List<String> getTaskLists(String userToken)
			throws UserNotAuthenticatedException {
		String username = validateUser(userToken);
		List<String> lists = new ArrayList<String>();
		for (Entry<String, TaskList> entry : this.taskLists.entrySet()) {
			if (entry.getValue().hasPermission(username)) {
				lists.add(entry.getKey());
			}
		}
		return lists;
	}

	@Override
	public Map<String, Boolean> getTasks(String userToken, String taskToken)
			throws UserNotAuthenticatedException,
			TaskListDoesNotExistException, AccessDeniedException,
			RemoteException {
		return validateTaskToken(userToken, taskToken).getTasks();
	}

	@Override
	public boolean createList(String userToken, String listName)
			throws TaskListAlreadyExistsException, RemoteException,
			UserNotAuthenticatedException {
		String username = validateUser(userToken);
		if (this.taskLists.containsKey(listName)) {
			throw new TaskListAlreadyExistsException(listName);
		} else {
			TaskList taskList = new TaskList(username);
			this.taskLists.put(listName, taskList);
			return true;
		}
	}

	private synchronized String validateUser(String userToken)
			throws UserNotAuthenticatedException {
		if (!this.cookies.containsKey(userToken)) {
			throw new UserNotAuthenticatedException(userToken);
		} else {
			return this.cookies.get(userToken);
		}
	}

	private synchronized TaskList validateTaskToken(String userToken,
			String taskToken) throws UserNotAuthenticatedException,
			TaskListDoesNotExistException, AccessDeniedException {
		String username = validateUser(userToken);
		if (!this.taskLists.containsKey(taskToken)) {
			throw new TaskListDoesNotExistException(taskToken);
		}
		TaskList taskList = this.taskLists.get(taskToken);
		if (!taskList.hasPermission(username)) {
			throw new AccessDeniedException(taskToken);
		}
		return taskList;
	}
}
