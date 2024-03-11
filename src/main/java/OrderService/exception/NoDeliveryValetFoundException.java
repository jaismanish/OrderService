package OrderService.exception;

import org.springframework.data.crossstore.ChangeSetPersister;

public class NoDeliveryValetFoundException extends Exception {
    public NoDeliveryValetFoundException(String message) {
        super(message);
    }
}
