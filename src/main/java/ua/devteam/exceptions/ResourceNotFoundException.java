package ua.devteam.exceptions;


public class ResourceNotFoundException extends RuntimeException {
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
