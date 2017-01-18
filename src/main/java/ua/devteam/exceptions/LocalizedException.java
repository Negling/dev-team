package ua.devteam.exceptions;

/**
 * Exception with error code from l10n properties, which can be actually used in response to user to explain what happened.
 */
public class LocalizedException extends RuntimeException {
    /*Error code from l10n properties file like "errorPage.noAccess", etc.*/
    private String localizedErrorCode;
    /*Array of params which can be bound to errorCode in MessageSource*/
    private Object[] errorParams;

    public LocalizedException(String message, Throwable cause, String localizedErrorCode, Object[] errorParams) {
        super(message, cause);
        this.localizedErrorCode = localizedErrorCode;
        this.errorParams = errorParams;
    }

    public LocalizedException(Throwable cause, String localizedErrorCode, Object[] errorParams) {
        super(cause);
        this.localizedErrorCode = localizedErrorCode;
        this.errorParams = errorParams;
    }

    public LocalizedException(String localizedErrorCode, Object[] errorParams) {
        this.localizedErrorCode = localizedErrorCode;
        this.errorParams = errorParams;
    }

    public String getLocalizedErrorCode() {
        return localizedErrorCode;
    }

    public Object[] getErrorParams() {
        return errorParams;
    }
}
