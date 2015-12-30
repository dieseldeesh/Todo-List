package exceptions;

public class TaskDoesNotExistException extends Exception {

	private static final long serialVersionUID = 3697239953561860863L;

	public TaskDoesNotExistException(String task, String list) {
		super("The task: \"" + task + "\" does not exist in the list : \""
				+ list + "\"");
	}
}
