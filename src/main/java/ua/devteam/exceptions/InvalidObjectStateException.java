package ua.devteam.exceptions;

/**
 * Used mostly at service tier, when object expected state or status is not match to received.
 */
public class InvalidObjectStateException extends LocalizedException {
    public InvalidObjectStateException(String message, Throwable cause, String localizedErrorCode, Object[] errorParams) {
        super(message, cause, localizedErrorCode, errorParams);
    }

    public InvalidObjectStateException(Throwable cause, String localizedErrorCode, Object[] errorParams) {
        super(cause, localizedErrorCode, errorParams);
    }

    public InvalidObjectStateException(String localizedErrorCode, Object[] errorParams) {
        super(localizedErrorCode, errorParams);
    }
}
