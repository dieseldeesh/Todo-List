package exceptions;

public class UserDoesNotExistException extends Exception {

	private static final long serialVersionUID = -6368849754665328060L;

	public UserDoesNotExistException(String username) {
		super("The user: \"" + username + "\" does not exist");
	}
}
