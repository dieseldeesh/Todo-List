package exceptions;

public class TaskListAlreadyExistsException extends Exception {

	private static final long serialVersionUID = -4669069009706870429L;

	public TaskListAlreadyExistsException(String list) {
		super("The task list : \"" + list + "\" already exists");
	}
}