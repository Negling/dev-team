package ua.devteam.validation;


import org.springframework.validation.Errors;

import java.util.Objects;

/**
 * That is an extension of {@link org.springframework.validation.ValidationUtils} class with some custom
 * validation methods necessary to current entity area.
 */
public abstract class ValidationUtils extends org.springframework.validation.ValidationUtils {

    /**
     * Prepares string to validation errors display, with cutting it of to custom length.
     *
     * @param value     string to be cut
     * @param maxLength max length of result string.
     * @return cut string with 3 dots at end, or original value if string length is less than length param or less or equal to 3.
     */
    public static String formatStringOverflow(String value, int maxLength) {
        if (value == null) {
            return null;
        } else if (value.length() < maxLength || value.length() <= 3) {
            return value;
        } else {
            return value.substring(0, maxLength - 3).concat("...");
        }
    }

    /**
     * Checks data string length, and if it greater than max length value - rejects it via errors object.
     *
     * @param data      string to be tested
     * @param maxValue  max length of tested string
     * @param errors    errors implementation object
     * @param errorCode error code in case of fail
     * @param params    error params in case of fail
     */
    public static void checkStringMaxLength(String data, int maxValue, Errors errors, String errorCode, Object[] params) {
        if (data != null && !data.isEmpty() && data.length() > maxValue) {
            errors.reject(errorCode, params, null);
        }
    }

    /**
     * Checks data string length, and if it greater than max length value - rejects it as field error via errors object.
     *
     * @param fieldName field name of bind to errors object
     * @param data      string to be tested
     * @param maxValue  max length of tested string
     * @param errors    errors implementation object
     * @param errorCode error code in case of fail
     * @param params    error params in case of fail
     */
    public static void checkStringMaxLength(String fieldName, String data, int maxValue, Errors errors, String errorCode,
                                            Object[] params) {
        if (data != null && !errors.hasFieldErrors(fieldName) && data.length() > maxValue) {
            errors.rejectValue(fieldName, errorCode, params, null);
        }
    }

    /**
     * Checks data string length, and if it lower than min length value - rejects it via errors object.
     *
     * @param data      string to be tested
     * @param minValue  min length of tested string
     * @param errors    errors implementation object
     * @param errorCode error code in case of fail
     * @param params    error params in case of fail
     */
    public static void checkStringMinLength(String data, int minValue, Errors errors, String errorCode, Object[] params) {
        if (data != null && !data.isEmpty() && data.length() < minValue) {
            errors.reject(errorCode, params, null);
        }
    }

    /**
     * Checks data string length, and if it greater than min length value - rejects it as field error via errors object.
     *
     * @param fieldName field name of bind to errors object
     * @param data      string to be tested
     * @param minValue  min length of tested string
     * @param errors    errors implementation object
     * @param errorCode error code in case of fail
     * @param params    error params in case of fail
     */
    public static void checkStringMinLength(String fieldName, String data, int minValue, Errors errors, String errorCode,
                                            Object[] params) {
        if (data != null && !errors.hasFieldErrors(fieldName) && data.length() < minValue) {
            errors.rejectValue(fieldName, errorCode, params, null);
        }
    }

    /**
     * Checks if data string matches to specific regExp pattern, in case of fail - rejects it via errors object.
     *
     * @param data      string to be tested
     * @param regex     regExp pattern
     * @param errors    errors implementation object
     * @param errorCode error code in case of fail
     * @param params    error params in case of fail
     */
    public static void checkStringMatchPattern(String data, String regex, Errors errors, String errorCode, Object[] params) {
        if (data != null && !data.isEmpty() && !data.matches(regex)) {
            errors.reject(errorCode, params, null);
        }
    }

    /**
     * Checks if data string matches to specific regExp pattern, in case of fail - rejects it as field error via errors object.
     *
     * @param fieldName field name of bind to errors object
     * @param data      string to be tested
     * @param regex     regExp pattern
     * @param errors    errors implementation object
     * @param errorCode error code in case of fail
     * @param params    error params in case of fail
     */
    public static void checkStringMatchPattern(String fieldName, String data, String regex, Errors errors, String errorCode,
                                               Object[] params) {
        if (data != null && !errors.hasFieldErrors(fieldName) && !data.matches(regex)) {
            errors.rejectValue(fieldName, errorCode, params, null);
        }
    }

    /**
     * Checks if two objects are equal, in case of fail - rejects as field error via errors object.
     *
     * @param fieldName   field name of bind to errors object
     * @param firstValue  value to check
     * @param secondValue second value to check
     * @param errors      errors implementation object
     * @param errorCode   error code in case of fail
     * @param params      error params in case of fail
     */
    public static void checkIfEquals(String fieldName, Object firstValue, Object secondValue, Errors errors, String errorCode,
                                     Object[] params) {
        if (firstValue != null && !Objects.equals(firstValue, secondValue)) {
            errors.rejectValue(fieldName, errorCode, params, null);
        }
    }


}
