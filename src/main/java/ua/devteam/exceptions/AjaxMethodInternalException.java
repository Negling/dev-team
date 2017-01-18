package ua.devteam.exceptions;

/**
 * Mostly used when exception occurs in controllers annotated with {@link org.springframework.web.bind.annotation.RestController},
 * to aware {@link org.springframework.web.bind.annotation.ExceptionHandler} to generate response entity with localized message.
 */
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
