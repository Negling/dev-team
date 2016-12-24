package ua.devteam.validation;


import org.springframework.validation.Errors;

public abstract class ValidationUtils extends org.springframework.validation.ValidationUtils {

    public static String formatStringOverflow(String value) {
        if (value == null) {
            return null;
        } else if (value.length() < 15) {
            return value;
        } else {
            return value.substring(0, 15).concat("...");
        }
    }

    public static void checkStringMaxLength(String data, int maxValue, Errors errors, String errorCode, Object[] params) {
        if (data != null && !data.isEmpty() && data.length() > maxValue) {
            errors.reject(errorCode, params, null);
        }
    }

    public static void checkStringMaxLength(String field, String data, int maxValue, Errors errors, String errorCode,
                                            Object[] params) {
        if (data != null && !errors.hasFieldErrors(field) && data.length() > maxValue) {
            errors.rejectValue(field, errorCode, params, null);
        }
    }

    public static void checkStringMinLength(String data, int minValue, Errors errors, String errorCode, Object[] params) {
        if (data != null && !data.isEmpty() && data.length() < minValue) {
            errors.reject(errorCode, params, null);
        }
    }

    public static void checkStringMinLength(String field, String data, int minValue, Errors errors, String errorCode,
                                            Object[] params) {
        if (data != null && !errors.hasFieldErrors(field) && data.length() < minValue) {
            errors.rejectValue(field, errorCode, params, null);
        }
    }

    public static void checkStringMatchPattern(String data, String regex, Errors errors, String errorCode, Object[] params) {
        if (data != null && !data.isEmpty() && !data.matches(regex)) {
            errors.reject(errorCode, params, null);
        }
    }

    public static void checkStringMatchPattern(String field, String data, String regex, Errors errors, String errorCode,
                                               Object[] params) {
        if (data != null && !errors.hasFieldErrors(field) && !data.matches(regex)) {
            errors.rejectValue(field, errorCode, params, null);
        }
    }

    public static void checkIfEquals(String field, String firstValue, String secondValue, Errors errors, String errorCode,
                                     Object[] params) {
        if (firstValue != null && !firstValue.equals(secondValue)) {
            errors.rejectValue(field, errorCode, params, null);
        }
    }


}
