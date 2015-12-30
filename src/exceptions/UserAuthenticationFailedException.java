package exceptions;

public class UserAuthenticationFailedException extends Exception {

	private static final long serialVersionUID = -3935308438735441550L;

	public UserAuthenticationFailedException() {
		super("The username and password combination you entered was invalid");
	}
}
