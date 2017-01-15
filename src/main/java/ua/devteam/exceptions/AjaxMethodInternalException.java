package ua.devteam.exceptions;

public class AjaxMethodInternalException extends LocalizedException {

    public AjaxMethodInternalException(String message, Throwable cause, String localizedErrorCode, Object[] errorParams) {
        super(message, cause, localizedErrorCode, errorParams);
    }

    public AjaxMethodInternalException(Throwable cause, String localizedErrorCode, Object[] errorParams) {
        super(cause, localizedErrorCode, errorParams);
    }

    public AjaxMethodInternalException(String localizedErrorCode, Object[] errorParams) {
        super(localizedErrorCode, errorParams);
    }
}
