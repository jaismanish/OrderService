package OrderService.exception;

public class UserNotRegisteredException extends IllegalArgumentException{
    public UserNotRegisteredException(String message) {
        super(message);
    }
}
