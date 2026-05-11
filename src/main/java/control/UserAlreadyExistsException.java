package control;

public class UserAlreadyExistsException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public UserAlreadyExistsException() {
		super("The user is already registered.");
	}
	
	public UserAlreadyExistsException(String content) {
		super(content);
	}
}
