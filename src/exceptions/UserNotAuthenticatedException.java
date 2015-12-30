package exceptions;

public class UserNotAuthenticatedException extends Exception {

	private static final long serialVersionUID = 4314775136175737301L;

	public UserNotAuthenticatedException(String token) {
		super("You need to login or sign up first. Invalid token : \"" + token + "\"");
	}
}
