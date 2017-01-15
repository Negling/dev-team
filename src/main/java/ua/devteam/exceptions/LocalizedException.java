package ua.devteam.exceptions;


public class LocalizedException extends RuntimeException {
    private String localizedErrorCode;
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
