package ua.devteam.exceptions;

/**
 * Used in case if GET {@link org.springframework.web.bind.annotation.RequestMethod} tries to get resource that no exist.
 */
public class ResourceNotFoundException extends RuntimeException {
    /*Requested resource URL. May be used on logging or error page.*/
    private String requestedURL;

    public ResourceNotFoundException(String message, String requestedURL) {
        super(message);
        this.requestedURL = requestedURL;
    }

    public ResourceNotFoundException(String requestedURL, Throwable cause) {
        super(cause);
        this.requestedURL = requestedURL;
    }

    public String getRequestedURL() {
        return requestedURL;
    }
}
