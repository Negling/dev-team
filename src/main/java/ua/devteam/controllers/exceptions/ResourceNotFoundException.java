package ua.devteam.controllers.exceptions;


public class ResourceNotFoundException extends RuntimeException {
    private String requestedURL;

    public ResourceNotFoundException(String message, String requestedURL) {
        super(message);
        this.requestedURL = requestedURL;
    }

    public ResourceNotFoundException(String requestedURL) {
        this.requestedURL = requestedURL;
    }

    public String getRequestedURL() {
        return requestedURL;
    }
}
