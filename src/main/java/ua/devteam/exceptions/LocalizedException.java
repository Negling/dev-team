package ua.devteam.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Exception with error code from l10n properties, which can be actually used in response to user to explain what happened.
 */
@AllArgsConstructor
public class LocalizedException extends RuntimeException {
    /*Error code from l10n properties file like "errorPage.noAccess", etc.*/
    private @Getter String localizedErrorCode;
    /*Array of params which can be bound to errorCode in MessageSource*/
    private @Getter Object[] errorParams;

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
}
