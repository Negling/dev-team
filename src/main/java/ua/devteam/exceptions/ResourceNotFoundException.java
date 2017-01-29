package ua.devteam.exceptions;

import lombok.Getter;

/**
 * Used in case if GET {@link org.springframework.web.bind.annotation.RequestMethod} tries to get resource that no exist.
 */
public class ResourceNotFoundException extends RuntimeException {
    /*Requested resource URL. May be used on logging or error page.*/
    private @Getter String requestedURL;

    public ResourceNotFoundException(String message, String requestedURL) {
        super(message);
        this.requestedURL = requestedURL;
    }

    public ResourceNotFoundException(String requestedURL, Throwable cause) {
        super(cause);
        this.requestedURL = requestedURL;
    }
}
