package exceptions;

public class TaskListDoesNotExistException extends Exception {

	private static final long serialVersionUID = -8381579901992561516L;

	public TaskListDoesNotExistException(String list) {
		super("The specified task list : \"" + list + "\" does not exist");
	}
}
