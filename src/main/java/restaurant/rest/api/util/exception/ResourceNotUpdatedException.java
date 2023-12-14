package restaurant.rest.api.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ResourceNotUpdatedException extends RuntimeException {
    public ResourceNotUpdatedException(String message) {
        super(message);
    }
}
